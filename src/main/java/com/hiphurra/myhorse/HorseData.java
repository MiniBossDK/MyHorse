package com.hiphurra.myhorse;

import com.hiphurra.myhorse.managers.YamlDataFile;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HorseData {

    private final YamlDataFile horseConfig;

    private static final String OWNER = ".owner";
    private static final String NAME = ".name";
    private static final String FRIENDS = ".friends";
    private static final String LOCKED = ".locked";
    private static final String HAS_INVENTORY = ".inventory";
    private static final String LOCATION = ".location";
    private static final String PRICE = ".price";

    public HorseData() {
        this.horseConfig = plugin.getConfigManager(com.hiphurra.myhorse.enums.YamlFile.HORSES);
    }

    public static void remove(UUID horseId) {
        setHorseConfig(horseId.toString(), null);
    }

    public static void setOwner(UUID horseId, UUID playerId) {
        setHorseConfig(horseId.toString() + OWNER, playerId.toString());
    }

    public static UUID getOwner(UUID horseId) {
        String ownerId = getHorseConfig().getString(horseId.toString() + OWNER);
        if(ownerId != null) return UUID.fromString(ownerId);
        return null;
    }

    public static boolean hasOwner(UUID horseId) {
        return getHorseConfig().get(horseId.toString() + OWNER) != null;
    }

    public static void setName(UUID horseId, String name) {
        setHorseConfig(horseId.toString() + NAME, name);
    }

    public static String getName(UUID horseId) {
        return getHorseConfig().getString(horseId.toString() + NAME);
    }

    public static void setTrustedPlayers(UUID horseId, List<UUID> playerIds) {
        setHorseConfig(horseId.toString() + FRIENDS, playerIds.stream().map(UUID::toString).collect(Collectors.toList()));
    }

    public static void addTrustedPlayer(UUID horseId, UUID friendId) {
        List<UUID> friendIds = getTrustedPlayers(horseId);
        friendIds.add(friendId);
        setHorseConfig(horseId.toString() + FRIENDS, friendIds.stream().map(UUID::toString).collect(Collectors.toList()));
    }

    public static void removeTrustedPlayer(UUID horseId, UUID friendId) {
        List<UUID> friendIds = getTrustedPlayers(horseId);
        friendIds.remove(friendId);
        setHorseConfig(horseId.toString() + FRIENDS, friendIds.stream().map(UUID::toString).collect(Collectors.toList()));
    }

    public static List<UUID> getTrustedPlayers(UUID horseId) {
        List<String> friendsAsStrings = getHorseConfig().getStringList(horseId.toString() + FRIENDS);
        List<UUID> friends = new ArrayList<>();
        for (String friend : friendsAsStrings) {
            friends.add(UUID.fromString(friend));
        }
        return friends;
    }

    public static boolean hasTrustedPlayers(UUID horseId) {
        return !getHorseConfig().getStringList(horseId.toString() + FRIENDS).isEmpty();
    }

    public static boolean isTrusted(UUID horseId, UUID playerId) {
        return getTrustedPlayers(horseId).contains(playerId);
    }

    public static void setLocked(UUID horseId, boolean locked) {
        setHorseConfig(horseId.toString() + LOCKED, locked);
    }

    public static boolean isLocked(UUID horseId) {
        return getHorseConfig().getBoolean(horseId.toString() + LOCKED);
    }

    public static void setCurrentLocation(UUID horseId, Location location) {
        setHorseConfig(horseId.toString() + LOCATION, location);
    }

    public static Location getHorseLocation(UUID horseId) {
        return getHorseConfig().getLocation(horseId.toString() + LOCATION);
    }

    public static void setForSale(UUID horseId, double price) {
        setHorseConfig(horseId.toString() + PRICE, price);
    }

    public static void cancelSale(UUID horseId) {
        setHorseConfig(horseId.toString() + PRICE, null);
    }

    public static boolean isForSale(UUID horseId) {
        return getHorseConfig().get(horseId.toString() + PRICE) != null;
    }

    public static double getPrice(UUID horseId) {
        return getHorseConfig().getDouble(horseId.toString() + PRICE);
    }

    // TODO - Evaluate if this method should exist
    public static void setInventory(boolean flag, UUID horseId) {
        setHorseConfig(horseId.toString() + HAS_INVENTORY, flag);
    }

    public static boolean hasInventory(UUID horseId) {
        return getHorseConfig().getBoolean(horseId.toString() + HAS_INVENTORY);
    }

    private static YamlDataFile getHorseConfig() {
        return horseConfig;
    }

    private static void setHorseConfig(String path, Object value) {
        getHorseConfig().set(path, value);
    }
}
