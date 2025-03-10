package com.github.rosapetals.officeServer.utils;

import com.github.rosapetals.officeServer.OfficeServer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class VaultHandler {
        private static Economy economy;

        public static void initiate() {
            economy = OfficeServer.getInstance().getEconomy();
        }

        public static void addMoney(Player player, double amount) {
            economy.depositPlayer(player, amount);
        }

        public static void removeMoney(Player player, double amount){
            economy.withdrawPlayer(player, amount);
        }

        public static double getBalance(Player player){
            return economy.getBalance(player);
        }
    }

