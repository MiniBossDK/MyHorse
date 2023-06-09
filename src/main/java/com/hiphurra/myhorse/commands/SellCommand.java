package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.HorseData;
import com.hiphurra.myhorse.HorseUtils;
import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.OwnerData;
import com.hiphurra.myhorse.builders.Message;
import com.hiphurra.myhorse.enums.LanguageString;
import com.hiphurra.myhorse.enums.PermissionNode;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

public class SellCommand implements Command {
    @Override
    public boolean execute(MyHorse plugin, CommandSender sender, String... args) {

        Player player = (Player) sender;
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        if(args.length == 1)
        {
            player.sendMessage(getUsage());
            return true;
        }
        for (Entry<UUID, String> horseEntry : ownerData.getHorses().entrySet()) {
            if(horseEntry.getValue().equalsIgnoreCase(args[1])) {
                HorseData horseData = new HorseData(plugin, horseEntry.getKey());
                AbstractHorse abstractHorse = HorseUtils.findHorse(horseData);
                if(args.length == 2) {
                    player.sendMessage("Horse needs a price to set it on sale");
                    return true;
                }
                if(args[2].equalsIgnoreCase("cancel")) {
                    if(horseData.isForSale()) {
                        horseData.cancelSale();
                        abstractHorse.setCustomName(horseData.getName());
                        Message message = new Message.MessageBuilder(plugin, LanguageString.YouCancelledHorseForSale)
                                            .setHorseName(horseData.getName())
                                            .build();
                        message.sendMessage(player);
                    } else {
                        Message message = new Message.MessageBuilder(plugin, LanguageString.NoPriceOnHorse)
                                .setHorseName(horseData.getName())
                                .setUsage(getUsage())
                                .build();
                        message.sendMessage(player);
                    }
                    return true;
                }
                try {
                    double sellingPrice = Double.parseDouble(args[2]);
                    horseData.setForSale(sellingPrice);
                    abstractHorse.setCustomName(ChatColor.GOLD + horseData.getName() + ChatColor.RED + " " + plugin.getEconomyManager().format(sellingPrice));
                    Message message = new Message.MessageBuilder(plugin, LanguageString.YouSetHorseForSale)
                            .setHorseName(horseData.getName())
                            .setEconomyAmount(sellingPrice)
                            .build();
                    message.sendMessage(player);
                    message = new Message.MessageBuilder(plugin, LanguageString.InfoCancelHorseSale)
                            .setUsage(getUsage())
                            .build();
                    message.sendMessage(player);
                    return true;
                } catch (NumberFormatException exception) {
                    // TODO - Use MessageBuilder
                    player.sendMessage(args[2] + " is not a number!");
                    return true;
                } catch (NullPointerException exception) {
                    // TODO - Use MessageBuilder
                    player.sendMessage("Horse needs a price to set it on sale");
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

    @Override
    public List<String> tab(MyHorse plugin, Player player, String... args) {
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        if(args.length == 2) {
            return ownerData.getHorses().values().stream().filter(name -> name.toLowerCase().contains(args[1].toLowerCase())).collect(Collectors.toList());
        }
        if(args.length == 3) {
            for (Entry<UUID, String> horseEntry : ownerData.getHorses().entrySet()) {
                if (args[1].equalsIgnoreCase(horseEntry.getValue())) {
                    HorseData horseData = new HorseData(plugin, horseEntry.getKey());
                    if (horseData.isForSale()) {
                        return new ArrayList<>(Collections.singleton("cancel"));
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    @NotNull
    @Override
    public String getName() {
        return "sell";
    }

    @NotNull
    @Override
    public String getUsage() {
        return "/myhorse sell <horse_name> <price>";
    }

    @NotNull
    @Override
    public PermissionNode getPermission() {
        return PermissionNode.SELL;
    }
}
