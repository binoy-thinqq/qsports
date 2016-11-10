package com.thinqq.qsports.shared.userprofile;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class CricketStatisticsRequestVo extends QSportsRequestVo {

	private static final long serialVersionUID = -8691026876225430556L;

	private String cricketProfileKey;
	
	private boolean isMinimumStatsReq;

	public boolean isMinimumStatsReq() {
		return isMinimumStatsReq;
	}

	public void setMinimumStatsReq(boolean isMinimumStatsReq) {
		this.isMinimumStatsReq = isMinimumStatsReq;
	}

	public String getCricketProfileKey() {
		return cricketProfileKey;
	}

	public void setCricketProfileKey(String cricketProfileKey) {
		this.cricketProfileKey = cricketProfileKey;
	}
	
}
