package com.mcdrum.dev;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Admin on 6/22/2014.
 */
public class PlayerBuild implements Listener{

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK || !ArenaManager.getInstance().inArena.containsKey(e.getPlayer().getName()))
            return;
        String arenaName = ArenaManager.getInstance().inArena.get(e.getPlayer().getName());

    }





















}
