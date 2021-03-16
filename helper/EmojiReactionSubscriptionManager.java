package com.bot.someweire.helper;

import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmojiReactionSubscriptionManager {

    @Autowired
    MessageSubscriptionManager messageSubscriptionManager;

    private Map<String, Disposable> reactionEmojiSubscriptions;

    public void addSubscription(Message userMessage, Message botMessage, Disposable subscription){
        messageSubscriptionManager.addSubscription(userMessage, botMessage);
        getReactionEmojiSubscriptions().put(botMessage.getId().asString(), subscription);
    }

    public void cancelSubscriptionOnMessage(Message userMessage, Message botMessage){
        messageSubscriptionManager.removeEntry(userMessage);
        getReactionEmojiSubscriptions().get(botMessage.getId().asString()).dispose();
        getReactionEmojiSubscriptions().remove(botMessage.getId().asString());
    }

    public void clearSubscriptions(){
        getReactionEmojiSubscriptions().clear();
    }

    private Map<String, Disposable> getReactionEmojiSubscriptions() {
        if(reactionEmojiSubscriptions == null) {
            reactionEmojiSubscriptions = new HashMap<String, Disposable>();
        }
        return reactionEmojiSubscriptions;
    }
}
