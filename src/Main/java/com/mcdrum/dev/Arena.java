package com.mcdrum.dev;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Admin on 6/22/2014.
 */
public class Arena {

    int countdown;
    int id;

    public void countDown(final Player p){
        countdown = 300;
        final String arena = ArenaManager.getInstance().inArena.get(p.getName());
        id = AntiSpleef.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(AntiSpleef.getInstance(), new BukkitRunnable() {

            @Override
            public void run() {
                if(countdown == 0){
                    ArenaManager.getInstance().removePlayer(p, arena);
                }
                countdown--;
            }
        }, 0L, 20L);
    }

}
