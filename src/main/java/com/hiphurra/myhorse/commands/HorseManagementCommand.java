package com.hiphurra.myhorse.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.hiphurra.myhorse.HorseData;
import org.bukkit.entity.Player;

@CommandAlias("%horsecommand")
@Subcommand("horse")
public class HorseManagementCommand extends BaseCommand {

    @Default
    @CommandCompletion("@horses")
    public void onHorses(Player player, String horseName) {

    }

    @Subcommand("list")
    @CommandCompletion("@horses")
    @Description("Shows a list of owned horses")
    public void onHorsesList(Player player, String horseName) {

    }

    @Subcommand("set")
    @CommandCompletion("@horses")
    @Description("Allows the player to configure their horse")
    public void onSet(Player player, String horseName, HorseData horseData) {

    }



}
