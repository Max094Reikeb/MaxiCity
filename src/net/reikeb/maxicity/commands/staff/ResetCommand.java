package net.reikeb.maxicity.commands.staff;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCommand implements CommandExecutor {

    MaxiCity plugin;

    public ResetCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (OfflinePlayer player : plugin.getServer().getOfflinePlayers()) {
            plugin.getPlayerManager().setPlayerBalance(player, 0);
        }
        for (Player players : plugin.getServer().getOnlinePlayers()) {
            plugin.getPlayerManager().setPlayerBalance(players, 0);
        }
        plugin.getConfig().set("naboo_balance", 0);
        plugin.getConfig().set("alderaan_balance", 0);
        plugin.getConfig().set("tatooine_balance", 0);
        plugin.getConfig().set("coruscant_balance", 0);
        sender.sendMessage(MaxiCity.chat("&aYou just reset all emerald counts!"));
        return true;
    }
}
