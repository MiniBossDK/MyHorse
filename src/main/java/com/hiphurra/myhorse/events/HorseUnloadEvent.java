package com.hiphurra.myhorse.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class HorseUnloadEvent extends Event implements Cancellable {


    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
