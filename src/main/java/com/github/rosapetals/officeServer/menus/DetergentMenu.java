package com.github.rosapetals.officeServer.menus;

import com.github.rosapetals.officeServer.features.Detergent;
import com.github.rosapetals.officeServer.features.DetergentData;
import com.github.rosapetals.officeServer.utils.CC;
import com.github.rosapetals.officeServer.utils.VaultHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.List;


public class DetergentMenu implements Listener {

    // 1: get clicked item, making sure the clicked item is not null or of Material.AIR, through event.getCurrentItem()
    // 2: get the name of the item through its ItemMeta, once you have ensured the item itself is not null
    // 3: use Detergent method fromName to locate the Detergent's Data by the item's name
    // 4: use .getDetergentData().getPrice() to find the price of the clicked detergent
    // 5: use eco method VaultHandler.getBalance(player) to get the player's balance and check they have enough to buy the clicked detergent
    // 6: If yes, add the clicked item to the player's inventory and charge them. If not, close the menu and send an error message
    // Hint: Check openDetergentMenu method and RestaurantMenu.java for an idea of how to do all this

        private void openDetergentMenu(Player p) {

            Inventory menu = Bukkit.createInventory(null, 27, CC.translate("&b&lDetergent Store"));

            for(Detergent detergent : Detergent.values())
            {
                ItemStack potion = new ItemStack(Material.POTION);
                if (detergent.getDetergentData().getDisplayItem() != null) {
                    PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

                    potionMeta.setBasePotionType(detergent.getDetergentData().getDisplayItem());

                    potion.setItemMeta(potionMeta);

                } else {
                    if(detergent.getDetergentData().name().equals(CC.translate("&9Cleaner X"))){
                        potion = Bukkit.getItemFactory().createItemStack("ominous_bottle");

                    } else if (detergent.getDetergentData().name().equals(CC.translate("&9MagiClean"))) {
                        potion = new ItemStack(Material.EXPERIENCE_BOTTLE);
                    }
                }

                ItemMeta itemMeta = potion.getItemMeta();

                itemMeta.setDisplayName(detergent.getDetergentData().name());


                List<String> detergentLore = List.of(CC.translate("&f&lPrice: " + detergent.getDetergentData().getPrice()),CC.translate("&f&lMultiplier: " + detergent.getDetergentData().getMultiplier()));

                itemMeta.setLore(detergentLore);

                potion.setItemMeta(itemMeta);

                menu.setItem(detergent.getDetergentData().getSlot(), potion);
            }

            p.playSound(p, Sound.BLOCK_CHISELED_BOOKSHELF_PICKUP, 1f, 1f);

            p.openInventory(menu);
        }


        @EventHandler
        public void detergentMenuClickListener(InventoryClickEvent event){

            if (event.getView().getTitle().equals(CC.translate("&b&lDetergent Store"))){
                event.setCancelled(true);
                if(event.getCurrentItem() != null){
                    String itemClicked = event.getCurrentItem().getItemMeta().getDisplayName();
                    Player player = (Player) event.getWhoClicked();
                    DetergentData detergentData = Detergent.fromName(itemClicked);
                    if (VaultHandler.getBalance(player)  >= detergentData.getPrice()){
                        player.getInventory().addItem(event.getCurrentItem());
                        VaultHandler.removeMoney(player, detergentData.getPrice());
                    } else {
                        player.sendMessage(CC.translate("&c&l&nYou don't have enough money!"));
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                    }
                }
            }
        }

    @EventHandler
    public void onDetergentMenuOpen(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.CHISELED_BOOKSHELF){
            event.setCancelled(true);
            openDetergentMenu(event.getPlayer());

        }

    }
}
