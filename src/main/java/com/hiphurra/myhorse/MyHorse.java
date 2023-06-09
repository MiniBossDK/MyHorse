package com.hiphurra.myhorse;

import com.hiphurra.myhorse.commands.CommandHandler;
import com.hiphurra.myhorse.enums.ConfigFile;
import com.hiphurra.myhorse.enums.NameType;
import com.hiphurra.myhorse.enums.Setting;
import com.hiphurra.myhorse.listeners.*;
import com.hiphurra.myhorse.managers.ConfigManager;
import com.hiphurra.myhorse.managers.EconomyManager;
import com.hiphurra.myhorse.managers.LanguageManager;
import com.hiphurra.myhorse.managers.PermissionManager;
import com.hiphurra.myhorse.stable.StableData;
import com.hiphurra.myhorse.stable.Stable;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class MyHorse extends JavaPlugin {

    List<ConfigManager> configManagers = new ArrayList<>();
    private final List<HorseOwner> horseOwners = new ArrayList<>();
    public final boolean defaultConfigExist = new File(getDataFolder(), "config.yml").exists();
    private final FileConfiguration defaultConfig = loadDefaultConfig();
    private final boolean isDebugging = getDefaultConfig().getBoolean("settings.debug", false);
    private final String language = getDefaultConfig().getString("settings.language", "en");
    private final LanguageManager languageManager = new LanguageManager(this, new Locale(language));
    private PermissionManager permissionManager;
    private EconomyManager economyManager;
    private final Map<Setting, Object> settings = new HashMap<>();

    @Override
    public void onEnable() {

        // Config
        loadDefaultConfig();
        loadSettings();
        initializeConfigFiles();

        try {
            loadConfigFiles();
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Something went wrong loading the config! Disables the plugin...");
        }
        if(!vaultExist()) {
            getLogger().log(Level.SEVERE, "Vault not found! Disables plugin...");
            getServer().getPluginManager().disablePlugin(this);
        }
        if(getPermissionPlugin() == null) {
            getLogger().log(Level.SEVERE, "Permissions plugin not found! Disables plugin...");
            getServer().getPluginManager().disablePlugin(this);
        }
        if(getChatPlugin() == null) {
            getLogger().log(Level.SEVERE, "Chat plugin not found! Disables plugin...");
            getServer().getPluginManager().disablePlugin(this);
        }
        if(getEconomyPlugin() == null) {
            getLogger().log(Level.SEVERE, "Economy plugin not found! Disables plugin...");
            getServer().getPluginManager().disablePlugin(this);
        }
        economyManager = new EconomyManager(this);
        permissionManager = new PermissionManager(this);

        // Commands
        try {
            PluginCommand myhorse = getCommand("myhorse");
            PluginCommand mh = getCommand("mh");
            if(myhorse != null && mh != null) {
                myhorse.setExecutor(new CommandHandler(this));
                mh.setExecutor(new CommandHandler(this));
            }
        } catch (InstantiationException | IllegalAccessException e) {
            getLogger().log(Level.SEVERE, "Couldn't register the commands! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
        }

        // Events
        getServer().getPluginManager().registerEvents(new MyHorseListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new EconomyListener(this), this);
        getServer().getPluginManager().registerEvents(new HorseDamageListener(this), this);
        getServer().getPluginManager().registerEvents(new StableListener(this), this);
    }

    @Override
    public void onDisable() {

    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public boolean vaultExist() {
        return getServer().getPluginManager().getPlugin("Vault") != null;
    }

    public @Nullable RegisteredServiceProvider<Permission> getPermissionPlugin() {
        return getServer().getServicesManager().getRegistration(Permission.class);
    }

    public @Nullable RegisteredServiceProvider<Chat> getChatPlugin() {
        return getServer().getServicesManager().getRegistration(Chat.class);
    }

    public @Nullable
    RegisteredServiceProvider<Economy> getEconomyPlugin() {
        return getServer().getServicesManager().getRegistration(Economy.class);
    }

    public void loadConfigFiles() throws IOException {
        for (ConfigManager configManager : configManagers) {
            configManager.loadConfig();
        }
    }

    private void initializeConfigFiles() {
        for (ConfigFile configFile : ConfigFile.values()) {
            configManagers.add(new ConfigManager(configFile, this));
        }
    }

    public FileConfiguration loadDefaultConfig() {
        if(!defaultConfigExist) saveResource("config.yml", false);
        return YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
    }

    public FileConfiguration getDefaultConfig() {
        return defaultConfig;
    }

    public ConfigManager getConfigManager(@NotNull ConfigFile configFile) {
        for (ConfigManager configManager : configManagers) {
            if(configManager.getConfigFile().equals(configFile)) {
                return configManager;
            }
        }
        return null;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }
    
    @SuppressWarnings("unchecked")
    public List<World> getAllowedWorlds() {
        Object setting = getSettings().get(Setting.ALLOWED_WORLDS);
        if(!(setting instanceof List)) {
            getLogger().log(Level.SEVERE, "World names is required to be a list! Disallowing all worlds...");
            return Collections.emptyList();
        }

        List<String> allowedWorldsAsStrings = (List<String>) setting;
        List<World> allowedWorlds = new ArrayList<>();
        for (String worldName : allowedWorldsAsStrings) {
            if(Bukkit.getWorld(worldName) == null) {
                getLogger().log(Level.WARNING,worldName + " is not a valid world! Ignoring it...");
                continue;
            }
            allowedWorlds.add(Bukkit.getWorld(worldName));
        }
        return allowedWorlds;
    }

    // FIXME - Does not load changed settings before full server restart
    public void loadSettings() {
        for (Setting setting : Setting.values()) {
            String settingName = setting.name().toLowerCase();
            if(!getDefaultConfig().contains("settings." + settingName))
            {
                getDefaultConfig().set("settings." + settingName, setting.getDefault());
                try {
                    getDefaultConfig().save(new File(getDataFolder(), "config.yml"));
                } catch (IOException exception) {
                    logDebug(Level.SEVERE, "Couldn't not save the default config file!");
                }
            }
            settings.put(setting, getDefaultConfig().get("settings." + settingName));
        }
    }

    public Map<Setting, Object> getSettings() {
        return settings;
    }

    public List<HorseOwner> getHorseOwners() {
        return horseOwners;
    }

    public String getRandomPlaceholderName(NameType type, List<String> ownedNames) {
        List<String> names;
        List<String> availableNames;
        if(type.equals(NameType.HORSE)) {
            availableNames = getHorsePlaceholderNames().stream().filter(name -> !ownedNames.contains(name)).collect(Collectors.toList());
            names = getHorsePlaceholderNames();
        } else {
            availableNames = getStablePlaceholderNames().stream().filter(name -> !ownedNames.contains(name)).collect(Collectors.toList());
            names = getStablePlaceholderNames();
        }
        if(availableNames.isEmpty()) return names.get(0).concat("1");
        return names.get((int) (Math.random() * names.size()));
    }

    private List<String> getStablePlaceholderNames() {
        List<String> names = new ArrayList<>();
        try {
            InputStreamReader inputStreamReader = (InputStreamReader) getTextResource("stable-names.txt");
            if (inputStreamReader == null) return Collections.emptyList();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            while (reader.readLine() != null) {
                names.add(reader.readLine());
            }
            reader.close();
            return names;
        } catch (IOException exception) {
            logDebug(Level.SEVERE, "Could not read stable placeholder names");
        }
        return Collections.emptyList();
    }

    private List<String> getHorsePlaceholderNames() {
        List<String> names = new ArrayList<>();
        try {
            InputStreamReader inputStreamReader = (InputStreamReader) getTextResource("names.txt");
            if (inputStreamReader == null) return Collections.emptyList();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            while (reader.readLine() != null) {
                names.add(reader.readLine());
            }
            reader.close();
            return names;
        } catch (IOException exception) {
            logDebug(Level.SEVERE, "Could not read horse placeholder names");
        }
        return Collections.emptyList();
    }

    public static String getNMSVersion() {
        String v = Bukkit.getServer().getClass().getPackage().getName();
        return v.substring(v.lastIndexOf('.') + 1);
    }

    public Set<Stable> getStableRegions() {
        Set<UUID> stableIds = getConfigManager(ConfigFile.STABLES).getKeys(false)
                .stream()
                .map(UUID::fromString)
                .collect(Collectors.toSet());
        return stableIds.stream().map(uuid -> new StableData(this, uuid).getRegion()).collect(Collectors.toSet());
    }

    public Set<OwnerData> getAllOwners() {
        Set<UUID> ownerIds = getConfigManager(ConfigFile.OWNERS).getKeys(false)
                .stream()
                .map(UUID::fromString)
                .collect(Collectors.toSet());
        return ownerIds.stream().map(uuid -> new OwnerData(this, uuid)).collect(Collectors.toSet());
    }

    public Set<HorseData> getAllHorses() {
        Set<UUID> horseIds = getConfigManager(ConfigFile.HORSES).getKeys(false)
                .stream()
                .map(UUID::fromString)
                .collect(Collectors.toSet());
        return horseIds.stream().map(uuid -> new HorseData(this, uuid)).collect(Collectors.toSet());
    }

    public void logDebug(Level level, String message) {
        if (isDebugging) getLogger().log(level, message);
    }
}
