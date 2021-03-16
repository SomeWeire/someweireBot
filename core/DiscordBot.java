package com.bot.someweire.core;

import com.bot.someweire.commands.abstractModel.AbstractCommand;
import com.bot.someweire.commands.abstractModel.Command;
import com.bot.someweire.commands.abstractModel.Command.authorization;
import com.bot.someweire.commands.abstractModel.CommandArgument.types;
import com.bot.someweire.configuration.DiscordProperties;
import com.bot.someweire.configuration.GeneralProperties;
import com.bot.someweire.configuration.WorldsProperties;
import com.bot.someweire.dao.PlayerDao;
import com.bot.someweire.dao.WorldDao;
import com.bot.someweire.helper.EmojiReactionSubscriptionManager;
import com.bot.someweire.helper.MessageSubscriptionManager;
import com.bot.someweire.model.World;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.guild.GuildEvent;
import discord4j.core.event.domain.guild.MemberJoinEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.voice.AudioProvider;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DiscordBot {

    private GatewayDiscordClient discordClient;

    @Autowired
    List<Command> commands;

    @Autowired
    MessageSubscriptionManager messageSubscriptionManager;

    @Autowired
    EmojiReactionSubscriptionManager subscriptionManager;

    @Autowired
    PlayerDao playerDao;

    @Autowired
    WorldDao worldDao;

    @Autowired
    WorldsProperties worldsProperties;

    @Autowired
    DiscordProperties discordProperties;

    AudioProvider provider;

    AudioPlayerManager playerManager;

    TrackScheduler trackScheduler;

    @Value("${server.id}")
    private String serverId;

    @Value("${server.token}")
    private String serverToken;

    public DiscordBot() {
    }

    public void startBot() {

        discordClient = DiscordClientBuilder.create(serverToken).build()
                .login()
                .block();

        discordClient.getEventDispatcher().on(MessageCreateEvent.class).subscribe(this::commandListener);
        discordClient.getEventDispatcher().on(MemberJoinEvent.class).subscribe(this::joinListener);
    }

    private void commandListener(MessageCreateEvent event) {
        Mono.just(event.getMessage())
                .filter(message -> !message.getAuthor().get().getId().equals(serverId))
                .filter(message -> !message.getChannelId().asString().equals(discordProperties.getChannelGeneral()))
                .filter(message -> !message.getChannelId().asString().equals(discordProperties.getChannelPresentation()))
                .filter(message -> !message.getChannelId().asString().equals(discordProperties.getChannelAide()))
                .flatMap(message -> {
                    Message lastBotMessage = messageSubscriptionManager.getLastBotMessageFromUserMessage(message);
                    if(!lastBotMessage.equals(message))
                    subscriptionManager.cancelSubscriptionOnMessage(message, lastBotMessage);
                    return Mono.just(message);
                })
                .subscribe(message -> {
                    String userId = message
                            .getAuthor()
                            .get()
                            .getId()
                            .asString();
                    boolean playerExists = playerDao.isPlayerExists(userId);
                    if (playerExists) {
                        for (Command next : commands) {
                            boolean executed = next.request(message);
                            if (executed) {
                                break;
                            }
                        }
                    } else {
                        List<Command> commandsNoAuth = commands.stream()
                                .filter(command -> !command.getType().equals(authorization.PLAYER)).collect(
                                        Collectors.toList());
                        for (Command next : commandsNoAuth) {
                            boolean executed = next.request(message);
                            if (executed) {
                                break;
                            }
                        }
                    }
                });
    }

    private void joinListener(MemberJoinEvent memberJoinEvent) {
        final Member member = memberJoinEvent.getMember();
        final String userId = member.getId().asString();
        if (!playerDao.isPlayerExists(userId)) {
            memberJoinEvent
                    .getMember()
                    .addRole(Snowflake.of(discordProperties.getRoleVisitor()))
                    .subscribe();
        } else {
            String worldName = worldDao.getByUser(userId).getName();
            if (worldsProperties.getNameSatorion().equals(worldName)) {
                member.addRole(Snowflake.of(discordProperties.getRoleSatorion())).subscribe();
            } else if (worldsProperties.getNameSzasgard().equals(worldName)) {
                member.addRole(Snowflake.of(discordProperties.getRoleSzasgard())).subscribe();
            } else if (worldsProperties.getNameFinn().equals(worldName)) {
                member.addRole(Snowflake.of(discordProperties.getRoleFinn())).subscribe();
            } else {
                member.addRole(Snowflake.of(discordProperties.getRoleVisitor())).subscribe();
            }
        }
    }

    protected void sendUpdateMessage(MessageCreateSpec messageCreateSpec, String userId, String message) {
        messageCreateSpec.setContent("<@" + userId + ">" + " " + message);
    }
}
