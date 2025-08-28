package com.github.rosapetals.officeServer.menus;

import com.github.rosapetals.officeServer.features.Detergent;
import com.github.rosapetals.officeServer.utils.CC;
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
import org.bukkit.inventory.meta.OminousBottleMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;


public class DetergentMenu implements Listener {

        private void openDetergentMenu(Player p) {

            Inventory menu = Bukkit.createInventory(null, 27, CC.translate("&b&lDetergent Store"));

            for(Detergent detergent : Detergent.values())
            {
                ItemStack potion = new ItemStack(Material.POTION);
                if (detergent.getDetergentData().getDisplayItem() != null) {
                    PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

                    potionMeta.setBasePotionData(detergent.getDetergentData().getDisplayItem());

                    potion.setItemMeta(potionMeta);

                } else {
                    if(detergent.getDetergentData().name().equals(CC.translate("&f&9Cleaner X"))){
                        potion = Bukkit.getItemFactory().createItemStack("ominous_bottle");

                    } else if (detergent.getDetergentData().name().equals(CC.translate("&f&9MagiClean"))) {
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





            }
        }

    @EventHandler
    public void onDetergentMenuOpen(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.CHISELED_BOOKSHELF){
            openDetergentMenu(event.getPlayer());

        }

    }
}
