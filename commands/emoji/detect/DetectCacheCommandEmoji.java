package com.bot.someweire.commands.emoji.detect;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DetectCacheCommandEmoji extends AbstractDetectCommandEmoji {

    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> event.getEmoji().
                                asUnicodeEmoji().
                                get().
                                getRaw().equals(emojiHelper.getDetectCommandEmoji()))
                        .flatMap(executed -> {
                            try {
                                botMessage.edit(messageEditSpec -> {
                                    if (playerHelper
                                            .getPlayerPosition(player)
                                            .getCaches()
                                            .isEmpty()) {
                                        sendEditMessage(messageEditSpec,
                                                player.getUserId(),
                                                enigmeProperties.getDetectTitle() +
                                                        " : " +
                                                        enigmeProperties.getDetectNoCache());
                                    } else if (playerHelper.playerHasFragment(
                                            player,
                                            playerHelper.getFragmentFromPlayerPositionCache(player))) {
                                        sendEditMessage(messageEditSpec, player.getUserId(),
                                                enigmeProperties.getDetectTitle() +
                                                        " : " +
                                                        enigmeProperties.getDetectFound());
                                    } else if (
                                            playerHelper
                                                    .getEnigmeFromPlayerPositionCache(player) != null &&
                                                    !playerHelper.playerHasEnigme(player,
                                                            playerHelper
                                                                    .getEnigmeFromPlayerPositionCache(player))) {
                                        sendEditMessage(messageEditSpec,
                                                player.getUserId(),
                                                enigmeProperties.getDetectTitle() +
                                                        " : " +
                                                        enigmeProperties.getDetectIsEnigme() +
                                                        "**" +
                                                        playerHelper
                                                                .getEnigmeFromPlayerPositionCache(player)
                                                                .getNumero() +
                                                        "**" +
                                                        "." +
                                                        enigmeProperties.getDetectIsEnigmeBuyMessage());
                                    } else if (
                                            playerHelper
                                                    .getEnigmeFromPlayerPositionCache(player) != null &&
                                                    !playerHelper
                                                            .getPlayerEnigmeProperties(
                                                                    player,
                                                                    playerHelper.getEnigmeFromPlayerPositionCache(player))
                                                            .isSolved() &&
                                                    !playerHelper
                                                            .getPlayerEnigmeProperties(
                                                                    player,
                                                                    playerHelper
                                                                            .getEnigmeFromPlayerPositionCache(player))
                                                            .isForfeit() &&
                                                    !playerHelper
                                                            .getPlayerEnigmeProperties(
                                                                    player,
                                                                    playerHelper
                                                                            .getEnigmeFromPlayerPositionCache(player))
                                                            .isPowered()) {
                                        sendEditMessage(messageEditSpec,
                                                player.getUserId(),
                                                enigmeProperties.getDetectTitle() +
                                                        " : " +
                                                        enigmeProperties.getDetectIsEnigme() +
                                                        "**" +
                                                        playerHelper
                                                                .getEnigmeFromPlayerPositionCache(player)
                                                                .getNumero() +
                                                        "**" +
                                                        "." +
                                                        enigmeProperties.getDetectIsEnigmeNotSolvedMessage());
                                    } else {
                                        subscriptionManager.cancelSubscriptionOnMessage(userMessage, botMessage);
                                        botMessage
                                                .delete()
                                                .then(botMessage.getChannel().block().createMessage(messageCreateSpec -> {
                                                    messageCreateSpec.setEmbed(embedCreateSpec -> {
                                                        setEmbed(embedCreateSpec,
                                                                messageCreateSpec);
                                                    });
                                                })).subscribe(newMessage -> {
                                            manageRemoveEmojis(newMessage);
                                            manageAddReactionEventToMenuEmojis(userMessage, newMessage);
                                        });
                                    }
                                }).subscribe();
                            } catch (Exception e) {
                                botMessage.edit(messageEditSpec -> sendEditMessage(messageEditSpec, player.getUserId(),
                                        generalProperties.getError() + " " + generalProperties.getRetry())).subscribe();
                            }
                            return Mono.just(executed);
                        }))
                .blockOptional().orElse(false);
    }

    @Override
    public types getType() {
        return types.MENU;
    }
}
