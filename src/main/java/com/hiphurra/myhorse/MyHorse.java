package com.hiphurra.myhorse;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import com.hiphurra.myhorse.commands.CommandContext;
import com.hiphurra.myhorse.commands.CommandHandler;
import com.hiphurra.myhorse.enums.NameType;
import com.hiphurra.myhorse.enums.Setting;
import com.hiphurra.myhorse.enums.YamlFile;
import com.hiphurra.myhorse.listeners.*;
import com.hiphurra.myhorse.managers.EconomyManager;
import com.hiphurra.myhorse.managers.LanguageManager;
import com.hiphurra.myhorse.managers.PermissionManager;
import com.hiphurra.myhorse.managers.YamlDataFile;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class MyHorse extends JavaPlugin {

    Map<YamlFile, YamlDataFile> yamlDataFiles = new HashMap<>();
    private final List<AbstractHorseOwner> horseOwners = new ArrayList<>();
    public final boolean defaultConfigExist = new File(getDataFolder(), "config.yml").exists();
    private final FileConfiguration defaultConfig = loadDefaultConfig();
    private final boolean isDebugging = getDefaultConfig().getBoolean("settings.debug", false);
    private final String language = getDefaultConfig().getString("settings.language", "en");
    private final LanguageManager languageManager = new LanguageManager(this, new Locale(language));
    private PermissionManager permissionManager;
    private EconomyManager economyManager;
    private final Map<Setting, Object> settings = new HashMap<>();
    public PaperCommandManager commandManager;

    @Override
    public void onEnable() {

        ConfigurationSerialization.registerClass(SalesRecord.class);

        // Config
        loadDefaultConfig();
        try {
            loadSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            loadDataFiles();
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
            registerCommands();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

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
        getServer().getPluginManager().registerEvents(new HorsePermissionListener(this), this);
        getServer().getPluginManager().registerEvents(new StableListener(this), this);
    }

    @Override
    public void onDisable() {

    }

    public void registerCommands() throws InstantiationException, IllegalAccessException {
        commandManager = new PaperCommandManager(this);

        commandManager.registerDependency(YamlDataFile.class, "HorseDataFile", yamlDataFiles.get(YamlFile.HORSES));
        commandManager.registerDependency(YamlDataFile.class, "StableDataFile", yamlDataFiles.get(YamlFile.STABLES));
        commandManager.registerDependency(YamlDataFile.class, "HorseOwnerDataFile", yamlDataFiles.get(YamlFile.OWNERS));

        commandManager.enableUnstableAPI("brigadier");

        // 2: Setup some replacement values that may be used inside of the annotations dynamically.
        commandManager.getCommandReplacements().addReplacements(
                // key - value
                "test", "foobar",
                // key - demonstrate that % is ignored  - value
                "%foo", "barbaz");
        // Another replacement for piped values
        commandManager.getCommandReplacements().addReplacement("testcmd", "test4|foobar|barbaz");

        commandManager.getCommandContexts().registerContext(
                /* The class of the object to resolve*/
                CommandContext.class,
                /* A resolver method - Placed the resolver in its own class for organizational purposes */
                CommandContext.getContextResolver());

        commandManager.setDefaultExceptionHandler((command, registeredCommand, sender, args, t) -> {
            getLogger().warning("Error occurred while executing command " + command.getName());
            return false; // mark as unhandeled, sender will see default message
        });

        commandManager.getCommandCompletions().registerAsyncCompletion("", c ->
                Arrays.asList("1", "2")
        );

        Set<Class<?extends BaseCommand>> commands = new Reflections("com.hiphurra.myhorse.commands").getSubTypesOf(BaseCommand.class);
        for(Class<?extends BaseCommand> command : commands) {
            commandManager.registerCommand(command.newInstance());
        }

        commandManager.setDefaultExceptionHandler((command, registeredCommand, sender, args, t) -> {
            getLogger().warning("Error occurred while executing command " + command.getName());
            return false; // mark as unhandeled, sender will see default message
        });
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

    public void loadDataFiles() throws IOException {
        for (YamlFile yamlFile : YamlFile.values()) {
            yamlDataFiles.put(yamlFile, new YamlDataFile(new File(getDataFolder(), yamlFile.getFileName())));
        }
    }

    public FileConfiguration loadDefaultConfig() {
        if(!defaultConfigExist) saveResource("config.yml", false);
        return YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
    }

    public FileConfiguration getDefaultConfig() {
        return defaultConfig;
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

    public void loadSettings() throws IOException {
        for (Setting setting : Setting.values()) {
            String settingName = setting.name().toLowerCase();
            if(!getDefaultConfig().contains("settings." + settingName)) {
                getDefaultConfig().set("settings." + settingName, setting.getDefault());
                getDefaultConfig().save(new File(getDataFolder(), "config.yml"));
            }
            settings.put(setting, defaultConfig.get("settings." + settingName));
        }
    }

    public Map<Setting, Object> getSettings() {
        return settings;
    }

    public List<AbstractHorseOwner> getHorseOwners() {
        return horseOwners;
    }

    public String getRandomPlaceholderName(NameType type, List<String> ownedNames) throws IOException {
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

    private List<String> getStablePlaceholderNames() throws IOException {
        return readFromTextResourceFile("stable-names.txt");
    }

    private List<String> getHorsePlaceholderNames() throws IOException {
        return readFromTextResourceFile("horse-names.txt");
    }

    private List<String> readFromTextResourceFile(String filename) throws IOException {
        List<String> textLines = new ArrayList<>();
        InputStreamReader inputStreamReader = (InputStreamReader) getTextResource(filename);
        if (inputStreamReader == null) return Collections.emptyList();
        BufferedReader reader = new BufferedReader(inputStreamReader);
        while (reader.readLine() != null) {
            textLines.add(reader.readLine());
        }
        reader.close();
        return textLines;
    }

    public static String getNMSVersion() {
        String v = Bukkit.getServer().getClass().getPackage().getName();
        return v.substring(v.lastIndexOf('.') + 1);
    }

    public void logDebug(Level level, String message) {
        if (isDebugging) getLogger().log(level, message);
    }
}
