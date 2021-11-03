package net.reikeb.maxicity.commands;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommand implements CommandExecutor {

    MaxiCity plugin;

    public MoneyCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = Bukkit.getPlayer(sender.getName());
            if (player == null) return false;
            sender.sendMessage(MaxiCity.chat("&aYou have " + plugin.getPlayerManager().getPlayerBalance(player) + " &aemeralds in your balance!"));
        } else {
            sender.sendMessage(MaxiCity.chat("&cOnly a player can do this command!"));
        }
        return true;
    }
}
