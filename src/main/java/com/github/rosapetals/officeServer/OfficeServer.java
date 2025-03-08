package com.github.rosapetals.officeServer;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;



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
        schedule.startAnnouncementLoop();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



}
//hi