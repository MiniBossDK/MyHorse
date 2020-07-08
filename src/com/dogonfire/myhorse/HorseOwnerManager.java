package com.dogonfire.myhorse;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class HorseOwnerManager
{
  private MyHorse plugin = null;
  private FileConfiguration ownersConfig = null;
  private File ownersConfigFile = null;
  
  HorseOwnerManager(MyHorse plugin)
  {
    this.plugin = plugin;
  }
  
  public void load()
  {
    if (this.ownersConfigFile == null) {
      this.ownersConfigFile = new File(this.plugin.getDataFolder(), "owners.yml");
    }
    this.ownersConfig = YamlConfiguration.loadConfiguration(this.ownersConfigFile);
    
    this.plugin.log("Loaded " + this.ownersConfig.getKeys(false).size() + " horse owners.");
  }
  
  public void save()
  {
    if ((this.ownersConfig == null) || (this.ownersConfigFile == null)) {
      return;
    }
    try
    {
      this.ownersConfig.save(this.ownersConfigFile);
    }
    catch (Exception ex)
    {
      this.plugin.log("Could not save config to " + this.ownersConfigFile + ": " + ex.getMessage());
    }
  }
  
  public void setBuying(UUID playerId, UUID horseIdentifier, boolean buying)
  {
    String pattern = "HH:mm:ss dd-MM-yyyy";
    DateFormat formatter = new SimpleDateFormat(pattern);
    Date thisDate = new Date();
    if (!buying) 
    {
      this.ownersConfig.set(playerId.toString() + ".Buying." + horseIdentifier.toString(), null);
    } 
    else 
    {
      this.ownersConfig.set(playerId.toString() + ".Buying." + horseIdentifier.toString(), formatter.format(thisDate));
    }
  }
  
  public boolean isBuying(UUID playerId, UUID horseIdentifier)
  {
    String pattern = "HH:mm:ss dd-MM-yyyy";
    DateFormat formatter = new SimpleDateFormat(pattern);
    Date date = null;
    Date thisDate = new Date();
    
    String buyingString = this.ownersConfig.getString(playerId.toString() + ".Buying." + horseIdentifier.toString());
    try
    {
      date = formatter.parse(buyingString);
    }
    catch (Exception ex)
    {
      date = new Date();
      date.setTime(0L);
    }
    
    long diff = thisDate.getTime() - date.getTime();
    
    if (diff > 3000L) 
    {
      return false;
    }
    return true;
  }
  
  public void setCurrentHorseIdentifierForPlayer(UUID playerId, UUID horseIdentifier)
  {
    if (horseIdentifier == null) {
      this.ownersConfig.set(playerId.toString() + ".CurrentHorse", null);
    } else {
      this.ownersConfig.set(playerId.toString() + ".CurrentHorse", horseIdentifier.toString());
    }
  }
  
  public UUID getCurrentHorseIdentifierForPlayer(UUID playerId)
  {
    String currentHorse = this.ownersConfig.getString(playerId.toString() + ".CurrentHorse");
    if (currentHorse == null) {
      return null;
    }
    return UUID.fromString(currentHorse);
  }
  
  public void setLastLogin(UUID playerId, Date lastLogin)
  {
    String pattern = "HH:mm:ss dd-MM-yyyy";
    DateFormat formatter = new SimpleDateFormat(pattern);
    
    	if (this.ownersConfig.contains(playerId.toString())) 
    	{
    		this.ownersConfig.set(playerId.toString() + ".LastLogin", formatter.format(lastLogin));
    	}
  }
  
  public void removeOwner(UUID playerId)
  {
    this.ownersConfig.set(playerId.toString(), null);
  }
  
  public List<UUID> getOwnersNotLoggedInForDays(int numberOfDays)
  {
    String pattern = "HH:mm:ss dd-MM-yyyy";
    DateFormat formatter = new SimpleDateFormat(pattern);
    Date date = null;
    UUID playerId;
    Date thisDate = new Date();
    
    List<UUID> ownerList = new ArrayList<>();
    for (String ownerId : this.ownersConfig.getKeys(false))
    {
      String loginString = this.ownersConfig.getString(ownerId.toString() + ".LastLogin");
      
      try
      {
    	  playerId = UUID.fromString(ownerId);
      }
      catch (Exception ex)
      {
    	  continue;
      }
      
      try
      {
    	  date = formatter.parse(loginString);
      }
      catch (Exception ex)
      {
        date = new Date();
        date.setTime(0L);
      }
      
      long diff = thisDate.getTime() - date.getTime();
      
      if (diff > numberOfDays) 
      {
        ownerList.add(playerId);
      }
    }
    return ownerList;
  }
}