package com.hiphurra.myhorse.horses;

import net.minecraft.server.v1_16_R3.*;

public class CustomSkeletonHorse extends EntityHorseSkeleton implements CustomSpecialHorse {

    public CustomSkeletonHorse(World var1) {
        super(EntityTypes.SKELETON_HORSE, var1);
    }



    @Override
    public boolean mate(EntityAnimal entityanimal) {
        if(entityanimal == this) return false;

        if(entityanimal instanceof CustomSkeletonHorse) {
            return (fo() && ((CustomSkeletonHorse) entityanimal).fo());
        }
        return false;
    }

    @Override
    public EntityAgeable createChild(WorldServer server, EntityAgeable ageable) {
        EntityHorseAbstract horseAbstract = null;
        if(ageable instanceof CustomSkeletonHorse) {
            horseAbstract = EntityTypes.SKELETON_HORSE.a(server);
        }
        a(ageable, horseAbstract);
        return horseAbstract;
    }
}
