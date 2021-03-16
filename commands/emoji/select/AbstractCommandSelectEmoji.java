package com.bot.someweire.commands.emoji.select;

import com.bot.someweire.commands.abstractModel.AbstractCommandEmoji;
import com.bot.someweire.model.Character;
import com.bot.someweire.model.Enigme;
import com.bot.someweire.model.World;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;


public abstract class AbstractCommandSelectEmoji extends AbstractCommandEmoji {

    protected Character player;
    protected Enigme enigme;
    protected World world;

    public void loadContext(Character player,
                            Enigme enigme,
                            World world){
        this.player = player;
        this.enigme = enigme;
        this.world = world;
    }


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



    protected void manageReactionsConfirmEmojis(Message msg){
        msg.removeAllReactions()
                .then(msg.addReaction(ReactionEmoji.unicode(emojiHelper.getConfirmEmoji())))
                .then(msg.addReaction(ReactionEmoji.unicode(emojiHelper.getCancelEmoji())))
                .subscribe();
    }

    protected void manageReactionRemoveConfirmEmojis(Message msg,
                                                   boolean forfeitPower,
                                                   boolean isSoldier){
        msg.removeAllReactions()
                .then(msg.addReaction(ReactionEmoji.unicode(emojiHelper.getIndiceEmoji())))
                .then(msg.addReaction(ReactionEmoji.unicode(emojiHelper.getForfeitEmoji())))
                .thenMany(subscriber -> {
                    if (!isSoldier){
                        msg.addReaction(ReactionEmoji.unicode(emojiHelper.getPowerEmoji())).subscribe();
                    }
                    if (forfeitPower){
                        msg.addReaction(ReactionEmoji.unicode(emojiHelper.getForfeitPowerEmoji())).subscribe();
                    }
                })
                .subscribe();
    }

    protected void manageReactionRemoveConfirmEmojisGetEnigme(Message msg){
        msg.removeAllReactions()
                .then(msg.addReaction(ReactionEmoji.unicode(emojiHelper.getFragmentEmoji())))
                .then(msg.addReaction(ReactionEmoji.unicode(emojiHelper.getPowerEmoji())))
                .subscribe();
    }

    protected void manageReactionsExceptionEmojis(Message userMessage, Message botMessage){
        subscriptionManager.cancelSubscriptionOnMessage(userMessage, botMessage);
        botMessage.suppressEmbeds(true)
                .then(botMessage.removeAllReactions())
                .subscribe();
    }

    @Override
    public types getType() {
        return types.SELECT;
    }
}
