package net.reikeb.maxicity.listeners.players;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.datas.managers.PlayerManager;
import net.reikeb.maxicity.misc.Utils;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class JoinQuit implements Listener {

    private final MaxiCity plugin;

    public JoinQuit(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        FileConfiguration config = this.plugin.getConfig();
        PlayerManager manager = this.plugin.getPlayerManager();

        if (!manager.hasPlayerJoined(player)) manager.setNewPlayer(player);
        Utils.setupTeams(plugin, manager);

        manager.setPlayerVanish(player, false);
        manager.setPlayerTeamChat(player, false);
        manager.setPlayerSocialSpy(player, false);

        manager.removePermissions(player);
        manager.loadPermissions(player);

        player.setPlayerListHeaderFooter(config.getString("t_head"), config.getString("t_foot"));
        setupPrefixes(player, config, manager);

        if (!manager.hasPlayerJoined(player)) {
            MaxiCity.broadcast(player, "&a" + player.getDisplayName() + " &a" + config.get("first_join_message") + " &a" + player.getDisplayName());
            player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 5));
            player.setPlayerListName(manager.getPlayerTeamList(player) + player.getName());
            manager.setJoinedPlayer(player, true);
            player.teleport(config.getLocation("cite_coos"));
        } else {
            player.sendMessage(MaxiCity.chat("&a" + config.get("join_message") + " &a" + player.getDisplayName()));
            if (manager.isPlayerNicked(player)) {
                player.setDisplayName(manager.getPlayerTeam(player) + manager.getPlayerNickname(player));
                player.setPlayerListName(manager.getPlayerTeam(player) + manager.getPlayerNickname(player));
            } else {
                player.setDisplayName(manager.getPlayerTeam(player) + player.getName());
                player.setPlayerListName(manager.getPlayerTeam(player) + player.getName());
            }
        }

        e.setJoinMessage(MaxiCity.chat("&2[&c+&2] " + player.getDisplayName()));
    }

    private void setupPrefixes(Player player, FileConfiguration config, PlayerManager manager) {
        if (player.hasPermission("team.admin")) {
            manager.setPlayerTeam(player, config.getString("admin"));
            manager.setPlayerTeamList(player, config.getString("admin_list"));
        } else if (player.hasPermission("team.moderator")) {
            manager.setPlayerTeam(player, config.getString("moderator"));
            manager.setPlayerTeamList(player, config.getString("moderator_list"));
        } else if (player.hasPermission("team.one")) {
            manager.setPlayerTeam(player, config.getString("first_team"));
            manager.setPlayerTeamList(player, config.getString("first_team_list"));
        } else if (player.hasPermission("team.two")) {
            manager.setPlayerTeam(player, config.getString("second_team"));
            manager.setPlayerTeamList(player, config.getString("second_team_list"));
        } else if (player.hasPermission("team.three")) {
            manager.setPlayerTeam(player, config.getString("third_team"));
            manager.setPlayerTeamList(player, config.getString("third_team_list"));
        } else if (player.hasPermission("team.four")) {
            manager.setPlayerTeam(player, config.getString("fourth_team"));
            manager.setPlayerTeamList(player, config.getString("fourth_team_list"));
        }
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent e) {
        this.plugin.getPlayerManager().removePermissions(e.getPlayer());
        e.setQuitMessage(MaxiCity.chat("&9[&4-&9] " + e.getPlayer().getDisplayName()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPreJoin(AsyncPlayerPreLoginEvent e) {
        // Check for bans
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMonitorLogin(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            this.plugin.getPlayerManager().removePermissions(event.getPlayer());
        }
    }

    @EventHandler
    private void onWorldChange(PlayerChangedWorldEvent e) {
        // If world changes
    }
}
