package net.reikeb.maxicity.managers;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class PlayerTeamManager {

    private HashMap<UUID, String> playerTeam = new HashMap<UUID, String>();

    public MaxiCity plugin;

    public PlayerTeamManager(MaxiCity plugin) {
        this.plugin = plugin;
    }

    public void saveTeamFile() throws FileNotFoundException, IOException {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            File file = new File("datas/teams.dat");
            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            UUID uuid = p.getUniqueId();

            if (playerTeam.get(uuid) != null) {
                playerTeam.put(uuid, playerTeam.get(uuid));
            }

            try {
                output.writeObject(playerTeam);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadTeamFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("datas/teams.dat");

        if (file != null) {
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObjects = input.readObject();
            input.close();

            if (!(readObjects instanceof HashMap)) {
                throw new IOException("Data is not hashmap!");
            }

            playerTeam = (HashMap<UUID, String>) readObjects;
            for (UUID key : playerTeam.keySet()) {
                playerTeam.put(key, playerTeam.get(key));
            }
        }
    }

    /**
     * Set and get methods
     */
    public void setPlayerTeam(OfflinePlayer p, String team) {
        playerTeam.put(p.getUniqueId(), team);
    }

    public String getPlayerTeam(OfflinePlayer p) {
        if (playerTeam.get(p.getUniqueId()) != null) {
            return playerTeam.get(p.getUniqueId());
        } else {
            return "";
        }
    }
}
