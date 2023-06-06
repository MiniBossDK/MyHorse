package com.hiphurra.myhorse;

import co.aikar.commands.*;
import co.aikar.commands.annotation.Dependency;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Horses {

    @Dependency
    MyHorse plugin;

    public void init() {
        registerCommandCompletions();
        registerCommandContexts();
    }

    private void registerCommandCompletions() {
        CommandCompletions<BukkitCommandCompletionContext> commandCompletions = plugin.commandManager.getCommandCompletions();
        commandCompletions.registerAsyncCompletion("horses", context -> {
            CommandSender sender = context.getSender();
            if (sender instanceof Player) {
                Player player = (Player) sender;
                return plugin.getOwnerData(player.getUniqueId()).getHorses().values();
            }
            return null;
        });
    }

    private void registerCommandContexts() {
        CommandContexts<BukkitCommandExecutionContext> commandContexts = plugin.commandManager.getCommandContexts();

        commandContexts.registerContext(HorseData.class, context -> {
            String name = context.popFirstArg();
            CommandSender sender = context.getSender();
            if(sender instanceof Player) {
                Player player = (Player) sender;
                List<String> horseIds = new ArrayList<String>( plugin.getOwnerData(player.getUniqueId()).getHorses().values() );
                if(!horseIds.contains(name)) {
                    throw new InvalidCommandArgument("Could not find a horse with that name");
                }
            }
            return null;
        });
    }

}
