package com.github.rosapetals.officeServer.database;

import com.github.rosapetals.officeServer.OfficeServer;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;

@Slf4j
public class DatabaseManager {

    private Connection connection;
    public OfficeServer plugin;
    private static DatabaseManager instance;
    public static String DATABASE_URL;
    public static String DATABASE_USERNAME;
    public static String DATABASE_PASSWORD;

    public DatabaseManager(OfficeServer plugin){
        this.plugin = plugin;
    }

    public Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        DATABASE_URL = OfficeServer.getInstance().getConfig().getString("database");
        DATABASE_USERNAME = OfficeServer.getInstance().getConfig().getString("user");
        DATABASE_PASSWORD = OfficeServer.getInstance().getConfig().getString("password");

        this.connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

        System.out.println("Connected to SparkedHost Database!");
        return this.connection;
    }

    public void initializeDatabase() throws SQLException {
        Statement statement = getConnection().createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS laundromat_players(uuid varchar(36) primary key, washingmachinelevel int, dryerlevel int)";
        statement.execute(sql);

        System.out.println("Created table!");

    }

    public void createPlayerStats(PlayerData data) throws SQLException{



        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO laundromat_players(uuid, washingmachinelevel, dryerlevel) VALUES (?, ?, ?)");

        statement.setString(1, data.getUuid());
        statement.setInt(2, data.getWashingMachineLevel());
        statement.setInt(3, data.getDryerLevel());

        statement.executeUpdate();

        statement.close();

    }

    public void updatePlayerStats(PlayerData data) throws SQLException{
        new BukkitRunnable() {
            @Override
            public void run() {
                PreparedStatement statement = null;
                try {
                    statement = OfficeServer.getInstance().getDatabase().getConnection().prepareStatement("UPDATE laundromat_players SET washingmachinelevel = ?, dryerlevel = ? WHERE uuid = ?");
                    statement.setInt(1, data.getWashingMachineLevel());
                    statement.setInt(2, data.getDryerLevel());
                    statement.setString(3, data.getUuid());

                    statement.executeUpdate();

                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskAsynchronously(OfficeServer.getInstance());
    }


}
