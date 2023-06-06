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

import javax.activation.CommandInfo;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private final MyHorse plugin;
    private final Set<Class<?extends Command>> commands;

     public CommandHandler(MyHorse plugin) throws InstantiationException, IllegalAccessException {
         this.plugin = plugin;
         commands = new Reflections("com.hiphurra.myhorse.commands").getSubTypesOf(Command.class);
     }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) {
            plugin.logDebug(Level.WARNING, "Only players are allow to use commands!");
            return true;
        }
        Player player = (Player) sender;

        String base = (args.length > 0 ? args[0] : "").toLowerCase();

        Class<?extends Command> command = getMatchingCommand(base);

        if(command == null) {
            Message message = new Message.MessageBuilder(plugin, LanguageString.InvalidCommand).build();
            message.sendMessage(player);
            return true;
        }

        if(hasPermission(player, command) || player.isOp()) {
            return command.cast(Command.class).execute(plugin, sender, args);
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
        Class<?extends Command> cmd = getMatchingCommand(base);
        Player player = (Player) sender;

        List<Class<?extends Command>> filteredCommands = commands.stream().filter(command1 -> hasPermission(player, command1) || player.isOp()).collect(Collectors.toList());

        if(base.isEmpty()) return filteredCommands.stream().map(cd -> cd.getAnnotation(CommandInfo.class).name()).collect(Collectors.toList());

        if(args.length == 1) return filteredCommands.stream().map(cd -> cd.getAnnotation(CommandInfo.class).name()).filter(name -> name.startsWith(base)).collect(Collectors.toList());


        if(cmd != null) return cmd.cast(Command.class).tab(plugin, player, args);

        return Collections.emptyList();
    }

    private Class<?extends Command> getMatchingCommand(String cmd) {
        for (Class<?extends Command> command : commands) {
            if(command.getAnnotation(CommandInfo.class) == null) {
                throw new NullPointerException("No command info in the command class: " + command.getName());
            }

            CommandInfo commandInfo = command.getAnnotation(CommandInfo.class);
            if(commandInfo.name().equalsIgnoreCase(cmd)) {
                return command;
            }
        }
         return null;
    }

    private boolean hasPermission(Player player, Class<?extends Command> command) {
        return plugin.getPermissionManager().hasPermission(player, command.getAnnotation(CommandInfo.class).permissionsNode());
    }
}
