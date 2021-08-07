package com.hiphurra.myhorse;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.*;

import java.util.function.Predicate;

public class HorseUtils {

    private final MyHorse plugin;


    public HorseUtils(MyHorse plugin) {
        this.plugin = plugin;
    }

    public static Predicate<Entity> isHorse() {
        return entity -> entity instanceof AbstractHorse && !(entity instanceof Llama);
    }

    public static Predicate<AbstractHorse> isTamed() {
        return Tameable::isTamed;
    }

    public static AbstractHorse findHorse(HorseData horseData) {
        for (World world : Bukkit.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (isHorse().test(entity)) {
                    if (entity.getUniqueId().equals(horseData.getHorseId())) {
                        return (AbstractHorse) entity;
                    }
                }
            }
        }
        return searchUnloadedChunks(horseData);
    }

    private static AbstractHorse searchUnloadedChunks(HorseData horseData) {
        Chunk horseChunk = horseData.getHorseLocation().getChunk();

        if (horseChunk.load(false)) {
            for (Entity entity : horseChunk.getEntities()) {
                if (isHorse().test(entity)) {
                    if (entity.getUniqueId().equals(horseData.getHorseId())) {
                        System.out.println("Chunk searching...");
                        return (AbstractHorse) entity;
                    }
                }
            }
        }
        return null;
    }


    public static String generateRandomHorseName(Player player) {
        return "";
    }




}
