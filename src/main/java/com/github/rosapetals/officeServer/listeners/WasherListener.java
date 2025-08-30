package com.github.rosapetals.officeServer.listeners;

import com.github.rosapetals.officeServer.OfficeServer;
import com.github.rosapetals.officeServer.features.Detergent;
import com.github.rosapetals.officeServer.features.DetergentData;
import com.github.rosapetals.officeServer.utils.CC;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class WasherListener implements Listener {

    // Adding Multipliers to Clothes
    // 1: Check if the player is holding something using p.getInventory().getItemInMainHand(), check to make sure it isnt null
    // 2: Next use getItemMeta() to check if it isn't null. if not, use Detergent.fromName(getItemInMainHand().getItemMeta().getDisplayName)
    // this will either return null or a DetergentData, so simply check to make sure it isn't null, if not, you can add the result to a local variable
    // 3: Now having the DetergentData, remove the item from the player's hand
    // 4: Then get the multiplier of the data and change lore line '1' of each clothing in the loop
    // by adding the initial price * the multiplier of the detergent

    private static final HashMap<UUID, Integer> washerStatus = new HashMap<>();
    // 0 = empty
    // 1 = washing
    // 2 = done
    private static final HashMap<UUID, Location> washingMachineLocation = new HashMap<>();

    private void openMenu(Player player) {
        int capacity = 5;
        Inventory menu = Bukkit.createInventory(null, InventoryType.HOPPER, player.getName() + "'s Washing Machine");

        player.openInventory(menu);
    }
    @EventHandler
    public void onWasherInteract (PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.BEEHIVE){
            event.setCancelled(true);

            int status = washerStatus.getOrDefault(player.getUniqueId(), 0);

            if (status == 1)
            {
                player.sendMessage(CC.translate("&c&l&nThe Washing Machine is already running!"));

            } else if (status == 2) {
                player.playSound(player.getLocation(), Sound.BLOCK_IRON_TRAPDOOR_OPEN, 1f, 1f);
                player.openInventory(player.getEnderChest());
            } else if (status == 0) {
                player.playSound(player.getLocation(), Sound.BLOCK_IRON_TRAPDOOR_OPEN, 1f, 1f);
                openMenu(player);
                washingMachineLocation.put(player.getUniqueId(), event.getClickedBlock().getLocation());

            }
        }

    }
    @EventHandler
    public void onInventoryClose (InventoryCloseEvent event){
        if (event.getPlayer() instanceof Player player && event.getView().getTitle().equals(player.getName() + "'s Washing Machine")) {


            if (event.getInventory().isEmpty()){
                return;
            }
            player.playSound(player.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 1f, 1f);

            washerStatus.put(player.getUniqueId(), 1);
            ItemStack[] clothes = event.getInventory().getContents();
            Inventory playerEnderChest = player.getEnderChest();
            int washerSpeed = 0;
            final double multiplier;

            Location loc = washingMachineLocation.get(player.getUniqueId());
            washingMachineLocation.remove(player.getUniqueId());

            if(player.getInventory().getItemInMainHand().getItemMeta() != null && Detergent.fromName(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()) != null) {
                DetergentData detergent =  Detergent.fromName(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
                multiplier = detergent.getMultiplier();
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            } else {
                multiplier = 1;
            }

            new BukkitRunnable() {
                int ticks = 0;
                @Override
                public void run() {
                    loc.getWorld().spawnParticle(Particle.WATER_BUBBLE, loc, 5, 0.2, 0.5, 0.2, 0.01);
                    if ((ticks +=5) >= 400) {
                        for (ItemStack item: clothes){

                            if (item == null || item.getItemMeta() == null)
                            {
                                continue;
                            }

                            ItemMeta meta = item.getItemMeta();
                            String name = meta.getDisplayName();
                            name = name.replace("Dirty", "Clean");
                            meta.setDisplayName(name);
                            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                            List<String> lore = meta.getLore();
                            double price = Double.parseDouble(ChatColor.stripColor(lore.get(1)).replaceAll("Price: ", ""));
                            lore.set(1, CC.translate("&5&lPrice: ")  + (multiplier * price));
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            playerEnderChest.addItem(item);
                            //temporary ^
                        }
                        washerStatus.put(player.getUniqueId(), 2);
                        player.playSound(player.getLocation(), Sound.BLOCK_BONE_BLOCK_PLACE, 1f, 1f);
                        this.cancel();
                    }
                }
            }.runTaskTimer(OfficeServer.getInstance(), 0, 5);

        }
    }

    @EventHandler
    public void onFullWashingMachineClose (InventoryCloseEvent event) {
    if (event.getInventory().getType() == InventoryType.ENDER_CHEST && event.getInventory().isEmpty()){
        washerStatus.put(event.getPlayer().getUniqueId(), 0);
    }


    }

}
