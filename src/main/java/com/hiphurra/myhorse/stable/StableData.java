package com.hiphurra.myhorse.stable;

import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.enums.ConfigFile;
import com.hiphurra.myhorse.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;
import java.util.logging.Level;

public class StableData {

    private final MyHorse plugin;
    private final ConfigManager stablesConfig;
    private final UUID stableId;

    private final static String OWNER = ".owner";
    private final static String NAME = ".name";
    private final static String REGION = ".region";
        private final static String REGION_WORLD = REGION + ".world";
        private final static String REGION_X_MIN = REGION + ".Xmin";
        private final static String REGION_Z_MIN = REGION + ".Zmin";
        private final static String REGION_X_MAX = REGION + ".Xmax";
        private final static String REGION_Z_MAX = REGION + ".Zmax";

    public StableData(MyHorse plugin, UUID ownerId) {
        this.plugin = plugin;
        this.stableId = ownerId;
        this.stablesConfig = plugin.getConfigManager(ConfigFile.STABLES);
    }

    public String getName() {
        return getStablesConfig().getString(getStableId().toString() + NAME);
    }

    public void setName(String name) {
        setStablesConfig(getStableId().toString() + NAME, name);
    }

    public void setOwner(UUID owner) {
        setStablesConfig(getStableId().toString() + OWNER, owner.toString());
    }

    public UUID getOwner() {
        return UUID.fromString(getStablesConfig().getString(getStableId().toString() + OWNER));
    }

    public Stable getRegion() {
        return new Stable(
            getOwner(),
            getName(),
            Bukkit.getWorld(getStablesConfig().getString(getStableId().toString() + REGION_WORLD)),
            getStablesConfig().getDouble(getStableId().toString() + REGION_X_MIN),
            getStablesConfig().getDouble(getStableId().toString() + REGION_Z_MIN),
            getStablesConfig().getDouble(getStableId().toString() + REGION_X_MAX),
            getStablesConfig().getDouble(getStableId().toString() + REGION_Z_MAX)
        );
    }

    public void setRegion(World world, double x_min, double z_min, double x_max, double z_max) {
        setStablesConfig(getStableId().toString() + REGION_WORLD, world.getName());
        setStablesConfig(getStableId().toString() + REGION_X_MIN, Math.min(x_min, x_max));
        setStablesConfig(getStableId().toString() + REGION_Z_MIN, Math.min(z_min, z_max));
        setStablesConfig(getStableId().toString() + REGION_X_MAX, Math.max(x_min, x_max));
        setStablesConfig(getStableId().toString() + REGION_Z_MAX, Math.max(z_min, z_max));
    }

    public void setRegion(Location loc1, Location loc2) {
        World world1 = loc1.getWorld();
        World world2 = loc2.getWorld();
        if(world1 == null || world2 == null) {
            plugin.logDebug(Level.WARNING, "The world in one of the locations is null!");
            return;
        }
        if(!world1.equals(world2)) {
            plugin.logDebug(Level.SEVERE, "The two defined locations are not in the same world!");
            return;
        }

        setStablesConfig(getStableId().toString() + REGION_WORLD, world1.getName());
        setStablesConfig(getStableId().toString() + REGION_X_MIN, Math.min(loc1.getBlockX(), loc2.getBlockX()));
        setStablesConfig(getStableId().toString() + REGION_Z_MIN, Math.min(loc1.getBlockZ(), loc2.getBlockZ()));
        setStablesConfig(getStableId().toString() + REGION_X_MAX, Math.max(loc1.getBlockX(), loc2.getBlockX()));
        setStablesConfig(getStableId().toString() + REGION_Z_MAX, Math.max(loc1.getBlockZ(), loc2.getBlockZ()));
    }

    public UUID getStableId() {
        return stableId;
    }

    private ConfigManager getStablesConfig() {
        return stablesConfig;
    }

    private void setStablesConfig(String path, Object value) {
        getStablesConfig().set(path, value);
    }
}
