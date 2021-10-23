package net.reikeb.maxicity.managers;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class BalanceManager {

    private HashMap<UUID, Integer> balance = new HashMap<UUID, Integer>();

    public MaxiCity plugin;

    public BalanceManager(MaxiCity plugin) {
        this.plugin = plugin;
    }

    public void saveBalanceFile() throws FileNotFoundException, IOException {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            File file = new File("datas/balances.dat");
            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            UUID uuid = p.getUniqueId();

            if (balance.get(uuid) != null) {
                balance.put(uuid, balance.get(uuid));
            }

            try {
                output.writeObject(balance);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadBalanceFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("datas/balances.dat");

        if (file != null) {
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObjects = input.readObject();
            input.close();

            if (!(readObjects instanceof HashMap)) {
                throw new IOException("Data is not hashmap!");
            }

            balance = (HashMap<UUID, Integer>) readObjects;
            for (UUID key : balance.keySet()) {
                balance.put(key, balance.get(key));
            }
        }
    }

    /**
     * Create, add, remove set and get methods
     */
    public void addBalanceToPlayer(OfflinePlayer p, int amount) {
        if (balance.get(p.getUniqueId()) != null) {
            balance.put(p.getUniqueId(), balance.get(p.getUniqueId()) + amount);
        } else {
            balance.put(p.getUniqueId(), amount);
        }
    }

    public void removeBalanceFromPlayer(OfflinePlayer p, int amount) {
        if (balance.get(p.getUniqueId()) != null) {
            balance.put(p.getUniqueId(), balance.get(p.getUniqueId()) - amount);
        }
    }

    public void setPlayerBalance(OfflinePlayer p, int amount) {
        balance.put(p.getUniqueId(), amount);
    }

    public int getPlayerBalance(OfflinePlayer p) {
        if (balance.get(p.getUniqueId()) != null) {
            return balance.get(p.getUniqueId());
        } else {
            return 0;
        }
    }
}
