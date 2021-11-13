package net.reikeb.maxicity.datas.managers;

import net.reikeb.maxicity.MaxiCity;
import net.reikeb.maxicity.datas.Area;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
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
            areaMap.put(name, new Area(upCorner, downCorner, name, owner.getUniqueId(), null));
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

    public List<String> getAreasNames() {
        Set<String> keySet = areaMap.keySet();
        List<String> sKeys = new ArrayList<>(keySet);
        List<String> names = new ArrayList<>();
        for (String key : sKeys) {
            names.add(areaMap.get(key).getName());
        }
        return names;
    }
}
