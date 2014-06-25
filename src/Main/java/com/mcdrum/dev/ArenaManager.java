package com.mcdrum.dev;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * @Author Hunter Sharpe
 */
public class ArenaManager {

    String pre = String.format("%s[%sAntiSpleef%s] ", ChatColor.DARK_GRAY, ChatColor.BLUE, ChatColor.DARK_GRAY);

    public static ArenaManager am = new ArenaManager();

    public static ArenaManager getManager(){
        return am;
    }

    public Arena getArena(String name){
        for(Arena a : Arena.arenaObjects){
            if(a.getName().equals(name)){
                return a;
            }
        }
        return null;
    }
    public void addPlayers(Player p, String arenaName){
        if(getArena(arenaName) != null){
            Arena arena = getArena(arenaName);

            if(!arena.isFull()){
                if(!arena.isInGame()){
                    p.getInventory().clear();
                    p.setHealth(p.getMaxHealth());
                    p.setFireTicks(0);
                    Random rand = new Random();
                    int n = rand.nextInt(2) + 1;
                    if(n == 1){
                        p.teleport(arena.getBlueSpawn());
                        arena.getPlayers().add(p.getName());
                        int playersLeft = 2 - arena.getPlayers().size();
                        arena.sendMessage(ChatColor.GRAY + p.getName() + " has joined the arena!");
                        if(playersLeft == 0){
                            startArena(arenaName);
                        }
                    }else{
                        p.teleport(arena.getRedSpawn());
                        arena.getPlayers().add(p.getName());
                        int playersLeft = 2 - arena.getPlayers().size();
                        arena.sendMessage(ChatColor.GRAY + p.getName() + " has joined the arena!");
                        if(playersLeft == 0){
                            startArena(arenaName);
                        }
                    }


                }else{
                    p.sendMessage(pre + ChatColor.GRAY + "The arena you are looking for is current in-game!");
                }
            }else{
                p.sendMessage(pre + ChatColor.GRAY + "The arena you are looking for is full!");
            }
        }else{
            p.sendMessage(pre + ChatColor.GRAY + "The arena you are looking for could not be found!");
        }
    }
    public void removePlayer(Player p, String arenaName){
        if(getArena(arenaName) != null){
            Arena arena = getArena(arenaName);
            if(arena.getPlayers().contains(p.getName())){
                p.getInventory().clear();
                p.setHealth(p.getMaxHealth());
                p.setFireTicks(0);
                p.teleport(arena.getEndLocation());
                arena.getPlayers().remove(p.getName());
                arena.sendMessage(ChatColor.GRAY + p.getName() + " has left the arena.");
                //TODO: remove existing left player!
            }else{
                p.sendMessage(pre + ChatColor.GRAY + "You're not in an arena!");
            }
        }else{
            p.sendMessage(pre + ChatColor.GRAY + "Arena does not exist!");
        }
    }
    public void startArena(String arenaName){
        if(getArena(arenaName) != null){
            Arena arena = getArena(arenaName);
            arena.sendMessage(ChatColor.GRAY + "Begin!");
            arena.setInGame(true);
            for(String s : arena.getPlayers()){
                Bukkit.getPlayer(s);
                //TODO: teleport to either blue/red spawn.
            }
        }
    }
    public void endArena(String arenaName){
        if(getArena(arenaName) != null){
            Arena arena = getArena(arenaName);
            arena.sendMessage(ChatColor.GRAY + "Game has ended!");
            arena.setInGame(false);
            //TODO: set winner.
            for(String s : arena.getPlayers()){
                Player p = Bukkit.getPlayer(s);
                p.teleport(arena.getEndLocation());
                p.getInventory().clear();
                p.setHealth(p.getMaxHealth());
                p.setFireTicks(0);
                arena.getPlayers().remove(p.getName());

            }
        }
    }
    public void loadArenas(){
        FileConfiguration config = null;

        for(String keys : config.getConfigurationSection("arenas").getKeys(false)){
            World world = Bukkit.getWorld("arenas." + keys + ".world");
            double blueX = config.getDouble("arenas." + "keys." + "blueX");
            double blueY = config.getDouble("arenas." + "keys." + "blueY");
            double blueZ = config.getDouble("arenas." + "keys." + "blueZ");
            Location blueSpawn = new Location(world, blueX, blueY, blueZ);
            double redX = config.getDouble("arenas." + "keys." + "redX");
            double redY = config.getDouble("arenas." + "keys." + "redY");
            double redZ = config.getDouble("arenas." + "keys." + "redZ");
            Location redSpawn = new Location(world, redX, redY, redZ);
            double endX = config.getDouble("arenas." + "keys." + "endX");
            double endY = config.getDouble("arenas." + "keys." + "endY");
            double endZ = config.getDouble("arenas." + "keys." + "end");
            Location endLocation = new Location(world, endX, endY, endZ);
            Arena arena = new Arena(keys, blueSpawn, redSpawn, endLocation, 2);

        }
    }
    public void createArena(String arenaName, Location blueSpawn, Location redSpawn, Location endLocation, int maxPlayers){
        Arena arena = new Arena(arenaName, blueSpawn, redSpawn, endLocation, 2);
        FileConfiguration config = null;
        config.set("arenas." + arenaName, null);
        String path = "arenas." + arenaName + ".";
        config.set(path + "blueX", blueSpawn.getX());
        config.set(path + "blueY", blueSpawn.getY());
        config.set(path + "blueZ", blueSpawn.getZ());

        config.set(path + "redX", redSpawn.getX());
        config.set(path + "redY", redSpawn.getY());
        config.set(path + "redZ", redSpawn.getZ());

        config.set(path + "endX", endLocation.getX());
        config.set(path + "endY", endLocation.getY());
        config.set(path + "endZ", endLocation.getZ());
        config.set(path + "maxplayers", 2);


    }
}
