package com.github.rosapetals.officeServer.listeners;

import com.github.rosapetals.officeServer.OfficeServer;
import com.github.rosapetals.officeServer.database.PlayerData;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (OfficeServer.getInstance().getPlayerData().get(event.getPlayer().getUniqueId()) != null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    // Check if they don't exist in the db (first time join)
                    PreparedStatement statement = OfficeServer.getInstance().getDatabase().getConnection().prepareStatement("SELECT * FROM laundromat_players WHERE uuid = ?");
                    statement.setString(1, event.getPlayer().getUniqueId().toString());
                    ResultSet results = statement.executeQuery();
                    if (!results.next()) {
                        PlayerData data = new PlayerData(event.getPlayer().getUniqueId().toString(), 0, 0);

                        OfficeServer.getInstance().getPlayerData().put(event.getPlayer().getUniqueId(), data);
                        statement.close();
                    } else {
                        PlayerData data = new PlayerData(event.getPlayer().getUniqueId().toString(), results.getInt(2), results.getInt(3));
                        OfficeServer.getInstance().getPlayerData().put(event.getPlayer().getUniqueId(), data);
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        }.runTaskAsynchronously(OfficeServer.getInstance());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                PlayerData data = OfficeServer.getInstance().getPlayerData().get(event.getPlayer().getUniqueId());
                data.save();
            }
        }.runTaskAsynchronously(OfficeServer.getInstance());
    }
}
