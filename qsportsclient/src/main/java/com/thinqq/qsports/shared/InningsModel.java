package com.thinqq.qsports.shared;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Binoy
 *
 */
public class InningsModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6839724553583262486L;
	String teamProfileId;
	String teamName;
	List<ScoreCardBattingModel> battingScoreCard;
	List<ScoreCardBowlingModel> bowlingScoreCard;
	int extras;
	int legBye;

	public List<ScoreCardBattingModel> getBattingScoreCard() {
		return battingScoreCard;
	}

	public void setBattingScoreCard(List<ScoreCardBattingModel> battingScoreCard) {
		this.battingScoreCard = battingScoreCard;
	}

	public List<ScoreCardBowlingModel> getBowlingScoreCard() {
		return bowlingScoreCard;
	}

	public void setBowlingScoreCard(List<ScoreCardBowlingModel> bowlingScoreCard) {
		this.bowlingScoreCard = bowlingScoreCard;
	}

	public String getTeamProfileId() {
		return teamProfileId;
	}

	public void setTeamProfileId(String teamProfileId) {
		this.teamProfileId = teamProfileId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getExtras() {
		return extras;
	}

	public void setExtras(int extras) {
		this.extras = extras;
	}

	public int getLegBye() {
		return legBye;
	}

	public void setLegBye(int legBye) {
		this.legBye = legBye;
	}

}
