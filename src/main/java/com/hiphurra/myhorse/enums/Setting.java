package com.hiphurra.myhorse.enums;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.stream.Collectors;

public enum Setting {
    STORAGE_SOLUTION("sqlite"),
    LANGUAGE("en"),
    ALLOW_HORSE_TELEPORTATION(false),
    ALLOW_CHEST_ON_ALL_HORSES(false),
    ALWAYS_SHOW_HORSE_NAME(true),
    INVULNERABLE_HORSES(false),
    GOTO_COMMAND_PRICE(0.0),
    SETNAME_COMMAND_PRICE(0.0),
    CHEST_PLACEMENT_PRICE(0.0),
    ALLOWED_WORLDS(Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList())),
    REALISTIC_HORSES(false),
    DEBUG(false);

    private final Object defaultValue;

    Setting(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Object getDefault() {
        return defaultValue;
    }
}
