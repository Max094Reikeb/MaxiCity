package net.reikeb.maxicity.listeners.players;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.managers.PlayerTeamManager;
import net.reikeb.maxicity.managers.SocialSpyManager;
import net.reikeb.maxicity.managers.TeamChatManager;

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
        e.setJoinMessage(null);
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
        // if {nick.%player%} is true:
        // set player tab name to "%{team.%player%.name}% %{nick.%player%.pseudo}%"
        // else
        // set player tab name to "%{team.%player%.name}% %player%"
        // if {join.%player%.first} is not set
        // {
        // add player to {playerList::*}
        // broadcast "&a%player% %{first_join_message}% %player%"
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 5));
        // set {join.%player%.first} to player's location
        // teleport player to {spawn.world.point}
        // }
        // message "%{join_message}% %player%"
        // set {join.%player%.first} to player's location
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent e) {
        e.setQuitMessage(null);
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
