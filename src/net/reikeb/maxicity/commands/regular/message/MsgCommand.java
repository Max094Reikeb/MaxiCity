package net.reikeb.maxicity.commands.regular.message;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MsgCommand implements TabExecutor {

    MaxiCity plugin;

    public MsgCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(MaxiCity.chat("/msg <player> <message>"));
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            Player user = Bukkit.getPlayer(sender.getName());
            if (user == null) return false;
            if ((target == null) || (!target.isOnline())) {
                sender.sendMessage(MaxiCity.chat("&cPlayer " + args[0] + " &ccould not be found!"));
            } else {
                if (args.length >= 2) { // If the message is there
                    StringBuilder message = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        message.append(args[i]).append(" ");
                    }
                    plugin.getPlayerManager().setChatPrivateReply(user, target);
                    plugin.getPlayerManager().setChatPrivateReply(target, user);
                    sender.sendMessage(MaxiCity.chat("&e[&6me &e» &6" + target.getName() + "&e] " + message.toString().trim()));
                    target.sendMessage(MaxiCity.chat("&e[&6" + user.getName() + " &e» &6me&e] " + message.toString().trim()));
                } else { // If there isn't a message
                    sender.sendMessage(MaxiCity.chat("&cYou did not put a message to send to " + target + "&c!"));
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> lis = new ArrayList<>();
        if (args.length == 1) {
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                lis.add(p.getName());
            }
        } else if (args.length == 2) {
            lis.add("message");
        }
        return lis;
    }
}
