package net.reikeb.maxicity.misc;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.UUID;

public class Maps {

    private HashMap<UUID, Integer> balance = new HashMap<UUID, Integer>();
    private HashMap<UUID, Boolean> joinedPlayer = new HashMap<UUID, Boolean>();
    private HashMap<UUID, Boolean> mutedPlayer = new HashMap<UUID, Boolean>();
    private HashMap<UUID, String> mutedReason = new HashMap<UUID, String>();
    private HashMap<UUID, Boolean> nickedPlayer = new HashMap<UUID, Boolean>();
    private HashMap<UUID, String> playerNickname = new HashMap<UUID, String>();
    private HashMap<UUID, String> playerTeam = new HashMap<UUID, String>();
    private HashMap<UUID, Boolean> socialSpy = new HashMap<UUID, Boolean>();
    private HashMap<UUID, Boolean> teamChat = new HashMap<UUID, Boolean>();

    public MaxiCity plugin;

    public Maps(MaxiCity plugin) {
        this.plugin = plugin;
    }

    /**
     * Balance methods
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

    /**
     * Join methods
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

    /**
     * Mute methods
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

    /**
     * Nick methods
     */
    public void setPlayerNickname(OfflinePlayer p, String nickname) {
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

    /**
     * Team methods
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

    /**
     * SocialSpy methods
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

    /**
     * TeamChat methods
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
