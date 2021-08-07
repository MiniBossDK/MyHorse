package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.HorseData;
import com.hiphurra.myhorse.HorseUtils;
import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.OwnerData;
import com.hiphurra.myhorse.builders.Message;
import com.hiphurra.myhorse.enums.LanguageString;
import com.hiphurra.myhorse.enums.PermissionNode;
import com.hiphurra.myhorse.enums.Setting;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ComeHereCommand implements Command {

    @Override
    public boolean execute(MyHorse plugin, CommandSender sender, String... args) {
        Player player = (Player) sender;
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        if(!plugin.getAllowedWorlds().contains(player.getWorld())) {
            Message message = new Message.MessageBuilder(plugin, LanguageString.HorseNotAllowedInWorld).build();
            message.sendMessage(player);
            return true;
        }
        if(args.length == 2) {
            for (UUID tamedHorseId : ownerData.getHorses().keySet()) {
                if(ownerData.getHorses().get(tamedHorseId).equalsIgnoreCase(args[1])) {
                    HorseData horseData = new HorseData(plugin, tamedHorseId);
                    AbstractHorse horse = HorseUtils.findHorse(horseData);
                    if(horse != null) {
                        horse.teleport(player.getLocation());
                        Message message = new Message.MessageBuilder(plugin, LanguageString.ComeHere)
                                .setHorseName(horseData.getName())
                                .build();
                        message.sendMessage(player);
                        return true;
                    }
                }
            }
            Message message = new Message.MessageBuilder(plugin, LanguageString.NoHorseWithSuchName)
                    .setHorseName(args[1])
                    .build();
            message.sendMessage(player);
            return true;
        }

        if(ownerData.getCurrentHorseIdentifier() == null) {
            player.sendMessage("No horse has been selected!");
            return false;
        }
        HorseData horseData = new HorseData(plugin, ownerData.getCurrentHorseIdentifier());


        Location location = horseData.getHorseLocation();
        if(location != null) {
            AbstractHorse horse = HorseUtils.findHorse(horseData);
            if(horse != null) {
                horse.teleport(player.getLocation());
                Message message = new Message.MessageBuilder(plugin, LanguageString.ComeHere)
                        .setHorseName(horseData.getName())
                        .build();
                message.sendMessage(player);
            }
        }

        return true;
    }

    @Override
    public List<String> tab(MyHorse plugin, Player player, String... args) {
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        if(args.length == 2) return ownerData.getHorses().values().stream().filter(name -> name.toLowerCase().contains(args[1].toLowerCase())).collect(Collectors.toList());
        return Collections.emptyList();
    }

    @Override
    public @NotNull String getName() {
        return "comehere";
    }

    @Override
    public @NotNull String getUsage() {
        return "/mh comehere |  /mh comehere (<name_of_horse>)";
    }

    @NotNull
    @Override
    public PermissionNode getPermission() {
        return PermissionNode.COMEHERE;
    }
}
