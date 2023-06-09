package com.hiphurra.myhorse.listeners;

import com.hiphurra.myhorse.*;
import com.hiphurra.myhorse.builders.Message;
import com.hiphurra.myhorse.enums.LanguageString;
import com.hiphurra.myhorse.enums.NameType;
import com.hiphurra.myhorse.enums.PermissionNode;
import com.hiphurra.myhorse.enums.Setting;
import com.hiphurra.myhorse.events.HorseBoughtEvent;
import com.hiphurra.myhorse.events.HorseDamageEvent;
import com.hiphurra.myhorse.events.HorseInventoryOpenEvent;
import com.hiphurra.myhorse.events.PlayerPutChestOnHorseEvent;
import com.hiphurra.myhorse.inventory.InventoryManager;
import com.hiphurra.myhorse.stable.Stable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class MyHorseListener implements Listener {

    private final MyHorse plugin;
    private final List<Player> inventoryViewers = new ArrayList<>();
    private final Map<UUID, AbstractHorse> playersExitedHorse = new HashMap<>();

    public MyHorseListener(MyHorse plugin) {
        this.plugin = plugin;
    }
    /*
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
    */
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
    /*
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
    */
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
                    // TODO - Use LanguageManager
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
        // TODO - This condition might not work always. Find a better way of identifying that the inventory is in fact the horse's inventory
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
        // TODO - This condition might not work always. Find a better way of identifying that the inventory is in fact the horse's inventory
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
        // TODO - This condition might not work always. Find a better way of identifying that the inventory is in fact the horse's inventory
        if(event.getDestination().getSize() % 9 != 0) return;
        AbstractHorse abstractHorse = (AbstractHorse) holder;
        InventoryManager inventoryManager = new InventoryManager(plugin, abstractHorse);
        // TODO - Test why this code is here and document why it is there
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
        AbstractHorse abstractHorse = (AbstractHorse) holder;
        InventoryManager inventoryManager = new InventoryManager(plugin, abstractHorse);
        // TODO - Test why this code is here and document why it is there
        Bukkit.getScheduler().runTaskLater(plugin, () -> inventoryManager.setItems(event.getClickedInventory().getStorageContents()), 1L);
    }

    // TODO - Maybe change it so the player does not open the inventory in horse while riding but when not riding and interacting while sneaking
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
        if(!horseData.hasOwner()) return;

        if(horseData.isPlayerOwner(player)) {
            ownerData.setCurrentHorseIdentifier(horseData.getHorseId());
            Message message = new Message.MessageBuilder(plugin, LanguageString.YouSelectedHorse)
                    .setHorseName(horseData.getName())
                    .build();
            message.sendMessage(player);
            return;
        }
        /*
        if(horseData.isPlayerTrusted(player) || isAdmin(player)) {
            Message message = new Message.MessageBuilder(plugin, LanguageString.YouMountedOwnedHorse)
                    .setHorseName(horseData.getName())
                    .setPlayerName(Bukkit.getOfflinePlayer(horseData.getOwnerId()).getName())
                    .build();
            message.sendMessage(player);
        }
        */
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
        plugin.getHorseOwners().add(new HorseOwner(event.getPlayer().getUniqueId()));
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
        HorseData horseData = new HorseData(plugin, abstractHorse.getUniqueId());

        if(!horseData.hasOwner()) return;

        // If the player tries to use a name tag on a horse
        if (hasPlayerItemInEitherHand(player, Material.NAME_TAG)) {
            if(horseData.isPlayerOwner(player))
            {
                Message message = new Message.MessageBuilder(plugin, LanguageString.CannotUseNameTags)
                        .setEconomyAmount((double) plugin.getSettings().get(Setting.SETNAME_COMMAND_PRICE))
                        .build();
                message.sendMessage(player);
                event.setCancelled(true);
            }
            else
            {
                Message message = new Message.MessageBuilder(plugin, LanguageString.CannotUseLockedHorse)
                        .setHorseName(horseData.getName())
                        .setPlayerName(Bukkit.getOfflinePlayer(horseData.getOwnerId()).getName())
                        .build();
                message.sendMessage(player);
                event.setCancelled(true);
            }
        }

        if(horseData.isForSale()) {
            buyHorse(abstractHorse, horseData, player);
            event.setCancelled(true);
            return;
        }

        // If the player is not the owner of the horse or an admin
        if(!horseData.isPlayerOwner(player) && !isAdmin(player))
        {
            // If the player is a trusted player of the horse or an admin
            if(horseData.isPlayerTrusted(player))
            {
                plugin.logDebug(Level.INFO, "Player " + player.getName() + " mounted trusted horse " + horseData.getName() + " owned by " + Bukkit.getOfflinePlayer(horseData.getOwnerId()).getName());
                Message message = new Message.MessageBuilder(plugin, LanguageString.YouMountedOwnedHorse)
                        .setHorseName(horseData.getName())
                        .setPlayerName(Bukkit.getOfflinePlayer(horseData.getOwnerId()).getName())
                        .build();
                message.sendMessage(player);
                return;
            }
            if(horseData.isLocked())
            {
                plugin.logDebug(Level.INFO, "Player " + player.getName() + " tried to mount locked horse " + horseData.getName() + " owned by " + Bukkit.getOfflinePlayer(horseData.getOwnerId()).getName());
                Message message = new Message.MessageBuilder(plugin, LanguageString.CannotUseLockedHorse)
                        .setPlayerName(Bukkit.getOfflinePlayer(horseData.getOwnerId()).getName())
                        .build();
                message.sendMessage(player);
                event.setCancelled(true);
            }
            else
            {
                plugin.logDebug(Level.INFO, "Player " + player.getName() + " mounted unlocked horse " + horseData.getName() + " owned by " + Bukkit.getOfflinePlayer(horseData.getOwnerId()).getName());
                Message message = new Message.MessageBuilder(plugin, LanguageString.YouMountedOwnedHorse)
                        .setHorseName(horseData.getName())
                        .setPlayerName(Bukkit.getOfflinePlayer(horseData.getOwnerId()).getName())
                        .build();
                message.sendMessage(player);
            }
            return;
        }

        // If the player is the owner of the horse or an admin
        if(horseData.isPlayerOwner(player) || isAdmin(player))
        {
            if (hasPlayerItemInEitherHand(player, Material.CHEST))
            {
                if (player.isSneaking())
                {
                    if ((boolean) plugin.getSettings().get(Setting.ALLOW_CHEST_ON_ALL_HORSES))
                    {
                        if (horseData.hasInventory()) {
                            Message message = new Message.MessageBuilder(plugin, LanguageString.HorseAlreadyHasChest)
                                    .setHorseName(horseData.getName())
                                    .setEconomyAmount(horseData.getPrice())
                                    .build();
                            message.sendMessage(player);
                            event.setCancelled(true);
                            return;
                        }

                        PlayerPutChestOnHorseEvent playerPutChestOnHorseEvent = new PlayerPutChestOnHorseEvent(player, abstractHorse);
                        plugin.getServer().getPluginManager().callEvent(playerPutChestOnHorseEvent);

                        event.setCancelled(true);
                    }
                    else
                    {
                        Message message = new Message.MessageBuilder(plugin, LanguageString.ChestFeatureDisabled).build();
                        message.sendMessage(player);
                        event.setCancelled(true);
                    }
                }

            }

            if(isAdmin(player) && !horseData.isPlayerOwner(player))
            {
                Message message = new Message.MessageBuilder(plugin, LanguageString.YouMountedOwnedHorse)
                        .setHorseName(horseData.getName())
                        .setPlayerName(Bukkit.getOfflinePlayer(horseData.getOwnerId()).getName())
                        .build();

                message.sendMessage(player);
            }
        }
    }

    private void buyHorse(AbstractHorse horseEntity, HorseData horseForSale, Player player)
    {
        if(horseForSale.getOwnerId().equals(player.getUniqueId()))
        {
            Message message = new Message.MessageBuilder(plugin, LanguageString.CannotBuyOwnHorse).build();
            message.sendMessage(player);
        }

        OwnerData ownerData = new OwnerData(plugin, player.getUniqueId());

        if (!plugin.getEconomyManager().hasAmount(player, horseForSale.getPrice())) {
            Message message = new Message.MessageBuilder(plugin, LanguageString.YouDoNotHaveEnoughMoneyToBuyHorse)
                    .setHorseName(horseForSale.getName())
                    .setEconomyAmount(horseForSale.getPrice())
                    .build();
            message.sendMessage(player);
        }
        if(!ownerData.isBuying()) {
            Message message = new Message.MessageBuilder(plugin, LanguageString.AreYouSureYouWantToBuyHorse)
                    .setHorseName(horseForSale.getName())
                    .setEconomyAmount(horseForSale.getPrice())
                    .build();
            message.sendMessage(player);
            ownerData.setBuying(true);
            // TODO - Validate if this creates problems down the line
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> ownerData.setBuying(false), 20L*10);
            return;
        }
        HorseBoughtEvent horseBoughtEvent = new HorseBoughtEvent(horseEntity, horseForSale.getPrice(), player);
        if(!horseBoughtEvent.isCancelled()) Bukkit.getServer().getPluginManager().callEvent(horseBoughtEvent);

    }

    private boolean hasPlayerItemInEitherHand(Player player, Material material)
    {
        return player.getInventory().getItemInMainHand().getType().equals(material)
                || player.getInventory().getItemInOffHand().getType().equals(material);
    }

    private boolean isAdmin(Player player)
    {
        return player.isOp() || plugin.getPermissionManager().hasPermission(player, PermissionNode.ADMIN);
    }
}
