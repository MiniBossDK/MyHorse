package com.hiphurra.myhorse.horses;

import net.minecraft.server.v1_16_R3.EntityHorse;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.World;

public class CustomHorse extends EntityHorse implements CustomAbstractHorse {


    public CustomHorse(EntityTypes<? extends EntityHorse> var0, World var1) {

        super(var0, var1);
    }



    @Override
    public void saveData(NBTTagCompound nbt) {
        super.saveData(nbt);

    }

    @Override
    public boolean isHungry() {
        return false;
    }

    @Override
    public void setOwner() {

    }

}
