package com.hiphurra.myhorse.customhorses;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomHorse implements CustomAbstractHorse<Horse> {

    private final UUID id;

    private EntityType type;
    private ItemStack armor;
    private ItemStack saddle;
    private Horse.Color color;
    private Horse.Style style;
    private UUID ownerId;
    private String name;
    private List<UUID> trustedPlayers = new ArrayList<>();
    private boolean isLocked;
    private Location currentLocation;
    private boolean hasInventory;

    public CustomHorse(UUID id) {
        this.id = id;
    }

    @Override
    public void extractEntityInformation(Horse horse) {
        this.type = horse.getType();
        this.color = horse.getColor();
        this.style = horse.getStyle();
        this.saddle = horse.getInventory().getSaddle();
        this.armor = horse.getInventory().getArmor();
        this.currentLocation = horse.getLocation();
    }

    public ItemStack getArmor() {
        return armor;
    }

    public boolean hasArmor() {
        return armor != null;
    }

    @Override
    public boolean hasSaddle() {
        return saddle != null;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public EntityType getType() {
        return type;
    }

    @Override
    public void setType(EntityType type) {
        this.type = type;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID playerId) {
        this.ownerId = playerId;
    }

    public boolean hasOwner() {
        return ownerId != null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UUID> getTrustedPlayers() {
        return trustedPlayers;
    }

    public void setTrustedPlayers(List<UUID> playerIds) {
        this.trustedPlayers = playerIds;
    }

    public boolean hasTrustedPlayers() {
        return false;
    }

    public boolean isTrusted(UUID playerId) {
        return false;
    }

    public boolean isLocked() {
        return false;
    }

    public void setLocked(boolean locked) {
        this.isLocked = locked;
    }

    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setInventory(boolean flag) {
        this.hasInventory = flag;
    }

    public boolean hasInventory() {
        return hasInventory;
    }
}
