package net.reikeb.maxicity.commands.mute;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {

    MaxiCity plugin;

    public MuteCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(MaxiCity.chat("/mute <player> [<reason>]"));
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if ((target == null) || (!target.isOnline())) {
                sender.sendMessage(MaxiCity.chat("&cPlayer " + args[0] + " &ccould not be found!"));
            } else {
                if (MaxiCity.getInstance().getPlayerManager().isPlayerMuted(target)) {
                    sender.sendMessage(MaxiCity.chat("&cPlayer " + target.getName() + " &cis already muted!"));
                } else {
                    if (args.length >= 2) { // If reason is here
                        StringBuilder reason = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            reason.append(args[i]).append(" ");
                        }
                        MaxiCity.getInstance().getPlayerManager().mutePlayer(target, reason.toString().trim());
                        sender.sendMessage(MaxiCity.chat("&aPlayer " + target.getName() + " &ahas been muted for: '" + reason.toString().trim() + "'!"));
                        target.sendMessage(MaxiCity.chat("&2You have been muted by " + sender.getName() + " for: '" + reason.toString().trim() + "'!"));
                    } else { // has no reason
                        MaxiCity.getInstance().getPlayerManager().mutePlayer(target, "");
                        sender.sendMessage(MaxiCity.chat("&aPlayer " + target.getName() + " &ahas been muted!"));
                        target.sendMessage(MaxiCity.chat("&2You have been muted by " + sender.getName()));
                    }
                }
            }
        }
        return true;
    }
}
