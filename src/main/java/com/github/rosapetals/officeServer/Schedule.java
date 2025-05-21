package com.github.rosapetals.officeServer;


import com.github.rosapetals.officeServer.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static com.github.rosapetals.officeServer.utils.BossBarUtil.changeBossColor;
import static com.github.rosapetals.officeServer.utils.BossBarUtil.updateBossBar;
import static com.github.rosapetals.officeServer.OfficeServer.setCurrentSchedule;


public class Schedule {


    private final String[] scheduleMessages = {
            "⋆⁺₊⋆ ☾⋆⁺₊⋆NIGHT SHIFT⋆⁺₊⋆ ☾⋆⁺₊⋆",
            "☼ DAYLIGHT ☼"
    };

    private final long[] scheduleTimes = {21000, 100};
    private final BarColor[] bossBarColors = {BarColor.PURPLE, BarColor.WHITE};
    private final Sound[] scheduleSounds = {
            Sound.BLOCK_ANVIL_PLACE,
            Sound.ENTITY_VILLAGER_CELEBRATE
    };

    public void startAnnouncementLoop() {
        new BukkitRunnable() {
            private int step = 0;

            @Override
            public void run() {
                String message = scheduleMessages[step];
                long time = scheduleTimes[step];
                BarColor color = bossBarColors[step];
                Sound sound = scheduleSounds[step];

                if (!Bukkit.getOnlinePlayers().isEmpty()) {
                    Bukkit.getOnlinePlayers().iterator().next().getWorld().setTime(time);
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateBossBar(player, 1, message);
                    setCurrentSchedule(message);
                    player.playSound(player, sound, 10, 1);
                    changeBossColor(player, color);

                    if (step == 0) {
                        player.sendMessage(CC.translate("&5&l[NIGHT SHIFT]&5 Better head back to the laundromat..."));
                    }
                }

                step = (step + 1) % scheduleMessages.length;
            }
        }.runTaskTimer(OfficeServer.getInstance(), 0L, 1000L);
    }
    }