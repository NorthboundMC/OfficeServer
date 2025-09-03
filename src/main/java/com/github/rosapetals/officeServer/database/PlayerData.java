package com.github.rosapetals.officeServer.database;

import com.github.rosapetals.officeServer.OfficeServer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.sql.SQLException;

public class PlayerData {

    @Getter
    @Setter
    private String uuid;

    @Getter
    @Setter
    private int washingMachineLevel;

    @Getter
    @Setter
    private int dryerLevel;

    public PlayerData(String uuid, int washingMachineLevel, int dryerLevel) {
        this.uuid = uuid;
        this.washingMachineLevel = washingMachineLevel;
        this.dryerLevel = dryerLevel;
    }

    public void save() {
        try {
            OfficeServer.getInstance().getDatabase().updatePlayerStats(this);
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Could not update player stats to the database, ignoring.");
            e.printStackTrace();
        }
    }



}
