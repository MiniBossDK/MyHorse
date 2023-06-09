package com.hiphurra.myhorse;

import com.hiphurra.myhorse.enums.ConfigFile;
import com.hiphurra.myhorse.enums.PermissionNode;
import com.hiphurra.myhorse.managers.ConfigManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HorseData {

    private final MyHorse plugin;
    private final ConfigManager horseConfig;
    private final UUID horseId;

    private static final String OWNER = ".owner";
    private static final String NAME = ".name";
    private static final String FRIENDS = ".friends";
    private static final String LOCKED = ".locked";
    private static final String HAS_INVENTORY = ".inventory";
    private static final String LOCATION = ".location";
    private static final String PRICE = ".price";

    public HorseData(MyHorse plugin, UUID horseId) {
        this.plugin = plugin;
        this.horseConfig = plugin.getConfigManager(ConfigFile.HORSES);
        this.horseId = horseId;
    }

    public void remove() {
        setHorseConfig(getHorseId().toString(), null);
    }

    public void setOwner(UUID playerId) {
        setHorseConfig(getHorseId().toString() + OWNER, playerId.toString());
    }

    public UUID getOwnerId() {
        String horseId = getHorseConfig().getString(getHorseId().toString() + OWNER);
        if(horseId != null) return UUID.fromString(horseId);
        return null;
    }

    public boolean hasOwner() {
        return getHorseConfig().get(getHorseId().toString() + OWNER) != null;
    }

    public void setName(String name) {
        setHorseConfig(getHorseId().toString() + NAME, name);
    }

    public String getName() {
        return getHorseConfig().getString(getHorseId().toString() + NAME);
    }

    public void setTrustedPlayers(List<UUID> playerIds) {
        setHorseConfig(getHorseId().toString() + FRIENDS, playerIds.stream().map(UUID::toString).collect(Collectors.toList()));
    }

    public void addTrustedPlayer(UUID friendId) {
        List<UUID> friendIds = Stream.concat(getTrustedPlayers().stream(), Stream.of(friendId)).collect(Collectors.toList());
        setHorseConfig(getHorseId().toString() + FRIENDS, friendIds.stream().map(UUID::toString).collect(Collectors.toList()));
    }

    public void removeTrustedPlayer(UUID friendId) {
        List<UUID> friendIds = getTrustedPlayers().stream().filter(uuid -> !uuid.equals(friendId)).collect(Collectors.toList());
        setHorseConfig(getHorseId().toString() + FRIENDS, friendIds.stream().map(UUID::toString).collect(Collectors.toList()));
    }

    public List<UUID> getTrustedPlayers() {
        List<String> friendsAsStrings = getHorseConfig().getStringList(getHorseId().toString() + FRIENDS);
        List<UUID> friends = new ArrayList<>();
        for (String friend : friendsAsStrings) {
            friends.add(UUID.fromString(friend));
        }
        return friends;
    }

    public boolean hasTrustedPlayers() {
        return !getHorseConfig().getStringList(getHorseId().toString() + FRIENDS).isEmpty();
    }

    public boolean isTrusted(UUID playerId) {
        return getTrustedPlayers().contains(playerId);
    }

    public void setLocked(boolean locked) {
        setHorseConfig(getHorseId().toString() + LOCKED, locked);
    }

    public boolean isLocked() {
        return getHorseConfig().getBoolean(getHorseId().toString() + LOCKED);
    }

    public void setCurrentLocation(Location location) {
        setHorseConfig(getHorseId().toString() + LOCATION, location);
    }

    public Location getHorseLocation() {
        return getHorseConfig().getLocation(getHorseId().toString() + LOCATION);
    }

    public void setForSale(double price) {
        setHorseConfig(getHorseId().toString() + PRICE, price);
    }

    public void cancelSale() {
        setHorseConfig(getHorseId().toString() + PRICE, null);
    }

    public boolean isForSale() {
        return getHorseConfig().get(getHorseId().toString() + PRICE) != null;
    }

    public double getPrice() {
        return getHorseConfig().getDouble(getHorseId().toString() + PRICE);
    }

    public void setInventory(boolean flag) {
        setHorseConfig(getHorseId().toString() + HAS_INVENTORY, flag);
    }

    public boolean hasInventory() {
        return getHorseConfig().getBoolean(getHorseId().toString() + HAS_INVENTORY);
    }

    /**
     * Checks if a player has the privileges to access the horse
     * @param player the player that tries to access the horse
     * @return true of the player has access and false if not
     */
    public boolean hasPlayerAccess(Player player)
    {
        return getTrustedPlayers().contains(player.getUniqueId())
                || player.getUniqueId().equals(getOwnerId())
                || player.isOp()
                || plugin.getPermissionManager().hasPermission(player, PermissionNode.ADMIN);
    }

    public boolean isPlayerTrusted(Player player)
    {
        return getTrustedPlayers().contains(player.getUniqueId());
    }

    public boolean isPlayerOwner(Player player)
    {
        return player.getUniqueId().equals(getOwnerId());
    }

    private ConfigManager getHorseConfig() {
        return horseConfig;
    }

    private void setHorseConfig(String path, Object value) {
        getHorseConfig().set(path, value);
    }

    public UUID getHorseId() {
        return horseId;
    }
}
