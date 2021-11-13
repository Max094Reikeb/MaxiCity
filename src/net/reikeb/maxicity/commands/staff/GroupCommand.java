package net.reikeb.maxicity.commands.staff;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.misc.Utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupCommand implements TabExecutor {

    MaxiCity plugin;

    public GroupCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        File teamsFile = new File(plugin.getDataFolder(), "teams.yml");
        YamlConfiguration yaml = new YamlConfiguration();
        if (!teamsFile.exists())
            Utils.setupTeamFile(plugin);

        if ((args.length == 0) || (args.length >= 4)) {
            sender.sendMessage(MaxiCity.chat("/group <add:remove:move> <player> <group>"));
            return true;
        } else if ((args.length == 1) || (args.length == 2)) {
            if (args[0].equalsIgnoreCase("add")) {
                sender.sendMessage(MaxiCity.chat("/group <add> <player> <group>"));
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                sender.sendMessage(MaxiCity.chat("/group <remove> <player> <group>"));
                return true;
            } else if (args[0].equalsIgnoreCase("move")) {
                sender.sendMessage(MaxiCity.chat("/group <move> <player> <group>"));
                return true;
            }
            return true;
        } else {
            @SuppressWarnings("deprecation")
            String group = args[2];
            if (!isGroupCorrect(group)) {
                sender.sendMessage(MaxiCity.chat("&cThe group " + args[2] + " &cdoesn't exist! Enter a valid group!"));
                return false;
            }

            OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
            if (args[0].equalsIgnoreCase("add")) {
                addPlayerToGroup(yaml, p, group);
                sender.sendMessage(MaxiCity.chat("&aYou have successfully added " + args[1] + " &ato group " + args[2] + "&a!"));
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (!removePlayerFromGroup(yaml, p, group)) {
                    sender.sendMessage(MaxiCity.chat("&cPlayer " + args[1] + " &cisn't part of group " + args[2] + "&c!"));
                } else {
                    sender.sendMessage(MaxiCity.chat("&aYou successfully removed " + args[1] + " &afrom group " + args[2] + "&a!"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("move")) {
                String playerGroup = getGroupOfPlayer((Player) p);
                if (!removePlayerFromGroup(yaml, p, playerGroup)) {
                    sender.sendMessage(MaxiCity.chat("&cPlayer " + args[1] + " &cisn't part of group " + playerGroup + " &cso he can't be moved from it!"));
                } else {
                    addPlayerToGroup(yaml, p, group);
                    sender.sendMessage(MaxiCity.chat("&aYou successfully moved " + args[1] + " &afrom group " + playerGroup + " &ato group " + args[2] + "&a!"));
                }
                return true;
            }
        }
        return true;
    }

    private boolean isGroupCorrect(String group) {
        if (group.equals("team_one")) return true;
        if (group.equals("team_two")) return true;
        if (group.equals("team_three")) return true;
        if (group.equals("team_four")) return true;
        if (group.equals("moderators")) return true;
        return group.equals("admin");
    }

    private String getGroupOfPlayer(Player player) {
        if (player.hasPermission("team.admin")) return "admin";
        if (player.hasPermission("team.moderators")) return "moderators";
        if (player.hasPermission("team.one")) return "team_one";
        if (player.hasPermission("team.two")) return "team_two";
        if (player.hasPermission("team.three")) return "team_three";
        if (player.hasPermission("team.four")) return "team_four";
        return "";
    }

    private void addPlayerToGroup(YamlConfiguration yaml, OfflinePlayer player, String group) {
        List<String> players = yaml.getStringList(group);
        players.add(player.getName());
        yaml.set(group, players);

        try {
            yaml.save("plugins/MaxiCity/teams.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean removePlayerFromGroup(YamlConfiguration yaml, OfflinePlayer player, String group) {
        List<String> players = yaml.getStringList(group);
        boolean flag = players.removeIf(s -> player.getName().equals(s));
        yaml.set(group, players);

        try {
            yaml.save("plugins/MaxiCity/teams.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flag;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> lis = new ArrayList<>();
        if (args.length == 1) {
            lis.add("add");
            lis.add("remove");
            lis.add("move");
        } else if (args.length == 2) {
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                lis.add(p.getName());
            }
        } else if (args.length == 3) {
            lis.add("team_one");
            lis.add("team_two");
            lis.add("team_three");
            lis.add("team_four");
            lis.add("moderators");
            lis.add("admin");
        }
        return lis;
    }
}
