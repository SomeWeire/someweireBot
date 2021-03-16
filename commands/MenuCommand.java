package com.bot.someweire.commands;

import com.bot.someweire.commands.abstractModel.AbstractCommand;
import com.bot.someweire.commands.abstractModel.AbstractTextCommandArgument;
import com.bot.someweire.commands.abstractModel.CommandArgument;
import com.bot.someweire.commands.abstractModel.CommandArgument.types;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MenuCommand extends AbstractCommand {

    public MenuCommand() {
        super();
    }

    @Override
    public boolean request(Message message) {
        String content = message.getContent();
        boolean processed = false;
        arguments
                .removeIf(commandArgument -> !commandArgument.getType().equals(types.MENU));
        if (content
                .startsWith(
                        generalProperties.getPrefix() +
                                menuProperties.getCommand())) {
            if (message
                    .getChannelId()
                    .asString()
                    .equals(getUserPrivateChannelId(message))) {
                sendNotPublicMessage(message, menuProperties.getTitle(), menuProperties.getNotPublic());
            } else {
                String commandArgument = content
                        .replace(
                                generalProperties.getPrefix() +
                                        menuProperties.getCommand(),
                                "")
                        .trim();

                for (CommandArgument next : arguments) {
                    boolean executed = next.tryExecute(message, commandArgument);
                    if (executed) {
                        processed = true;
                        break;
                    }
                }
                if (!processed) {
                    sendWrongArgumentsMessage(message, menuProperties.getTitle(), menuProperties.getErrorArguments());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public authorization getType() {
        return authorization.PLAYER;
    }
}
