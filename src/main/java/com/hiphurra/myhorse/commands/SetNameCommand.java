package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.HorseData;
import com.hiphurra.myhorse.HorseUtils;
import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.OwnerData;
import com.hiphurra.myhorse.builders.AbstractHorseBuilder;
import com.hiphurra.myhorse.builders.Message;
import com.hiphurra.myhorse.enums.LanguageString;
import com.hiphurra.myhorse.enums.PermissionNode;
import com.hiphurra.myhorse.enums.Setting;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.Map.Entry;

public class SetNameCommand implements Command {


    @Override
    public boolean execute(MyHorse plugin, CommandSender sender, String... args) {

        Player player = (Player) sender;
        Number amount = (Number) plugin.getSettings().get(Setting.SETNAME_COMMAND_PRICE);
        if(plugin.getEconomyManager() != null)
        {
            if(!plugin.getEconomyManager().hasAmount(player, amount.doubleValue())) {
                Message message = new Message.MessageBuilder(plugin, LanguageString.UseCommandToNameYourHorse)
                        .setEconomyAmount(amount.doubleValue())
                        .build();
                message.sendMessage(player);
                return true;
            }
        }
        Message message = new Message.MessageBuilder(plugin, LanguageString.InvalidHorseName).build();
        if(args.length > 1) {
            if(isReservedName(args[1])) {
                message.sendMessage(player);
                return true;
            }
        }
        switch(args.length) {
            case 2:
                return new FromSelectedHorseSubCommand(plugin, player, args, amount).execute();
            case 3:
                return new FromHorseNameSubCommand(plugin, player, args, amount).execute();
            default:
                player.sendMessage(getUsage());
                return true;
        }
    }

    @Override
    public List<String> tab(MyHorse plugin, Player player, String... args) {
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        if(args.length == 3) return new ArrayList<>( ownerData.getHorses().values() );
        return Collections.emptyList();
    }

    @Override
    public @NotNull String getName() {
        return "setname";
    }

    @Override
    public @NotNull String getUsage() {
        return "/mh setname <name>  |  /mh setname <name> <horse_to_name>";
    }

    @Override
    public @NotNull
    PermissionNode getPermission() {
        return PermissionNode.NAME;
    }

    private static class FromSelectedHorseSubCommand {

        private final MyHorse plugin;
        private final Player player;
        private final String[] args;
        private final Number amount;

        public FromSelectedHorseSubCommand(MyHorse plugin, Player player, String[] args, Number amount) {
            this.plugin = plugin;
            this.player = player;
            this.args = args;
            this.amount = amount;
        }

        public boolean execute() {
            OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
            if(ownerData.hasHorseName(args[1])) {
                Message message = new Message.MessageBuilder(plugin, LanguageString.AlreadyHasHorseWithThatName)
                        .setHorseName(args[1])
                        .build();
                message.sendMessage(player);
                return true;
            }

            UUID horseId = ownerData.getCurrentHorseIdentifier();

            if(horseId == null) {
                Message message = new Message.MessageBuilder(plugin, LanguageString.NoHorseSelected).build();
                message.sendMessage(player);
            }
            HorseData horseData = new HorseData(plugin, ownerData.getCurrentHorseIdentifier());
            horseData.setName(args[1]);
            Message message = new Message.MessageBuilder(plugin, LanguageString.SetHorseName)
                    .setHorseName(args[1])
                    .build();
            message.sendMessage(player);
            plugin.getEconomyManager().withdrawAmount(player, amount.doubleValue());
            AbstractHorse abstractHorse = HorseUtils.findHorse(horseData);
            if(abstractHorse != null) {
                abstractHorse.setCustomName(args[1]);
                abstractHorse.setCustomNameVisible(true);
            }
            return true;
        }
    }

    private static class FromHorseNameSubCommand {

        private final MyHorse plugin;
        private final Player player;
        private final String[] args;
        private final Number amount;

        public FromHorseNameSubCommand(MyHorse plugin, Player player, String[] args, Number amount) {
            this.plugin = plugin;
            this.player = player;
            this.args = args;
            this.amount = amount;
        }

        public boolean execute() {
            OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
            if(ownerData.hasHorseName(args[1])) {
                Message message = new Message.MessageBuilder(plugin, LanguageString.AlreadyHasHorseWithThatName)
                        .setHorseName(args[1])
                        .build();
                message.sendMessage(player);
                return true;
            }

            for (Entry<UUID, String> horseEntry : ownerData.getHorses().entrySet()) {
                if(args[2].equalsIgnoreCase(horseEntry.getValue())) {
                    HorseData horseData = new HorseData(plugin, horseEntry.getKey());
                    horseData.setName(args[1]);
                    Message message = new Message.MessageBuilder(plugin, LanguageString.SetHorseName)
                            .setHorseName(args[1])
                            .build();
                    message.sendMessage(player);
                    plugin.getEconomyManager().withdrawAmount(player, amount.doubleValue());
                    AbstractHorse abstractHorse = HorseUtils.findHorse(horseData);
                    if(abstractHorse != null) {
                        abstractHorse.setCustomName(args[1]);
                        abstractHorse.setCustomNameVisible(true);
                    }
                    return true;
                }
            }

            Message message = new Message.MessageBuilder(plugin, LanguageString.NoHorseWithSuchName)
                    .setHorseName(args[2])
                    .build();
            message.sendMessage(player);

            return true;
        }
    }

    private boolean isReservedName(String name) {
        return Arrays.stream(new String[] { "*", "null" }).anyMatch(reservedName -> reservedName.equalsIgnoreCase(name));
    }
}
