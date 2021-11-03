package net.reikeb.maxicity.commands.message;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MsgCommand implements CommandExecutor {

    MaxiCity plugin;

    public MsgCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(MaxiCity.chat("/msg <player> <message>"));
            return true;
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if ((target == null) || (!target.isOnline())) {
                sender.sendMessage(MaxiCity.chat("&cPlayer " + target + " &ccould not be found!"));
            } else {
                if (args.length >= 2) { // message is here
                    StringBuilder message = new StringBuilder();
                    for (int i = 2; i <= args.length; i++) {
                        message.append(args[i]);
                    }
                    Player user = Bukkit.getPlayer(sender.getName());
                    if (user == null) return false;
                    plugin.getPlayerManager().setChatPrivateReply(user, target);
                    plugin.getPlayerManager().setChatPrivateReply(target, user);
                    sender.sendMessage(MaxiCity.chat("&e[&6me &e» &6" + target + "&e] " + message));
                    target.sendMessage(MaxiCity.chat("&e[&6" + user + "&e &e» &6me&e] " + message));
                } else { // no message
                    sender.sendMessage(MaxiCity.chat("&cYou did not put a message to send to " + target + "&c!"));
                }
            }
        }
        return true;
    }
}
