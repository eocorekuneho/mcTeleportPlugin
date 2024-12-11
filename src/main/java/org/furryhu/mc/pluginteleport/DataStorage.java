package org.furryhu.mc.pluginteleport;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataStorage {
    private final TeleportPlugin plugin;
    private File dataFile;
    private FileConfiguration dataConfig;

    public DataStorage(TeleportPlugin plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "data.yml");
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void saveData() {
        try {
            for (UUID playerUUID : TeleportPlugin.homes.keySet()) {
                // Player player = Bukkit.getPlayer(playerUUID);
                // String playerName = player.getName();
                Map<String, Location> homes = TeleportPlugin.homes.get(playerUUID);
                Map<String, Location> waypoints = TeleportPlugin.waypoints.get(playerUUID);

                // Save homes
                try {
                    for (Map.Entry<String, Location> entry : homes.entrySet()) {
                        String homeName = entry.getKey();
                        Location location = entry.getValue();
                        dataConfig.set(playerUUID + ".homes." + homeName, location);
                    }
                } catch (Exception ex) {
                    plugin.getLogger().warning("Could not save homes: " + ex.getMessage());
                }

                // Save waypoints
                try {
                    for (Map.Entry<String, Location> entry : waypoints.entrySet()) {
                        String waypointName = entry.getKey();
                        Location location = entry.getValue();
                        dataConfig.set(playerUUID + ".waypoints." + waypointName, location);
                    }
                } catch (Exception ex) {
                    plugin.getLogger().warning("Could not save waypoints: " + ex.getMessage());
                }

            }
            dataConfig.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save data: " + e.getMessage());
        }
    }

    public void loadData() {
        if (!dataFile.exists()) {
            return; // No data file to load
        }

        for (String playerUUID : dataConfig.getKeys(false)) {
            Map<String, Location> homes = new HashMap<>();
            Map<String, Location> waypoints = new HashMap<>();

            // Load homes
            if (dataConfig.contains(playerUUID + ".homes")) {
                for (String homeName : dataConfig.getConfigurationSection(playerUUID + ".homes").getKeys(false)) {
                    Location location = dataConfig.getLocation(playerUUID + ".homes." + homeName);
                    homes.put(homeName, location);
                }
            }

            // Load waypoints
            if (dataConfig.contains(playerUUID + ".waypoints")) {
                for (String waypointName : dataConfig.getConfigurationSection(playerUUID + ".waypoints")
                        .getKeys(false)) {
                    Location location = dataConfig.getLocation(playerUUID + ".waypoints." + waypointName);
                    waypoints.put(waypointName, location);
                }
            }

            // Store loaded data
            TeleportPlugin.homes.put(UUID.fromString(playerUUID), homes);
            TeleportPlugin.waypoints.put(UUID.fromString(playerUUID), waypoints);
        }
    }
}
