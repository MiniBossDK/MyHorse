package com.hiphurra.myhorse.inventory;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class InventoryData {

    private final UUID inventoryHolderId;
    private final ItemStack[] items;

    public InventoryData(UUID inventoryHolderId, ItemStack[] items) {
        this.inventoryHolderId = inventoryHolderId;
        this.items = items;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public UUID getInventoryHolderId() {
        return inventoryHolderId;
    }
}
