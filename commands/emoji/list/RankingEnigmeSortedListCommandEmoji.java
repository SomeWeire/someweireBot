package com.bot.someweire.commands.emoji.list;

import com.bot.someweire.model.Character;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
class RankingEnigmeSortedListCommandEmoji extends AbstractRankingListCommandEmoji {

    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        final List<String> filters = new ArrayList<>();
        filters.add(emojiHelper.getRankingmoji());
        filters.add(emojiHelper.getUpEmoji());

        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> filters.stream().anyMatch(s ->
                                event.getEmoji()
                                        .asUnicodeEmoji()
                                        .get()
                                        .getRaw().equals(s)))
                        .filter(executed -> !isEnigmeRankingList(event))
                        .filter(executed -> !isWorldList(event))
                        .filter(executed -> !isPlayerEnigmeList(event))
                        .flatMap(executed -> {
                            try {
                                final List<Character> allPlayerByWorld = playerDao.AllCharactersByWorld(world.getId());
                                allPlayerByWorld
                                        .sort(Comparator
                                                .comparingInt(playerFromList ->
                                                        playerHelper.getPlayerSolvedEnigmesByWorld(
                                                                (Character) playerFromList,
                                                                world).size())
                                                .reversed());
                                final List<Map<String, List<String>>> embedContent = setEmbedContent(allPlayerByWorld,
                                        true);
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
                        })
                ).blockOptional().orElse(false);
    }

    @Override
    public types getType() {
        return types.MENU;
    }
}