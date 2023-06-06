package com.hiphurra.myhorse.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.OwnerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

@CommandAlias("owner openinventory")
@Description("Command for horse owner related issues")
@CommandPermission("myhorse.admin")
public class OpenInventoryCommand extends BaseCommand {

    @Dependency
    private MyHorse plugin;

    @HelpCommand
    public void doHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("horses")
    @Syntax("<player>")
    @CommandCompletion("@player")
    public void onHorsesList(CommandSender sender, @Single String name) {
        OfflinePlayer[] players = Bukkit.getOfflinePlayers();
        UUID playerId = null;
        for (OfflinePlayer offlinePlayer : players) {
            if(offlinePlayer.getName() == null) continue;
            if(offlinePlayer.getName().equalsIgnoreCase(name)) {
                playerId = offlinePlayer.getUniqueId();
                break;
            }
        }
        if(playerId == null) return;
        OwnerData ownerData = new OwnerData(plugin, playerId);

    }
}
