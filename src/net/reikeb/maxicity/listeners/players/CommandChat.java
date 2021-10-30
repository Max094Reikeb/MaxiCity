package net.reikeb.maxicity.listeners.players;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.misc.Maps;

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
        Maps manager = new Maps(MaxiCity.getInstance());

        if (config.getBoolean("chat_enabled")) {
            event.setCancelled(true);
            if (event.getMessage().contains("<3")) event.getMessage().replace("<3", "&4❤&f");
            if (event.getMessage().contains(":)")) event.getMessage().replace(":)", "☺");
            if (event.getMessage().contains(":(")) event.getMessage().replace(":(", "☹");
            if (event.getMessage().contains("harrypotter")) event.getMessage().replace("harrypotter", "ϟ");
            if (event.getMessage().contains("=>")) event.getMessage().replace("=>", "⇨");
            if (event.getMessage().contains("->")) event.getMessage().replace("->", "→");
            if (event.getMessage().contains("<-")) event.getMessage().replace("<-", "←");
            if (event.getMessage().contains("citedesetoiles"))
                event.getMessage().replace("citedesetoiles", MaxiCity.chat("&2Cité &ades &2Étoiles"));

            if (!manager.isPlayerMuted(player)) {
                if (manager.getPlayerTeamChat(player)) {
                    for (Player p : player.getServer().getOnlinePlayers()) {

                        if (player.hasPermission("team.naboo") && p.hasPermission("team.naboo")) {
                            if (manager.isPlayerNicked(player)) {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + manager.getPlayerTeam(player) + " &r"
                                        + manager.getPlayerNickname(player) + ": " + event.getMessage()));
                            } else {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + manager.getPlayerTeam(player) + " &r"
                                        + player.getDisplayName() + ": " + event.getMessage()));
                            }
                        }

                        if (player.hasPermission("team.tatooine") && p.hasPermission("team.tatooine")) {
                            if (manager.isPlayerNicked(player)) {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + manager.getPlayerTeam(player) + " &r"
                                        + manager.getPlayerNickname(player) + ": " + event.getMessage()));
                            } else {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + manager.getPlayerTeam(player) + " &r"
                                        + player.getDisplayName() + ": " + event.getMessage()));
                            }
                        }

                        if (player.hasPermission("team.alderaan") && p.hasPermission("team.alderaan")) {
                            if (manager.isPlayerNicked(player)) {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + manager.getPlayerTeam(player) + " &r"
                                        + manager.getPlayerNickname(player) + ": " + event.getMessage()));
                            } else {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + manager.getPlayerTeam(player) + " &r"
                                        + player.getDisplayName() + ": " + event.getMessage()));
                            }
                        }

                        if (player.hasPermission("team.coruscant") && p.hasPermission("team.coruscant")) {
                            if (manager.isPlayerNicked(player)) {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + manager.getPlayerTeam(player) + " &r"
                                        + manager.getPlayerNickname(player) + ": " + event.getMessage()));
                            } else {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] " + manager.getPlayerTeam(player) + " &r"
                                        + player.getDisplayName() + ": " + event.getMessage()));
                            }
                        }
                    }
                } else {
                    if (manager.isPlayerNicked(player)) {
                        MaxiCity.broadcast(player, MaxiCity.chat(manager.getPlayerTeam(player) + " &r" +
                                manager.getPlayerNickname(player) + ": " + event.getMessage()));
                    } else {
                        MaxiCity.broadcast(player, MaxiCity.chat(manager.getPlayerTeam(player) + " &r" +
                                player.getDisplayName() + ": " + event.getMessage()));
                    }
                }
            } else {
                player.sendMessage(MaxiCity.chat("&cYou have been muted for " + manager.getMutedPlayerReason(player)));
            }
        } else {
            if (player.hasPermission("ee.chatalways")) {
                if (!manager.isPlayerMuted(player)) {
                    event.setCancelled(true);
                    if (manager.isPlayerNicked(player)) {
                        MaxiCity.broadcast(player, MaxiCity.chat(manager.getPlayerTeam(player) + " &r" +
                                manager.getPlayerNickname(player) + ": " + event.getMessage()));
                    } else {
                        MaxiCity.broadcast(player, MaxiCity.chat(manager.getPlayerTeam(player) + " &r" +
                                player.getDisplayName() + ": " + event.getMessage()));
                    }
                } else {
                    event.setCancelled(true);
                    player.sendMessage(MaxiCity.chat("&cYou have been muted for " + manager.getMutedPlayerReason(player)));
                }
            } else {
                event.setCancelled(true);
            }
        }
    }
}
