package org.furryhu.mc.pluginteleport;

import org.bukkit.GameMode;
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
                } else if (args.length == 2) {
                    String tInfo = getTravelInfo(args[0], player);
                    if (tInfo != "") {
                        suggestions.add(tInfo);
                    }
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
                } else if (args.length == 2) {
                    String tInfo = getTravelInfo(args[0], player);
                    if (tInfo != "") {
                        suggestions.add(tInfo);
                    }
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

    private String getTravelInfo(String targetWPName, Player player) {
        Location wp;
        if (targetWPName.trim().length() == 0) {
            return "";
        }
        if (TeleportPlugin.homes.get(player.getUniqueId()).containsKey(targetWPName) == false) {
            if (TeleportPlugin.waypoints.get(player.getUniqueId()).containsKey(targetWPName) == false) {
                return "";
            } else {
                wp = TeleportPlugin.waypoints.get(player.getUniqueId()).get(targetWPName);
            }
        } else {
            wp = TeleportPlugin.homes.get(player.getUniqueId()).get(targetWPName);
        }

        String tplSameWorld = "This trip's distance is %,d block. This will cost you %,d XP.";
        String tplDiffWorld = "Your location is in a different world. This will cost you %,d XP.";
        Location start = player.getLocation();
        Location end = wp;
        String msg;
        List<String> flags = new ArrayList<String>();
        if (player.getGameMode() != GameMode.SURVIVAL) {
            flags.add("nonsurvival");
        }
        if (start.getWorld() != end.getWorld()) {
            flags.add("diffworld");
        }
        if (flags.contains("diffworld") == false) {
            int distance = Utils.getDistance(start, end);

            msg = String.format(
                    tplSameWorld,
                    distance,
                    Utils.getCost(distance, flags));
        } else {
            msg = String.format(
                    tplDiffWorld,
                    Utils.getCost(0, flags));
        }
        return msg;
    }

}
