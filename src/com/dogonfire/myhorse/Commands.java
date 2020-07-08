package com.dogonfire.myhorse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Player;

public class Commands
{
	private MyHorse	plugin	= null;

	Commands(MyHorse p)
	{
		this.plugin = p;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = null;
		if ((sender instanceof Player))
		{
			player = (Player) sender;
		}
		if (player == null)
		{
			if ((cmd.getName().equalsIgnoreCase("myhorse")) || (cmd.getName().equalsIgnoreCase("mh")))
			{
				if (args.length == 0)
				{
					CommandMyHorse(sender);
					this.plugin.log(sender.getName() + " /mh");
					return true;
				}
				if (args.length == 1)
				{
					if (args[0].equalsIgnoreCase("reload"))
					{
						this.plugin.reloadSettings();
						this.plugin.loadSettings();

						this.plugin.getHorseManager().load();
						this.plugin.getStableManager().load();

						return true;
					}
				}
			}
			return true;
		}
		if ((cmd.getName().equalsIgnoreCase("myhorse")) || (cmd.getName().equalsIgnoreCase("mh")))
		{
			if (args.length == 0)
			{
				CommandMyHorse(sender);
				this.plugin.log(sender.getName() + " /mh");
				return true;
			}
			if (args[0].equalsIgnoreCase("spawn"))
			{
				if (CommandSpawn(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse spawn");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("list"))
			{
				if (CommandList(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse list");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("comehere"))
			{
				if (CommandComeHere(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse comehere");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("info"))
			{
				if (CommandInfo(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse info");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("claim"))
			{
				if (CommandClaim(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse claim");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("lock"))
			{
				CommandLock(player, args);

				return true;
			}
			if (args[0].equalsIgnoreCase("unlock"))
			{
				if (CommandUnlock(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse unlock");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("goaway"))
			{
				if (CommandGoAway(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse goaway");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("kill"))
			{
				if (CommandKill(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse kill");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("sell"))
			{
				if (CommandSell(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse sell");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("reload"))
			{
				if (CommandReload(sender))
				{
					this.plugin.log(sender.getName() + " /myhorse reload");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("help"))
			{
				if (CommandHelp(sender))
				{
					this.plugin.log(sender.getName() + " /myhorse help");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("list"))
			{
				CommandList(player, args);

				return true;
			}
			if (args[0].equalsIgnoreCase("lock"))
			{
				CommandLock(player, args);

				return true;
			}
			if ((args[0].equalsIgnoreCase("goto")) && (this.plugin.useHorseTeleportation))
			{
				if (CommandGoto(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse goto");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("select"))
			{
				if (CommandSelect(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse select");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("addfriend"))
			{
				if (CommandAddFriend(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse addfriend");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("removefriend"))
			{
				if (CommandRemoveFriend(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse removefriend");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("kill"))
			{
				if (CommandKill(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse kill");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("setowner"))
			{
				if (CommandSetOwner(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse setowner");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("sell"))
			{
				if (CommandSell(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse sell");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("name"))
			{
				if (CommandSetName(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse name");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("goaway"))
			{
				if (CommandGoAway(player, args))
				{
					this.plugin.log(sender.getName() + " /myhorse goaway");
				}
				return true;
			}
			return true;
		}
		return true;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		List<String> horses = new ArrayList<String>();
		if((cmd.getName().equalsIgnoreCase("myhorse")) || (cmd.getName().equalsIgnoreCase("mh"))) {
			if(args.length == 0) {
				// TODO - return a list of commands here. Make a list of commands.
			}
			// TODO - Use a list of command names for nicer code
			if (args[0].equalsIgnoreCase("kill"))
			{
				List<UUID> horsesOwned = this.plugin.getHorseManager().getHorsesForOwner(player.getUniqueId());
				for(UUID horseId : horsesOwned) {
					horses.add(this.plugin.getHorseManager().getNameForHorse(horseId));
				}
				return horses;
			}
			if (args[0].equalsIgnoreCase("sell"))
			{
				List<UUID> horsesOwned = this.plugin.getHorseManager().getHorsesForOwner(player.getUniqueId());
				for(UUID horseId : horsesOwned) {
					horses.add(this.plugin.getHorseManager().getNameForHorse(horseId));
				}
				return horses;
			}
			if (args[0].equalsIgnoreCase("lock"))
			{
				List<UUID> horsesOwned = this.plugin.getHorseManager().getHorsesForOwner(player.getUniqueId());
				for(UUID horseId : horsesOwned) {
					horses.add(this.plugin.getHorseManager().getNameForHorse(horseId));
				}
				return horses;
			}
			if ((args[0].equalsIgnoreCase("goto")) && (this.plugin.useHorseTeleportation))
			{
				List<UUID> horsesOwned = this.plugin.getHorseManager().getHorsesForOwner(player.getUniqueId());
				for(UUID horseId : horsesOwned) {
					horses.add(this.plugin.getHorseManager().getNameForHorse(horseId));
				}
				return horses;
			}
			if (args[0].equalsIgnoreCase("select"))
			{
				List<UUID> horsesOwned = this.plugin.getHorseManager().getHorsesForOwner(player.getUniqueId());
				for(UUID horseId : horsesOwned) {
					horses.add(this.plugin.getHorseManager().getNameForHorse(horseId));
				}
				return horses;
			}
			if (args[0].equalsIgnoreCase("kill"))
			{
				List<UUID> horsesOwned = this.plugin.getHorseManager().getHorsesForOwner(player.getUniqueId());
				for(UUID horseId : horsesOwned) {
					horses.add(this.plugin.getHorseManager().getNameForHorse(horseId));
				}
				return horses;
			}
			if (args[0].equalsIgnoreCase("sell"))
			{
				List<UUID> horsesOwned = this.plugin.getHorseManager().getHorsesForOwner(player.getUniqueId());
				for(UUID horseId : horsesOwned) {
					horses.add(this.plugin.getHorseManager().getNameForHorse(horseId));
				}
				return horses;
			}
		}
		return null;
	}

	private boolean CommandMyHorse(CommandSender sender)
	{
		sender.sendMessage(ChatColor.YELLOW + "------------------ " + this.plugin.getDescription().getFullName() + " ------------------");
		sender.sendMessage(ChatColor.AQUA + "By DogOnFire");
		sender.sendMessage("");

		sender.sendMessage(ChatColor.AQUA + "Use " + ChatColor.WHITE + "/myhorse help" + ChatColor.AQUA + " for a list of commands");

		return true;
	}

	private boolean CommandHelp(CommandSender sender)
	{
		if ((sender != null) && (!sender.isOp()) && (!this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.help")))
		{
			sender.sendMessage(ChatColor.RED + "You do not have permission for that");
			return false;
		}
		sender.sendMessage(ChatColor.YELLOW + "------------------ " + this.plugin.getDescription().getFullName() + " ------------------");
		sender.sendMessage(ChatColor.AQUA + "/myhorse" + ChatColor.WHITE + " - Basic info");
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.comehere")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse comehere" + ChatColor.WHITE + " - Teleports your selected horse to you");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.name")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse name <name>" + ChatColor.WHITE + " - Gives your selected horse a name");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.lock")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse lock" + ChatColor.WHITE + " - Locks your horse");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.unlock")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse unlock" + ChatColor.WHITE + " - Unlocks your horse");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.addfriend")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse addfriend" + ChatColor.WHITE + " - Add friend to your horse");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.removefriend")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse removefriend" + ChatColor.WHITE + " - Removes a friend from your horse");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.setpublic")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse setpublic" + ChatColor.WHITE + " - Sets public access for your horse");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.list")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse list" + ChatColor.WHITE + " - Shows a list of your owned horses");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.goto")))
		{
			if (this.plugin.useHorseTeleportation)
			{
				sender.sendMessage(ChatColor.AQUA + "/myhorse goto <id>" + ChatColor.WHITE + " - Teleports you to one of your owned horses");
			}
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.setowner")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse setowner <ownername>" + ChatColor.WHITE + " - Set a new owner for your selected horse!");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.select")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse select <id>" + ChatColor.WHITE + " - Selects one of your horses!");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.info")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse info" + ChatColor.WHITE + " - Display info about your horse!");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.sell")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse sell <price>" + ChatColor.WHITE + " - Set your selected horse for sale!");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.claim")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse claim" + ChatColor.WHITE + " - Claims a un-owned horse");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.goaway")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse goaway" + ChatColor.WHITE + " - Sets your horse free!");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.kill")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse kill" + ChatColor.WHITE + " - Kill the selected horse!");
		}
		if ((sender.isOp()) || (this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.spawn")))
		{
			sender.sendMessage(ChatColor.AQUA + "/myhorse spawn <mule|skeleton|normal|undead> <baby>" + ChatColor.WHITE + " - Spawns a horse!");
		}
		return true;
	}

	private boolean CommandReload(CommandSender sender)
	{
		if ((!sender.isOp()) && (!this.plugin.getPermissionsManager().hasPermission((Player) sender, "myhorse.reload")))
		{
			sender.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		this.plugin.loadSettings();

		this.plugin.getStableManager().load();
		this.plugin.getHorseManager().load();

		sender.sendMessage(ChatColor.YELLOW + this.plugin.getDescription().getFullName() + ": " + ChatColor.WHITE + "Reloaded configuration.");
		this.plugin.log(sender.getName() + " /mh reload");

		return true;
	}

	private boolean CommandSpawn(Player player, String[] args)
	{
		if ((!player.isOp()) && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.spawn")))
		{
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if ((args.length < 1) || (args.length > 3))
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse spawn");
			player.sendMessage(ChatColor.RED + "Usage: /myhorse spawn <mule|donkey|skeleton|normal|undead>");
			player.sendMessage(ChatColor.RED + "Usage: /myhorse spawn <mule|donkey|skeleton|normal|undead> baby");
			return false;
		}
		
		EntityType variant = null;
		
		if (args.length > 1)
		{
			if (args[1].equalsIgnoreCase("skeleton"))
			{
				variant = EntityType.SKELETON_HORSE;
			}
			else if (args[1].equalsIgnoreCase("mule"))
			{
				variant = EntityType.MULE;
			}
			else if (args[1].equalsIgnoreCase("donkey"))
			{
				variant = EntityType.DONKEY;
			}
			else if (args[1].equalsIgnoreCase("undead"))
			{
				variant = EntityType.ZOMBIE_HORSE;
			}
			else if (args[1].equalsIgnoreCase("normal"))
			{
				variant = EntityType.HORSE;
			} else {
				player.sendMessage(ChatColor.RED + "Invalid type of horse");
				return false;
			}
		}
		
		this.plugin.getLanguageManager().setName(variant.name());

		boolean baby = false;
		if (args.length == 2)
		{
			if (args[1].equals("baby"))
			{
				baby = true;
				this.plugin.getLanguageManager().setName("baby " + variant.name());
			}
		}
		else if (args.length == 3)
		{
			if (args[2].equals("baby"))
			{
				baby = true;
				this.plugin.getLanguageManager().setName("baby " + variant.name());
			}
		}

		this.plugin.getHorseManager().newHorse(player, variant, baby);

		player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.SpawnedHorse, ChatColor.GREEN));

		return true;
	}

	private boolean CommandSetName(Player player, String[] args)
	{
		if ((!player.isOp()) && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.name")))
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if (args.length != 2)
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse name <horsename>");
			return false;
		}
		String horseName = args[1];
		for (int n = 2; n < args.length; n++)
		{
			horseName = horseName + " " + args[n];
		}
		if ((horseName.length() < 2) || (horseName.equals("null")))
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.InvalidHorseName, ChatColor.DARK_RED));
			return false;
		}
		if (this.plugin.getHorseManager().ownedHorseWithName(player.getName(), horseName).booleanValue())
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.AlreadyHasHorseWithThatName, ChatColor.DARK_RED));
			return false;
		}
		UUID horseIdentifier = this.plugin.getOwnerManager().getCurrentHorseIdentifierForPlayer(player.getUniqueId());
		if (horseIdentifier == null)
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoHorseSelected, ChatColor.DARK_RED));
			return false;
		}
		AbstractHorse horse = (AbstractHorse) this.plugin.getHorseManager().getHorseEntity(horseIdentifier);
		if (horse == null)
		{
			return false;
		}
		this.plugin.getHorseManager().setNameForHorse(horseIdentifier, horseName);

		horse.setCustomName(this.plugin.getHorseNameColorForPlayer(player.getUniqueId()) + horseName);
		horse.setCustomNameVisible(true);

		this.plugin.getLanguageManager().setName(horseName);
		player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.SetHorseName, ChatColor.GREEN));

		return true;
	}

	private boolean CommandLock(Player player, String[] args)
	{
		if ((!player.isOp()) && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.lock")))
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if ((args.length < 1) || (args.length > 2))
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse lock");
			player.sendMessage(ChatColor.RED + "Usage: /myhorse lock <horsename>");
			return false;
		}
		UUID horseIdentifier = getHorseIdentifierFromArgs(player, args);
		if (horseIdentifier == null)
		{
			return false;
		}
		this.plugin.getHorseManager().setLockedForHorse(horseIdentifier, true);

		this.plugin.getLanguageManager().setName(this.plugin.getHorseManager().getNameForHorse(horseIdentifier));
		player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.HorseLocked, ChatColor.GREEN));

		return true;
	}

	private boolean CommandUnlock(Player player, String[] args)
	{
		if ((!player.isOp()) && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.unlock")))
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if ((args.length < 1) || (args.length > 2))
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse unlock");
			player.sendMessage(ChatColor.RED + "Usage: /myhorse unlock <horsename>");
			return false;
		}
		UUID horseIdentifier = getHorseIdentifierFromArgs(player, args);
		if (horseIdentifier == null)
		{
			return false;
		}
		this.plugin.getHorseManager().setLockedForHorse(horseIdentifier, false);

		this.plugin.getLanguageManager().setName(this.plugin.getHorseManager().getNameForHorse(horseIdentifier));
		player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.HorseUnlocked, ChatColor.GREEN));

		return true;
	}

	private boolean CommandComeHere(Player player, String[] args)
	{
		if ((!player.isOp()) && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.comehere")))
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		
		if ((args.length < 1) || (args.length > 2))
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse comehere");
			player.sendMessage(ChatColor.RED + "Usage: /myhorse comehere <horsename>");
			return false;
		}
		
		UUID horseIdentifier = getHorseIdentifierFromArgs(player, args);
		if (horseIdentifier == null)
		{
			return false;
		}
		
		// TODO - Fix using Fido's chunkloading method from https://github.com/DoggyCraftDK/MyDog/tree/master/src/dk/fido2603/mydog
		AbstractHorse horse = (AbstractHorse) this.plugin.getHorseManager().getHorseEntity(horseIdentifier);
		
		if (horse == null)
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.YourHorseIsFarAway, ChatColor.DARK_RED));
			return false;
		}
		
		Location source = horse.getLocation();
		Location destination = player.getLocation();
		if (!horse.teleport(destination))
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.CouldNotTeleportYourHorse, ChatColor.DARK_RED));
			return false;
		}
		destination.getWorld().save();
		
		if (source.getWorld().getUID() != horse.getWorld().getUID())
		{
			source.getWorld().save();
		}
		
		this.plugin.getHorseManager().setHorseLastSelectionPosition(horseIdentifier, destination);

		this.plugin.getLanguageManager().setName(this.plugin.getHorseManager().getNameForHorse(horseIdentifier));
		player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.ComeHere, ChatColor.GREEN));

		return true;
	}

	private boolean CommandGoAway(Player player, String[] args)
	{
		if ((!player.isOp()) && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.goaway")))
		{
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if ((args.length < 1) || (args.length > 2))
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse goaway");
			player.sendMessage(ChatColor.RED + "Usage: /myhorse goaway <horsename>");
			return false;
		}
		UUID horseIdentifier = getHorseIdentifierFromArgs(player, args);
		if (horseIdentifier == null)
		{
			return false;
		}
		AbstractHorse horse = (AbstractHorse) this.plugin.getHorseManager().getHorseEntity(horseIdentifier);
		if (horse != null)
		{
			horse.setTamed(false);
		}
		this.plugin.getLanguageManager().setName(this.plugin.getHorseManager().getNameForHorse(horseIdentifier));
		player.sendMessage(ChatColor.GREEN + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.YouSetYourHorseFree, ChatColor.GREEN));

		this.plugin.getHorseManager().setOwnerForHorse(horseIdentifier, null);

		return true;
	}

	private UUID getHorseIdentifierFromArgs(Player player, String[] args)
	{
		UUID horseIdentifier = null;
		if (args.length == 1)
		{
			horseIdentifier = this.plugin.getOwnerManager().getCurrentHorseIdentifierForPlayer(player.getUniqueId());
			if (horseIdentifier == null)
			{
				player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoHorseSelected, ChatColor.DARK_RED));
				return null;
			}
		}
		else
		{
			try
			{
				horseIdentifier = this.plugin.getHorseManager().getHorseByName(args[1]);
			}
			catch (Exception nameException)
			{
				int horseIndex = 0;
				try
				{
					horseIndex = Integer.parseInt(args[1]) - 1;
				}
				catch (Exception indexException)
				{
					this.plugin.getLanguageManager().setName(args[1]);
					if (StringUtils.isAlphanumeric(args[1]))
					{
						player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoHorseWithSuchName, ChatColor.DARK_RED));
					}
					else
					{
						player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoHorseWithSuchID, ChatColor.DARK_RED));
					}
					return null;
				}
				List<UUID> horseList = null;
				if (this.plugin.getPermissionsManager().hasPermission(player, "myhorse.admin"))
				{
					horseList = this.plugin.getHorseManager().getAllHorses();
				}
				else
				{
					horseList = this.plugin.getHorseManager().getHorsesForOwner(player.getUniqueId());
				}
				if ((horseIndex < 0) || (horseIndex >= horseList.size()))
				{
					player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.InvalidCommand, ChatColor.DARK_RED));
					return null;
				}
				horseIdentifier = (UUID) horseList.get(horseIndex);
			}
		}
		return horseIdentifier;
	}

	private boolean CommandKill(Player player, String[] args)
	{
		if ((!player.isOp()) && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.kill")))
		{
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if ((args.length < 1) || (args.length > 2))
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse kill");
			player.sendMessage(ChatColor.RED + "Usage: /myhorse kill <horsename>");
			return false;
		}
		UUID horseIdentifier = getHorseIdentifierFromArgs(player, args);
		if (horseIdentifier == null)
		{
			return false;
		}
		Entity horse = this.plugin.getHorseManager().getHorseEntity(horseIdentifier);
		if (horse != null)
		{
			horse.remove();
		}
		String horseName = this.plugin.getHorseManager().getNameForHorse(horseIdentifier);

		this.plugin.getHorseManager().setOwnerForHorse(horseIdentifier, null);
		this.plugin.getOwnerManager().setCurrentHorseIdentifierForPlayer(player.getUniqueId(), null);

		this.plugin.getLanguageManager().setName(horseName);
		player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.YouKilledHorse, ChatColor.GREEN));

		return true;
	}

	private boolean CommandSetOwner(Player player, String[] args)
	{
		if ((!player.isOp()) && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.setowner")))
		{
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if (args.length != 2)
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse setowner <playername>");
			return false;
		}
		UUID horseIdentifier = this.plugin.getOwnerManager().getCurrentHorseIdentifierForPlayer(player.getUniqueId());
		if (horseIdentifier == null)
		{
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoHorseSelected, ChatColor.DARK_RED));
			return false;
		}
		String ownerName = args[1];

		Player ownerPlayer = this.plugin.getServer().getPlayer(ownerName);
		if (ownerPlayer == null)
		{
			this.plugin.getLanguageManager().setPlayerName(ownerName);
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.ThatPlayerIsNotOnline, ChatColor.DARK_RED));
			return false;
		}
		int maxHorses = this.plugin.getMaximumHorsesForPlayer(ownerPlayer.getName());
		int numberOfHorses = this.plugin.getHorseManager().getHorsesForOwner(ownerPlayer.getUniqueId()).size();
		if ((maxHorses > 0) && (numberOfHorses >= maxHorses))
		{
			this.plugin.getLanguageManager().setPlayerName(ownerPlayer.getName());
			this.plugin.getLanguageManager().setAmount(String.valueOf(maxHorses));
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.PlayerCannotHaveMoreHorses, ChatColor.DARK_RED));
			return false;
		}
		AbstractHorse horse = (AbstractHorse) this.plugin.getHorseManager().getHorseEntity(horseIdentifier);
		String horseName = this.plugin.getHorseManager().getNameForHorse(horseIdentifier);
		if (horse == null)
		{
			horse = this.plugin.getHorseManager().spawnHorse(horseIdentifier, this.plugin.getHorseManager().getHorseLastSelectionPosition(horseIdentifier));
			horseIdentifier = horse.getUniqueId();
			this.plugin.getOwnerManager().setCurrentHorseIdentifierForPlayer(player.getUniqueId(), horseIdentifier);
		}
		horse.setOwner(ownerPlayer);
		horse.setCustomName(this.plugin.getHorseNameColorForPlayer(ownerPlayer.getUniqueId()) + horseName);
		horse.setCustomNameVisible(true);

		this.plugin.getOwnerManager().setCurrentHorseIdentifierForPlayer(player.getUniqueId(), null);

		this.plugin.getHorseManager().setOwnerForHorse(horseIdentifier, ownerPlayer.getUniqueId());

		this.plugin.getLanguageManager().setName(horseName);
		this.plugin.getLanguageManager().setPlayerName(ownerName);

		player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.YouSetOwnerForHorse, ChatColor.GREEN));
		ownerPlayer.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.YouAreNewOwnerOfHorse, ChatColor.GREEN));

		return true;
	}

	private boolean CommandList(Player player, String[] args)
	{
		if (!player.isOp() && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.list")))
		{
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if (args.length < 1 || args.length > 2)
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse list");
			player.sendMessage(ChatColor.RED + "Usage: /myhorse list <page number>");
			return false;
		}
		List<UUID> horseList = null;
		if (this.plugin.getPermissionsManager().hasPermission(player, "myhorse.admin"))
		{
			horseList = this.plugin.getHorseManager().getAllHorses();
		}
		else
		{
			horseList = this.plugin.getHorseManager().getHorsesForOwner(player.getUniqueId());
		}
		
		int totalHorses = horseList.size();
		int pageIndex = 0;
		int toIndex = 0;
		if (args.length > 1)
		{
			try
			{
				pageIndex = Integer.parseInt(args[1]);
			}
			catch (Exception ex)
			{
				player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.InvalidCommand, ChatColor.DARK_RED));
				return false;
			}
			if (pageIndex * 15 < horseList.size() - 1)
			{
				toIndex = pageIndex * 15 + 15;
				if (toIndex >= horseList.size())
				{
					toIndex = horseList.size() - 1;
				}
				horseList = horseList.subList(pageIndex * 15, toIndex);
			}
			else
			{
				pageIndex = 0;
			}
		}
		else if (horseList.size() > 16)
		{
			horseList = horseList.subList(0, 15);
		}
		
		this.plugin.getLanguageManager().setAmount("" + totalHorses);
		player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.YourOwnedHorsesList, ChatColor.GOLD));

		int n = pageIndex * 15 + 1;
		for (UUID horseIdentifier : horseList)
		{
			String horseName = this.plugin.getHorseManager().getNameForHorse(horseIdentifier);
			if (player.isOp() || this.plugin.getPermissionsManager().hasPermission(player, "myhorse.admin"))
			{
				UUID ownerId = this.plugin.getHorseManager().getOwnerForHorse(horseIdentifier);
				
				if(ownerId!=null)
				{
					player.sendMessage("" + ChatColor.AQUA + n++ + ") " + this.plugin.getHorseNameColorForPlayer(ownerId) + horseName + ChatColor.AQUA + " (" + plugin.getServer().getOfflinePlayer(ownerId).getName() + ")");
				}
				else
				{
					this.plugin.getHorseManager().setOwnerForHorse(horseIdentifier, null);
					plugin.log("Horse " + horseIdentifier + " has no owner. Freeing horse.");
				}				
			}
			else
			{
				player.sendMessage("" + ChatColor.AQUA + n++ + ") " + this.plugin.getHorseNameColorForPlayer(player.getUniqueId()) + horseName + ChatColor.AQUA);
			}
		}

		if ((this.plugin.getPermissionsManager().hasPermission(player, "myhorse.goto")) && (horseList.size() > 0))
		{
			if (this.plugin.useHorseTeleportation)
			{
				player.sendMessage("");
				this.plugin.getLanguageManager().setAmount("/myhorse goto <number>");
				player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.UseGotoCommand, ChatColor.AQUA));
			}
		}
		return true;
	}

	private boolean CommandGoto(Player player, String[] args)
	{
		if ((!player.isOp()) && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.goto")))
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if ((args.length < 1) || (args.length > 2))
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse goto");
			player.sendMessage(ChatColor.RED + "Usage: /myhorse goto <horsename>");
			return false;
		}
		UUID horseIdentifier = getHorseIdentifierFromArgs(player, args);
		if (horseIdentifier == null)
		{
			return false;
		}
		Location location = this.plugin.getHorseManager().getHorseLastSelectionPosition(horseIdentifier);
		if (location != null)
		{
			player.teleport(location);
		}
		return true;
	}

	private boolean CommandSelect(Player player, String[] args)
	{
		if (!player.isOp() && !this.plugin.getPermissionsManager().hasPermission(player, "myhorse.select"))
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if (args.length != 2)
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse select <horsename>");
			player.sendMessage(ChatColor.RED + "Usage: /myhorse select <id>");
			return false;
		}
		UUID horseIdentifier = getHorseIdentifierFromArgs(player, args);
		if (horseIdentifier == null)
		{
			return false;
		}
		String horseName = this.plugin.getHorseManager().getNameForHorse(horseIdentifier);

		UUID ownerId = this.plugin.getHorseManager().getOwnerForHorse(horseIdentifier);

		if (!player.isOp() && !this.plugin.getPermissionsManager().hasPermission(player, "myhorse.admin"))
		{
			if(ownerId != player.getUniqueId() && !this.plugin.getHorseManager().isHorseFriend(horseIdentifier, player.getUniqueId()))
			{
				player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.CannotUseLockedHorse, ChatColor.DARK_RED));			
				return false;
			}
		}

		this.plugin.getOwnerManager().setCurrentHorseIdentifierForPlayer(player.getUniqueId(), horseIdentifier);
		this.plugin.getLanguageManager().setName(horseName);
		player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.YouSelectedHorse, ChatColor.GREEN));

		return true;
	}

	private boolean CommandInfo(Player player, String[] args)
	{
		if (!player.isOp() && !this.plugin.getPermissionsManager().hasPermission(player, "myhorse.info"))
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if ((args.length < 1) || (args.length > 2))
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse info");
			player.sendMessage(ChatColor.RED + "Usage: /myhorse info <horsename>");
			return false;
		}
		UUID horseIdentifier = getHorseIdentifierFromArgs(player, args);
		if (horseIdentifier == null)
		{
			return false;
		}
		AbstractHorse horse = (AbstractHorse) this.plugin.getHorseManager().getHorseEntity(horseIdentifier);
		if (horse == null)
		{
			return false;
		}
		String horseName = this.plugin.getHorseManager().getNameForHorse(horseIdentifier);
		if (horseName == null)
		{
			return false;
		}
		// TODO - Make a work around
		String typeName = horse.getType().name();
		
		
		AbstractHorse horseVar = (AbstractHorse) horse;
		player.sendMessage(ChatColor.YELLOW + "------------------ " + horseName + " ------------------");

		player.sendMessage(ChatColor.AQUA + "Type: " + ChatColor.WHITE + typeName);
		if(typeName == "Horse")	{
			Horse h = (Horse) horse;
			player.sendMessage(ChatColor.AQUA + "Variant: " + ChatColor.WHITE + h.getStyle().name());
		}
		player.sendMessage(ChatColor.AQUA + "Age: " + ChatColor.WHITE + horseVar.getAge());
		player.sendMessage(ChatColor.AQUA + "Owner: " + ChatColor.WHITE + horseVar.getOwner());

		player.sendMessage(ChatColor.AQUA + "Max Health: " + ChatColor.WHITE + horseVar.getAttribute(Attribute.GENERIC_MAX_HEALTH));
		player.sendMessage(ChatColor.AQUA + "Jump Strength: " + ChatColor.WHITE + horseVar.getJumpStrength());

		List<UUID> friends = this.plugin.getHorseManager().getHorseFriends(horseIdentifier);
		if (friends.size() > 0)
		{
			player.sendMessage(ChatColor.AQUA + "Friends: ");
			for (UUID friendId : friends)
			{
				player.sendMessage(ChatColor.WHITE + "  " + plugin.getServer().getOfflinePlayer(friendId).getName());
			}
		}
		
		//((CraftPlayer) player).getHandle().world.broadcastEntityEffect(el, (byte) 7); // TODO - Delete this?

		this.plugin.getLanguageManager().setName(horseName);

		return true;
	}

	private boolean CommandSell(Player player, String[] args)
	{
		if (this.plugin.getEconomy() == null)
		{
			return false;
		}
		if ((!player.isOp()) && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.sell")))
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if ((args.length < 1) || (args.length > 2))
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse sell <price>");
			return false;
		}

		UUID horseIdentifier = this.plugin.getOwnerManager().getCurrentHorseIdentifierForPlayer(player.getUniqueId());

		if (horseIdentifier == null)
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoHorseSelected, ChatColor.DARK_RED));
			return false;
		}

		if (args.length == 1)
		{
			AbstractHorse horse = (AbstractHorse) this.plugin.getHorseManager().getHorseEntity(horseIdentifier);
			if (horse == null)
			{
				horse = this.plugin.getHorseManager().spawnHorse(horseIdentifier, this.plugin.getHorseManager().getHorseLastSelectionPosition(horseIdentifier));
				horseIdentifier = horse.getUniqueId();
				this.plugin.getOwnerManager().setCurrentHorseIdentifierForPlayer(player.getUniqueId(), horseIdentifier);
			}
			String horseName = this.plugin.getHorseManager().getNameForHorse(horseIdentifier);
			horse.setCustomName(this.plugin.getHorseNameColorForPlayer(player.getUniqueId()) + horseName);

			this.plugin.getHorseManager().setHorseForSale(horseIdentifier, 0);

			this.plugin.getLanguageManager().setName(horseName);
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.YouCancelledHorseForSale, ChatColor.DARK_RED));

			return false;
		}

		int sellingPrice;
		try
		{
			sellingPrice = Integer.parseInt(args[1]);
		}
		catch (Exception ex)
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.InvalidCommand, ChatColor.DARK_RED));
			return false;
		}

		if (sellingPrice <= 0)
		{
			player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.InvalidCommand, ChatColor.DARK_RED));
			return false;
		}
		String horseName = this.plugin.getHorseManager().getNameForHorse(horseIdentifier);

		AbstractHorse horse = (AbstractHorse) this.plugin.getHorseManager().getHorseEntity(horseIdentifier);

		horse.setCustomName(ChatColor.GOLD + horseName + ChatColor.RED + " " + this.plugin.getEconomy().format(sellingPrice));

		this.plugin.getHorseManager().setHorseForSale(horseIdentifier, sellingPrice);

		this.plugin.getLanguageManager().setAmount(this.plugin.getEconomy().format(sellingPrice));
		this.plugin.getLanguageManager().setName(horseName);

		player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.YouSetHorseForSale, ChatColor.GREEN));

		this.plugin.getLanguageManager().setAmount("/myhorse sell");
		player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.InfoCancelHorseSale, ChatColor.AQUA));

		return true;
	}

	private boolean CommandAddFriend(Player player, String[] args)
	{
		if ((!player.isOp()) && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.addfriend")))
		{
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if (args.length != 2)
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse addfriend <playername>");
			return false;
		}
		UUID horseIdentifier = this.plugin.getOwnerManager().getCurrentHorseIdentifierForPlayer(player.getUniqueId());
		if (horseIdentifier == null)
		{
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoHorseSelected, ChatColor.DARK_RED));
			return false;
		}
		
		String friendName = args[1];
		UUID friendId = null;
		for(OfflinePlayer playerName : Bukkit.getOfflinePlayers()) {
			if(playerName.getName() == friendName) friendId = playerName.getUniqueId();
		}
		
		if(friendId==null)
		{
			return false;
		}		
		
		if (this.plugin.getHorseManager().isHorseFriend(horseIdentifier, friendId))
		{
			this.plugin.getLanguageManager().setPlayerName(friendName);
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.AlreadyHorseFriend, ChatColor.DARK_RED));
			return false;
		}
		
		if (friendId.equals(player.getUniqueId()))
		{
			return false;
		}
		
		this.plugin.getHorseManager().addHorseFriend(horseIdentifier, friendId);

		this.plugin.getLanguageManager().setPlayerName(friendName);
		player.sendMessage(ChatColor.GREEN + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.YouAddedFriendToHorse, ChatColor.GREEN));

		return true;
	}

	private boolean CommandRemoveFriend(Player player, String[] args)
	{
		if ((!player.isOp()) && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.removefriend")))
		{
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if (args.length != 2)
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse removefriend <playername>");
			return false;
		}
		UUID horseIdentifier = this.plugin.getOwnerManager().getCurrentHorseIdentifierForPlayer(player.getUniqueId());
		if (horseIdentifier == null)
		{
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoHorseSelected, ChatColor.DARK_RED));
			return false;
		}
		
		String friendName = args[1];
		
		UUID friendId = plugin.getServer().getPlayer(friendName).getUniqueId();
		
		if(friendId==null)
		{
			return false;
		}		
						
		if (!this.plugin.getHorseManager().isHorseFriend(horseIdentifier, friendId))
		{
			this.plugin.getLanguageManager().setPlayerName(friendName);
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NotHorseFriend, ChatColor.DARK_RED));
			return false;
		}
		this.plugin.getHorseManager().removeHorseFriend(horseIdentifier, friendId);

		this.plugin.getLanguageManager().setPlayerName(friendName);
		player.sendMessage(ChatColor.GREEN + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.YouRemovedFriendToHorse, ChatColor.GREEN));

		return true;
	}

	private boolean CommandClaim(Player player, String[] args)
	{
		if ((!player.isOp()) && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.claim")))
		{
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if (args.length != 1)
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse claim");
			return false;
		}
		if (!player.isInsideVehicle())
		{
			return false;
		}
		
		if (!(player.getVehicle() instanceof AbstractHorse) && player.getVehicle().getType() == EntityType.LLAMA || player.getVehicle().getType() == EntityType.TRADER_LLAMA)
		{
			return false;
		}
		AbstractHorse horse = (AbstractHorse) player.getVehicle();
		
		if(horse instanceof Llama) return false;
		
		UUID horseIdentifier = horse.getUniqueId();
		if (horseIdentifier == null)
		{
			return false;
		}

		if (this.plugin.getHorseManager().isHorseOwned(horseIdentifier))
		{
			UUID playerId = this.plugin.getHorseManager().getOwnerForHorse(horseIdentifier);
			this.plugin.getLanguageManager().setPlayerName(plugin.getServer().getOfflinePlayer(playerId).getName());
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.YouCannotClaimThisHorse, ChatColor.DARK_RED));
			return false;
		}

		int maxHorses = this.plugin.getMaximumHorsesForPlayer(player.getName());
		int numberOfHorses = this.plugin.getHorseManager().getHorsesForOwner(player.getUniqueId()).size();
		if ((maxHorses > 0) && (numberOfHorses >= maxHorses))
		{
			this.plugin.getLanguageManager().setName(player.getName());
			this.plugin.getLanguageManager().setAmount("" + maxHorses);
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.YouCannotHaveMoreHorses, ChatColor.DARK_RED));
			return false;
		}
		String horseName = this.plugin.getHorseManager().getNewHorseName();

		this.plugin.getHorseManager().setNameForHorse(horseIdentifier, horseName);

		horse.setCustomName(this.plugin.getHorseNameColorForPlayer(player.getUniqueId()) + horseName);
		horse.setCustomNameVisible(true);

		horse.setOwner(player);

		this.plugin.getHorseManager().setOwnerForHorse(horseIdentifier, player.getUniqueId());

		player.sendMessage(ChatColor.GREEN + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.YouClaimedAHorse, ChatColor.GREEN));

		return true;
	}

	private boolean CommandPurge(Player player, String[] args)
	{
		if ((!player.isOp()) && (!this.plugin.getPermissionsManager().hasPermission(player, "myhorse.purge")))
		{
			player.sendMessage(ChatColor.RED + this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.NoPermissionForCommand, ChatColor.DARK_RED));
			return false;
		}
		if ((args.length < 1) || (args.length > 2))
		{
			player.sendMessage(ChatColor.RED + "Usage: /myhorse purge");
			player.sendMessage(ChatColor.RED + "Usage: /myhorse purge <number of days>");
			return false;
		}
		int numberOfDays = 1;
		int numberOfPurgedHorses = 0;
		int numberOfPurgedOwners = 0;

		List<UUID> ownerList = this.plugin.getOwnerManager().getOwnersNotLoggedInForDays(numberOfDays);
		for (UUID ownerId : ownerList)
		{
			List<UUID> horseList = this.plugin.getHorseManager().getHorsesForOwner(ownerId);
			for (UUID horseIdentifier : horseList)
			{
				this.plugin.getHorseManager().setOwnerForHorse(horseIdentifier, null);
				numberOfPurgedHorses++;
			}

			this.plugin.getOwnerManager().removeOwner(ownerId);

			numberOfPurgedOwners++;
		}

		this.plugin.getLanguageManager().setAmount("" + numberOfPurgedHorses);

		player.sendMessage(this.plugin.getLanguageManager().getLanguageString(LanguageManager.LANGUAGESTRING.HorsesWasPurged, ChatColor.GREEN));

		return true;
	}
}
