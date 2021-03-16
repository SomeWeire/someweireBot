package com.bot.someweire.commands.argument;

import com.bot.someweire.commands.abstractModel.AbstractEmbedCommandArgument;
import com.bot.someweire.model.*;
import com.bot.someweire.model.Character;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class EnigmeSolveCommandArgument extends AbstractEmbedCommandArgument {

    @Override
    protected void setEmbed(EmbedCreateSpec embedCreateSpec, MessageCreateSpec messageCreateSpec, MessageEditSpec messageEditSpec) {
        final List<Indice> indices =
                playerHelper.getPlayerIndicesByEnigme(
                        context.getPlayer(),
                        context.getSelectedEnigme());

        embedCreateSpec.setTitle(
                enigmeProperties.getSelectTitle()
                        + context.getSelectedEnigme().getNumero());

        embedCreateSpec.setDescription(context.getWorld().getName());

        embedCreateSpec.addField(
                enigmeProperties.getSelectEnigmeTitle(),
                context.getSelectedEnigme().getTitre(),
                true);

        sendUpdateMessage(messageCreateSpec,
                context
                        .getPlayer()
                        .getUserId(),
                buildEnigmeMessage(
                        indices,
                        context
                                .getPlayer()
                                .isForfeitPower()));

        attachImageAndChangeColorToEmbed(
                embedCreateSpec,
                messageCreateSpec,
                worldHelper.getWorldEnigmeThumbPath(
                        context
                                .getWorld()
                                .getName()),
                enigmeProperties.getThumbEnigmePrefix() +
                        context.getSelectedEnigme().getNumero() +
                        enigmeProperties.getThumbEnigmeSuffix(),
                context
                        .getWorld()
                        .getName(),
                context.getPlayer(),
                EMBED_AUTHOR_INFO_FRAGMENTS);
    }

    @Override
    public boolean tryExecute(Message message, String command) {
        if (command.matches("^\\d+$") &&
                message
                        .getChannelId()
                        .asString()
                        .equals(getUserPrivateChannelId(message))) {
            final int enigmeChoice = Integer.parseInt(command);
            final Character player = playerDao.getByUserId(message
                    .getAuthor()
                    .get()
                    .getId()
                    .asString());
            context.setPlayer(player);
            context.setRegion(playerHelper.getPlayerRegion(player));
            context.setWorld(worldDao
                    .getByRegionId(context
                            .getRegion()
                            .getId()));
            final Optional<Enigme> enigmeOptional = enigmeDao
                    .getByNumero(
                            enigmeChoice,
                            context
                                    .getWorld()
                                    .getId());
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
                                                player.getUserId(),
                                                enigmeProperties.getSelectTitle() +
                                                        " **" +
                                                        enigme.getNumero() +
                                                        "**" +
                                                        " : " +
                                                        enigmeProperties.getSelectPublicChannel()));

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
                                                        player.getUserId(),
                                                        enigmeProperties.getSelectTitle() +
                                                                " **" +
                                                                enigme.getNumero() +
                                                                "**" +
                                                                " : " +
                                                                enigmeProperties.getSelectAlreadySolved());

                                            } else if (forfeitEnigme) {
                                                sendUpdateMessage(messageCreateSpec,
                                                        player.getUserId(),
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

                                                subscriptionManager.addSubscription(
                                                        message,
                                                        message,
                                                        message
                                                                .getClient()
                                                                .on(MessageUpdateEvent.class)
                                                                .subscribe(messageUpdateEvent ->
                                                                        manageEnigmeSolveEvent(messageUpdateEvent,
                                                                                message)));
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
                    .subscribe();
            return true;
        }
        return false;
    }

    @Override
    public types getType() {
        return types.ENIGME;
    }

    private void manageEnigmeSolveEvent(MessageUpdateEvent messageUpdateEvent,
                                        Message commandMessage) {
        final Character player = context.getPlayer();
        final Enigme enigme = context.getSelectedEnigme();
        final HasEnigmeProperties hasEnigmeProperties = playerHelper.getPlayerEnigmeProperties(player, enigme);

        Mono.just(
                messageUpdateEvent
                        .getMessage()
                        .block())
                .filter(msg -> msg
                        .getId()
                        .asString()
                        .equals(commandMessage
                                .getId()
                                .asString()))
                .flatMap(msg -> {
                    msg
                            .getChannel()
                            .block()
                            .createMessage(messageCreateSpec -> {
                                try {
                                    boolean solving = enigmeDao.trySolveEnigme(msg
                                                    .getContent(),
                                            enigme.getId(),
                                            player.getUserId());
                                    final int playerTries = hasEnigmeProperties
                                            .getTries() +
                                            1;

                                    if (!solving) {
                                        if (playerTries < Integer.parseInt(enigmeProperties.getTriesMore())) {
                                            sendUpdateMessage(messageCreateSpec,
                                                    player.getUserId(),
                                                    enigmeProperties.getSelectEnigmeWrongSolution());
                                        } else if (playerTries < Integer.parseInt(enigmeProperties.getTriesForfeit())) {
                                            sendUpdateMessage(messageCreateSpec,
                                                    player.getUserId(),
                                                    enigmeProperties.getSelectEnigmeTriesMore());
                                        } else {
                                            sendUpdateMessage(messageCreateSpec,
                                                    player.getUserId(),
                                                    enigmeProperties.getSelectEnigmeTriesForfeit());
                                        }
                                        hasEnigmeProperties.setTries(playerTries);
                                    } else {
                                        if (!playerHelper
                                                .getPlayerEnigmeProperties(
                                                        playerDao.solveEnigme(player.getUserId(),
                                                                enigme.getId()),
                                                        enigme)
                                                .isSolved()) throw new Exception();

                                        Random random = new Random();
                                        final int rng = random.nextInt(100);
                                        final int powersWithBonus = player.getPowers() +
                                                Integer.parseInt(enigmeProperties.getBonusPowers());

                                        if (playerTries <= Integer.parseInt(enigmeProperties.getBonusTries()) &&
                                                rng >= Integer.parseInt(enigmeProperties.getBonusPercentage())) {

                                            final Character characterUpdated =
                                                    playerDao.updatePlayerPowers(player.getUserId(), powersWithBonus);

                                            if (characterUpdated.getPowers() != powersWithBonus) {
                                                sendUpdateMessage(messageCreateSpec,
                                                        player.getUserId(),
                                                        enigmeProperties.getSelectEnigmeRightSolutionWithBonusError());
                                            } else {
                                                sendUpdateMessage(messageCreateSpec,
                                                        player.getUserId(),
                                                        enigmeProperties.getSelectEnigmeRightSolutionWithBonus() +
                                                                characterUpdated.getPowers());
                                            }
                                        } else {
                                            sendUpdateMessage(
                                                    messageCreateSpec,
                                                    player.getUserId(),
                                                    enigmeProperties.getSelectEnigmeRightSolution());
                                        }
                                        subscriptionManager.cancelSubscriptionOnMessage(msg, msg);
                                    }
                                } catch (Exception e) {
                                    sendUpdateExceptionMessage(
                                            messageCreateSpec,
                                            player.getUserId(),
                                            enigmeProperties.getSelectTitle(),
                                            generalProperties.getError());
                                }
                            }).subscribe();
                    return Mono.just(msg);
                })
                .subscribe();
    }

    private String buildEnigmeMessage(List<Indice> indices, boolean isForfeitPower) {

        StringBuilder builder = new StringBuilder();

        builder.append(enigmeProperties.getSelectEnigmeMessage());
        builder.append("\n");

        if (!indices.isEmpty()) {

            builder.append(enigmeProperties.getIndicePresentation());
            builder.append("\n");

            indices.forEach(indice -> {
                builder.append("**");
                builder.append(indice.getNumero());
                builder.append("**");
                builder.append(" : ");
                builder.append("**");
                builder.append(indice.getTitre());
                builder.append("**");
                if(context.getPlayer().getIndices().get(indice).isPowered()){
                    builder.append(" - ");
                    builder.append(enigmeProperties.getIndicePowered());
                }
                builder.append("\n");
            });
        }

        if (isForfeitPower) {
            builder.append(enigmeProperties.getForfeitPowerDisplay());
        }

        return builder.toString();
    }
}
