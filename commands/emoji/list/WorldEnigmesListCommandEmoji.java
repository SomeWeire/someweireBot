package com.bot.someweire.commands.emoji.list;

import com.bot.someweire.model.Enigme;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class WorldEnigmesListCommandEmoji extends AbstractEnigmesListCommandEmoji {

    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        final List<String> filters = new ArrayList<>();
        filters.add(emojiHelper.getEnigmeListEmoji());
        filters.add(emojiHelper.getUpEmoji());

        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> filters.stream().anyMatch(s ->
                                event.getEmoji()
                                        .asUnicodeEmoji()
                                        .get()
                                        .getRaw().equals(s)))
                        .filter(executed -> !isWorldList(event))
                        .filter(executed -> !isFragmentRankingList(event))
                        .filter(executed -> !isEnigmeRankingList(event))
                        .flatMap(executed -> {
                            try {
                                final List<Enigme> allEnigmesByWorld = enigmeDao.getAllEnigmesByWorld(world.getId());

                                final List<Map<String, List<String>>> embedContent = setEmbedContent(allEnigmesByWorld,
                                        true,
                                        player.getEnigmes());

                                subscriptionManager.cancelSubscriptionOnMessage(userMessage, botMessage);
                                botMessage
                                        .delete()
                                        .then(
                                                botMessage
                                                        .getChannel()
                                                        .block()
                                                        .createMessage(messageCreateSpec -> messageCreateSpec
                                                                .setEmbed(embedCreateSpec -> {
                                                                    setEmbed(messageCreateSpec,
                                                                            null,
                                                                            embedCreateSpec,
                                                                            player,
                                                                            world,
                                                                            0,
                                                                            getTableSize(embedContent),
                                                                            true,
                                                                            embedContent);
                                                                })))
                                        .subscribe(newBotMessage -> {
                                            manageReactionsListEmojis(newBotMessage);
                                            manageAddReactionEventToEnigmeListEmojis(userMessage,
                                                    newBotMessage,
                                                    this);
                                        });
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
