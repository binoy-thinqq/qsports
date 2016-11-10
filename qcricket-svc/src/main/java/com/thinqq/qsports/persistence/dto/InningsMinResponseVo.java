package com.thinqq.qsports.persistence.dto;

import java.util.Date;

public class InningsMinResponseVo extends BaseResponseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8892796006103299938L;

	Integer inningsId;
	
	Integer matchId;
	  
	Integer teamId;
	
	Integer order;
	
	String teamName;
	
	Integer byes;
	
	Integer wide;
	
	Integer noBall;
	
	Integer legByes;
	
	Integer penaltyRuns;
	
	Integer totalRuns;
	
	Double overs;
	
	Integer revisedTarget;
	
	Integer wickets;
	
	Integer createdId;

	Date createdTime;

	Integer updatedId;

	Date updatedTime;
	
	public Integer getInningsId() {
		return inningsId;
	}

	public void setInningsId(Integer inningsId) {
		this.inningsId = inningsId;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getByes() {
		return byes;
	}

	public void setByes(Integer byes) {
		this.byes = byes;
	}

	public Integer getWide() {
		return wide;
	}

	public void setWide(Integer wide) {
		this.wide = wide;
	}

	public Integer getNoBall() {
		return noBall;
	}

	public void setNoBall(Integer noBall) {
		this.noBall = noBall;
	}

	public Integer getLegByes() {
		return legByes;
	}

	public void setLegByes(Integer legByes) {
		this.legByes = legByes;
	}

	public Integer getPenaltyRuns() {
		return penaltyRuns;
	}

	public void setPenaltyRuns(Integer penaltyRuns) {
		this.penaltyRuns = penaltyRuns;
	}

	public Integer getTotalRuns() {
		return totalRuns;
	}

	public void setTotalRuns(Integer totalRuns) {
		this.totalRuns = totalRuns;
	}

	public Double getOvers() {
		return overs;
	}

	public void setOvers(Double overs) {
		this.overs = overs;
	}

	public Integer getRevisedTarget() {
		return revisedTarget;
	}

	public void setRevisedTarget(Integer revisedTarget) {
		this.revisedTarget = revisedTarget;
	}

	public Integer getWickets() {
		return wickets;
	}

	public void setWickets(Integer wickets) {
		this.wickets = wickets;
	}

	public Integer getCreatedId() {
		return createdId;
	}

	public void setCreatedId(Integer createdId) {
		this.createdId = createdId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getUpdatedId() {
		return updatedId;
	}

	public void setUpdatedId(Integer updatedId) {
		this.updatedId = updatedId;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

}