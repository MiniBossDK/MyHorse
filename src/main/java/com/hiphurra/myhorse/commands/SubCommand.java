package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.MyHorse;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public interface SubCommand {

    boolean execute(MyHorse plugin, CommandSender sender, String... args);

    default List<String> tab(MyHorse plugin, Player player, String... args) {
        return Collections.emptyList();
    }

}
