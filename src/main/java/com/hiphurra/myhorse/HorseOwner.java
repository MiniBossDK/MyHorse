package com.hiphurra.myhorse;

import com.hiphurra.myhorse.customhorses.CustomAbstractHorse;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class HorseOwner implements AbstractHorseOwner {

    private final UUID id;
    private UUID selectedHorseId;
    private final List<UUID> horses = new ArrayList<>();
    private final List<SalesRecord> salesRecords = new ArrayList<>();

    public HorseOwner(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void selectHorse(@Nullable UUID horseId) {
        this.selectedHorseId = horseId;
    }

    @Override
    public @Nullable UUID getSelectedHorse() throws IllegalArgumentException {
        return selectedHorseId;
    }

    @Override
    public List<UUID> getAllTrustedPlayers() {
        return null;
    }

    @Override
    public void removeTrustOnAllHorses(UUID playerId) {
        
    }

    @Override
    public void addTrustOnAllHorses(UUID playerId) {

    }

    @Override
    public void setLastLogin(Date lastLogin) {

    }

    @Override
    public Date getLastLoginDate() {
        return null;
    }

    @Override
    public void addHorse(UUID horseId) {
        horses.add(horseId);
    }

    @Override
    public List<UUID> getHorses() {
        return horses;
    }

    @Override
    public boolean hasHorseFromId(UUID horseId) {
        return horses.contains(horseId);
    }

    @Override
    public boolean hasHorseFromName(String name) {
        return false;
    }

    @Override
    public List<SalesRecord> getSalesRecords() {
        return salesRecords;
    }

    @Override
    public void setSalesRecord(SalesRecord salesRecord) {

    }

    @Override
    public void deleteSalesRecord(UUID recordId) {

    }

    @Override
    public void setBuying(boolean buying) {

    }

    @Override
    public boolean isBuying() {
        return false;
    }

    @Override
    public void setAreaMode(boolean areaMode) {

    }

    @Override
    public boolean isDefiningArea() {
        return false;
    }

    @Override
    public void setStartLocation(Location location) {

    }

    @Override
    public Location getStartLocation() {
        return null;
    }
}
