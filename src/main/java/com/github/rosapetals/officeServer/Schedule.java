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
            "☼ MORNING CREW ☼",
            "◆ LUNCH BREAK ◆",
            "☾☼ AFTERNOON SHIFT ☾☼",
            "⋆⁺₊⋆ ☾⋆⁺₊⋆NIGHT SHIFT⋆⁺₊⋆ ☾⋆⁺₊⋆"
    };

    private final long[] scheduleTimes = {222200, 5000, 6000, 14000};
    private final BarColor[] bossBarColors = {BarColor.YELLOW, BarColor.GREEN, BarColor.PINK, BarColor.RED};
    private final Sound[] scheduleSounds = {
            Sound.BLOCK_ANVIL_PLACE,
            Sound.ENTITY_VILLAGER_CELEBRATE,
            Sound.BLOCK_ANVIL_PLACE,
            Sound.BLOCK_ANVIL_PLACE
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

                Bukkit.getOnlinePlayers().iterator().next().getWorld().setTime(time);

                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateBossBar(player, (step + 1) * 0.25, message);
                    setCurrentSchedule(message);
                    player.playSound(player, sound, 10, 1);
                    changeBossColor(player, color);

                    if (step == 2) {
                        player.sendMessage(CC.translate("&c[BOSS] GET BACK TO WORK..."));
                        player.playSound(player, Sound.ENTITY_VILLAGER_NO, 10, 1);
                    }
                    else if (step == 3) {
                        player.playSound(player, Sound.ENTITY_GHAST_SCREAM, 10, 1);
                    }
                }

                step = (step + 1) % scheduleMessages.length;
            }
        }.runTaskTimer(OfficeServer.getInstance(), 0L, 3000L);
    }
    }