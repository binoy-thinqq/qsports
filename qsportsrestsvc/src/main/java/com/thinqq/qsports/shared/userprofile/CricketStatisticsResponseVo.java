package com.thinqq.qsports.shared.userprofile;

import java.util.List;

import com.thinqq.qsports.shared.QSportsResponseVo;

public class CricketStatisticsResponseVo extends QSportsResponseVo {

	private static final long serialVersionUID = 1331057896738442874L;
	
	private List<String> t20Batting;
	private List<String> oDBatting;
	private List<String> testBatting;
	private List<String> t20Bowling;
	private List<String> oDBowling;
	private List<String> testBowling;
	private String cricketProfileKey;

	public List<String> getT20Batting() {
		return t20Batting;
	}

	public void setT20Batting(List<String> t20Batting) {
		this.t20Batting = t20Batting;
	}

	public List<String> getoDBatting() {
		return oDBatting;
	}

	public void setoDBatting(List<String> oDBatting) {
		this.oDBatting = oDBatting;
	}

	public List<String> getTestBatting() {
		return testBatting;
	}

	public void setTestBatting(List<String> testBatting) {
		this.testBatting = testBatting;
	}

	public List<String> getT20Bowling() {
		return t20Bowling;
	}

	public void setT20Bowling(List<String> t20Bowling) {
		this.t20Bowling = t20Bowling;
	}

	public List<String> getoDBowling() {
		return oDBowling;
	}

	public void setoDBowling(List<String> oDBowling) {
		this.oDBowling = oDBowling;
	}

	public List<String> getTestBowling() {
		return testBowling;
	}

	public void setTestBowling(List<String> testBowling) {
		this.testBowling = testBowling;
	}

	public String getCricketProfileKey() {
		return cricketProfileKey;
	}

	public void setCricketProfileKey(String cricketProfileKey) {
		this.cricketProfileKey = cricketProfileKey;
	}

}
