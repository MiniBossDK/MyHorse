package com.hiphurra.myhorse.listeners;

import com.hiphurra.myhorse.HorseData;
import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.events.HorseInventoryOpenEvent;
import com.hiphurra.myhorse.events.PlayerPutChestOnHorseEvent;
import com.hiphurra.myhorse.inventory.InventoryManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    private final MyHorse plugin;

    public InventoryListener(MyHorse plugin) {

        this.plugin = plugin;
    }

    @EventHandler
    public void horseInventoryOpenEvent(HorseInventoryOpenEvent event) {
        InventoryManager inventoryManager = new InventoryManager(plugin, event.getInventoryHolder());
        event.getPlayer().openInventory(inventoryManager.getInventory());
    }

    @EventHandler
    public void playerPutChestOnHorseEvent(PlayerPutChestOnHorseEvent event) {
        HorseData horseData = new HorseData(plugin, event.getHorse().getUniqueId());
        horseData.setInventory(true);
        InventoryManager inventoryManager = new InventoryManager(plugin, event.getHorse());
        inventoryManager.setItems(new ItemStack[9]);
        // TODO - Use the LanguageManager to give the message
        event.getPlayer().sendMessage("Chest has been placed!");
    }

}
