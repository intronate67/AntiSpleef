package com.mcdrum.dev;

import org.bukkit.conversations.ConversationFactory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @Author Hunter Sharpe
 */
public class AntiSpleef extends JavaPlugin{

    private ConversationFactory conversationFactory;

    public void onEnable(){
        getCommand("as").setExecutor(new CommandHandler());
    }
    public void onDisable(){

    }
    public AntiSpleef(){
        CommandHandler.getInstance().conversationFactory = new ConversationFactory(this)
                .withModality(true)
                .withFirstPrompt(new CommandHandler.SpawnPrompt())
                .withEscapeSequence("/exit")
                .withTimeout(10)
                .thatExcludesNonPlayersWithMessage("You cannot run these commands!")
                .addConversationAbandonedListener(new CommandHandler());
    }

}
