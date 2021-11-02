package net.reikeb.maxicity.commands;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {

    public MaxiCity plugin;

    public BroadcastCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("ee.broadcast")) {
            if (args.length == 0) {
                sender.sendMessage(MaxiCity.chat("/broadcast <message>"));
                return true;
            } else {
                StringBuilder message = new StringBuilder();
                for (int i = 1; i <= args.length; i++) {
                    message.append(args[i]);
                }
                MaxiCity.broadcast(plugin.getServer(), "&6&l----------");
                MaxiCity.broadcast(plugin.getServer(), "&6&l" + message);
                MaxiCity.broadcast(plugin.getServer(), "&6&l----------");
            }
        } else {
            sender.sendMessage(MaxiCity.chat("&cYou do not have permission to execute this command"));
        }
        return true;
    }
}
