package com.bot.someweire.commands.emoji.select;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PowerGetEnigmeCommandEmoji extends AbstractCommandSelectEmoji {

    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> event.getEmoji().
                                asUnicodeEmoji().
                                get().
                                getRaw().equals(emojiHelper.getPowerEmoji()))
                        .filter(executed ->
                                !playerHelper.playerHasEnigme(player, enigme))
                        .filter(executed ->
                                player.getClassType().equals(playerProperties.getSoldier()))
                        .flatMap(executed -> {
                            botMessage.edit(messageEditSpec -> {
                                if (player.getPowers() >= 1) {
                                    String builder = enigmeProperties.getPowerConfirmGetEnigme() +
                                            "\n" +
                                            enigmeProperties.getPowerDisplay() +
                                            player.getPowers();
                                    sendEditMessage(messageEditSpec,
                                            player.getUserId(),
                                            builder
                                    );
                                    manageReactionsConfirmEmojis(botMessage);
                                } else sendEditMessage(messageEditSpec,
                                        player.getUserId(),
                                        enigmeProperties.getPowerNotEnough());
                            }).subscribe();
                            return Mono.just(executed);
                        }))
                .blockOptional().orElse(false);
    }
}
