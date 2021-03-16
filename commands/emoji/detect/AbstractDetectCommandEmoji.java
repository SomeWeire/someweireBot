package com.bot.someweire.commands.emoji.detect;

import com.bot.someweire.commands.abstractModel.AbstractCommandEmoji;
import com.bot.someweire.model.*;
import com.bot.someweire.model.Character;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;

import java.util.stream.Collectors;

public abstract class AbstractDetectCommandEmoji extends AbstractCommandEmoji {
    protected Character player;
    protected World world;

    public void loadContext(Character player,
                            World world) {
        this.player = player;
        this.world = world;
    }

    @Override
    public types getType() {
        return types.DETECT;
    }

    protected void setEmbed(EmbedCreateSpec embedCreateSpec,
                            MessageCreateSpec messageCreateSpec) {
        final Fragment fragmentFromPositionCache =
                playerHelper.getFragmentFromPlayerPositionCache(player);

        final Enigme enigmeFromPlayerPositionCache =
                playerHelper.getEnigmeFromPlayerPositionCache(player);

        try {
            int playerQuantite = 0;
            String messageContent = "";

            if (!playerHelper
                    .getPlayerPositionCache(player)
                    .getEnigme()
                    .isEmpty()) {
                if (playerHelper
                        .getPlayerEnigmeProperties(
                                player,
                                enigmeFromPlayerPositionCache)
                        .isForfeit()) {
                    playerQuantite = 1;
                    messageContent = enigmeProperties.getDetectForfeit();
                } else if (playerHelper
                        .getPlayerEnigmeProperties(
                                player,
                                enigmeFromPlayerPositionCache)
                        .isPowered()) {
                    playerQuantite = fragmentFromPositionCache
                            .getQuantite();
                } else {
                    int indiceCost = 0;
                    int indicePowered = 0;

                    for (Indice indice : playerHelper.getIndicesByPlayerEnigme(
                            player,
                            enigmeFromPlayerPositionCache)) {
                        if (!playerHelper
                                .getPlayerIndiceProperties(
                                        player,
                                        indice.getId()).isPowered()) {
                            indiceCost += fragmentFromPositionCache
                                    .getQuantite() /
                                    Integer.parseInt(enigmeProperties.getIndicesCost());
                        } else {
                            ++indicePowered;
                        }
                    }

                    playerQuantite = fragmentFromPositionCache
                            .getQuantite() -
                            indiceCost;

                    messageContent =
                            enigmeProperties.getIndiceDetectNumber() +
                                    playerHelper
                                            .getIndicesByPlayerEnigme(
                                                    player,
                                                    enigmeFromPlayerPositionCache)
                                            .size() +
                                    "\n" +
                                    enigmeProperties.getIndiceDetectPowered() +
                                    indicePowered +
                                    "\n" +
                                    enigmeProperties.getIndiceDetectCost() +
                                    indiceCost;
                }
            } else {
                playerQuantite = fragmentFromPositionCache
                        .getQuantite();
            }

            int powersToUpdate = player.getPowers();

            if (powersToUpdate +
                    Integer.parseInt(enigmeProperties.getWinPower()) <=        //TO TEST
                    Integer.parseInt(enigmeProperties.getPowerMaxLevel1()) || player.getName().equals("Sumanguru")) {
                powersToUpdate += Integer.parseInt(enigmeProperties.getWinPower());
            }

            Character characterUpdated = playerDao
                    .updatePlayerFragments(player.getUserId(),
                            fragmentFromPositionCache
                                    .getId(),
                            playerQuantite,
                            powersToUpdate);

            if (!playerHelper.playerHasFragment(
                    characterUpdated,
                    fragmentFromPositionCache))
                throw new Exception();
            if (playerQuantite != playerHelper
                    .getPlayerFragmentProperties(
                            characterUpdated,
                            fragmentFromPositionCache
                                    .getId())
                    .getLeft())
                throw new Exception();

            if (characterUpdated.getPowers() > player.getPowers())
                messageContent += "<@" +
                        player.getUserId() +
                        ">" +
                        " " +
                        enigmeProperties.getPowersDetectUpdated() +
                        "**" +
                        characterUpdated.getPowers()
                        + "**";

            messageCreateSpec.setContent(messageContent);

            attachImageAndChangeColorToEmbed(embedCreateSpec,
                    messageCreateSpec,
                    worldsProperties.getThumbPath(),
                    EMBED_IMAGE_FRAGMENT,
                    world.getName(),
                    player,
                    EMBED_AUTHOR_INFO_CLASSTYPE);

            embedCreateSpec.setTitle(enigmeProperties.getDetectTitle());
            embedCreateSpec.setDescription(enigmeProperties.getDetectWinFragments());
            embedCreateSpec.addField(
                    enigmeProperties.getDetectWinFragmentsDescriptionType(),
                    fragmentFromPositionCache
                            .getType(),
                    true);
            embedCreateSpec.addField(
                    enigmeProperties.getDetectWinFragmentsDescriptionQuantite(),
                    Integer.toString(playerQuantite) +
                            "/" +
                            fragmentFromPositionCache
                                    .getQuantite(),
                    true);
        } catch (Exception e) {
            setErrorEmbed(
                    player.getUserId(),
                    embedCreateSpec,
                    enigmeProperties.getDetectTitle(),
                    generalProperties.getError());
        }
    }

    protected void manageRemoveEmojis(Message botMessage) {
        botMessage
                .removeAllReactions()
                .then(botMessage.addReaction(ReactionEmoji.unicode(emojiHelper.getBackEmoji())))
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
}
