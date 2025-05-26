package com.github.rosapetals.officeServer.listeners;

import com.github.rosapetals.officeServer.utils.CC;
import com.github.rosapetals.officeServer.utils.VaultHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LaundrySellListener implements Listener {
    private void openMenu(Player player) {

        Inventory menu = Bukkit.createInventory(null, InventoryType.CHEST,  "Items to Sell:");

        player.openInventory(menu);

    }

    @EventHandler
    public void  onClickVillager (PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        if (event.getRightClicked() instanceof Villager villager && villager.getName().equals("Mildred")){
            event.setCancelled(true);

            openMenu(player);

        }
    }
    @EventHandler
    public void onInventoryClose (InventoryCloseEvent event){
        if (event.getPlayer() instanceof Player player && event.getView().getTitle().equals("Items to Sell:")) {
            if (event.getInventory().isEmpty()){
                return;
            }
            ItemStack[] sellables = event.getInventory().getContents();
            for (ItemStack item : sellables) {
                if (item == null || item.getItemMeta() == null) {
                    continue;
                }

                if (item.getItemMeta().getDisplayName().contains("Clean") && item.getItemMeta().hasEnchant(Enchantment.ARROW_INFINITE)) {

                    if (item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains(CC.translate("&5&lRarity: Rare"))) {
                        VaultHandler.addMoney(player, 50);
                    } else {
                        VaultHandler.addMoney(player, 0.10);
                    }
                }
            }
            player.playSound(player, Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);


        }
    }
}
