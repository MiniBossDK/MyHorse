package com.hiphurra.myhorse.horses;

import net.minecraft.server.v1_16_R3.*;

public class CustomZombieHorse extends EntityHorseZombie implements CustomSpecialHorse {

    public CustomZombieHorse(World var1) {
        super(EntityTypes.ZOMBIE_HORSE, var1);
    }

    @Override
    public boolean mate(EntityAnimal entityanimal) {
        if(entityanimal == this) return false;

        if(entityanimal instanceof CustomZombieHorse) {
            return (fo() && ((CustomZombieHorse) entityanimal).fo());
        }
        return false;
    }

    @Override
    public EntityAgeable createChild(WorldServer server, EntityAgeable ageable) {
        EntityHorseAbstract horseAbstract = null;
        if(ageable instanceof CustomZombieHorse) {
            horseAbstract = EntityTypes.ZOMBIE_HORSE.a(server);
        }
        a(ageable, horseAbstract);
        return horseAbstract;
    }

}
