package com.github.rosapetals.officeServer.listeners;


import com.github.rosapetals.officeServer.menus.ComputerMenu;
import com.github.rosapetals.officeServer.utils.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.BROWN_WOOL && player.getItemInHand().getType() == Material.STICK) {
            ItemMeta meta = player.getItemInHand().getItemMeta();
            if (Cooldown.isOnCooldown("broom", player)) return;
            Cooldown.createCooldown("broom", player, 1);
            if (meta.getDisplayName().equals("BROOM")) {
                event.getClickedBlock().setType(Material.GREEN_WOOL);
                Bukkit.broadcastMessage("You swept away the pile of dirt.");
                player.playSound(player, Sound.ENTITY_VILLAGER_YES, 10, 1);
            }  else {
                player.sendMessage("[BOSS] Can't you see this broom is broken!!");
                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 10, 1);
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.BROWN_WOOL) {
            player.sendMessage("[BOSS] Go grab a broom from the supply closet to complete this task!");
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 10, 1);
        }
    }

    @EventHandler
    public void onCompClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (event.getClickedBlock().getType() == Material.PLAYER_HEAD || event.getClickedBlock().getType() == Material.PLAYER_WALL_HEAD) {
            Skull skull = (Skull) block.getState();
            PersistentDataContainer data = skull.getPersistentDataContainer();
            if (data.has(new NamespacedKey("yourplugin", "computer_head"), PersistentDataType.STRING)) {
                String value = data.get(new NamespacedKey("yourplugin", "computer_head"), PersistentDataType.STRING);
                if ("true".equals(value)) {
                    // This is your custom head with the extra NBT data
                    event.getPlayer().sendMessage("You clicked the custom Computer Head!");
                }
            }
        }
    }



}
