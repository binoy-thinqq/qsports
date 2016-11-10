package com.thinqq.qsports.persistence.dto;

public class TossInfoVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8821600583023748986L;

	Integer matchId;
	
	Integer tossWonBy;
	
	Integer electedTo;

	public Integer getTossWonBy() {
		return tossWonBy;
	}

	public void setTossWonBy(Integer tossWonBy) {
		this.tossWonBy = tossWonBy;
	}

	public Integer getElectedTo() {
		return electedTo;
	}

	public void setElectedTo(Integer electedTo) {
		this.electedTo = electedTo;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}
	
}
