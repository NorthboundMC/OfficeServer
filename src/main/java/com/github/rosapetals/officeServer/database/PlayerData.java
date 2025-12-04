package com.github.rosapetals.officeServer.database;

import com.github.rosapetals.officeServer.OfficeServer;
import com.github.rosapetals.officeServer.features.LaundryUnit;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PlayerData {


    private String uuid;

    private int washingMachineLevel;

    private int dryerLevel;

    private ItemStack[] dryerInventory;

    private ItemStack[] washerInventory;

    private LaundryUnit laundryUnit;


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


    public int getWashingMachineLevel() {
        return washingMachineLevel;
    }

    public void setWashingMachineLevel(int washingMachineLevel) {
        this.washingMachineLevel = washingMachineLevel;
    }

    public int getDryerLevel() {
        return dryerLevel;
    }

    public void setDryerLevel(int dryerLevel) {
        this.dryerLevel = dryerLevel;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ItemStack[] getWasherInventory() {
        return washerInventory;
    }

    public void setWasherInventory(ItemStack[] washerInventory) {
        this.washerInventory = washerInventory;
    }

    public ItemStack[] getDryerInventory() {
        return dryerInventory;
    }

    public void setDryerInventory(ItemStack[] dryerInventory) {
        this.dryerInventory = dryerInventory;
    }

    public void setLaundryUnit(LaundryUnit laundryUnit) {
        this.laundryUnit = laundryUnit;
    }

    public LaundryUnit getLaundryUnit() {return laundryUnit;}

}
