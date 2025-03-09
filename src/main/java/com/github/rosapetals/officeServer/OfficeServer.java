package com.github.rosapetals.officeServer;

import com.github.rosapetals.officeServer.listeners.BlockListener;
import com.github.rosapetals.officeServer.listeners.CommandListener;
import com.github.rosapetals.officeServer.listeners.PlayerListeners;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import static com.github.rosapetals.officeServer.utils.BossBarUtil.*;


public final class OfficeServer extends JavaPlugin implements Listener {

    private static String currentSchedule = "";

    private static OfficeServer instance;

    public static OfficeServer getInstance() {
        return instance;
    }
    public static void setCurrentSchedule(String string)  {
        currentSchedule = string;
    }
    public static String getCurrentSchedule() {
        return currentSchedule;
    }


    private static final Schedule schedule = new Schedule();




    @Override
    public void onEnable() {
        System.out.println("Works.");
        instance = this;
        Bukkit.getPluginManager().registerEvents(this,this);
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(),this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
        schedule.startAnnouncementLoop();
        for(Player player: Bukkit.getOnlinePlayers()){
            createBossBar(player,"☼ MORNING CREW ☼");
            changeBossColor(player, BarColor.YELLOW);
        }
    }

    @Override
    public void onDisable() {
        for (Player player: Bukkit.getOnlinePlayers()) {
            removeBossBar(player);
        }
    }



}
//hi