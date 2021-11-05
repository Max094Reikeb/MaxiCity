package net.reikeb.maxicity.listeners.players;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.misc.CityUtils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class Interact implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getMaterial() != Material.LECTERN) return;
        // Check if player is in the correct region

        giveEmeralds(event.getPlayer());
    }

    public static void giveEmeralds(Player player) {
        int nbrEmeralds = 0;
        for (ItemStack i : player.getInventory().getContents()) {
            if (i.getType().equals(Material.EMERALD)) {
                nbrEmeralds += i.getAmount();
                player.getInventory().remove(Material.EMERALD);
            } else if (i.getType().equals(Material.EMERALD_BLOCK)) {
                nbrEmeralds += (i.getAmount() * 9);
                player.getInventory().remove(Material.EMERALD_BLOCK);
            }
        }

        if (nbrEmeralds == 0) {
            player.sendMessage(MaxiCity.chat("&aYou don't have any emeralds on you!"));
        } else {
            MaxiCity.getInstance().getPlayerManager().addBalanceToPlayer(player, nbrEmeralds);
            player.sendMessage(MaxiCity.chat("&aYou added " + nbrEmeralds + "&aemeralds to your balance!"));

            CityUtils.modifyTeamBalance(player, MaxiCity.getInstance().getConfig(), nbrEmeralds);
        }
    }
}
