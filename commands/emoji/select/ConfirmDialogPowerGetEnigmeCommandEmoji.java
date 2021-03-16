package com.bot.someweire.commands.emoji.select;

import com.bot.someweire.model.Character;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ConfirmDialogPowerGetEnigmeCommandEmoji extends AbstractCommandSelectEmoji{

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
                                !playerHelper
                                        .playerHasEnigme(player, enigme))
                        .filter(executed ->
                                event
                                        .getMessage()
                                        .block()
                                        .getContent()
                                        .contains(enigmeProperties.getPowerConfirmGetEnigme()))
                        .flatMap(executed -> {
                            try {
                                Character characterUpdatedEnigme = playerDao
                                        .ownEnigmeByPower(player.getUserId(),
                                                enigme.getId(),
                                                player.getPowers() -
                                                        Integer.parseInt(enigmeProperties.getBuyEnigmePowerCost()));

                                if(characterUpdatedEnigme.getPowers() !=
                                        player.getPowers() -
                                                Integer.parseInt(enigmeProperties.getBuyEnigmePowerCost())
                                        ||
                                        !playerHelper.playerHasEnigme(characterUpdatedEnigme, enigme))
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

                                String builder = enigmeProperties.getSelectConfirmedBuy()
                                        + "\n"
                                        + "\n"
                                        + enigmeProperties.getPowerDisplay()
                                        + characterUpdatedEnigme.getPowers();

                                botMessage
                                        .edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                                player.getUserId(),
                                                builder
                                        )).subscribe();

                                manageReactionsExceptionEmojis(userMessage, botMessage);
                            } catch (Exception e){
                                botMessage
                                        .edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                                player.getUserId(),
                                                generalProperties.getError() +
                                                        " " +
                                                        generalProperties.getRetry()))
                                        .subscribe();

                                manageReactionRemoveConfirmEmojisGetEnigme(botMessage);
                            }
                            return Mono.just(executed);
                        }))
                .blockOptional().orElse(false);
    }
}
