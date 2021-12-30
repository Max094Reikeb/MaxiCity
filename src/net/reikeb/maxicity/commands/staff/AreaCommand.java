package net.reikeb.maxicity.commands.staff;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.datas.managers.AreaManager;
import net.reikeb.maxicity.datas.managers.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AreaCommand implements TabExecutor {

    MaxiCity plugin;

    public AreaCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        AreaManager areaManager = plugin.getAreaManager();
        PlayerManager playerManager = plugin.getPlayerManager();
        if ((args.length == 0) || (args.length >= 5)) {
            sender.sendMessage(MaxiCity.chat("/area <new:remove:set:add:info> <name> [<owner:coowner:pvp:redstone>] [<player:true:false>]"));
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("new")) {
                sender.sendMessage(MaxiCity.chat("/area <new> <name>"));
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                sender.sendMessage(MaxiCity.chat("/area <remove> <name> [<coowner>] [<player>]"));
                return true;
            } else if (args[0].equalsIgnoreCase("set")) {
                sender.sendMessage(MaxiCity.chat("/area <set> <name> <owner> <player>"));
                return true;
            } else if (args[0].equalsIgnoreCase("add")) {
                sender.sendMessage(MaxiCity.chat("/area <add> <name> <coowner> <player>"));
                return true;
            } else if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage(MaxiCity.chat("/area <info> <name>"));
                return true;
            }
            return true;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("new")) {
                if (!areaManager.doesAreaExist(args[1])) {
                    Player player = Bukkit.getPlayer(sender.getName());
                    if (player == null) return false;
                    Location loc1 = playerManager.getPlayerLocation1(player);
                    Location loc2 = playerManager.getPlayerLocation2(player);
                    if (loc1 == null) {
                        sender.sendMessage(MaxiCity.chat("&cThe first location must be set in order to create a new area!"));
                        return false;
                    }
                    if (loc2 == null) {
                        sender.sendMessage(MaxiCity.chat("&cThe second location must be set in order to create a new area!"));
                        return false;
                    }
                    areaManager.addNewArea(loc1, loc2, args[1], player);
                    sender.sendMessage(MaxiCity.chat("&aThe area '" + args[1] + "&a' has been created!"));
                } else {
                    sender.sendMessage(MaxiCity.chat("&cThe area '" + args[1] + "&c' already exists!"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (areaManager.doesAreaExist(args[1])) {
                    areaManager.removeAreaFromName(args[1]);
                    sender.sendMessage(MaxiCity.chat("&aThe area '" + args[1] + "&a' has been deleted!"));
                } else {
                    sender.sendMessage(MaxiCity.chat("&cThe area '" + args[1] + "&c' does not exist!"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("info")) {
                if (areaManager.doesAreaExist(args[1])) {
                    Player owner = areaManager.getAreaOwner(args[1]);
                    List<String> coOwners = areaManager.getAreaCoOwners(args[1]);
                    sender.sendMessage(MaxiCity.chat("&2===[Area infos]==="));
                    sender.sendMessage(MaxiCity.chat("&2Name: &c" + args[1]));
                    sender.sendMessage(MaxiCity.chat("&2Owner: &c" + owner.getName()));
                    if ((coOwners != null) && (!coOwners.isEmpty())) {
                        sender.sendMessage(MaxiCity.chat("&2Co-owners: &c" + coOwners));
                    } else {
                        sender.sendMessage(MaxiCity.chat("&2Co-owners: &cNone"));
                    }
                    sender.sendMessage(MaxiCity.chat("&2PVP: &c" + areaManager.isPvpActive(args[1])));
                    sender.sendMessage(MaxiCity.chat("&2Redstone: &c" + areaManager.isRedstoneActive(args[1])));
                    sender.sendMessage(MaxiCity.chat("&2================"));
                } else {
                    sender.sendMessage(MaxiCity.chat("&cThe area '" + args[1] + "&c' does not exist!"));
                }
                return true;
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("set")) {
                sender.sendMessage(MaxiCity.chat("/area <set> <name> <owner:pvp:redstone> <player:true:false>"));
                return true;
            } else if (args[0].equalsIgnoreCase("add")) {
                sender.sendMessage(MaxiCity.chat("/area <add> <name> <coowner> <player>"));
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                sender.sendMessage(MaxiCity.chat("/area <remove> <name> <coowner> <player>"));
            }
            return true;
        } else {
            if (args[0].equalsIgnoreCase("set")) {
                if (areaManager.doesAreaExist(args[1])) {
                    if (args[2].equalsIgnoreCase("owner")) {
                        OfflinePlayer p = Bukkit.getOfflinePlayer(args[3]);
                        if (p.isOnline()) {
                            areaManager.setNewAreaOwner(p, args[1]);
                            sender.sendMessage(MaxiCity.chat("&aYou have set " + p.getName() + " &aas new owner of area '" + args[1] + "&a'!"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("pvp")) {
                        if (args[3].equalsIgnoreCase("true")) {
                            areaManager.setPvp(args[1], true);
                            sender.sendMessage(MaxiCity.chat("&aYou have set pvp in area '" + args[1] + "&a' to true!"));
                        } else if (args[3].equalsIgnoreCase("false")) {
                            areaManager.setPvp(args[1], false);
                            sender.sendMessage(MaxiCity.chat("&aYou have set pvp in area '" + args[1] + "&a' to false!"));
                        } else {
                            sender.sendMessage(MaxiCity.chat("/area <set> <name> <pvp> <true:false>"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("redstone")) {
                        if (args[3].equalsIgnoreCase("true")) {
                            areaManager.setRedstone(args[1], true);
                            sender.sendMessage(MaxiCity.chat("&aYou have set redstone in area '" + args[1] + "&a' to true!"));
                        } else if (args[3].equalsIgnoreCase("false")) {
                            areaManager.setRedstone(args[1], false);
                            sender.sendMessage(MaxiCity.chat("&aYou have set redstone in area '" + args[1] + "&a' to false!"));
                        } else {
                            sender.sendMessage(MaxiCity.chat("/area <set> <name> <redstone> <true:false>"));
                            return true;
                        }
                    }
                    sender.sendMessage(MaxiCity.chat("/area <set> <name> <owner:pvp:redstone> <player:true:false>"));
                    return true;
                } else {
                    sender.sendMessage(MaxiCity.chat("&cThe area '" + args[1] + "&c' does not exist!"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("add")) {
                if (areaManager.doesAreaExist(args[1])) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(args[3]);
                    if (args[2].equalsIgnoreCase("coowner")) {
                        if (p.isOnline()) {
                            areaManager.addAreaCoOwner(p, args[1]);
                            sender.sendMessage(MaxiCity.chat("&aYou have added " + p.getName() + " &aas coowner of area '" + args[1] + "&a'!"));
                            return true;
                        }
                    }
                    sender.sendMessage(MaxiCity.chat("/area <add> <name> <coowner> <player>"));
                    return true;
                } else {
                    sender.sendMessage(MaxiCity.chat("&cThe area '" + args[1] + "&c' does not exist!"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (areaManager.doesAreaExist(args[1])) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(args[3]);
                    if (args[2].equalsIgnoreCase("coowner")) {
                        if (p.isOnline()) {
                            if (areaManager.removeAreaCoOwner(p, args[1])) {
                                sender.sendMessage(MaxiCity.chat("&aYou have removed " + p.getName() + " &afrom coowner list of area '" + args[1] + "&a'!"));
                            } else {
                                sender.sendMessage(MaxiCity.chat("&cPlayer " + p.getName() + " &cis not a coowner of area '" + args[1] + "&a'!"));
                            }
                            return true;
                        }
                    }
                    sender.sendMessage(MaxiCity.chat("/area <remove> <name> <coowner> <player>"));
                    return true;
                } else {
                    sender.sendMessage(MaxiCity.chat("&cThe area '" + args[1] + "&c' does not exist!"));
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> lis = new ArrayList<>();
        if (args.length == 1) {
            lis.add("new");
            lis.add("remove");
            lis.add("set");
            lis.add("add");
            lis.add("info");
        } else if (args.length == 2 && (!args[0].equalsIgnoreCase("new")) && (plugin.getAreaManager().getAreasNames() != null)) {
            lis.addAll(plugin.getAreaManager().getAreasNames());
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("set")) {
                lis.add("owner");
                lis.add("pvp");
                lis.add("redstone");
            } else if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                lis.add("coowner");
            }
        } else if (args.length == 4) {
            if (args[2].equalsIgnoreCase("pvp") || args[2].equalsIgnoreCase("redstone")) {
                lis.add("true");
                lis.add("false");
            } else {
                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    lis.add(p.getName());
                }
            }
        }
        return lis;
    }
}
