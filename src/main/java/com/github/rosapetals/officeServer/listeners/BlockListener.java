package com.github.rosapetals.officeServer.listeners;


import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemMeta meta = player.getItemInHand().getItemMeta();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.BROWN_WOOL) {
                if (player.getItemInHand().getType() == Material.STICK) {
                    if (meta.getDisplayName().equals("BROOM")) {
                        event.getClickedBlock().setType(Material.GREEN_WOOL);
                        Bukkit.broadcastMessage("You swept away the pile of dirt.");
                        player.playSound(player, Sound.ENTITY_VILLAGER_YES, 10, 1);
                    }  else {
                        player.sendMessage("[BOSS] Can't you see this broom is broken!!");
                        player.playSound(player, Sound.ENTITY_VILLAGER_NO, 10, 1);
                    }
                } else {
                    player.sendMessage("[BOSS] Go grab a broom from the supply closet to complete this task!");
                    player.playSound(player, Sound.ENTITY_VILLAGER_NO, 10, 1);
                }
            }
        }
    }



}
