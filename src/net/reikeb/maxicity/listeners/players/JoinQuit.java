package net.reikeb.maxicity.listeners.players;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.misc.Maps;

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
        Maps manager = new Maps(MaxiCity.getInstance());

        manager.setPlayerTeamChat(player, false);
        manager.setPlayerSocialSpy(player, false);

        player.setPlayerListHeaderFooter(config.getString("t_head"), config.getString("t_foot"));

        if (player.hasPermission("team.naboo")) {
            manager.setPlayerTeam(player, config.getString("first_team"));
            // add player to nametag "naboo"
        } else if (player.hasPermission("team.tatooine")) {
            manager.setPlayerTeam(player, config.getString("second_team"));
            // add player to nametag "tatooine"
        } else if (player.hasPermission("team.alderaan")) {
            manager.setPlayerTeam(player, config.getString("third_team"));
            // add player to nametag "alderaan"
        } else if (player.hasPermission("team.coruscant")) {
            manager.setPlayerTeam(player, config.getString("fourth_team"));
            // add player to nametag "coruscant"
        }

        if (!manager.hasPlayerJoined(player)) {
            // add player to {playerList::*}
            MaxiCity.broadcast(player, "&a" + player.getDisplayName() + " &a" + config.get("first_join_message") + " &a" + player.getDisplayName());
            player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 5));
            manager.setJoinedPlayer(player, true);
            // teleport player to world spawn
        } else {
            player.sendMessage(MaxiCity.chat("&a" + config.get("join_message") + " &a" + player.getDisplayName()));
            if (manager.isPlayerNicked(player)) {
                player.setDisplayName(manager.getPlayerTeam(player) + manager.getPlayerNickname(player));
            } else {
                player.setDisplayName(manager.getPlayerTeam(player) + player.getDisplayName());
            }
        }

        e.setJoinMessage(MaxiCity.chat("&2[&c+&2] " + player.getDisplayName()));
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent e) {
        Maps manager = new Maps(MaxiCity.getInstance());
        e.setQuitMessage(MaxiCity.chat("&9[&4-&9] " + e.getPlayer().getDisplayName()));
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
