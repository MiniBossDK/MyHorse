package com.hiphurra.myhorse.stable;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Stable extends Region {

    private final UUID ownerId;
    private final String name;
    private List<UUID> trustedPlayers = new ArrayList<>();
    private List<UUID> horses = new ArrayList<>();

    public Stable(UUID ownerId, String name, World world, double minX, double minZ, double maxX, double maxZ) {
        super(world, minX, minZ, maxX, maxZ);
        this.ownerId = ownerId;
        this.name = name;
    }

    public Stable(UUID ownerId, String name, Location loc1, Location loc2) {
        super(loc1, loc2);
        this.ownerId = ownerId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHorses(List<UUID> horses) {
        this.horses = horses;
    }

    public List<UUID> getHorses() {
        return horses;
    }

    public void setTrustedPlayers(List<UUID> trustedPlayers) {
        this.trustedPlayers = trustedPlayers;
    }

    public List<UUID> getTrustedPlayers() {
        return trustedPlayers;
    }

    public UUID getOwner() {
        return ownerId;
    }
}
