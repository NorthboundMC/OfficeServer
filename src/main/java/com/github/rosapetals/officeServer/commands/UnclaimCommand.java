package com.github.rosapetals.officeServer.commands;

import com.github.rosapetals.officeServer.OfficeServer;
import com.github.rosapetals.officeServer.database.PlayerData;
import com.github.rosapetals.officeServer.features.LaundryUnitManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnclaimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("unclaim") && sender instanceof Player){

            Player player = (Player) sender;
            PlayerData playerData = OfficeServer.getInstance().getPlayerData().get(player.getUniqueId());

            LaundryUnitManager.unclaimUnit(playerData.getLaundryUnit().getWasherLocation());
            playerData.setLaundryUnit(null);

            return true;
        } else {
            return false;
        }

    }
}
