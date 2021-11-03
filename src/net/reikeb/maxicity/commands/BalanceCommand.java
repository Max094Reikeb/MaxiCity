package net.reikeb.maxicity.commands;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BalanceCommand implements CommandExecutor {

    MaxiCity plugin;

    public BalanceCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(MaxiCity.chat("/balance <add:remove:set> <player> <amount>"));
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("add")) {
                sender.sendMessage(MaxiCity.chat("/balance <add> <player> <amount>"));
            } else if (args[0].equalsIgnoreCase("remove")) {
                sender.sendMessage(MaxiCity.chat("/balance <remove> <player> <amount>"));
            } else if (args[0].equalsIgnoreCase("set")) {
                sender.sendMessage(MaxiCity.chat("/balance <set> <player> <amount>"));
            } else if (args[0].equalsIgnoreCase("balance")) {
                sender.sendMessage(MaxiCity.chat("/balance <balance> <player>"));
            }
        } else if (args.length == 2) {
            @SuppressWarnings("deprecation")
            OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
            if (args[0].equalsIgnoreCase("balance")) {
                if (p.isOnline()) {
                    sender.sendMessage(MaxiCity.chat("&a" + args[1] + " &abalance's is " + plugin.getPlayerManager().getPlayerBalance(p) + " &aemeralds"));
                    return true;
                }
            } else {
                sender.sendMessage(MaxiCity.chat("&cPlayer " + args[1] + " &dcould not be found!"));
            }
        } else if (args.length == 3) {
            @SuppressWarnings("deprecation")
            int amount = Integer.parseInt(args[2]);
            OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
            if (args[0].equalsIgnoreCase("add")) {
                if (p.isOnline()) {
                    plugin.getPlayerManager().addBalanceToPlayer(p, amount);
                    sender.sendMessage(MaxiCity.chat("&aYou have successfully added " + args[2] + " &aemeralds to player " + p.getName()));
                } else {
                    sender.sendMessage(MaxiCity.chat("&cPlayer " + args[1] + " &ccould not be found"));
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (p.isOnline()) {
                    plugin.getPlayerManager().removeBalanceFromPlayer(p, amount);
                    sender.sendMessage(MaxiCity.chat("&aYou have successfully removed " + args[2] + " &aemeralds from player " + p.getName()));
                } else {
                    sender.sendMessage(MaxiCity.chat("&cPlayer " + args[1] + " &ccould not be found "));
                }
            } else if (args[0].equalsIgnoreCase("set")) {
                if (p.isOnline()) {
                    plugin.getPlayerManager().setPlayerBalance(p, amount);
                    sender.sendMessage(MaxiCity.chat("&aYou have successfully set " + p.getName() + "&a's balance to " + args[2] + " &aemeralds"));
                } else {
                    sender.sendMessage(MaxiCity.chat("&cPlayer " + args[1] + " &ccould not be found"));
                }
            }
        }
        return true;
    }
}
