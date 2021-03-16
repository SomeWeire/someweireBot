package com.bot.someweire.commands.abstractModel;

import com.bot.someweire.configuration.*;
import com.bot.someweire.dao.*;
import com.bot.someweire.helper.*;
import com.bot.someweire.model.Character;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import discord4j.rest.util.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import reactor.core.publisher.Mono;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

public abstract class AbstractCommandEnd {

    @Autowired
    protected EmojiReactionSubscriptionManager subscriptionManager;

    @Autowired
    protected MessageSubscriptionManager messageSubscriptionManager;

    @Autowired
    protected EmojiHelper emojiHelper;

    @Autowired
    protected List<CommandEmoji> emojisCommands;

    @Autowired
    protected EmojiCommandVisitor emojiCommandVisitor;

    @Autowired
    protected CharacterHelper playerHelper;

    @Autowired
    protected WorldHelper worldHelper;

    @Autowired
    protected Context context;

    @Autowired
    protected WorldsProperties worldsProperties;

    @Autowired
    protected GeneralProperties generalProperties;

    @Autowired
    protected LieuProperties lieuProperties;

    @Autowired
    protected EnigmeProperties enigmeProperties;

    @Autowired
    protected PlayerProperties playerProperties;

    @Autowired
    protected MenuProperties menuProperties;

    @Autowired
    protected DiscordProperties discordProperties;

    @Autowired
    protected PlayerDao playerDao;

    @Autowired
    protected EnigmeDao enigmeDao;

    @Autowired
    protected WorldDao worldDao;

    @Autowired
    protected IndiceDao indiceDao;

    @Autowired
    protected FragmentDao fragmentDao;

    @Autowired
    protected LieuDao lieuDao;

    protected static final String EMBED_AUTHOR_INFO_CLASSTYPE = "classType";
    protected static final String EMBED_AUTHOR_INFO_NAME = "name";
    protected static final String EMBED_AUTHOR_INFO_FRAGMENTS = "fragments";
    protected static final String EMBED_IMAGE_INFO = "info";
    protected static final String EMBED_IMAGE_FRAGMENT = "detect";
    protected static final String EMBED_IMAGE_PROFILE = "profile";


    protected void setErrorEmbed(String userId, EmbedCreateSpec embedCreateSpec, String title, String errorDescriptionTitle) {
        embedCreateSpec.setAuthor("@" + userId, null, null);
        embedCreateSpec.setTitle(title);
        embedCreateSpec.setDescription(errorDescriptionTitle);
        embedCreateSpec.addField(lieuProperties.getInfoDescription(), generalProperties.getRetry(), false);
        embedCreateSpec.setColor(Color.of(java.awt.Color.decode(worldsProperties.getColorDefault()).getRGB()));
    }

    protected void attachImageAndChangeColorToEmbed(EmbedCreateSpec embedCreateSpec,
                                                    MessageCreateSpec messageCreateSpec,
                                                    String path,
                                                    String imageName,
                                                    String worldName,
                                                    Character player,
                                                    String playerTitle) {

        final String color = worldHelper.getWorldColor(worldName);
        final String fragment = worldHelper.getWorldCurrencyThumb(worldName);
//        final String lieu = worldHelper.getWorldLieuThumb(worldName);
        final String lieu = playerHelper.getPlayerPosition(player).getThumb() + worldsProperties.getThumbPathExtension();
        final String thumbCharacter = playerHelper.getThumbCharacter(player);
        String authorInfo = "";

        if (imageName.equals(EMBED_IMAGE_INFO)) imageName = lieu;
        if (imageName.equals(EMBED_IMAGE_FRAGMENT)) imageName = fragment;
        if (imageName.equals(EMBED_IMAGE_PROFILE)) imageName = thumbCharacter;
        if (playerTitle.equals(EMBED_AUTHOR_INFO_NAME)) authorInfo = player.getName();
        if (playerTitle.equals(EMBED_AUTHOR_INFO_CLASSTYPE)) authorInfo = player.getName()
                + "\n"
                + player.getClassType();
        if (playerTitle.equals(EMBED_AUTHOR_INFO_FRAGMENTS)) authorInfo = player.getName() +
                "\n" +
                player.getClassType() +
                "\n" +
                emojiHelper.getFragmentEmoji() +
                playerHelper.getPlayerFragmentsLeftByWorld(player, worldHelper.getWorldCurrency(worldName)) +
                " " +
                worldHelper.getWorldCurrency(worldName);

        try {
            if (messageCreateSpec != null) {

                if (imageName != "") {
                    messageCreateSpec.addFile(
                            imageName,
                            new BufferedInputStream(
                                    new ClassPathResource(path + imageName).getInputStream()));

                    embedCreateSpec.setImage("attachment://" + imageName);
                }


                messageCreateSpec.addFile(
                        thumbCharacter,
                        new BufferedInputStream(
                                new ClassPathResource(playerProperties.getThumbPath() + thumbCharacter).getInputStream()));
            }

            if (!imageName.equals(thumbCharacter)) {
                embedCreateSpec.setAuthor(authorInfo,
                        "",
                        "attachment://" +
                                thumbCharacter);
            } else embedCreateSpec.setAuthor(authorInfo,
                    "",
                    "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        embedCreateSpec.setColor(Color.of(java.awt.Color.decode(color).getRGB()));

    }

    protected void sendExceptionMessage(Message message, String title) {
        message.getAuthor().ifPresent(user -> {
            Mono<Message> portail_lieu = message.getChannel().flatMap(messageChannel -> {
                return messageChannel.createMessage(messageCreateSpec -> {
                    sendUpdateMessage(messageCreateSpec, user.getId().asString(),
                            title + " : " + generalProperties.getError() + " " + generalProperties.getRetry());
                });
            });
            portail_lieu.subscribe();
        });
    }

    protected void sendUpdateMessage(MessageCreateSpec messageCreateSpec, String userId, String message) {
        messageCreateSpec.setContent("<@" + userId + ">" + " " + message);
    }

    protected void sendUpdateExceptionMessage(MessageCreateSpec messageCreateSpec, String userId, String title, String error) {
        sendUpdateMessage(messageCreateSpec, userId,
                title + " : " + error + " " + generalProperties.getRetry());
    }

    protected void sendEditMessage(MessageEditSpec messageEditSpec, String userId, String message) {
        messageEditSpec.setContent("<@" + userId + ">" + " " + message);
    }

    protected String getUserPrivateChannelId(Message message){
        return message
                .getAuthor()
                .get()
                .getPrivateChannel()
                .block()
                .getId()
                .asString();
    }
}
