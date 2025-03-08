package com.github.rosapetals.officeServer;

import org.bukkit.plugin.java.JavaPlugin;

public final class OfficeServer extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Works.");
        Schedule.schedule();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
//hi