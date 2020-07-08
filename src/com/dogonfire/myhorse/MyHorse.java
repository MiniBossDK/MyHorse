package com.dogonfire.myhorse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MyHorse extends JavaPlugin
{
	private static MyHorse						plugin;
	public boolean								vaultEnabled			= false;
	private LanguageManager						languageManager			= null;
	private PermissionsManager					permissionsManager		= null;
	private StableManager						stableManager			= null;
	private HorseManager						horseManager			= null;
	private HorseOwnerManager					ownerManager			= null;
	private FileConfiguration					config					= null;
	private Commands							commands				= null;
	public boolean								debug					= false;
	public boolean								downloadLanguageFile	= true;
	public boolean								horseDamageDisabled		= true;
	public boolean								allowChestsOnAllHorses	= true;
	public boolean								autoClaim				= true;
	public boolean								economyEnabled			= false;
	public boolean								useUpdateNotifications	= true;
	public boolean								useHorseTeleportation	= false;
	public boolean								metricsOptOut			= false;
	private List<UUID>							allowedWorlds			= new ArrayList<UUID>();
	private List<EntityDamageEvent.DamageCause>	damageProtection		= new ArrayList<DamageCause>();
	public String								serverName				= "Your Server";
	public String								language				= "english";
	private Economy								economy					= null;
	public int									maxHorsesPrPlayer		= 3;

	public static final String					NMS						= "v1_16_R1";
	
	public boolean isCombatibleServer()
	{
		try
		{
			Class<?> theClass = Class.forName("net.minecraft.server." + NMS + ".ItemStack");
		
			return theClass != null;
		}
		catch(Exception ex)
		{
			return false;
		}		
	}
	
	public Economy getEconomy()
	{
		return this.economy;
	}

	public HorseManager getHorseManager()
	{
		return this.horseManager;
	}

	public HorseOwnerManager getOwnerManager()
	{
		return this.ownerManager;
	}

	public StableManager getStableManager()
	{
		return this.stableManager;
	}

	public LanguageManager getLanguageManager()
	{
		return this.languageManager;
	}

	public PermissionsManager getPermissionsManager()
	{
		return this.permissionsManager;
	}

	public void log(String message)
	{
		Logger.getLogger("minecraft").info("[" + getDescription().getFullName() + "] " + message);
	}

	public void logDebug(String message)
	{
		if (this.debug)
		{
			Logger.getLogger("minecraft").info("[" + getDescription().getFullName() + "] " + message);
		}
	}

	public void sendInfo(Player player, String message)
	{
		player.sendMessage(ChatColor.AQUA + message);
	}

	public void reloadSettings()
	{
		reloadConfig();

		loadSettings();
	}

	public void loadSettings()
	{
		this.config = getConfig();

		this.allowChestsOnAllHorses = this.config.getBoolean("Settings.AllowChestsOnAllHorses", false);
		this.metricsOptOut = this.config.getBoolean("Settings.MetricsOptOut", false);
		this.useUpdateNotifications = this.config.getBoolean("Settings.DisplayUpdateNotifications", true);
		this.debug = this.config.getBoolean("Settings.Debug", false);
		this.downloadLanguageFile = this.config.getBoolean("Settings.DownloadLanguageFile", true);

		this.serverName = this.config.getString("Settings.ServerName", "Your Server");

		List<String> damageList = this.config.getStringList("Settings.DamageProtections");
		if ((damageList == null) || (damageList.size() == 0))
		{
			log("No damage protection settings found in config file.");
			log("Writing default damage protections to config.");

			damageList = new ArrayList<String>();

			damageList.add(EntityDamageEvent.DamageCause.PROJECTILE.name());
			damageList.add(EntityDamageEvent.DamageCause.POISON.name());
			damageList.add(EntityDamageEvent.DamageCause.MELTING.name());
			damageList.add(EntityDamageEvent.DamageCause.MAGIC.name());
			damageList.add(EntityDamageEvent.DamageCause.CUSTOM.name());
			damageList.add(EntityDamageEvent.DamageCause.DROWNING.name());
			damageList.add(EntityDamageEvent.DamageCause.FIRE.name());
			damageList.add(EntityDamageEvent.DamageCause.FIRE_TICK.name());
			damageList.add(EntityDamageEvent.DamageCause.ENTITY_ATTACK.name());
			damageList.add(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION.name());
			damageList.add(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION.name());
			damageList.add(EntityDamageEvent.DamageCause.LIGHTNING.name());
			damageList.add(EntityDamageEvent.DamageCause.LAVA.name());

			this.config.set("Settings.DamageProtections", damageList);

			saveConfig();
		}
		damageList = this.config.getStringList("Settings.DamageProtections");
		this.damageProtection.clear();

		this.useHorseTeleportation = this.config.getBoolean("Settings.HorseTeleportationEnabled", false);
		for (String damageName : damageList)
		{
			this.damageProtection.add(EntityDamageEvent.DamageCause.valueOf(damageName));
			logDebug(damageName + " can NOT damage owned horses");
		}
		List<String> worldNames = this.config.getStringList("Settings.Worlds");
		if ((worldNames == null) || (worldNames.size() == 0))
		{
			log("No worlds found in config file.");
			worldNames = new ArrayList<String>();
			for (World world : getServer().getWorlds())
			{
				this.allowedWorlds.add(world.getUID());
				worldNames.add(world.getName());
				log("Enabed in world '" + world.getName() + "'");
			}
			this.config.set("Settings.Worlds", worldNames);
			saveConfig();
		}
		else
		{
			for (String worldName : worldNames)
			{
				World world = getServer().getWorld(worldName);
				if (world == null)
				{
					log("Could NOT enable MyHorse in world '" + worldName + "'. No world found with such name.");
				}
				else
				{
					this.allowedWorlds.add(world.getUID());
					log("Enabled in '" + worldName + "'");
				}
			}
			if (worldNames.size() == 0)
			{
				log("WARNING: No worlds are set in config file. MyHorse is DISABLED on this server!");
			}
		}
		
		for (String groupName : getPermissionsManager().getGroups())
		{
			if (this.config.getString("Groups." + groupName) == null)
			{
				this.config.set("Groups." + groupName + ".HorseNameColor", ChatColor.GOLD.name());
				this.config.set("Groups." + groupName + ".MaximumHorses", Integer.valueOf(5));
			}
		}

	}

	public ChatColor getHorseNameColorForPlayer(UUID playerId)
	{
		if (playerId == null)
		{
			return ChatColor.GOLD;
		}

		String groupName;

		try
		{
			groupName = getPermissionsManager().getGroup(this.getServer().getOfflinePlayer(playerId).getName());
		}
		catch (Exception ex)
		{
			log("ERROR getting group name for player " + this.getServer().getOfflinePlayer(playerId).getName() + ":" + ex.getMessage());
			return ChatColor.GOLD;
		}

		ChatColor nameColor;

		try
		{
			nameColor = ChatColor.valueOf(this.config.getString("Groups." + groupName + ".HorseNameColor"));
		}
		catch (Exception ex)
		{
			log("Could not get horse name color from player " + this.getServer().getOfflinePlayer(playerId).getName() + "'s group '" + groupName + "' in config.yml!");
			nameColor = ChatColor.GOLD;
		}
		return nameColor;
	}

	public int getMaximumHorsesForPlayer(String playerName)
	{
		return this.config.getInt("Groups." + getPermissionsManager().getGroup(playerName) + ".MaximumHorses");
	}

	public void saveSettings()
	{
		this.config.set("Settings.ServerName", this.serverName);
		this.config.set("Settings.Debug", Boolean.valueOf(this.debug));
		this.config.set("Settings.DownloadLanguageFile", Boolean.valueOf(this.downloadLanguageFile));
		this.config.set("Settings.InvulnerableHorses", Boolean.valueOf(this.horseDamageDisabled));
		this.config.set("Settings.HorseTeleportationEnabled", Boolean.valueOf(this.useHorseTeleportation));
		this.config.set("Settings.AllowChestsOnAllHorses", Boolean.valueOf(this.allowChestsOnAllHorses));
		this.config.set("Settings.DisplayUpdateNotifications", Boolean.valueOf(this.useUpdateNotifications));
		this.config.set("Settings.MetricsOptOut", Boolean.valueOf(this.metricsOptOut));

		saveConfig();
	}

	public void onEnable()
	{
		if(!this.isCombatibleServer())
		{
			log("* Your server is not compatible with the MyHorse plugin");
			log("* This MyHorse plugin is only compatible with a " + NMS + " server");
			this.setEnabled(false);
			return;
		}
		plugin = this;
		this.languageManager = new LanguageManager(this);
		this.permissionsManager = new PermissionsManager(this);
		this.horseManager = new HorseManager(this);
		this.ownerManager = new HorseOwnerManager(this);
		this.stableManager = new StableManager(this);
		this.commands = new Commands(this);
		this.permissionsManager.load();

		loadSettings();
		saveSettings();

		this.languageManager.load();
		this.stableManager.load();
		this.horseManager.load();
		this.ownerManager.load();
		
		getServer().getPluginManager().registerEvents(new EventListener(this), this);

		Runnable horseManagerSavetask = new Runnable()
		{
			public void run()
			{
				MyHorse.this.horseManager.save();
			}
		};
		getServer().getScheduler().runTaskTimerAsynchronously(this, horseManagerSavetask, 20L, 2400L);

		Runnable horseOwnerSavetask = new Runnable()
		{
			public void run()
			{
				MyHorse.this.ownerManager.save();
			}
		};
		getServer().getScheduler().runTaskTimerAsynchronously(this, horseOwnerSavetask, 1200L, 2400L);

		if (!this.metricsOptOut)
		{
			try
			{
				Metrics metrics = new Metrics(this);
				
				com.dogonfire.myhorse.Metrics.Graph serversGraph = metrics.createGraph("Servers");

				serversGraph.addPlotter(new Metrics.Plotter("Servers")
				{
					@Override
					public int getValue()
					{
						return 1;	
					}
				});

				serversGraph.addPlotter(new Metrics.Plotter("Using vault")
				{
					@Override
					public int getValue()
					{
						if(MyHorse.this.economyEnabled)
						{
							return 1;
						}
						
						return 0;	
					}
				});

				com.dogonfire.myhorse.Metrics.Graph permissionsUsedGraph = metrics.createGraph("Permission plugins used");

				permissionsUsedGraph.addPlotter(new Metrics.Plotter("Using PermissionsBukkit")
				{
					public int getValue()
					{
						if (MyHorse.this.getPermissionsManager().getPermissionPluginName().equals("PermissionsBukkit"))
						{
							return 1;
						}
						return 0;
					}
				});
				permissionsUsedGraph.addPlotter(new Metrics.Plotter("Using PermissionsEx")
				{
					public int getValue()
					{
						if (MyHorse.this.getPermissionsManager().getPermissionPluginName().equals("PermissionsEx"))
						{
							return 1;
						}
						return 0;
					}
				});
				permissionsUsedGraph.addPlotter(new Metrics.Plotter("Using GroupManager")
				{
					public int getValue()
					{
						if (MyHorse.this.getPermissionsManager().getPermissionPluginName().equals("GroupManager"))
						{
							return 1;
						}
						return 0;
					}
				});
				permissionsUsedGraph.addPlotter(new Metrics.Plotter("Using bPermissions")
				{
					public int getValue()
					{
						if (MyHorse.this.getPermissionsManager().getPermissionPluginName().equals("bPermissions"))
						{
							return 1;
						}
						return 0;
					}
				});
				
				metrics.start();
			}
			catch (Exception ex)
			{
				log("Failed to submit metrics :-(");
			}
		}

		PluginManager pm = getServer().getPluginManager();

		// Check for Vault
		if (pm.getPlugin("Vault") != null && pm.getPlugin("Vault").isEnabled())
		{
			log("Vault detected.");

			RegisteredServiceProvider<Permission> permissionProvider = this.getServer().getServicesManager().getRegistration(Permission.class);
			RegisteredServiceProvider<Chat> chatProvider = this.getServer().getServicesManager().getRegistration(Chat.class);
			if (permissionProvider == null || chatProvider == null)
			{
				plugin.log("A permission provider or a chat provider was not found! Will not enable the vault integration!");
			}
			else
			{
				this.vaultEnabled = true;
			}
		}
		else
		{
			log("Vault not found.");
		}
	}
	public void onDisable()
	{
		if(!this.isCombatibleServer())
		{
			return;		
		}
		
		reloadSettings();

		saveSettings();

		this.stableManager.save();
		this.horseManager.save();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		return this.commands.onCommand(sender, cmd, label, args);
	}
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return commands.onTabComplete(sender, cmd, label, args);
	}

	public boolean isAllowedInWorld(World world)
	{
		return this.allowedWorlds.contains(world.getUID());
	}

	public boolean isDamageProtection(EntityDamageEvent.DamageCause damage)
	{
		return this.damageProtection.contains(damage);
	}
}