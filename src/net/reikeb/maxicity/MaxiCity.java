package net.reikeb.maxicity;

import net.reikeb.maxicity.commands.regular.CityCommand;
import net.reikeb.maxicity.commands.regular.MoneyCommand;
import net.reikeb.maxicity.commands.regular.PluginsOverrideCommand;
import net.reikeb.maxicity.commands.regular.TeamChatCommand;
import net.reikeb.maxicity.commands.regular.message.MsgCommand;
import net.reikeb.maxicity.commands.regular.message.ReplyCommand;
import net.reikeb.maxicity.commands.staff.*;
import net.reikeb.maxicity.commands.staff.mute.MuteCommand;
import net.reikeb.maxicity.commands.staff.mute.ReasonMuteCommand;
import net.reikeb.maxicity.commands.staff.mute.UnmuteCommand;
import net.reikeb.maxicity.datas.managers.AreaManager;
import net.reikeb.maxicity.datas.managers.PlayerManager;
import net.reikeb.maxicity.listeners.players.Cancelled;
import net.reikeb.maxicity.listeners.players.CommandChat;
import net.reikeb.maxicity.listeners.players.Interact;
import net.reikeb.maxicity.listeners.players.JoinQuit;
import net.reikeb.maxicity.misc.Utils;
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

public class MaxiCity extends JavaPlugin {

    public static MaxiCity instance;
    public static Version version;

    public FileConfiguration config;
    public static PlayerManager playerManager;
    public static AreaManager areaManager;

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public AreaManager getAreaManager() {
        return areaManager;
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
        areaManager.saveHashMap();

        /**
         * Disabling custom permissions
         */
        getLogger().info("Disabling permissions...");
        playerManager.disablePermissions();

        getLogger().info("Disabling plugin...");
        getServer().getScheduler().cancelTasks(this);
    }

    @Override
    public void onEnable() {
        /**
         * Check if the version matches
         */
        version = Version.fromString(Utils.version);
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
        getLogger().info("Loading and setting up config files and permissions...");
        Utils.setupConfigFile(this, this.getConfig());
        Utils.setupTeamFile(this);
        Utils.repeat(this);
        this.getConfig().set("chat_enabled", true);
        this.saveConfig();
        reloadConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        playerManager = new PlayerManager(this);
        areaManager = new AreaManager(this);
        playerManager.loadHashMap();
        areaManager.loadHashMap();

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
        registerCommand("city", new CityCommand(this));
        registerCommand("setcity", new SpawnCommand(this));
        registerCommand("money", new MoneyCommand(this));
        registerCommand("stopholo", new StopHoloCommand(this));
        registerCommand("group", new GroupCommand(this));
        registerCommand("area", new AreaCommand(this));

        registerListener(new JoinQuit(this));
        registerListener(new CommandChat());
        registerListener(new Interact());
        registerListener(new Cancelled());

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
