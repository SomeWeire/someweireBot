package com.bot.someweire.commands.emoji.list;

import com.bot.someweire.model.Character;
import com.bot.someweire.model.Enigme;
import com.bot.someweire.model.HasEnigmeProperties;
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

public abstract class AbstractEnigmesListCommandEmoji extends AbstractListCommandEmoji {

    public void setEmbed(MessageCreateSpec messageCreateSpec,
                         MessageEditSpec messageEditSpec,
                         EmbedCreateSpec embedCreateSpec,
                         Character player,
                         World world,
                         int index,
                         int tableSize,
                         boolean isWorldList,
                         List<Map<String, List<String>>> embedContent) {

        String messageContent = "";

        final String nameCurrency = worldHelper.getWorldCurrency(world.getName());

        attachImageAndChangeColorToEmbed(embedCreateSpec,
                null,
                worldsProperties.getThumbPath(),
                "",
                world.getName(),
                player,
                EMBED_AUTHOR_INFO_FRAGMENTS);

        if (isWorldList) {
            embedCreateSpec.setTitle(enigmeProperties.getWorldListTitle());
            messageContent += enigmeProperties.getWorldListMessage();
        } else {
            embedCreateSpec.setTitle(enigmeProperties.getPlayerListTitle());
            messageContent += enigmeProperties.getPlayerListMessage();
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
                if (s.equals(enigmeProperties.getWorldListPrixColumn())) {
                    if (!strings.isEmpty()) {
                        embedCreateSpec.addField(s + " (" + nameCurrency.toLowerCase() + ")", strings.get(index), true);
                    } else {
                        embedCreateSpec.addField(s + " (" + nameCurrency.toLowerCase() + ")", "-", true);
                    }
                } else {
                    if (!strings.isEmpty()) {
                        embedCreateSpec.addField(s, strings.get(index), true);
                    } else {
                        embedCreateSpec.addField(s, "-", true);
                    }
                }
            });
        });
    }

    protected LinkedList<Map<String, List<String>>> setEmbedContent(List<Enigme> listEnigmes,
                                                                    boolean isWorldList,
                                                                    Map<Enigme, HasEnigmeProperties> listEnigmesPlayer) {

        List<String> stringListNumeros = new LinkedList<>();
        List<String> stringListTitres = new LinkedList<>();
        List<String> stringListThirdColumn = new LinkedList<>();


        List<String> embedContentNumeros = new LinkedList<>();
        List<String> embedContentTitres = new LinkedList<>();
        List<String> embedContentThirdColumn = new LinkedList<>();


        if (isWorldList) {
            listEnigmes.stream().forEach(enigme -> {
                stringListNumeros.add(String.valueOf(enigme.getNumero()));
                stringListTitres.add(enigme.getTitre());
                stringListThirdColumn.add(String.valueOf(enigme.getPrix()));
            });
        } else {
            listEnigmesPlayer.forEach((enigme, hasEnigmeProperties) -> {
                listEnigmes.forEach(enigme1 -> {
                    if (enigme.getId().equals(enigme1.getId())) {
                        stringListNumeros.add(String.valueOf(enigme.getNumero()));
                        stringListTitres.add(enigme.getTitre());
                        if (hasEnigmeProperties.isSolved()) {
                            stringListThirdColumn.add("oui");
                        } else {
                            stringListThirdColumn.add("non");
                        }
                    }
                });
            });
        }

        int k = 10;
        for (int i = 0; i < listEnigmes.size(); i += 10) {
            List<String> tenNumerosList = stringListNumeros.stream()
                    .skip(i)
                    .limit(k)
                    .collect(Collectors.
                            toList());

            List<String> tenTitresList = stringListTitres.stream()
                    .skip(i)
                    .limit(k)
                    .collect(Collectors.
                            toList());

            List<String> tenThirdColumnList = stringListThirdColumn.stream()
                    .skip(i)
                    .limit(k)
                    .collect(Collectors.
                            toList());
            k += 10;
            embedContentNumeros.add(tenNumerosList.stream().reduce((s, s2) -> {
                return "\n" + s + "\n" + s2;
            }).get());

            embedContentTitres.add(tenTitresList.stream().reduce((s, s2) -> {
                return "\n" + s + "\n" + s2;
            }).get());

            embedContentThirdColumn.add(tenThirdColumnList.stream().reduce((s, s2) -> {
                return "\n" + s + "\n" + s2;
            }).get());

        }

        LinkedList<Map<String, List<String>>> maps = new LinkedList<>();

        maps.add(Map.of(enigmeProperties.getWorldListNumeroColumn(), embedContentNumeros));
        maps.add(Map.of(enigmeProperties.getWorldListTitresColumn(), embedContentTitres));
        if (isWorldList) {
            maps.add(Map.of(enigmeProperties.getWorldListPrixColumn(), embedContentThirdColumn));
        } else {
            maps.add(Map.of(enigmeProperties.getPlayerListResolvedColumn(), embedContentThirdColumn));
        }
        return maps;
    }

    @Override
    public types getType() {
        return types.ENIGMELIST;
    }

    protected void manageAddReactionEventToEnigmeListEmojis(Message userMessage,
                                                      Message botMessage,
                                                      WorldEnigmesListCommandEmoji worldListEmoji) {
        if(emojisCommands
                .stream()
                .noneMatch(commandEmoji -> commandEmoji instanceof WorldEnigmesListCommandEmoji))
            emojisCommands.add(worldListEmoji);

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
                                                                .equals(types.ENIGMELIST) ||
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
