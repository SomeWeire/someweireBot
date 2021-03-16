package com.bot.someweire.commands.abstractModel;

import com.bot.someweire.configuration.*;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public abstract class AbstractCommand implements Command{

  @Autowired
  protected List<CommandArgument> arguments;

  @Autowired
  protected MenuProperties menuProperties;

  @Autowired
  protected LieuProperties lieuProperties;

  @Autowired
  protected GeneralProperties generalProperties;

  @Autowired
  protected WorldsProperties worldsProperties;

  @Autowired
  protected EnigmeProperties enigmeProperties;

  @Autowired
  protected PlayerProperties playerProperties;

  @Autowired
  protected DiscordProperties discordProperties;

  public AbstractCommand () {
  }

  protected void sendWrongArgumentsMessage(Message message, String title, String content){
    message.getAuthor().ifPresent(user -> {
      message.getChannel().flatMap(messageChannel -> {
        return messageChannel.createMessage(messageCreateSpec -> {
          sendUpdateMessage(messageCreateSpec, user.getId().asString(),
              title + " : " + content);
        });
      }).subscribe();
    });
  }

  protected void sendNotPublicMessage(Message message, String title, String content){
    message.getAuthor().ifPresent(user -> {
      message.getChannel().flatMap(messageChannel -> {
        return messageChannel.createMessage(messageCreateSpec -> {
          sendUpdateMessage(messageCreateSpec, user.getId().asString(),
                  title + " : " + content);
        });
      }).subscribe();
    });
  }

  protected void sendUpdateMessage(MessageCreateSpec messageCreateSpec, String userId, String message){
    messageCreateSpec.setContent("<@" + userId + ">" + " " + message);
  }

  protected void sendEditMessage(MessageEditSpec messageEditSpec, String userId, String message){
    messageEditSpec.setContent("<@" + userId + ">" + " " + message);
  }

  protected void checkIfPermission(){
    if(this.getType().equals(authorization.NONPLAYER)){

    } else {

    }
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
