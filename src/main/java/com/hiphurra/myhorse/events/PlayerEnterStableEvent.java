package com.hiphurra.myhorse.events;

import com.hiphurra.myhorse.stable.Stable;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerEnterStableEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final UUID owner;
    private final Stable stableArea;
    private boolean cancelled;

    public PlayerEnterStableEvent(UUID owner, Stable stableArea) {
        this.owner = owner;
        this.stableArea = stableArea;
    }

    public UUID getOwner() {
        return owner;
    }

    public Stable getStableArea() {
        return stableArea;
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
