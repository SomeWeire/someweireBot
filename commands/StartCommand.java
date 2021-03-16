package com.bot.someweire.commands;

import com.bot.someweire.commands.abstractModel.AbstractCommand;
import com.bot.someweire.dao.PlayerDao;
import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class StartCommand extends AbstractCommand {

  @Autowired
  PlayerDao playerDao;

  public StartCommand () {
    super();
  }

  @Override
  public boolean request (Message message) {
    String content = message.getContent();
    if (content.startsWith(generalProperties.getPrefix()+
        generalProperties.getStartCommand())){
      String commandArgument = content.replace(generalProperties.getPrefix() +
          generalProperties.getStartCommand(), "").trim();
        message.getAuthor().ifPresent(user -> {
          final String userId = message.
              getAuthor()
              .get()
              .getId()
              .asString();
          message.getChannel().flatMap(messageChannel -> {
            return messageChannel.createMessage(messageCreateSpec -> {
              if(message.getChannelId().asString().equals(getUserPrivateChannelId(message))){
                sendUpdateMessage(messageCreateSpec,
                    userId,
                    generalProperties.getStartPrivateChannel());
              } else if(!commandArgument.isBlank()){
                if(playerDao.isPlayerExists(userId)){
                  sendUpdateMessage(messageCreateSpec,
                      userId,
                      generalProperties.getStartErase());
                } else {
                  sendUpdateMessage(messageCreateSpec,
                      userId,
                      generalProperties.getStartContent());
                }
              } else {
                sendUpdateMessage(messageCreateSpec,
                    userId,
                    generalProperties.getStartNoName());
              }
            });
          }).flatMap(msg -> {
            if(!msg.getContent().contains(generalProperties.getStartNoName()) ||
                !msg.getContent().contains(generalProperties.getStartPrivateChannel())){
              msg.getClient().on(ReactionAddEvent.class).subscribe(reactionAddEvent -> {
                manageReactionAddEvent(reactionAddEvent,
                    msg,
                    commandArgument,
                    userId,
                    msg.getContent().contains(generalProperties.getStartErase()));
              });
            }
//            Mono.just(msg).filter(message1 -> !message1.getContent().contains(generalProperties.getStartNoName()))
//                .filter(message1 -> !message1.getContent().contains(generalProperties.getStartPrivateChannel()))
//                .doOnNext(message1 -> {
            return Mono.just(msg);
          }).flatMapMany(msg -> {
            if(!msg.getContent().contains(generalProperties.getStartNoName()) &&
            !msg.getContent().contains(generalProperties.getStartPrivateChannel())){
              return Flux.just(msg.addReaction(
                  ReactionEmoji.unicode("\uD83D\uDC68\u200D\uD83D\uDE80")).
                      block(),
                  msg.addReaction(
                      ReactionEmoji.unicode("\uD83D\uDC69\u200D\uD83D\uDE80")).
                      block(),
                  msg.addReaction(
                      ReactionEmoji.unicode("\uD83E\uDDD9\u200D♂️")).
                      block(),
                  msg.addReaction(
                      ReactionEmoji.unicode("\uD83E\uDDD9\u200D♀️")).
                      block(),
                  msg.addReaction(
                      ReactionEmoji.unicode("\uD83E\uDDB8\u200D♂️")).
                      block(),
                  msg.addReaction(
                      ReactionEmoji.unicode("\uD83E\uDDB8\u200D♀️")).
                      block());
            } else return Flux.just(msg);
          }).subscribe();
        });
        return true;
    }
    return false;
  }

  @Override
  public authorization getType () {
    return authorization.NONPLAYER;
  }

  private void manageReactionAddEvent(ReactionAddEvent reactionAddEvent,
      Message msg,
      String playerName,
      String userId,
      boolean erase){
    if(reactionAddEvent.
        getMessageId().
        asString().
        equals(msg.
            getId().
            asString()) &&
        reactionAddEvent.
            getUserId().
            asString().
            equals(userId)){
      if(!playerDao.isPlayerExists(userId) || erase){
        try {
          boolean created = false;
          String worldName = "";
          if(reactionAddEvent.
              getEmoji().
              asUnicodeEmoji().
              get().
              getRaw().equals("\uD83D\uDC68\u200D\uD83D\uDE80")) {
            if(erase){
              created = playerDao.resetPlayer(playerName,
                  playerProperties.getSoldier(),
                  playerProperties.getMale(),
                  userId,
                  worldsProperties.getNameSatorion());
            } else {
              created = playerDao.createPlayer(playerName,
                  playerProperties.getSoldier(),
                  playerProperties.getMale(),
                  userId,
                  worldsProperties.getNameSatorion());
            }
            if(created){
              worldName = worldsProperties.getNameSatorion();
            }
          } else if (reactionAddEvent.
              getEmoji().
              asUnicodeEmoji().
              get().
              getRaw().equals("\uD83D\uDC69\u200D\uD83D\uDE80")) {
            if(erase){
              created = playerDao.resetPlayer(playerName,
                  playerProperties.getSoldier(),
                  playerProperties.getFemale(),
                  userId,
                  worldsProperties.getNameSatorion());
            } else {
              created = playerDao.createPlayer(playerName,
                  playerProperties.getSoldier(),
                  playerProperties.getFemale(),
                  userId,
                  worldsProperties.getNameSatorion());
            }
            if(created){
              worldName = worldsProperties.getNameSatorion();
            }
          } else if (reactionAddEvent.
              getEmoji().
              asUnicodeEmoji().
              get().
              getRaw().equals("\uD83E\uDDD9\u200D♂️")) {
            if(erase){
              created = playerDao.resetPlayer(playerName,
                  playerProperties.getKnight(),
                  playerProperties.getMale(),
                  userId,
                  worldsProperties.getNameSzasgard());
            } else {
              created = playerDao.createPlayer(playerName,
                  playerProperties.getKnight(),
                  playerProperties.getMale(),
                  userId,
                  worldsProperties.getNameSzasgard());
            }
            if(created){
              worldName = worldsProperties.getNameSzasgard();
            }
          } else if (reactionAddEvent.
              getEmoji().
              asUnicodeEmoji().
              get().
              getRaw().equals("\uD83E\uDDD9\u200D♀️")) {
            if(erase){
              created = playerDao.resetPlayer(playerName,
                  playerProperties.getKnight(),
                  playerProperties.getFemale(),
                  userId,
                  worldsProperties.getNameSzasgard());
            } else {
              created = playerDao.createPlayer(playerName,
                  playerProperties.getKnight(),
                  playerProperties.getFemale(),
                  userId,
                  worldsProperties.getNameSzasgard());
            }
            if(created){
              worldName = worldsProperties.getNameSzasgard();
            }
          } else if (reactionAddEvent.
              getEmoji().
              asUnicodeEmoji().
              get().
              getRaw().equals("\uD83E\uDDB8\u200D♂️")) {
            created = playerDao.createPlayer(playerName,
                playerProperties.getHero(),
                playerProperties.getMale(),
                userId,
                worldsProperties.getNameFinn());
            if(created){
              worldName = worldsProperties.getNameFinn();
              }
          } else if (reactionAddEvent.
              getEmoji().
              asUnicodeEmoji().
              get().
              getRaw().equals("\uD83E\uDDB8\u200D♀️")) {
            created = playerDao.createPlayer(playerName,
                playerProperties.getHero(),
                playerProperties.getFemale(),
                userId,
                worldsProperties.getNameFinn());
            if(created){
              worldName = worldsProperties.getNameFinn();
            }
          }
          boolean finalCreated = created;
          msg.edit(messageEditSpec -> {
            if(finalCreated){
              sendEditMessage(messageEditSpec,
                  userId,
                  generalProperties.getStartSuccess()+
                      " **"+
                      playerName+
                      "**");
            } else
              sendEditMessage(messageEditSpec,
                  userId,
                  generalProperties.getStartTitle()+
                      " : "+
                      generalProperties.getError()+
                      " "+
                      generalProperties.getRetry());
            msg.removeAllReactions().subscribe();
          }).subscribe();
          updateRole(reactionAddEvent.getMember().get(), worldName);
        }catch (Exception e){
          msg.edit(messageEditSpec -> {
              sendEditMessage(messageEditSpec,
                  userId,
                  generalProperties.getStartTitle()+
                      " : "+
                      generalProperties.getError()+
                      " "+
                      generalProperties.getRetry());
            msg.removeAllReactions().subscribe();
          }).subscribe();
        }
      }
    }
  }

  private void updateRole(Member member, String worldName){
    if(worldsProperties.getNameSatorion().equals(worldName)){
      member.getRoles().subscribe(role -> {
        if(role.getId().asString().equals(discordProperties.getRoleFinn()) ||
            role.getId().asString().equals(discordProperties.getRoleSzasgard()) ||
              role.getId().asString().equals(discordProperties.getRoleVisitor())){
          member.removeRole(role.getId()).subscribe();
        }
      });
      member.addRole(Snowflake.of(discordProperties.getRoleSatorion())).subscribe();
    } else if(worldsProperties.getNameSzasgard().equals(worldName)){
      member.getRoles().subscribe(role -> {
        if(role.getId().asString().equals(discordProperties.getRoleFinn()) ||
            role.getId().asString().equals(discordProperties.getRoleSatorion()) ||
              role.getId().asString().equals(discordProperties.getRoleVisitor())){
          member.removeRole(role.getId()).subscribe();
        }
      });
      member.addRole(Snowflake.of(discordProperties.getRoleSzasgard())).subscribe();
    } else if (worldsProperties.getNameFinn().equals(worldName)){
      member.getRoles().subscribe(role -> {
        if(role.getId().asString().equals(discordProperties.getRoleSzasgard()) ||
            role.getId().asString().equals(discordProperties.getRoleSatorion()) ||
              role.getId().asString().equals(discordProperties.getRoleVisitor())){
          member.removeRole(role.getId()).subscribe();
        }
      });
      member.addRole(Snowflake.of(discordProperties.getRoleFinn())).subscribe();
    }
  }
}
