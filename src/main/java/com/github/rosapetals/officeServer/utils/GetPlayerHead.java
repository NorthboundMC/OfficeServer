package com.github.rosapetals.officeServer.utils;


import com.skullcreator.SkullCreator;
import org.bukkit.inventory.ItemStack;

public class GetPlayerHead {
    public static ItemStack getCustomHead(String code) {
        return SkullCreator.itemFromBase64(code);
    }
}
