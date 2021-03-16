package com.bot.someweire.commands.emoji.list;

import com.bot.someweire.model.Character;
import com.bot.someweire.model.World;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.core.spec.MessageEditSpec;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractRankingListCommandEmoji extends AbstractListCommandEmoji{

    public void setEmbed(MessageCreateSpec messageCreateSpec,
                         MessageEditSpec messageEditSpec,
                         EmbedCreateSpec embedCreateSpec,
                         Character player,
                         World world,
                         int index,
                         int tableSize,
                         boolean isRankingEnigmeList,
                         List<Map<String, List<String>>> embedContent) {

        String messageContent = "";

        attachImageAndChangeColorToEmbed(embedCreateSpec,
                null,
                null,
                "",
                world.getName(),
                player,
                EMBED_AUTHOR_INFO_CLASSTYPE);

        if (isRankingEnigmeList) {
            embedCreateSpec.setTitle(playerProperties.getEnigmeSortedRanking());
            messageContent += playerProperties.getRankingEnigmeListMessage();
        } else {
            embedCreateSpec.setTitle(playerProperties.getFragmentsSortedRanking());
            messageContent += playerProperties.getRankingFragmentListMessage();
        }

        embedCreateSpec.setDescription("*" + world.getName() + "*");

        messageContent += "\n\n" +
                enigmeProperties.getWorldListContent() +
                index +
                "/" +
                tableSize;

        if (messageCreateSpec != null)
            messageCreateSpec
                    .setContent(messageContent);
        if (messageEditSpec != null)
            messageEditSpec
                    .setContent(messageContent);

        embedContent.forEach(stringListMap -> {
            stringListMap.forEach((s, strings) -> {
                    if (!strings.isEmpty()) {
                        embedCreateSpec.addField(s, strings.get(index), true);
                    } else {
                        embedCreateSpec.addField(s, "-", true);
                    }
            });
        });
    }

    protected LinkedList<Map<String, List<String>>> setEmbedContent(List<Character> listPlayers,
                                                                    boolean isRankingEnigmeList) {

        List<String> stringListRank = new LinkedList<>();
        List<String> stringListName = new LinkedList<>();
        List<String> stringThirdColumn = new LinkedList<>();

        List<String> embedContentRank = new LinkedList<>();
        List<String> embedContentName = new LinkedList<>();
        List<String> embedContentThirdColumn = new LinkedList<>();

            listPlayers.forEach(playerFromList -> {
                if(listPlayers.indexOf(playerFromList)+1 == 1){
                    stringListRank.add(emojiHelper.getFirstfPlaceEmoji());
                } else if (listPlayers.indexOf(playerFromList)+1 == 2){
                    stringListRank.add(emojiHelper.getSecondPlaceEmoji());
                } else if (listPlayers.indexOf(playerFromList)+1 == 3){
                    stringListRank.add(emojiHelper.getThirdPlaceEmoji());
                } else
                    stringListRank.add(String.valueOf(listPlayers.indexOf(playerFromList)+1));
                stringListName.add(playerFromList.getName());
                if (isRankingEnigmeList) {
                    stringThirdColumn.add(String.valueOf(playerHelper
                            .getPlayerSolvedEnigmesByWorld(playerFromList, world)
                            .size()));
                } else {
                    stringThirdColumn.add(String.valueOf(playerHelper.getPlayerFragmentsLeftByWorld(
                            playerFromList,
                            worldHelper.getWorldCurrency(world.getName()))));
                }
            });

        int k = 10;
        for (int i = 0; i < listPlayers.size(); i += 10) {

            List<String> tenRankList = stringListRank.stream()
                    .skip(i)
                    .limit(k)
                    .collect(Collectors.
                            toList());

            List<String> tenNameList = stringListName.stream()
                    .skip(i)
                    .limit(k)
                    .collect(Collectors.
                            toList());

            List<String> tenThirdColumnList = stringThirdColumn.stream()
                    .skip(i)
                    .limit(k)
                    .collect(Collectors.
                            toList());

            k += 10;
            embedContentRank.add(tenRankList.stream().reduce((s, s2) -> {
                return "\n" + s + "\n" + s2;
            }).get());

            embedContentName.add(tenNameList.stream().reduce((s, s2) -> {
                return "\n" + s + "\n" + s2;
            }).get());

            embedContentThirdColumn.add(tenThirdColumnList.stream().reduce((s, s2) -> {
                return "\n" + s + "\n" + s2;
            }).get());
        }

        LinkedList<Map<String, List<String>>> maps = new LinkedList<>();

        maps.add(Map.of(playerProperties.getRankingRankColumn(), embedContentRank));
        maps.add(Map.of(playerProperties.getRankingNameColumn(), embedContentName));
        if (isRankingEnigmeList) {
            maps.add(Map.of(playerProperties.getRankingSolvedEnigmeNumberColumn(), embedContentThirdColumn));
        } else {
            maps.add(Map.of(
                    worldHelper.getWorldCurrency(world.getName()) + " " + playerProperties.getRankingFragmentLeftNumberColumn(),
                    embedContentThirdColumn));
        }
        return maps;
    }

    @Override
    public types getType() {
        return types.PLAYERLIST;
    }

    protected void manageAddReactionEventToEnigmeListEmojis(Message userMessage,
                                                            Message botMessage,
                                                            RankingEnigmeSortedListCommandEmoji sortedListCommandEmoji) {
        if(emojisCommands
                .stream()
                .noneMatch(commandEmoji -> commandEmoji instanceof RankingEnigmeSortedListCommandEmoji))
            emojisCommands.add(sortedListCommandEmoji);

        subscriptionManager.addSubscription(
                userMessage,
                botMessage,
                botMessage
                        .getClient()
                        .on(ReactionAddEvent.class)
                        .subscribe(reactionAddEvent -> emojiCommandVisitor
                                .manageEmojiCommands(emojisCommands
                                                .stream()
                                                .filter(commandEmoji ->
                                                        commandEmoji
                                                                .getType()
                                                                .equals(types.PLAYERLIST) ||
                                                                commandEmoji
                                                                        .getType()
                                                                        .equals(types.MENU))
                                                .collect(Collectors.toList()),
                                        player,
                                        world,
                                        null,
                                        null,
                                        userMessage,
                                        botMessage,
                                        reactionAddEvent)));
    }
}
