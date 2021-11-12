package net.reikeb.maxicity.datas;

import net.reikeb.maxicity.misc.Cuboid;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Area implements Cloneable, ConfigurationSerializable {

    private Location upCorner;
    private Location downCorner;
    private String name;
    private UUID owner;
    private List<UUID> coOwners;
    private Cuboid cuboid;

    public Area(Location upCorner, Location downCorner, String name, UUID owner, List<UUID> coOwners) {
        this.upCorner = upCorner;
        this.downCorner = downCorner;
        this.name = name;
        this.owner = owner;
        this.coOwners = coOwners;
        this.cuboid = new Cuboid(upCorner, downCorner);
    }

    public Area(Location upCorner, Location downCorner, String name, UUID owner, List<UUID> coOwners, Cuboid cuboid) {
        this.upCorner = upCorner;
        this.downCorner = downCorner;
        this.name = name;
        this.owner = owner;
        this.coOwners = coOwners;
        this.cuboid = cuboid;
    }

    public Location getUpCorner() {
        return this.upCorner;
    }

    public void setUpCorner(Location upCorner) {
        this.upCorner = upCorner;
    }

    public Location getDownCorner() {
        return this.downCorner;
    }

    public void setDownCorner(Location downCorner) {
        this.downCorner = downCorner;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public List<UUID> getCoOwners() {
        return this.coOwners;
    }

    public void setCoOwners(List<UUID> coOwners) {
        this.coOwners = coOwners;
    }

    public Cuboid getCuboid() {
        return this.cuboid;
    }

    public Area clone() {
        try {
            return (Area) super.clone();
        } catch (CloneNotSupportedException exception) {
            throw new Error(exception);
        }
    }

    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("upCorner", this.upCorner);
        data.put("downCorner", this.downCorner);
        data.put("name", this.name);
        data.put("owner", this.owner);
        data.put("coOwners", this.coOwners);
        data.put("cuboid", this.cuboid);
        return data;
    }

    public static Area deserialize(Map<String, Object> args) {
        return new Area((Location) args.get("upCorner"), (Location) args.get("downCorner"), (String) args.get("name"), (UUID) args.get("owner"), (List<UUID>) args.get("coOwners"), (Cuboid) args.get("cuboid"));
    }
}
