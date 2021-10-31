package net.reikeb.maxicity.datas;

public class PlayerData {

    private int balance;
    private boolean hasPlayerJoined;
    private boolean isPlayerMuted;
    private String mutedReason;
    private boolean isPlayerNicked;
    private String playerNickname;
    private String playerTeam;
    private boolean socialSpy;
    private boolean teamChat;

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
}
