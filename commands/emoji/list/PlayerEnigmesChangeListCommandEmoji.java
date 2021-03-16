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
public class PlayerEnigmesChangeListCommandEmoji extends AbstractEnigmesListCommandEmoji {

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
                        .filter(executed -> !isWorldList(event))
                        .flatMap(executed -> {

                            final List<Enigme> allEnigmesByPlayer = enigmeDao.getAllEnigmesByCharacterAndWorld(player.getUserId(), world.getId());

                            final List<Map<String, List<String>>> embedContent = setEmbedContent(allEnigmesByPlayer,
                                    false,
                                    player.getEnigmes());

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
                        }))
                .blockOptional().orElse(false);
    }
}
