package com.github.rosapetals.officeServer.features;

import com.github.rosapetals.officeServer.OfficeServer;
import com.github.rosapetals.officeServer.database.PlayerData;
import com.github.rosapetals.officeServer.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Beehive;

import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LaundryUnitManager {


    private static final Map<Location, LaundryUnit> activeUnits = new HashMap<>();


    public static void claimUnit(Player player, Location blockLocation) {

        Block washerBlock;
        Block dryerBlock;
        Location washerLocation;
        Location dryerLocation;

        if (blockLocation.getBlock().getType() == Material.BEEHIVE) {
            dryerLocation = blockLocation.clone().add(0, 1, 0);
            washerLocation = blockLocation;
            washerBlock = blockLocation.getBlock();
            dryerBlock = dryerLocation.getBlock();
        } else {
            washerLocation = blockLocation.clone().add(0, -1, 0);
            dryerLocation = blockLocation;
            washerBlock = washerLocation.getBlock();
            dryerBlock = blockLocation.getBlock();
        }

        PlayerData data = OfficeServer.getInstance().getPlayerData().get(player.getUniqueId());

        if (activeUnits.containsKey(washerLocation)) {
            player.sendMessage(CC.translate("&cThis unit has already been claimed by " + Bukkit.getPlayer(activeUnits.get(washerLocation).getOwner()).getName()));
            return;
        } else if (data.getLaundryUnit() != null) {
            player.sendMessage(CC.translate("&cYou already own a unit."));
            return;
        }

        setHoneyLevel(washerBlock, data.getWashingMachineLevel());
        if (data.getDryerLevel() == 0) {

            dryerBlock.setType(Material.OAK_FENCE);
            player.sendMessage(CC.translate("&bThis unit now belongs to you. The dryer previously there was broken and could not be fixed. You will have to buy a new one when you can afford it."));


        } else {
            setHoneyLevel(dryerBlock, data.getDryerLevel());
            player.sendMessage(CC.translate("&bThis unit now belongs to you."));

        }

        LaundryUnit unit = new LaundryUnit(
                player.getUniqueId(),
                washerLocation,
                dryerLocation
        );

        activeUnits.put(washerLocation, unit);
        data.setLaundryUnit(unit);
    }

    public static void unclaimUnit(Location washerLocation) {

        Location dryerLocation = washerLocation.clone().add(0, 1, 0);

        activeUnits.remove(washerLocation);

        Block washerBlock = washerLocation.getBlock();
        Block dryerBlock = dryerLocation.getBlock();

        if (dryerBlock.getType() == Material.OAK_FENCE) {
            dryerBlock.setType(Material.BEE_NEST);
        }

        setHoneyLevel(washerBlock, 1);
        setHoneyLevel(dryerBlock, 0);

    }

    private static void setHoneyLevel(Block block, int level) {


        Beehive data = (Beehive) block.getBlockData();

        data.setHoneyLevel(level);

        block.setBlockData(data, true);

    }

    public static Map<Location, LaundryUnit> getActiveUnits() {
        return Collections.unmodifiableMap(activeUnits);
    }
}