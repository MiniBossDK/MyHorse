package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.HorseData;
import com.hiphurra.myhorse.HorseUtils;
import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.OwnerData;
import com.hiphurra.myhorse.enums.PermissionNode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

public class KillCommand implements Command {
    @Override
    public boolean execute(MyHorse plugin, CommandSender sender, String... args) {
        Player player = (Player) sender;
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        for (Entry<UUID, String> horseEntry : ownerData.getHorses().entrySet()) {
            if(horseEntry.getValue().equalsIgnoreCase(args[1])) {
                HorseData horseData = new HorseData(plugin, horseEntry.getKey());
                AbstractHorse abstractHorse = HorseUtils.findHorse(horseData);
                if(abstractHorse == null) return true;
                if(ownerData.getCurrentHorseIdentifier() != null) {
                    if(ownerData.getCurrentHorseIdentifier().equals(horseEntry.getKey())) {
                        ownerData.setCurrentHorseIdentifier(null);
                    }
                }
                abstractHorse.setHealth(0);
                return true;
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

    @NotNull
    @Override
    public String getName() {
        return "kill";
    }

    @NotNull
    @Override
    public String getUsage() {
        return "/mh kill <horse_name>";
    }

    @NotNull
    @Override
    public PermissionNode getPermission() {
        return PermissionNode.KILL;
    }
}
