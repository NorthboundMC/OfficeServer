package com.github.rosapetals.officeServer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.github.rosapetals.officeServer.BossBarUtil.createBossBar;
import static com.github.rosapetals.officeServer.BossBarUtil.removeBossBar;
//import static com.github.rosapetals.officeServer.OfficeServer.getCurrentSchedule;


public class PlayerListeners implements Listener {
    private static final OfficeServer officePlugin = OfficeServer.getInstance();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
       createBossBar(player, "test");

    }

    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        removeBossBar(player);
    }
}
