package org.furryhu.mc.pluginteleport;

import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TeleportPlugin extends JavaPlugin implements Listener {
    public static final Map<UUID, Map<String, Location>> homes = new HashMap<>();
    public static final Map<UUID, Map<String, Location>> waypoints = new HashMap<>();
    public static final Map<UUID, String> lastUsedTeleport = new HashMap<>();

    //public static final Map<UUID, World> worlds = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("TeleportPlugin has been enabled!");

        // Register the command executor for teleport commands
        TeleportCommand teleportCommand = new TeleportCommand();
        getCommand("gohome").setExecutor(teleportCommand);
        getCommand("sethome").setExecutor(teleportCommand);
        getCommand("listhome").setExecutor(teleportCommand);
        getCommand("delhome").setExecutor(teleportCommand);
        getCommand("setwp").setExecutor(teleportCommand);
        getCommand("listwp").setExecutor(teleportCommand);
        getCommand("delwp").setExecutor(teleportCommand);
        getCommand("gowp").setExecutor(teleportCommand);

        // Register the tab completer
        TeleportTabCompleter tabCompleter = new TeleportTabCompleter();
        getCommand("gohome").setTabCompleter(tabCompleter);
        getCommand("sethome").setTabCompleter(tabCompleter);
        getCommand("delhome").setTabCompleter(tabCompleter);
        getCommand("setwp").setTabCompleter(tabCompleter);
        getCommand("delwp").setTabCompleter(tabCompleter);
        getCommand("gowp").setTabCompleter(tabCompleter);

        getServer().getPluginManager().registerEvents(this, this);
        DataStorage storage = new DataStorage(this);
        if (storage != null && !Bukkit.getWorlds().isEmpty()) {
            storage.loadData();
            getLogger().info("TeleportPlugin data has been loaded.");
        } else {
            getLogger().info("TeleportPlugin DataStorage is null.");
            return;
        }

    }

    @Override
    public void onDisable() {
        DataStorage storage = new DataStorage(this);
        if (storage != null) {
            storage.saveData();
            getLogger().info("TeleportPlugin data has been saved.");
        }
        getLogger().info("TeleportPlugin has been disabled!");
    }

    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        DataStorage storage = new DataStorage(this);
        // Code to execute when a player leaves
        if (storage != null && !Bukkit.getWorlds().isEmpty()) {
            storage.saveData();
            getLogger().info("TeleportPlugin data has been saved.");
        } else {
            getLogger().info("TeleportPlugin DataStorage is null.");
            return;
        }
    }

    /*
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        DataStorage storage = new DataStorage(this);
        // Code to execute when a player joins
        if (storage != null && !Bukkit.getWorlds().isEmpty()) {
            storage.saveData();
            storage.loadData();
        } else {
            getLogger().info("TeleportPlugin DataStorage is null.");
            return;
        }
        getLogger().info("TeleportPlugin data has been loaded.");

    }
    */

}