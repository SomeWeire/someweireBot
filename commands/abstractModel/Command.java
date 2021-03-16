package com.bot.someweire.commands.abstractModel;
import discord4j.core.object.entity.Message;

public interface Command {

  enum authorization{
    PLAYER,
    NONPLAYER
  }

  boolean request(Message message);

  authorization getType();
}
