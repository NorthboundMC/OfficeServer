package com.github.rosapetals.officeServer.listeners;

import com.github.rosapetals.officeServer.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class BookshelfListener implements Listener {
    private void openMenu(Player player) {

        Inventory menu = Bukkit.createInventory(null, 18, CC.translate("&b&lDetergent Store"));

        player.openInventory(menu);
    }


}
