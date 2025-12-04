package com.github.rosapetals.officeServer.features;

import com.github.rosapetals.officeServer.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Beehive;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LaundryUnitManager {


    private static final Map<Location, LaundryUnit> activeUnits = new HashMap<>();


    public static void claimUnit(Player player, Location washerLocation) {

        Location dryerLocation = washerLocation.clone().add(0, 1, 0);

        if (activeUnits.containsKey(washerLocation)) {
            player.sendMessage(CC.translate("&cThis unit has already been claimed by " + Bukkit.getPlayer(activeUnits.get(washerLocation).getOwner())));
            return;
        }


        LaundryUnit unit = new LaundryUnit(
                player.getUniqueId(),
                washerLocation,
                dryerLocation
        );

        activeUnits.put(washerLocation, unit);
        activeUnits.put(dryerLocation, unit);
    }

    public static void unclaimUnit(Location washerLocation) {

        Location dryerLocation = washerLocation.clone().add(0, 1, 0);

        activeUnits.remove(washerLocation);
        activeUnits.remove(dryerLocation);

        Block block = washerLocation.getBlock();
        Beehive hive = (Beehive) block.getBlockData();
        hive.update(true, false);

    }

    public static Map<Location, LaundryUnit> getActiveUnits() {return Collections.unmodifiableMap(activeUnits);}

}
