package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.*;
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
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

public class TrustCommand implements Command {
    @Override
    public boolean execute(MyHorse plugin, CommandSender sender, String... args) {
        Player player = (Player) sender;

        switch (args.length) {
            case 2:
                return new FromSelectedHorseSubCommand(plugin, player, args).execute();
            case 3:
                return new FromHorseNameSubCommand(plugin, player, args).execute();
            default:
                player.sendMessage(ChatColor.AQUA + getUsage());
                return true;
        }
    }

    @Override
    public List<String> tab(MyHorse plugin, Player player, String... args) {
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        if(args.length == 2) {
            return Bukkit.getOnlinePlayers().stream()
                    .filter(onlinePlayer -> !onlinePlayer.getUniqueId().equals(player.getUniqueId()))
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }
        if(args.length == 3) {
            List<String> options = new ArrayList<>();
            options.add("*");
            options.addAll(ownerData.getHorses().values());
            return options;
        }
        return Command.super.tab(plugin, player, args);
    }

    @Override
    public @NotNull String getName() {
        return "trust";
    }

    @Override
    public @NotNull String getUsage() {
        return "/mh trust <player_name> | /mh trust <player_name> <horse_name>" ;
    }

    @NotNull
    @Override
    public PermissionNode getPermission() {
        return PermissionNode.ADDFRIEND;
    }

    private static class FromSelectedHorseSubCommand {

        private final MyHorse plugin;
        private final Player player;
        private final String[] args;

        public FromSelectedHorseSubCommand(MyHorse plugin, Player player, String[] args) {
            this.plugin = plugin;
            this.player = player;
            this.args = args;
        }

        public boolean execute() {
            OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
            HorseData horseData = new HorseData(plugin, ownerData.getCurrentHorseIdentifier());
            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                if(offlinePlayer.getName() == null) continue;
                if(offlinePlayer.getName().equalsIgnoreCase(args[1])) {
                    if(horseData.isTrusted(offlinePlayer.getUniqueId())) {
                        Message message = new Message.MessageBuilder(plugin, LanguageString.AlreadyHorseFriend)
                                .setHorseName(horseData.getName())
                                .setPlayerName(offlinePlayer.getName())
                                .build();
                        message.sendMessage(player);
                        return true;
                    }
                    horseData.addTrustedPlayer(offlinePlayer.getUniqueId());
                    Message message = new Message.MessageBuilder(plugin, LanguageString.YouAddedFriendToHorse)
                            .setHorseName(horseData.getName())
                            .setPlayerName(offlinePlayer.getName())
                            .build();
                    message.sendMessage(player);
                    return true;
                }
            }
            Message message = new Message.MessageBuilder(plugin, LanguageString.PlayerNeverPlayedOnTheServer)
                    .setPlayerName(args[1])
                    .build();
            message.sendMessage(player);
            return true;
        }
    }

    private static class FromHorseNameSubCommand {

        private final MyHorse plugin;
        private final Player player;
        private final String[] args;

        public FromHorseNameSubCommand(MyHorse plugin, Player player, String[] args) {
            this.plugin = plugin;
            this.player = player;
            this.args = args;
        }

        public boolean execute() {
            OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
            if(args[2].equalsIgnoreCase("*")) {
                for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                    if(offlinePlayer.getName() == null) continue;
                    if (offlinePlayer.getName().equalsIgnoreCase(args[1])) {
                        ownerData.addTrustOnAllHorses(offlinePlayer.getUniqueId());
                        Message message = new Message.MessageBuilder(plugin, LanguageString.YouTrustedPlayerOnAllHorses)
                                .setPlayerName(offlinePlayer.getName())
                                .build();
                        message.sendMessage(player);
                        return true;
                    }
                }
            }
            for (Entry<UUID, String> horseEntry : ownerData.getHorses().entrySet()) {
                if(horseEntry.getValue().equalsIgnoreCase(args[2])) {
                    HorseData horseData = new HorseData(plugin, horseEntry.getKey());
                    for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                        if(offlinePlayer.getName() == null) continue;
                        if (offlinePlayer.getName().equalsIgnoreCase(args[1])) {
                            if (horseData.isTrusted(offlinePlayer.getUniqueId())) {
                                Message message = new Message.MessageBuilder(plugin, LanguageString.AlreadyHorseFriend)
                                        .setHorseName(horseData.getName())
                                        .setPlayerName(offlinePlayer.getName())
                                        .build();
                                message.sendMessage(player);
                                return true;
                            }
                            horseData.addTrustedPlayer(offlinePlayer.getUniqueId());
                            Message message = new Message.MessageBuilder(plugin, LanguageString.YouAddedFriendToHorse)
                                    .setHorseName(horseData.getName())
                                    .setPlayerName(offlinePlayer.getName())
                                    .build();
                            message.sendMessage(player);
                            return true;
                        }
                    }
                }
            }
            Message message = new Message.MessageBuilder(plugin, LanguageString.NoHorseWithSuchName)
                    .setHorseName(args[1])
                    .build();
            message.sendMessage(player);
            return true;
        }
    }
}
