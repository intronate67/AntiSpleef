package com.mcdrum.dev;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Created by Admin on 6/22/2014.
 */
public class CommandHandler implements CommandExecutor{

    FileConfiguration config = AntiSpleef.getInstance().getConfig();

    public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args){

        String pre = String.format("%s[%sAntiSpleef%s] ", ChatColor.DARK_GRAY, ChatColor.BLUE, ChatColor.DARK_GRAY);

        if(args.length == 0 || args.length >= 3){
            sender.sendMessage(pre + ChatColor.GRAY + "Use /as help for help.");
            return true;
        }
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run AntiSpleef commands!");
            return true;
        }
        Player p = (Player) sender;
        if(args[0].equalsIgnoreCase("create")){
            if(args.length != 2){
                p.sendMessage(pre + ChatColor.GRAY + "Command Usage: /as create <arena-name>");
                return true;
            }
            if(config.contains(args[1])){
                p.sendMessage(pre + ChatColor.GRAY + "Arena already exists!");
                return true;
            }
            config.set("Arenas", args[1]);
            p.sendMessage(pre + ChatColor.GRAY + "Created arena: " + args[1] + "!");
            return true;
        }
        if(args[0].equalsIgnoreCase("setspawn")){
            if(args.length != 3){
                p.sendMessage(pre + ChatColor.GRAY + "Command usage: /as setspawn <1-2> <arena-name>");
                return true;
            }
            if(!args[1].equalsIgnoreCase("1") || !args[1].equalsIgnoreCase("2")){
                p.sendMessage(pre + ChatColor.GRAY + "Invalid spawn number, has to be either 1 or 2");
                return true;
            }
            if(!config.contains(args[2])){
                p.sendMessage(pre + ChatColor.GRAY + "Arena does not exist, you should make one!");
                return true;
            }
            double x = p.getLocation().getX();
            double y = p.getLocation().getY();
            double z = p.getLocation().getZ();
            String world = p.getWorld().getName();
            config.set("Arena." + args[2] + args[1] + "x", x);
            config.set("Arena." + args[2] + args[1] + "y", y);
            config.set("Arena." + args[2] + args[1] + "z", z);
            config.set("Arena." + args[2] + args[1] + "world", world);
            p.sendMessage(pre + ChatColor.GRAY + "Created spawn #" + args[1] + " at " + x + ", " + y + ", " + z + ", " + world);
            return true;
        }
        if(args[0].equalsIgnoreCase("join")){
            if(args.length != 2){
                p.sendMessage(pre + ChatColor.GRAY + "Not a valid arena!");
                return true;
            }
            World world = Bukkit.getWorld(config.getString("Arena." + args[1] + "world"));
            double x = config.getDouble("Arena." + args[1] + "x");
            double y = config.getDouble("Arena." + args[1] + "y");
            double z = config.getDouble("Arena." + args[1] + "z");
            Location loc = new Location(world, x, y, z);
            ArenaManager.getInstance().addPlayer(p, loc, args[1]);
            return true;
        }
        if(args[0].equalsIgnoreCase("leave")){
            if(args.length != 1){
                p.sendMessage(pre + ChatColor.GRAY + "Command usage: /as leave");
                return true;
            }
            String arena = ArenaManager.getInstance().inArena.get(p.getName());
            ArenaManager.getInstance().removePlayer(p, arena);
            return true;
        }
        return true;
    }

}
