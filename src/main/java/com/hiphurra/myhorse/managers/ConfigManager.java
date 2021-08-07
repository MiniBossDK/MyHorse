package com.hiphurra.myhorse.managers;

import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.enums.ConfigFile;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class ConfigManager {

    private FileConfiguration config;
    private final File file;
    private final ConfigFile configFile;
    private final MyHorse plugin;

    public ConfigManager(ConfigFile configFile, MyHorse plugin) {
        this.configFile = configFile;
        this.plugin = plugin;
        this.file = new File(this.plugin.getDataFolder(), configFile.getName());
    }

    public void loadConfig() throws IllegalArgumentException {

        if (!file.exists()) plugin.logDebug(Level.INFO,configFile.getName() + " does not exist. Creating it...");
        config = YamlConfiguration.loadConfiguration(file);
        save();
    }

    public String getFileName() {
        return configFile.getName();
    }

    public Object get(String path) {
        return getConfig().get(path);
    }

    public String getString(String path) {
        return getConfig().getString(path);
    }

    public boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }

    public List<String> getStringList(String path) {
        return getConfig().getStringList(path);
    }

    public Set<String> getKeys(boolean deep) {
        return getConfig().getKeys(deep);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return getConfig().getConfigurationSection(path);
    }

    public Location getLocation(String path) {
        return getConfig().getLocation(path);
    }

    public int getInt(String path) {
        return getConfig().getInt(path);
    }

    public double getDouble(String path) {
        return getConfig().getDouble(path);
    }

    public boolean contains(String path) {
        return getConfig().contains(path);
    }

    public void set(@NotNull String path, @Nullable Object value) {
        getConfig().set(path, value);
        save();
    }

    private void save() {
        try {
            getConfig().save(file);
        } catch (IOException e) {
            plugin.logDebug(Level.SEVERE, config.getName() + " was not able to save!");
        }
    }

    private FileConfiguration getConfig() {
        return config;
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }
}
