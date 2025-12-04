package com.github.rosapetals.officeServer.listeners;

import com.github.rosapetals.officeServer.OfficeServer;
import com.github.rosapetals.officeServer.utils.CC;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.type.Switch;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ClothesLineListener implements Listener {

    // 1 - Use PlayerInteractEvent to detect right-clicking the chain
    // 2 - Granted there is no banner in front of it, spawn a blue banner in front of it
    // 3 - Take the item from the player's hand and add it to clothingItem, with the banner as Block
    // 4 - Add the player's UUID to associatedPlayer with the banner as Block
    // 5 - Wait 30 seconds, turn green.
    // 6 - If the Banner is right-clicked and associatedPlayer of the Block contains the player's UUID, give the item, and clear all hashmaps with the block
    // using clothingItem/associatedPlayer.remove(player.getUniqueId) and then delete the block.
    // 7 - If, after 30 seconds, the banner still exists, drop the clothingItem at the block's location, delete all hashmaps, and delete the block
    private HashMap<Block, UUID> associatedPlayer = new HashMap<>();
    private HashMap<Block, ItemStack> clothingItem = new HashMap<>();

    @EventHandler
    public void onLineInteract (PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block banner = player.getWorld().getBlockAt(new Location(player.getWorld(), event.getClickedBlock().getX()- 1, event.getClickedBlock().getY(), event.getClickedBlock().getZ()));
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.CHAIN && banner.getType() == Material.AIR && player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Wet")){

            banner.setType(Material.LIGHT_BLUE_WALL_BANNER);
            BlockData data = banner.getBlockData();
            ((Directional) data).setFacing(BlockFace.WEST);
            banner.setBlockData(data);

            clothingItem.put(banner,player.getInventory().getItemInMainHand());
            associatedPlayer.put(banner, player.getUniqueId());
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));

            Bukkit.getScheduler().runTaskLater(OfficeServer.getInstance(), () -> {
                banner.setType(Material.GREEN_WALL_BANNER);
                BlockData greenData = banner.getBlockData();
                ((Directional) greenData).setFacing(BlockFace.WEST);
                banner.setBlockData(greenData);
                ItemStack item = clothingItem.get(banner);
                ItemMeta meta = item.getItemMeta(); 
                meta.setDisplayName(item.getItemMeta().getDisplayName().replace("Wet", "Clean"));
                item.setItemMeta(meta);
                Bukkit.getScheduler().runTaskLater(OfficeServer.getInstance(), () -> {
                   if(banner.getType() == Material.GREEN_WALL_BANNER){
                    player.getWorld().dropItem(banner.getLocation(), clothingItem.get(banner));
                    associatedPlayer.remove(banner);
                    clothingItem.remove(banner);
                    banner.setType(Material.AIR);
                   }
                }, 600);
            }, 600);



        } else if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.LIGHT_BLUE_WALL_BANNER){
            player.sendMessage(CC.translate("&c&lThis item is still drying!"));
        } else if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.GREEN_WALL_BANNER) {
            Block block = event.getClickedBlock();
            if (associatedPlayer.get(block) == player.getUniqueId()) {
                block.setType(Material.AIR);
                player.getInventory().addItem(clothingItem.get(block));
                associatedPlayer.remove(banner);
                clothingItem.remove(banner);
            }}
    }

}
