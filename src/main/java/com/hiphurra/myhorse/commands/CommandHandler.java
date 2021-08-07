package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.builders.Message;
import com.hiphurra.myhorse.enums.LanguageString;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private final MyHorse plugin;
    private final List<Command> commands = new ArrayList<>();

     public CommandHandler(MyHorse plugin) throws InstantiationException, IllegalAccessException {
         this.plugin = plugin;
         registerCommands();
     }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.Command cmd, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) {
            plugin.logDebug(Level.WARNING, "Only players are allow to use commands!");
            return true;
        }
        Player player = (Player) sender;

        String base = (args.length > 0 ? args[0] : "").toLowerCase();

        Command command = getMatchingCommand(base);

        if(command == null) {
            Message message = new Message.MessageBuilder(plugin, LanguageString.InvalidCommand).build();
            message.sendMessage(player);
            return true;
        }

        if(hasPermission(player, command) || player.isOp()) {
            return command.execute(plugin, sender, args);
        } else {
            Message message = new Message.MessageBuilder(plugin, LanguageString.NoPermissionForCommand).build();
            message.sendMessage(player);
            return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String alias, @NotNull String[] args) {

         if(!(sender instanceof Player)) return Collections.emptyList();

        String base = (args.length > 0 ? args[0] : "").toLowerCase();
        Command cmd = getMatchingCommand(base);
        Player player = (Player) sender;

        List<Command> filteredCommands = commands.stream().filter(command1 -> hasPermission(player, command1) || player.isOp()).collect(Collectors.toList());

        if(base.isEmpty()) return filteredCommands.stream().map(Command::getName).collect(Collectors.toList());

        if(args.length == 1) return filteredCommands.stream().map(Command::getName).filter(name -> name.startsWith(base)).collect(Collectors.toList());


        if(cmd != null) return cmd.tab(plugin, player, args);

        return Collections.emptyList();
    }

    private Command getMatchingCommand(String cmd) {
        for (Command command : commands) {
            if(command.getName().equalsIgnoreCase(cmd)) {
                return command;
            }
        }
         return null;
    }

    private boolean hasPermission(Player player, Command command) {
        return plugin.getPermissionManager().hasPermission(player, command.getPermission());
    }

    public void registerCommands() throws InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections("com.hiphurra.myhorse.commands");
        for (Class< ?extends Command> commandClass : reflections.getSubTypesOf(Command.class)) {
            commands.add(commandClass.newInstance());
        }
    }
}
