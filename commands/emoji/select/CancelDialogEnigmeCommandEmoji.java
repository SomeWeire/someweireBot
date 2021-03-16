package com.bot.someweire.commands.emoji.select;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class CancelDialogEnigmeCommandEmoji extends AbstractCommandSelectEmoji {

    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        final List<String> filters = new ArrayList<>();
        filters.add(enigmeProperties.getIndiceConfirm());
        filters.add(enigmeProperties.getForfeitConfirm());
        filters.add(enigmeProperties.getIndiceConfirm());
        filters.add(enigmeProperties.getForfeitPowerConfirm());
        filters.add(enigmeProperties.getPowerConfirmIndice());
        filters.add(enigmeProperties.getPowerConfirmPassEnigme());
        filters.add(enigmeProperties.getPowerConfirmIndiceMax());

        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> event
                                .getEmoji()
                                .asUnicodeEmoji()
                                .get()
                                .getRaw()
                                .equals(emojiHelper.getCancelEmoji()))
                        .filter(executed ->
                                playerHelper
                                        .playerHasEnigme(player, enigme))
                        .filter(executed ->
                                filters
                                        .stream()
                                        .anyMatch(filter -> event
                                                .getMessage()
                                                .block()
                                                .getContent()
                                                .contains(filter)))
                        .flatMap(executed -> {
                            botMessage.edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                    player.getUserId(),
                                    enigmeProperties.getSelectPublicEnigmeMessage()))
                                    .subscribe();
                            manageReactionRemoveConfirmEmojis(botMessage,
                                    player.isForfeitPower(),
                                    player.getClassType()
                                            .equals(playerProperties.getSoldier()));
                            return Mono.just(executed);
                        })).blockOptional().orElse(false);
    }

    @Override
    public types getType() {
        return types.SELECT;
    }
}
