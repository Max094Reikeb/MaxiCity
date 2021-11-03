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
            return true;
        } else {
            StringBuilder message = new StringBuilder();
            for (int i = 1; i <= args.length; i++) {
                message.append(args[i]);
            }
            Player user = Bukkit.getPlayer(sender.getName());
            if (user == null) return false;
            Player target = plugin.getPlayerManager().getChatPrivateReply(user);
            sender.sendMessage(MaxiCity.chat("&e[&6me &e» &6" + target + "&e] " + message));
            target.sendMessage(MaxiCity.chat("&e[&6" + user + "&e &e» &6me&e] " + message));
        }
        return true;
    }
}
