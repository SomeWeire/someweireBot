package com.bot.someweire.commands.emoji.select;

import com.bot.someweire.model.Indice;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class PowerGetIndiceCommandEmoji extends AbstractCommandSelectEmoji {

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
                                playerHelper.playerHasEnigme(player, enigme))
                        .filter(executed ->
                                player.getClassType().equals(playerProperties.getHero()))
                        .flatMap(executed -> {
                            botMessage.edit(messageEditSpec -> {
                                StringBuilder builder = new StringBuilder();

                                if(player.getPowers() >= 1) {

                                    List<Indice> byCharacterAndEnigme =
                                            playerHelper.getPlayerIndicesByEnigme(player, enigme);

                                    if(byCharacterAndEnigme.size() < Integer
                                            .parseInt(enigmeProperties.getIndicesNumber())) {

                                        builder.append(enigmeProperties.getPowerConfirmIndice());
                                        builder.append("\n");
                                        builder.append(enigmeProperties.getPowerDisplay());
                                        builder.append(player.getPowers());
                                        sendEditMessage(messageEditSpec,
                                                player.getUserId(),
                                                builder.toString()
                                        );
                                        manageReactionsConfirmEmojis(botMessage);

                                    } else if(byCharacterAndEnigme.stream().filter(indice -> playerHelper.
                                            getPlayerIndiceProperties(player, indice.getId())
                                            .isPowered()).count() != Integer
                                            .parseInt(enigmeProperties.getIndicesNumber())){

                                        builder.append(enigmeProperties.getPowerConfirmIndiceMax());
                                        builder.append("\n");
                                        builder.append(enigmeProperties.getPowerDisplay());
                                        builder.append(player.getPowers());
                                        sendEditMessage(messageEditSpec,
                                                player.getUserId(),
                                                builder.toString()
                                        );
                                        manageReactionsConfirmEmojis(botMessage);

                                    } else {
                                        sendEditMessage(messageEditSpec,
                                                player.getUserId(),
                                                enigmeProperties.getIndicePoweredMax()
                                        );
                                    }
                                } else sendEditMessage(messageEditSpec,
                                            player.getUserId(),
                                            enigmeProperties.getPowerNotEnough());
                            }).subscribe();
                         return Mono.just(executed);
                        }))
                .blockOptional().orElse(false);
    }
}
