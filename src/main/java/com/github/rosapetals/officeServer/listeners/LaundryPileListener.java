package com.github.rosapetals.officeServer.listeners;

import com.github.rosapetals.officeServer.Schedule;
import com.github.rosapetals.officeServer.utils.CC;
import org.bukkit.*;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LaundryPileListener implements Listener {
    private static List<String> rareDescriptors = List.of(
       "&4&lS&6&lh&e&li&a&ln&b&ly",
       "&4&lM&6&le&e&lt&a&la&b&ll&5&ll&4&li&6&lc",
       "&4&lR&6&la&e&lr&a&le",
       "&4&lO&6&lr&e&ln&a&la&b&lt&5&le",
       "&4&lM&6&la&e&lr&a&lv&b&le&5&ll&4&lo&6&lu&e&ls",
       "&4&lL&6&lu&e&lxu&a&lr&b&ly",
       "&4&lE&6&lx&e&lq&a&lu&b&li&5&ls&4&li&6&lt&e&le",
       "&4&lH&6&le&e&ll&a&le&b&ln&5&la&4&l'&6&ls",
       "&4&lR&6&lo&e&ls&a&la&b&l'&5&ls"
    );
    private static List<String> colors = List.of(
            "&4",
            "&6",
            "&e",
            "&a",
            "&b",
            "&5"



            );
    private static List<String> descriptors = List.of(
        "&oFancy",
        "&oSimple",
        "&oCute",
        "&oPretty",
        "&oNice",
        "&oOld",
        "&oCheap",
        "&oExpensive",
        "&oUgly",
        "&oRegular",
        "&oRetro",
        "&oModern",
        "&oUrban",
        "&oThin",
        "&oTough",
        "&oDelicate"
    );

    private static final Schedule schedule = new Schedule();

    private final Random RANDOM = new Random();

    @EventHandler
    public void onLaundryPileInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.COMPOSTER) {
            
            event.setCancelled(true);

            if (player.getInventory().firstEmpty() == -1)
            {
                player.sendMessage(CC.translate("&c&l&nYour inventory is full!"));
                return;
            }

            Levelled levelled = (Levelled) event.getClickedBlock().getBlockData();
            if (levelled.getLevel() == 8) {
                schedule.addVillagerSpawnLocation(new Location(player.getWorld(), event.getClickedBlock().getX() + 1.5, event.getClickedBlock().getY(), event.getClickedBlock().getZ() + 0.5 , 90, 0));
                levelled.setLevel(0);
                event.getClickedBlock().setBlockData(levelled);

                Color color = Color.fromRGB(RANDOM.nextInt(256), RANDOM.nextInt(256), RANDOM.nextInt(256));

                List<Material> leatherArmor = List.of(
                        Material.LEATHER_HELMET,
                        Material.LEATHER_CHESTPLATE,
                        Material.LEATHER_LEGGINGS,
                        Material.LEATHER_BOOTS
                );
                List<String> itemDescriptors = List.of(
                        "&fHat",
                        "&fShirt",
                        "&fPants",
                        "&fShoes"
                );

                int chosenClothingItem = RANDOM.nextInt(leatherArmor.size());

                ItemStack randomClothingItem = new ItemStack(leatherArmor.get(chosenClothingItem));
                LeatherArmorMeta randomClothingItemMeta = (LeatherArmorMeta) randomClothingItem.getItemMeta();
                if(RANDOM.nextInt(100) >= 90){
                    randomClothingItemMeta.setDisplayName(CC.translate("&fDirty " + rareDescriptors.get(RANDOM.nextInt(rareDescriptors.size())) + " " + itemDescriptors.get(chosenClothingItem)));
                    randomClothingItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    randomClothingItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    randomClothingItemMeta.setLore(List.of(CC.translate("&5&lRarity: Rare")));

                } else {

                    List<String> descriptors2 = new ArrayList<>(descriptors);
                    int firstDescriptor = RANDOM.nextInt(descriptors2.size());
                    String firstDescriptorName = descriptors2.get(firstDescriptor);
                    descriptors2.remove(firstDescriptor);

                    randomClothingItemMeta.setDisplayName(CC.translate("&fDirty " + colors.get(RANDOM.nextInt(colors.size())) + firstDescriptorName + " &fand " +  colors.get(RANDOM.nextInt(colors.size())) + descriptors2.get(RANDOM.nextInt(descriptors2.size())) + " " + itemDescriptors.get(chosenClothingItem)));
                    randomClothingItemMeta.setLore(List.of(CC.translate("&5&lRarity: Common")));
                    randomClothingItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    randomClothingItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);


                }

                randomClothingItemMeta.setColor(color);

                randomClothingItem.setItemMeta(randomClothingItemMeta);

                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_WOOL_HIT, 1f, 1f);

                player.getInventory().addItem(randomClothingItem);




            }


        }


    }
}