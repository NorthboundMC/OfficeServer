package com.github.rosapetals.officeServer;


import com.github.rosapetals.officeServer.database.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("checkme") && sender instanceof Player){
            Player player = (Player) sender;
            PlayerData playerData = OfficeServer.getInstance().getPlayerData().get(player.getUniqueId());

            player.sendMessage("your washing machine level: " + playerData.getWashingMachineLevel() + " also " + playerData.getWasherInventory());
            player.sendMessage("also " + playerData.getWasherInventory());

            return true;
        } else {
            return false;
        }

    }
}