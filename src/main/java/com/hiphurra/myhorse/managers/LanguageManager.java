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
    // TODO - This long gone from working, so maybe delete it down the road
    private void downloadLanguageFile(Locale locale) throws IOException {
        BufferedInputStream in = new BufferedInputStream(
                new URL("https://raw.githubusercontent.com/DogOnFire/MyHorse/master/lang/" + locale.getDisplayLanguage().toLowerCase()).openStream());

        FileOutputStream fileOutputStream = new FileOutputStream(this.plugin.getDataFolder() + "/lang/" + locale.getDisplayLanguage().toLowerCase() + ".yml");

        BufferedOutputStream bout = new BufferedOutputStream(fileOutputStream, 1024);

        byte[] data = new byte[1024];

        int x;
        while ((x = in.read(data, 0, 1024)) >= 0) {
            bout.write(data, 0, x);
        }
        bout.close();

        in.close();
    }

    public void loadLanguageConfig() {

        File languageConfigFile = new File(this.plugin.getDataFolder() + "/lang/" + locale.getDisplayLanguage().toLowerCase() + ".yml");
        if(!languageConfigFile.exists()) {
            try {
                downloadLanguageFile(locale);
            } catch (IOException exception) {
                plugin.logDebug(Level.SEVERE, "Could not download " + locale.getDisplayLanguage() + ".yml!");
            }
        }
        languageConfig = YamlConfiguration.loadConfiguration(languageConfigFile);
    }



    public String parseString(String id, MessageType messageType) {
        String message = messageType.getChatColor() + id.replaceAll("&([0-9a-f])", "§$1");
        if (message.contains(NAME)) {
            message = message.replace(NAME, ChatColor.GOLD + getHorseName() + messageType.getChatColor());
        }
        if (message.contains(PLAYER_NAME)) {
            message = message.replace(PLAYER_NAME, ChatColor.GOLD + getPlayerName() + messageType.getChatColor());
        }
        if (message.contains(AMOUNT)) {
            if((amount.doubleValue() % 1) == 0) message = message.replace(AMOUNT, ChatColor.GOLD + Integer.toString(amount.intValue()) + messageType.getChatColor());
            else message = message.replace(AMOUNT, ChatColor.GOLD + Double.toString(amount.doubleValue()) + messageType.getChatColor());
        }
        if(message.contains(AMOUNT_ECONOMY)) {
            message = message.replace(AMOUNT_ECONOMY, ChatColor.GOLD + plugin.getEconomyManager().format(getAmountEconomy()) + messageType.getChatColor());
        }
        if(message.contains(USAGE)) {
            message = message.replace(USAGE, ChatColor.GOLD + getUsage()  + messageType.getChatColor());
        }
        if(message.contains(DAMAGE_CAUSE)) {
            message = message.replace(DAMAGE_CAUSE, ChatColor.GOLD + getDamageCause().name() + messageType.getChatColor());
        }
        if(message.contains(WORLD)) {
            message = message.replace(WORLD, ChatColor.GOLD + getWorld().getName() + messageType.getChatColor());
        }
        return message;
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
