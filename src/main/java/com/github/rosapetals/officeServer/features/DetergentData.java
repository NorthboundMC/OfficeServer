package com.github.rosapetals.officeServer.features;

import lombok.Data;
import org.bukkit.potion.PotionData;

@Data
public class DetergentData {

    private double multiplier;
    private double price;
    private String name;
    private PotionData displayItem;
    private String description;
    private int slot;


    public DetergentData(double multiplier, double price, String name, PotionData displayItem, String description, int slot) {
        this.multiplier = multiplier;
        this.price = price;
        this.name = name;
        this.displayItem = displayItem;
        this.description = description;
        this.slot  = slot;

    }

    public double getMultiplier() {
        return this.multiplier;
    }

    public double getPrice() {
        return this.price;
    }

    public String name() {
        return this.name;
    }

    public PotionData getDisplayItem() {
        return this.displayItem;
    }

    public String getDescription() {
        return this.description;
    }

    public int getSlot() {
        return this.slot;
    }

   }

