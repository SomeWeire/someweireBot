package com.bot.someweire.commands.abstractModel;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;

public abstract class AbstractCommandEmoji extends AbstractCommandEnd implements CommandEmoji {


    protected boolean filter(Message userMessage, Message botMessage, ReactionAddEvent event){
        return event
                .getMessageId()
                .asString()
                .equals(botMessage
                        .getId()
                        .asString()) && event
                .getUserId()
                .asString()
                .equals(userMessage
                        .getAuthor()
                        .get()
                        .getId()
                        .asString());
    }
}
