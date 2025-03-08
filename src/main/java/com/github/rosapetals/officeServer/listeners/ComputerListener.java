package com.github.rosapetals.officeServer.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ComputerListener implements Listener {



    @EventHandler
    public void onComputerOpen(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.PLAYER_HEAD){

            
            
        }






    }

}
