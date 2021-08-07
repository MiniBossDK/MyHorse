package com.hiphurra.myhorse.events;


import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class HorseBoughtEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final AbstractHorse abstractHorse;
    private final double price;
    private final Player buyer;
    private boolean cancelled;

    public HorseBoughtEvent(AbstractHorse abstractHorse, double price, Player buyer) {
        this.abstractHorse = abstractHorse;
        this.price = price;
        this.buyer = buyer;
        this.cancelled = false;
    }

    public AbstractHorse getAbstractHorse() {
        return abstractHorse;
    }

    public double getPrice() {
        return price;
    }

    public Player getBuyer() {
        return buyer;
    }

    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
