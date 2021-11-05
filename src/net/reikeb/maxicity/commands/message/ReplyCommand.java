package net.reikeb.maxicity.commands.message;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand implements CommandExecutor {

    MaxiCity plugin;

    public ReplyCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(MaxiCity.chat("/r <message>"));
        } else {
            Player user = Bukkit.getPlayer(sender.getName());
            if (user == null) return false;
            Player target = plugin.getPlayerManager().getChatPrivateReply(user);
            if (target == null) {
                sender.sendMessage(MaxiCity.chat("&cYou don't have a player to respond to!"));
                return true;
            }
            if (!target.isOnline()) {
                sender.sendMessage(MaxiCity.chat("&cPlayer " + target + " &ccould not be found!"));
                return true;
            }
            StringBuilder message = new StringBuilder();
            for (String arg : args) {
                message.append(arg).append(" ");
            }
            sender.sendMessage(MaxiCity.chat("&e[&6me &e» &6" + target.getName() + "&e] " + message.toString().trim()));
            target.sendMessage(MaxiCity.chat("&e[&6" + user.getName() + "&e &e» &6me&e] " + message.toString().trim()));
        }
        return true;
    }
}
