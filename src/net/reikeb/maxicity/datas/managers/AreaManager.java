package net.reikeb.maxicity.datas.managers;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.datas.Area;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class AreaManager {

    public final HashMap<String, Area> areaMap = new HashMap<>();

    public MaxiCity plugin;
    public DataManager dataManager;

    public AreaManager(MaxiCity plugin) {
        this.plugin = plugin;
        this.dataManager = new DataManager(plugin);
    }

    public void saveHashMap() {
        Set<String> keySet = areaMap.keySet();
        List<String> sKeys = new ArrayList<>(keySet);
        dataManager.getConfig().set("areaMap", sKeys);
        dataManager.saveConfig();
    }

    public void loadHashMap() {
        List<String> sKeys = dataManager.getConfig().getStringList("areaMap");
        if (sKeys.isEmpty()) return;
        for (String sKey : sKeys) {
            areaMap.put(sKey, areaMap.get(sKey));
        }
    }

    public void addNewArea(Location upCorner, Location downCorner, String name, Player owner) {
        if (!doesAreaExist(name))
            areaMap.put(name, new Area(upCorner, downCorner, name, owner.getUniqueId(), null, false, false));
    }

    public void setNewAreaOwner(OfflinePlayer newOwner, String areaName) {
        if (doesAreaExist(areaName))
            areaMap.get(areaName).setOwner(newOwner.getUniqueId());
    }

    public Player getAreaOwner(String areaName) {
        if (doesAreaExist(areaName))
            return Bukkit.getPlayer(areaMap.get(areaName).getOwner());
        return null;
    }

    public void addAreaCoOwner(OfflinePlayer coOwner, String areaName) {
        if (doesAreaExist(areaName)) {
            List<UUID> coOwners = areaMap.get(areaName).getCoOwners();
            if (coOwners != null) {
                coOwners.add(coOwner.getUniqueId());
                areaMap.get(areaName).setCoOwners(coOwners);
            } else {
                List<UUID> newCoOwners = new ArrayList<>();
                newCoOwners.add(coOwner.getUniqueId());
                areaMap.get(areaName).setCoOwners(newCoOwners);
            }
        }
    }

    public List<String> getAreaCoOwners(String areaName) {
        if (doesAreaExist(areaName)) {
            List<UUID> coOwners = areaMap.get(areaName).getCoOwners();
            List<String> pCoOwners = new ArrayList<>();
            if (coOwners == null) return null;
            for (UUID uuid : coOwners) {
                pCoOwners.add(Bukkit.getPlayer(uuid).getName());
            }
            return pCoOwners;
        }
        return null;
    }

    public boolean removeAreaCoOwner(OfflinePlayer player, String areaName) {
        if (doesAreaExist(areaName)) {
            List<UUID> coOwners = areaMap.get(areaName).getCoOwners();
            if (!coOwners.contains(player.getUniqueId())) return false;
            coOwners.remove(player.getUniqueId());
            areaMap.get(areaName).setCoOwners(coOwners);
            return true;
        }
        return false;
    }

    public void removeAreaFromName(String name) {
        if (doesAreaExist(name))
            areaMap.remove(name);
    }

    public boolean doesAreaExist(String name) {
        return (areaMap.get(name) != null);
    }

    public boolean isPlayerInArea(Player player, String areaName) {
        if (!doesAreaExist(areaName)) return false;
        return areaMap.get(areaName).getCuboid().isIn(player);
    }

    public boolean isEntityInArea(LivingEntity entity, String areaName) {
        if (!doesAreaExist(areaName)) return false;
        return areaMap.get(areaName).getCuboid().isIn(entity);
    }

    public boolean isLocationInArea(Location location, String areaName) {
        if (!doesAreaExist(areaName)) return false;
        return areaMap.get(areaName).getCuboid().isIn(location);
    }

    public boolean isPlayerOwner(Player player, String areaName) {
        if (!doesAreaExist(areaName)) return false;
        return areaMap.get(areaName).getOwner() == player.getUniqueId();
    }

    public boolean isPlayerCoOwner(Player player, String areaName) {
        if (!doesAreaExist(areaName)) return false;
        for (String names : getAreaCoOwners(areaName)) {
            if (Bukkit.getPlayer(names) == player) {
                return true;
            }
        }
        return false;
    }

    public boolean isPvpActive(String areaName) {
        if (!doesAreaExist(areaName)) return false;
        return areaMap.get(areaName).isPvpActive();
    }

    public void setPvp(String areaName, boolean pvp) {
        if (!doesAreaExist(areaName)) return;
        areaMap.get(areaName).setPvp(pvp);
    }

    public boolean isRedstoneActive(String areaName) {
        if (!doesAreaExist(areaName)) return false;
        return areaMap.get(areaName).isRedstoneActive();
    }

    public void setRedstone(String areaName, boolean redstone) {
        if (!doesAreaExist(areaName)) return;
        areaMap.get(areaName).setRedstone(redstone);
    }

    public List<Area> getPlayerAreas(Player player) {
        Set<String> keySet = areaMap.keySet();
        List<String> sKeys = new ArrayList<>(keySet);
        List<Area> areas = new ArrayList<>();
        for (String key : sKeys) {
            if (isPlayerInArea(player, key)) {
                areas.add(areaMap.get(key));
            }
        }
        return areas;
    }

    public List<Area> getEntityAreas(LivingEntity entity) {
        Set<String> keySet = areaMap.keySet();
        List<String> sKeys = new ArrayList<>(keySet);
        List<Area> areas = new ArrayList<>();
        for (String key : sKeys) {
            if (isEntityInArea(entity, key)) {
                areas.add(areaMap.get(key));
            }
        }
        return areas;
    }

    public List<Area> getLocationAreas(Location location) {
        Set<String> keySet = areaMap.keySet();
        List<String> sKeys = new ArrayList<>(keySet);
        List<Area> areas = new ArrayList<>();
        for (String key : sKeys) {
            if (isLocationInArea(location, key)) {
                areas.add(areaMap.get(key));
            }
        }
        return areas;
    }

    public List<String> getAreasNames() {
        Set<String> keySet = areaMap.keySet();
        List<String> sKeys = new ArrayList<>(keySet);
        List<String> names = new ArrayList<>();
        for (String key : sKeys) {
            names.add(areaMap.get(key).getName());
        }
        return names;
    }

    public boolean isPlayerAbleToManageArea(Player player) {
        List<Area> areas = getPlayerAreas(player);
        for (Area area : areas) {
            if (isPlayerOwner(player, area.getName())) return true;
            if (player.isOp()) return true;
            return isPlayerCoOwner(player, area.getName());
        }
        return true;
    }
}
