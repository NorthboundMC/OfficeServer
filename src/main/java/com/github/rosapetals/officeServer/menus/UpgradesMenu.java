package com.github.rosapetals.officeServer.menus;

import com.github.rosapetals.officeServer.OfficeServer;
import com.github.rosapetals.officeServer.database.PlayerData;
import com.github.rosapetals.officeServer.utils.*;
import net.royawesome.jlibnoise.module.modifier.Invert;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class UpgradesMenu implements Listener {
    private void openUpgradeMenu(Player p) {
        Inventory menu = Bukkit.createInventory(null, InventoryType.HOPPER, CC.translate("Upgrade Menu"));
        menu.setItem(1, ItemUtils.CreateCustomItem(GetPlayerHead.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWExNzJkZDUwYWIxZTk0NjFlMzVlOWE5OGVhNTU0MzZhYTQ1N2Q4YjViZGMwOWIzYmJkNzM1NGUyMjA1ZmJhZSJ9fX0="), CC.translate("&lWashing Machine"), "", false));
        menu.setItem(3, ItemUtils.CreateCustomItem(GetPlayerHead.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGQ3ZGNkMjBkZWUxNTE5ZDNhMTAyMGUyOTE5NjhlNDhiNTRmODYyNjU1NTNiZDUwM2Y5NjU3N2I1ZDc3NmIwOSJ9fX0="), CC.translate("&lDryer"), "", true));
        p.playSound(p, Sound.ENTITY_ARROW_HIT_PLAYER, 1f, 1f);
        p.openInventory(menu);
    }
    @EventHandler
    public void upgradeMenuClickListener(InventoryClickEvent event){

        if (event.getView().getTitle().equals(CC.translate("Upgrade Menu")) && event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR){
            ItemStack clickedItem = event.getCurrentItem();
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if (ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equals("Washing Machine")){
                openWashingMachineUpgradeMenu(player);
            }
            else if (ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equals("Dryer")){
                openUpgradeMenu(player);
            }
        }
    }
    private void openWashingMachineUpgradeMenu(Player p) {
        Inventory menu = Bukkit.createInventory(null, InventoryType.HOPPER, CC.translate("&eUpgrade Tiers"));
        PlayerData data = OfficeServer.getInstance().getPlayerData().get(p.getUniqueId());
        int washingMachineLevel = data.getWashingMachineLevel();
        menu.setItem(0, ItemUtils.CreateCustomItem(new ItemStack(Material.BARRIER), CC.translate("&6&lBACK"), "", false));
        menu.setItem(1, ItemUtils.CreateCustomItem(new ItemStack(Material.IRON_INGOT), CC.translate("&6&lIron"), washingMachineLevel < 1 ? "You can get this upgrade" : "You already have this upgrade!", washingMachineLevel >= 1));
        menu.setItem(2, ItemUtils.CreateCustomItem(new ItemStack(Material.GOLD_INGOT), CC.translate("&6&lGold"), washingMachineLevel < 1 ? "Locked" : (washingMachineLevel < 2 ? "You can get this upgrade" : "You already have this upgrade!"), washingMachineLevel >= 2));
        menu.setItem(3, ItemUtils.CreateCustomItem(new ItemStack(Material.DIAMOND), CC.translate("&6&lDiamond"), washingMachineLevel < 1 ? "Locked" : (washingMachineLevel < 2 ? "You can get this upgrade" : "You already have this upgrade!"), washingMachineLevel >= 2));
        menu.setItem(4, ItemUtils.CreateCustomItem(new ItemStack(Material.OBSIDIAN), CC.translate("&6&lObsidian"), washingMachineLevel < 1 ? "Locked" : (washingMachineLevel < 2 ? "You can get this upgrade" : "You already have this upgrade!"), washingMachineLevel >= 2));
        p.openInventory(menu);
    }
    private void openDryerUpgradeMenu(Player p) {
        //1 - iron
        //2 - gold
        //3 - diamond
        //4 - obsidian
        Inventory menu = Bukkit.createInventory(null, InventoryType.HOPPER, CC.translate("&eUpgrade Tiers"));
        PlayerData data = OfficeServer.getInstance().getPlayerData().get(p.getUniqueId());
        int dryerLevel = data.getDryerLevel();
        menu.setItem(0, ItemUtils.CreateCustomItem(new ItemStack(Material.BARRIER), CC.translate("&6&lBACK"), "", false));
        menu.setItem(1, ItemUtils.CreateCustomItem(new ItemStack(Material.IRON_INGOT), CC.translate("&6&lBuy a dryer"), dryerLevel < 1 ? "You can get this upgrade" : "You already have this upgrade!", dryerLevel >= 1));
        menu.setItem(2, ItemUtils.CreateCustomItem(new ItemStack(Material.GOLD_INGOT), CC.translate("&6&lGold"), dryerLevel < 1 ? "Locked" : (dryerLevel < 2 ? "You can get this upgrade" : "You already have this upgrade!"), dryerLevel >= 2));
        menu.setItem(3, ItemUtils.CreateCustomItem(new ItemStack(Material.DIAMOND), CC.translate("&6&lDiamond"), dryerLevel < 2 ? "Locked" : (dryerLevel < 3 ? "You can get this upgrade" : "You already have this upgrade!"), dryerLevel >= 3));
        menu.setItem(4, ItemUtils.CreateCustomItem(new ItemStack(Material.OBSIDIAN), CC.translate("&6&lObsidian"), dryerLevel < 3 ? "Locked" : (dryerLevel < 4 ? "You can get this upgrade" : "You already have this upgrade!"), dryerLevel >= 2));
        p.openInventory(menu);
    }
    @EventHandler
    public void onUpgradeMenuOpen(PlayerInteractEntityEvent event) {

        if (event.getRightClicked() instanceof Villager villager && ChatColor.stripColor(villager.getName()).equals("Margaret")){
            openUpgradeMenu(event.getPlayer());
        }
    }

}
