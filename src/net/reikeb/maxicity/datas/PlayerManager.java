package net.reikeb.maxicity.datas;

import net.reikeb.maxicity.MaxiCity;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.*;

public class PlayerManager {

    public static final HashMap<UUID, PlayerData> playerDataMap = new HashMap<UUID, PlayerData>();
    public static final HashMap<UUID, PermissionAttachment> playerPermissions = new HashMap<UUID, PermissionAttachment>();
    public MaxiCity plugin;

    public PlayerManager(MaxiCity plugin) {
        this.plugin = plugin;
    }

    public void saveHashMap() {
        List<String> sUUIDs = new ArrayList<>();
        Set<UUID> UUIDset = playerDataMap.keySet();
        for (UUID uuid : UUIDset) {
            sUUIDs.add(uuid.toString());
        }
        plugin.getConfig().set("playerDataMap", sUUIDs);
        plugin.saveConfig();
    }

    public void loadHashMap() {
        List<String> sUUIDs = plugin.getConfig().getStringList("playerDataMap");
        if (sUUIDs.isEmpty()) return;
        for (String sUUID : sUUIDs) {
            UUID uuid = UUID.fromString(sUUID);
            playerDataMap.put(uuid, playerDataMap.get(uuid));
        }
    }

    public void removePermissions(Player player) {
        if (!playerPermissions.containsKey(player.getUniqueId())) return;

        PermissionAttachment permission = playerPermissions.get(player.getUniqueId());
        if (permission != null) permission.remove();

        plugin.getServer().getPluginManager().removePermission(player.getUniqueId().toString());
    }

    public void disablePermissions() {
        for (PermissionAttachment permission : playerPermissions.values()) {
            permission.remove();
        }
        playerPermissions.clear();
    }

    public void loadPermissions(Player player) {
        PermissionAttachment permission = playerPermissions.get(player.getUniqueId());

        if (permission == null) {
            permission = player.addAttachment(plugin);
            playerPermissions.put(player.getUniqueId(), permission);
            permission.setPermission("team.one", getTeamGroup(player).equals(Group.TEAM_ONE));
            permission.setPermission("team.two", getTeamGroup(player).equals(Group.TEAM_TWO));
            permission.setPermission("team.three", getTeamGroup(player).equals(Group.TEAM_THREE));
            permission.setPermission("team.four", getTeamGroup(player).equals(Group.TEAM_FOUR));
            permission.setPermission("team.moderator", getTeamGroup(player).equals(Group.MODERATOR));
            permission.setPermission("team.admin", getTeamGroup(player).equals(Group.ADMIN));
            permission.setPermission("ee.balance", getTeamGroup(player).equals(Group.MODERATOR) || getTeamGroup(player).equals(Group.ADMIN));
            permission.setPermission("ee.socialspy", getTeamGroup(player).equals(Group.MODERATOR) || getTeamGroup(player).equals(Group.ADMIN));
            permission.setPermission("ee.hologram", getTeamGroup(player).equals(Group.ADMIN));
            permission.setPermission("ee.mute", getTeamGroup(player).equals(Group.MODERATOR) || getTeamGroup(player).equals(Group.ADMIN));
            permission.setPermission("ee.unmute", getTeamGroup(player).equals(Group.MODERATOR) || getTeamGroup(player).equals(Group.ADMIN));
            permission.setPermission("ee.showmute", getTeamGroup(player).equals(Group.MODERATOR) || getTeamGroup(player).equals(Group.ADMIN));
            permission.setPermission("ee.vanish", getTeamGroup(player).equals(Group.MODERATOR) || getTeamGroup(player).equals(Group.ADMIN));
            permission.setPermission("ee.broadcast", getTeamGroup(player).equals(Group.ADMIN));
            permission.setPermission("ee.chat", getTeamGroup(player).equals(Group.MODERATOR) || getTeamGroup(player).equals(Group.ADMIN));
            permission.setPermission("ee.reset", getTeamGroup(player).equals(Group.ADMIN));
            permission.setPermission("ee.city", getTeamGroup(player).equals(Group.ADMIN));
            permission.setPermission("ee.chatalways", getTeamGroup(player).equals(Group.MODERATOR) || getTeamGroup(player).equals(Group.ADMIN));
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
        if (playerDataMap.get(p.getUniqueId()) == null) return;
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

    public void setNewPlayer(OfflinePlayer p) {
        playerDataMap.put(p.getUniqueId(), new PlayerData(false, false, false, false, false));
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
        if (playerDataMap.get(p.getUniqueId()) != null) {
            return playerDataMap.get(p.getUniqueId()).isPlayerMuted();
        } else {
            return false;
        }
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
        if (playerDataMap.get(p.getUniqueId()) != null) {
            return playerDataMap.get(p.getUniqueId()).isPlayerNicked();
        } else {
            return false;
        }
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

    public void setPlayerTeamList(OfflinePlayer p, String team) {
        playerDataMap.get(p.getUniqueId()).setPlayerTeamList(team);
    }

    public String getPlayerTeamList(OfflinePlayer p) {
        if (playerDataMap.get(p.getUniqueId()) != null) {
            return playerDataMap.get(p.getUniqueId()).getPlayerTeamList();
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

    /**
     * Player reply methods
     */
    public void setChatPrivateReply(OfflinePlayer p, OfflinePlayer target) {
        if ((playerDataMap.get(p.getUniqueId()) == null) || (!target.isOnline())) return;
        playerDataMap.get(p.getUniqueId()).setChatPlayerReply((Player) target);
    }

    public Player getChatPrivateReply(OfflinePlayer p) {
        if (playerDataMap.get(p.getUniqueId()) != null) {
            return playerDataMap.get(p.getUniqueId()).getChatPlayerReply();
        } else {
            return null;
        }
    }

    /**
     * Group methods
     */
    public void setTeamGroup(OfflinePlayer p, Group teamGroup) {
        if (playerDataMap.get(p.getUniqueId()) == null) return;
        playerDataMap.get(p.getUniqueId()).setTeamGroup(teamGroup);
    }

    public Group getTeamGroup(OfflinePlayer p) {
        if (playerDataMap.get(p.getUniqueId()) != null) {
            return playerDataMap.get(p.getUniqueId()).getTeamGroup();
        } else {
            return null;
        }
    }
}
