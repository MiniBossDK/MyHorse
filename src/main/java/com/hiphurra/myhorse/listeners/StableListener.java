package com.hiphurra.myhorse.listeners;

import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.events.PlayerDefiningAreaEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StableListener implements Listener {

    private final MyHorse plugin;

    public StableListener(MyHorse plugin) {

        this.plugin = plugin;
    }

    @EventHandler
    public void playerDefiningAreaEvent(PlayerDefiningAreaEvent event) {
        Player player = event.getPlayer();
        Block block = player.getWorld().getHighestBlockAt(player.getLocation());
        if(block.getType().equals(Material.AIR) || block.getType().equals(Material.WATER)) block.setType(Material.RED_CONCRETE);
    }

}
