package com.github.rosapetals.officeServer;


import com.github.rosapetals.officeServer.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.github.rosapetals.officeServer.utils.BossBarUtil.changeBossColor;
import static com.github.rosapetals.officeServer.utils.BossBarUtil.updateBossBar;
import static com.github.rosapetals.officeServer.OfficeServer.setCurrentSchedule;


public class Schedule {


    private static Random RANDOM;

    private static OfficeServer instance;

    private static World world;

    private static List<Location> villagerSpawnLocations = new ArrayList<>();

    public void startCustomerLoop() {

        RANDOM = new Random();
        instance = OfficeServer.getInstance();
        world = instance.getServer().getWorld("world");

        List<Location> villagerSpawnLocations2 = List.of(

                new Location(world, -0.5, -60, 22.5, 90, 0),
                new Location(world, -0.5, -60, 21.5, 90, 0),
                new Location(world, -0.5, -60, 20.5, 90, 0),
                new Location(world, -0.5, -60, 19.5, 90, 0),
                new Location(world, -0.5, -60, 18.5, 90, 0)
                );

        villagerSpawnLocations = new ArrayList<>(villagerSpawnLocations2);

        new BukkitRunnable() {

            @Override
            public void run() {


                spawnNewCustomer();


            }
        }.runTaskTimer(instance, 0, RANDOM.nextInt(100, 140));
    }



    public void spawnNewCustomer() {

        if (villagerSpawnLocations.isEmpty())
        {
            return;
        }
        int chosenLocation = RANDOM.nextInt(villagerSpawnLocations.size());

        Villager villager = ((Villager) world.spawnEntity(villagerSpawnLocations.get(chosenLocation), EntityType.VILLAGER));

        Block composter = world.getBlockAt(new Location(world, villager.getLocation().getX() - 1.5, villager.getLocation().getY(), villager.getLocation().getZ()));

        Levelled composterLevel = (Levelled) composter.getBlockData();

        composterLevel.setLevel(8);
        composter.setBlockData(composterLevel);

        villager.setAI(false);

        world.playSound(villager.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 0.3f, 1f);

        villager.setSilent(true);

        villagerSpawnLocations.remove(chosenLocation);
        new BukkitRunnable() {

            @Override
            public void run() {

                villager.remove();

            }
        }.runTaskLater(instance, 40);

    }

    public void addVillagerSpawnLocation(Location location){

        villagerSpawnLocations.add(location);

    }
}