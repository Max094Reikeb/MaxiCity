package net.reikeb.maxicity.listeners.players;

import net.reikeb.maxicity.misc.CityUtils;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class Interact implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getMaterial() != Material.LECTERN) return;
        // Check if player is in the correct region

        CityUtils.giveEmeralds(event.getPlayer());
    }
}
