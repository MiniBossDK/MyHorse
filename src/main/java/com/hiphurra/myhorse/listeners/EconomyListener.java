package com.hiphurra.myhorse.listeners;

import com.hiphurra.myhorse.HorseData;
import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.OwnerData;
import com.hiphurra.myhorse.SalesRecord;
import com.hiphurra.myhorse.builders.Message;
import com.hiphurra.myhorse.enums.LanguageString;
import com.hiphurra.myhorse.events.HorseBoughtEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.Date;

public class EconomyListener implements Listener {

    private final MyHorse plugin;

    public EconomyListener(MyHorse plugin) {

        this.plugin = plugin;
    }

    @EventHandler
    public void horseBoughtEvent(HorseBoughtEvent event) {
        System.out.println("here");
        Player buyer = event.getBuyer();
        HorseData horseData = new HorseData(plugin, event.getAbstractHorse().getUniqueId());
        OwnerData ownerData = new OwnerData(plugin, buyer.getUniqueId());
        ownerData.setBuying(false);
        OfflinePlayer horseOwner = Bukkit.getOfflinePlayer(horseData.getOwner());
        ownerData.setSalesRecord(new SalesRecord(
                horseData.getPrice(),
                Bukkit.getOfflinePlayer(ownerData.getOwnerId()).getName(),
                new Date(),
                horseData.getName(),
                horseOwner.isOnline()
        ));

        if(horseOwner.isOnline()) {
            Message message = new Message.MessageBuilder(plugin, LanguageString.PlayerBoughtYourHorse)
                    .setPlayerName(buyer.getName())
                    .setHorseName(horseData.getName())
                    .setEconomyAmount(event.getPrice())
                    .build();
            message.sendMessage((Player) horseOwner);
        }

        Message message = new Message.MessageBuilder(plugin, LanguageString.YouBoughtHorse)
                .setPlayerName(horseOwner.getName())
                .setHorseName(horseData.getName())
                .setEconomyAmount(horseData.getPrice())
                .build();
        message.sendMessage(buyer);

        plugin.getEconomyManager().withdrawAmount(buyer, horseData.getPrice());
        plugin.getEconomyManager().depositAmount(horseOwner, horseData.getPrice());

        event.getAbstractHorse().setCustomName(horseData.getName());
        horseData.cancelSale();
        horseData.setLocked(true);
        horseData.setTrustedPlayers(Collections.emptyList());
        horseData.setOwner(buyer.getUniqueId());
    }



}
