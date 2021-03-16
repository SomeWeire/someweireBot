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
public class RankingFragmentSortedChangeListCommandEmoji extends AbstractRankingListCommandEmoji {

    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        final List<String> filters = new ArrayList<>();
        filters.add(emojiHelper.getLeftEmoji());
        filters.add(emojiHelper.getRightEmoji());

        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> filters.stream().anyMatch(s ->
                                event.getEmoji()
                                        .asUnicodeEmoji()
                                        .get()
                                        .getRaw().equals(s)))
                        .filter(executed -> !isEnigmeRankingList(event))
                        .flatMap(executed -> {
                            final List<Character> allPlayerByWorld = playerDao.AllCharactersByWorld(world.getId());
                            allPlayerByWorld.sort(
                                    Comparator
                                            .comparingInt(playerFromList -> playerHelper.getPlayerFragmentsLeftByWorld(
                                                    (Character) playerFromList,
                                                    worldHelper.getWorldCurrency(world.getName())))
                                            .reversed());

                            final List<Map<String, List<String>>> embedContent = setEmbedContent(allPlayerByWorld,
                                    false);
                            int currentPage = getCurrentPage(event, getTableSize(embedContent));

                            botMessage.edit(messageEditSpec -> messageEditSpec.setEmbed(embedCreateSpec -> {

                                setEmbed(null,
                                        messageEditSpec,
                                        embedCreateSpec,
                                        player,
                                        world,
                                        Math.min(Math.max(currentPage +
                                                        getPreviousOrNextPageIndex(event), 0),
                                                getTableSize(embedContent)),
                                        getTableSize(embedContent),
                                        false,
                                        embedContent);

                                manageReactionsListEmojis(botMessage);
                            })).subscribe();
                            return Mono.just(executed);
                        })).blockOptional().orElse(false);
    }
}
