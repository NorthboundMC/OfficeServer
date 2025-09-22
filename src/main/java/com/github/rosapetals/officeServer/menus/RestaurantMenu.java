package com.github.rosapetals.officeServer.menus;

import com.github.rosapetals.officeServer.utils.CC;
import com.github.rosapetals.officeServer.utils.ItemUtils;
import com.github.rosapetals.officeServer.utils.RegionDetector;
import com.github.rosapetals.officeServer.utils.VaultHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class RestaurantMenu implements Listener {


    private void openRestaurantMenu(Player p) {

        Inventory menu = Bukkit.createInventory(null, InventoryType.HOPPER, CC.translate("&eRestaurant Menu"));

        menu.setItem(1, ItemUtils.CreateCustomItem(new ItemStack(Material.BAKED_POTATO), CC.translate("&e&lCheesey Burger"), "&f&lPrice: $0.60\n&fSatiates the hunger", false));

        ItemStack cola = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) cola.getItemMeta();
        potionMeta.setBasePotionType(PotionType.HARMING);
        cola.setItemMeta(potionMeta);

        menu.setItem(3, ItemUtils.CreateCustomItem(cola, CC.translate("&c&lCola"), "&f&lPrice: $0.60\n&fNOW ONLY 10% DETERGENT", false));

        p.playSound(p, Sound.ENTITY_ARROW_HIT_PLAYER, 1f, 1f);

        p.openInventory(menu);
    }

    @EventHandler
    public void onRestaurantMenuOpen(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.PLAYER_HEAD && RegionDetector.detect(event.getPlayer().getLocation()).contains("restaurant")){
            openRestaurantMenu(event.getPlayer());
        }
    }

    @EventHandler
    public void restaurantMenuClickListener(InventoryClickEvent event){

        if (event.getView().getTitle().equals(CC.translate("&eRestaurant Menu")) && event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR){
            ItemStack clickedItem = event.getCurrentItem();
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            double price = Double.parseDouble(ChatColor.stripColor(clickedItem.getItemMeta().getLore().get(0)).replaceAll("Price: \\$", ""));

            if (VaultHandler.getBalance(player) >= price)
            {
                VaultHandler.removeMoney(player, price);
                player.getInventory().addItem(clickedItem);
            }


        }
    }


}
