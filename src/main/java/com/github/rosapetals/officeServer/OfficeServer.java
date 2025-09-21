package com.github.rosapetals.officeServer;

import com.github.rosapetals.officeServer.database.DatabaseManager;
import com.github.rosapetals.officeServer.database.PlayerData;
import com.github.rosapetals.officeServer.listeners.*;
import com.github.rosapetals.officeServer.menus.DetergentMenu;
import com.github.rosapetals.officeServer.menus.RestaurantMenu;
import com.github.rosapetals.officeServer.scoreboard.Scoreboard;
import com.github.rosapetals.officeServer.scoreboard.mrmicky.FastBoard;
import com.github.rosapetals.officeServer.utils.VaultHandler;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.rosapetals.officeServer.utils.BossBarUtil.*;


@Slf4j
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

    private DatabaseManager database;

    private final Map<UUID, PlayerData> playerData = new HashMap<>();

    private final Map<UUID, FastBoard> playerScoreBoards = new HashMap<>();

    private final Scoreboard scoreboard = new Scoreboard();




    @Override
    public void onEnable() {
        System.out.println("Works.");
        instance = this;
        this.getCommand("checkme").setExecutor(new CheckCommand());
        Bukkit.getPluginManager().registerEvents(this,this);
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(),this);
        Bukkit.getPluginManager().registerEvents(new LaundryPileListener(), this);
        Bukkit.getPluginManager().registerEvents(new WasherListener(), this);
        Bukkit.getPluginManager().registerEvents(new DryerListener(), this);
        Bukkit.getPluginManager().registerEvents(new RestaurantMenu(), this);
        Bukkit.getPluginManager().registerEvents(new Scoreboard(), this);
        Bukkit.getPluginManager().registerEvents(new JoinLeaveListener(), this);
        Bukkit.getPluginManager().registerEvents(new ClothesLineListener(), this);
        Bukkit.getPluginManager().registerEvents(new DetergentMenu(), this);
        Bukkit.getPluginManager().registerEvents(new LaundrySellListener(), this);
        HandleDatabase();
        vaultSetup();
        schedule.startCustomerLoop();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/minecraft:kill @e[type=minecraft:villager]");

        Scoreboard.initializePlugin();
        for(Player player: Bukkit.getOnlinePlayers()){
            Scoreboard.createBoard(player);
            scoreboard.boardRunnable(player.getUniqueId(), player);
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


    private void HandleDatabase() {


        try{
            this.database = new DatabaseManager(this);
            database.initializeDatabase();
        } catch (SQLException e){
            Bukkit.getLogger().severe("Unable to load database, connect, or create tables");
            e.printStackTrace();

        }


        for (Player player : Bukkit.getOnlinePlayers()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        // Check if they don't exist in the db (first time join)
                        PreparedStatement statement = database.getConnection().prepareStatement("SELECT * FROM laundromat_players WHERE uuid = ?");
                        statement.setString(1, player.getUniqueId().toString());
                        ResultSet results = statement.executeQuery();
                        if (!results.next()) {
                            PlayerData data = new PlayerData(player.getUniqueId().toString(), 0, 0);
                            OfficeServer.getInstance().getDatabase().createPlayerStats(data);
                            playerData.put(player.getUniqueId(), data);
                            statement.close();
                        } else {
                            PlayerData data = new PlayerData(player.getUniqueId().toString(), results.getInt(2), results.getInt(3));

                            playerData.put(player.getUniqueId(), data);
                        }
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                }
            }.runTaskAsynchronously(this);
        }

    }


    public DatabaseManager getDatabase() {
        return database;
    }

    public Map<UUID, PlayerData> getPlayerData() {
        return playerData;
    }

    public Map<UUID, FastBoard> getPlayerScoreBoards() {
        return playerScoreBoards;
    }

}
