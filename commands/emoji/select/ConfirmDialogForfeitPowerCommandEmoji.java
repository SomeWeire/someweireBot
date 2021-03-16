package com.bot.someweire.commands.emoji.select;

import com.bot.someweire.model.Indice;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConfirmDialogForfeitPowerCommandEmoji extends AbstractCommandSelectEmoji {

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
                                        .contains(enigmeProperties.getForfeitPowerConfirm()))
                        .flatMap(executed -> {
                            try {
                                List<Indice> indicesUpdated = new ArrayList<>();

                                if(playerHelper.getPlayerIndicesByEnigme(player, enigme).size()
                                        >= Integer.parseInt(enigmeProperties.getIndicesNumber())){
                                    indicesUpdated.addAll(indiceDao
                                            .userForfeitPowerForPlayerWhenHasAllIndices(player.getUserId(), enigme.getId()));
                                } else {
                                    indicesUpdated.addAll(indiceDao
                                            .userForfeitPowerForPlayerWhenHasNotAllIndices(player.getUserId(), enigme.getId()));
                                }

                                if (indicesUpdated.size()==Integer.parseInt(enigmeProperties.getIndicesNumber())){
                                    botMessage.edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                            player.getUserId(),
                                            enigmeProperties.getForfeitPower()
                                    )).subscribe();

                                    manageReactionsExceptionEmojis(userMessage, botMessage);
                                } else throw new Exception();

                            } catch (Exception e){
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