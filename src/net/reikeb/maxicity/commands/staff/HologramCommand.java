package net.reikeb.maxicity.commands.staff;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HologramCommand implements TabExecutor {

    public MaxiCity plugin;

    public HologramCommand(MaxiCity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(MaxiCity.chat("/holo <create:set:reload:delete>"));
            return true;

        } else {
            FileConfiguration config = MaxiCity.getInstance().getConfig();

            if (args[0].equalsIgnoreCase("create")) {
                generateHolo(plugin, config);
                sender.sendMessage(MaxiCity.chat("&aThe hologram has been successfully created!"));

            } else if (args[0].equalsIgnoreCase("set")) {
                if (sender.getServer().getPlayer(sender.getName()) == null) return false;
                config.set("holo_scores_location", sender.getServer().getPlayer(sender.getName()).getLocation());
                sender.sendMessage(MaxiCity.chat("&aThe spawn point of the hologram has been successfully modified!"));

            } else if (args[0].equalsIgnoreCase("reload")) {
                regenerateHolo(plugin);
                sender.sendMessage(MaxiCity.chat("&aThe hologram has been successfully reloaded!"));

            } else if (args[0].equalsIgnoreCase("delete")) {
                HologramsAPI.getHolograms(plugin).removeAll(HologramsAPI.getHolograms(plugin));
                sender.sendMessage(MaxiCity.chat("&aThe hologram has been successfully deleted!"));

            } else {
                sender.sendMessage(MaxiCity.chat("/holo <create:set:reload:delete>"));
                return true;
            }
        }
        return true;
    }

    public void regenerateHolo(MaxiCity plugin) {
        if (!plugin.getConfig().getBoolean("holo_reload")) return;
        Collection<Hologram> holograms = HologramsAPI.getHolograms(plugin);
        for (Hologram hologram : holograms) {
            hologram.delete();
        }
        generateHolo(plugin, plugin.getConfig());
    }

    public void generateHolo(MaxiCity plugin, FileConfiguration config) {
        if (config.getLocation("holo_scores_location") == null) return;
        Hologram hologram = HologramsAPI.createHologram(plugin, config.getLocation("holo_scores_location"));
        hologram.appendItemLine(new ItemStack(Material.EMERALD));
        hologram.appendTextLine(MaxiCity.chat("&aTeam Scores : "));
        hologram.appendTextLine(MaxiCity.chat("Naboo : " + config.getInt("naboo_balance") + " emeralds"));
        hologram.appendTextLine(MaxiCity.chat("Tatooine : " + config.getInt("tatooine_balance") + " emeralds"));
        hologram.appendTextLine(MaxiCity.chat("Alderaan : " + config.getInt("alderaan_balance") + " emeralds"));
        hologram.appendTextLine(MaxiCity.chat("Coruscant : " + config.getInt("coruscant_balance") + " emeralds"));
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> lis = new ArrayList<>();
        if (args.length == 1) {
            lis.add("create");
            lis.add("set");
            lis.add("reload");
            lis.add("delete");
        }
        return lis;
    }
}
