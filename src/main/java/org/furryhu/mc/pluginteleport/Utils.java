package org.furryhu.mc.pluginteleport;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Utils {
    public static double cost1block = 0.08;

    public static int getDistance(Location a, Location b) {
        double dX, dY, dZ, dSum;
        dX = Math.abs(a.getX() - b.getX());
        dY = Math.abs(a.getY() - b.getY());
        dZ = Math.abs(a.getZ() - b.getZ());
        dSum = dX + dY + dZ;
        return (int) dSum;
    }

    public static int getCost(int distance, List<String> flags) {
        double retval = 0;
        Boolean diffworld = false, nonsurvival = false;
        if(flags != null){
            diffworld = flags.contains("diffworld");
            nonsurvival = flags.contains("nonsurvival");
        }
        
        retval = (distance * cost1block);

        if (diffworld) {
            retval = 500;
        }
        if (nonsurvival) {
            retval = 0;
        }

        /* other stuff based on flags... */
        return (int) retval;
    }


    public static int getTotalExperience(int level) {
        int xp = 0;

        if (level >= 0 && level <= 15) {
            xp = (int) Math.round(Math.pow(level, 2) + 6 * level);
        } else if (level > 15 && level <= 30) {
            xp = (int) Math.round((2.5 * Math.pow(level, 2) - 40.5 * level + 360));
        } else if (level > 30) {
            xp = (int) Math.round(((4.5 * Math.pow(level, 2) - 162.5 * level + 2220)));
        }
        return xp;
    }

    public static int getTotalExperience(Player player) {
        return Math.round(player.getExp() * player.getExpToLevel()) + getTotalExperience(player.getLevel());
    }

    public static void setTotalExperience(Player player, int amount) {
        int level = 0;
        int xp = 0;
        float a = 0;
        float b = 0;
        float c = -amount;

        if (amount > getTotalExperience(0) && amount <= getTotalExperience(15)) {
            a = 1;
            b = 6;
        } else if (amount > getTotalExperience(15) && amount <= getTotalExperience(30)) {
            a = 2.5f;
            b = -40.5f;
            c += 360;
        } else if (amount > getTotalExperience(30)) {
            a = 4.5f;
            b = -162.5f;
            c += 2220;
        }
        level = (int) Math.floor((-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a));
        xp = amount - getTotalExperience(level);
        player.setLevel(level);
        player.setExp(0);
        player.giveExp(xp);
    }
}
