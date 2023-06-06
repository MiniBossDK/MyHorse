package com.hiphurra.myhorse.listeners;

import com.hiphurra.myhorse.*;
import com.hiphurra.myhorse.builders.Message;
import com.hiphurra.myhorse.customhorses.CustomSkeletonHorse;
import com.hiphurra.myhorse.enums.LanguageString;
import com.hiphurra.myhorse.enums.NameType;
import com.hiphurra.myhorse.enums.Setting;
import com.hiphurra.myhorse.events.HorseBoughtEvent;
import com.hiphurra.myhorse.events.HorseDamageEvent;
import com.hiphurra.myhorse.events.HorseInventoryOpenEvent;
import com.hiphurra.myhorse.events.PlayerPutChestOnHorseEvent;
import com.hiphurra.myhorse.inventory.InventoryManager;
import com.hiphurra.myhorse.stable.Stable;
import net.minecraft.server.v1_16_R3.World;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class MyHorseListener implements Listener {

    private final MyHorse plugin;
    private final List<Player> inventoryViewers = new ArrayList<>();
    private final Map<UUID, AbstractHorse> playersExitedHorse = new HashMap<>();

    public MyHorseListener(MyHorse plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void createSpawnEvent(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        if(event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;
        if(!(entity instanceof SkeletonHorse)) return;
        Location loc = entity.getLocation();
        WorldServer server = ((CraftWorld) event.getLocation().getWorld()).getHandle();
        CustomSkeletonHorse customSkeletonHorse = new CustomSkeletonHorse(server);
        customSkeletonHorse.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        server.addEntity(customSkeletonHorse, CreatureSpawnEvent.SpawnReason.CUSTOM);
        entity.remove();
    }
    /* TODO - Make teleportation with horse work consistently
    @EventHandler
    public void onPlayerExitVehicle(VehicleExitEvent event) {
        if(!(event.getExited() instanceof Player)) return;
        Player player = (Player) event.getExited();
        if(!HorseUtils.isHorse().test(event.getVehicle())) return;
        AbstractHorse abstractHorse = (AbstractHorse) event.getVehicle();
        HorseData horseData = new HorseData(plugin, abstractHorse.getUniqueId());
        if(!horseData.hasOwner()) return;
        System.out.println("Added: " + player.getName());
        playersExitedHorse.put(player.getUniqueId(), abstractHorse);
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        System.out.println("Here1!");
        System.out.println(playersExitedHorse.size());
        switch (event.getCause()) {
            case CHORUS_FRUIT:
            case END_GATEWAY:
            case UNKNOWN:
            case ENDER_PEARL:
            case NETHER_PORTAL:
            case END_PORTAL:
                return;
        }
        if(!playersExitedHorse.containsKey(player.getUniqueId())) return;
        System.out.println("Here3!");
        AbstractHorse abstractHorse = playersExitedHorse.get(player.getUniqueId());
        playersExitedHorse.remove(player.getUniqueId());
        Location destination = event.getTo();
        System.out.println("Destination: " + destination);
        if(destination == null) return;
        Bukkit.getScheduler().runTaskLater(plugin, () -> abstractHorse.teleport(destination), 5L);
        Bukkit.getScheduler().runTaskLater(plugin, () -> abstractHorse.addPassenger(player), 6L);
    }
     */

    @EventHandler (priority = EventPriority.MONITOR)
    public void chunkLoadEvent(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if(entity instanceof SkeletonHorse) {
                Location loc = entity.getLocation();
                World server = ((CraftWorld) entity.getLocation().getWorld()).getHandle();
                CustomSkeletonHorse customSkeletonHorse = new CustomSkeletonHorse(server);
                customSkeletonHorse.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
                server.addEntity(customSkeletonHorse);
                entity.remove();
            }
        }
    }

    @EventHandler
    public void entityDeathEvent(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if(!HorseUtils.isHorse().test(entity)) return;
        AbstractHorse abstractHorse = (AbstractHorse) entity;
        HorseData horseData = new HorseData(plugin, abstractHorse.getUniqueId());
        if(!horseData.hasOwner()) return;
        InventoryManager inventoryManager = new InventoryManager(plugin, abstractHorse);
        ItemStack[] itemsInInventory = inventoryManager.getInventory().getContents();
        for (ItemStack itemStack : itemsInInventory) {
            if(itemStack != null) {
                abstractHorse.getWorld().dropItemNaturally(abstractHorse.getLocation(), itemStack);
            }
        }
        horseData.remove();
    }

    @EventHandler
    public void entityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        Entity damagedEntity = event.getEntity();
        Entity attackerEntity = event.getDamager();
        if(!HorseUtils.isHorse().test(damagedEntity)) return;
        AbstractHorse abstractHorse = (AbstractHorse) damagedEntity;
        HorseDamageEvent horseDamageEvent = new HorseDamageEvent(abstractHorse, event.getCause());
        plugin.getServer().getPluginManager().callEvent(horseDamageEvent);
    }

    @EventHandler
    public void entityDamageByBlockEvent(EntityDamageByBlockEvent event) {
        Entity damagedEntity = event.getEntity();
        if(!HorseUtils.isHorse().test(damagedEntity)) return;
        AbstractHorse abstractHorse = (AbstractHorse) damagedEntity;
        HorseDamageEvent horseDamageEvent = new HorseDamageEvent(abstractHorse, event.getCause());
        plugin.getServer().getPluginManager().callEvent(horseDamageEvent);
        event.setCancelled(true);
    }

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = event.getTo();
        if(location == null) return;
        for (Stable stableRegion : plugin.getStableRegions()) {
            if(stableRegion.isInRegion(location)) {
                if(!stableRegion.getOwner().equals(player.getUniqueId())) {
                    player.sendMessage(ChatColor.RED + "You don't have permission to enter "  + ChatColor.GOLD + Bukkit.getOfflinePlayer(stableRegion.getOwner()).getName() +  "'s" +  ChatColor.RED + " stable!");
                    event.setCancelled(true);
                }
            }
        }

        /*
        if(!ownerData.isDefiningArea()) return;
        PlayerDefiningAreaEvent playerDefiningAreaEvent = new PlayerDefiningAreaEvent(event.getPlayer(), );
        if(!playerDefiningAreaEvent.isCancelled()) plugin.getServer().getPluginManager().callEvent(playerDefiningAreaEvent);
         */
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void chunkUnloadEvent(ChunkUnloadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if(HorseUtils.isHorse().test(entity)) {
                AbstractHorse horse = (AbstractHorse) entity;
                HorseData horseData = new HorseData(plugin, horse.getUniqueId());
                if(horseData.hasOwner()) {
                    horseData.setCurrentLocation(horse.getLocation());
                }
            }
        }
    }

    @EventHandler
    public void entityTameEvent(EntityTameEvent event) {
        if(!HorseUtils.isHorse().test(event.getEntity())) return;
        AbstractHorse abstractHorse = (AbstractHorse) event.getEntity();
        HorseData horseData = new HorseData(plugin, abstractHorse.getUniqueId());
        if(horseData.hasOwner()) return;
        if(!(event.getOwner() instanceof Player)) return;
        Player player = (Player) event.getOwner();
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        String name = plugin.getRandomPlaceholderName(NameType.HORSE, new ArrayList<>(ownerData.getHorses().values()));
        abstractHorse.setOwner(player);
        ownerData.setCurrentHorseIdentifier(abstractHorse.getUniqueId());
        horseData.setOwner(player.getUniqueId());
        horseData.setName(name);
        abstractHorse.setCustomName(name);
        abstractHorse.setCustomNameVisible(true);
        horseData.setCurrentLocation(abstractHorse.getLocation());
        horseData.setLocked(true);
        Message message = new Message.MessageBuilder(plugin, LanguageString.YouClaimedAHorse)
                .build();
        message.sendMessage(player);
    }

    @EventHandler
    public void inventoryCloseEvent(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if(holder == null) return;
        if(!(holder instanceof Entity)) return;
        if(!(HorseUtils.isHorse().test((Entity) holder))) return;
        if(event.getInventory().getSize() % 9 != 0) return;
        AbstractHorse abstractHorse = (AbstractHorse) holder;
        InventoryManager inventoryManager = new InventoryManager(plugin, abstractHorse);
        inventoryManager.setItems(event.getInventory().getStorageContents());
        inventoryViewers.removeIf(player -> player.equals(event.getPlayer()));
    }

    @EventHandler
    public void inventoryInteractEvent(InventoryDragEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if(holder == null) return;
        if(!(holder instanceof Entity)) return;
        if(!(HorseUtils.isHorse().test((Entity) holder))) return;
        if(event.getInventory().getSize() % 9 != 0) return;
        AbstractHorse abstractHorse = (AbstractHorse) holder;
        InventoryManager inventoryManager = new InventoryManager(plugin, abstractHorse);
        inventoryManager.setItems(event.getInventory().getStorageContents());
    }

    @EventHandler
    public void inventoryMoveItemEvent(InventoryMoveItemEvent event) {
        InventoryHolder holder = event.getDestination().getHolder();
        if(holder == null) return;
        if(!(holder instanceof Entity)) return;
        if(!(HorseUtils.isHorse().test((Entity) holder))) return;
        if(event.getDestination().getSize() % 9 != 0) return;
        AbstractHorse abstractHorse = (AbstractHorse) holder;
        InventoryManager inventoryManager = new InventoryManager(plugin, abstractHorse);
        Bukkit.getScheduler().runTaskLater(plugin, () -> inventoryManager.setItems(event.getDestination().getStorageContents()), 1L);
    }

    @EventHandler
    public void inventoryInteractEvent(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;
        InventoryHolder holder = event.getClickedInventory().getHolder();
        if(holder == null) return;
        if(!(holder instanceof Entity)) return;
        if(!(HorseUtils.isHorse().test((Entity) holder))) return;
        if(event.getInventory().getSize() % 9 != 0) return;
        System.out.println("yes");
        AbstractHorse abstractHorse = (AbstractHorse) holder;
        InventoryManager inventoryManager = new InventoryManager(plugin, abstractHorse);
        Bukkit.getScheduler().runTaskLater(plugin, () -> inventoryManager.setItems(event.getClickedInventory().getStorageContents()), 1L);
    }

    @EventHandler
    public void inventoryOpenEvent(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        Player player = (Player) event.getPlayer();
        if(!(holder instanceof Entity)) return;
        if(!(HorseUtils.isHorse().test((Entity) holder))) return;
        HorseData horseData = new HorseData(plugin, ((Entity) holder).getUniqueId());
        if(!horseData.hasInventory()) return;
        if(player.isSneaking()) return;
        if(event.getInventory().getSize() % 9 != 0) {
            event.setCancelled(true);
        }
        InventoryManager inventoryManager = new InventoryManager(plugin, (AbstractHorse) holder);
        if(inventoryViewers.contains((Player) event.getPlayer())) return;
        inventoryViewers.add((Player) event.getPlayer());
        HorseInventoryOpenEvent horseInventoryOpenEvent = new HorseInventoryOpenEvent((AbstractHorse) holder, inventoryManager.getInventory(), (Player) event.getPlayer());
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().getPluginManager().callEvent(horseInventoryOpenEvent), 1L);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void playerEnterHorseEvent(VehicleEnterEvent event) {
        if(!HorseUtils.isHorse().test(event.getVehicle())) return;
        if(!(event.getEntered() instanceof Player)) return;
        AbstractHorse abstractHorse = (AbstractHorse) event.getVehicle();
        Player player = (Player) event.getEntered();
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        HorseData horseData = new HorseData(plugin, abstractHorse.getUniqueId());
        if(horseData.getOwner() == null) return;
        if(!horseData.getOwner().equals(player.getUniqueId()) && !horseData.getTrustedPlayers().contains(player.getUniqueId())) {
            Message message = new Message.MessageBuilder(plugin, LanguageString.YouMountedOwnedHorse)
                    .setHorseName(horseData.getName())
                    .setPlayerName(Bukkit.getOfflinePlayer(horseData.getOwner()).getName())
                    .build();
            message.sendMessage(player);
            event.setCancelled(true);
        }
        if(horseData.getOwner().equals(player.getUniqueId())) {
            ownerData.setCurrentHorseIdentifier(horseData.getHorseId());
            Message message = new Message.MessageBuilder(plugin, LanguageString.YouSelectedHorse)
                    .setHorseName(horseData.getName())
                    .build();
            message.sendMessage(player);
        }

    }

    /*
    @EventHandler(priority = EventPriority.HIGH)
    public void playerOpenHorseInventoryEvent(InventoryOpenEvent event) {
        if(!(event.getInventory().getHolder() instanceof Entity)) return;
        if(!HorseUtils.isHorse().test((Entity) event.getInventory().getHolder())) return;
        Player player = (Player) event.getPlayer();
        if(!player.isSneaking()) return;
        AbstractHorse abstractHorse = (AbstractHorse) event.getInventory().getHolder();
        HorseData horseData = new HorseData(plugin, abstractHorse.getUniqueId());
        if(!horseData.getOwner().equals(player.getUniqueId()) && !horseData.getFriends().contains(player.getUniqueId()) && horseData.isLocked()) {
            Message message = new Message.MessageBuilder(plugin, LanguageString.CannotUseLockedHorse)
                    .setHorseName(horseData.getName())
                    .setPlayerName(plugin.getServer().getOfflinePlayer(horseData.getOwner()).getName())
                    .build();
            message.sendMessage(player);
            event.setCancelled(true);
        }
    }
    */

    @EventHandler (priority = EventPriority.LOWEST)
    public void playerDisconnectedEvent(PlayerQuitEvent event) {
        OwnerData ownerData = new OwnerData(plugin, event.getPlayer().getUniqueId());
        if(ownerData.isBuying()) ownerData.setBuying(false);
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void playerJoinEvent(PlayerJoinEvent event) {
        plugin.getHorseOwners().add(new AbstractHorseOwner(event.getPlayer().getUniqueId()));
        OwnerData ownerData = new OwnerData(plugin, event.getPlayer().getUniqueId());
        if(ownerData.getSalesRecords().isEmpty()) return;
        List<SalesRecord> salesRecords = ownerData.getSalesRecords().stream().filter(salesRecord -> !salesRecord.isSeen()).collect(Collectors.toList());

        if(salesRecords.isEmpty()) return;
        for (SalesRecord salesRecord : salesRecords) {
            Message message = new Message.MessageBuilder(plugin, LanguageString.PlayerBoughtYourHorse)
                    .setPlayerName(salesRecord.getBuyerName())
                    .setHorseName(salesRecord.getHorseName())
                    .setEconomyAmount(salesRecord.getPrice())
                    .build();
            message.sendMessage(event.getPlayer());
            salesRecord.setHasSeen(true);
            ownerData.setSalesRecord(salesRecord);
        }
    }

    @EventHandler
    public void playerInteractHorseEvent(PlayerInteractEntityEvent event) {
        if(!HorseUtils.isHorse().test(event.getRightClicked())) return;
        Player player = event.getPlayer();
        AbstractHorse abstractHorse = (AbstractHorse) event.getRightClicked();
        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());
        HorseData horseData = new HorseData(plugin, abstractHorse.getUniqueId());
        if(!horseData.hasOwner()) return;
        if(horseData.isForSale()) {
            if(horseData.getOwner().equals(player.getUniqueId())) {
                Message message = new Message.MessageBuilder(plugin, LanguageString.CannotBuyOwnHorse).build();
                message.sendMessage(player);
            } else if (plugin.getEconomyManager().hasAmount(player, horseData.getPrice())) {
                if(!ownerData.isBuying()) {
                    Message message = new Message.MessageBuilder(plugin, LanguageString.AreYouSureYouWantToBuyHorse)
                            .setHorseName(horseData.getName())
                            .setEconomyAmount(horseData.getPrice())
                            .build();
                    message.sendMessage(player);
                    ownerData.setBuying(true);
                    plugin.getServer().getScheduler().runTaskLater(plugin, () -> ownerData.setBuying(false), 20L*10);
                    event.setCancelled(true);
                    return;
                }
                HorseBoughtEvent horseBoughtEvent = new HorseBoughtEvent(abstractHorse, horseData.getPrice(), player);
                if(!horseBoughtEvent.isCancelled()) Bukkit.getServer().getPluginManager().callEvent(horseBoughtEvent);
                event.setCancelled(true);
                return;
            } else {
                Message message = new Message.MessageBuilder(plugin, LanguageString.YouDoNotHaveEnoughMoneyToBuyHorse)
                        .setHorseName(horseData.getName())
                        .setEconomyAmount(horseData.getPrice())
                        .build();
                message.sendMessage(player);
            }
            event.setCancelled(true);
            return;
        }
        if(!horseData.getOwner().equals(player.getUniqueId()) || !player.isOp()) return;
        if(horseData.getOwner().equals(player.getUniqueId())) {
            if(player.getInventory().getItemInMainHand().getType().equals(Material.CHEST)
                    || player.getInventory().getItemInOffHand().getType().equals(Material.CHEST) ) {
                if(player.isSneaking()) {
                    PlayerPutChestOnHorseEvent playerPutChestOnHorseEvent = new PlayerPutChestOnHorseEvent(player, abstractHorse);
                    plugin.getServer().getPluginManager().callEvent(playerPutChestOnHorseEvent);
                    event.setCancelled(true);
                    return;
                }
                event.setCancelled(true);
                return;
            }
        }
        if(!horseData.getOwner().equals(player.getUniqueId()) || !player.isOp()) {
            if(!horseData.getTrustedPlayers().contains(player.getUniqueId())) {
                Message message = new Message.MessageBuilder(plugin, LanguageString.CannotUseLockedHorse)
                        .setPlayerName(Bukkit.getOfflinePlayer(horseData.getOwner()).getName())
                        .build();
                message.sendMessage(player);
                event.setCancelled(true);
            } else {
                Message message = new Message.MessageBuilder(plugin, LanguageString.YouMountedOwnedHorse)
                        .setHorseName(horseData.getName())
                        .setPlayerName(Bukkit.getOfflinePlayer(horseData.getOwner()).getName())
                        .build();
                message.sendMessage(player);
            }
        } else {
            if(player.getInventory().getItemInMainHand().getType().equals(Material.NAME_TAG) ||
                    player.getInventory().getItemInMainHand().getType().equals(Material.NAME_TAG)) {
                Message message = new Message.MessageBuilder(plugin, LanguageString.CannotUseNameTags)
                        .setAmount((double) plugin.getSettings().get(Setting.SETNAME_COMMAND_PRICE))
                        .build();
                message.sendMessage(player);
                event.setCancelled(true);
            }
        }
    }
}
