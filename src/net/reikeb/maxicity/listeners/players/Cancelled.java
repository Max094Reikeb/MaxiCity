package net.reikeb.maxicity.listeners.players;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.datas.Area;
import net.reikeb.maxicity.datas.managers.AreaManager;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;

public class Cancelled implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        AreaManager areaManager = MaxiCity.getInstance().getAreaManager();
        if (!areaManager.isPlayerAbleToManageArea(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        AreaManager areaManager = MaxiCity.getInstance().getAreaManager();
        if (!areaManager.isPlayerAbleToManageArea(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        AreaManager areaManager = MaxiCity.getInstance().getAreaManager();
        for (Area area : areaManager.getLocationAreas(event.getLocation())) {
            if ((area != null) && (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.COMMAND))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerHurt(EntityDamageEvent event) {
        AreaManager areaManager = MaxiCity.getInstance().getAreaManager();
        if (event.getEntity() instanceof Player player) {
            for (Area area : areaManager.getPlayerAreas(player)) {
                if (!area.isPvpActive()) event.setCancelled(true);
            }
        } else if (event.getEntity() instanceof LivingEntity entity) {
            for (Area area : areaManager.getEntityAreas(entity)) {
                if (!area.isPvpActive()) event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        AreaManager areaManager = MaxiCity.getInstance().getAreaManager();
        for (Area area : areaManager.getLocationAreas(event.getBlock().getLocation())) {
            if (!area.isRedstoneActive()) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        AreaManager areaManager = MaxiCity.getInstance().getAreaManager();
        for (Area area : areaManager.getLocationAreas(event.getBlock().getLocation())) {
            if (!area.isRedstoneActive()) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        AreaManager areaManager = MaxiCity.getInstance().getAreaManager();
        for (Area area : areaManager.getLocationAreas(event.getBlock().getLocation())) {
            if (area != null) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        AreaManager areaManager = MaxiCity.getInstance().getAreaManager();
        for (Area area : areaManager.getLocationAreas(event.getBlockClicked().getLocation())) {
            if (area != null) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        AreaManager areaManager = MaxiCity.getInstance().getAreaManager();
        for (Area area : areaManager.getLocationAreas(event.getBlockClicked().getLocation())) {
            if (area != null) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLecternUse(PlayerTakeLecternBookEvent event) {
        AreaManager areaManager = MaxiCity.getInstance().getAreaManager();
        for (Area area : areaManager.getLocationAreas(event.getLectern().getLocation())) {
            if (area != null) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onVehicleCreate(VehicleCreateEvent event) {
        AreaManager areaManager = MaxiCity.getInstance().getAreaManager();
        for (Area area : areaManager.getLocationAreas(event.getVehicle().getLocation())) {
            if (area != null) event.setCancelled(true);
        }
    }
}
