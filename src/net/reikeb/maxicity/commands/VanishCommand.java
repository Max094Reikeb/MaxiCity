package net.reikeb.maxicity.commands;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class VanishCommand implements CommandExecutor {

    MaxiCity plugin;
    ArrayList<Player> vanished = new ArrayList<>();

    public VanishCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (p.isOp()) {
                toggleVanish(p);
            }
        }
        return true;
    }

    private void toggleVanish(Player p) {
        if (vanished.contains(p)) {
            for (Player people : Bukkit.getOnlinePlayers())
                people.showPlayer(plugin, p);
            vanished.remove(p);
            p.sendMessage(MaxiCity.chat("&aYou have been un-vanished!"));
        } else {
            for (Player people : Bukkit.getOnlinePlayers())
                people.hidePlayer(plugin, p);
            vanished.add(p);
            p.sendMessage(MaxiCity.chat("&aYou have been vanished!"));
        }
    }
}
