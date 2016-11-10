package com.thinqq.qsports.persistence.dto;

import java.util.List;

public class MatchInningsDetailsResponse extends BaseResponseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6503096877012161419L;
	
	Integer matchId;
	
	List<InningsResponseVo> innings;
	
	boolean isEditable;

	public List<InningsResponseVo> getInnings() {
		return innings;
	}

	public void setInnings(List<InningsResponseVo> innings) {
		this.innings = innings;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	

}
