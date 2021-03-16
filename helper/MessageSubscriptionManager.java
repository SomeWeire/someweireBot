package com.bot.someweire.helper;

import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageSubscriptionManager {

    private Map<String, Message> messageSubscriptions;

    public void addSubscription(Message userMessage, Message botMessage){
        getMessageSubscriptions().put(userMessage
                .getAuthor()
                .get()
                .getId()
                .asString(), botMessage);
    }

    private boolean containsSubscription(Message userMessage){
        return getMessageSubscriptions().containsKey(userMessage
                .getAuthor()
                .get()
                .getId()
                .asString());
    }

    public Message getLastBotMessageFromUserMessage(Message userMessage){
        if(containsSubscription(userMessage))
            return getMessageSubscriptions().get(userMessage
                    .getAuthor()
                    .get()
                    .getId()
                    .asString());
        else return userMessage;
    }

    public void removeEntry(Message userMessage){
        if(containsSubscription(userMessage))
        getMessageSubscriptions().remove(
                userMessage
                .getAuthor()
                .get()
                .getId()
                .asString());
    }

    public void clearSubscriptions(){
        getMessageSubscriptions().clear();
    }

    private Map<String, Message> getMessageSubscriptions() {
        if(messageSubscriptions == null) {
            messageSubscriptions = new HashMap<String, Message>();
        }
        return messageSubscriptions;
    }
}
