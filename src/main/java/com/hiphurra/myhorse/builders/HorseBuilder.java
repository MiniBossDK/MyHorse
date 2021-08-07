package com.hiphurra.myhorse.builders;

import org.bukkit.EntityEffect;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.loot.LootTable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class HorseBuilder {


    private final Horse horse;

    public HorseBuilder(Horse horse) {

        this.horse = horse;
    }
    
    public HorseBuilder setColor(@NotNull Horse.Color color) {
        horse.setColor(color);
        return this;
    }
    
    public HorseBuilder setStyle(@NotNull Horse.Style style) {
        horse.setStyle(style);
        return this;
    }

    public HorseBuilder setDomestication(int level) {
        horse.setDomestication(level);
        return this;
    }
    
    public HorseBuilder setMaxDomestication(int level) {
        horse.setMaxDomestication(level);
        return this;
    }
    
    public HorseBuilder setJumpStrength(double strength) {
        horse.setJumpStrength(strength);
        return this;
    }
    
    public HorseBuilder setTamed(boolean tame) {
        horse.setTamed(tame);
        return this;
    }
    
    public HorseBuilder setOwner(@Nullable AnimalTamer tamer) {
        horse.setOwner(tamer);
        return this;
    }

    public HorseBuilder setBreedCause(@Nullable UUID uuid) {
        horse.setBreedCause(uuid);
        return this;
    }

    public HorseBuilder setLoveModeTicks(int ticks) {
        horse.setLoveModeTicks(ticks);
        return this;
    }
    
    public HorseBuilder setAge(int age) {
        horse.setAge(age);
        return this;
    }

    
    public HorseBuilder setAgeLock(boolean lock) {
        horse.setAgeLock(lock);
        return this;
    }
    
    public HorseBuilder setBaby() {
        horse.setBaby();
        return this;
    }

    
    public HorseBuilder setAdult() {
        horse.setAdult();
        return this;
    }
    
    public HorseBuilder setBreed(boolean breed) {
        horse.setBreed(breed);
        return this;
    }
    
    public HorseBuilder setTarget(@Nullable LivingEntity target) {
        horse.setTarget(target);
        return this;
    }

    public HorseBuilder setAware(boolean aware) {
        horse.setAware(aware);
        return this;
    }

    public HorseBuilder setRemainingAir(int ticks) {
        horse.setRemainingAir(ticks);
        return this;
    }

    public HorseBuilder setMaximumAir(int ticks) {
        horse.setMaximumAir(ticks);
        return this;
    }
    
    public HorseBuilder setArrowCooldown(int ticks) {
        horse.setArrowCooldown(ticks);
        return this;
    }

    public HorseBuilder setArrowsInBody(int count) {
        horse.setArrowsInBody(count);
        return this;
    }
    
    public HorseBuilder setMaximumNoDamageTicks(int ticks) {
        horse.setMaximumNoDamageTicks(ticks);
        return this;
    }
    
    public HorseBuilder setLastDamage(double damage) {
        horse.setLastDamage(damage);
        return this;
    }
    
    public HorseBuilder setNoDamageTicks(int ticks) {
        horse.setNoDamageTicks(ticks);
        return this;
    }

    public HorseBuilder removePotionEffect(@NotNull PotionEffectType type) {
        horse.removePotionEffect(type);
        return this;
    }
    
    public HorseBuilder setRemoveWhenFarAway(boolean remove) {
        horse.setRemoveWhenFarAway(remove);
        return this;
    }

    public HorseBuilder setCanPickupItems(boolean pickup) {
        horse.setCanPickupItems(pickup);
        return this;
    }

    public HorseBuilder setGliding(boolean gliding) {
        horse.setGliding(gliding);
        return this;
    }

    public HorseBuilder setSwimming(boolean swimming) {
        horse.setSwimming(swimming);
        return this;
    }

    public HorseBuilder setAI(boolean ai) {
        horse.setAI(ai);
        return this;
    }

    public HorseBuilder attack(@NotNull Entity target) {
        horse.attack(target);
        return this;
    }

    
    public HorseBuilder swingMainHand() {
        horse.swingMainHand();
        return this;
    }

    
    public HorseBuilder swingOffHand() {
        horse.swingOffHand();
        return this;
    }

    
    public HorseBuilder setCollidable(boolean collidable) {
        horse.setCollidable(collidable);
        return this;
    }

    public <T> HorseBuilder setMemory(@NotNull MemoryKey<T> memoryKey, @Nullable T memoryValue) {
        horse.setMemory(memoryKey, memoryValue);
        return this;
    }
    
    public HorseBuilder setInvisible(boolean invisible) {
        horse.setInvisible(invisible);
        return this;
    }
    
    public HorseBuilder damage(double amount) {
        horse.damage(amount);
        return this;
    }

    
    public HorseBuilder damage(double amount, @Nullable Entity source) {
        horse.damage(amount, source);
        return this;
    }

    public HorseBuilder setHealth(double health) {
        horse.setHealth(health);
        return this;
    }
    
    public HorseBuilder setAbsorptionAmount(double amount) {
        horse.setAbsorptionAmount(amount);
        return this;
    }

    public HorseBuilder setRotation(float yaw, float pitch) {
        horse.setRotation(yaw, pitch);
        return this;
    }

    public HorseBuilder setFireTicks(int ticks) {
        horse.setFireTicks(ticks);
        return this;
    }

    
    public HorseBuilder remove() {
        horse.remove();
        return this;
    }
    
    public HorseBuilder sendMessage(@NotNull String message) {
        horse.swingOffHand();
        return this;
    }

    public HorseBuilder setPersistent(boolean persistent) {
        horse.setPersistent(persistent);
        return this;
    }
    
    public HorseBuilder addPassenger(@NotNull Entity passenger) {
        horse.addPassenger(passenger);
        return this;
    }

    
    public HorseBuilder removePassenger(@NotNull Entity passenger) {
        horse.removePassenger(passenger);
        return this;
    }

    public HorseBuilder eject() {
        horse.eject();
        return this;
    }
    
    public HorseBuilder setFallDistance(float distance) {
        horse.setFallDistance(distance);
        return this;
    }

    
    public HorseBuilder setLastDamageCause(@Nullable EntityDamageEvent event) {
        horse.setLastDamageCause(event);
        return this;
    }

    public HorseBuilder setTicksLived(int value) {
        horse.setTicksLived(value);
        return this;
    }

    
    public HorseBuilder playEffect(@NotNull EntityEffect type) {
        horse.playEffect(type);
        return this;
    }

    public HorseBuilder setCustomNameVisible(boolean flag) {
        horse.setCustomNameVisible(flag);
        return this;
    }
    
    public HorseBuilder setGlowing(boolean flag) {
        horse.setGlowing(flag);
        return this;
    }

    public HorseBuilder setInvulnerable(boolean flag) {
        horse.setInvulnerable(flag);
        return this;
    }
    
    public HorseBuilder setSilent(boolean flag) {
        horse.setSilent(flag);
        return this;
    }
    
    public HorseBuilder setGravity(boolean gravity) {
        horse.setGravity(gravity);
        return this;
    }
    
    public HorseBuilder setPortalCooldown(int cooldown) {
        horse.setPortalCooldown(cooldown);
        return this;
    }
    
    public HorseBuilder addScoreboardTag(@NotNull String tag) {
        horse.addScoreboardTag(tag);
        return this;
    }

    public HorseBuilder removeScoreboardTag(@NotNull String tag) {
        horse.removeScoreboardTag(tag);
        return this;
    }
    
    public HorseBuilder setVelocity(@NotNull Vector vel) {
        horse.setVelocity(vel);
        return this;
    }

    public HorseBuilder setCustomName(@Nullable String name) {
        horse.setCustomName(name);
        return this;
    }

    public HorseBuilder setLootTable(@Nullable LootTable table) {
        horse.setLootTable(table);
        return this;
    }
    
    public HorseBuilder setSeed(long seed) {
        horse.setSeed(seed);
        return this;
    }
    
    public HorseBuilder setMetadata(@NotNull String metadataKey, @NotNull MetadataValue newMetadataValue) {
        horse.setMetadata(metadataKey, newMetadataValue);
        return this;
    }

    public HorseBuilder removeMetadata(@NotNull String metadataKey, @NotNull Plugin owningPlugin) {
        horse.removeMetadata(metadataKey, owningPlugin);
        return this;
    }

    public HorseBuilder recalculatePermissions() {
        horse.recalculatePermissions();
        return this;
    }
    
    public HorseBuilder setOp(boolean value) {
        horse.setOp(value);
        return this;
    }

    public Horse build() {
        return horse;
    }
}
