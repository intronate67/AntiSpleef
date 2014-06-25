package com.mcdrum.dev;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;

/**
 * @Author Hunter Sharpe
 */
public class Arena {

    public static ArrayList<Arena> arenaObjects = new ArrayList<Arena>();

    private Location blueSpawn, redSpawn, lobbyLocation, endLocation;

    private String name;

    private ArrayList<String> players = new ArrayList<String>();

    private int maxPlayers = 2;

    private boolean inGame = false;

    public Arena(String arenaName, Location blueSpawn, Location redSpawn, Location endLocation, int maxPlayers){

        this.name = arenaName;
        this.blueSpawn = blueSpawn;
        this.redSpawn = redSpawn;
        this.endLocation = endLocation;
        this.maxPlayers = maxPlayers;

        arenaObjects.add(this);
    }
    public Location getBlueSpawn(){
        return this.blueSpawn;
    }
    public Location getRedSpawn(){
        return this.redSpawn;
    }
    public void setBlueSpawn(Location blueSpawn){
        this.blueSpawn = blueSpawn;
    }
    public void setRedSpawn(Location redSpawn){
        this.redSpawn = redSpawn;
    }
    public Location getEndLocation(){
        return this.endLocation;
    }
    public void setEndLocation(Location endLocation){
        this.endLocation = endLocation;
    }
    public ArrayList<String> getPlayers(){
        return this.players;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public boolean isFull(){
        if(players.size() == 2){
            return true;
        }else{
            return false;
        }
    }
    public boolean isInGame(){
        return inGame;
    }
    public void setInGame(boolean inGame){
        this.inGame = inGame;
    }
    public void sendMessage(String message){
        String pre = String.format("%s[%sAntiSpleef%s] ", ChatColor.DARK_GRAY, ChatColor.BLUE, ChatColor.DARK_GRAY);
        for(String s : players){
            Bukkit.getPlayer(s).sendMessage(pre + message);
        }
    }



}
