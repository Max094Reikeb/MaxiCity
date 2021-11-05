package net.reikeb.maxicity.misc;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.commands.HologramCommand;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CityUtils {

    private static final String packageName = Bukkit.getServer().getClass().getPackage().getName();
    public static final String version = packageName.substring(packageName.lastIndexOf(".") + 1);
    private static Class<?> craftPlayer, packet;
    private static Method getHandle, sendPacket;
    private static Field connection;

    /**
     * General method that modifies the balance of a player's team
     *
     * @param player      The player
     * @param config      The plugin's config
     * @param nbrEmeralds The number to change
     */
    public static void modifyTeamBalance(Player player, FileConfiguration config, int nbrEmeralds) {
        if (player.hasPermission("team.naboo")) {
            config.set("naboo_balance", config.getInt("naboo_balance") + nbrEmeralds);
        } else if (player.hasPermission("team.alderaan")) {
            config.set("alderaan_balance", config.getInt("alderaan_balance") + nbrEmeralds);
        } else if (player.hasPermission("team.tatooine")) {
            config.set("tatooine_balance", config.getInt("tatooine_balance") + nbrEmeralds);
        } else if (player.hasPermission("team.coruscant")) {
            config.set("coruscant_balance", config.getInt("coruscant_balance") + nbrEmeralds);
        }
    }

    /**
     * Try to cast a player to a CraftPlayer.
     *
     * @param player The bukkit player.
     * @return The Craft alternative or null if the class for CraftPlayer could
     * not be found.
     */
    public static Object castToCraft(Player player) {
        if (craftPlayer == null)
            try {
                craftPlayer = Class.forName("org.bukkit.craftbukkit." + CityUtils.version + ".entity.CraftPlayer");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        return craftPlayer.cast(player);
    }

    /**
     * Try to cast a player to a NMS player.
     *
     * @param player The bukkit player.
     * @return The NMS alternative or null if problems with reflection.
     */
    public static Object castToNMS(Player player) {
        Object craft = castToCraft(player);
        if (craft == null)
            return null;
        if (getHandle == null)
            try {
                getHandle = craftPlayer.getMethod("getHandle");
            } catch (Exception exc) {
                return null;
            }
        try {
            return getHandle.invoke(castToCraft(player));
        } catch (Exception exc) {
            return null;
        }
    }

    /**
     * Send a player a packet.
     *
     * @param inPacket The packet.
     * @param inPlayer The player.
     * @throws Exception if an exception occurred.
     */
    public static void sendPacket(Object inPacket, Player inPlayer) throws Exception {
        if (packet == null)
            packet = Class.forName("net.minecraft.server." + CityUtils.version + ".Packet");

        Object handle = castToNMS(inPlayer);
        if (handle == null)
            return;
        if (connection == null)
            connection = handle.getClass().getField("playerConnection");
        Object con = connection.get(handle);
        if (con == null)
            return;
        if (sendPacket == null)
            sendPacket = con.getClass().getMethod("sendPacket", packet);
        sendPacket.invoke(con, inPacket);
    }

    public static void setDeclaredField(Object obj, String fieldName, Object value) {
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(obj, value);
            f.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to repeat stuff every 5 minutes
     */
    public static void repeat(MaxiCity plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                String sWorld = plugin.getConfig().getString("main_world");
                if (sWorld == null) return;
                World world = plugin.getServer().getWorld(sWorld);
                if (world == null) return;
                world.setClearWeatherDuration(99999);
                HologramCommand hologram = new HologramCommand(plugin);
                hologram.regenerateHolo(plugin);
            }
        }.runTaskTimer(plugin, 20L * 5L * 60L, 20L * 5L * 60L);
    }
}
