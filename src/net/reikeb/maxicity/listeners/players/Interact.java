package net.reikeb.maxicity.listeners.players;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.datas.managers.AreaManager;
import net.reikeb.maxicity.datas.managers.PlayerManager;
import net.reikeb.maxicity.misc.Utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class Interact implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        AreaManager areaManager = MaxiCity.getInstance().getAreaManager();
        PlayerManager playerManager = MaxiCity.getInstance().getPlayerManager();

        if (event.getHand() == EquipmentSlot.HAND) {
            if (event.getClickedBlock() != null) {
                if (event.getClickedBlock().getType() == Material.LECTERN) {
                    if (areaManager.isPlayerInArea(player, "spawn")) {
                        giveEmeralds(player);
                    }
                }
                if (player.getItemInHand().getType() == Material.WOODEN_AXE) {
                    if (player.getGameMode() != GameMode.CREATIVE) return;
                    if (!player.hasPermission("ee.loc")) return;
                    if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                        event.setCancelled(true);
                        playerManager.setPlayerLocation1(player, event.getClickedBlock().getLocation());
                        player.sendMessage(MaxiCity.chat("&aPosition 1 set!"));
                    } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        event.setCancelled(true);
                        playerManager.setPlayerLocation2(player, event.getClickedBlock().getLocation());
                        player.sendMessage(MaxiCity.chat("&aPosition 2 set!"));
                    }
                }
            }
        }
    }

    public static void giveEmeralds(Player player) {
        int nbrEmeralds = 0;
        for (ItemStack i : player.getInventory().getContents()) {
            if (i != null) {
                if (i.getType().equals(Material.EMERALD)) {
                    nbrEmeralds += i.getAmount();
                    player.getInventory().remove(Material.EMERALD);
                } else if (i.getType().equals(Material.EMERALD_BLOCK)) {
                    nbrEmeralds += (i.getAmount() * 9);
                    player.getInventory().remove(Material.EMERALD_BLOCK);
                }
            }
        }

        if (nbrEmeralds == 0) {
            player.sendMessage(MaxiCity.chat("&aYou don't have any emeralds on you!"));
        } else {
            MaxiCity.getInstance().getPlayerManager().addBalanceToPlayer(player, nbrEmeralds);
            player.sendMessage(MaxiCity.chat("&aYou added " + nbrEmeralds + " &aemeralds to your balance!"));

            Utils.modifyTeamBalance(player, MaxiCity.getInstance().getConfig(), nbrEmeralds);
        }
    }
}
