package com.hiphurra.myhorse.managers;

import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.enums.LanguageString;
import com.hiphurra.myhorse.enums.MessageType;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

public class LanguageManager {

    private final MyHorse plugin;
    private final Locale locale;
    private FileConfiguration languageConfig = null;

    private Number amount;
    private String playerName;
    private String horseName;
    private double amountEconomy;
    private String usage;
    private EntityDamageEvent.DamageCause damageCause;
    private World world;


    private final static String PLAYER_NAME = "$PlayerName";
    private final static String NAME = "$Name";
    private final static String AMOUNT = "$Amount";
    private final static String AMOUNT_ECONOMY = "$Economy";
    private final static String USAGE = "$Usage";
    private final static String DAMAGE_CAUSE = "$DamageCause";
    private final static String WORLD = "$World";


    public LanguageManager(MyHorse plugin, Locale locale) {
        this.locale = locale;
        this.plugin = plugin;
        loadLanguageConfig();
    }

    private File downloadLanguageFile(Locale locale) throws IOException {
        BufferedInputStream in = new BufferedInputStream(
                new URL("https://raw.githubusercontent.com/DogOnFire/MyHorse/master/lang/" + locale.getDisplayLanguage().toLowerCase() + ".yml").openStream());

        FileOutputStream fileOutputStream = new FileOutputStream(this.plugin.getDataFolder() + "/lang/" + locale.getDisplayLanguage().toLowerCase() + ".yml");
        BufferedOutputStream bout = new BufferedOutputStream(fileOutputStream, 1024);
        byte[] data = new byte[1024];

        int x;
        while ((x = in.read(data, 0, 1024)) >= 0) {
            bout.write(data, 0, x);
        }
        bout.close();

        in.close();
        return new File(this.plugin.getDataFolder() + "/lang/" + locale.getDisplayLanguage().toLowerCase() + ".yml");
    }

    public void loadLanguageConfig() {

        File languageConfigFile = new File(this.plugin.getDataFolder() + "/lang/" + getLocale().getDisplayLanguage().toLowerCase() + ".yml");
        if(!languageConfigFile.exists()) {
            try {
                languageConfigFile = downloadLanguageFile(getLocale());
            } catch (IOException exception) {
                plugin.logDebug(Level.SEVERE, "Could not download " + getLocale().getDisplayLanguage() + ".yml!");
            }
        }
        languageConfig = YamlConfiguration.loadConfiguration(languageConfigFile);
    }



    public String parseString(String id, MessageType messageType) {
        String string = messageType.getChatColor() + id.replaceAll("&([0-9a-f])", "§$1");

        if (string.contains(NAME)) {
            string = string.replace(NAME, ChatColor.GOLD + getHorseName() + messageType.getChatColor());
        }
        if (string.contains(PLAYER_NAME)) {
            string = string.replace(PLAYER_NAME, ChatColor.GOLD + getPlayerName() + messageType.getChatColor());
        }
        if (string.contains(AMOUNT)) {
            if((amount.doubleValue() % 1) == 0) string = string.replace(AMOUNT, ChatColor.GOLD + Integer.toString(amount.intValue()) + messageType.getChatColor());
            else string = string.replace(AMOUNT, ChatColor.GOLD + Double.toString(getAmount().doubleValue()) + messageType.getChatColor());
        }
        if(string.contains(AMOUNT_ECONOMY)) {
            string = string.replace(AMOUNT_ECONOMY, ChatColor.GOLD + plugin.getEconomyManager().format(getAmountEconomy()) + messageType.getChatColor());
        }
        if(string.contains(USAGE)) {
            string = string.replace(USAGE, ChatColor.GOLD + getUsage()  + messageType.getChatColor());
        }
        if(string.contains(DAMAGE_CAUSE)) {
            string = string.replace(DAMAGE_CAUSE, ChatColor.GOLD + getDamageCause().name() + messageType.getChatColor());
        }
        if(string.contains(WORLD)) {
            string = string.replace(WORLD, ChatColor.GOLD + getWorld().getName() + messageType.getChatColor());
        }
        return string;
    }

    public String getLanguageString(LanguageString languageString) {
        List<String> strings = languageConfig.getStringList(languageString.name());
        if (strings.size() == 0) {
            this.plugin.logDebug(Level.WARNING,"No language strings found for " + languageString.name() + "!");
            return languageString.name() + " MISSING";
        }

        return parseString((String) strings.toArray()[(int) (Math.random() * strings.size())], languageString.getMessageType());
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setDamageCause(EntityDamageEvent.DamageCause damageCause) {
        this.damageCause = damageCause;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void setAmountEconomy(double amount) {
        this.amountEconomy = amount;
    }

    public void setAmount(Number amount) {
        this.amount = amount;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public World getWorld() {
        return world;
    }

    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
    }

    public String getUsage() {
        return usage;
    }

    public double getAmountEconomy() {
        return amountEconomy;
    }

    public Number getAmount() {
        return amount;
    }

    public String getHorseName() {
        return horseName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Locale getLocale() {
        return locale;
    }
}
