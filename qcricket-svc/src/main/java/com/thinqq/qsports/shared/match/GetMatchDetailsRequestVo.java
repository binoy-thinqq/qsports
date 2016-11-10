package com.thinqq.qsports.shared.match;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class GetMatchDetailsRequestVo extends QSportsRequestVo {

	private static final long serialVersionUID = -7473456090194541965L;
	
	private String matchKey;

	public String getMatchKey() {
		return matchKey;
	}

	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}
}
