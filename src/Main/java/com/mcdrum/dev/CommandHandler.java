package com.mcdrum.dev;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;

/**
 * @Author Hunter Sharpe
 */
public class CommandHandler implements CommandExecutor, ConversationAbandonedListener{

    private static CommandHandler ch = new CommandHandler();

    public static CommandHandler getInstance(){
        return ch;
    }

    static String pre = String.format("%s[%sAntiSpleef%s] ", ChatColor.DARK_GRAY, ChatColor.BLUE, ChatColor.DARK_GRAY);

    public ConversationFactory conversationFactory;

    private static Location blueSpawn;
    private static Location redSpawn;
    private static Location endLocation;

    private static String arenaName;

    public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can perform AntiSpleef commands!");
            return true;
        }
        Player p = (Player) sender;
        if(args[0].equalsIgnoreCase("createarena")){
            if(args.length != 2){
                p.sendMessage(pre + ChatColor.GRAY + "Command usage: /as createarena <arenaName> - starts conversation with player to set arena information,");
                return true;
            }
            if(!(p instanceof Conversable)){
                p.sendMessage("Nope");
                return true;
            }
            arenaName = args[1];
            conversationFactory.buildConversation((Conversable)p).begin();

        }
        return true;
    }
    public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent){
        if(abandonedEvent.gracefulExit()){
            abandonedEvent.getContext().getForWhom().sendRawMessage("Conversation exited gracefully.");
        }else{
            abandonedEvent.getContext().getForWhom().sendRawMessage("Conversation abandoned by " + abandonedEvent.getCanceller().getClass().getName());
        }
    }

    public static class SpawnPrompt extends FixedSetPrompt {
        public SpawnPrompt(){
            super("done",
                  "Done",
                    "Exit");
        }
        public String getPromptText(ConversationContext context){
            return pre + ChatColor.GRAY + "Goto the blue spawn point and type - done or Type Exit to exit conversation.";
        }
        @Override
        protected Prompt acceptValidatedInput(ConversationContext context, String s){
            if(s.equalsIgnoreCase("exit")){
                return Prompt.END_OF_CONVERSATION;
            }
            String str = context.getForWhom().getClass().getName();
            Player p = Bukkit.getPlayer(str);
            Location loc = p.getLocation();
            blueSpawn = loc;
            context.setSessionData("type", s);

            //TODO: set blue spawn to conversable player location.
            return new SpawnPrompt1();
        }
    }
    public static class SpawnPrompt1 extends FixedSetPrompt{
        public SpawnPrompt1(){
            super("done",
                    "Done",
                    "Exit");
        }
        public String getPromptText(ConversationContext context){
            return pre + ChatColor.GRAY +"Goto red spawn point and type - done or Type exit to exit conversation.";
        }
        @Override
        protected Prompt acceptValidatedInput(ConversationContext context, String s){
            if(s.equalsIgnoreCase("exit")){
                return Prompt.END_OF_CONVERSATION;
            }
            String str = context.getForWhom().getClass().getName();
            Player p = Bukkit.getPlayer(str);
            Location loc = p.getLocation();
            redSpawn = loc;
            context.setSessionData("type", s);
            return new SpawnPrompt2();
        }

    }
    public static class SpawnPrompt2 extends FixedSetPrompt{
        public SpawnPrompt2(){
            super("done",
                    "Done",
                    "Exit");
        }
        public String getPromptText(ConversationContext context){
            return pre + ChatColor.GRAY + "Goto end location point and type - done or Type exit to exit conversation.";
        }
        @Override
        protected Prompt acceptValidatedInput(ConversationContext context, String s){
            if(s.equalsIgnoreCase("exit")){
                return Prompt.END_OF_CONVERSATION;
            }
            String str = context.getForWhom().getClass().getName();
            Player p = Bukkit.getPlayer(str);
            Location loc = p.getLocation();
            endLocation = loc;
            context.setSessionData("type", s);
            return new FinishedPrompt();
        }
    }
    private static class FinishedPrompt extends MessagePrompt{
        public String getPromptText(ConversationContext context){
            ArenaManager.getManager().createArena(arenaName, blueSpawn, redSpawn, endLocation, 2);
            return pre + ChatColor.GRAY + "You arena has been made";
        }
        @Override
        protected Prompt getNextPrompt(ConversationContext context){
            return Prompt.END_OF_CONVERSATION;
        }
    }


}
