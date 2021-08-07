package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.OwnerData;
import com.hiphurra.myhorse.stable.Region;
import com.hiphurra.myhorse.stable.StableData;
import com.hiphurra.myhorse.enums.PermissionNode;
import com.hiphurra.myhorse.stable.Stable;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class StableCommand implements Command {

    @Override
    public boolean execute(MyHorse plugin, CommandSender sender, String... args) {
        Player player = (Player) sender;

        if(args.length < 2) {
            player.sendMessage(getUsage());
            return true;
        }

        switch (args[1]) {
            case "set":
                return new SetSubCommand(plugin, player, args).execute();
            case "goto":
                return new GotoSubCommand(plugin, player, args).execute();
            default:
                player.sendMessage(getUsage());
                return true;
        }

        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        StableData stableData = new StableData(plugin, UUID.randomUUID());

        if (ownerData.isDefiningArea()) {
            Region region = new Region(ownerData.getStartLocation(), player.getLocation());
            if(region.getWidth() < 6 && region.getHeight() < 6) {
                player.sendMessage(ChatColor.RED + "You tried to make a " + region.getWidth() + " by " + region.getHeight() + " stable. Stable needs to be at least 6 blocks by 6 blocks.");
                ownerData.setAreaMode(false);
                return true;
            }
            for (Stable stableRegion : plugin.getStableRegions()) {
                if(stableRegion.isIntersecting(region)) {
                    player.sendMessage("Overlapping");
                    ownerData.setAreaMode(false);
                    return true;
                }
            }
            stableData.setOwner(player.getUniqueId());
            stableData.setRegion(ownerData.getStartLocation(), player.getLocation());
            ownerData.setAreaMode(false);
            player.sendMessage("Out of area mode");
        } else {
            ownerData.setAreaMode(true);
            ownerData.setStartLocation(player.getLocation());
            player.sendMessage("Now in area mode");
        }
        return true;
    }

    @Override
    public List<String> tab(MyHorse plugin, Player player, String... args) {

        return Command.super.tab(plugin, player, args);
    }

    @NotNull
    @Override
    public String getName() {
        return "stable";
    }

    @NotNull
    @Override
    public String getUsage() {
        return "/mh stable <set | unset> ";
    }

    @NotNull
    @Override
    public PermissionNode getPermission() {
        return PermissionNode.STABLE;
    }

    public static class SetSubCommand {

        private final MyHorse plugin;
        private final Player sender;
        private final String[] args;


        public SetSubCommand(MyHorse plugin, Player sender, String[] args) {
            this.plugin = plugin;
            this.sender = sender;
            this.args = args;
        }

        public boolean execute() {
            if(args.length > )


            return true;
        }


        public static class StableSubSubCommand {

            private final MyHorse plugin;
            private final Player sender;
            private final String[] args;


            public StableSubSubCommand(MyHorse plugin, Player sender, String[] args) {
                this.plugin = plugin;
                this.sender = sender;
                this.args = args;
            }

            public boolean execute() {
                return true;
            }


        }

        public static class StallSubSubCommand {

            private final MyHorse plugin;
            private final Player sender;
            private final String[] args;


            public StallSubSubCommand(MyHorse plugin, Player sender, String[] args) {
                this.plugin = plugin;
                this.sender = sender;
                this.args = args;
            }

            public boolean execute() {
                return true;
            }

        }


    }

    public static class GotoSubCommand {

        private final MyHorse plugin;
        private final Player sender;
        private final String[] args;


        public GotoSubCommand(MyHorse plugin, Player sender, String[] args) {
            this.plugin = plugin;
            this.sender = sender;
            this.args = args;
        }

        public boolean execute() {
            return true;
        }

    }
}
