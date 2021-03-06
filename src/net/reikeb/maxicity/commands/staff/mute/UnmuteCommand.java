package net.reikeb.maxicity.commands.staff.mute;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnmuteCommand implements CommandExecutor {

    MaxiCity plugin;

    public UnmuteCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(MaxiCity.chat("/unmute <player>"));
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if ((target == null) || (!target.isOnline())) {
                sender.sendMessage(MaxiCity.chat("&cPlayer " + args[0] + " &ccould not be found!"));
            } else {
                if (!MaxiCity.getInstance().getPlayerManager().isPlayerMuted(target)) {
                    sender.sendMessage(MaxiCity.chat("&cPlayer " + target.getName() + " &cis not muted!"));
                } else {
                    MaxiCity.getInstance().getPlayerManager().unmutePlayer(target);
                    sender.sendMessage(MaxiCity.chat("&aPlayer " + target.getName() + " &ahas been unmuted!"));
                    target.sendMessage(MaxiCity.chat("&2You have been unmuted by " + sender.getName()));
                }
            }
        }
        return true;
    }
}
