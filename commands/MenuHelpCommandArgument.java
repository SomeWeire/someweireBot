package com.bot.someweire.commands;

import com.bot.someweire.commands.abstractModel.AbstractTextCommandArgument;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MenuHelpCommandArgument extends AbstractTextCommandArgument {

    @Override
    public boolean tryExecute(Message message, String command) {
        if (command.equals(menuProperties.getHelp())) {
            message.getChannel().flatMap(messageChannel -> {
                return messageChannel.createMessage(messageCreateSpec -> {
                    sendUpdateMessage(
                            messageCreateSpec,
                            message
                                    .getAuthor()
                                    .get()
                                    .getId().
                                    asString(),
                            menuProperties.getHelpContent());
                });
            }).subscribe();
            return true;
        }
        return false;
    }

    @Override
    public types getType() {
        return types.MENU;
    }
}