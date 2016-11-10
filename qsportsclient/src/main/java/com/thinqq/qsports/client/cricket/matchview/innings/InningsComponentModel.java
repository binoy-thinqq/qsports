package com.thinqq.qsports.client.cricket.matchview.innings;

import com.thinqq.qsports.client.common.IComponentModel;
import com.thinqq.qsports.client.cricket.matchview.battingcard.BattingCardModel;
import com.thinqq.qsports.client.cricket.matchview.bowlingcard.BowlingCardModel;

public class InningsComponentModel implements IComponentModel {
private String inningsName;
private String scoreSummary;
private BattingCardModel battingCard;
private BowlingCardModel bowlingCard;
private boolean inProgress;

public String getInningsName() {
	return inningsName;
}
public void setInningsName(String inningsName) {
	this.inningsName = inningsName;
}
public BattingCardModel getBattingCard() {
	return battingCard;
}
public void setBattingCard(BattingCardModel battingCard) {
	this.battingCard = battingCard;
}
public BowlingCardModel getBowlingCard() {
	return bowlingCard;
}
public void setBowlingCard(BowlingCardModel bowlingCard) {
	this.bowlingCard = bowlingCard;
}
public boolean isInProgress() {
	return inProgress;
}
public void setInProgress(boolean inProgress) {
	this.inProgress = inProgress;
}
public String getScoreSummary() {
	return scoreSummary;
}
public void setScoreSummary(String scoreSummary) {
	this.scoreSummary = scoreSummary;
}
}
