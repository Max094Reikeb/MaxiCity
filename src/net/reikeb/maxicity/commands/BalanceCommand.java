package net.reikeb.maxicity.commands;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.managers.BalanceManager;

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
        if (sender.hasPermission("ee.adminBalance")) {
            if (args.length == 0) {
                sender.sendMessage(MaxiCity.chat("/balance <add:remove:set> <player> <amount>"));
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("add")) {
                    sender.sendMessage(MaxiCity.chat("/balance <add> <player> <amount>"));
                } else if (args[0].equalsIgnoreCase("remove")) {
                    sender.sendMessage(MaxiCity.chat("/balance <remove> <player> <amount>"));
                } else if (args[1].equalsIgnoreCase("set")) {
                    sender.sendMessage(MaxiCity.chat("/balance <set> <player> <amount>"));
                } else if (args[0].equalsIgnoreCase("balance")) {
                    sender.sendMessage(MaxiCity.chat("/balance <balance> <player>"));
                }
            } else if (args.length == 2) {
                BalanceManager manager = new BalanceManager(plugin);
                @SuppressWarnings("deprecation")
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                if (args[0].equalsIgnoreCase("balance")) {
                    if (p.isOnline()) {
                        sender.sendMessage(MaxiCity.chat(args[1] + " &abalance's is " + manager.getPlayerBalance(p) + " &aemeralds"));
                        return true;
                    }
                } else {
                    sender.sendMessage(MaxiCity.chat("&cPlayer " + args[1] + " &dcould not be found!"));
                }
            } else if (args.length == 3) {
                @SuppressWarnings("deprecation")
                int amount = Integer.parseInt(args[2]);
                BalanceManager manager = new BalanceManager(plugin);
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                if (args[0].equalsIgnoreCase("add")) {
                    if (p.isOnline()) {
                        manager.addBalanceToPlayer(p, amount);
                        sender.sendMessage(MaxiCity.chat("&aYou have successfully added " + args[2] + " &aemeralds to player " + p.getName()));
                    } else {
                        sender.sendMessage(MaxiCity.chat("&cPlayer " + args[1] + " &ccould not be found"));
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (p.isOnline()) {
                        manager.removeBalanceFromPlayer(p, amount);
                        sender.sendMessage(MaxiCity.chat("&aYou have successfully removed " + args[2] + "&aemeralds from player " + p.getName()));
                    } else {
                        sender.sendMessage(MaxiCity.chat("&cPlayer " + args[1] + " &ccould not be found "));
                    }
                } else if (args[0].equalsIgnoreCase("set")) {
                    if (p.isOnline()) {
                        manager.setPlayerBalance(p, amount);
                        sender.sendMessage(MaxiCity.chat("&aYou have successfully set the player " + p.getName() + " &a" + args[2] + " &aemeralds"));
                    } else {
                        sender.sendMessage(MaxiCity.chat("&cPlayer " + args[1] + " &ccould not be found"));
                    }
                }
            }
        } else {
            sender.sendMessage(MaxiCity.chat("&cYou do not have permission to execute this command"));
        }
        return true;
    }
}
