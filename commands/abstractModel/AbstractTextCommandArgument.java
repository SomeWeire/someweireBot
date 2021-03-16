package com.bot.someweire.commands.abstractModel;

import com.bot.someweire.configuration.*;
import com.bot.someweire.dao.*;
import com.bot.someweire.helper.CharacterHelper;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public abstract class AbstractTextCommandArgument implements CommandArgument {

  @Autowired
  protected CharacterHelper playerHelper;

  @Autowired
  protected GeneralProperties generalProperties;

  @Autowired
  protected LieuProperties lieuProperties;

  @Autowired
  protected WorldsProperties worldsProperties;

  @Autowired
  protected MenuProperties menuProperties;

  @Autowired
  protected EnigmeProperties enigmeProperties;

  @Autowired
  protected PlayerProperties playerProperties;

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

  public AbstractTextCommandArgument () {
  }

  protected void sendExceptionMessage(Message message, String title){
    message.getAuthor().ifPresent(user -> {
      Mono<Message> portail_lieu = message.getChannel().flatMap(messageChannel -> {
        return messageChannel.createMessage(messageCreateSpec -> {
          sendUpdateMessage(messageCreateSpec, user.getId().asString(),
              title+ " : " + generalProperties.getError() + " " + generalProperties.getRetry());
        });
      });
      portail_lieu.subscribe();
    });
  }

  protected void sendUpdateMessage(MessageCreateSpec messageCreateSpec, String userId, String message){
    messageCreateSpec.setContent("<@" + userId + ">" + " " + message);
  }

  protected void sendEditMessage(MessageEditSpec messageEditSpec, String userId, String message){
    messageEditSpec.setContent("<@" + userId + ">" + " " + message);
  }

}
