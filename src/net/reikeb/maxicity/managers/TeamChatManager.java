package net.reikeb.maxicity.managers;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TeamChatManager {

    private HashMap<UUID, Boolean> teamChat = new HashMap<UUID, Boolean>();

    public MaxiCity plugin;

    public TeamChatManager(MaxiCity plugin) {
        this.plugin = plugin;
    }

    public void saveTeamChatFile() throws FileNotFoundException, IOException {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            File file = new File("datas/team_chat.dat");
            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            UUID uuid = p.getUniqueId();

            if (teamChat.get(uuid) != null) {
                teamChat.put(uuid, teamChat.get(uuid));
            }

            try {
                output.writeObject(teamChat);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadTeamChatFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("datas/team_chat.dat");

        if (file != null) {
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObjects = input.readObject();
            input.close();

            if (!(readObjects instanceof HashMap)) {
                throw new IOException("Data is not hashmap!");
            }

            teamChat = (HashMap<UUID, Boolean>) readObjects;
            for (UUID key : teamChat.keySet()) {
                teamChat.put(key, teamChat.get(key));
            }
        }
    }

    /**
     * Set and get methods
     */
    public void setPlayerTeamChat(OfflinePlayer p, boolean status) {
        teamChat.put(p.getUniqueId(), status);
    }

    public boolean getPlayerTeamChat(OfflinePlayer p) {
        if (teamChat.get(p.getUniqueId()) != null) {
            return teamChat.get(p.getUniqueId());
        } else {
            return false;
        }
    }
}
