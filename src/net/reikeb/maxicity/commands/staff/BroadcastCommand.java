package net.reikeb.maxicity.commands.staff;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class BroadcastCommand implements TabExecutor {

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
                message.append(arg).append(" ");
            }
            MaxiCity.broadcast(plugin.getServer(), "&6&l----------");
            MaxiCity.broadcast(plugin.getServer(), "&6&l" + message.toString().trim());
            MaxiCity.broadcast(plugin.getServer(), "&6&l----------");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> lis = new ArrayList<>();
        if (args.length == 1) {
            lis.add("message");
        }
        return lis;
    }
}
