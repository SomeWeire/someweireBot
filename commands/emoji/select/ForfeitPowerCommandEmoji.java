package com.bot.someweire.commands.emoji.select;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ForfeitPowerCommandEmoji extends AbstractCommandSelectEmoji {

    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> event.getEmoji().
                                asUnicodeEmoji().
                                get().
                                getRaw().equals(emojiHelper.getForfeitPowerEmoji()))
                        .filter(executed ->
                                playerHelper.playerHasEnigme(player, enigme)
                        ).flatMap(executed -> {
                            if (
                                    playerHelper
                                            .getPlayerIndicesByEnigme(player, enigme)
                                            .stream()
                                            .filter(indice -> playerHelper
                                                    .getPlayerIndiceProperties(player, indice.getId())
                                                    .isPowered()).count() != Integer.parseInt(enigmeProperties.getIndicesNumber())
                            ) {
                                botMessage.edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                        player.getUserId(),
                                        enigmeProperties.getForfeitPowerConfirm()
                                )).subscribe();
                                manageReactionsConfirmEmojis(botMessage);
                            } else
                                botMessage.edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                        player.getUserId(),
                                        enigmeProperties.getIndicePoweredMax()
                                )).subscribe();
                            return Mono.just(executed);
                        })
                ).blockOptional().orElse(false);
    }
}
