package com.github.rosapetals.officeServer.menus;


import com.github.rosapetals.officeServer.utils.MoneyFormatter;
import com.github.rosapetals.officeServer.utils.VaultHandler;
import com.github.rosapetals.officeServer.utils.ItemUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class ComputerMenu implements Listener {

    public static void openMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 36, player.getDisplayName() + "'s computer");

        ItemStack playBal = ItemUtils.CreateCustomItem(new ItemStack(Material.NETHER_STAR), player.getName() + "'s Balance:", MoneyFormatter.put((long)VaultHandler.getBalance(player)),true);
        menu.setItem(32, playBal);

        player.openInventory(menu);
    }


}