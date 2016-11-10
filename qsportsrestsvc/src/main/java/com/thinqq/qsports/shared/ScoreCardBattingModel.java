package com.thinqq.qsports.shared;

import java.io.Serializable;
import java.util.List;


/**
 * Batting model
 * @author Binoy
 *
 */
public class ScoreCardBattingModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4929023015818429720L;
	
	private String batsmanProfileId;
	private String batsman = "";
	private String caughtByProfileID;
	private String caughtBy = "";
	private String bowledByProfileID;
	private String bowledBy = "";
	private int outType;
	private int runsScored;
	private int ballsFaced;
	private int fours;
	private int sixes;
	private List<String> errors;

	public String getBatsman() {

		return batsman;
	}

	public void setBatsman(String batsman) {
		this.batsman = batsman;
	}

	public String getCaughtBy() {
		return caughtBy;
	}

	public void setCaughtBy(String caughtBy) {
		this.caughtBy = caughtBy;
	}

	public String getBowledBy() {
		return bowledBy;
	}

	public void setBowledBy(String bowledBy) {
		this.bowledBy = bowledBy;
	}

	public int getOutType() {
		return outType;
	}

	public void setOutType(int outType) {
		this.outType = outType;
	}

	public int getRunsScored() {
		return runsScored;
	}

	public void setRunsScored(int runsScored) {
		this.runsScored = runsScored;
	}

	public int getBallsFaced() {
		return ballsFaced;
	}

	public void setBallsFaced(int ballsFaced) {
		this.ballsFaced = ballsFaced;
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

	public String getProfileId() {
		return batsmanProfileId;
	}

	public void setProfileId(String batsmanProfileId) {
		this.batsmanProfileId = batsmanProfileId;
	}

	public String getBatsmanProfileId() {
		return batsmanProfileId;
	}

	public void setBatsmanProfileId(String batsmanProfileId) {
		this.batsmanProfileId = batsmanProfileId;
	}

	public String getCaughtByProfileID() {
		return caughtByProfileID;
	}

	public void setCaughtByProfileID(String caughtByProfileID) {
		this.caughtByProfileID = caughtByProfileID;
	}

	public String getBowledByProfileID() {
		return bowledByProfileID;
	}

	public void setBowledByProfileID(String bowledByProfileID) {
		this.bowledByProfileID = bowledByProfileID;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	
}
