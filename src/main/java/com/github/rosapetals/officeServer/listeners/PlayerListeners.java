package com.github.rosapetals.officeServer.listeners;

import com.github.rosapetals.officeServer.OfficeServer;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.github.rosapetals.officeServer.BossBarUtil.*;
import static com.github.rosapetals.officeServer.OfficeServer.getCurrentSchedule;



public class PlayerListeners implements Listener {
    private static final OfficeServer officePlugin = OfficeServer.getInstance();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        createBossBar(player, getCurrentSchedule());
        World world = player.getWorld();
        Location worldSpawn = world.getSpawnLocation();
        player.teleport(worldSpawn);
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        removeBossBar(player);
    }
}
