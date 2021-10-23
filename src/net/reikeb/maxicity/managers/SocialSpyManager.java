package net.reikeb.maxicity.managers;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class SocialSpyManager {

    private HashMap<UUID, Boolean> socialSpy = new HashMap<UUID, Boolean>();

    public MaxiCity plugin;

    public SocialSpyManager(MaxiCity plugin) {
        this.plugin = plugin;
    }

    public void saveSocialSpyFile() throws FileNotFoundException, IOException {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            File file = new File("datas/social_spy.dat");
            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            UUID uuid = p.getUniqueId();

            if (socialSpy.get(uuid) != null) {
                socialSpy.put(uuid, socialSpy.get(uuid));
            }

            try {
                output.writeObject(socialSpy);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadSocialSpyFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("datas/social_spy.dat");

        if (file != null) {
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObjects = input.readObject();
            input.close();

            if (!(readObjects instanceof HashMap)) {
                throw new IOException("Data is not hashmap!");
            }

            socialSpy = (HashMap<UUID, Boolean>) readObjects;
            for (UUID key : socialSpy.keySet()) {
                socialSpy.put(key, socialSpy.get(key));
            }
        }
    }

    /**
     * Set and get methods
     */
    public void setPlayerSocialSpy(OfflinePlayer p, boolean status) {
        socialSpy.put(p.getUniqueId(), status);
    }

    public boolean getPlayerSocialSpy(OfflinePlayer p) {
        if (socialSpy.get(p.getUniqueId()) != null) {
            return socialSpy.get(p.getUniqueId());
        } else {
            return false;
        }
    }
}
