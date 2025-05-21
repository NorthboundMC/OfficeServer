package com.github.rosapetals.officeServer.listeners;

import com.github.rosapetals.officeServer.Schedule;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LaundryPileListener implements Listener {

    private static final Schedule schedule = new Schedule();

    @EventHandler
    public void onLaundryPileInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.COMPOSTER) {

            event.setCancelled(true);
            Levelled levelled = (Levelled) event.getClickedBlock().getBlockData();
            if (levelled.getLevel() == 8) {
                schedule.addVillagerSpawnLocation(new Location(player.getWorld(), event.getClickedBlock().getX() + 1.5, event.getClickedBlock().getY(), event.getClickedBlock().getZ() + 0.5 , 90, 0));
                levelled.setLevel(0);
                event.getClickedBlock().setBlockData(levelled);

            }


        }


    }
}