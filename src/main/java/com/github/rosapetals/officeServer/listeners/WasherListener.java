package com.github.rosapetals.officeServer.listeners;

import com.github.rosapetals.officeServer.OfficeServer;
import com.github.rosapetals.officeServer.database.PlayerData;
import com.github.rosapetals.officeServer.features.Detergent;
import com.github.rosapetals.officeServer.features.DetergentData;
import com.github.rosapetals.officeServer.features.LaundryUnitManager;
import com.github.rosapetals.officeServer.utils.CC;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
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

            PlayerData data = OfficeServer.getInstance().getPlayerData().get(player.getUniqueId());

            if (data.getLaundryUnit() == null) {

                LaundryUnitManager.claimUnit(player, event.getClickedBlock().getLocation());
                return;
            } else if (!(data.getLaundryUnit().getWasherLocation().equals(event.getClickedBlock().getLocation()))) {
                LaundryUnitManager.claimUnit(player, event.getClickedBlock().getLocation());
                return;
            }

            int status = washerStatus.getOrDefault(player.getUniqueId(), 0);

            if (status == 1)
            {
                player.sendMessage(CC.translate("&c&l&nThe Washing Machine is already running!"));

            } else if (status == 2) {
                player.playSound(player.getLocation(), Sound.BLOCK_IRON_TRAPDOOR_OPEN, 1f, 1f);

                Inventory washerInventory = Bukkit.createInventory(null, InventoryType.CHEST, player.getName() + "'s Washing Machine.");

                washerInventory.setContents(OfficeServer.getInstance().getPlayerData().get(player.getUniqueId()).getWasherInventory());

                player.openInventory(washerInventory);


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

            final double multiplier;

            double zFactor = 0;
                    if(((Directional) washingMachineLocation.get(player.getUniqueId()).getBlock().getBlockData()).getFacing() == BlockFace.SOUTH){
                        zFactor = 1;
                    }
            Location loc = washingMachineLocation.get(player.getUniqueId()).add(0.5,0.5,zFactor);
            washingMachineLocation.remove(player.getUniqueId());

            if(player.getInventory().getItemInMainHand().getItemMeta() != null && Detergent.fromName(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()) != null) {
                DetergentData detergent =  Detergent.fromName(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
                multiplier = detergent.getMultiplier();
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            } else {
                multiplier = 1;
            }

            PlayerData data = OfficeServer.getInstance().getPlayerData().get(player.getUniqueId());

            List<ItemStack> list = new java.util.ArrayList<>(List.of());
            WashParticles(loc, player.getUniqueId());

            Bukkit.getScheduler().runTaskLater(OfficeServer.getInstance(), () -> {


                for (ItemStack item: clothes){

                    if (item == null || item.getItemMeta() == null)
                    {
                        continue;
                    }

                    ItemMeta meta = item.getItemMeta();
                    String name = meta.getDisplayName();
                    name = name.replace("Dirty", "Wet");
                    meta.setDisplayName(name);
                    meta.addEnchant(Enchantment.INFINITY, 1, true);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    List<String> lore = meta.getLore();
                    double price = Double.parseDouble(ChatColor.stripColor(lore.get(1)).replaceAll("Price: ", ""));
                    lore.set(1, CC.translate("&5&lPrice: ")  + (multiplier * price));
                    lore.add(CC.translate("&8Cleaned by: " + player.getName()));
                    meta.setLore(lore);
                    item.setItemMeta(meta);

                    list.add(item);


                }
                washerStatus.put(player.getUniqueId(), 2);
                player.playSound(player.getLocation(), Sound.BLOCK_BONE_BLOCK_PLACE, 1f, 1f);
                data.setWasherInventory(list.toArray(new ItemStack[0]));
                }, 400);

        }
    }

    @EventHandler
    public void onFullWashingMachineClose (InventoryCloseEvent event) {
    if (event.getView().getTitle().equals(event.getPlayer().getName() + "'s Washing Machine.") && event.getInventory().isEmpty()){
        washerStatus.put(event.getPlayer().getUniqueId(), 0);
    }


    }

    public static int getWasherStatus(UUID player) {
        return washerStatus.get(player) != null ? washerStatus.get(player) : -1;
    }

    private void WashParticles(Location loc, UUID player) {

        new BukkitRunnable() {
            @Override
            public void run() {
                if (washerStatus.get(player) != 1) this.cancel();
                loc.getWorld().playSound(loc, Sound.BLOCK_PISTON_CONTRACT, 0.01f, 1.0f);

                loc.getWorld().spawnParticle(Particle.BUBBLE_POP, loc, 3, 0.3, 0.1, 0.05, 0.1);
            }
        }.runTaskTimer(OfficeServer.getInstance(), 0L, 2L);
    }

}
