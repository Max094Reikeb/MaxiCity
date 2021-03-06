package net.reikeb.maxicity.commands.regular;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.datas.managers.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamChatCommand implements CommandExecutor {

    public MaxiCity plugin;

    public TeamChatCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = Bukkit.getPlayer(sender.getName());
        if (player == null) return false;
        PlayerManager manager = plugin.getPlayerManager();
        if (manager.getPlayerTeamChat(player)) {
            manager.setPlayerTeamChat(player, false);
            sender.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] &cTeam chat has been unactivated!"));
        } else if (!manager.getPlayerTeamChat(player)) {
            manager.setPlayerTeamChat(player, true);
            sender.sendMessage(MaxiCity.chat("&f[&aTeamChat&f] &aTeam chat has been activated!"));
        } else {
            sender.sendMessage(MaxiCity.chat("&cSomething went horribly wrong. Send a message to the plugin's author."));
        }
        return true;
    }
}
