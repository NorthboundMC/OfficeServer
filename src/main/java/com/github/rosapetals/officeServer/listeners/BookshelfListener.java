package com.github.rosapetals.officeServer.listeners;

import com.github.rosapetals.officeServer.menus.DetergentMenu;
import com.github.rosapetals.officeServer.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class BookshelfListener implements Listener {

    DetergentMenu detergentMenu = new DetergentMenu();

    @EventHandler
    public void onComputerOpen(PlayerInteractEvent event) {

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.CHISELED_BOOKSHELF){
                detergentMenu.openDetergentMenu(event.getPlayer());

        }

    }


    private void openMenu(Player player) {

        detergentMenu.openDetergentMenu(player);
    }




}
