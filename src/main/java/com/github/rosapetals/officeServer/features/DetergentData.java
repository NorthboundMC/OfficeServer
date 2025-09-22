package com.github.rosapetals.officeServer.features;

import lombok.Data;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

@Data
public class DetergentData {

    private double multiplier;
    private double price;
    private String name;
    private PotionType displayItem;
    private String description;
    private int slot;


    public DetergentData(double multiplier, double price, String name, PotionType displayItem, String description, int slot) {
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

    public PotionType getDisplayItem() {
        return this.displayItem;
    }

    public String getDescription() {
        return this.description;
    }

    public int getSlot() {
        return this.slot;
    }

   }

