package com.thinqq.qsports.shared.userprofile;

import java.util.Set;

import com.thinqq.qsports.persistence.dto.BattingStatisticsVo;
import com.thinqq.qsports.persistence.dto.BowlingStatisticsVo;
import com.thinqq.qsports.shared.QSportsResponseVo;

public class CricketStatisticsResponseVo extends QSportsResponseVo {

	private static final long serialVersionUID = 1331057896738442874L;
	
	Set<BattingStatisticsVo> battingStats;
	
	Set<BowlingStatisticsVo> bowlingStats;

	public Set<BattingStatisticsVo> getBattingStats() {
		return battingStats;
	}

	public void setBattingStats(Set<BattingStatisticsVo> battingStats) {
		this.battingStats = battingStats;
	}

	public Set<BowlingStatisticsVo> getBowlingStats() {
		return bowlingStats;
	}

	public void setBowlingStats(Set<BowlingStatisticsVo> bowlingStats) {
		this.bowlingStats = bowlingStats;
	}


}
