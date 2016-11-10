package com.thinqq.qsports.persistence.dto;

public class CricketTeamMinStatsVo extends BaseResponseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3362728842353055645L;
	private Integer  cricketFormat;
	Integer won;
	Integer lost;
	Integer matches;
	
	public Integer getWon() {
		return won;
	}
	public void setWon(Integer won) {
		this.won = won;
	}
	public Integer getLost() {
		return lost;
	}
	public void setLost(Integer lost) {
		this.lost = lost;
	}
	public Integer getMatches() {
		return matches;
	}
	public void setMatches(Integer matches) {
		this.matches = matches;
	}
	public Integer getCricketFormat() {
		return cricketFormat;
	}
	public void setCricketFormat(Integer cricketFormat) {
		this.cricketFormat = cricketFormat;
	}
	

}
