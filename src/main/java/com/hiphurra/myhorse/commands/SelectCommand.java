package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.OwnerData;
import com.hiphurra.myhorse.builders.Message;
import com.hiphurra.myhorse.enums.LanguageString;
import com.hiphurra.myhorse.enums.PermissionNode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class SelectCommand implements Command {

    @Override
    public boolean execute(MyHorse plugin, CommandSender sender, String... args) {
        Player player = (Player) sender;
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());

        for (Entry<UUID, String> entry : ownerData.getHorses().entrySet()) {
            if(entry.getValue().equalsIgnoreCase(args[1])) {
                ownerData.setCurrentHorseIdentifier(entry.getKey());
                Message message = new Message.MessageBuilder(plugin, LanguageString.YouSelectedHorse)
                        .setHorseName(entry.getValue())
                        .build();
                message.sendMessage(player);
                return true;
            }
        }

        return false;
    }

    @Override
    public List<String> tab(MyHorse plugin, Player player, String... args) {
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        if(args.length == 2) return new ArrayList<>( ownerData.getHorses().values() );
        return Collections.emptyList();
    }

    @Override
    public @NotNull String getName() {
        return "select";
    }

    @Override
    public @NotNull String getUsage() {
        return "/mh select <horse_name>";
    }

    @NotNull
    @Override
    public PermissionNode getPermission() {
        return PermissionNode.SELECT;
    }
}
