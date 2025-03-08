package com.github.rosapetals.officeServer;


import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class BossBarUtil {
    private static final HashMap<UUID, BossBar> bossBars = new HashMap<>();
    public static void createBossBar(Player player,String title) {
        BossBar bossBar = Bukkit.createBossBar(title, BarColor.RED, BarStyle.SOLID);
        bossBar.setProgress(1.0); // Full progress
        bossBar.addPlayer(player);
        bossBars.put(player.getUniqueId(), bossBar);
    }

    public static void removeBossBar(Player player) {
        BossBar bossBar = bossBars.remove(player.getUniqueId());
        if (bossBar != null) {
            bossBar.removeAll();
        }
    }

    public static void updateBossBar(Player player, double progress, String title) {
        BossBar bossBar = bossBars.get(player.getUniqueId());
        if (bossBar != null) {
            bossBar.setProgress(progress);
            bossBar.setTitle(title);
        }
    }
}

