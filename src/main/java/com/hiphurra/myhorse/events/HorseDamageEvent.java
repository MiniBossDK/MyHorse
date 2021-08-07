package com.hiphurra.myhorse.events;

import org.bukkit.entity.AbstractHorse;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public class HorseDamageEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final AbstractHorse damagedHorse;
    private final EntityDamageEvent.DamageCause damageCause;
    private boolean cancelled;

    public HorseDamageEvent(AbstractHorse horse,  EntityDamageEvent.DamageCause damageCause) {
        this.damagedHorse = horse;
        this.damageCause = damageCause;
    }

    public AbstractHorse getDamagedHorse() {
        return damagedHorse;
    }

    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
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
