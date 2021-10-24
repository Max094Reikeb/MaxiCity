package net.reikeb.maxicity;

import net.reikeb.maxicity.commands.*;
import net.reikeb.maxicity.listeners.players.CommandChat;
import net.reikeb.maxicity.listeners.players.JoinQuit;
import net.reikeb.maxicity.managers.*;
import net.reikeb.maxicity.misc.CityUtils;
import net.reikeb.maxicity.misc.Version;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class MaxiCity extends JavaPlugin {

    public static MaxiCity instance;
    public static Version version;

    public FileConfiguration config;
    public CityUtils.DataManager data;

    public static MaxiCity getInstance() {
        return instance;
    }

    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void broadcast(Player p, String s) {
        p.getServer().broadcastMessage(chat(s));
    }

    @Override
    public void onDisable() {
        /**
         * Save managers
         */
        getLogger().info("Saving managers...");
        BalanceManager balanceManager = new BalanceManager(this);
        TeamChatManager teamChatManager = new TeamChatManager(this);
        SocialSpyManager socialSpyManager = new SocialSpyManager(this);
        PlayerTeamManager playerTeamManager = new PlayerTeamManager(this);
        NickManager nickManager = new NickManager(this);
        JoinManager joinManager = new JoinManager(this);
        MuteManager muteManager = new MuteManager(this);
        try {
            balanceManager.saveBalanceFile();
            teamChatManager.saveTeamChatFile();
            socialSpyManager.saveSocialSpyFile();
            playerTeamManager.saveTeamFile();
            nickManager.saveNickedPlayersFiles();
            nickManager.saveNicknamesFile();
            joinManager.saveJoinedPlayerFile();
            muteManager.saveMutedPlayersFile();
            muteManager.saveMutedReasonsFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Saving config files
         */
        getLogger().info("Saving config files...");
        this.saveConfig();

        getLogger().info("Disabling plugin...");
        getServer().getScheduler().cancelTasks(this);
    }

    @Override
    public void onEnable() {
        /**
         * Check if the version matches
         */
        version = Version.fromString(CityUtils.version);
        if (version == null) {
            getLogger().warning("This version of MaxiCity is not compatible with your version of Craftbukkit/Paper/Spigot (" + getServer().getVersion() + ")");
            getLogger().warning("Disabling MaxiCity...");
            setEnabled(false);
            return;
        } else
            getLogger().info("Using hooks for " + version);

        /**
         * Check if Holographic Displays is present
         */
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** Disabling MaxiCity! ***");
            this.setEnabled(false);
            return;
        }

        instance = this;

        /**
         * Load managers
         */
        getLogger().info("Loading managers...");
        BalanceManager balanceManager = new BalanceManager(this);
        TeamChatManager teamChatManager = new TeamChatManager(this);
        SocialSpyManager socialSpyManager = new SocialSpyManager(this);
        PlayerTeamManager playerTeamManager = new PlayerTeamManager(this);
        NickManager nickManager = new NickManager(this);
        JoinManager joinManager = new JoinManager(this);
        MuteManager muteManager = new MuteManager(this);
        try {
            balanceManager.loadBalanceFile();
            teamChatManager.loadTeamChatFile();
            socialSpyManager.loadSocialSpyFile();
            playerTeamManager.loadTeamFile();
            nickManager.loadNickedPlayersFile();
            nickManager.loadNicknamesFile();
            joinManager.loadJoinedPlayerFile();
            muteManager.loadMutedReasonsFile();
            muteManager.loadMutedPlayersFile();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        /**
         * Load config file
         */
        getLogger().info("Loading and setting up config files...");
        this.getConfig().set("t_head", "&b&lBienvenue sur la Cité des Étoiles !");
        this.getConfig().set("t_foot", "&1Twitter - @CiteDesEtoiles");
        this.getConfig().set("first_join_message", "&aa rejoint la Cité des Étoiles pour la première fois ! Bienvenue");
        this.getConfig().set("join_message", "&aBienvenue dans la Cité des Étoiles");
        this.getConfig().set("cite_coos", "&aLa Cité se trouve en 185 73 282");
        this.getConfig().set("chat_enabled", true);
        this.getConfig().set("empereur", "&4[Empereur] ");
        this.getConfig().set("grand_amiral", "&6[Grand Amiral] ");
        this.getConfig().set("naboo_file", "&2[Naboo] ");
        this.getConfig().set("tatooine_file", "&e[Tatooine] ");
        this.getConfig().set("alderaan_file", "&3[Alderaan] ");
        this.getConfig().set("coruscant_file", "&d[Coruscant] ");
        this.saveConfig();
        reloadConfig();

        // Setting up config
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.data = new CityUtils.DataManager(this);

        /**
         * Register all commands
         */
        getLogger().info("Registering commands...");
        // Override /plugins commands
        getCommand("plugins").setExecutor(new PluginsOverrideCommand(this));
        getCommand("pl").setExecutor(new PluginsOverrideCommand(this));
        getCommand("about").setExecutor(new PluginsOverrideCommand(this));
        getCommand("ver").setExecutor(new PluginsOverrideCommand(this));
        getCommand("version").setExecutor(new PluginsOverrideCommand(this));
        getCommand("bukkit:pl").setExecutor(new PluginsOverrideCommand(this));
        getCommand("bukkit:plugin").setExecutor(new PluginsOverrideCommand(this));
        getCommand("bukkit:ver").setExecutor(new PluginsOverrideCommand(this));
        getCommand("bukkit:version").setExecutor(new PluginsOverrideCommand(this));

        // Balance commands
        new BalanceCommand(this);

        // Other commands
        new TeamChatCommand(this);
        new SocialSpyCommand(this);
        new HologramCommand(this);

        /**
         * Register all listeners
         */
        getLogger().info("Registering listeners...");
        PluginManager p = getServer().getPluginManager();
        p.registerEvents(new JoinQuit(), this);
        p.registerEvents(new CommandChat(), this);

        /**
         * Register all managers
         */
        getLogger().info("Registering managers...");
        new BalanceManager(this);
        new TeamChatManager(this);
        new SocialSpyManager(this);
        new PlayerTeamManager(this);
        new NickManager(this);
        new JoinManager(this);
        new MuteManager(this);
    }
}
