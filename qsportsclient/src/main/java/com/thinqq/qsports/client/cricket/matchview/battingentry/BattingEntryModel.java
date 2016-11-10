package com.thinqq.qsports.client.cricket.matchview.battingentry;

import com.thinqq.qsports.client.common.IComponentModel;
import com.thinqq.qsports.client.cricket.PlayerProfileMinData;
import com.thinqq.qsports.shared.OutTypeEnum;

public class BattingEntryModel  implements IComponentModel,Comparable<BattingEntryModel>{

private long entryId;
private int sequence;
private PlayerProfileMinData batsman;
private PlayerProfileMinData fielder1;
private PlayerProfileMinData bowlerOrFielder2;
private boolean isCaughtAndBowled;
private OutTypeEnum outType;
private int runs;
private int ballsFaced;
private float strikeRate;
private int fours;
private int sixes;

public BattingEntryModel(){}

public BattingEntryModel(long entryId, int sequence,
		PlayerProfileMinData batsman, PlayerProfileMinData fielder1,
		PlayerProfileMinData bowlerOrFielder2, boolean isCaughtAndBowled,
		OutTypeEnum outType, int runs, int ballsFaced, float strikeRate,
		int fours, int sixes) {
	super();
	this.entryId = entryId;
	this.sequence = sequence;
	this.batsman = batsman;
	this.fielder1 = fielder1;
	this.bowlerOrFielder2 = bowlerOrFielder2;
	this.isCaughtAndBowled = isCaughtAndBowled;
	this.outType = outType;
	this.runs = runs;
	this.ballsFaced = ballsFaced;
	this.strikeRate = strikeRate;
	this.fours = fours;
	this.sixes = sixes;
}
public PlayerProfileMinData getBatsman() {
	return batsman;
}
public void setBatsman(PlayerProfileMinData batsman) {
	this.batsman = batsman;
}
public PlayerProfileMinData getFielder1() {
	return fielder1;
}
public void setFielder1(PlayerProfileMinData fielder1) {
	this.fielder1 = fielder1;
}
public PlayerProfileMinData getBowlerOrFielder2() {
	return bowlerOrFielder2;
}
public void setBowlerOrFielder2(PlayerProfileMinData bowlerOrFielder2) {
	this.bowlerOrFielder2 = bowlerOrFielder2;
}
public OutTypeEnum getOutType() {
	return outType;
}
public void setOutType(OutTypeEnum outType) {
	this.outType = outType;
}
public int getRuns() {
	return runs;
}
public void setRuns(int runs) {
	this.runs = runs;
}
public int getBallsFaced() {
	return ballsFaced;
}
public void setBallsFaced(int ballsFaced) {
	this.ballsFaced = ballsFaced;
}
public float getStrikeRate() {
	return strikeRate;
}
public void setStrikeRate(float strikeRate) {
	this.strikeRate = strikeRate;
}
public int getFours() {
	return fours;
}
public void setFours(int fours) {
	this.fours = fours;
}
public int getSixes() {
	return sixes;
}
public void setSixes(int sixes) {
	this.sixes = sixes;
}
public long getEntryId() {
	return entryId;
}
public void setEntryId(long entryId) {
	this.entryId = entryId;
}
public int getSequence() {
	return sequence;
}
public void setSequence(int sequence) {
	this.sequence = sequence;
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
	BattingEntryModel other = (BattingEntryModel) obj;
	if (entryId != other.entryId)
		return false;
	return true;
}
@Override
public int compareTo(BattingEntryModel o) {
	Integer seq = new Integer(this.sequence);
	return seq.compareTo(o.sequence);
}
public boolean isCaughtAndBowled() {
	return isCaughtAndBowled;
}
public void setCaughtAndBowled(boolean isCaughtAndBowled) {
	this.isCaughtAndBowled = isCaughtAndBowled;
}

}
