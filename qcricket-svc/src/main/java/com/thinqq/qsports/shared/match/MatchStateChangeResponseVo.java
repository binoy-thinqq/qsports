package com.thinqq.qsports.shared.match;

import com.thinqq.qsports.shared.QSportsResponseVo;

public class MatchStateChangeResponseVo extends QSportsResponseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7811328882282848303L;
	private boolean matchStateChangedSuccessfully;

	public boolean isMatchStateChangedSuccessfully() {
		return matchStateChangedSuccessfully;
	}

	public void setMatchStateChangedSuccessfully(
			boolean matchStateChangedSuccessfully) {
		this.matchStateChangedSuccessfully = matchStateChangedSuccessfully;
	}

}
