package net.reikeb.maxicity.commands.staff;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.datas.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    MaxiCity plugin;

    public VanishCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PlayerManager manager = plugin.getPlayerManager();
        if (sender instanceof Player player) {
            if (manager.isPlayerVanished(player)) {
                for (Player people : Bukkit.getOnlinePlayers())
                    people.showPlayer(plugin, player);
                manager.setPlayerVanish(player, false);
                player.sendMessage(MaxiCity.chat("&aYou have been un-vanished!"));
            } else {
                for (Player people : Bukkit.getOnlinePlayers())
                    people.hidePlayer(plugin, player);
                manager.setPlayerVanish(player, true);
                player.sendMessage(MaxiCity.chat("&aYou have been vanished!"));
            }
        }
        return true;
    }
}
