package net.reikeb.maxicity.managers;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NickManager {

    private HashMap<UUID, Boolean> nickedPlayer = new HashMap<UUID, Boolean>();
    private HashMap<UUID, String> playerNickname = new HashMap<UUID, String>();

    public MaxiCity plugin;

    public NickManager(MaxiCity plugin) {
        this.plugin = plugin;
    }

    public void saveNickedPlayersFiles() throws FileNotFoundException, IOException {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            File file = new File("datas/nicked_players.dat");
            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            UUID uuid = p.getUniqueId();

            if (nickedPlayer.get(uuid) != null) {
                nickedPlayer.put(uuid, nickedPlayer.get(uuid));
            }

            try {
                output.writeObject(nickedPlayer);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveNicknamesFile() throws FileNotFoundException, IOException {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            File file = new File("datas/player_nicknames.dat");
            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            UUID uuid = p.getUniqueId();

            if (playerNickname.get(uuid) != null) {
                playerNickname.put(uuid, playerNickname.get(uuid));
            }

            try {
                output.writeObject(playerNickname);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadNickedPlayersFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("datas/nicked_players.dat");

        if (file != null) {
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObjects = input.readObject();
            input.close();

            if (!(readObjects instanceof HashMap)) {
                throw new IOException("Data is not hashmap!");
            }

            nickedPlayer = (HashMap<UUID, Boolean>) readObjects;
            for (UUID key : nickedPlayer.keySet()) {
                nickedPlayer.put(key, nickedPlayer.get(key));
            }
        }
    }

    public void loadNicknamesFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("datas/player_nicknames.dat");

        if (file != null) {
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObjects = input.readObject();
            input.close();

            if (!(readObjects instanceof HashMap)) {
                throw new IOException("Data is not hashmap!");
            }

            playerNickname = (HashMap<UUID, String>) readObjects;
            for (UUID key : playerNickname.keySet()) {
                playerNickname.put(key, playerNickname.get(key));
            }
        }
    }

    /**
     * Set and get methods
     */
    public void setPlayerNicked(OfflinePlayer p, String nickname) {
        nickedPlayer.put(p.getUniqueId(), true);
        playerNickname.put(p.getUniqueId(), nickname);
    }

    public void removePlayerNickname(OfflinePlayer p) {
        nickedPlayer.put(p.getUniqueId(), false);
        playerNickname.put(p.getUniqueId(), "");
    }

    public boolean isPlayerNicked(OfflinePlayer p) {
        if (nickedPlayer.get(p.getUniqueId()) != null) {
            return nickedPlayer.get(p.getUniqueId());
        } else {
            return false;
        }
    }

    public String getPlayerNickname(OfflinePlayer p) {
        if (playerNickname.get(p.getUniqueId()) != null) {
            return playerNickname.get(p.getUniqueId());
        } else {
            return p.getName();
        }
    }
}
