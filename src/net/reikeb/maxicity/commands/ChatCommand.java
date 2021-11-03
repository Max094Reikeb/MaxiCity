package net.reikeb.maxicity.commands;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChatCommand implements CommandExecutor {

    MaxiCity plugin;

    public ChatCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(MaxiCity.chat("/balance <on:off>"));
            return true;
        } else if (args.length == 1) {
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
}
