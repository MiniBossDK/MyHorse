package com.hiphurra.myhorse.inventory;

import com.hiphurra.myhorse.HorseData;
import com.hiphurra.myhorse.MyHorse;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

public class InventoryManager {

    private final AbstractHorse abstractHorse;
    private final MyHorse plugin;
    private final Inventory inventory;
    private final static int INVENTORY_SIZE = 9;

    public InventoryManager(MyHorse plugin, AbstractHorse abstractHorse) {
        this.plugin = plugin;
        this.abstractHorse = abstractHorse;
        this.inventory = Bukkit.createInventory(abstractHorse, INVENTORY_SIZE, new HorseData(plugin, abstractHorse.getUniqueId()).getName() + "'s inventory");
        if(getInventoryData() == null) {
            this.inventory.setStorageContents(new ItemStack[INVENTORY_SIZE]);
        } else if(getInventoryData().getItems().length > INVENTORY_SIZE) {
            for (ItemStack item : getInventoryData().getItems()) {
                if(item != null) this.inventory.addItem(item);
            }
        } else {
            this.inventory.setStorageContents(getInventoryData().getItems());
        }
    }

    public void setItems(ItemStack[] items) {
        getPersistentDataContainer().set(getNamespacedKey(), new InventoryDataTagType(plugin), new InventoryData(abstractHorse.getUniqueId(), items));
    }

    public Inventory getInventory() {
        return inventory;
    }

    private InventoryData getInventoryData() {
        return getPersistentDataContainer().get(getNamespacedKey(), new InventoryDataTagType(plugin));
    }

    private PersistentDataContainer getPersistentDataContainer() {
        return abstractHorse.getPersistentDataContainer();
    }

    private NamespacedKey getNamespacedKey() {
        return new NamespacedKey(plugin, "inventory");
    }
}
