package com.hiphurra.myhorse.builders;

import org.bukkit.EntityEffect;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.ChestedHorse;
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

public class ChestedHorseBuilder {

    private final ChestedHorse chestedHorse;

    public ChestedHorseBuilder(ChestedHorse chestedHorse) {
        this.chestedHorse = chestedHorse;
    }

    public ChestedHorseBuilder setCarryingChest(boolean chest) {
        chestedHorse.setCarryingChest(chest);
        return this;
    }
    
    public ChestedHorseBuilder setDomestication(int level) {
        chestedHorse.setDomestication(level);
        return this;
    }
    
    public ChestedHorseBuilder setMaxDomestication(int level) {
        chestedHorse.setMaxDomestication(level);
        return this;
    }

    
    public ChestedHorseBuilder setJumpStrength(double strength) {
        chestedHorse.setJumpStrength(strength);
        return this;
    }
    
    public ChestedHorseBuilder setTamed(boolean tame) {
        chestedHorse.setTamed(tame);
        return this;
    }
    
    public ChestedHorseBuilder setOwner(@Nullable AnimalTamer tamer) {
        chestedHorse.setOwner(tamer);
        return this;
    }
    
    public ChestedHorseBuilder setBreedCause(@Nullable UUID uuid) {
        chestedHorse.setBreedCause(uuid);
        return this;
    }

    public ChestedHorseBuilder setLoveModeTicks(int ticks) {
        chestedHorse.setLoveModeTicks(ticks);
        return this;
    }

    public ChestedHorseBuilder setAge(int age) {
        chestedHorse.setAge(age);
        return this;
    }

    
    public ChestedHorseBuilder setAgeLock(boolean lock) {
        chestedHorse.setAgeLock(lock);
        return this;
    }

    public ChestedHorseBuilder setBaby() {
        chestedHorse.setBaby();
        return this;
    }

    
    public ChestedHorseBuilder setAdult() {
        chestedHorse.setAdult();
        return this;
    }
    
    public ChestedHorseBuilder setBreed(boolean breed) {
        chestedHorse.setBreed(breed);
        return this;
    }

    public ChestedHorseBuilder setTarget(@Nullable LivingEntity target) {
        chestedHorse.setTarget(target);
        return this;
    }

    public ChestedHorseBuilder setAware(boolean aware) {
        chestedHorse.setAware(aware);
        return this;
    }
    
    public ChestedHorseBuilder setRemainingAir(int ticks) {
        chestedHorse.setRemainingAir(ticks);
        return this;
    }

    public ChestedHorseBuilder setMaximumAir(int ticks) {
        chestedHorse.setMaximumAir(ticks);
        return this;
    }

    public ChestedHorseBuilder setArrowCooldown(int ticks) {
        chestedHorse.setArrowCooldown(ticks);
        return this;
    }


    public ChestedHorseBuilder setArrowsInBody(int count) {
        chestedHorse.setArrowsInBody(count);
        return this;
    }

    public ChestedHorseBuilder setMaximumNoDamageTicks(int ticks) {
        chestedHorse.setMaximumNoDamageTicks(ticks);
        return this;
    }

    public ChestedHorseBuilder setLastDamage(double damage) {
        chestedHorse.setLastDamage(damage);
        return this;
    }

    public ChestedHorseBuilder setNoDamageTicks(int ticks) {
        chestedHorse.setNoDamageTicks(ticks);
        return this;
    }

    public ChestedHorseBuilder removePotionEffect(@NotNull PotionEffectType type) {
        chestedHorse.removePotionEffect(type);
        return this;
    }

    
    public ChestedHorseBuilder setRemoveWhenFarAway(boolean remove) {
        chestedHorse.setRemoveWhenFarAway(remove);
        return this;
    }

    public ChestedHorseBuilder setCanPickupItems(boolean pickup) {
        chestedHorse.setCanPickupItems(pickup);
        return this;
    }

    public ChestedHorseBuilder setGliding(boolean gliding) {
        chestedHorse.setGliding(gliding);
        return this;
    }

    public ChestedHorseBuilder setSwimming(boolean swimming) {
        chestedHorse.setSwimming(swimming);
        return this;
    }
    
    public ChestedHorseBuilder setAI(boolean ai) {
        chestedHorse.setAI(ai);
        return this;
    }

    public ChestedHorseBuilder attack(@NotNull Entity target) {
        chestedHorse.attack(target);
        return this;
    }


    public ChestedHorseBuilder swingMainHand() {
        chestedHorse.swingMainHand();
        return this;
    }


