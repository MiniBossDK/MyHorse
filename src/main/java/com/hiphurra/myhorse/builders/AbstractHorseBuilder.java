package com.hiphurra.myhorse.builders;


import org.bukkit.EntityEffect;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.loot.LootTable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AbstractHorseBuilder {

    private final AbstractHorse abstractHorse;

    public AbstractHorseBuilder(AbstractHorse abstractHorse) {
        this.abstractHorse = abstractHorse;
    }
    
    public AbstractHorseBuilder setDomestication(int level) {
        abstractHorse.setDomestication(level);
        return this;
    }

    public AbstractHorseBuilder setMaxDomestication(int level) {
        abstractHorse.setMaxDomestication(level);
        return this;
    }
    
    public AbstractHorseBuilder setJumpStrength(double strength) {
        abstractHorse.setJumpStrength(strength);
        return this;
    }
    
    public AbstractHorseBuilder setTamed(boolean tame) {
        abstractHorse.setTamed(tame);
        return this;
    }

    public AbstractHorseBuilder setOwner(@Nullable AnimalTamer tamer) {
        abstractHorse.setOwner(tamer);
        return this;
    }
    
    public AbstractHorseBuilder setBreedCause(@Nullable UUID uuid) {
        abstractHorse.setBreedCause(uuid);
        return this;
    }

    public AbstractHorseBuilder setLoveModeTicks(int ticks) {
        abstractHorse.setLoveModeTicks(ticks);
        return this;
    }
    
    public AbstractHorseBuilder setAge(int age) {
        abstractHorse.setAge(age);
        return this;
    }
    
    public AbstractHorseBuilder setAgeLock(boolean lock) {
        abstractHorse.setAgeLock(lock);
        return this;
    }

    
    public AbstractHorseBuilder setBaby() {
        abstractHorse.setBaby();
        return this;
    }
    
    public AbstractHorseBuilder setAdult() {
        abstractHorse.setAdult();
        return this;
    }
    
    public AbstractHorseBuilder setBreed(boolean breed) {
        abstractHorse.setBreed(breed);
        return this;
    }
    
    public AbstractHorseBuilder setTarget(@Nullable LivingEntity target) {
        abstractHorse.setTarget(target);
        return this;
    }
    
    public AbstractHorseBuilder setAware(boolean aware) {
        abstractHorse.setAware(aware);
        return this;
    }
    
    public AbstractHorseBuilder setRemainingAir(int ticks) {
        abstractHorse.setRemainingAir(ticks);
        return this;
    }
    
    public AbstractHorseBuilder setMaximumAir(int ticks) {
        abstractHorse.setMaximumAir(ticks);
        return this;
    }
    
    public AbstractHorseBuilder setArrowCooldown(int ticks) {
        abstractHorse.setArrowCooldown(ticks);
        return this;
    }
    
    public AbstractHorseBuilder setArrowsInBody(int count) {
        abstractHorse.setArrowsInBody(count);
        return this;
    }

    public AbstractHorseBuilder setMaximumNoDamageTicks(int ticks) {
        abstractHorse.setMaximumNoDamageTicks(ticks);
        return this;
    }

    public AbstractHorseBuilder setLastDamage(double damage) {
        abstractHorse.setLastDamage(damage);
        return this;
    }
    
    public AbstractHorseBuilder setNoDamageTicks(int ticks) {
        abstractHorse.setNoDamageTicks(ticks);
        return this;
    }

    public AbstractHorseBuilder removePotionEffect(@NotNull PotionEffectType type) {
        abstractHorse.removePotionEffect(type);
        return this;
    }
    
    public AbstractHorseBuilder setRemoveWhenFarAway(boolean remove) {
        abstractHorse.setRemoveWhenFarAway(remove);
        return this;
    }

    public AbstractHorseBuilder setCanPickupItems(boolean pickup) {
        abstractHorse.setCanPickupItems(pickup);
        return this;
    }

    public AbstractHorseBuilder setLeashHolder(@Nullable Entity holder) {
        abstractHorse.setLeashHolder(holder);
        return this;
    }
    
    public AbstractHorseBuilder setGliding(boolean gliding) {
        abstractHorse.setGliding(gliding);
        return this;
    }

    public AbstractHorseBuilder setSwimming(boolean swimming) {
        abstractHorse.setSwimming(swimming);
        return this;
    }

    public AbstractHorseBuilder setAI(boolean ai) {
        abstractHorse.setAI(ai);
        return this;
    }
    
    public AbstractHorseBuilder attack(@NotNull Entity target) {
        abstractHorse.attack(target);
        return this;
    }

    
    public AbstractHorseBuilder swingMainHand() {
        abstractHorse.swingMainHand();
        return this;
    }

    
    public AbstractHorseBuilder swingOffHand() {
        abstractHorse.swingOffHand();
        return this;
    }

    
    public AbstractHorseBuilder setCollidable(boolean collidable) {
        abstractHorse.setCollidable(collidable);
        return this;
    }
    
    public <T> AbstractHorseBuilder setMemory(@NotNull MemoryKey<T> memoryKey, @Nullable T memoryValue) {
        abstractHorse.setMemory(memoryKey, memoryValue);
        return this;
    }
    
    public AbstractHorseBuilder setInvisible(boolean invisible) {
        abstractHorse.setInvisible(invisible);
        return this;
    }

    public AbstractHorseBuilder damage(double amount) {
        abstractHorse.damage(amount);
        return this;
    }

    public AbstractHorseBuilder damage(double amount, @Nullable Entity source) {
        abstractHorse.damage(amount, source);
        return this;
    }

    public AbstractHorseBuilder setHealth(double health) {
        abstractHorse.setHealth(health);
        return this;
    }

    public AbstractHorseBuilder setAbsorptionAmount(double amount) {
        abstractHorse.setAbsorptionAmount(amount);
        return this;
    }

    public AbstractHorseBuilder setRotation(float yaw, float pitch) {
        abstractHorse.setRotation(yaw, pitch);
        return this;
    }
    
    public AbstractHorseBuilder setFireTicks(int ticks) {
        abstractHorse.setFireTicks(ticks);
        return this;
    }

    
    public AbstractHorseBuilder remove() {
        abstractHorse.remove();
        return this;
    }
    
    public AbstractHorseBuilder setPersistent(boolean persistent) {
        abstractHorse.setPersistent(persistent);
        return this;
    }
    
    public AbstractHorseBuilder setFallDistance(float distance) {
        abstractHorse.setFallDistance(distance);
        return this;
    }
    
    public AbstractHorseBuilder setLastDamageCause(@Nullable EntityDamageEvent event) {
        abstractHorse.setLastDamageCause(event);
        return this;
    }
    
    public AbstractHorseBuilder playEffect(@NotNull EntityEffect type) {
        abstractHorse.playEffect(type);
        return this;
    }
    
    public AbstractHorseBuilder setCustomNameVisible(boolean flag) {
        abstractHorse.setCustomNameVisible(flag);
        return this;
    }

    public AbstractHorseBuilder setGlowing(boolean flag) {
        abstractHorse.setGlowing(flag);
        return this;
    }
    
    public AbstractHorseBuilder setInvulnerable(boolean flag) {
        abstractHorse.setInvulnerable(flag);
        return this;
    }

    
    public AbstractHorseBuilder setSilent(boolean flag) {
        abstractHorse.setSilent(flag);
        return this;
    }

    public AbstractHorseBuilder setGravity(boolean gravity) {
        abstractHorse.setGravity(gravity);
        return this;
    }
    
    public AbstractHorseBuilder setPortalCooldown(int cooldown) {
        abstractHorse.setPortalCooldown(cooldown);
        return this;
    }

    public AbstractHorseBuilder setVelocity(@NotNull Vector vel) {
        abstractHorse.setVelocity(vel);
        return this;
    }

    public AbstractHorseBuilder setCustomName(@Nullable String name) {
        abstractHorse.setCustomName(name);
        return this;
    }

    
    public AbstractHorseBuilder setLootTable(@Nullable LootTable table) {
        abstractHorse.setLootTable(table);
        return this;
    }

    public AbstractHorseBuilder setSeed(long seed) {
        abstractHorse.setSeed(seed);
        return this;
    }

    public AbstractHorseBuilder setMetadata(@NotNull String metadataKey, @NotNull MetadataValue newMetadataValue) {
        abstractHorse.setMetadata(metadataKey, newMetadataValue);
        return this;
    }
    
    public AbstractHorseBuilder removeMetadata(@NotNull String metadataKey, @NotNull Plugin owningPlugin) {
        abstractHorse.removeMetadata(metadataKey, owningPlugin);
        return this;
    }
    
    public AbstractHorseBuilder removeAttachment(@NotNull PermissionAttachment attachment) {
        abstractHorse.removeAttachment(attachment);
        return this;
    }

    public AbstractHorseBuilder setOp(boolean value) {
        abstractHorse.setOp(value);
        return this;
    }

    public AbstractHorse build() {
        return abstractHorse;
    }
}