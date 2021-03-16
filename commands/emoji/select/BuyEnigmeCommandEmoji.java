package com.bot.someweire.commands.emoji.select;

import com.bot.someweire.model.*;
import com.bot.someweire.model.Character;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

@Component
public class BuyEnigmeCommandEmoji extends AbstractCommandSelectEmoji {

    @Override
    public boolean tryExecute(Message userMessage,
                              Message botMessage,
                              ReactionAddEvent event) {
        return Mono.just(filter(userMessage, botMessage, event))
                .filter(toExecute -> toExecute)
                .flatMap(toExecute -> Mono.just(toExecute)
                        .filter(executed -> event.getEmoji().
                                asUnicodeEmoji().
                                get().
                                getRaw().equals(emojiHelper.getFragmentEmoji()))
                        .filter(executed ->
                            !playerHelper.playerHasEnigme(player, enigme)
                        ).flatMap(executed -> {
                            final String userId = player.getUserId();
                            final int prix = enigme.getPrix();
                            final Set<Fragment> fragmentSet = player.getFragments().keySet();

                            try {
                                botMessage.edit(messageEditSpec -> {

                                    final String fragmentType = worldHelper.getWorldCurrency(world.getName());

                                    final int fragmentsLeft = playerHelper.getPlayerFragmentsLeftByWorld(player, fragmentType);

                                    if (prix > fragmentsLeft) {
                                        sendEditMessage(messageEditSpec,
                                                userId,
                                                enigmeProperties.getSelectTitle() +
                                                        " **" +
                                                        enigme.getNumero() +
                                                        "**" +
                                                        " : " +
                                                        enigmeProperties.getSelectNotEnoughFunds());
                                    } else {
                                        Map<Fragment, HasFragmentProperties> fragmentSpendingMap = playerHelper
                                                .spendFragment(player, fragmentType, fragmentsLeft);
                                        boolean error = false;

                                        for (Fragment fragment:fragmentSpendingMap.keySet()){
                                                final Long fragmentId = fragment.getId();

                                                int amountToUpdate = fragmentSpendingMap.get(fragment).getLeft();
                                                int left = this.playerHelper.getPlayerFragmentProperties(playerDao
                                                                .setValueLeftOnFragmentForPlayer(userId, fragmentId, amountToUpdate),
                                                        fragmentId).getLeft();

                                                error |= left != amountToUpdate;

                                                if(error) {
                                                    break;
                                                }
                                        }

                                        if(!error){

                                            Character characterUpdated = playerDao.ownEnigme(userId, enigme.getId());
                                            error |= !playerHelper.playerHasEnigme(characterUpdated, enigme);

                                            if(error){

                                                error = false;
                                                for (Fragment fragment:fragmentSpendingMap.keySet()){
                                                    final Long fragmentId = fragment.getId();

                                                    int amountToRestore = player.getFragments().get(fragment).getLeft();
                                                    int left = this.playerHelper.getPlayerFragmentProperties(playerDao
                                                                    .setValueLeftOnFragmentForPlayer(userId, fragmentId, amountToRestore),
                                                            fragmentId).getLeft();

                                                    error |= left != amountToRestore;

                                                    if(error) {
                                                        break;
                                                    }
                                                }
                                                sendEditMessage(messageEditSpec, userId,
                                                        generalProperties.getError() + " " + generalProperties.getRetry());
                                            }

                                            userMessage
                                                    .getAuthor()
                                                    .get()
                                                    .getPrivateChannel()
                                                    .block()
                                                    .createMessage(messageCreateSpec -> {
                                                        sendUpdateMessage(messageCreateSpec,
                                                                player.getUserId(),
                                                                enigmeProperties.getSelectTitle() +
                                                                        context.getSelectedEnigme().getNumero() +
                                                                        " : " +
                                                                        enigmeProperties.getSelectEnigmePrivateMessage());
                                                    }).subscribe();

                                            sendEditMessage(messageEditSpec,
                                                    userId,
                                                    enigmeProperties.getSelectTitle() +
                                                            enigme.getNumero() +
                                                            " : " +
                                                            enigmeProperties.getSelectConfirmedBuy());
                                        } else {
                                            sendEditMessage(messageEditSpec, userId,
                                                    generalProperties.getError() + " " + generalProperties.getRetry());
                                        }

                                    }

                                }).subscribe();
                            } catch (Exception e){
                                botMessage.edit(messageEditSpec -> sendEditMessage(messageEditSpec, userId,
                                        generalProperties.getError() + " " + generalProperties.getRetry())).subscribe();
                            }
                            botMessage.removeAllReactions().subscribe();
                            return Mono.just(executed);
                })).blockOptional().orElse(false);
    }
}
