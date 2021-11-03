package net.reikeb.maxicity;

import net.reikeb.maxicity.commands.*;
import net.reikeb.maxicity.commands.message.MsgCommand;
import net.reikeb.maxicity.commands.message.ReplyCommand;
import net.reikeb.maxicity.commands.mute.MuteCommand;
import net.reikeb.maxicity.commands.mute.ReasonMuteCommand;
import net.reikeb.maxicity.commands.mute.UnmuteCommand;
import net.reikeb.maxicity.datas.DataManager;
import net.reikeb.maxicity.datas.PlayerManager;
import net.reikeb.maxicity.listeners.players.CommandChat;
import net.reikeb.maxicity.listeners.players.JoinQuit;
import net.reikeb.maxicity.misc.CityUtils;
import net.reikeb.maxicity.misc.Version;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MaxiCity extends JavaPlugin {

    public static MaxiCity instance;
    public static Version version;

    public FileConfiguration config;
    public DataManager data;
    public static PlayerManager playerManager;

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public static MaxiCity getInstance() {
        return instance;
    }

    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void broadcast(Player p, String s) {
        p.getServer().broadcastMessage(chat(s));
    }

    public static void broadcast(Server server, String s) {
        server.broadcastMessage(chat(s));
    }

    @Override
    public void onDisable() {

        /**
         * Saving config files
         */
        getLogger().info("Saving config files...");
        playerManager.saveHashMap();

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
         * Load config file
         */
        getLogger().info("Loading and setting up config files...");
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.getConfig().set("t_head", "§b§lSome random tab header");
            this.getConfig().set("t_foot", "§1Some random tab footer");
            this.getConfig().set("first_join_message", "&ajoined the city for the first time! Welcome");
            this.getConfig().set("join_message", "&aWelcome back to the city");
            this.getConfig().set("cite_coos", "&aThe city is located in 0 0 0");
            this.getConfig().set("admin", "&4[Admin] ");
            this.getConfig().set("moderator", "&6[Moderator] ");
            this.getConfig().set("first_team", "&2[Naboo] ");
            this.getConfig().set("second_team", "&e[Tatooine] ");
            this.getConfig().set("third_team", "&3[Alderaan] ");
            this.getConfig().set("fourth_team", "&d[Coruscant] ");
        }
        this.getConfig().set("chat_enabled", true);
        this.saveConfig();
        reloadConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        this.data = new DataManager(this);
        playerManager = new PlayerManager(this);
        playerManager.loadHashMap();

        /**
         * Register commands & listeners
         */
        getLogger().info("Registering commands & listeners...");
        registerCommand("plugins", new PluginsOverrideCommand(this));
        registerCommand("balance", new BalanceCommand(this));
        registerCommand("teamchat", new TeamChatCommand(this));
        registerCommand("socialspy", new SocialSpyCommand(this));
        registerCommand("holo", new HologramCommand(this));
        registerCommand("mute", new MuteCommand(this));
        registerCommand("unmute", new UnmuteCommand(this));
        registerCommand("mutereason", new ReasonMuteCommand(this));
        registerCommand("vanish", new VanishCommand(this));
        registerCommand("broadcast", new BroadcastCommand(this));
        registerCommand("chat", new ChatCommand(this));
        registerCommand("emeraldreset", new ResetCommand(this));
        registerCommand("msg", new MsgCommand(this));
        registerCommand("r", new ReplyCommand(this));

        registerListener(new JoinQuit());
        registerListener(new CommandChat());

        getLogger().info("- Created by Max094_Reikeb -");
    }

    private boolean registerCommand(String name, CommandExecutor executor) {
        try {
            PluginCommand command = getCommand(name);
            command.setExecutor(executor);

            getLogger().info("Registered command /" + name + " ✔️");
            return true;
        } catch (Exception e) {
            getLogger().severe("Can't register /" + name + " command!");
            return false;
        }
    }

    private void registerListener(Listener listener) {
        getLogger().info("Registered listener " + listener.getClass().getSimpleName() + " ✔️");
        getServer().getPluginManager().registerEvents(listener, getInstance());
    }
}
