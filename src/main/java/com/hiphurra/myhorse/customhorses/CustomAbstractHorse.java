package com.hiphurra.myhorse.customhorses;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.UUID;

public interface CustomAbstractHorse<T> {

    void extractEntityInformation(T horse);

    UUID getId();

    EntityType getType();

    boolean hasSaddle();

    void setType(EntityType type);

    void setOwnerId(UUID playerId);


    UUID getOwnerId();


    boolean hasOwner();


    void setName(String name);


    String getName();


    void setTrustedPlayers(List<UUID> playerIds);


    List<UUID> getTrustedPlayers();


    boolean hasTrustedPlayers();


    boolean isTrusted(UUID playerId);


    void setLocked(boolean locked);


    boolean isLocked();


    void setCurrentLocation(Location location);


    Location getCurrentLocation();


    // TODO - Evaluate if this method should exist
    void setInventory(boolean flag);


    boolean hasInventory();

}
