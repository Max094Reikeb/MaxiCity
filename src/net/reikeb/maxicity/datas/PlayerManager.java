package net.reikeb.maxicity.datas;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.OfflinePlayer;

import java.util.*;

public class PlayerManager {

    public final HashMap<UUID, PlayerData> playerDataMap = new HashMap<UUID, PlayerData>();
    public MaxiCity plugin;

    public PlayerManager(MaxiCity plugin) {
        this.plugin = plugin;
    }

    public void saveHashMap() {
        List<String> suuids = new ArrayList<>();
        Set<UUID> uuidSet = playerDataMap.keySet();
        for(UUID uuid : uuidSet) {
            suuids.add(uuid.toString());
        }
        plugin.getConfig().set("playerDataMap", suuids);
        plugin.saveConfig();
    }

    public void loadHashMap() {
        List<String> suuids = plugin.getConfig().getStringList("playerDataMap");
        if (suuids.isEmpty()) return;
        for (String suuid : suuids) {
            UUID uuid = UUID.fromString(suuid);
            playerDataMap.put(uuid, playerDataMap.get(uuid));
        }
    }

    public void addBalanceToPlayer(OfflinePlayer p, int amount) {
        setPlayerBalance(p, (getPlayerBalance(p) + amount));
    }

    public void removeBalanceFromPlayer(OfflinePlayer p, int amount) {
        if (playerDataMap.get(p.getUniqueId()).getBalance() >= amount) {
            setPlayerBalance(p, (getPlayerBalance(p) - amount));
        }
    }

    public void setPlayerBalance(OfflinePlayer p, int amount) {
        playerDataMap.get(p.getUniqueId()).setBalance(amount);
    }

    public int getPlayerBalance(OfflinePlayer p) {
        if (playerDataMap.get(p.getUniqueId()) != null) {
            return playerDataMap.get(p.getUniqueId()).getBalance();
        } else {
            return 0;
        }
    }

    /**
     * Join methods
     */
    public void setJoinedPlayer(OfflinePlayer p, boolean status) {
        playerDataMap.get(p.getUniqueId()).setPlayerJoined(status);
    }

    public boolean hasPlayerJoined(OfflinePlayer p) {
        if (playerDataMap.get(p.getUniqueId()) != null) {
            return playerDataMap.get(p.getUniqueId()).hasPlayerJoined();
        } else {
            return false;
        }
    }

    /**
     * Mute methods
     */
    public void mutePlayer(OfflinePlayer p, String reason) {
        playerDataMap.get(p.getUniqueId()).setPlayerMuted(true);
        playerDataMap.get(p.getUniqueId()).setMutedReason(reason);
    }

    public void unmutePlayer(OfflinePlayer p) {
        playerDataMap.get(p.getUniqueId()).setPlayerMuted(false);
        playerDataMap.get(p.getUniqueId()).setMutedReason("");
    }

    public boolean isPlayerMuted(OfflinePlayer p) {
        return playerDataMap.get(p.getUniqueId()).isPlayerMuted();
    }

    public String getMutedPlayerReason(OfflinePlayer p) {
        if (playerDataMap.get(p.getUniqueId()) != null) {
            return playerDataMap.get(p.getUniqueId()).getMutedReason();
        } else {
            return "";
        }
    }

    /**
     * Nick methods
     */
    public void setPlayerNickname(OfflinePlayer p, String nickname) {
        playerDataMap.get(p.getUniqueId()).setPlayerNicked(true);
        playerDataMap.get(p.getUniqueId()).setPlayerNickname(nickname);
    }

    public void removePlayerNickname(OfflinePlayer p) {
        playerDataMap.get(p.getUniqueId()).setPlayerNicked(false);
        playerDataMap.get(p.getUniqueId()).setPlayerNickname("");
    }

    public boolean isPlayerNicked(OfflinePlayer p) {
        return playerDataMap.get(p.getUniqueId()).isPlayerNicked();
    }

    public String getPlayerNickname(OfflinePlayer p) {
        if (playerDataMap.get(p.getUniqueId()) != null) {
            return playerDataMap.get(p.getUniqueId()).getPlayerNickname();
        } else {
            return p.getName();
        }
    }

    /**
     * Team methods
     */
    public void setPlayerTeam(OfflinePlayer p, String team) {
        playerDataMap.get(p.getUniqueId()).setPlayerTeam(team);
    }

    public String getPlayerTeam(OfflinePlayer p) {
        if (playerDataMap.get(p.getUniqueId()) != null) {
            return playerDataMap.get(p.getUniqueId()).getPlayerTeam();
        } else {
            return "";
        }
    }

    /**
     * SocialSpy methods
     */
    public void setPlayerSocialSpy(OfflinePlayer p, boolean status) {
        if (playerDataMap.get(p.getUniqueId()) == null) return;
        playerDataMap.get(p.getUniqueId()).setSocialSpy(status);
    }

    public boolean getPlayerSocialSpy(OfflinePlayer p) {
        if (playerDataMap.get(p.getUniqueId()) != null) {
            return playerDataMap.get(p.getUniqueId()).getSocialSpy();
        } else {
            return false;
        }
    }

    /**
     * TeamChat methods
     */
    public void setPlayerTeamChat(OfflinePlayer p, boolean status) {
        if (playerDataMap.get(p.getUniqueId()) == null) return;
        playerDataMap.get(p.getUniqueId()).setTeamChat(status);
    }

    public boolean getPlayerTeamChat(OfflinePlayer p) {
        if (playerDataMap.get(p.getUniqueId()) != null) {
            return playerDataMap.get(p.getUniqueId()).getTeamChat();
        } else {
            return false;
        }
    }
}
