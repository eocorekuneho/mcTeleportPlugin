package org.furryhu.mc.pluginteleport;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeleportTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (!(sender instanceof Player)) {
            return suggestions; // Only players can use these commands
        }

        Player player = (Player) sender;

        switch (command.getName().toLowerCase()) {
            case "gohome":
                if (args.length == 1) {
                    // Suggest home names
                    suggestions.addAll(getHomeNames(player));
                }
                break;
            case "sethome":
                if (args.length == 1) {
                    suggestions.addAll(getHomeNames(player));
                }
                break;
            case "delhome":
                if (args.length == 1) {
                    // Suggest home names
                    suggestions.addAll(getHomeNames(player));
                }
                break;
            case "setwp":
                if (args.length == 1) {
                    // Suggest teleport point names
                    suggestions.addAll(getTeleportPointNames(player));
                }
                break;
            case "delwp":
                if (args.length == 1) {
                    // Suggest teleport point names
                    suggestions.addAll(getTeleportPointNames(player));
                }
                break;
            case "gowp":
                if (args.length == 1) {
                    // Suggest teleport point names
                    suggestions.addAll(getTeleportPointNames(player));
                }
                break;
            // Add more cases for other commands as needed
        }

        return suggestions;
    }

    private List<String> getHomeNames(Player player) {
        // Retrieve home names for the player
        List<String> homeNames = new ArrayList<>();
        Map<String, Location> playerHomes = TeleportPlugin.homes.get(player.getUniqueId());
        if (playerHomes != null) {
            homeNames.addAll(playerHomes.keySet());
        }
        return homeNames;
    }

    private List<String> getTeleportPointNames(Player player) {
        // Retrieve teleport point names for the player
        List<String> tpNames = new ArrayList<>();
        Map<String, Location> playerTeleports = TeleportPlugin.waypoints.get(player.getUniqueId());
        if (playerTeleports != null) {
            tpNames.addAll(playerTeleports.keySet());
        }
        return tpNames;
    }
}
