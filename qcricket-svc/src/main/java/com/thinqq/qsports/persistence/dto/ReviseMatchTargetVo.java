package com.thinqq.qsports.persistence.dto;

import com.thinqq.qsports.persistence.validation.annotation.Overs;
import com.thinqq.qsports.persistence.validation.annotation.Size;

public class ReviseMatchTargetVo extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7870828082869848755L;
	private Integer revisedTarget;
	@Overs
	private Double revisedOvers;
	@Size(minLength = 10, length = 500)
	private String revisedReason;
	private Integer matchId;
	
	public Integer getRevisedTarget() {
		return revisedTarget;
	}
	public void setRevisedTarget(Integer revisedTarget) {
		this.revisedTarget = revisedTarget;
	}
	public Double getRevisedOvers() {
		return revisedOvers;
	}
	public void setRevisedOvers(Double revisedOvers) {
		this.revisedOvers = revisedOvers;
	}
	public String getRevisedReason() {
		return revisedReason;
	}
	public void setRevisedReason(String revisedReason) {
		this.revisedReason = revisedReason;
	}
	public Integer getMatchId() {
		return matchId;
	}
	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}
	
	
}
