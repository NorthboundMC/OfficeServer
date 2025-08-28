package com.github.rosapetals.officeServer;

import com.github.rosapetals.officeServer.listeners.*;
import com.github.rosapetals.officeServer.utils.VaultHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import static com.github.rosapetals.officeServer.utils.BossBarUtil.*;


public final class OfficeServer extends JavaPlugin implements Listener {
    private Economy economy = null;

    public Economy getEconomy() {
        return economy;
    }

    private static String currentSchedule = "";


    private static OfficeServer instance;

    public static OfficeServer getInstance() {
        return instance;
    }

    private static final Schedule schedule = new Schedule();




    @Override
    public void onEnable() {
        System.out.println("Works.");
        instance = this;
        Bukkit.getPluginManager().registerEvents(this,this);
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(),this);
        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
        Bukkit.getPluginManager().registerEvents(new LaundryPileListener(), this);
        Bukkit.getPluginManager().registerEvents(new WasherListener(), this);
        Bukkit.getPluginManager().registerEvents(new LaundrySellListener(), this);
        Bukkit.getPluginManager().registerEvents(new BookshelfListener(), this);
        vaultSetup();
        schedule.startCustomerLoop();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/minecraft:kill @e[type=minecraft:villager]");

        for(Player player: Bukkit.getOnlinePlayers()){
            createBossBar(player,"⋆⁺₊⋆ ☾⋆⁺₊⋆NIGHT SHIFT⋆⁺₊⋆ ☾⋆⁺₊⋆");
            changeBossColor(player, BarColor.PURPLE);
            player.getWorld().setTime(21000);
        }
    }

    private void vaultSetup() {
        RegisteredServiceProvider<Economy> rsp = this.getServer()
                .getServicesManager().getRegistration(Economy.class);

        if (rsp == null) throw new NullPointerException("Economy service provider was not found");
        economy = rsp.getProvider();
        rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) throw new NullPointerException("Economy service provider was not found");
        economy = rsp.getProvider();

        VaultHandler.initiate();
    }

    @Override
    public void onDisable() {
        for (Player player: Bukkit.getOnlinePlayers()) {
            removeBossBar(player);
        }
    }

    @SuppressWarnings("Lombok")
    public static void setCurrentSchedule(String string)  {
        currentSchedule = string;
    }
    @SuppressWarnings("Lombok")
    public static String getCurrentSchedule() {
        return currentSchedule;
    }


}
