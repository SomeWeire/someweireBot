package com.bot.someweire.commands.argument;

import com.bot.someweire.commands.abstractModel.AbstractEmbedCommandArgument;
import com.bot.someweire.commands.abstractModel.CommandEmoji;
import com.bot.someweire.model.Character;
import com.bot.someweire.model.*;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class EnigmeSelectCommandArgument extends AbstractEmbedCommandArgument {


    @Override
    public boolean tryExecute(Message message, String command) {
        if (command.matches("^\\d+$") && !message
                .getChannelId()
                .asString()
                .equals(getUserPrivateChannelId(message))) {
            final int enigmeChoice = Integer.parseInt(command);
            final String userId = message
                    .getAuthor()
                    .get()
                    .getId()
                    .asString();
            final Character player = playerDao.getByUserId(userId);
            context.setPlayer(player);
            context.setRegion(playerHelper.getPlayerRegion(player));
            final World world = worldDao.getByRegionId(context
                    .getRegion()
                    .getId());
            context.setWorld(world);
            final Optional<Enigme> enigmeOptional = enigmeDao
                    .getByNumero(enigmeChoice,
                            world.getId());
            message
                    .getChannel()
                    .block()
                    .createMessage(messageCreateSpec -> {
                        try {
                            enigmeOptional.ifPresentOrElse(enigme -> {
                                context.setSelectedEnigme(enigme);
                                Mono.just(playerHelper.playerHasEnigme(
                                        player,
                                        enigme))
                                        .filter(hasEnigme -> !hasEnigme)
                                        .subscribe(hasEnigme -> sendUpdateMessage(messageCreateSpec,
                                                userId,
                                                enigmeProperties.getSelectTitle() +
                                                        " **" +
                                                        enigme.getNumero() +
                                                        "**" +
                                                        " : " +
                                                        enigmeProperties.getSelectConfirmBuy()));

                                Mono.just(playerHelper.playerHasEnigme(
                                        player,
                                        enigme))
                                        .filter(hasEnigme -> hasEnigme)
                                        .subscribe(hasEnigme -> {
                                                final boolean solvedEnigme =
                                                        playerHelper
                                                                .getPlayerEnigmeProperties(
                                                                        player,
                                                                        enigme)
                                                                .isSolved();

                                                final boolean forfeitEnigme =
                                                        playerHelper
                                                                .getPlayerEnigmeProperties(
                                                                        player,
                                                                        enigme)
                                                                .isForfeit();

                                                final boolean poweredEnigme =
                                                        playerHelper
                                                                .getPlayerEnigmeProperties(
                                                                        player,
                                                                        enigme)
                                                                .isPowered();

                                                if (solvedEnigme || poweredEnigme) {
                                                    sendUpdateMessage(messageCreateSpec,
                                                            userId,
                                                            enigmeProperties.getSelectTitle() +
                                                                    " **" +
                                                                    enigme.getNumero() +
                                                                    "**" +
                                                                    " : " +
                                                                    enigmeProperties.getSelectAlreadySolved());

                                                } else if (forfeitEnigme) {
                                                    sendUpdateMessage(messageCreateSpec,
                                                            userId,
                                                            enigmeProperties.getSelectTitle() +
                                                                    " **" +
                                                                    enigme.getNumero() +
                                                                    "**" +
                                                                    " : " +
                                                                    enigmeProperties.getSelectAlreadyForfeit());
                                                } else {
                                                    messageCreateSpec.setEmbed(embedCreateSpec -> setEmbed(embedCreateSpec,
                                                            messageCreateSpec,
                                                            null));
                                                }
                                        });
                            }, () -> {
                                sendUpdateExceptionMessage(messageCreateSpec,
                                        context.getPlayer().getUserId(),
                                        enigmeProperties.getSelectTitle(),
                                        enigmeProperties.getSelectError());
                            });
                        } catch (Exception e) {
                            sendUpdateExceptionMessage(messageCreateSpec,
                                    context.getPlayer().getUserId(),
                                    enigmeProperties.getSelectTitle(),
                                    generalProperties.getError());
                        }
                    })
                    .flatMap(msg -> {
                        if (msg
                                .getContent()
                                .contains(enigmeProperties.getSelectPublicEnigmeMessage())
                                ||
                                msg
                                        .getContent()
                                        .contains(enigmeProperties.getSelectConfirmBuy())) {
                            subscriptionManager.addSubscription(
                                    message,
                                    msg,
                                    msg
                                            .getClient()
                                            .on(ReactionAddEvent.class)
                                            .subscribe(reactionAddEvent -> emojiCommandVisitor
                                                    .manageEmojiCommands(emojisCommands
                                                                    .stream()
                                                                    .filter(commandEmoji -> commandEmoji
                                                                            .getType()
                                                                            .equals(CommandEmoji.types.SELECT))
                                                                    .collect(Collectors.toList()),
                                                            context.getPlayer(),
                                                            context.getWorld(),
                                                            context.getSelectedEnigme(),
                                                            null,
                                                            message,
                                                            msg,
                                                            reactionAddEvent)));
                        }
                        return Mono.just(msg);
                    })
                    .flatMapMany(msg -> {
                        if (msg.getContent().contains(enigmeProperties.getSelectConfirmBuy())) {
                            msg
                                    .addReaction(ReactionEmoji.unicode(emojiHelper.getFragmentEmoji()))
                                    .subscribe();
                            if (player.getClassType().equals(playerProperties.getSoldier())) {
                                msg
                                        .addReaction(ReactionEmoji.unicode(emojiHelper.getPowerEmoji()))
                                        .subscribe();
                            }
                            return Flux.empty();
                        } else if (msg
                                .getContent()
                                .contains(enigmeProperties.getSelectPublicEnigmeMessage())) {
                            msg
                                    .addReaction(ReactionEmoji.unicode(emojiHelper.getIndiceEmoji()))
                                    .subscribe();
                            msg
                                    .addReaction(ReactionEmoji.unicode(emojiHelper.getForfeitEmoji()))
                                    .subscribe();

                            if (!player.getClassType().equals(playerProperties.getSoldier())) {
                                msg
                                        .addReaction(ReactionEmoji.unicode(emojiHelper.getPowerEmoji()))
                                        .subscribe();
                            }

                            if (player.isForfeitPower()) {
                                msg
                                        .addReaction(ReactionEmoji.unicode(emojiHelper.getForfeitPowerEmoji()))
                                        .subscribe();
                            }
                            return Flux.empty();
                        } else return Mono.just(msg);
                    }).subscribe();
            return true;
        }
        return false;
    }

    @Override
    public types getType() {
        return types.ENIGME;
    }


    @Override
    protected void setEmbed(EmbedCreateSpec embedCreateSpec,
                            MessageCreateSpec messageCreateSpec,
                            MessageEditSpec messageEditSpec) {
        final Character player = context.getPlayer();
        final World world = context.getWorld();
        final Enigme selectedEnigme = context.getSelectedEnigme();

        sendUpdateMessage(messageCreateSpec,
                player
                        .getUserId(),
                enigmeProperties.getSelectPublicEnigmeMessage()
                );

        embedCreateSpec.setTitle(
                enigmeProperties.getSelectTitle()
                        + selectedEnigme.getNumero());

        embedCreateSpec.setDescription(world.getName());

        embedCreateSpec.addField(
                enigmeProperties.getSelectEnigmeTitle(),
                selectedEnigme.getTitre(),
                true);

        attachImageAndChangeColorToEmbed(
                embedCreateSpec,
                messageCreateSpec,
                "",
                "",
                world
                        .getName(),
                player,
                EMBED_AUTHOR_INFO_FRAGMENTS);
    }
}