package com.hiphurra.myhorse.dao;

import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public interface AbstractHorseDAO {

    void remove(UUID horseId);

    void setOwner(UUID horseId, UUID playerId);


    UUID getOwner(UUID horseId);


    boolean hasOwner(UUID horseId);


    void setName(UUID horseId, String name);


    String getName(UUID horseId);


    void setTrustedPlayers(UUID horseId, List<UUID> playerIds);


    void addTrustedPlayer(UUID horseId, UUID friendId);


    void removeTrustedPlayer(UUID horseId, UUID friendId);


    List<UUID> getTrustedPlayers(UUID horseId);


    boolean hasTrustedPlayers(UUID horseId);


    boolean isTrusted(UUID horseId, UUID playerId);


    void setLocked(UUID horseId, boolean locked);


    boolean isLocked(UUID horseId);


    void setCurrentLocation(UUID horseId, Location location);


    Location getHorseLocation(UUID horseId);


    void setForSale(UUID horseId, double price);


    void cancelSale(UUID horseId);


    boolean isForSale(UUID horseId);


    double getPrice(UUID horseId);


    // TODO - Evaluate if this method should exist
    void setInventory(boolean flag, UUID horseId);


    boolean hasInventory(UUID horseId);


}
