package com.hiphurra.myhorse.builders;

import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.enums.LanguageString;
import com.hiphurra.myhorse.managers.LanguageManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Message {

    private final LanguageString languageString;
    private final String horseName;
    private final String playerName;
    private final Number amount;
    private final double economyAmount;
    private final EntityDamageEvent.DamageCause damageCause;
    private final String usage;
    private final World world;
    private final MyHorse plugin;

    private Message(MessageBuilder messageBuilder) {
        this.languageString = messageBuilder.languageString;
        this.horseName = messageBuilder.horseName;
        this.playerName = messageBuilder.playerName;
        this.amount = messageBuilder.amount;
        this.plugin = messageBuilder.plugin;
        this.economyAmount = messageBuilder.economyAmount;
        this.usage = messageBuilder.usage;
        this.damageCause = messageBuilder.damageCause;
        this.world = messageBuilder.world;
    }

    public static class MessageBuilder {

        private final MyHorse plugin;
        private final LanguageString languageString;

        private String horseName;
        private String playerName;
        private Number amount;
        private double economyAmount;
        private String usage;
        private EntityDamageEvent.DamageCause damageCause;
        private World world;

        public MessageBuilder(MyHorse plugin, LanguageString languageString) {
                this.plugin = plugin;
                this.languageString = languageString;
        }

        public MessageBuilder setWorld(World world) {
            this.world = world;
            return this;
        }

        public MessageBuilder setUsage(String usage) {
            this.usage = usage;
            return this;
        }

        public MessageBuilder setPlayerName(String playerName) {
            this.playerName = playerName;
            return this;
        }

        public MessageBuilder setHorseName(String horseName) {
            this.horseName = horseName;
            return this;
        }

        public MessageBuilder setAmount(Number amount) {
            this.amount = amount;
            return this;
        }

        public MessageBuilder setEconomyAmount(double amount) {
            this.economyAmount = amount;
            return this;
        }

        public MessageBuilder setDamageCause(EntityDamageEvent.DamageCause damageCause) {
            this.damageCause = damageCause;
            return this;
        }

        public Message build() {
            return new Message(this);
        }

    }

    public void sendMessage(Player player) {
        LanguageManager languageManager = plugin.getLanguageManager();
        languageManager.setUsage(this.usage);
        languageManager.setAmountEconomy(this.economyAmount);
        languageManager.setAmount(this.amount);
        languageManager.setHorseName(this.horseName);
        languageManager.setPlayerName(this.playerName);
        languageManager.setDamageCause(this.damageCause);
        languageManager.setWorld(this.world);

        player.sendMessage(languageManager.getLanguageString(this.languageString));
    }

    public String getMessage() {
        LanguageManager languageManager = plugin.getLanguageManager();
        languageManager.setUsage(this.usage);
        languageManager.setAmountEconomy(this.economyAmount);
        languageManager.setAmount(this.amount);
        languageManager.setHorseName(this.horseName);
        languageManager.setPlayerName(this.playerName);
        languageManager.setDamageCause(this.damageCause);
        languageManager.setWorld(this.world);
        return languageManager.getLanguageString(this.languageString);
    }

}
