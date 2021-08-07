package com.hiphurra.myhorse.managers;

import com.hiphurra.myhorse.MyHorse;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;

import java.util.logging.Level;

public class EconomyManager {

    private final MyHorse plugin;
    private Economy economyPlugin = null;

    public EconomyManager(MyHorse plugin) {
        economyPlugin = plugin.getEconomyPlugin().getProvider();
        plugin.logDebug(Level.INFO, "Using for Economy: " + plugin.getEconomyPlugin().getProvider().getName());
        this.plugin = plugin;
    }

    public boolean hasAmount(OfflinePlayer player, double amount) {
        return economyPlugin.has(player, amount);
    }

    public void withdrawAmount(OfflinePlayer player, double amount) {
        economyPlugin.withdrawPlayer(player, amount);
    }

    public String format(double amount) {
        return economyPlugin.format(amount);
    }

    public void depositAmount(OfflinePlayer player, double amount) {
        economyPlugin.depositPlayer(player, amount);
    }

}
