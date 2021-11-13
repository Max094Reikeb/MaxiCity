package net.reikeb.maxicity.commands.staff;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class ChatCommand implements TabExecutor {

    MaxiCity plugin;

    public ChatCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(MaxiCity.chat("/chat <on:off>"));
            return true;
        } else {
            if (args[0].equalsIgnoreCase("on")) {
                if (plugin.getConfig().getBoolean("chat_enabled")) {
                    sender.sendMessage(MaxiCity.chat("&cThe chat is already enabled!"));
                } else {
                    plugin.getConfig().set("chat_enabled", true);
                    plugin.saveConfig();
                    MaxiCity.broadcast(plugin.getServer(), "&a" + sender.getName() + " &aenabled the chat!");
                }
            } else if (args[0].equalsIgnoreCase("off")) {
                if (plugin.getConfig().getBoolean("chat_enabled")) {
                    plugin.getConfig().set("chat_enabled", false);
                    plugin.saveConfig();
                    MaxiCity.broadcast(plugin.getServer(), "&4" + sender.getName() + " &4disabled the chat!");
                } else {
                    sender.sendMessage(MaxiCity.chat("&cThe chat is already disabled!"));
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> lis = new ArrayList<>();
        if (args.length == 1) {
            lis.add("on");
            lis.add("off");
        }
        return lis;
    }
}
