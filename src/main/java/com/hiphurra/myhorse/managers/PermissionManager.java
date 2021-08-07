package com.hiphurra.myhorse.managers;

import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.enums.PermissionNode;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.logging.Level;

public class PermissionManager {

    private final MyHorse plugin;
    private Permission  vaultPermission	= null;
    private Chat vaultChat = null;

    public PermissionManager(MyHorse plugin)
    {
        this.plugin = plugin;
        plugin.logDebug(Level.INFO, "Using for Permissions: " + plugin.getPermissionPlugin().getProvider().getName());
        plugin.logDebug(Level.INFO, "Using for Chat: " + plugin.getChatPlugin().getProvider().getName());
        if (plugin.vaultExist())
        {
            vaultPermission = plugin.getPermissionPlugin().getProvider();
            vaultChat = plugin.getChatPlugin().getProvider();
        }
    }

    public boolean hasPermission(Player player, PermissionNode node)
    {
        if (plugin.vaultExist())
        {
            return vaultPermission.has(player, node.getNode());
        }
        else
        {
            return player.hasPermission(node.getNode());
        }
    }

    public boolean isGroup(String groupName)
    {
        if (plugin.vaultExist())
        {
            for (String group : vaultPermission.getGroups())
            {
                if (group.contains(groupName))
                    return true;
            }
        }
        return false;
    }

    public String[] getGroups()
    {
        if (plugin.vaultExist())
        {
            return vaultPermission.getGroups();
        }
        return null;
    }

    public String getGroup(String playerName)
    {
        if (plugin.vaultExist())
        {
            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers())
            {
                if(offlinePlayer.getName().equals(playerName))
                {
                    return vaultPermission.getPrimaryGroup(null, offlinePlayer);
                }
            }
        }
        return "";
    }

    public String getPrefix(UUID playerId)
    {
        if (plugin.vaultExist())
        {
            Player player = plugin.getServer().getPlayer(playerId);
            return vaultChat.getPlayerPrefix(player);
        }
        return "";
    }

    public void setGroup(UUID playerId, String groupName)
    {
        if (plugin.vaultExist())
        {
            Player player = plugin.getServer().getPlayer(playerId);
            vaultPermission.playerAddGroup(player, groupName);
        }
    }
}
