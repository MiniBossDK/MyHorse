package com.hiphurra.myhorse.stable;

import com.hiphurra.myhorse.managers.YamlDataFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class StableData {

    private final YamlDataFile stablesConfig;

    private final static String OWNER = ".owner";
    private final static String NAME = ".name";
    private final static String REGION = ".region";
        private final static String REGION_WORLD = REGION + ".world";
        private final static String REGION_X_MIN = REGION + ".Xmin";
        private final static String REGION_Z_MIN = REGION + ".Zmin";
        private final static String REGION_X_MAX = REGION + ".Xmax";
        private final static String REGION_Z_MAX = REGION + ".Zmax";

    public StableData() {
        this.stablesConfig = plugin.getConfigManager(com.hiphurra.myhorse.enums.YamlFile.STABLES);
    }

    public String getName(UUID stableId) {
        return getStablesConfig().getString(stableId.toString() + NAME);
    }

    public void setName(UUID stableId, String name) {
        setStablesConfig(stableId.toString() + NAME, name);
    }

    public void setOwner(UUID stableId, UUID owner) {
        setStablesConfig(stableId.toString() + OWNER, owner.toString());
    }

    public UUID getOwner(UUID stableId) {
        return UUID.fromString(getStablesConfig().getString(stableId.toString() + OWNER));
    }

    public Stable getRegion(UUID stableId) {
        return new Stable(
            getOwner(stableId),
            getName(stableId),
            Bukkit.getWorld(getStablesConfig().getString(stableId.toString() + REGION_WORLD)),
            getStablesConfig().getDouble(stableId.toString() + REGION_X_MIN),
            getStablesConfig().getDouble(stableId.toString() + REGION_Z_MIN),
            getStablesConfig().getDouble(stableId.toString() + REGION_X_MAX),
            getStablesConfig().getDouble(stableId.toString() + REGION_Z_MAX)
        );
    }

    public void setRegion(UUID stableId, World world, double x_min, double z_min, double x_max, double z_max) {
        setStablesConfig(stableId.toString() + REGION_WORLD, world.getName());
        setStablesConfig(stableId.toString() + REGION_X_MIN, Math.min(x_min, x_max));
        setStablesConfig(stableId.toString() + REGION_Z_MIN, Math.min(z_min, z_max));
        setStablesConfig(stableId.toString() + REGION_X_MAX, Math.max(x_min, x_max));
        setStablesConfig(stableId.toString() + REGION_Z_MAX, Math.max(z_min, z_max));
    }

    public void setRegion(UUID stableId, Location loc1, Location loc2) {
        World world1 = loc1.getWorld();
        World world2 = loc2.getWorld();
        if(world1 == null || world2 == null) return;

        if(!world1.equals(world2)) return;

        setStablesConfig(stableId.toString() + REGION_WORLD, world1.getName());
        setStablesConfig(stableId.toString() + REGION_X_MIN, Math.min(loc1.getBlockX(), loc2.getBlockX()));
        setStablesConfig(stableId.toString() + REGION_Z_MIN, Math.min(loc1.getBlockZ(), loc2.getBlockZ()));
        setStablesConfig(stableId.toString() + REGION_X_MAX, Math.max(loc1.getBlockX(), loc2.getBlockX()));
        setStablesConfig(stableId.toString() + REGION_Z_MAX, Math.max(loc1.getBlockZ(), loc2.getBlockZ()));
    }

    private YamlDataFile getStablesConfig() {
        return stablesConfig;
    }

    private void setStablesConfig(String path, Object value) {
        getStablesConfig().set(path, value);
    }
}
