package com.github.rosapetals.officeServer.listeners;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {


    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();


        if (event.getMessage().equalsIgnoreCase("/setspawn")) {
            event.setCancelled(true);
            if (player.isOp()) {
                Location location = player.getLocation();
                world.setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
                player.sendMessage("[SUCCESS] Set the spawn point of " + world.getName() + "to your location.");
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            }

            } else if (event.getMessage().equalsIgnoreCase("/spawn")) {
                event.setCancelled(true);
                Location spawnLoc = world.getSpawnLocation();
                player.teleport(spawnLoc);
                player.sendMessage("Teleporting to the set spawn location.");

            }
        }


    }

