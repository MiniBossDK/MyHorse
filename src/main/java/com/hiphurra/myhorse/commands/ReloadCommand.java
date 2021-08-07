package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.enums.PermissionNode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class ReloadCommand implements Command {
    @Override
    public boolean execute(MyHorse plugin, CommandSender sender, String... args) {
        try {
            plugin.loadConfigFiles();
            plugin.loadDefaultConfig();
            plugin.loadSettings();
            plugin.getLanguageManager().loadLanguageConfig();
            plugin.logDebug(Level.INFO, "Configs has been reloaded!");
        } catch (IOException e) {
            plugin.logDebug(Level.SEVERE, "Couldn't reload config files!");
        }

        return true;
    }

    @Override
    public List<String> tab(MyHorse myHorse, Player player, String... args) {
        return Collections.emptyList();
    }

    @Override
    public @NotNull String getName() {
        return "reload";
    }

    @Override
    public @NotNull String getUsage() {
        return "/mh reload";
    }

    @NotNull
    @Override
    public PermissionNode getPermission() {
        return PermissionNode.RELOAD;
    }
}
