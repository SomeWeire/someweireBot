package com.bot.someweire.commands.emoji.select;

import com.bot.someweire.model.Character;
import com.bot.someweire.model.Indice;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConfirmDialogPowerGetIndiceCommandEmoji extends AbstractCommandSelectEmoji {

    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        final List<String> filters = new ArrayList<>();
        filters.add(enigmeProperties.getPowerConfirmIndice());
        filters.add(enigmeProperties.getPowerConfirmIndiceMax());

        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> event
                                .getEmoji()
                                .asUnicodeEmoji()
                                .get()
                                .getRaw()
                                .equals(emojiHelper.getConfirmEmoji()))
                        .filter(executed ->
                                playerHelper
                                        .playerHasEnigme(player, enigme))
                        .filter(executed ->
                                filters
                                        .stream()
                                        .anyMatch(filter -> event
                                                .getMessage()
                                                .block()
                                                .getContent()
                                                .contains(filter)))
                        .flatMap(executed -> {
                            try {
                                final List<Indice> indicesNumber = playerHelper
                                        .getPlayerIndicesByEnigme(player, enigme);

                                final List<Indice> byEnigme = playerHelper
                                        .getIndicesByPlayerEnigme(player, enigme);

                                Character characterUpdated;

                                Indice newPoweredIndice;

                                if (indicesNumber.size() >= Integer
                                        .parseInt(enigmeProperties.getIndicesNumber())) {

                                    newPoweredIndice = indicesNumber.stream()
                                            .filter(indice -> !playerHelper.getPlayerIndiceProperties(player, indice.getId()).isPowered())
                                            .findFirst()
                                            .get();

                                    characterUpdated = playerDao
                                            .powerOwnedIndice(player.getUserId(),
                                                    newPoweredIndice.getId(),
                                                    player.getPowers() -
                                                            Integer.parseInt(enigmeProperties.getIndicePowerCost()));
                                } else {
                                    newPoweredIndice = byEnigme.stream()
                                            .filter(indice -> indice.getNumero() == indicesNumber.size() + 1)
                                            .findFirst()
                                            .get();

                                    characterUpdated = playerDao
                                            .ownPoweredIndice(player.getUserId(),
                                                    newPoweredIndice.getId(),
                                                    player.getPowers() -
                                                            Integer.parseInt(enigmeProperties.getIndicePowerCost()));
                                }

                                final Long newPoweredIndiceId = newPoweredIndice.getId();

                                if (playerHelper
                                        .getPlayerIndices(characterUpdated)
                                        .stream()
                                        .noneMatch(indice -> indice.getId().equals(newPoweredIndiceId)))
                                    throw new Exception();

                                if (!playerHelper
                                        .getPlayerIndiceProperties(characterUpdated, newPoweredIndiceId)
                                        .isPowered())
                                    throw new Exception();

                                if (characterUpdated.getPowers() != player.getPowers() -
                                        Integer.parseInt(enigmeProperties.getIndicePowerCost()))
                                    throw new Exception();

                                userMessage
                                        .getAuthor()
                                        .get()
                                        .getPrivateChannel()
                                        .block()
                                        .createMessage(messageCreateSpec -> {
                                            sendUpdateMessage(messageCreateSpec,
                                                    player.getUserId(),
                                                    enigmeProperties.getSelectTitle() +
                                                            context.getSelectedEnigme().getNumero() +
                                                            " : " +
                                                            enigmeProperties.getSelectEnigmePrivateMessage());
                                        }).subscribe();

                                String builder = enigmeProperties.getPowerIndice()
                                        + "\n"
                                        + "\n"
                                        + enigmeProperties.getPowerDisplay()
                                        +
                                        characterUpdated.getPowers();

                                botMessage.edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                        player.getUserId(),
                                        builder
                                )).subscribe();

                                manageReactionsExceptionEmojis(userMessage, botMessage);
                            } catch (Exception e) {
                                botMessage
                                        .edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                                player.getUserId(),
                                                generalProperties.getError() +
                                                        " " +
                                                        generalProperties.getRetry()))
                                        .subscribe();

                                manageReactionRemoveConfirmEmojis(botMessage,
                                        player.isForfeitPower(),
                                        player.getClassType()
                                                .equals(playerProperties.getSoldier()));
                            }
                            return Mono.just(executed);

                        })).blockOptional().orElse(false);
    }
}
