package com.bot.someweire.commands.argument;

import com.bot.someweire.commands.abstractModel.AbstractEmbedCommandArgument;
import com.bot.someweire.commands.abstractModel.CommandEmoji;
import com.bot.someweire.model.*;
import com.bot.someweire.model.Character;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import discord4j.core.spec.MessageEditSpec;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MenuInfoCommandArgument extends AbstractEmbedCommandArgument {

    public MenuInfoCommandArgument() {
        super();
    }

    @Override
    public boolean tryExecute(Message message, String command) {
        return Mono.just(command.equals(lieuProperties.getInfoCommand()))
                .filter(toExecute -> toExecute)
                .flatMap(executed -> {
                    message
                            .getChannel()
                            .flatMap(messageChannel -> {
                                return messageChannel.createMessage(messageCreateSpec -> {
                                    messageCreateSpec.setEmbed(embedCreateSpec -> {
                                        context
                                                .setPlayer(playerDao
                                                        .getByUserId(message
                                                                .getAuthor()
                                                                .get()
                                                                .getId()
                                                                .asString()));
                                        context
                                                .setLieu(playerHelper
                                                        .getPlayerPosition(context.getPlayer()));
                                        context
                                                .setRegion(playerHelper
                                                        .getPlayerRegion(context.getPlayer()));
                                        context
                                                .setWorld(worldDao
                                                        .getByRegionId(context.getRegion().getId()));

                                        List<Lieu> allRoads = new LinkedList<>();
                                        allRoads.addAll(lieuDao.getIncomingRoadsFromId(context.getLieu().getId()));
                                        allRoads.addAll(context.getLieu().getRoads());
                                        context.setRoads(allRoads);

                                        setEmbed(embedCreateSpec,
                                                messageCreateSpec,
                                                null);
                                    });
                                });
                            }).flatMap(botMessage -> {
                        subscriptionManager.addSubscription(
                                message,
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
                                                                                .equals(CommandEmoji.types.VOYAGE) ||
                                                                                commandEmoji
                                                                                        .getType()
                                                                                        .equals(CommandEmoji.types.MENU))
                                                                .collect(Collectors.toList()),
                                                        context.getPlayer(),
                                                        context.getWorld(),
                                                        null,
                                                        context.getRoads(),
                                                        message,
                                                        botMessage,
                                                        reactionAddEvent)));
                        return Mono.just(botMessage);
                    }).flatMap(botMessage -> {
                        for (int i = 0; i < context.getRoads().size(); i++) {
                            botMessage
                                    .addReaction(ReactionEmoji.unicode(emojiHelper
                                            .getRoadChoiceEmojis()
                                            .get(i)))
                                    .subscribe();
                        }
                        botMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getDetectCommandEmoji())).subscribe();
                        if (!context.getLieu().getPortals().isEmpty())
                            botMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getPortailEmoji())).subscribe();
                        botMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getEnigmeListEmoji())).subscribe();
                        botMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getProfileEmoji())).subscribe();
                        botMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getRankingmoji())).subscribe();
                        return Mono.just(botMessage);
                    }).subscribe();

                    return Mono.just(executed);
                })
                .blockOptional()
                .orElse(false);
    }

    @Override
    public types getType() {
        return types.MENU;
    }


    @Override
    protected void setEmbed(EmbedCreateSpec embedCreateSpec,
                            MessageCreateSpec messageCreateSpec,
                            MessageEditSpec messageEditSpec) {
        final Character player = context.getPlayer();
        final Lieu lieu = context.getLieu();
        final Region region = context.getRegion();
        final World world = context.getWorld();
        final List<Lieu> roads = context.getRoads();
        try {
            String messageContent = lieuProperties.getInfoPublicMessage();

            attachImageAndChangeColorToEmbed(embedCreateSpec,
                    messageCreateSpec,
                    worldHelper.getWorldLieuThumbPath(world.getName()),
                    EMBED_IMAGE_INFO,
                    world.getName(),
                    player,
                    EMBED_AUTHOR_INFO_CLASSTYPE);

            embedCreateSpec.setTitle(lieuProperties.getInfoTitle());
            embedCreateSpec.setDescription(lieu.getName());
            embedCreateSpec.addField(lieuProperties.getInfoDescription(),
                    lieu.getDescription(),
                    false);
            embedCreateSpec.addField(lieuProperties.getInfoRegion(),
                    region.getName() +
                            " : " +
                            region.getDescription(),
                    true);

            final InRegionProperties inRegionProperties =
                    lieu
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

            if (!lieu
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
    }
}