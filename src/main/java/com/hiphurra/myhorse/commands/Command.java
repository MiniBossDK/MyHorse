package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.enums.PermissionNode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public interface Command {

    boolean execute(MyHorse plugin, CommandSender sender, String... args);

    default List<String> tab(MyHorse plugin, Player player, String... args) {
        return Collections.emptyList();
    }
    @NotNull
    String getName();
    @NotNull
    String getUsage();
    @NotNull
    PermissionNode getPermission();
}
