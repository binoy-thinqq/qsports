package com.thinqq.qsports.shared.match;

import java.util.List;

import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.QSportsResponseVo;

public class GetMatchDetailsResponseVo extends QSportsResponseVo {

	private static final long serialVersionUID = -1828143799026968719L;
	
	private String matchKey;
	private boolean matchNotFound;
	private int matchFormat;
	private NameAndKey team1;
	private List<NameAndKey> team1Players;
	private NameAndKey team2;
	private List<NameAndKey> team2Players;
	private NameAndKey matchOwnerKey;
	private boolean isTeam1EditAllowed;
	private boolean isTeam2EditAllowed;
	private boolean isMatchEditAllowed;
	private String date;
	private String ground;
	private String city;
	private String state;
	private String country;
	private List<String> umpires;
	private String pitch;
	private String weather;
	private int version;
	private boolean isEligibleToStart;
	
	public String getMatchKey() {
		return matchKey;
	}
	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}
	public int getMatchFormat() {
		return matchFormat;
	}
	public void setMatchFormat(int matchFormat) {
		this.matchFormat = matchFormat;
	}
	public NameAndKey getTeam2() {
		return team2;
	}
	public void setTeam2(NameAndKey team2) {
		this.team2 = team2;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getGround() {
		return ground;
	}
	public void setGround(String ground) {
		this.ground = ground;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<String> getUmpires() {
		return umpires;
	}
	public void setUmpires(List<String> umpires) {
		this.umpires = umpires;
	}
	public String getPitch() {
		return pitch;
	}
	public void setPitch(String pitch) {
		this.pitch = pitch;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public NameAndKey getTeam1() {
		return team1;
	}
	public void setTeam1(NameAndKey team1) {
		this.team1 = team1;
	}
	public boolean isMatchNotFound() {
		return matchNotFound;
	}
	public void setMatchNotFound(boolean matchNotFound) {
		this.matchNotFound = matchNotFound;
	}
	public boolean isTeam1EditAllowed() {
		return isTeam1EditAllowed;
	}
	public void setTeam1EditAllowed(boolean isTeam1EditAllowed) {
		this.isTeam1EditAllowed = isTeam1EditAllowed;
	}
	public boolean isTeam2EditAllowed() {
		return isTeam2EditAllowed;
	}
	public void setTeam2EditAllowed(boolean isTeam2EditAllowed) {
		this.isTeam2EditAllowed = isTeam2EditAllowed;
	}
	public boolean isMatchEditAllowed() {
		return isMatchEditAllowed;
	}
	public void setMatchEditAllowed(boolean isMatchEditAllowed) {
		this.isMatchEditAllowed = isMatchEditAllowed;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public List<NameAndKey> getTeam1Players() {
		return team1Players;
	}
	public void setTeam1Players(List<NameAndKey> team1Players) {
		this.team1Players = team1Players;
	}
	public List<NameAndKey> getTeam2Players() {
		return team2Players;
	}
	public void setTeam2Players(List<NameAndKey> team2Players) {
		this.team2Players = team2Players;
	}
	public NameAndKey getMatchOwnerKey() {
		return matchOwnerKey;
	}
	public void setMatchOwnerKey(NameAndKey matchOwnerKey) {
		this.matchOwnerKey = matchOwnerKey;
	}
	public boolean isEligibleToStart() {
		return isEligibleToStart;
	}
	public void setEligibleToStart(boolean isEligibleToStart) {
		this.isEligibleToStart = isEligibleToStart;
	}
	
}
