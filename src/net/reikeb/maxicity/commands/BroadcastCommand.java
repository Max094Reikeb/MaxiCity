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
        if (args.length == 0) {
            sender.sendMessage(MaxiCity.chat("/broadcast <message>"));
            return true;
        } else {
            StringBuilder message = new StringBuilder();
            for (String arg : args) {
                message.append(arg);
            }
            MaxiCity.broadcast(plugin.getServer(), "&6&l----------");
            MaxiCity.broadcast(plugin.getServer(), "&6&l" + message);
            MaxiCity.broadcast(plugin.getServer(), "&6&l----------");
        }
        return true;
    }
}
