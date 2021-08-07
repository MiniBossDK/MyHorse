package com.hiphurra.myhorse;

import java.util.UUID;

public class HorseOwner {

    private final UUID ownerId;
    private boolean areaMode = false;

    public HorseOwner(UUID ownerId) {

        this.ownerId = ownerId;
    }

    public void setAreaMode(boolean areaMode) {
        this.areaMode = areaMode;
    }

    public boolean isAreaMode() {
        return areaMode;
    }

    public UUID getOwnerId() {
        return ownerId;
    }
}
