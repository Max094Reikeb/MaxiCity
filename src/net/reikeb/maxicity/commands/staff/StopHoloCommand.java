package net.reikeb.maxicity.commands.staff;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StopHoloCommand implements CommandExecutor {

    MaxiCity plugin;

    public StopHoloCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (plugin.getConfig().getBoolean("holo_reload")) {
            plugin.getConfig().set("holo_reload", false);
            sender.sendMessage(MaxiCity.chat("&aHologram reload has been disabled!"));
        } else if (!plugin.getConfig().getBoolean("holo_reload")) {
            plugin.getConfig().set("holo_reload", true);
            sender.sendMessage(MaxiCity.chat("&aHologram reload has been enabled!"));
        } else {
            sender.sendMessage(MaxiCity.chat("&cSomething went horribly wrong. Send a message to the plugin's author."));
        }
        return true;
    }
}
