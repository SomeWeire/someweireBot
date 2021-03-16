package com.bot.someweire.commands.emoji.select;

import com.bot.someweire.model.Character;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ConfirmDialogPowerPassEnigmeCommandEmoji extends AbstractCommandSelectEmoji {

    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
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
                                event
                                        .getMessage()
                                        .block()
                                        .getContent()
                                        .contains(enigmeProperties.getPowerConfirmPassEnigme()))
                        .flatMap(executed -> {
                            try {
                                Character characterUpdatedEnigme = playerDao
                                        .solveEnigmeByPower(player.getUserId(),
                                                enigme.getId(),
                                                playerHelper
                                                        .getPlayerEnigmeProperties(player, enigme)
                                                        .getTries(),
                                                player.getPowers() -
                                                        Integer.parseInt(enigmeProperties.getPassEnigmePowerCost()));

                                if (characterUpdatedEnigme.getPowers() !=
                                        player.getPowers() -
                                                Integer.parseInt(enigmeProperties.getPassEnigmePowerCost())
                                        ||
                                        !playerHelper.getPlayerEnigmeProperties(characterUpdatedEnigme,
                                                enigme)
                                                .isPowered()) {
                                    throw new Exception();
                                }

                                String builder = enigmeProperties.getPowerPassEnigme()
                                        + "\n"
                                        + enigmeProperties.getPowerDisplay()
                                        + characterUpdatedEnigme.getPowers();

                                botMessage
                                        .edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                                player.getUserId(),
                                                builder
                                        )).subscribe();

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
                                                            enigmeProperties.getForfeitEnigmeSolution()
                                                            + "**"
                                                            + enigmeDao.getEnigmeSolution(enigme.getId())
                                                            + "**");
                                        }).subscribe();

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
                        })
                ).blockOptional().orElse(false);
    }
}
