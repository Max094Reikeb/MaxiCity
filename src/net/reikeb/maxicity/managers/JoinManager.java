package net.reikeb.maxicity.managers;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class JoinManager {

    private HashMap<UUID, Boolean> joinedPlayer = new HashMap<UUID, Boolean>();

    public MaxiCity plugin;

    public JoinManager(MaxiCity plugin) {
        this.plugin = plugin;
    }

    public void saveJoinedPlayerFile() throws FileNotFoundException, IOException {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            File file = new File("datas/joined_players.dat");
            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            UUID uuid = p.getUniqueId();

            if (joinedPlayer.get(uuid) != null) {
                joinedPlayer.put(uuid, joinedPlayer.get(uuid));
            }

            try {
                output.writeObject(joinedPlayer);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadJoinedPlayerFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("datas/joined_players.dat");

        if (file != null) {
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObjects = input.readObject();
            input.close();

            if (!(readObjects instanceof HashMap)) {
                throw new IOException("Data is not hashmap!");
            }

            joinedPlayer = (HashMap<UUID, Boolean>) readObjects;
            for (UUID key : joinedPlayer.keySet()) {
                joinedPlayer.put(key, joinedPlayer.get(key));
            }
        }
    }

    /**
     * Set and get methods
     */
    public void setJoinedPlayer(OfflinePlayer p, boolean status) {
        joinedPlayer.put(p.getUniqueId(), status);
    }

    public boolean hasPlayerJoined(OfflinePlayer p) {
        if (joinedPlayer.get(p.getUniqueId()) != null) {
            return joinedPlayer.get(p.getUniqueId());
        } else {
            return false;
        }
    }
}
