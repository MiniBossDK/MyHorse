package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.HorseData;
import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.OwnerData;
import com.hiphurra.myhorse.builders.Message;
import com.hiphurra.myhorse.enums.LanguageString;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class CommandLock implements Command {


    @Override
    public boolean execute(MyHorse plugin, CommandSender sender, String... args) {

        Player player = (Player) sender;
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        Message message = null;
        if(args.length == 1) {
            HorseData horseData = new HorseData(plugin, ownerData.getCurrentHorseIdentifier());
            horseData.setLocked(true);
            message = new Message.MessageBuilder(plugin, LanguageString.HorseLocked)
                    .setHorseName(horseData.getName())
                    .build();
            message.sendMessage(player);
            return true;
        } else {
            for (Entry<UUID, String> entry : ownerData.getHorses().entrySet()) {
                if(entry.getValue().equalsIgnoreCase(args[1])) {
                    HorseData horseData = new HorseData(plugin, entry.getKey());
                    horseData.setLocked(true);
                    message = new Message.MessageBuilder(plugin, LanguageString.HorseLocked)
                            .setHorseName(horseData.getName())
                            .build();
                    message.sendMessage(player);
                    return true;
                }
            }
            message = new Message.MessageBuilder(plugin, LanguageString.NoHorseWithSuchName)
                    .setHorseName(args[1])
                    .build();
            message.sendMessage(player);
        }

        return true;
    }

    @Override
    public List<String> tab(MyHorse plugin, Player player, String... args) {
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        if(args.length == 2) return new ArrayList<>( ownerData.getHorses().values() );
        return Collections.emptyList();
    }
}
