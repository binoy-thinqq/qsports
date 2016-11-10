package com.thinqq.qsports.persistence.dto;

public class ReviseTargetRequestVo extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5569826410295002923L;

	Integer matchId;
	
	double revisedOvers;
	
	int revisedTarget;
	
	String revisedReason;

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public double getRevisedOvers() {
		return revisedOvers;
	}

	public void setRevisedOvers(double revisedOvers) {
		this.revisedOvers = revisedOvers;
	}

	public int getRevisedTarget() {
		return revisedTarget;
	}

	public void setRevisedTarget(int revisedTarget) {
		this.revisedTarget = revisedTarget;
	}

	public String getRevisedReason() {
		return revisedReason;
	}

	public void setRevisedReason(String revisedReason) {
		this.revisedReason = revisedReason;
	}
	
}
