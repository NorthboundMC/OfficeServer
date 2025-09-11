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

public class DryerListener implements Listener {


    private static final HashMap<UUID, Integer> dryerStatus = new HashMap<>();
    // 0 = empty
    // 1 = washing
    // 2 = done
    private static final HashMap<UUID, Location> dryerLocation = new HashMap<>();

    private void openMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, InventoryType.CHEST, player.getName() + "'s Dryer");

        player.openInventory(menu);
    }
    @EventHandler
    public void onDryerInteract (PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.BEE_NEST){
            event.setCancelled(true);

            int status = dryerStatus.getOrDefault(player.getUniqueId(), 0);

            if (status == 1)
            {
                player.sendMessage(CC.translate("&c&l&nThe Dryer is already running!"));

            } else if (status == 2) {
                player.playSound(player.getLocation(), Sound.BLOCK_IRON_TRAPDOOR_OPEN, 1f, 1f);
                player.openInventory(player.getEnderChest());
            } else if (status == 0) {
                player.playSound(player.getLocation(), Sound.BLOCK_IRON_TRAPDOOR_OPEN, 1f, 1f);
                openMenu(player);
                dryerLocation.put(player.getUniqueId(), event.getClickedBlock().getLocation());

            }
        }

    }
    @EventHandler
    public void onInventoryClose (InventoryCloseEvent event){
        if (event.getPlayer() instanceof Player player && event.getView().getTitle().equals(player.getName() + "'s Dryer")) {

            if (event.getInventory().isEmpty()){
                return;
            }
            player.playSound(player.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 1f, 1f);

            dryerStatus.put(player.getUniqueId(), 1);
            ItemStack[] clothes = event.getInventory().getContents();
            Inventory playerEnderChest = player.getEnderChest();
            Location loc = dryerLocation.get(player.getUniqueId());
            dryerLocation.remove(player.getUniqueId());


            new BukkitRunnable() {
                int ticks = 0;
                @Override
                public void run() {
                    loc.getWorld().spawnParticle(Particle.SPORE_BLOSSOM_AIR, loc, 5, 0.2, 0.5, 0.2, 0.01);
                    if ((ticks +=5) >= 400) {
                        for (ItemStack item: clothes){

                            if (item == null || item.getItemMeta() == null)
                            {
                                continue;
                            }

                            ItemMeta meta = item.getItemMeta();
                            String name = meta.getDisplayName();
                            name = name.replace("Wet", "Clean");
                            meta.setDisplayName(name);
                            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                            item.setItemMeta(meta);
                            playerEnderChest.addItem(item);
                            //temporary ^
                        }
                        dryerStatus.put(player.getUniqueId(), 2);
                        player.playSound(player.getLocation(), Sound.ITEM_ELYTRA_FLYING, 1f, 1f);
                        this.cancel();
                    }
                }
            }.runTaskTimerAsynchronously(OfficeServer.getInstance(), 0, 5);

        }
    }

    @EventHandler
    public void onFullDryerClose (InventoryCloseEvent event) {
        if (event.getInventory().getType() == InventoryType.ENDER_CHEST && event.getInventory().isEmpty()){
            dryerStatus.put(event.getPlayer().getUniqueId(), 0);
        }


    }

}
