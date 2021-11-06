package net.reikeb.maxicity.commands.regular;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class CityCommand implements CommandExecutor {

    MaxiCity plugin;

    public CityCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = plugin.getConfig();
        double cityX = config.getLocation("cite_coos").getX();
        double cityY = config.getLocation("cite_coos").getY();
        double cityZ = config.getLocation("cite_coos").getZ();
        sender.sendMessage(MaxiCity.chat(config.getString("cite_coos_message") + cityX + " " + cityY + " " + cityZ));
        return true;
    }
}
