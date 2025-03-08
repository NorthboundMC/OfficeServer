package com.github.rosapetals.officeServer;


import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static com.github.rosapetals.officeServer.BossBarUtil.updateBossBar;
import static com.github.rosapetals.officeServer.OfficeServer.setCurrentSchedule;


public class Schedule {
    private static final OfficeServer officePlugin = OfficeServer.getInstance();

        public void startAnnouncementLoop() {
            new BukkitRunnable() {
                private int step = 0;
                @Override
                public void run() {
                    switch (step) {
                        case 0:
                            Bukkit.broadcastMessage("§aHi");
                            for(Player player: Bukkit.getOnlinePlayers()) {
                                updateBossBar(player, 0.25, "Morning");
                                player.getWorld().setTime(2300);
                                setCurrentSchedule("Morning");
                        }
                            break;
                        case 1:
                            Bukkit.broadcastMessage("§bHello");
                            for(Player player: Bukkit.getOnlinePlayers()) {
                                updateBossBar(player, 0.5, "Afternoon");
                                player.getWorld().setTime(6000);
                                setCurrentSchedule("Afternoon");

                            }
                            break;
                        case 2:
                            Bukkit.broadcastMessage("§eWhat's up");
                            for(Player player: Bukkit.getOnlinePlayers()) {
                                updateBossBar(player, 1.0, "Night");
                                player.getWorld().setTime(12000);
                                setCurrentSchedule("Night");
                            }
                            break;
                    }
                    step = (step + 1) % 3; // Loop back to 0 after the third message
                }
            }.runTaskTimer(OfficeServer.getInstance(), 0L, 600L); // 2400 ticks = 2 minutes
        }
    }