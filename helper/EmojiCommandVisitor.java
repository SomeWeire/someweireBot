package com.bot.someweire.helper;

import com.bot.someweire.commands.abstractModel.CommandEmoji;
import com.bot.someweire.commands.emoji.detect.AbstractDetectCommandEmoji;
import com.bot.someweire.commands.emoji.list.AbstractEnigmesListCommandEmoji;
import com.bot.someweire.commands.emoji.list.AbstractListCommandEmoji;
import com.bot.someweire.commands.emoji.list.AbstractRankingListCommandEmoji;
import com.bot.someweire.commands.emoji.menu.AbstractMenuCommandEmoji;
import com.bot.someweire.commands.emoji.select.AbstractCommandSelectEmoji;
import com.bot.someweire.commands.emoji.voyage.AbstractVoyageCommandEmoji;
import com.bot.someweire.model.Character;
import com.bot.someweire.model.Enigme;
import com.bot.someweire.model.Lieu;
import com.bot.someweire.model.World;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.AbstractList;
import java.util.List;

@Component
public class EmojiCommandVisitor {


    public void visitSelectEmojiCommand(CommandEmoji commandEmoji,
                                           Character player,
                                           Enigme enigme,
                                           World world){
        Mono.just(commandEmoji)
                .filter(commandEmoji1 -> commandEmoji instanceof AbstractCommandSelectEmoji)
                .subscribe(commandEmoji1 -> ((AbstractCommandSelectEmoji) commandEmoji)
                        .loadContext(player,
                                enigme,
                                world));
    }

    public void visitListEmojiCommand(CommandEmoji commandEmoji,
                                      Character player,
                                      World world){
        Mono.just(commandEmoji)
                .filter(commandEmoji1 -> commandEmoji instanceof AbstractListCommandEmoji)
                .subscribe(commandEmoji1 -> ((AbstractListCommandEmoji) commandEmoji)
                        .loadContext(player,
                                world));
    }

    public void visitVoyageEmojiCommand(CommandEmoji commandEmoji,
                                        Character player,
                                        World world,
                                        List<Lieu> roads){

        Mono.just(commandEmoji)
                .filter(commandEmoji1 -> commandEmoji instanceof AbstractVoyageCommandEmoji)
                .subscribe(commandEmoji1 -> ((AbstractVoyageCommandEmoji) commandEmoji)
                        .loadContext(player,
                                world,
                                roads));

    }

    public void visitDetectEmojiCommand(CommandEmoji commandEmoji,
                                        Character player,
                                        World world){

        Mono.just(commandEmoji)
                .filter(commandEmoji1 -> commandEmoji instanceof AbstractDetectCommandEmoji)
                .subscribe(commandEmoji1 -> ((AbstractDetectCommandEmoji) commandEmoji)
                        .loadContext(player,
                                world));

    }

    public void visitDetectMenuCommand(CommandEmoji commandEmoji,
                                        Character player,
                                        World world){

        Mono.just(commandEmoji)
                .filter(commandEmoji1 -> commandEmoji instanceof AbstractMenuCommandEmoji)
                .subscribe(commandEmoji1 -> ((AbstractMenuCommandEmoji) commandEmoji)
                        .loadContext(player,
                                world));

    }

    public void manageEmojiCommands(List<CommandEmoji> commandEmojis,
                                  Character player,
                                  World world,
                                  Enigme enigme,
                                  List<Lieu> roads,
                                  Message userMessage,
                                  Message botMessage,
                                  ReactionAddEvent reactionAddEvent){
        for (CommandEmoji next : commandEmojis) {
            visitEmojiCommands(next,
                    player,
                    world,
                    enigme,
                    roads);
            if (next.tryExecute(userMessage, botMessage, reactionAddEvent)) {
                break;
            }
        }
    }

    public void visitEmojiCommands(CommandEmoji commandEmoji,
                                      Character player,
                                      World world,
                                      Enigme enigme,
                                      List<Lieu> roads){
        if(commandEmoji instanceof AbstractListCommandEmoji) visitListEmojiCommand(commandEmoji, player, world);

        if(commandEmoji instanceof AbstractCommandSelectEmoji) visitSelectEmojiCommand(commandEmoji, player, enigme, world);

        if(commandEmoji instanceof AbstractVoyageCommandEmoji) visitVoyageEmojiCommand(commandEmoji, player, world, roads);

        if(commandEmoji instanceof AbstractDetectCommandEmoji) visitDetectEmojiCommand(commandEmoji, player, world);

        if(commandEmoji instanceof AbstractMenuCommandEmoji) visitDetectMenuCommand(commandEmoji, player, world);
    }
}
