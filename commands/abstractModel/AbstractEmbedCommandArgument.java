package com.bot.someweire.commands.abstractModel;

import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.core.spec.MessageEditSpec;

public abstract class AbstractEmbedCommandArgument extends AbstractCommandEnd implements CommandArgument {


  protected abstract void setEmbed(EmbedCreateSpec embedCreateSpec,
                                   MessageCreateSpec messageCreateSpec,
                                   MessageEditSpec messageEditSpec);

}
