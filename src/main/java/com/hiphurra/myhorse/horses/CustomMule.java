package com.hiphurra.myhorse.horses;

import net.minecraft.server.v1_16_R3.EntityHorseMule;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;

public class CustomMule extends EntityHorseMule implements CustomChestedHorse {

    public CustomMule(EntityTypes<? extends EntityHorseMule> var0, World var1) {
        super(var0, var1);
    }
}
