package com.mcdrum.dev;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Admin on 6/22/2014.
 */
public class ArenaManager {
    String pre = String.format("%s[%sAntiSpleef%s] ", ChatColor.DARK_GRAY, ChatColor.BLUE, ChatColor.DARK_GRAY);

    private static ArenaManager instance = new ArenaManager();

    public static final ArenaManager getInstance() {
        return instance;
    }

    public HashMap<String, Location> preLoc = new HashMap<String, Location>();

    public HashMap<String, String> inArena = new HashMap<String, String>();

    FileConfiguration config = AntiSpleef.getInstance().getConfig();


    public void addPlayer(Player p, Location loc, String arena){
        if(preLoc.containsKey(p.getName()) || inArena.containsKey(p.getName())){
            p.sendMessage(pre + ChatColor.GRAY + "You are already in an arena!");
        }
        preLoc.put(p.getName(), p.getLocation());
        inArena.put(p.getName(), arena);
        p.sendMessage(pre + ChatColor.GRAY + "You have joined the arena: " + arena);
        p.teleport(loc);
    }
    public void removePlayer(Player p, String arena){
        if(!inArena.containsKey(p.getName())){
            p.sendMessage(pre + ChatColor.GRAY + "You are not in a game!");
            return;
        }
        inArena.remove(p.getName(), arena);
        p.teleport(preLoc.get(p.getName()));
        preLoc.remove(p.getName());
        p.sendMessage(pre + ChatColor.GRAY + "You have left the arena: " + arena);
    }


}
