package com.hiphurra.myhorse;

import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AbstractHorseOwner {

    UUID getId();

    void selectHorse(@Nullable UUID horseIdentifier);


    @Nullable UUID getSelectedHorse() throws IllegalArgumentException;


    List<UUID> getAllTrustedPlayers();


    void removeTrustOnAllHorses(UUID playerId);


    void addTrustOnAllHorses(UUID playerId);


    void setLastLogin(Date lastLogin);


    Date getLastLoginDate();

    void addHorse(UUID horseId);

    List<UUID> getHorses();


    boolean hasHorseFromId(UUID horseId);


    boolean hasHorseFromName(String name);


    List<SalesRecord> getSalesRecords();


    void setSalesRecord(SalesRecord salesRecord);


    void deleteSalesRecord(UUID recordId);


    void setBuying(boolean buying);


    boolean isBuying();


    void setAreaMode(boolean areaMode);


    boolean isDefiningArea();


    void setStartLocation(Location location);


    Location getStartLocation();


}
