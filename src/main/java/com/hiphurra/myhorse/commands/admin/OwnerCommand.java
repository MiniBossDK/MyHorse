package com.hiphurra.myhorse.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import com.hiphurra.myhorse.AbstractHorseOwner;
import com.hiphurra.myhorse.HorseOwner;
import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.OwnerData;
import com.hiphurra.myhorse.customhorses.CustomHorse;
import com.hiphurra.myhorse.managers.YamlDataFile;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Horse;

import java.util.UUID;

@CommandAlias("owner")
@Description("Command for horse owner related issues")
@CommandPermission("myhorse.admin")
public class OwnerCommand extends BaseCommand {

    @Dependency
    private MyHorse plugin;

    @Dependency("HorseDataFile")
    private YamlDataFile horseDataFile;

    @HelpCommand
    public void doHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("horses")
    @Syntax("<player>")
    @CommandCompletion("@player")
    public void onHorsesList(CommandSender sender, @Single String name) {
        CustomHorse customHorse = new CustomHorse(UUID.randomUUID());
        horseDataFile.getAbstractHorseData(UUID.randomUUID()).getId();
        customHorse.extractEntityInformation((Horse) horse);
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

        HorseOwner horseOwner = new HorseOwner(UUID.randomUUID());

        OwnerData ownerData = new OwnerData(plugin, playerId);

    }
}
