package com.bot.someweire.commands;

import com.bot.someweire.commands.abstractModel.AbstractCommand;
import com.bot.someweire.commands.abstractModel.AbstractTextCommandArgument;
import com.bot.someweire.commands.abstractModel.CommandArgument;
import com.bot.someweire.commands.abstractModel.CommandArgument.types;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class EnigmeCommand extends AbstractCommand {

    public EnigmeCommand() {
        super();
    }

    @Override
    public boolean request(Message message) {
        String content = message.getContent();
        boolean processed = false;
        arguments
                .removeIf(commandArgument -> !commandArgument.getType().equals(types.ENIGME));
        if (content
                .startsWith(
                        generalProperties.getPrefix() +
                                enigmeProperties.getCommand())) {
            String commandArgument = content
                    .replace(
                            generalProperties.getPrefix() +
                                    enigmeProperties.getCommand(),
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
                sendWrongArgumentsMessage(message, enigmeProperties.getTitle(), enigmeProperties.getErrorArguments());
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
