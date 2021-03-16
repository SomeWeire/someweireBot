package com.bot.someweire.commands.emoji.voyage;

import com.bot.someweire.model.Lieu;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class VoyageCommandEmoji extends AbstractVoyageCommandEmoji{

    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        final List<String> filters = new ArrayList<>(emojiHelper.getRoadChoiceEmojis());

        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> filters.stream().anyMatch(s ->
                                event.getEmoji()
                                        .asUnicodeEmoji()
                                        .get()
                                        .getRaw().equals(s)))
                        .flatMap(executed -> {
                            int choice = filters.indexOf(filters
                                    .stream()
                                    .filter(s -> event.getEmoji()
                                            .asUnicodeEmoji()
                                            .get()
                                            .getRaw()
                                            .equals(s))
                                    .findFirst()
                                    .get())
                                    + 1;
                            botMessage.edit(messageEditSpec -> {
                                try {
                                    if(choice > roads.size()){
                                        sendEditMessage(messageEditSpec,
                                                player.getUserId(),
                                                lieuProperties.getVoyageTitle()+
                                                        " : "+
                                                        lieuProperties.getVoyageError());
                                    } else {
                                        Lieu newLieu = updateLocation(player,
                                                choice,
                                                roads);
                                        if (newLieu == null || newLieu
                                                .getId()
                                                .equals(playerHelper
                                                        .getPlayerPosition(player)
                                                        .getId()))
                                           throw new Exception();
                                        botMessage
                                                .delete()
                                                .subscribe();
                                        sendEditMessage(messageEditSpec,
                                                    player.getUserId(),
                                                    lieuProperties.getVoyageTitle() +
                                                            " : " +
                                                            lieuProperties.getVoyageSuccess() +
                                                            " "+
                                                            "***"+
                                                            newLieu.getName()+
                                                            "***");
                                        manageRemoveEmojis(userMessage,
                                                botMessage,
                                                false,
                                                true,
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
                        }))
                .blockOptional().orElse(false);
    }
}
