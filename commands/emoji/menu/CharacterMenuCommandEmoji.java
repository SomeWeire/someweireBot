package com.bot.someweire.commands.emoji.menu;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CharacterMenuCommandEmoji extends AbstractMenuCommandEmoji {

    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> event.getEmoji()
                                .asUnicodeEmoji()
                                .get()
                                .getRaw().equals(emojiHelper.getProfileEmoji()))
                        .flatMap(executed -> {
                            subscriptionManager.cancelSubscriptionOnMessage(userMessage, botMessage);
                            botMessage
                                    .delete()
                                    .then(botMessage.getChannel().block().createMessage(messageCreateSpec -> {
                                        messageCreateSpec.setEmbed(embedCreateSpec -> {
                                            setProfileEmbed(embedCreateSpec,
                                                    messageCreateSpec);
                                        });
                                    })).subscribe(newMessage -> {
                                manageRemoveEmojis(newMessage);
                                manageAddReactionEventToMenuEmojis(userMessage, newMessage);
                            });
                            return Mono.just(executed);
                        }))
                .blockOptional().orElse(false);
    }
}
