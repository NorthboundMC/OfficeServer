package com.github.rosapetals.officeServer.listeners;

import com.github.rosapetals.officeServer.OfficeServer;
import com.github.rosapetals.officeServer.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class WasherListener implements Listener {

    private static final HashMap<UUID, Integer> washerStatus = new HashMap<>();
    // 0 = empty
    // 1 = washing
    // 2 = done

    private void openMenu(Player player) {
        int capacity = 5;
        Inventory menu = Bukkit.createInventory(null, InventoryType.HOPPER, player.getName() + "'s Washing Machine");

        player.openInventory(menu);
    }
    @EventHandler
    public void onWasherInteract (PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked() instanceof ItemFrame itemFrame && itemFrame.getItem().getType() == Material.LIGHT_BLUE_STAINED_GLASS_PANE){
            event.setCancelled(true);

            int status = washerStatus.getOrDefault(player.getUniqueId(), 0);

            if (status == 1)
            {
                player.sendMessage(CC.translate("&c&l&nThe Washing Machine is already running!"));

            } else if (status == 2) {
                player.playSound(player.getLocation(), Sound.BLOCK_IRON_TRAPDOOR_OPEN, 1f, 1f);
                player.openInventory(player.getEnderChest());
            } else if (status == 0) {
                player.playSound(player.getLocation(), Sound.BLOCK_IRON_TRAPDOOR_OPEN, 1f, 1f);
                openMenu(player);
            }
        }

    }
    @EventHandler
    public void onInventoryClose (InventoryCloseEvent event){
        if (event.getPlayer() instanceof Player player && event.getView().getTitle().equals(player.getName() + "'s Washing Machine")) {


            if (event.getInventory().isEmpty()){
                return;
            }
            player.playSound(player.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 1f, 1f);

            washerStatus.put(player.getUniqueId(), 1);
            ItemStack[] clothes = event.getInventory().getContents();
            Inventory playerEnderChest = player.getEnderChest();
            int washerSpeed = 0;
            new BukkitRunnable() {

                @Override
                public void run() {

                    for (ItemStack item: clothes){

                        if (item == null || item.getItemMeta() == null)
                        {
                            continue;
                        }

                        ItemMeta meta = item.getItemMeta();
                        String name = meta.getDisplayName();
                        name = name.replace("Dirty", "Clean");
                        meta.setDisplayName(name);
                        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        item.setItemMeta(meta);
                        playerEnderChest.addItem(item);
                        //temporary ^
                    }
                    washerStatus.put(player.getUniqueId(), 2);



                }
            }.runTaskLater(OfficeServer.getInstance(), 20*20 - washerSpeed * 20);



        }
    }

    @EventHandler
    public void onFullWashingMachineClose (InventoryCloseEvent event) {
    if (event.getInventory().getType() == InventoryType.ENDER_CHEST && event.getInventory().isEmpty()){
        washerStatus.put(event.getPlayer().getUniqueId(), 0);
    }


    }

}
