package com.bot.someweire.commands.emoji.menu;

import com.bot.someweire.commands.abstractModel.AbstractCommandEmoji;
import com.bot.someweire.model.Character;
import com.bot.someweire.model.World;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import java.util.stream.Collectors;

public abstract class AbstractMenuCommandEmoji extends AbstractCommandEmoji {

    protected Character player;
    protected World world;

    public void loadContext(Character player,
                            World world) {
        this.player = player;
        this.world = world;
    }

    @Override
    public types getType() {
        return types.MENU;
    }

    protected void manageRemoveEmojis(Message botMessage){
        botMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getBackEmoji()))
                .subscribe();
    }

    protected void manageAddReactionEventToMenuEmojis(Message userMessage,
                                                      Message botMessage) {
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

    protected void setProfileEmbed(EmbedCreateSpec embedCreateSpec,
                       MessageCreateSpec messageCreateSpec){

        attachImageAndChangeColorToEmbed(embedCreateSpec,
                messageCreateSpec,
                worldsProperties.getThumbPath(),
                EMBED_IMAGE_PROFILE,
                world.getName(),
                player,
                EMBED_AUTHOR_INFO_NAME);

        embedCreateSpec.setTitle(menuProperties.getProfil());

        embedCreateSpec.addField(playerProperties.getProfilGender(),
                player.getGender(), true);

        embedCreateSpec.addField(playerProperties.getProfilClasstype(),
                player.getClassType(), true);

        embedCreateSpec.addField(playerProperties.getProfilDescription(),
                playerHelper.getCharacterDescription(player), false);

        embedCreateSpec.addField(playerProperties.getProfilPower(),
                playerHelper.getCharacterPowerDescription(player), false);

        embedCreateSpec.addField(playerProperties.getProfilPowersLeft(),
                String.valueOf(player.getPowers()), true);

        embedCreateSpec.addField(playerProperties.getProfilFragments(),
                playerHelper.getCharacterFragmentsDescription(player), true);
    }
}
