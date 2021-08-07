package com.hiphurra.myhorse.events;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a horse is loaded in from chunk load
 */
public class HorseLoadEvent extends Event implements Cancellable {

    private final AbstractHorse abstractHorse;
    private final Chunk chunk;
    private final World world;

    public HorseLoadEvent(AbstractHorse abstractHorse, Chunk chunk, World world) {
        this.abstractHorse = abstractHorse;
        this.chunk = chunk;
        this.world = world;
    }

    public AbstractHorse getAbstractHorse() {
        return abstractHorse;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public World getWorld() {
        return world;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return null;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }
}
