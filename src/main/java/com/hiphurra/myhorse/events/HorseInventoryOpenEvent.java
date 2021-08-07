package com.hiphurra.myhorse.events;

import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class HorseInventoryOpenEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final AbstractHorse inventoryHolder;
    private final Inventory inventory;
    private final Player player;
    private boolean cancelled;

    public HorseInventoryOpenEvent(AbstractHorse inventoryHolder, Inventory inventory, Player player) {
        this.inventoryHolder = inventoryHolder;
        this.inventory = inventory;
        this.player = player;
        this.cancelled = false;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public AbstractHorse getInventoryHolder() {
        return inventoryHolder;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
