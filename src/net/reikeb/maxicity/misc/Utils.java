package net.reikeb.maxicity.misc;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.commands.staff.HologramCommand;
import net.reikeb.maxicity.datas.Group;
import net.reikeb.maxicity.datas.managers.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static final String packageName = Bukkit.getServer().getClass().getPackage().getName();
    public static final String version = packageName.substring(packageName.lastIndexOf(".") + 1);

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

    /**
     * General method that modifies the balance of a player's team
     *
     * @param player      The player
     * @param config      The plugin's config
     * @param nbrEmeralds The number to change
     */
    public static void modifyTeamBalance(Player player, FileConfiguration config, int nbrEmeralds) {
        if (player.hasPermission("team.one")) {
            config.set("naboo_balance", config.getInt("naboo_balance") + nbrEmeralds);
        } else if (player.hasPermission("team.two")) {
            config.set("tatooine_balance", config.getInt("tatooine_balance") + nbrEmeralds);
        } else if (player.hasPermission("team.three")) {
            config.set("alderaan_balance", config.getInt("alderaan_balance") + nbrEmeralds);
        } else if (player.hasPermission("team.four")) {
            config.set("coruscant_balance", config.getInt("coruscant_balance") + nbrEmeralds);
        }
    }

    /**
     * Method that gives a Group to a player depending on the team.yml file
     *
     * @param plugin  The MaxiCity plugin class
     * @param manager The PlayerManager class
     */
    public static void setupTeams(MaxiCity plugin, PlayerManager manager) {
        File teamsFile = new File(plugin.getDataFolder(), "teams.yml");
        if (!teamsFile.exists())
            setupTeamFile(plugin);
        YamlConfiguration yaml = new YamlConfiguration();
        try {
            yaml.load(teamsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        if (yaml.getString("team_one") != null) {
            for (String str : yaml.getString("team_one").split("\\s*,\\s*")) {
                String playerName = str.substring(1, (str.length() - 1));
                if (Bukkit.getPlayer(playerName) != null) {
                    manager.setTeamGroup(Bukkit.getPlayer(playerName), Group.TEAM_ONE);
                }
            }
        }
        if (yaml.getString("team_two") != null) {
            for (String str : yaml.getString("team_two").split("\\s*,\\s*")) {
                String playerName = str.substring(1, (str.length() - 1));
                if (Bukkit.getPlayer(playerName) != null) {
                    manager.setTeamGroup(Bukkit.getPlayer(playerName), Group.TEAM_TWO);
                }
            }
        }
        if (yaml.getString("team_three") != null) {
            for (String str : yaml.getString("team_three").split("\\s*,\\s*")) {
                String playerName = str.substring(1, (str.length() - 1));
                if (Bukkit.getPlayer(playerName) != null) {
                    manager.setTeamGroup(Bukkit.getPlayer(playerName), Group.TEAM_THREE);
                }
            }
        }
        if (yaml.getString("team_four") != null) {
            for (String str : yaml.getString("team_four").split("\\s*,\\s*")) {
                String playerName = str.substring(1, (str.length() - 1));
                if (Bukkit.getPlayer(playerName) != null) {
                    manager.setTeamGroup(Bukkit.getPlayer(playerName), Group.TEAM_FOUR);
                }
            }
        }
        if (yaml.getString("moderators") != null) {
            for (String str : yaml.getString("moderators").split("\\s*,\\s*")) {
                String playerName = str.substring(1, (str.length() - 1));
                if (Bukkit.getPlayer(playerName) != null) {
                    manager.setTeamGroup(Bukkit.getPlayer(playerName), Group.MODERATOR);
                }
            }
        }
        if (yaml.getString("admin") != null) {
            for (String str : yaml.getString("admin").split("\\s*,\\s*")) {
                String playerName = str.substring(1, (str.length() - 1));
                if (Bukkit.getPlayer(playerName) != null) {
                    manager.setTeamGroup(Bukkit.getPlayer(playerName), Group.ADMIN);
                }
            }
        }
    }

    /**
     * Method that loads and sets up default team.yml file
     *
     * @param plugin The MaxiCity plugin class
     */
    public static void setupTeamFile(MaxiCity plugin) {
        File teamsFile = new File(plugin.getDataFolder(), "teams.yml");
        if (teamsFile.exists()) return;

        plugin.saveResource("teams.yml", false);

        YamlConfiguration yaml = new YamlConfiguration();
        // Section team one
        yaml.createSection("team_one");
        List<String> values = new ArrayList<String>();
        values.add("Goldorion");
        yaml.set("team_one", values);

        // Section team two
        yaml.createSection("team_two");
        List<String> values2 = new ArrayList<String>();
        values2.add("Klemen");
        yaml.set("team_two", values2);

        // Section team three
        yaml.createSection("team_three");
        List<String> values3 = new ArrayList<String>();
        values3.add("willpill");
        yaml.set("team_three", values3);

        // Section team four
        yaml.createSection("team_four");
        List<String> values4 = new ArrayList<String>();
        values4.add("TheFlow_");
        yaml.set("team_four", values4);

        // Section moderator
        yaml.createSection("moderators");
        List<String> valModo = new ArrayList<String>();
        valModo.add("Nat7123");
        yaml.set("moderators", valModo);

        // Section admin
        yaml.createSection("admin");
        List<String> valAdmin = new ArrayList<String>();
        valAdmin.add("Max094_Reikeb");
        yaml.set("admin", valAdmin);
        try {
            yaml.save("plugins/MaxiCity/teams.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
