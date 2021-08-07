package com.hiphurra.myhorse.commands;

import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.OwnerData;
import com.hiphurra.myhorse.builders.Message;
import com.hiphurra.myhorse.enums.LanguageString;
import com.hiphurra.myhorse.enums.PermissionNode;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListCommand implements Command {

    private int numberOfPages = 0;

    @Override
    public boolean execute(MyHorse plugin, CommandSender sender, String... args) {

        Player player = (Player) sender;
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        List<String> horseNames = new ArrayList<>( ownerData.getHorses().values() );
        int maxListSizePerPage = 10;
        numberOfPages = (int) Math.ceil((double) horseNames.size() / maxListSizePerPage);
        int pageNumber = 1;
        if(args.length == 2) {
            try {
                pageNumber = Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.RED + " is not a number!");
                return true;
            }
        }
        if(pageNumber > numberOfPages || pageNumber < 1) {
            player.sendMessage("Wrong page number");
            return true;
        }
        Message message = new Message.MessageBuilder(plugin, LanguageString.YourOwnedHorsesList)
                .setAmount(ownerData.getHorses().size())
                .build();
        message.sendMessage(player);
        player.sendMessage(ChatColor.AQUA + "----------      Page " + pageNumber + " of " + numberOfPages + "     ----------");
        int fromIndex = ((pageNumber-1) * maxListSizePerPage);

        for (int i = fromIndex; i < horseNames.size(); i++) {
            player.sendMessage(ChatColor.AQUA + "- " + horseNames.get(i) + ChatColor.AQUA);

            if(pageNumber == numberOfPages && (horseNames.size() == i)) {
                return true;
            }
            if(i == (fromIndex + maxListSizePerPage) - 1) {
                return true;
            }
        }
        return true;
    }

    @Override
    public List<String> tab(MyHorse plugin, Player player, String... args) {
        if(args.length == 2) {
            List<String> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= numberOfPages; i++) {
                pageNumbers.add(String.valueOf(i));
            }
            return pageNumbers;
        }
        return Collections.emptyList();
    }

    @NotNull
    @Override
    public String getName() {
        return "list";
    }

    @NotNull
    @Override
    public String getUsage() {
        return "/mh list <page number>";
    }

    @NotNull
    @Override
    public PermissionNode getPermission() {
        return PermissionNode.LIST;
    }
}
