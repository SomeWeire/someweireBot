package com.bot.someweire.commands.emoji.list;

import com.bot.someweire.commands.abstractModel.AbstractCommandEmoji;
import com.bot.someweire.model.Character;
import com.bot.someweire.model.World;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;

import java.util.List;
import java.util.Map;

public abstract class AbstractListCommandEmoji  extends AbstractCommandEmoji {

    protected Character player;
    protected World world;

    public void loadContext(Character player,
                            World world) {
        this.player = player;
        this.world = world;
    }

    protected void manageReactionsListEmojis(Message botMessage) {
        botMessage.removeAllReactions()
                .then(botMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getLeftEmoji())))
                .then(botMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getRightEmoji())))
                .then(botMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getDownEmoji())))
                .then(botMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getUpEmoji())))
                .then(botMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getBackEmoji())))
                .subscribe();
    }

    protected int getCurrentPage(ReactionAddEvent event, int tableSize) {
        String message = "";
        if(isEnigmeRankingList(event))
            message = playerProperties.getRankingEnigmeListMessage();

        if(isFragmentRankingList(event))
            message = playerProperties.getRankingFragmentListMessage();

        if(isWorldList(event))
            message = enigmeProperties.getWorldListMessage();

        if(isPlayerEnigmeList(event))
            message = enigmeProperties.getPlayerListMessage();

        return Integer.parseInt(
                String.valueOf(
                        event.
                                getMessage()
                                .block()
                                .getContent()
                                .replace(message, "")
                                .replace(enigmeProperties.getWorldListContent(), "")
                                .replace("/" + String.valueOf(tableSize), ""))
                        .trim());
    }

    protected int getTableSize(List<Map<String, List<String>>> embedContent) {
        int tableSize = embedContent.
                stream()
                .findFirst()
                .get()
                .values()
                .stream()
                .findFirst()
                .get()
                .size() - 1;
        return Math.max(tableSize, 0);
    }

    protected int getPreviousOrNextPageIndex(ReactionAddEvent event) {
        if (event.getEmoji()
                .asUnicodeEmoji()
                .get()
                .getRaw().equals(emojiHelper.getRightEmoji())) return 1;
        else if (event.getEmoji()
                .asUnicodeEmoji()
                .get()
                .getRaw().equals(emojiHelper.getLeftEmoji())) return -1;
        else return 0;
    }

    protected boolean isWorldList(ReactionAddEvent event) {
        return event
                .getMessage()
                .block().
                        getEmbeds().
                        stream().
                        findFirst().
                        get().
                        getTitle().
                        get().
                        equals(enigmeProperties.getWorldListTitle());
    }

    protected boolean isEnigmeRankingList(ReactionAddEvent event) {
        return event
                .getMessage()
                .block().
                        getEmbeds().
                        stream().
                        findFirst().
                        get().
                        getTitle().
                        get().
                        equals(playerProperties.getEnigmeSortedRanking());
    }

    protected boolean isFragmentRankingList(ReactionAddEvent event) {
        return event
                .getMessage()
                .block().
                        getEmbeds().
                        stream().
                        findFirst().
                        get().
                        getTitle().
                        get().
                        equals(playerProperties.getFragmentsSortedRanking());
    }

    protected boolean isPlayerEnigmeList(ReactionAddEvent event) {
        return event
                .getMessage()
                .block().
                        getEmbeds().
                        stream().
                        findFirst().
                        get().
                        getTitle().
                        get().
                        equals(enigmeProperties.getPlayerListTitle());
    }
}
