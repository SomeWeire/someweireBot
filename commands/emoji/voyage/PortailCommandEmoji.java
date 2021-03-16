package com.bot.someweire.commands.emoji.voyage;

import com.bot.someweire.model.Lieu;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PortailCommandEmoji extends AbstractVoyageCommandEmoji{


    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> event.getEmoji().
                                asUnicodeEmoji().
                                get().
                                getRaw().equals(emojiHelper.getPortailEmoji()))
                        .flatMap(executed -> {
                            botMessage.edit(messageEditSpec -> {
                                try {
                                    if (playerHelper
                                            .getPlayerPosition(player)
                                            .getPortals()
                                            .isEmpty()) {
                                        sendEditMessage(messageEditSpec,
                                                player.getUserId(),
                                                lieuProperties.getPortailTitle() +
                                                        " : " +
                                                        lieuProperties.getPortailEmpty());
                                    } else {
                                        Lieu newLieu = updateLocationFromPortal(player);
                                        if (newLieu.getId().equals(
                                                playerHelper
                                                        .getPlayerPosition(player)
                                                        .getId()))
                                            throw new Exception();

                                            sendEditMessage(messageEditSpec,
                                                    player.getUserId(),
                                                    lieuProperties.getPortailTitle() +
                                                            " : " +
                                                            lieuProperties.getPortailSuccess() +
                                                            " " +
                                                            lieuProperties.getVoyageSuccess() +
                                                            " "+
                                                            newLieu.getName());
                                            updateRole(userMessage
                                                            .getAuthorAsMember()
                                                            .block(),
                                                    world.getName());
                                            manageRemoveEmojis(userMessage,
                                                    botMessage,
                                                    true,
                                                    false,
                                                    this);
                                    }
                                } catch (Exception e){
                                    sendEditMessage(messageEditSpec,
                                                    player.getUserId(),
                                                    generalProperties.getError() +
                                                            " " +
                                                            generalProperties.getRetry());
                                }
                            }).subscribe();
                            return Mono.just(executed);
                        })).blockOptional().orElse(false);
    }
}
