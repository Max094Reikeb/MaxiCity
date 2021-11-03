package net.reikeb.maxicity.commands;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    public MaxiCity plugin;

    public SpawnCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = Bukkit.getPlayer(sender.getName());
            if (player == null) return false;
            MaxiCity.getInstance().getConfig().set("cite_coos", player.getLocation());
        } else {
            sender.sendMessage(MaxiCity.chat("&cOnly a player can do this command!"));
        }
        return true;
    }
}
