package com.bot.someweire.commands.emoji.list;

import com.bot.someweire.model.Character;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
public class RankingFragmentSortedListCommandEmoji extends AbstractRankingListCommandEmoji {
    @Override
    public boolean tryExecute(Message userMessage, Message botMessage, ReactionAddEvent event) {
        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> event.getEmoji()
                                .asUnicodeEmoji()
                                .get()
                                .getRaw().equals(emojiHelper.getDownEmoji()))
                        .filter(executed -> isEnigmeRankingList(event))
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

                            botMessage.edit(messageEditSpec -> messageEditSpec.setEmbed(embedCreateSpec -> {

                                setEmbed(null,
                                        messageEditSpec,
                                        embedCreateSpec,
                                        player,
                                        world,
                                        0,
                                        getTableSize(embedContent),
                                        false,
                                        embedContent);

                                manageReactionsListEmojis(botMessage);
                            })).subscribe();
                            return Mono.just(executed);
                        })).blockOptional().orElse(false);
    }
}
