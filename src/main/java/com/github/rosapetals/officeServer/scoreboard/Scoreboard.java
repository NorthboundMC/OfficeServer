package com.github.rosapetals.officeServer.scoreboard;

import com.github.rosapetals.officeServer.OfficeServer;
import com.github.rosapetals.officeServer.listeners.DryerListener;
import com.github.rosapetals.officeServer.listeners.WasherListener;
import com.github.rosapetals.officeServer.scoreboard.mrmicky.FastBoard;
import com.github.rosapetals.officeServer.utils.CC;
import com.github.rosapetals.officeServer.utils.MoneyFormatter;
import com.github.rosapetals.officeServer.utils.VaultHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class Scoreboard implements Listener {

    private static OfficeServer plugin;

    @EventHandler
    public void applyScoreboard(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        UUID u = p.getUniqueId();

        createBoard(p);
        boardRunnable(u, p);

    }

    public static void initializePlugin() {
        plugin = OfficeServer.getInstance();
    }

    public static void createBoard(Player p) {

        UUID u = p.getUniqueId();

        int washer = WasherListener.getWasherStatus(u);
        int dryer = DryerListener.getDryerStatus(u);
        String washerStatus = washer == -1 || washer == 0 ? "Inactive" : washer == 1 ? "\uD83E\uDEE7 Washing" : washer == 2 ? "✓ Done" : "unknown";
        String dryerStatus = dryer == -1 || dryer == 0 ? "Inactive" : dryer == 1 ? "≋ Drying" : dryer == 2 ? "✓ Done" : "unknown";


        String text1 = CC.translate("&b&lLaundromat");
        String text3 = CC.translate("");
        String text4 = CC.translate("&b◎ Money: " + MoneyFormatter.put((long) VaultHandler.getBalance(p)));
        String text6 = CC.translate("&b▣ Dryer: " + dryerStatus);
        String text5 = CC.translate("&b⊚ Washer: " + washerStatus);
        String text7 = CC.translate("");
        String text12 = CC.translate("&blaundromat.minehut.gg");
        FastBoard board = new FastBoard(p);
        board.updateTitle(text1);
        plugin.getPlayerScoreBoards().put(u, board);
        board.updateLines(
                "" + text3,
                "" + text4,
                "" + text5,
                "" + text6,
                "" + text7,
                "" + text12);
        plugin.getPlayerScoreBoards().putIfAbsent(u, board);
    }

    public void updateBoard(FastBoard board, Player p) {

        UUID u = p.getUniqueId();

        int washer = WasherListener.getWasherStatus(u);
        int dryer = DryerListener.getDryerStatus(u);
        String washerStatus = washer == -1 || washer == 0 ? "Inactive" : washer == 1 ? "\uD83E\uDEE7 Washing" : washer == 2 ? "✓ Done" : "unknown";
        String dryerStatus = dryer == -1 || dryer == 0 ? "Inactive" : dryer == 1 ? "≋ Drying" : dryer == 2 ? "✓ Done" : "unknown";

        String text1 = CC.translate("&b&lLaundromat");
        String text3 = CC.translate("");
        String text4 = CC.translate("&b◎ Money: $" + MoneyFormatter.put((long) VaultHandler.getBalance(p)));
        String text6 = CC.translate("&b▣ Dryer: " + dryerStatus);
        String text5 = CC.translate("&b⊚ Washer: " + washerStatus);
        String text7 = CC.translate("");
        String text12 = CC.translate("&blaundromat.minehut.gg");
        board.updateTitle("" + text1);
        plugin.getPlayerScoreBoards().put(u, board);
        board.updateLines(
                "" + text3,
                "" + text4,
                "" + text5,
                "" + text6,
                "" + text7,
                "" + text12);
    }

    public void boardRunnable(UUID u, Player p){

        Bukkit.getScheduler().runTaskTimerAsynchronously(OfficeServer.getInstance(), () -> {
            if (!p.isOnline()) return;
            updateBoard(plugin.getPlayerScoreBoards().get(u), p);
        }, 50L, 50);
    }
}
