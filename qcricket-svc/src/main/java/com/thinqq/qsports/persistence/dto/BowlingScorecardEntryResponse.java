package com.thinqq.qsports.persistence.dto;

public class BowlingScorecardEntryResponse {
	
	private Integer bowlingEntryId;
	
	private String bowlerName;
	
	private Integer bowlerId;
	
	private Integer matchPlayerId;
	
	private Double overs;
	
	private Integer runs;
	
	private Integer noBalls;
	
	private Integer wide;
	
	private Integer maiden;

	public Integer getBowlerId() {
		return bowlerId;
	}

	public void setBowlerId(Integer bowlerId) {
		this.bowlerId = bowlerId;
	}

	public Integer getBowlingEntryId() {
		return bowlingEntryId;
	}

	public void setBowlingEntryId(Integer bowlingEntryId) {
		this.bowlingEntryId = bowlingEntryId;
	}

	public String getBowlerName() {
		return bowlerName;
	}

	public void setBowlerName(String bowlerName) {
		this.bowlerName = bowlerName;
	}

	public Integer getMatchPlayerId() {
		return matchPlayerId;
	}

	public void setMatchPlayerId(Integer matchPlayerId) {
		this.matchPlayerId = matchPlayerId;
	}

	public Double getOvers() {
		return overs;
	}

	public void setOvers(Double overs) {
		this.overs = overs;
	}

	public Integer getRuns() {
		return runs;
	}

	public void setRuns(Integer runs) {
		this.runs = runs;
	}

	public Integer getNoBalls() {
		return noBalls;
	}

	public void setNoBalls(Integer noBalls) {
		this.noBalls = noBalls;
	}

	public Integer getWide() {
		return wide;
	}

	public void setWide(Integer wide) {
		this.wide = wide;
	}

	public Integer getMaiden() {
		return maiden;
	}

	public void setMaiden(Integer maiden) {
		this.maiden = maiden;
	}
}
