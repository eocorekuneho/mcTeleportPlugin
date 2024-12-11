package org.furryhu.mc.pluginteleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        switch (command.getName().toLowerCase()) {
            case "gohome":
                return handleGoHome(player.getUniqueId(), args);
            case "sethome":
                return handleSetHome(player.getUniqueId(), args);
            case "listhome":
                return handleListHome(player.getUniqueId());
            case "delhome":
                return handleDelHome(player.getUniqueId(), args);
            case "setwp":
                return handleSetWP(player.getUniqueId(), args);
            case "listwp":
                return handleListWP(player.getUniqueId());
            case "delwp":
                return handleDelWP(player.getUniqueId(), args);
            case "gowp":
                return handleGoWP(player.getUniqueId(), args);
            default:
                return false;
        }
    }

    private boolean handleGoHome(UUID playerUUID, String[] args) {
        Player player = Bukkit.getPlayer(playerUUID);
        Map<String, Location> playerHomes = TeleportPlugin.homes.getOrDefault(player.getUniqueId(), new HashMap<>());
        if (args.length == 0) {
            // Go to the first home if no name is provided
            if (!playerHomes.isEmpty()) {
                Location homeLocation = playerHomes.values().iterator().next();
                player.teleport(homeLocation);
                player.sendMessage("Teleported to your first Home point.");
            } else {
                player.sendMessage("You have no Home points set.");
            }
        } else {
            String homeName = args[0];
            Location homeLocation = playerHomes.get(homeName);
            if (homeLocation != null) {
                player.teleport(homeLocation);
                player.sendMessage("Teleported to Home point: " + homeName);
            } else {
                player.sendMessage("Home point not found: " + homeName);
            }
        }
        return true;
    }

    private boolean handleSetHome(UUID playerUUID, String[] args) {
        Player player = Bukkit.getPlayer(playerUUID);
        String homeName;
        if (args.length == 0) {
            homeName = "default";
        } else {
            homeName = args[0];
        }

        Location location = player.getLocation();
        TeleportPlugin.homes.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(homeName, location);
        player.sendMessage("Home point set: " + homeName);
        return true;
    }

    private boolean handleListHome(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        Map<String, Location> playerHomes = TeleportPlugin.homes.get(player.getUniqueId());
        if (playerHomes == null || playerHomes.isEmpty()) {
            player.sendMessage("You have no Home points set.");
        } else {
            player.sendMessage("Your Home points:");
            playerHomes.keySet().forEach(homeName -> player.sendMessage("- " + homeName));
        }
        return true;
    }

    private boolean handleDelHome(UUID playerUUID, String[] args) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (args.length == 0) {
            player.sendMessage("Please provide a name for the Home point to delete.");
            return true;
        }

        String homeName = args[0];
        Map<String, Location> playerHomes = TeleportPlugin.homes.get(player.getUniqueId());
        if (playerHomes != null && playerHomes.remove(homeName) != null) {
            player.sendMessage("Home point deleted: " + homeName);
        } else {
            player.sendMessage("Home point not found: " + homeName);
        }
        return true;
    }

    private boolean handleSetWP(UUID playerUUID, String[] args) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (args.length == 0) {
            player.sendMessage("Please provide a name for your Waypoint.");
            return true;
        }

        String tpName = args[0];
        Location location = player.getLocation();
        TeleportPlugin.waypoints.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(tpName, location);
        player.sendMessage("Waypoint set: " + tpName);
        
        return true;
    }

    private boolean handleListWP(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        Map<String, Location> playerTeleports = TeleportPlugin.waypoints.get(player.getUniqueId());
        if (playerTeleports == null || playerTeleports.isEmpty()) {
            player.sendMessage("You have no Waypoints set.");
        } else {
            player.sendMessage("Your Waypoints:");
            playerTeleports.keySet().forEach(teleportName -> player.sendMessage("- " + teleportName));
        }
        return true;
    }

    private boolean handleDelWP(UUID playerUUID, String[] args) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (args.length == 0) {
            player.sendMessage("Please provide a name for the Waypoint to delete.");
            return true;
        }

        String tpName = args[0];
        Map<String, Location> playerTPs = TeleportPlugin.waypoints.get(player.getUniqueId());
        if (playerTPs != null && playerTPs.remove(tpName) != null) {
            player.sendMessage("Waypoint deleted: " + tpName);
        } else {
            player.sendMessage("Waypoint not found: " + tpName);
        }
        return true;
    }

    private boolean handleGoWP(UUID playerUUID, String[] args) {
        Player player = Bukkit.getPlayer(playerUUID);
        Map<String, Location> playerTeleports = TeleportPlugin.waypoints.getOrDefault(player.getUniqueId(), new HashMap<>());
        if (args.length == 0) {
            String lastTPName = TeleportPlugin.lastUsedTeleport.get(player.getUniqueId());
            if(lastTPName != null && playerTeleports.containsKey(lastTPName)){
                player.teleport(playerTeleports.get(lastTPName));
                player.sendMessage("Teleported to your last Waypoint: " + lastTPName);
                return true;
            } else {
                player.sendMessage("Please provide a Waypoint name.");
                return true;
            }
        }

        String tpName = args[0];
        Location tpLocation = playerTeleports.get(tpName);
        if (tpLocation != null) {
            player.teleport(tpLocation);
            player.sendMessage("Teleported to Waypoint: " + tpName);
            // Update last used teleport point
            TeleportPlugin.lastUsedTeleport.put(player.getUniqueId(), tpName);
        } else {
            player.sendMessage("Waypoint not found: " + tpName);
        }

        return true;
    }
}
