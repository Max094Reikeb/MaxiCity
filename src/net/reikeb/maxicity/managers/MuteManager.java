package net.reikeb.maxicity.managers;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class MuteManager {

    private HashMap<UUID, Boolean> mutedPlayer = new HashMap<UUID, Boolean>();
    private HashMap<UUID, String> mutedReason = new HashMap<UUID, String>();

    public MaxiCity plugin;

    public MuteManager(MaxiCity plugin) {
        this.plugin = plugin;
    }

    public void saveMutedPlayersFile() throws FileNotFoundException, IOException {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            File file = new File("datas/muted_players.dat");
            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            UUID uuid = p.getUniqueId();

            if (mutedPlayer.get(uuid) != null) {
                mutedPlayer.put(uuid, mutedPlayer.get(uuid));
            }

            try {
                output.writeObject(mutedPlayer);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveMutedReasonsFile() throws FileNotFoundException, IOException {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            File file = new File("datas/muted_reasons.dat");
            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            UUID uuid = p.getUniqueId();

            if (mutedReason.get(uuid) != null) {
                mutedReason.put(uuid, mutedReason.get(uuid));
            }

            try {
                output.writeObject(mutedReason);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadMutedPlayersFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("datas/muted_players.dat");

        if (file != null) {
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObjects = input.readObject();
            input.close();

            if (!(readObjects instanceof HashMap)) {
                throw new IOException("Data is not hashmap!");
            }

            mutedPlayer = (HashMap<UUID, Boolean>) readObjects;
            for (UUID key : mutedPlayer.keySet()) {
                mutedPlayer.put(key, mutedPlayer.get(key));
            }
        }
    }

    public void loadMutedReasonsFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("datas/muted_reasons.dat");

        if (file != null) {
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObjects = input.readObject();
            input.close();

            if (!(readObjects instanceof HashMap)) {
                throw new IOException("Data is not hashmap!");
            }

            mutedReason = (HashMap<UUID, String>) readObjects;
            for (UUID key : mutedReason.keySet()) {
                mutedReason.put(key, mutedReason.get(key));
            }
        }
    }

    /**
     * Set and get methods
     */
    public void mutePlayer(OfflinePlayer p, String reason) {
        mutedPlayer.put(p.getUniqueId(), true);
        mutedReason.put(p.getUniqueId(), reason);
    }

    public void unmutePlayer(OfflinePlayer p) {
        mutedPlayer.put(p.getUniqueId(), false);
        mutedReason.put(p.getUniqueId(), "");
    }

    public boolean isPlayerMuted(OfflinePlayer p) {
        if (mutedPlayer.get(p.getUniqueId()) != null) {
            return mutedPlayer.get(p.getUniqueId());
        } else {
            return false;
        }
    }

    public String getMutedPlayerReason(OfflinePlayer p) {
        if (mutedReason.get(p.getUniqueId()) != null) {
            return mutedReason.get(p.getUniqueId());
        } else {
            return p.getName();
        }
    }
}
