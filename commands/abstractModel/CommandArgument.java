package com.bot.someweire.commands.abstractModel;

import discord4j.core.object.entity.Message;

public interface CommandArgument {

  enum types{
    MENU,
    ENIGME
  }

  boolean tryExecute(Message message, String command);

  types getType();
}
