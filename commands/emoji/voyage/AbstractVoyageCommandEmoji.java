package com.bot.someweire.commands.emoji.voyage;

import com.bot.someweire.commands.abstractModel.AbstractCommandEmoji;
import com.bot.someweire.commands.abstractModel.CommandEmoji;
import com.bot.someweire.model.Character;
import com.bot.someweire.model.InRegionProperties;
import com.bot.someweire.model.Lieu;
import com.bot.someweire.model.World;
import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.MessageCreateSpec;
import org.springframework.core.io.ClassPathResource;
import reactor.core.publisher.Mono;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractVoyageCommandEmoji extends AbstractCommandEmoji {

    Character player;
    World world;
    List<Lieu> roads;

    public void loadContext(Character player,
                            World world,
                            List<Lieu> roads) {
        this.player = player;
        this.world = world;
        this.roads = roads;
    }

    protected void updateRole(Member member, String newWorldName) {
        if (worldsProperties.getNameSatorion().equals(newWorldName)) {
            member.getRoles().subscribe(role -> {
                if (role.getId().asString().equals(discordProperties.getRoleFinn()) ||
                        role.getId().asString().equals(discordProperties.getRoleSzasgard())) {
                    member.removeRole(role.getId()).subscribe();
                }
            });
            member.addRole(Snowflake.of(discordProperties.getRoleSatorion())).subscribe();
        } else if (worldsProperties.getNameSzasgard().equals(newWorldName)) {
            member.getRoles().subscribe(role -> {
                if (role.getId().asString().equals(discordProperties.getRoleFinn()) ||
                        role.getId().asString().equals(discordProperties.getRoleSatorion())) {
                    member.removeRole(role.getId()).subscribe();
                }
            });
            member.addRole(Snowflake.of(discordProperties.getRoleSzasgard())).subscribe();
        } else if (worldsProperties.getNameFinn().equals(newWorldName)) {
            member.getRoles().subscribe(role -> {
                if (role.getId().asString().equals(discordProperties.getRoleSzasgard()) ||
                        role.getId().asString().equals(discordProperties.getRoleSatorion())) {
                    member.removeRole(role.getId()).subscribe();
                }
            });
            member.addRole(Snowflake.of(discordProperties.getRoleFinn())).subscribe();
        }
    }

    protected Lieu updateLocation(Character player,
                                  int choice,
                                  List<Lieu> roads) {
        Lieu newLieu = null;

        try {
            Character characterUpdated = playerDao
                    .updatePlayerLocation(player.getId(),
                            playerHelper.getPlayerPosition(player).getId(),
                            roads.get(choice - 1).getId());
            newLieu = playerHelper.getPlayerPosition(characterUpdated);
        } catch (Exception e) {
            return playerHelper.getPlayerPosition(player);
        }

        if (newLieu != null) {
            return newLieu;
        }
        return playerHelper.getPlayerPosition(player);
    }

    protected Lieu updateLocationFromPortal(Character player) {

        Lieu newLieu = null;

        try {
            Character characterUpdated = playerDao
                    .updatePlayerLocation(player.getId(),
                            playerHelper.getPlayerPosition(player).getId(),
                            playerHelper.updateLieuFromPortal(player).getId());

            if (playerHelper.getPlayerPosition(characterUpdated) == null)
                throw new Exception();

            newLieu = playerHelper.getPlayerPosition(characterUpdated);

        } catch (Exception e) {
            return playerHelper.getPlayerPosition(player);
        }

        return newLieu;
    }

    protected void manageRemoveEmojis(Message userMessage,
                                      Message botMessage,
                                      boolean fromPortal,
                                      boolean botMessageDeleted,
                                      AbstractVoyageCommandEmoji abstractVoyageCommandEmoji) {

        if (emojisCommands
                        .stream()
                        .noneMatch(commandEmoji -> commandEmoji instanceof VoyageCommandEmoji))
            emojisCommands.add(abstractVoyageCommandEmoji);

        if (emojisCommands
                .stream()
                .noneMatch(commandEmoji -> commandEmoji instanceof PortailCommandEmoji))
            emojisCommands.add(abstractVoyageCommandEmoji);

        if(!botMessageDeleted)
        botMessage
                .removeAllReactions()
                .then(botMessage.suppressEmbeds(true))
                .subscribe();

        subscriptionManager
                .cancelSubscriptionOnMessage(userMessage, botMessage);

        Mono.just(fromPortal)
                .filter(aVoid -> !fromPortal)
                .flatMap(aVoid -> {
                    Mono.from(s -> {
                        this.player = playerDao
                                .getByUserId(userMessage
                                        .getAuthor()
                                        .get()
                                        .getId()
                                        .asString());
                        List<Lieu> allRoads = new LinkedList<>();
                        allRoads.addAll(lieuDao.getIncomingRoadsFromId(playerHelper
                                .getPlayerPosition(player)
                                .getId()));
                        allRoads.addAll(playerHelper
                                .getPlayerPosition(player)
                                .getRoads());
                        this.roads = allRoads;
                    }).subscribe();
                    return Mono.just(aVoid);
                })
                .flatMap(aVoid -> {
                    return botMessage
                            .getChannel()
                            .block()
                            .createMessage(messageCreateSpec -> {
                                rebuildMenuEmbed(messageCreateSpec);
                            });
                })
                .subscribe(newBotMessage -> {
                    subscriptionManager.addSubscription(
                            userMessage,
                            newBotMessage,
                            newBotMessage
                                    .getClient()
                                    .on(ReactionAddEvent.class)
                                    .subscribe(reactionAddEvent -> emojiCommandVisitor
                                            .manageEmojiCommands(
                                                    emojisCommands
                                                            .stream()
                                                            .filter(commandEmoji ->
                                                                    commandEmoji
                                                                            .getType()
                                                                            .equals(CommandEmoji.types.VOYAGE) ||
                                                                            commandEmoji
                                                                                    .getType()
                                                                                    .equals(CommandEmoji.types.MENU))
                                                            .collect(Collectors.toList()),
                                                    player,
                                                    world,
                                                    null,
                                                    roads,
                                                    userMessage,
                                                    newBotMessage,
                                                    reactionAddEvent)));
                    for (int i = 0; i < roads.size(); i++) {
                        newBotMessage
                                .addReaction(ReactionEmoji.unicode(emojiHelper
                                        .getRoadChoiceEmojis()
                                        .get(i)))
                                .subscribe();
                    }
                    newBotMessage
                            .addReaction(ReactionEmoji.unicode(emojiHelper.getDetectCommandEmoji()))
                            .then(Mono
                                    .just(!playerHelper
                                            .getPlayerPosition(player)
                                            .getPortals()
                                            .isEmpty())
                                    .doOnNext(hasPortal -> {
                                        if (hasPortal)
                                            newBotMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getPortailEmoji())).subscribe();
                                    }))
                            .then(newBotMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getEnigmeListEmoji())))
                            .then(newBotMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getProfileEmoji())))
                            .then(newBotMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getRankingmoji())))
                            .subscribe();
                });
    }

    protected void rebuildMenuEmbed(MessageCreateSpec messageCreateSpec) {
        messageCreateSpec.setEmbed(embedCreateSpec -> {
            try {
                String messageContent = lieuProperties.getInfoPublicMessage();

                attachImageAndChangeColorToEmbed(embedCreateSpec,
                        messageCreateSpec,
                        worldHelper.getWorldLieuThumbPath(world.getName()),
                        "info",
                        world.getName(),
                        player,
                        "classType");

                final String thumb = playerHelper.getThumbCharacter(player);

                ClassPathResource classPathResource = new ClassPathResource(playerProperties.getThumbPath() + thumb);
                try {
                    messageCreateSpec.addFile(thumb, new BufferedInputStream(classPathResource.getInputStream()));
                    String authorInfo = player.getName() + "\n" + player.getClassType();
                    embedCreateSpec.setAuthor(authorInfo, "", "attachment://" + thumb);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                embedCreateSpec.setTitle(lieuProperties.getInfoTitle());
                embedCreateSpec.setDescription(playerHelper
                        .getPlayerPosition(player)
                        .getName());
                embedCreateSpec.addField(lieuProperties.getInfoDescription(),
                        playerHelper.getPlayerPosition(player).getDescription(),
                        false);
                embedCreateSpec.addField(lieuProperties.getInfoRegion(),
                        playerHelper
                                .getPlayerRegion(player)
                                .getName() +
                                " : " +
                                playerHelper
                                        .getPlayerRegion(player)
                                        .getDescription(),
                        true);

                final InRegionProperties inRegionProperties = playerHelper
                        .getPlayerPosition(player)
                        .getRegions()
                        .values()
                        .stream()
                        .findFirst()
                        .get();

                embedCreateSpec.addField(lieuProperties.getInfoRoutesDistance(),
                        inRegionProperties.getDistance() +
                                lieuProperties.getInfoRoutesUnite() +
                                " " +
                                inRegionProperties.getLocation(),
                        true);


                roads.forEach(lieu1 -> {
                    final InRegionProperties inRegionPropertiesRoad = lieu1.
                            getRegions().
                            values().
                            stream().
                            findFirst().
                            get();

                    embedCreateSpec.addField(lieuProperties.getInfoRoutes() +
                                    " " +
                                    ((roads.indexOf(lieu1) + 1)),
                            lieu1.getName() +
                                    " : " +
                                    inRegionPropertiesRoad.getDistance() +
                                    lieuProperties.getInfoRoutesUnite() +
                                    " " +
                                    inRegionPropertiesRoad.getLocation() +
                                    " (" +
                                    lieuProperties.getInfoRoutesDistance() +
                                    ")",
                            false);
                });

                embedCreateSpec.addField(lieuProperties.getInfoMonde(),
                        world.getName() +
                                " : " +
                                world.getDescription(),
                        false);

                if (!playerHelper
                        .getPlayerPosition(player)
                        .getPortals()
                        .isEmpty()) {
                    messageContent += "\n\n" +
                            "<@" +
                            player.getUserId() +
                            ">" +
                            " " +
                            lieuProperties.getInfoPortail();
                }

                messageCreateSpec.setContent(messageContent);
            } catch (Exception e) {
                setErrorEmbed(player.getUserId(),
                        embedCreateSpec,
                        lieuProperties.getInfoTitle(),
                        lieuProperties.getError());
            }
        });
    }


    @Override
    public types getType() {
        return types.VOYAGE;
    }
}