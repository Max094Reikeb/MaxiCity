package net.reikeb.maxicity.datas;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerData {

    private int balance;
    private boolean hasPlayerJoined;
    private boolean isPlayerMuted;
    private String mutedReason;
    private boolean isPlayerNicked;
    private String playerNickname;
    private String playerTeam;
    private String playerTeamList;
    private boolean socialSpy;
    private boolean teamChat;
    private Player chatPlayerReply;
    private Group teamGroup;
    private boolean isPlayerVanished;
    private Area area;
    private Location loc1;
    private Location loc2;

    public PlayerData(boolean hasPlayerJoined, boolean isPlayerMuted, boolean isPlayerNicked, boolean socialSpy,
                      boolean teamChat, boolean isPlayerVanished) {
        this.hasPlayerJoined = hasPlayerJoined;
        this.isPlayerMuted = isPlayerMuted;
        this.isPlayerNicked = isPlayerNicked;
        this.socialSpy = socialSpy;
        this.teamChat = teamChat;
        this.isPlayerVanished = isPlayerVanished;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean hasPlayerJoined() {
        return this.hasPlayerJoined;
    }

    public void setPlayerJoined(boolean joined) {
        this.hasPlayerJoined = joined;
    }

    public boolean isPlayerMuted() {
        return this.isPlayerMuted;
    }

    public void setPlayerMuted(boolean muted) {
        this.isPlayerMuted = muted;
    }

    public String getMutedReason() {
        return this.mutedReason;
    }

    public void setMutedReason(String mutedReason) {
        this.mutedReason = mutedReason;
    }

    public boolean isPlayerNicked() {
        return this.isPlayerNicked;
    }

    public void setPlayerNicked(boolean playerNicked) {
        this.isPlayerNicked = playerNicked;
    }

    public String getPlayerNickname() {
        return this.playerNickname;
    }

    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public String getPlayerTeam() {
        return this.playerTeam;
    }

    public void setPlayerTeam(String playerTeam) {
        this.playerTeam = playerTeam;
    }

    public String getPlayerTeamList() {
        return this.playerTeamList;
    }

    public void setPlayerTeamList(String playerTeamList) {
        this.playerTeamList = playerTeamList;
    }

    public boolean getSocialSpy() {
        return this.socialSpy;
    }

    public void setSocialSpy(boolean socialSpy) {
        this.socialSpy = socialSpy;
    }

    public boolean getTeamChat() {
        return this.teamChat;
    }

    public void setTeamChat(boolean teamChat) {
        this.teamChat = teamChat;
    }

    public Player getChatPlayerReply() {
        return this.chatPlayerReply;
    }

    public void setChatPlayerReply(Player chatPlayerReply) {
        this.chatPlayerReply = chatPlayerReply;
    }

    public Group getTeamGroup() {
        return this.teamGroup;
    }

    public void setTeamGroup(Group teamGroup) {
        this.teamGroup = teamGroup;
    }

    public boolean isPlayerVanished() {
        return this.isPlayerVanished;
    }

    public void setPlayerVanished(boolean isPlayerVanished) {
        this.isPlayerVanished = isPlayerVanished;
    }

    public Area getArea() {
        return this.area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Location getLoc1() {
        return this.loc1;
    }

    public void setLoc1(Location loc1) {
        this.loc1 = loc1;
    }

    public Location getLoc2() {
        return this.loc2;
    }

    public void setLoc2(Location loc2) {
        this.loc2 = loc2;
    }
}
