package com.dogonfire.myhorse;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class HorseManager
{
	private MyHorse				plugin				= null;
	private FileConfiguration	horsesConfig		= null;
	private File				horsesConfigFile	= null;
	private Random				random				= new Random();
	
	
	HorseManager(MyHorse plugin)
	{
		this.plugin = plugin;
	}

	public void load()
	{
		if (this.horsesConfigFile == null)
		{
			this.horsesConfigFile = new File(this.plugin.getDataFolder(), "horses.yml");
		}
		this.horsesConfig = YamlConfiguration.loadConfiguration(this.horsesConfigFile);
		if (this.horsesConfig == null)
		{
			this.plugin.log("Error loading horses.yml! This plugin will NOT work.");
			return;
		}
		this.plugin.log("Loaded " + this.horsesConfig.getKeys(false).size() + " horses.");
	}

	public void save()
	{
		if ((this.horsesConfig == null) || (this.horsesConfigFile == null))
		{
			return;
		}
		try
		{
			this.horsesConfig.save(this.horsesConfigFile);
		}
		catch (Exception ex)
		{
			this.plugin.log("Could not save config to " + this.horsesConfigFile + ": " + ex.getMessage());
		}
	}

	public void newHorse(Player player, EntityType horseVariant, boolean baby)
	{		
		AbstractHorse horse;
		
		if(horseVariant != EntityType.LLAMA || horseVariant != EntityType.TRADER_LLAMA) {
			horse = (AbstractHorse) player.getLocation().getWorld().spawnEntity(player.getLocation(), horseVariant);
			if(baby) ((Ageable) horse).setBaby();
		} else {
			player.sendMessage(ChatColor.YELLOW + horseVariant.name() + " is not a valid horsetype");
		}
	}

	public Boolean ownedHorseWithName(String ownerName, String name)
	{
		for (String horseIdentifierString : this.horsesConfig.getKeys(false))
		{
			String horseOwnerName = this.horsesConfig.getString(horseIdentifierString + ".Owner");
			if ((horseOwnerName != null) && (horseOwnerName.equals(ownerName)))
			{
				String horseName = this.horsesConfig.getString(horseIdentifierString + ".Name");
				if (horseName.equalsIgnoreCase(name))
				{
					return Boolean.valueOf(true);
				}
			}
		}
		return Boolean.valueOf(false);
	}

	public String getNewHorseName()
	{
		String horseName = "";
		switch (this.random.nextInt(56))
		{
			case 0:
				horseName = "Snowwhite";
				break;
			case 1:
				horseName = "Blackie";
				break;
			case 2:
				horseName = "Rainbow Dash";
				break;
			case 3:
				horseName = "Black Beauty";
				break;
			case 4:
				horseName = "Misty";
				break;
			case 5:
				horseName = "Ghostrunner";
				break;
			case 6:
				horseName = "Sleipner";
				break;
			case 7:
				horseName = "Binky";
				break;
			case 8:
				horseName = "Starlight";
				break;
			case 9:
				horseName = "Stormy";
				break;
			case 10:
				horseName = "Silver";
				break;
			case 11:
				horseName = "Snowfire";
				break;
			case 12:
				horseName = "Luna";
				break;
			case 13:
				horseName = "Lucky";
				break;
			case 14:
				horseName = "Prince";
				break;
			case 15:
				horseName = "Spirit";
				break;
			case 16:
				horseName = "Coco";
				break;
			case 17:
				horseName = "Romeo";
				break;
			case 18:
				horseName = "King";
				break;
			case 19:
				horseName = "Buttercup";
				break;
			case 20:
				horseName = "Rose";
				break;
			case 21:
				horseName = "Thunder";
				break;
			case 22:
				horseName = "Lightning";
				break;
			case 23:
				horseName = "Titan";
				break;
			case 24:
				horseName = "Beast";
				break;
			case 25:
				horseName = "Firestarter";
				break;
			case 26:
				horseName = "Spitfire";
				break;
			case 27:
				horseName = "Tornado";
				break;
			case 28:
				horseName = "Dreamrunner";
				break;
			case 29:
				horseName = "Nova";
				break;
			case 30:
				horseName = "Shadow";
				break;
			case 31:
				horseName = "Cookie";
				break;
			case 32:
				horseName = "Maria";
				break;
			case 33:
				horseName = "Thunderhoof";
				break;
			case 34:
				horseName = "Mirage";
				break;
			case 35:
				horseName = "Neptune";
				break;
			case 36:
				horseName = "Athena";
				break;
			case 37:
				horseName = "Calypso";
				break;
			case 38:
				horseName = "Nitro";
				break;
			case 39:
				horseName = "Diana";
				break;
			case 40:
				horseName = "Electra";
				break;
			case 41:
				horseName = "Kira";
				break;
			case 42:
				horseName = "April";
				break;
			case 43:
				horseName = "Aurora";
				break;
			case 44:
				horseName = "Angelfire";
				break;
			case 45:
				horseName = "Rainbow";
				break;
			case 46:
				horseName = "Ranger";
				break;
			case 47:
				horseName = "Nirvana";
				break;
			case 48:
				horseName = "Tomcat";
				break;
			case 49:
				horseName = "Treasure";
				break;
			case 50:
				horseName = "Tyson";
				break;
			case 51:
				horseName = "Pearl";
				break;
			case 52:
				horseName = "Pilgrim";
				break;
			case 53:
				horseName = "Playboy";
				break;
			case 54:
				horseName = "Popcorn";
				break;
			case 55:
				horseName = "Majesty";
		}
		return horseName;
	}

	public boolean isHorseLocked(UUID horseIdentifier)
	{
		return this.horsesConfig.getBoolean(horseIdentifier.toString() + ".Locked");
	}

	public void setLockedForHorse(UUID horseIdentifier, boolean locked)
	{
		this.horsesConfig.set(horseIdentifier.toString() + ".Locked", Boolean.valueOf(locked));
	}

	public boolean isHorsePublic(UUID horseIdentifier)
	{
		return this.horsesConfig.getBoolean(horseIdentifier.toString() + ".Public");
	}

	public void setPublicForHorse(UUID horseIdentifier, boolean flag)
	{
		this.horsesConfig.set(horseIdentifier.toString() + ".Public", Boolean.valueOf(flag));
	}

	public void setNameForHorse(UUID horseIdentifier, String name)
	{
		String pattern = "HH:mm dd-MM-yyyy";
		DateFormat formatter = new SimpleDateFormat(pattern);
		Date thisDate = new Date();

		this.horsesConfig.set(horseIdentifier.toString() + ".Name", name);
		this.horsesConfig.set(horseIdentifier.toString() + ".BirthDate", formatter.format(thisDate));
	}

	public Horse spawnHorse(UUID horseIdentifier, Location location)
	{
		Horse.Style style;

		try
		{
			String styleName = this.horsesConfig.getString(horseIdentifier.toString() + ".Style");
			style = Horse.Style.valueOf(styleName);
		}
		catch (Exception ex)
		{
			style = Horse.Style.values()[this.random.nextInt(Horse.Style.values().length)];
		}

		Horse.Color variant;


		String variantName = this.horsesConfig.getString(horseIdentifier.toString() + ".Variant");
		variant = Horse.Color.valueOf(variantName);

		Horse.Color color;

		try
		{
			String colorName = this.horsesConfig.getString(horseIdentifier.toString() + ".Color");
			color = Horse.Color.valueOf(colorName);
		}
		catch (Exception ex)
		{
			color = Horse.Color.values()[this.random.nextInt(Horse.Color.values().length)];
		}

		double maxHealth = this.horsesConfig.getDouble(horseIdentifier.toString() + ".MaxHealth");
		double jumpStrength = this.horsesConfig.getDouble(horseIdentifier.toString() + ".JumpStrength");

		String name = this.horsesConfig.getString(horseIdentifier.toString() + ".Name");

		Horse horse = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);

		String ownerString = this.horsesConfig.getString(horseIdentifier.toString() + ".Owner");
		UUID ownerId = null;

		try
		{
			ownerId = UUID.fromString(ownerString);

			OfflinePlayer ownerPlayer = this.plugin.getServer().getOfflinePlayer(ownerId);

			if (ownerPlayer != null)
			{
				horse.setOwner(ownerPlayer);
			}
		}
		catch (Exception ex)
		{
			this.horsesConfig.set(horseIdentifier.toString() + ".Owner", null);
		}

		horse.setTamed(true);
		horse.setStyle(style);
		horse.setColor(color);

		if (maxHealth > 0.0D)
		{
			horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
		}

		if (jumpStrength > 0.0D)
		{
			horse.setJumpStrength(jumpStrength);
		}

		horse.setCustomName(this.plugin.getHorseNameColorForPlayer(ownerId) + name);
		horse.setCustomNameVisible(true);

		setOwnerForHorse(horse.getUniqueId(), ownerId);
		setHorseStatistics(horse.getUniqueId(), style, maxHealth, jumpStrength, variant);
		setHorseLastSelectionPosition(horse.getUniqueId(), location);
		setHorseColor(horse.getUniqueId(), color);
		
		for (UUID friendId : getHorseFriends(horseIdentifier))
		{
			addHorseFriend(horse.getUniqueId(), friendId);
		}
		
		setNameForHorse(horse.getUniqueId(), name);

		this.horsesConfig.set(horseIdentifier.toString(), null);

		return horse;
	}

	public Entity getHorseEntity(UUID horseIdentifier)
	{
		// TODO - Use Fido's method from MyDog to make this work properly
		Entity horse = null;
		for(World world : plugin.getServer().getWorlds()) 
		{
			for (Entity otherEntity : world.getEntities())
			{
				if(otherEntity instanceof AbstractHorse)
				{
					if(otherEntity.getType() != EntityType.TRADER_LLAMA && otherEntity.getType() != EntityType.LLAMA) 
					{
						if (otherEntity.getUniqueId().equals(horseIdentifier))
						{
							return otherEntity;
						}
					}
				}
			}
		}
		Location loc = getHorseLastSelectionPosition(horseIdentifier);
		if(horse == null) 
		{
			loc.getChunk().setForceLoaded(true);
			System.out.println("Horse in a not loaded chunk");
			if(loc.getChunk().load()) 
			{
				System.out.println("Chunk loaded");
				for (Entity otherEntity : loc.getChunk().getEntities())
				{
					if(otherEntity instanceof AbstractHorse)
					{
						if(otherEntity.getType() != EntityType.TRADER_LLAMA && otherEntity.getType() != EntityType.LLAMA) 
						{
							if (otherEntity.getUniqueId().equals(horseIdentifier))
							{
								loc.getChunk().setForceLoaded(false);
								if(loc.getChunk().unload()) System.out.println("Chunk unloaded");
								return otherEntity;
							}
						}
					}
				}
				loc.getChunk().setForceLoaded(false);
				if(loc.getChunk().unload()) System.out.println("Chunk unloaded");
			} 
			else 
			{
				System.out.println("Chunk could not be loaded");
				return null;
			}
	}
		return null;
	}
	
	public UUID getHorseByName(String horseName)
	{
		for (String horseIdentifierString : this.horsesConfig.getKeys(false))
		{
			String name = this.horsesConfig.getString(horseIdentifierString + ".Name");
			if ((name != null) && (name.equalsIgnoreCase(horseName)))
			{
				return UUID.fromString(horseIdentifierString);
			}
		}
		return null;
	}
	
	public UUID getOwnerForHorse(UUID horseIdentifier)
	{
		String ownerString = this.horsesConfig.getString(horseIdentifier.toString() + ".Owner");
		UUID ownerId = null;
		
		try
		{
			ownerId = UUID.fromString(ownerString);
		}
		catch(Exception ex)
		{
			return null;
		}
		
		return ownerId;
	}
	
	public void createCustomInventoryForHorse(UUID horseIdentifier) {
		// TODO - Create a custom inventory for the horse
		Inventory inv = Bukkit.createInventory(null, 32, plugin.getHorseManager().getNameForHorse(horseIdentifier) + "'s Inventory");
		isHorseCarryingChest(horseIdentifier);
		this.horsesConfig.set(horseIdentifier + ".IsCarryingChest", true);
	}
	
	public void removeCustomInventoryForHorse(UUID horseIdentifier) 
	{
		
	}
	
	public HashMap<Integer, Material> getHorseCustomInventory(UUID horseIdentifier) 
	{
		// TODO - Save horse custom inventory in config
		HashMap<Integer, Material> horseInv = new HashMap<Integer, Material>();
		return  horseInv;
	}
		
	public boolean isHorseCarryingChest(UUID horseIdentifier) 
	{
		return this.horsesConfig.getBoolean(horseIdentifier + "isCarryingChest");
	}
	
	public String getNameForHorse(UUID horseIdentifier)
	{
		return this.horsesConfig.getString(horseIdentifier.toString() + ".Name");
	}

	public boolean isHorseOwned(UUID horseIdentifier)
	{
		return this.horsesConfig.contains(horseIdentifier.toString() + ".Owner");
	}

	public void setHorseColor(UUID horseIdentifier, Horse.Color color)
	{
		this.horsesConfig.set(horseIdentifier.toString() + ".Color", color.name());
	}

	public void setOwnerForHorse(UUID horseIdentifier, UUID ownerId)
	{
		if (ownerId == null)
		{
			this.horsesConfig.set(horseIdentifier.toString(), null);
			
			AbstractHorse horse = (AbstractHorse) getHorseEntity(horseIdentifier);
			
			if (horse != null)
			{
				if(horse instanceof ChestedHorse) {
					ChestedHorse chestedHorse = (ChestedHorse) getHorseEntity(horseIdentifier);
					chestedHorse.setCarryingChest(false);
					chestedHorse.setCarryingChest(false);
				}
				horse.setCustomName(null);
				horse.setCustomNameVisible(false);
				horse.setOwner(null);
				horse.setTamed(false);
			}
		}
		else
		{
			AbstractHorse horse = (AbstractHorse) getHorseEntity(horseIdentifier);
			if (horse != null)
			{
				OfflinePlayer player = this.plugin.getServer().getOfflinePlayer(ownerId);

				horse.setOwner(player);
				horse.setTamed(true);
			}

			this.horsesConfig.set(horseIdentifier.toString() + ".Owner", ownerId.toString());
		}
	}

	public void setHorseStatistics(UUID horseIdentifier, Horse.Style style, double maxHealth, double jumpStrength, Horse.Color color)
	{
		this.horsesConfig.set(horseIdentifier.toString() + ".Style", style.name());
		this.horsesConfig.set(horseIdentifier.toString() + ".Variant", color.name());
		this.horsesConfig.set(horseIdentifier.toString() + ".MaxHealth", Double.valueOf(maxHealth));
		this.horsesConfig.set(horseIdentifier.toString() + ".JumpStrength", Double.valueOf(jumpStrength));
	}

	public List<UUID> getAllHorses()
	{
		List<UUID> horseList = new ArrayList<>();
		for (String horseIdentifierString : this.horsesConfig.getKeys(false))
		{
			UUID horseIdentifier = null;
			try
			{
				horseIdentifier = UUID.fromString(horseIdentifierString);
			}
			catch (Exception ex)
			{
				continue;
			}
			horseList.add(horseIdentifier);
		}
		return horseList;
	}

	public List<UUID> getHorsesForOwner(UUID ownerId)
	{
		List<UUID> horseList = new ArrayList<UUID>();
		for (String horseIdentifierString : this.horsesConfig.getKeys(false))
		{
			String horseOwnerString = this.horsesConfig.getString(horseIdentifierString + ".Owner");
			UUID horseOwnerId = null;

			try
			{
				horseOwnerId = UUID.fromString(horseOwnerString);
			}
			catch (Exception ex)
			{
				continue;
			}

			if (horseOwnerId.equals(ownerId))
			{
				UUID horseIdentifier = null;
				
				try
				{
					horseIdentifier = UUID.fromString(horseIdentifierString);
				}
				catch (Exception ex)
				{
					continue;
				}

				horseList.add(horseIdentifier);
			}
		}
		return horseList;
	}

	public void setHorseLastSelectionPosition(UUID horseIdentifier, Location location)
	{
		this.horsesConfig.set(horseIdentifier + ".LastSelection.X", Integer.valueOf(location.getBlockX()));
		this.horsesConfig.set(horseIdentifier + ".LastSelection.Y", Integer.valueOf(location.getBlockY()));
		this.horsesConfig.set(horseIdentifier + ".LastSelection.Z", Integer.valueOf(location.getBlockZ()));
		this.horsesConfig.set(horseIdentifier + ".LastSelection.World", location.getWorld().getName());
	}

	public Location getHorseLastSelectionPosition(UUID horseIdentifier)
	{
		Location location = null;
		String worldName = this.horsesConfig.getString(horseIdentifier + ".LastSelection.World");
		double x = this.horsesConfig.getInt(horseIdentifier + ".LastSelection.X");
		double y = this.horsesConfig.getInt(horseIdentifier + ".LastSelection.Y");
		double z = this.horsesConfig.getInt(horseIdentifier + ".LastSelection.Z");
		try
		{
			World world = this.plugin.getServer().getWorld(worldName);

			location = new Location(world, x, y, z);
		}
		catch (Exception ex)
		{
			this.plugin.log(ex.getMessage());
			return null;
		}
		return location;
	}

	public void setHorseForSale(UUID horseIdentifier, int sellingPrice)
	{
		if (sellingPrice <= 0)
		{
			this.horsesConfig.set(horseIdentifier + ".Price", null);
		}
		else
		{
			this.horsesConfig.set(horseIdentifier + ".Price", Integer.valueOf(sellingPrice));
		}
	}

	public int getHorsePrice(UUID horseIdentifier)
	{
		return this.horsesConfig.getInt(horseIdentifier + ".Price");
	}

	public UUID newHorse(String playerName, LivingEntity entity)
	{
		AbstractHorse horse = (AbstractHorse) entity;

		this.horsesConfig.set(horse.getUniqueId() + ".Name", "Horsy");
		if(horse instanceof Horse) {
			Horse horse1 = (Horse) entity;
			this.horsesConfig.set(horse.getUniqueId() + ".Color", horse1.getColor().name());
			this.horsesConfig.set(horse.getUniqueId() + ".Style", horse1.getStyle().name());
		}
		this.horsesConfig.set(horse.getUniqueId() + ".MaxHealth", horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		this.horsesConfig.set(horse.getUniqueId() + ".JumpStrength", Double.valueOf(horse.getJumpStrength()));

		return horse.getUniqueId();
	}

	public List<UUID> getHorseFriends(UUID horseIdentifier)
	{
		List<String> friends = this.horsesConfig.getStringList(horseIdentifier + ".Friends");
		List<UUID> friendList = new ArrayList<UUID>();
		
		if(friends==null)
		{
			return null;
		}
		
		UUID friendId = null;
		
		for(String friend : friends)
		{
			try
			{
				friendId = UUID.fromString(friend);
			}
			catch(Exception ex)
			{
				continue;
			}
			
			friendList.add(friendId);
		}
		
		return friendList;
	}

	public void addHorseFriend(UUID horseIdentifier, UUID playerId)
	{
		List<String> friendList = this.horsesConfig.getStringList(horseIdentifier + ".Friends");
		UUID friendId = null;
		
		for (String friendString : friendList)
		{
			try
			{
				friendId = UUID.fromString(friendString);
			}
			catch(Exception ex)
			{
				continue;
			}

			if (friendId.equals(playerId))
			{
				return;
			}
		}

		friendList.add(playerId.toString());

		this.horsesConfig.set(horseIdentifier + ".Friends", friendList);
	}

	public void removeHorseFriend(UUID horseIdentifier, UUID playerId)
	{
		List<String> friendList = this.horsesConfig.getStringList(horseIdentifier + ".Friends");
		UUID friendId = null;

		for (String friendString : friendList)
		{
			try
			{
				friendId = UUID.fromString(friendString);
			}
			catch(Exception ex)
			{
				continue;
			}

			if (friendId.equals(playerId))
			{
				friendList.remove(playerId.toString());
				this.horsesConfig.set(horseIdentifier + ".Friends", friendList);
				return;				
			}
		}		
	}

	public void clearHorseFriends(UUID horseIdentifier)
	{
		this.horsesConfig.set(horseIdentifier + ".Friends", null);
	}

	public boolean isHorseFriend(UUID horseIdentifier, UUID playerId)
	{
		List<String> friendList = this.horsesConfig.getStringList(horseIdentifier + ".Friends");
		UUID friendId = null;
		
		for (String friendString : friendList)
		{
			try
			{
				friendId = UUID.fromString(friendString);
			}
			catch(Exception ex)
			{
				continue;
			}
						
			if (friendId.equals(playerId))
			{
				return true;
			}
		}
		
		return false;
	}
}
