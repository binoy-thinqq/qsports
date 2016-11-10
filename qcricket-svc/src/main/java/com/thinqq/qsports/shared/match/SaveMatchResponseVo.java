package com.thinqq.qsports.shared.match;

import com.thinqq.qsports.shared.QSportsResponseVo;

public class SaveMatchResponseVo extends QSportsResponseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2426512948931619420L;
	
	private boolean matchSavedSuccessfully;
	private String matchKey;
	
	public boolean isMatchSavedSuccessfully() {
		return matchSavedSuccessfully;
	}
	public void setMatchSavedSuccessfully(boolean matchSavedSuccessfully) {
		this.matchSavedSuccessfully = matchSavedSuccessfully;
	}
	public String getMatchKey() {
		return matchKey;
	}
	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}
	

}
