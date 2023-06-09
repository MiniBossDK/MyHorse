package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.HorseData;
import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.OwnerData;
import com.hiphurra.myhorse.builders.Message;
import com.hiphurra.myhorse.enums.LanguageString;
import com.hiphurra.myhorse.enums.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
// TODO - Make sure this command also eject the player from the horse if they are riding it.
// TODO - Maybe also make a new command that ejects all players from horses.
public class RemoveTrustCommand implements Command {
    @Override
    public boolean execute(MyHorse plugin, CommandSender sender, String... args) {

        Player player = (Player) sender;
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        if(args.length == 3) {
            if(args[2].equalsIgnoreCase("*")) {
                for (UUID trustedPlayerId : ownerData.getAllTrustedPlayers()) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(trustedPlayerId);
                    if(offlinePlayer.getName() == null) continue;
                    if(offlinePlayer.getName().equalsIgnoreCase(args[1])) {
                        ownerData.removeTrustOnAllHorses(trustedPlayerId);
                        Message message = new Message.MessageBuilder(plugin, LanguageString.YouUntrustedPlayerOnAllHorses)
                                .setPlayerName(args[1])
                                .build();
                        message.sendMessage(player);
                        return true;
                    }
                }
                Message message = new Message.MessageBuilder(plugin, LanguageString.NotHorseFriendOnAnyHorse)
                        .setPlayerName(args[1])
                        .build();
                message.sendMessage(player);
                return true;
            }
            for (Entry<UUID, String> horseEntry : ownerData.getHorses().entrySet()) {
                if(horseEntry.getValue().equalsIgnoreCase(args[2])) {
                    HorseData horseData = new HorseData(plugin, horseEntry.getKey());
                    for (UUID trustedPlayerId : horseData.getTrustedPlayers()) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(trustedPlayerId);
                        if(offlinePlayer.getName() == null) continue;
                        if(offlinePlayer.getName().equalsIgnoreCase(args[1])) {
                            horseData.removeTrustedPlayer(trustedPlayerId);
                            Message message = new Message.MessageBuilder(plugin, LanguageString.YouRemovedFriendToHorse)
                                    .setHorseName(horseData.getName())
                                    .setPlayerName(args[1])
                                    .build();
                            message.sendMessage(player);
                            return true;
                        }
                    }
                    Message message = new Message.MessageBuilder(plugin, LanguageString.NotHorseFriendOnAnyHorse)
                            .setHorseName(horseData.getName())
                            .setPlayerName(args[2])
                            .build();
                    message.sendMessage(player);
                    return true;
                }
            }
            Message message = new Message.MessageBuilder(plugin, LanguageString.NoHorseWithSuchName)
                    .setHorseName(args[1])
                    .build();
            message.sendMessage(player);
            return true;
        }
        player.sendMessage(ChatColor.AQUA + getUsage());
        return true;
    }

    @Override
    public List<String> tab(MyHorse plugin, Player player, String... args) {
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        UUID playerId = null;
        List<String> friendNames = new ArrayList<>();
        if(args.length == 2) {
            return ownerData.getAllTrustedPlayers()
                    .stream()
                    .map(uuid -> Bukkit.getOfflinePlayer(uuid).getName())
                    .collect(Collectors.toList());
        }
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if(offlinePlayer.getName() == null) continue;
            if(offlinePlayer.getName().equalsIgnoreCase(args[1])) {
                playerId = offlinePlayer.getUniqueId();
            }
        }
        if(args.length == 3) {
            friendNames.add("*");
            for (Entry<UUID, String> horseEntry : ownerData.getHorses().entrySet()) {
                HorseData horseData = new HorseData(plugin, horseEntry.getKey());
                if (playerId != null) {
                    if(horseData.isTrusted(playerId)) {
                        friendNames.add(horseData.getName());
                    }
                }
            }
            return friendNames;
        }
        return Collections.emptyList();
    }

    @Override
    public @NotNull String getName() {
        return "untrust";
    }

    @Override
    public @NotNull String getUsage() {
        return "/mh untrust <player_name> <horse_name>";
    }

    @NotNull
    @Override
    public PermissionNode getPermission() {
        return PermissionNode.REMOVEFRIEND;
    }
}
