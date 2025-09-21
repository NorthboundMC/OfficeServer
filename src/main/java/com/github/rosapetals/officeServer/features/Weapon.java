package com.github.rosapetals.officeServer.features;

import com.github.rosapetals.officeServer.utils.CC;
import com.github.rosapetals.officeServer.utils.ItemUtils;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public enum Weapon {
    BOLT(CC.translate("&lBolt"),
            new WeaponData(CC.translate("&lBolt"),
                    ItemUtils.CreateCustomItem(new ItemStack(Material.SNOWBALL), CC.translate("&lBolt"), CC.translate("A metal bolt\\Throw it at people"), false),
                    1,
                    null,
                    null,
                    20,
                    1,
                    false,
                    1
                    )),
    LINT_ROLLER(CC.translate("&lLint Roller"),
            new WeaponData(CC.translate("&lLint Roller"),
                    ItemUtils.CreateCustomItem(new ItemStack(Material.WOODEN_SWORD), CC.translate("&lLint Roller"), CC.translate("A flimsy lint roller\\Hit people with it"), false),
                    1,
                    List.of(Enchantment.DURABILITY),
                    List.of(2),
                    1.6,
                    4,
                    false,
                    10
            )),
    COAT_HANGER(CC.translate("&lCoat Hanger"),
            new WeaponData(CC.translate("&lCoat Hanger"),
                    ItemUtils.CreateCustomItem(new ItemStack(Material.IRON_PICKAXE), CC.translate("&lCoat Hanger"), CC.translate("A sharp coat hanger\\Hit people with it"), false),
                    1,
                    List.of(Enchantment.DURABILITY),
                    List.of(3),
                    1.2,
                    6,
                    false,
                    50
            )),
    CLOTHING_IRON(CC.translate("&lClothing Iron"),
            new WeaponData(CC.translate("&lClothing Iron"),
                    ItemUtils.CreateCustomItem(new ItemStack(Material.IRON_AXE), CC.translate("&lClothing Iron"), CC.translate("A heavy clothing iron\\Hit people with it"), false),
                    1,
                    List.of(Enchantment.DURABILITY),
                    List.of(2),
                    0.9,
                    9,
                    false,
                    200
            )),
    LINT_TRAY(CC.translate("&lLint Tray"),
            new WeaponData(CC.translate("&lLint Tray"),
                    ItemUtils.CreateCustomItem(new ItemStack(Material.NETHERITE_AXE), CC.translate("&lLint Tray"), CC.translate("A heavy lint tray \\Hit people while you are falling for massive damage"), false),
                    1,
                    null,
                    null,
                    0.6,
                    6,
                    false,
                    5000
            )),
    SCISSORS(CC.translate("&lScissors"),
            new WeaponData(CC.translate("&lScissors"),
                    ItemUtils.CreateCustomItem(new ItemStack(Material.DIAMOND_HOE), CC.translate("&lScissors"), CC.translate("Scissors\\Hit people with it\\Causes Bleeding\\&4&lDO NOT RUN WHILE HOLDING"), false),
                    1,
                    List.of(Enchantment.DURABILITY),
                    List.of(5),
                    4,
                    1,
                    false,
                    1800
            )),
    FORK(CC.translate("&lFork"),
            new WeaponData(CC.translate("&lFork"),
                    ItemUtils.CreateCustomItem(new ItemStack(Material.TRIDENT), CC.translate("&lFork"), CC.translate("A big fork\\Throw it at people"), false),
                    1,
                    List.of(Enchantment.DURABILITY, Enchantment.LOYALTY),
                    List.of(2, 3),
                    1.1,
                    9,
                    false,
                    2500
            )),
    BAKING_POWDER(CC.translate("&lBaking Powder"),
            new WeaponData(CC.translate("&lBaking Powder"),
                    ItemUtils.CreateCustomItem(new ItemStack(Material.EGG), CC.translate("&lBaking Powder"), CC.translate("Some baking powder\\Throw it to create a powerful splash that causes knockback"), false),
                    1,
                    null,
                    null,
                    20,
                    1,
                    false,
                    20
            )),
    CHOPPING_BOARD(CC.translate("&lChopping Board"),
            new WeaponData(CC.translate("&lChopping Board"),
                    ItemUtils.CreateCustomItem(new ItemStack(Material.SHIELD), CC.translate("&lChopping Board"), CC.translate("A sturdy chopping board\\Use it to protect yourself from being hit"), false),
                    1,
                    null,
                    null,
                    1.6,
                    4,
                    false,
                    3000
            ));



    @Getter
    private final String nameID;
    @Getter
    private final WeaponData weaponData;



    Weapon(String nameID, WeaponData weaponData) {
        this.nameID = nameID;
        this.weaponData = weaponData;

    }

    public static WeaponData fromName(String nameID) {
        for (Weapon weapon : values()) {
            if (CC.translate(weapon.nameID).equals(CC.translate(nameID))) {
                return weapon.weaponData;
            }
        }

        return null;
    }

    public WeaponData getWeaponData() {
        return weaponData;
    }
}
