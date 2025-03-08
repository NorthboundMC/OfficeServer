package com.github.rosapetals.officeServer;


import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Color.*;
import java.awt.*;
import java.util.*;

import static com.github.rosapetals.officeServer.BossBarUtil.changeBossColor;
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
                            for(Player player: Bukkit.getOnlinePlayers()) {
                                updateBossBar(player, 0.25, "☼ MORNING CREW ☼");
                                player.getWorld().setTime(222200);
                                setCurrentSchedule("☼ MORNING CREW ☼");
                                player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 10, 1);
                                changeBossColor(player, BarColor.YELLOW);

                        }
                            break;
                        case 1:
                            for(Player player: Bukkit.getOnlinePlayers()) {
                                updateBossBar(player, 0.4, "◆ LUNCH BREAK ◆");
                                player.getWorld().setTime(5000);
                                setCurrentSchedule("◆ LUNCH BREAK ◆");
                                player.playSound(player, Sound.ENTITY_VILLAGER_CELEBRATE, 10, 1);
                                changeBossColor(player, BarColor.GREEN);
                            }
                            break;
                        case 2:
                            for(Player player: Bukkit.getOnlinePlayers()) {
                                updateBossBar(player, 0.5, "☾☼ AFTERNOON SHIFT ☾☼");
                                player.getWorld().setTime(6000);
                                setCurrentSchedule("☾☼ AFTERNOON SHIFT ☾☼");
                                player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 10, 1);
                                player.sendMessage("[BOSS] GET BACK TO WORK... ");
                                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 10, 1);
                                changeBossColor(player, BarColor.PINK);
                            }
                            break;
                        case 3:
                            for(Player player: Bukkit.getOnlinePlayers()) {
                                updateBossBar(player, 1.0, "⋆⁺₊⋆ ☾⋆⁺₊⋆NIGHT SHIFT⋆⁺₊⋆ ☾⋆⁺₊⋆");
                                player.getWorld().setTime(14000);
                                setCurrentSchedule("⋆⁺₊⋆ ☾⋆⁺₊⋆NIGHT SHIFT⋆⁺₊⋆ ☾⋆⁺₊⋆");
                                player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 10, 1);
                                player.playSound(player, Sound.ENTITY_GHAST_SCREAM, 10, 1);
                                changeBossColor(player, BarColor.RED);

                            }

                    }
                    step = (step + 1) % 4; // Loop back to 0 after the third message
                }
            }.runTaskTimer(OfficeServer.getInstance(), 0L, 6000L); // 2400 ticks = 2 minutes
        }
    }