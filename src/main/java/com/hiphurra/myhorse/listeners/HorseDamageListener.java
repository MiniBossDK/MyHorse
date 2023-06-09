package com.hiphurra.myhorse.listeners;

import com.hiphurra.myhorse.HorseData;
import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.enums.Setting;
import com.hiphurra.myhorse.events.HorseDamageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HorseDamageListener implements Listener {

    private final MyHorse plugin;

    public HorseDamageListener(MyHorse plugin) {

        this.plugin = plugin;
    }

    @EventHandler
    public void horseDamageEvent(HorseDamageEvent event) {
        HorseData horseData = new HorseData(plugin, event.getDamagedHorse().getUniqueId());
        if(!horseData.hasOwner()) return;
        if((boolean) plugin.getSettings().get(Setting.INVULNERABLE_HORSES)) event.setCancelled(true);
    }

}
