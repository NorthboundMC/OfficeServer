package com.github.rosapetals.officeServer.features;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WeaponData {
    private String name;
    private ItemStack item;
    private int customModelData;
    private List<Enchantment> enchants;
    private List<Integer> levels;
    private double attackSpeed;
    private double damage;
    private boolean unbreakable;
    private double price;
        public WeaponData(String name, ItemStack item, int customModelData, List<Enchantment> enchants, List<Integer> levels, double attackSpeed, double damage, boolean unbreakable, double price){
            this.item = item;
            this.name = name;
            this.customModelData = customModelData;
            this.enchants = enchants;
            this.levels = levels;
            this.attackSpeed = attackSpeed;
            this.damage = damage;
            this.unbreakable = unbreakable;
            this.price = price;
        }

    public ItemStack getItem() {
        return this.item;
    }
    public String name() {
        return this.name;
    }
    public int getCustomModelData() {
        return this.customModelData;
    }
    public List<Enchantment> getEnchants() {
        return this.enchants;
    }
    public List<Integer> getLevels() {
        return this.levels;
    }
    public double getAttackSpeed() {
        return this.attackSpeed;
    }
    public double getDamage() {
        return this.damage;
    }
    public boolean getUnbreakable() {
        return this.unbreakable;
    }

    public double getPrice() {
        return this.price;
    }

}
