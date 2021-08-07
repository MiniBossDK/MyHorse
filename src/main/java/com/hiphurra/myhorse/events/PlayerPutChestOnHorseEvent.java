package com.hiphurra.myhorse.events;

import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerPutChestOnHorseEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final AbstractHorse horse;
    private boolean cancelled;

    public PlayerPutChestOnHorseEvent(Player player, AbstractHorse horse) {
        this.player = player;
        this.horse = horse;
    }

    public Player getPlayer() {
        return player;
    }

    public AbstractHorse getHorse() {
        return horse;
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
