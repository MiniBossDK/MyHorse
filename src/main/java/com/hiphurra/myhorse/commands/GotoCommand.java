package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.HorseData;
import com.hiphurra.myhorse.HorseUtils;
import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.OwnerData;
import com.hiphurra.myhorse.builders.Message;
import com.hiphurra.myhorse.enums.LanguageString;
import com.hiphurra.myhorse.enums.PermissionNode;
import com.hiphurra.myhorse.enums.Setting;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class GotoCommand implements Command {

    // TODO - Implement with Economy
    @Override
    public boolean execute(MyHorse plugin, CommandSender sender, String... args) {
        Player player = (Player) sender;
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        Number amount = (Number) plugin.getSettings().get(Setting.GOTO_COMMAND_PRICE);
        if(!plugin.getEconomyManager().hasAmount(player, amount.doubleValue())) {
            Message message = new Message.MessageBuilder(plugin, LanguageString.UseGotoCommand)
                    .setEconomyAmount(amount.doubleValue())
                    .build();
            message.sendMessage(player);
            return true;
        }
        if(args.length == 2) {
            for (Entry<UUID, String> tamedHorse : ownerData.getHorses().entrySet()) {
                if(tamedHorse.getValue().equalsIgnoreCase(args[1])) {
                    HorseData horseData = new HorseData(plugin, tamedHorse.getKey());
                    AbstractHorse horse = HorseUtils.findHorse(horseData);
                    if(horse != null) {
                        player.teleport(horse.getLocation());
                        plugin.getEconomyManager().withdrawAmount(player, amount.doubleValue());
                        Message message = new Message.MessageBuilder(plugin, LanguageString.UseGotoCommandSuccess)
                                .setHorseName(horseData.getName())
                                .setEconomyAmount(amount.doubleValue())
                                .build();
                        message.sendMessage(player);
                        return true;
                    }
                }
            }
            Message message = new Message.MessageBuilder(plugin, LanguageString.NoHorseWithSuchName)
                    .setHorseName(args[1])
                    .build();
            message.sendMessage(player);
            return true;
        }
        player.sendMessage(ChatColor.AQUA + getUsage());
        return true;
    }

    @Override
    public List<String> tab(MyHorse plugin, Player player, String... args) {
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        if(args.length == 2) return new ArrayList<>( ownerData.getHorses().values() );
        return Collections.emptyList();
    }

    @Override
    public @NotNull String getName() {
        return "goto";
    }

    @Override
    public @NotNull String getUsage() {
        return "/mh goto <horse_name>";
    }

    @NotNull
    @Override
    public PermissionNode getPermission() {
        return PermissionNode.GOTO;
    }
}
