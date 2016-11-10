package com.thinqq.qsports.shared.teamprofile;

import java.util.List;

import com.thinqq.qsports.shared.QSportsResponseVo;

public class GetTeamStatisticsResponseVo extends QSportsResponseVo{

	private static final long serialVersionUID = -2782234551084800857L;
	private List<String> t20Statistics;
	private List<String> oDStatistics;
	private List<String> testStatistics;
	private String winPercentage;
	
	public List<String> getT20Statistics() {
		return t20Statistics;
	}
	public void setT20Statistics(List<String> t20Statistics) {
		this.t20Statistics = t20Statistics;
	}
	public List<String> getoDStatistics() {
		return oDStatistics;
	}
	public void setoDStatistics(List<String> oDStatistics) {
		this.oDStatistics = oDStatistics;
	}
	public List<String> getTestStatistics() {
		return testStatistics;
	}
	public void setTestStatistics(List<String> testStatistics) {
		this.testStatistics = testStatistics;
	}
	public String getWinPercentage() {
		return winPercentage;
	}
	public void setWinPercentage(String winPercentage) {
		this.winPercentage = winPercentage;
	}
	
	
}
