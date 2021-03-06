package net.reikeb.maxicity.commands.staff;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.misc.Utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BalanceCommand implements TabExecutor {

    MaxiCity plugin;

    public BalanceCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ((args.length == 0) || (args.length >= 4)) {
            sender.sendMessage(MaxiCity.chat("/balance <balance:add:remove:set> <player> [<amount>]"));
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("add")) {
                sender.sendMessage(MaxiCity.chat("/balance <add> <player> <amount>"));
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                sender.sendMessage(MaxiCity.chat("/balance <remove> <player> <amount>"));
                return true;
            } else if (args[0].equalsIgnoreCase("set")) {
                sender.sendMessage(MaxiCity.chat("/balance <set> <player> <amount>"));
                return true;
            } else if (args[0].equalsIgnoreCase("balance")) {
                sender.sendMessage(MaxiCity.chat("/balance <balance> <player>"));
                return true;
            }
            return true;
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
            return true;
        } else {
            @SuppressWarnings("deprecation")
            int amount = Integer.parseInt(args[2]);
            OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
            if (args[0].equalsIgnoreCase("add")) {
                if (p.isOnline()) {
                    plugin.getPlayerManager().addBalanceToPlayer(p, amount);
                    Utils.modifyTeamBalance((Player) p, plugin.getConfig(), amount);
                    sender.sendMessage(MaxiCity.chat("&aYou have successfully added " + args[2] + " &aemeralds to player " + p.getName()));
                } else {
                    sender.sendMessage(MaxiCity.chat("&cPlayer " + args[1] + " &ccould not be found"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (p.isOnline()) {
                    plugin.getPlayerManager().removeBalanceFromPlayer(p, amount);
                    Utils.modifyTeamBalance((Player) p, plugin.getConfig(), -amount);
                    sender.sendMessage(MaxiCity.chat("&aYou have successfully removed " + args[2] + " &aemeralds from player " + p.getName()));
                } else {
                    sender.sendMessage(MaxiCity.chat("&cPlayer " + args[1] + " &ccould not be found "));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("set")) {
                if (p.isOnline()) {
                    int before = plugin.getPlayerManager().getPlayerBalance(p);
                    plugin.getPlayerManager().setPlayerBalance(p, amount);
                    Utils.modifyTeamBalance((Player) p, plugin.getConfig(), (amount - before));
                    sender.sendMessage(MaxiCity.chat("&aYou have successfully set " + p.getName() + "&a's balance to " + args[2] + " &aemeralds"));
                } else {
                    sender.sendMessage(MaxiCity.chat("&cPlayer " + args[1] + " &ccould not be found"));
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> lis = new ArrayList<>();
        if (args.length == 1) {
            lis.add("balance");
            lis.add("add");
            lis.add("remove");
            lis.add("set");
        } else if (args.length == 2) {
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                lis.add(p.getName());
            }
        } else if (args.length == 3 && (!args[0].equalsIgnoreCase("balance"))) {
            lis.add("count");
        }
        return lis;
    }
}
