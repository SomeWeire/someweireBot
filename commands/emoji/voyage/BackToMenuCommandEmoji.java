package com.bot.someweire.commands.emoji.voyage;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BackToMenuCommandEmoji extends AbstractVoyageCommandEmoji {

    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> event.getEmoji()
                                .asUnicodeEmoji()
                                .get()
                                .getRaw().equals(emojiHelper.getBackEmoji()))
                        .flatMap(executed -> {
                            botMessage
                                    .delete()
                                    .subscribe();
                            manageRemoveEmojis(userMessage,
                                    botMessage,
                                    false,
                                    true,
                                    this);
                            return Mono.just(executed);
                        }))
                .blockOptional().orElse(false);
    }

    @Override
    public types getType() {
        return types.MENU;
    }
}
