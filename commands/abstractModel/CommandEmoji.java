package com.bot.someweire.commands.abstractModel;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;

public interface CommandEmoji {

    enum types{
        SELECT,
        ENIGMELIST,
        PLAYERLIST,
        VOYAGE,
        MENU,
        DETECT
    }

    boolean tryExecute(Message userMessage,
                              Message botMessage ,
                              ReactionAddEvent event);

    CommandEmoji.types getType();
}