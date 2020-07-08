package com.dogonfire.myhorse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class StableManager
{
  private MyHorse plugin = null;
  private Random random = new Random();
  private HashMap<String, String> playerBets = new HashMap<>();
  private HashMap<String, Integer> betAmounts = new HashMap<>();
  private int leaderWaypoint = 0;
  String leaderPlayerName;
  private long lastRaceTime = 0L;
  
  StableManager(MyHorse plugin)
  {
    this.plugin = plugin;
  }
  
  public void load() {}
  
  public void save() {}
  
  public int newStable(String ownerName, Location min, Location max)
  {
    int stableIdentifier = this.random.nextInt();
    


    return stableIdentifier;
  }
  
  Integer getStableBySignLocation(Location signLocation)
  {
    return null;
  }
  
  Block getStableFenceBySignLocation(Location signLocation)
  {
    return null;
  }
  
  Integer getStableByInsideLocation(Location signLocation)
  {
    return null;
  }
  
  public String getOwnerFromStableSign(Block block, String[] lines)
  {
    if ((block == null) || (block.getType() != Material.OAK_SIGN)) {
      return null;
    }
    String stableHeader = lines[0].trim();
    if (!stableHeader.equalsIgnoreCase("stable")) {
      return null;
    }
    String horseName = lines[1];
    if ((horseName == null) || (horseName.length() < 2)) {
      return null;
    }
    String playerName = lines[2];
    if ((playerName == null) || (playerName.length() < 1)) {
      return null;
    }
    return playerName;
  }
  
  public void handleBlockClick(String playerName, Block block) {}
  
  private void bleedMinimumLocation(Location location, Location min)
  {
    if (location.getBlockX() < min.getBlockX()) {
      min.setX(location.getX());
    }
  }
  
  private void bleedMaximumLocation(Location location, Location max)
  {
    if (location.getBlockX() < max.getBlockX()) {
      max.setX(location.getX());
    }
  }
  
  private void bleedStableBlocks(Block block, List<Location> list, Location min, Location max, int depth)
  {
    if (depth > 30) {
      return;
    }
    Block eastBlock = block.getRelative(BlockFace.EAST);
    if ((eastBlock.getType() == Material.AIR) && (!list.contains(eastBlock.getLocation())))
    {
      bleedMinimumLocation(eastBlock.getLocation(), min);
      bleedMaximumLocation(eastBlock.getLocation(), max);
      bleedStableBlocks(eastBlock, list, min, max, depth + 1);
    }
    Block westBlock = block.getRelative(BlockFace.WEST);
    if ((westBlock.getType() == Material.AIR) && (!list.contains(westBlock.getLocation())))
    {
      bleedMinimumLocation(eastBlock.getLocation(), min);
      bleedMaximumLocation(eastBlock.getLocation(), max);
      bleedStableBlocks(westBlock, list, min, max, depth + 1);
    }
    Block northBlock = block.getRelative(BlockFace.NORTH);
    if ((northBlock.getType() == Material.AIR) && (!list.contains(northBlock.getLocation())))
    {
      bleedMinimumLocation(eastBlock.getLocation(), min);
      bleedMaximumLocation(eastBlock.getLocation(), max);
      bleedStableBlocks(northBlock, list, min, max, depth + 1);
    }
    Block southBlock = block.getRelative(BlockFace.SOUTH);
    if ((southBlock.getType() == Material.AIR) && (!list.contains(southBlock.getLocation())))
    {
      bleedMinimumLocation(eastBlock.getLocation(), min);
      bleedMaximumLocation(eastBlock.getLocation(), max);
      bleedStableBlocks(southBlock, list, min, max, depth + 1);
    }
  }
  
  public void handlePlaceSign(String playerName, Block block, String[] lines)
  {
    String ownerName = getOwnerFromStableSign(block, lines);
    if (ownerName != null)
    {
      Integer stableIdentifier = getStableByInsideLocation(block.getLocation());
      if (stableIdentifier == null)
      {
        List<Location> list = new ArrayList<>();
        Location max = new Location(block.getWorld(), -99999.0D, 0.0D, -99999.0D);
        Location min = new Location(block.getWorld(), 99999.0D, 0.0D, 99999.0D);
        
        Block fenceBlock = getStableFenceBySignLocation(block.getLocation());
        bleedStableBlocks(fenceBlock, list, min, max, 0);
        

        max.setX(min.getX() + 1.0D);
        max.setZ(min.getZ() + 1.0D);
        min.setX(min.getX() - 1.0D);
        min.setZ(min.getZ() - 1.0D);
        
        // TODO - Delete?
        int i = newStable(ownerName, min, max);
      }
      return;
    }
  }
  
  public void update() {}
}


/* Location:           C:\temp\MyHorse.jar
 * Qualified Name:     com.dogonfire.myhorse.StableManager
 * JD-Core Version:    0.7.0.1
 */