package com.bot.someweire.commands.emoji.select;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class BuyIndiceCommandEmoji extends AbstractCommandSelectEmoji {

    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> event.getEmoji().
                                asUnicodeEmoji().
                                get().
                                getRaw().equals(emojiHelper.getIndiceEmoji()))
                        .filter(executed ->
                                playerHelper.playerHasEnigme(player, enigme)
                        ).flatMap(executed -> {
                            if (playerHelper.getPlayerIndicesByEnigme(player, enigme).size()
                                    >= Integer.parseInt(enigmeProperties.getIndicesNumber())) {
                                if (player
                                        .getClassType()
                                        .equals(playerProperties.getHero())) {
                                    String builder =
                                            enigmeProperties.getIndiceMax() +
                                                    "\n\n" +
                                                    enigmeProperties.getIndicePoweredAvailable();
                                    botMessage.edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                            player.getUserId(),
                                            builder
                                    )).subscribe();
                                } else {
                                    botMessage.edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                            player.getUserId(),
                                            enigmeProperties.getIndiceMax()
                                    )).subscribe();
                                }

                            } else {
                                botMessage.edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                        player.getUserId(),
                                        enigmeProperties.getIndiceConfirm()
                                )).subscribe();
                                manageReactionsConfirmEmojis(botMessage);
                            }
                            return Mono.just(executed);
                        })).blockOptional().orElse(false);
    }
}
