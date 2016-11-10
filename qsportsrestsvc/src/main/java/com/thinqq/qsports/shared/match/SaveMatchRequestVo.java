package com.thinqq.qsports.shared.match;

import java.util.Date;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class SaveMatchRequestVo extends QSportsRequestVo {

	private static final long serialVersionUID = 5123672855484765338L;

	private String matchKey;
	private String ownTeamKey;
	private String opponentTeamKey;
	private Date matchDate;
	private int matchFormat;
	private String groundName;
	private String city;
	private String state;
	private String countryCode;
	
	public String getMatchKey() {
		return matchKey;
	}
	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}
	public String getOwnTeamKey() {
		return ownTeamKey;
	}
	public void setOwnTeamKey(String ownTeamKey) {
		this.ownTeamKey = ownTeamKey;
	}
	public String getOpponentTeamKey() {
		return opponentTeamKey;
	}
	public void setOpponentTeamKey(String opponentTeamKey) {
		this.opponentTeamKey = opponentTeamKey;
	}
	public Date getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}
	public int getMatchFormat() {
		return matchFormat;
	}
	public void setMatchFormat(int matchFormat) {
		this.matchFormat = matchFormat;
	}
	public String getGroundName() {
		return groundName;
	}
	public void setGroundName(String groundName) {
		this.groundName = groundName;
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
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
}
