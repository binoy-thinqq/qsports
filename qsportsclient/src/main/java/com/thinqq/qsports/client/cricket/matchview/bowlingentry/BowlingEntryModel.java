package com.thinqq.qsports.client.cricket.matchview.bowlingentry;

import com.thinqq.qsports.client.common.IComponentModel;
import com.thinqq.qsports.client.cricket.PlayerProfileMinData;

public class BowlingEntryModel  implements IComponentModel,Comparable<BowlingEntryModel>{
private long entryId;
private PlayerProfileMinData bowler;
private int overs;
private int balls;
private int maidens;
private int runs;
private int wickets;
private float economy;
private int wides;
private int noballs;

public BowlingEntryModel(long entryId, PlayerProfileMinData bowler, int overs,
		int balls, int maidens, int runs, int wickets, float economy,
		int wides, int noballs) {
	super();
	this.entryId = entryId;
	this.bowler = bowler;
	this.overs = overs;
	this.balls = balls;
	this.maidens = maidens;
	this.runs = runs;
	this.wickets = wickets;
	this.economy = economy;
	this.wides = wides;
	this.noballs = noballs;
}

public long getEntryId() {
	return entryId;
}
public void setEntryId(long entryId) {
	this.entryId = entryId;
}
public PlayerProfileMinData getBowler() {
	return bowler;
}
public void setBowler(PlayerProfileMinData bowler) {
	this.bowler = bowler;
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
public int getMaidens() {
	return maidens;
}
public void setMaidens(int maidens) {
	this.maidens = maidens;
}
public int getRuns() {
	return runs;
}
public void setRuns(int runs) {
	this.runs = runs;
}
public int getWickets() {
	return wickets;
}
public void setWickets(int wickets) {
	this.wickets = wickets;
}
public float getEconomy() {
	return economy;
}
public void setEconomy(float economy) {
	this.economy = economy;
}
public int getWides() {
	return wides;
}
public void setWides(int wides) {
	this.wides = wides;
}
public int getNoballs() {
	return noballs;
}
public void setNoballs(int noballs) {
	this.noballs = noballs;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (entryId ^ (entryId >>> 32));
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	BowlingEntryModel other = (BowlingEntryModel) obj;
	if (entryId != other.entryId)
		return false;
	return true;
}
@Override
public int compareTo(BowlingEntryModel o) {
	Integer overs = new Integer(this.overs);
	int oversComp = overs.compareTo(o.overs);
	if(oversComp ==0){
		Integer balls = new Integer(this.balls);
		return balls.compareTo(o.balls);
	}
	return oversComp;
}

}
