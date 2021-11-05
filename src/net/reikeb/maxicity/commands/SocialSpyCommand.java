package net.reikeb.maxicity.commands;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.datas.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SocialSpyCommand implements CommandExecutor {

    MaxiCity plugin;

    public SocialSpyCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = Bukkit.getPlayer(sender.getName());
        if (player == null) return false;
        PlayerManager manager = plugin.getPlayerManager();
        if (manager.getPlayerSocialSpy(player)) {
            manager.setPlayerSocialSpy(player, false);
            sender.sendMessage(MaxiCity.chat("&f[&4SocialSpy&f] &cSocialSpy has been desactivated!"));
        } else if (!manager.getPlayerSocialSpy(player)) {
            manager.setPlayerSocialSpy(player, true);
            sender.sendMessage(MaxiCity.chat("&f[&4SocialSpy&f] &2SocialSpy has been activated!"));
        } else {
            sender.sendMessage(MaxiCity.chat("&cSomething went horribly wrong. Send a message to the plugin's author."));
        }
        return true;
    }
}
