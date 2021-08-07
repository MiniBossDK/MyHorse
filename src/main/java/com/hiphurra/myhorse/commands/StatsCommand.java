package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.*;
import com.hiphurra.myhorse.builders.Message;
import com.hiphurra.myhorse.enums.LanguageString;
import com.hiphurra.myhorse.enums.PermissionNode;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

// TODO - Make the message output of the command look nicer
public class StatsCommand implements Command {

    @Override
    public boolean execute(MyHorse plugin, CommandSender sender, String... args) {
        boolean horseFound = false;
        Player player = (Player) sender;
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        HorseData horseData = new HorseData(plugin, ownerData.getCurrentHorseIdentifier());
        if(args.length == 2) {
            for (Entry<UUID, String> horseEntry : ownerData.getHorses().entrySet()) {
                if (args[1].equalsIgnoreCase(horseEntry.getValue())) {
                    horseData = new HorseData(plugin, horseEntry.getKey());
                    horseFound = true;
                }
            }
            if(!horseFound) {
                Message message = new Message.MessageBuilder(plugin, LanguageString.NoHorseWithSuchName)
                        .setHorseName(args[1])
                        .build();
                message.sendMessage(player);
                return true;
            }
        }
        AbstractHorse horseEntity = HorseUtils.findHorse(horseData);
        player.sendMessage(ChatColor.YELLOW + "------------------ " + horseData.getName() + " ------------------");
        player.sendMessage(ChatColor.AQUA + "Type: " + ChatColor.WHITE + horseEntity.getType().name());
        if (horseEntity.getType() == EntityType.HORSE) {
            Horse horse = (Horse) horseEntity;
            player.sendMessage(ChatColor.AQUA + "Color: " + ChatColor.WHITE + horse.getColor().name());
        }
        player.sendMessage(ChatColor.AQUA + "Max Health: " + ChatColor.WHITE
                + horseEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.sendMessage(ChatColor.AQUA + "Jump Strength: " + ChatColor.WHITE
                + MathUtils.round(
                -0.1817584952 * Math.pow(horseEntity.getJumpStrength(), 3)
                        + 3.689713992 * Math.pow(horseEntity.getJumpStrength(), 2)
                        + 2.128599134 * horseEntity.getJumpStrength() - 0.343930367, 2) + " blocks");
        AttributeInstance attributeInstance = horseEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if(attributeInstance != null) player.sendMessage(ChatColor.AQUA + "Max Speed: " + ChatColor.WHITE + attributeInstance.getValue());
        if (horseData.getTrustedPlayers().size() > 0) {
            player.sendMessage(ChatColor.AQUA + "Friends: ");
            for (UUID friendId : horseData.getTrustedPlayers()) {
                player.sendMessage(ChatColor.WHITE + "- " + plugin.getServer().getOfflinePlayer(friendId).getName());
            }
        }
        return true;
    }

    @Override
    public List<String> tab(MyHorse plugin, Player player, String... args) {
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        if(args.length == 2) return new ArrayList<>( ownerData.getHorses().values() );
        return Collections.emptyList();
    }

    @Override
    public @NotNull String getName() {
        return "stats";
    }

    @Override
    public @NotNull String getUsage() {
        return "/mh stats <horse_name> | /mh stats";
    }

    @NotNull
    @Override
    public PermissionNode getPermission() {
        return PermissionNode.INFO;
    }
}
