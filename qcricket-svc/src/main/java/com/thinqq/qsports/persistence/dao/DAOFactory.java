package com.thinqq.qsports.persistence.dao;


public class DAOFactory {

	public static UserDAO getUserDAO(){
		return UserDAO.getInstance();
	}
	public static CricketProfileDAO getCricketProfileDAO(){
		return CricketProfileDAO.getInstance();
	}
	public static BattingStatisticsDAO getBattingStatisticsDAO(){
		return BattingStatisticsDAO.getInstance();
	}
	public static BowlingStatisticsDAO getBowlingStatisticsDAO(){
		return BowlingStatisticsDAO.getInstance();
	}
}
