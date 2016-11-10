package com.thinqq.qsports.client.cricket.matchview.battingcard;

import java.util.List;
import java.util.Map;

import com.thinqq.qsports.client.common.IComponentModel;
import com.thinqq.qsports.client.cricket.PlayerProfileMinData;
import com.thinqq.qsports.client.cricket.matchview.battingentry.BattingEntryModel;

public class BattingCardModel implements IComponentModel {

	private String teamName;
	private boolean inProgress;
	private Map<Long,BattingEntryModel> battingEntries;
	private int eNoBalls;
	private int eWides;
	private int eByes;
	private int eLegByes;
	private int ePenaltyRuns;
	private int eTotal;
	private int total;
	private int wickets;
	private int overs;
	private int balls;
	private List<PlayerProfileMinData> batsmenToBat;
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public boolean isInProgress() {
		return inProgress;
	}
	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}
	public Map<Long,BattingEntryModel> getBattingEntries() {
		return battingEntries;
	}
	public void setBattingEntries(Map<Long,BattingEntryModel> battingEntries) {
		this.battingEntries = battingEntries;
	}
	public int geteNoBalls() {
		return eNoBalls;
	}
	public void seteNoBalls(int eNoBalls) {
		this.eNoBalls = eNoBalls;
	}
	public int geteWides() {
		return eWides;
	}
	public void seteWides(int eWides) {
		this.eWides = eWides;
	}
	public int geteByes() {
		return eByes;
	}
	public void seteByes(int eByes) {
		this.eByes = eByes;
	}
	public int geteLegByes() {
		return eLegByes;
	}
	public void seteLegByes(int eLegByes) {
		this.eLegByes = eLegByes;
	}
	public int getePenaltyRuns() {
		return ePenaltyRuns;
	}
	public void setePenaltyRuns(int ePenaltyRuns) {
		this.ePenaltyRuns = ePenaltyRuns;
	}
	public int geteTotal() {
		return eTotal;
	}
	public void seteTotal(int eTotal) {
		this.eTotal = eTotal;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getWickets() {
		return wickets;
	}
	public void setWickets(int wickets) {
		this.wickets = wickets;
	}
	public int getOvers() {
		return overs;
	}
	public void setOvers(int overs) {
		this.overs = overs;
	}
	public int getBalls() {
		return balls;
	}
	public void setBalls(int balls) {
		this.balls = balls;
	}
	public List<PlayerProfileMinData> getBatsmenToBat() {
		return batsmenToBat;
	}
	public void setBatsmenToBat(List<PlayerProfileMinData> batsmenToBat) {
		this.batsmenToBat = batsmenToBat;
	}
}
