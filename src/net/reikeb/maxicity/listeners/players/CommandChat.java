package net.reikeb.maxicity.listeners.players;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.managers.MuteManager;
import net.reikeb.maxicity.managers.NickManager;
import net.reikeb.maxicity.managers.PlayerTeamManager;
import net.reikeb.maxicity.managers.TeamChatManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class CommandChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = MaxiCity.getInstance().getConfig();

        PlayerTeamManager playerTeamManager = new PlayerTeamManager(MaxiCity.getInstance());
        TeamChatManager teamChatManager = new TeamChatManager(MaxiCity.getInstance());
        NickManager nickManager = new NickManager(MaxiCity.getInstance());
        MuteManager muteManager = new MuteManager(MaxiCity.getInstance());

        if (config.getBoolean("chat_enabled")) {
            event.setCancelled(true);
            if (event.getMessage().contains("<3")) event.getMessage().replace("<3", "&4❤&f");
            if (event.getMessage().contains(":)")) event.getMessage().replace(":)", "☺");
            if (event.getMessage().contains(":(")) event.getMessage().replace(":(", "☹");
            if (event.getMessage().contains("harrypotter")) event.getMessage().replace("harrypotter", "ϟ");
            if (event.getMessage().contains("=>")) event.getMessage().replace("=>", "⇨");
            if (event.getMessage().contains("->")) event.getMessage().replace("->", "→");
            if (event.getMessage().contains("<-")) event.getMessage().replace("<-", "←");
            if (event.getMessage().contains("citedesetoiles")) event.getMessage().replace("citedesetoiles", MaxiCity.chat("&2Cité &ades &2Étoiles"));

            if (!muteManager.isPlayerMuted(player)) {
                if (teamChatManager.getPlayerTeamChat(player)) {
                    for (Player p : player.getServer().getOnlinePlayers()) {

                        if (player.hasPermission("team.naboo") && p.hasPermission("team.naboo")) {
                            if (nickManager.isPlayerNicked(player)) {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + playerTeamManager.getPlayerTeam(player) + " &r"
                                        + nickManager.getPlayerNickname(player) + ": " + event.getMessage()));
                            } else {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + playerTeamManager.getPlayerTeam(player) + " &r"
                                        + player.getDisplayName() + ": " + event.getMessage()));
                            }
                        }

                        if (player.hasPermission("team.tatooine") && p.hasPermission("team.tatooine")) {
                            if (nickManager.isPlayerNicked(player)) {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + playerTeamManager.getPlayerTeam(player) + " &r"
                                        + nickManager.getPlayerNickname(player) + ": " + event.getMessage()));
                            } else {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + playerTeamManager.getPlayerTeam(player) + " &r"
                                        + player.getDisplayName() + ": " + event.getMessage()));
                            }
                        }

                        if (player.hasPermission("team.alderaan") && p.hasPermission("team.alderaan")) {
                            if (nickManager.isPlayerNicked(player)) {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + playerTeamManager.getPlayerTeam(player) + " &r"
                                        + nickManager.getPlayerNickname(player) + ": " + event.getMessage()));
                            } else {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + playerTeamManager.getPlayerTeam(player) + " &r"
                                        + player.getDisplayName() + ": " + event.getMessage()));
                            }
                        }

                        if (player.hasPermission("team.coruscant") && p.hasPermission("team.coruscant")) {
                            if (nickManager.isPlayerNicked(player)) {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + playerTeamManager.getPlayerTeam(player) + " &r"
                                        + nickManager.getPlayerNickname(player) + ": " + event.getMessage()));
                            } else {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + playerTeamManager.getPlayerTeam(player) + " &r"
                                        + player.getDisplayName() + ": " + event.getMessage()));
                            }
                        }
                    }
                } else {
                    if (nickManager.isPlayerNicked(player)) {
                        MaxiCity.broadcast(player, MaxiCity.chat(playerTeamManager.getPlayerTeam(player) + " &r" +
                                nickManager.getPlayerNickname(player) + ": " + event.getMessage()));
                    } else {
                        MaxiCity.broadcast(player, MaxiCity.chat(playerTeamManager.getPlayerTeam(player) + " &r" +
                                player.getDisplayName() + ": " + event.getMessage()));
                    }
                }
            } else {
                player.sendMessage(MaxiCity.chat("&cYou have been muted for " + muteManager.getMutedPlayerReason(player)));
            }
        } else {
            if (player.hasPermission("ee.chatalways")) {
                if (!muteManager.isPlayerMuted(player)) {
                    event.setCancelled(true);
                    if (nickManager.isPlayerNicked(player)) {
                        MaxiCity.broadcast(player, MaxiCity.chat(playerTeamManager.getPlayerTeam(player) + " &r" +
                                nickManager.getPlayerNickname(player) + ": " + event.getMessage()));
                    } else {
                        MaxiCity.broadcast(player, MaxiCity.chat(playerTeamManager.getPlayerTeam(player) + " &r" +
                                player.getDisplayName() + ": " + event.getMessage()));
                    }
                } else {
                    event.setCancelled(true);
                    player.sendMessage(MaxiCity.chat("&cYou have been muted for " + muteManager.getMutedPlayerReason(player)));
                }
            } else {
                event.setCancelled(true);
            }
        }
    }
}
