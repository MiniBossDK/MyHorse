package com.hiphurra.myhorse.enums;

import org.bukkit.ChatColor;

public enum MessageType {
    INFO(ChatColor.AQUA),
    SUCCESS(ChatColor.GREEN),
    WARNING(ChatColor.YELLOW),
    SEVERE(ChatColor.RED);

    private final ChatColor chatColor;

    MessageType(ChatColor chatColor) {
        this.chatColor = chatColor;
    }

    public ChatColor getChatColor() {
        return chatColor;
        }
}
