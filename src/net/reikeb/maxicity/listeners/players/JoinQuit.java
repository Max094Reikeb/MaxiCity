package net.reikeb.maxicity.listeners.players;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.managers.*;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class JoinQuit implements Listener {

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        FileConfiguration config = MaxiCity.getInstance().getConfig();

        TeamChatManager teamChatManager = new TeamChatManager(MaxiCity.getInstance());
        teamChatManager.setPlayerTeamChat(player, false);
        SocialSpyManager socialSpyManager = new SocialSpyManager(MaxiCity.getInstance());
        socialSpyManager.setPlayerSocialSpy(player, false);

        player.setPlayerListHeaderFooter(config.getString("t_head"), config.getString("t_foot"));

        PlayerTeamManager playerTeamManager = new PlayerTeamManager(MaxiCity.getInstance());
        if (player.hasPermission("team.naboo")) {
            playerTeamManager.setPlayerTeam(player, config.getString("naboo_file"));
            // add player to nametag "naboo"
        } else if (player.hasPermission("team.alderaan")) {
            playerTeamManager.setPlayerTeam(player, config.getString("alderaan_file"));
            // add player to nametag "alderaan"
        } else if (player.hasPermission("team.tatooine")) {
            playerTeamManager.setPlayerTeam(player, config.getString("tatooine_file"));
            // add player to nametag "tatooine"
        } else if (player.hasPermission("coruscant_file")) {
            playerTeamManager.setPlayerTeam(player, config.getString("coruscant_file"));
            // add player to nametag "coruscant"
        }

        NickManager nickManager = new NickManager(MaxiCity.getInstance());
        if (nickManager.isPlayerNicked(player)) {
            player.setDisplayName(playerTeamManager.getPlayerTeam(player) + " " + nickManager.getPlayerNickname(player));
        } else {
            player.setDisplayName(playerTeamManager.getPlayerTeam(player) + " " + player.getDisplayName());
        }

        JoinManager joinManager = new JoinManager(MaxiCity.getInstance());
        if (!joinManager.hasPlayerJoined(player)) {
            // add player to {playerList::*}
            MaxiCity.broadcast(player, "&a" + player.getDisplayName() + " &a" + config.get("first_join_message") + " &a" + player.getDisplayName());
            player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 5));
            joinManager.setJoinedPlayer(player, true);
            // teleport player to world spawn
        }
        player.sendMessage(MaxiCity.chat("&a" + config.get("join_message") + " &a" + e.getPlayer()));
        e.setJoinMessage(MaxiCity.chat("&2[&c+&2] " + playerTeamManager.getPlayerTeam(e.getPlayer()) + " " + e.getPlayer()));
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent e) {
        PlayerTeamManager playerTeamManager = new PlayerTeamManager(MaxiCity.getInstance());
        e.setQuitMessage(MaxiCity.chat("&9[&4-&9] " + playerTeamManager.getPlayerTeam(e.getPlayer()) + " " + e.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPreJoin(AsyncPlayerPreLoginEvent e) {
        // Check for bans
    }

    @EventHandler
    private void onWorldChange(PlayerChangedWorldEvent e) {
        // If world changes
    }
}
