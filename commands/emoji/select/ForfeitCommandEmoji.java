package com.bot.someweire.commands.emoji.select;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ForfeitCommandEmoji extends AbstractCommandSelectEmoji {

    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> event.getEmoji().
                                asUnicodeEmoji().
                                get().
                                getRaw().equals(emojiHelper.getForfeitEmoji()))
                        .filter(executed ->
                                playerHelper.playerHasEnigme(player, enigme)
                        ).flatMap(executed -> {

                            botMessage.edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                    player.getUserId(),
                                    enigmeProperties.getForfeitConfirm()
                            )).subscribe();

                            manageReactionsConfirmEmojis(botMessage);

                            return Mono.just(executed);
                        })
                ).blockOptional().orElse(false);
    }
}
