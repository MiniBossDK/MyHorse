package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.HorseData;
import com.hiphurra.myhorse.HorseUtils;
import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.OwnerData;
import com.hiphurra.myhorse.builders.Message;
import com.hiphurra.myhorse.enums.LanguageString;
import com.hiphurra.myhorse.enums.NameType;
import com.hiphurra.myhorse.enums.PermissionNode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO - This command should maybe only be for admins. Seems a bit OP to be able to claim any horse without any effort.
public class ClaimCommand implements Command {

    @Override
    public boolean execute(MyHorse plugin, CommandSender sender, String... args) {
        Player player = (Player) sender;
        Entity entity = player.getVehicle();
        if(HorseUtils.isHorse().test(entity) && entity != null) {
            AbstractHorse horse = (AbstractHorse) entity;
            HorseData horseData = new HorseData(plugin, entity.getUniqueId());
            if(!HorseUtils.isTamed().test(horse) && !horseData.hasOwner()) {
                OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
                String name = plugin.getRandomPlaceholderName(NameType.HORSE, new ArrayList<String>( ownerData.getHorses().values() ));
                horse.setOwner(player);
                ownerData.setCurrentHorseIdentifier(horse.getUniqueId());
                horseData.setOwner(player.getUniqueId());
                horseData.setName(name);
                horse.setCustomName(name);
                horse.setCustomNameVisible(true);
                horseData.setCurrentLocation(entity.getLocation());
                horseData.setLocked(true);
                Message message = new Message.MessageBuilder(plugin, LanguageString.YouClaimedAHorse)
                        .build();
                message.sendMessage(player);
                return true;
            }
            if(horseData.getOwnerId().equals(player.getUniqueId())){
                Message message = new Message.MessageBuilder(plugin, LanguageString.AlreadyOwnThatHorse)
                        .setHorseName(horseData.getName())
                        .build();
                message.sendMessage(player);
                return true;
            } else {
                Message message = new Message.MessageBuilder(plugin, LanguageString.YouCannotClaimThisHorse)
                        .setPlayerName(plugin.getServer().getOfflinePlayer(horseData.getOwnerId()).getName())
                        .build();
                message.sendMessage(player);
                return true;
            }
        }
        Message message = new Message.MessageBuilder(plugin, LanguageString.NotAHorse).build();
        message.sendMessage(player);
        return true;
    }

    @Override
    public List<String> tab(MyHorse plugin, Player player, String... args) {
        return Collections.emptyList();
    }

    @Override
    public @NotNull String getName() {
        return "claim";
    }

    @Override
    public @NotNull String getUsage() {
        return "/mh claim";
    }

    @NotNull
    @Override
    public PermissionNode getPermission() {
        return PermissionNode.CLAIM;
    }

}
