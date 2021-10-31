package net.reikeb.maxicity.commands;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.datas.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TeamChatCommand implements CommandExecutor {

    public MaxiCity plugin;

    public TeamChatCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("ee.teamChat")) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(sender.getName());
            PlayerManager manager = new PlayerManager(plugin);
            if (manager.getPlayerTeamChat(player)) {
                manager.setPlayerTeamChat(player, false);
                sender.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] &Team chat has been desactivated!"));
            } else if (!manager.getPlayerTeamChat(player)) {
                manager.setPlayerTeamChat(player, true);
                sender.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] &Team chat has been activated!"));
            } else {
                sender.sendMessage(MaxiCity.chat("&cSomething went horribly wrong. Send a message to the plugin's author."));
            }
        } else {
            sender.sendMessage(MaxiCity.chat("&cYou do not have permission to execute this command"));
        }
        return true;
    }
}
