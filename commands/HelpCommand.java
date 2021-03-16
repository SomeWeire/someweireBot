package com.bot.someweire.commands;

import com.bot.someweire.commands.abstractModel.AbstractCommand;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class HelpCommand extends AbstractCommand {

    @Override
    public boolean request(Message message) {
        String content = message.getContent();
        if (content.equals(
                generalProperties.getPrefix() +
                        generalProperties.getHelp())) {
            message
                    .getChannel()
                    .flatMap(messageChannel -> {
                        return messageChannel.createMessage(messageCreateSpec -> {
                            sendUpdateMessage(
                                    messageCreateSpec,
                                    message
                                            .getAuthor()
                                            .get()
                                            .getId()
                                            .asString(),
                                    generalProperties.getHelpContent());
                });
            }).subscribe();
            return true;
        }
        return false;
    }

    @Override
    public authorization getType() {
        return authorization.PLAYER;
    }
}
