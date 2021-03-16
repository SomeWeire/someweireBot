package com.bot.someweire.commands.emoji.select;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CancelDialogBuyEnigmeCommandEmoji extends AbstractCommandSelectEmoji{

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
                                .equals(emojiHelper.getCancelEmoji()))
                        .filter(executed ->
                                !playerHelper
                                        .playerHasEnigme(player, enigme))
                        .filter(executed ->event
                                                .getMessage()
                                                .block()
                                                .getContent()
                                                .contains(enigmeProperties.getPowerConfirmGetEnigme())))
                        .flatMap(executed -> {
                            botMessage.edit(messageEditSpec -> sendEditMessage(messageEditSpec,
                                    player.getUserId(),
                                    rebuildBuyEnigmeMessage()))
                                    .subscribe();
                            manageReactionRemoveConfirmEmojisGetEnigme(botMessage);
                            return Mono.just(executed);
                        }).blockOptional().orElse(false);
    }

    private String rebuildBuyEnigmeMessage(){
        return enigmeProperties.getSelectTitle() +
                " **" +
                enigme.getNumero() +
                "**" +
                " : " +
                enigmeProperties.getSelectConfirmBuy();
    }
}
