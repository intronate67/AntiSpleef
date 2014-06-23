package com.mcdrum.dev;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by Admin on 6/22/2014.
 */
public class AntiSpleef extends JavaPlugin{

    private static AntiSpleef instance = new AntiSpleef();

    public static AntiSpleef getInstance(){
        return instance;
    }

    public void onEnable(){
        getCommand("as").setExecutor(new CommandHandler());
        if(!new File(this.getDataFolder(), "config.yml").exists()){
            this.saveDefaultConfig();
        }
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerBuild(), this);

    }
    public void onDisable(){

    }

}
