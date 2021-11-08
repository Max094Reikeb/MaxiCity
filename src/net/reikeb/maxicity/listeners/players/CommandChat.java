package net.reikeb.maxicity.listeners.players;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.datas.managers.PlayerManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = MaxiCity.getInstance().getConfig();
        PlayerManager manager = MaxiCity.getInstance().getPlayerManager();

        event.setCancelled(true);

        if (config.getBoolean("chat_enabled")) {
            if (!manager.isPlayerMuted(player)) {
                if (manager.getPlayerTeamChat(player)) {
                    for (Player p : player.getServer().getOnlinePlayers()) {

                        if (player.hasPermission("team.one") && p.hasPermission("team.one")) {
                            if (manager.isPlayerNicked(player)) {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] &r" + manager.getPlayerNickname(player) + ": " + event.getMessage()));
                            } else {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] &r" + player.getDisplayName() + ": " + event.getMessage()));
                            }
                        }

                        if (player.hasPermission("team.two") && p.hasPermission("team.two")) {
                            if (manager.isPlayerNicked(player)) {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] &r" + manager.getPlayerNickname(player) + ": " + event.getMessage()));
                            } else {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] &r" + player.getDisplayName() + ": " + event.getMessage()));
                            }
                        }

                        if (player.hasPermission("team.three") && p.hasPermission("team.three")) {
                            if (manager.isPlayerNicked(player)) {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] &r" + manager.getPlayerNickname(player) + ": " + event.getMessage()));
                            } else {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] &r" + player.getDisplayName() + ": " + event.getMessage()));
                            }
                        }

                        if (player.hasPermission("team.four") && p.hasPermission("team.four")) {
                            if (manager.isPlayerNicked(player)) {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] &r" + manager.getPlayerNickname(player) + ": " + event.getMessage()));
                            } else {
                                p.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] &r" + player.getDisplayName() + ": " + event.getMessage()));
                            }
                        }
                    }
                } else {
                    if (manager.isPlayerNicked(player)) {
                        MaxiCity.broadcast(player, MaxiCity.chat(manager.getPlayerTeam(player) + " &r" + manager.getPlayerNickname(player) + ": " + event.getMessage()));
                    } else {
                        MaxiCity.broadcast(player, MaxiCity.chat(manager.getPlayerTeam(player) + " &r" + player.getDisplayName() + ": " + event.getMessage()));
                    }
                }
            } else {
                player.sendMessage(MaxiCity.chat("&cYou can't talk, you have been muted for: '" + manager.getMutedPlayerReason(player) + "'"));
            }
        } else {
            if (player.hasPermission("ee.chatalways")) {
                if (!manager.isPlayerMuted(player)) {
                    if (manager.isPlayerNicked(player)) {
                        MaxiCity.broadcast(player, MaxiCity.chat(manager.getPlayerTeam(player) + " &r" + manager.getPlayerNickname(player) + ": " + event.getMessage()));
                    } else {
                        MaxiCity.broadcast(player, MaxiCity.chat(manager.getPlayerTeam(player) + " &r" + player.getDisplayName() + ": " + event.getMessage()));
                    }
                } else {
                    player.sendMessage(MaxiCity.chat("&cYou can't talk, you have been muted for: '" + manager.getMutedPlayerReason(player) + "'"));
                }
            } else {
                player.sendMessage(MaxiCity.chat("&cThe chat is off, you can't talk right now!"));
            }
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = MaxiCity.getInstance().getConfig();
        PlayerManager manager = MaxiCity.getInstance().getPlayerManager();

        if (config.getBoolean("chat_enabled")) {
            if (manager.isPlayerMuted(player)) {
                event.setCancelled(true);
                player.sendMessage(MaxiCity.chat("&cYou can't talk, you have been muted for: '" + manager.getMutedPlayerReason(player) + "'"));
            } else {
                for (Player p : player.getServer().getOnlinePlayers()) {
                    if (manager.getPlayerSocialSpy(p)) {
                        p.sendMessage(MaxiCity.chat("&f[&4SocialSpy&f] " + player.getDisplayName() + event.getMessage()));
                    }
                }
            }
        } else {
            if (!player.hasPermission("ee.chatalways")) {
                event.setCancelled(true);
                player.sendMessage(MaxiCity.chat("&cThe chat is off, you can't talk right now!"));
            }
        }
    }
}
