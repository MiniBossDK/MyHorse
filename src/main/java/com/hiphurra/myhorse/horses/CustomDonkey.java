package com.hiphurra.myhorse.horses;

import net.minecraft.server.v1_16_R3.EntityHorseDonkey;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;

public class CustomDonkey extends EntityHorseDonkey implements CustomChestedHorse {

    public CustomDonkey(EntityTypes<? extends EntityHorseDonkey> var0, World var1) {
        super(var0, var1);
    }



}
