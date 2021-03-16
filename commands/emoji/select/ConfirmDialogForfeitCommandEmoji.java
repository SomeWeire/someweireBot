package com.bot.someweire.commands.emoji.select;

import com.bot.someweire.model.Character;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ConfirmDialogForfeitCommandEmoji extends AbstractCommandSelectEmoji {

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
                                        .contains(enigmeProperties.getForfeitConfirm()))
                        .flatMap(executed -> {
                            try {
                                Character characterUpdated = playerDao
                                        .forfeitByCharacter(player.getUserId(),
                                                enigme.getId());

                                if (!characterUpdated
                                        .isForfeitPower() ||
                                        !playerHelper
                                                .getPlayerEnigmeProperties(characterUpdated,
                                                        enigme)
                                                .isForfeit())
                                    throw new Exception();

                                botMessage.edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                        player.getUserId(),
                                        enigmeProperties.getForfeit()
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
                                botMessage.edit(messageEditSpec -> sendEditMessage(messageEditSpec, player.getUserId(),
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
