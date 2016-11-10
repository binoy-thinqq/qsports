package com.thinqq.qsports.client.cricket;

public class PlayerProfileMinData {

private String displayName;
private String profileToken;
private Long userId;
private boolean isCaptain;
private boolean isWicketKeeper;
private boolean isPlaying;
private boolean isInStrike;


public PlayerProfileMinData(String displayName, String profileToken,
		Long userId) {
	super();
	this.displayName = displayName;
	this.profileToken = profileToken;
	this.userId = userId;
}
public PlayerProfileMinData() {
	super();
}
public PlayerProfileMinData(String displayName, String profileToken,
		Long userId, boolean isCaptain, boolean isWicketKeeper,
		boolean isPlaying, boolean isInStrike) {
	super();
	this.displayName = displayName;
	this.profileToken = profileToken;
	this.userId = userId;
	this.isCaptain = isCaptain;
	this.isWicketKeeper = isWicketKeeper;
	this.isPlaying = isPlaying;
	this.isInStrike = isInStrike;
}
public String getDisplayName() {
	return displayName;
}
public void setDisplayName(String displayName) {
	this.displayName = displayName;
}
public String getProfileToken() {
	return profileToken;
}
public void setProfileToken(String profileToken) {
	this.profileToken = profileToken;
}
public Long getUserId() {
	return userId;
}
public void setUserId(Long userId) {
	this.userId = userId;
}
public boolean isCaptain() {
	return isCaptain;
}
public void setCaptain(boolean isCaptain) {
	this.isCaptain = isCaptain;
}
public boolean isWicketKeeper() {
	return isWicketKeeper;
}
public void setWicketKeeper(boolean isWicketKeeper) {
	this.isWicketKeeper = isWicketKeeper;
}
public boolean isPlaying() {
	return isPlaying;
}
public void setPlaying(boolean isPlaying) {
	this.isPlaying = isPlaying;
}
public boolean isInStrike() {
	return isInStrike;
}
public void setInStrike(boolean isInStrike) {
	this.isInStrike = isInStrike;
}
}
