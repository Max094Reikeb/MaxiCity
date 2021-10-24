package net.reikeb.maxicity.commands;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PluginsOverrideCommand implements CommandExecutor {

    private MaxiCity plugin;

    public PluginsOverrideCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            sender.sendMessage(MaxiCity.chat("Plugins (3): &aMaxiCity-core, MaxiCity-events, MaxiCity-moderation"));
        }

        return true;
    }
}