    public ChestedHorseBuilder swingOffHand() {
        chestedHorse.swingOffHand();
        return this;
    }

    
    public ChestedHorseBuilder setCollidable(boolean collidable) {
        chestedHorse.setCollidable(collidable);
        return this;
    }

    public <T> ChestedHorseBuilder setMemory(@NotNull MemoryKey<T> memoryKey, @Nullable T memoryValue) {
        chestedHorse.setMemory(memoryKey, memoryValue);
        return this;
    }

    public ChestedHorseBuilder setInvisible(boolean invisible) {
        chestedHorse.setInvisible(invisible);
        return this;
    }
    
    public ChestedHorseBuilder damage(double amount) {
        chestedHorse.damage(amount);
        return this;
    }

    
    public ChestedHorseBuilder damage(double amount, @Nullable Entity source) {
        chestedHorse.damage(amount, source);
        return this;
    }

    public ChestedHorseBuilder setHealth(double health) {
        chestedHorse.setHealth(health);
        return this;
    }

    
    public ChestedHorseBuilder setAbsorptionAmount(double amount) {
        chestedHorse.setAbsorptionAmount(amount);
        return this;
    }

    public ChestedHorseBuilder setRotation(float yaw, float pitch) {
        chestedHorse.setRotation(yaw, pitch);
        return this;
    }

    public ChestedHorseBuilder setFireTicks(int ticks) {
        chestedHorse.setFireTicks(ticks);
        return this;
    }

    
    public ChestedHorseBuilder remove() {
        chestedHorse.remove();
        return this;
    }

    public ChestedHorseBuilder setPersistent(boolean persistent) {
        chestedHorse.setPersistent(persistent);
        return this;
    }
    
    public ChestedHorseBuilder setFallDistance(float distance) {
        chestedHorse.setFallDistance(distance);
        return this;
    }

    
    public ChestedHorseBuilder setLastDamageCause(@Nullable EntityDamageEvent event) {
        chestedHorse.setLastDamageCause(event);
        return this;
    }
    
    public ChestedHorseBuilder setTicksLived(int value) {
        chestedHorse.setTicksLived(value);
        return this;
    }

    
    public ChestedHorseBuilder playEffect(@NotNull EntityEffect type) {
        chestedHorse.playEffect(type);
        return this;
    }
    
    public ChestedHorseBuilder setCustomNameVisible(boolean flag) {
        chestedHorse.setCustomNameVisible(flag);
        return this;
    }
    
    public ChestedHorseBuilder setGlowing(boolean flag) {
        chestedHorse.setGlowing(flag);
        return this;
    }

    public ChestedHorseBuilder setInvulnerable(boolean flag) {
        chestedHorse.setInvulnerable(flag);
        return this;
    }
    
    public ChestedHorseBuilder setSilent(boolean flag) {
        chestedHorse.setSilent(flag);
        return this;
    }
    
    public ChestedHorseBuilder setGravity(boolean gravity) {
        chestedHorse.setGravity(gravity);
        return this;
    }

    public ChestedHorseBuilder setPortalCooldown(int cooldown) {
        chestedHorse.setPortalCooldown(cooldown);
        return this;
    }

    public ChestedHorseBuilder setVelocity(@NotNull Vector vel) {
        chestedHorse.setVelocity(vel);
        return this;
    }

    public ChestedHorseBuilder setCustomName(@Nullable String name) {
        chestedHorse.setCustomName(name);
        return this;
    }

    
    public ChestedHorseBuilder setLootTable(@Nullable LootTable table) {
        chestedHorse.setLootTable(table);
        return this;
    }

    public ChestedHorseBuilder setSeed(long seed) {
        chestedHorse.setSeed(seed);
        return this;
    }

    public ChestedHorseBuilder setMetadata(@NotNull String metadataKey, @NotNull MetadataValue newMetadataValue) {
        chestedHorse.setMetadata(metadataKey, newMetadataValue);
        return this;
    }

    public ChestedHorseBuilder removeMetadata(@NotNull String metadataKey, @NotNull Plugin owningPlugin) {
        chestedHorse.removeMetadata(metadataKey, owningPlugin);
        return this;
    }
    
    public ChestedHorseBuilder removeAttachment(@NotNull PermissionAttachment attachment) {
        chestedHorse.removeAttachment(attachment);
        return this;
    }

    
    public ChestedHorseBuilder recalculatePermissions() {
        chestedHorse.recalculatePermissions();
        return this;
    }
    
    public ChestedHorseBuilder setOp(boolean value) {
        chestedHorse.setOp(value);
        return this;
    }

    public ChestedHorse build() {
        return chestedHorse;
    }
}
