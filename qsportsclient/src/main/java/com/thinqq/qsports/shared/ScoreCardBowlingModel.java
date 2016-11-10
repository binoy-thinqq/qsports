package com.thinqq.qsports.shared;

import java.io.Serializable;
import java.util.List;

public class ScoreCardBowlingModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4931500733563604606L;
	/**
	 * 
	 */
	private Long bowlerProfileId;
	private String bowlerName = "";
	private float oversBowled;
	private int maiden;
	private int wickets;
	private int runsConcieved;
	private int wides;
	private int noBalls;
	private List<String> errors;

	public String getBowlerName() {
		return bowlerName;
	}

	public void setBowlerName(String bowlerName) {
		this.bowlerName = bowlerName;
	}

	public float getOversBowled() {
		return oversBowled;
	}

	public void setOversBowled(float oversBowled) {
		this.oversBowled = oversBowled;
	}

	public int getMaiden() {
		return maiden;
	}

	public void setMaiden(int maiden) {
		this.maiden = maiden;
	}

	public int getWickets() {
		return wickets;
	}

	public void setWickets(int wickets) {
		this.wickets = wickets;
	}

	public int getRunsConcieved() {
		return runsConcieved;
	}

	public void setRunsConcieved(int runsConcieved) {
		this.runsConcieved = runsConcieved;
	}

	public int getWides() {
		return wides;
	}

	public void setWides(int extras) {
		this.wides = extras;
	}

	public int getNoBalls() {
		return noBalls;
	}

	public void setNoBalls(int noBalls) {
		this.noBalls = noBalls;
	}

	public Long getBowlerProfileId() {
		return bowlerProfileId;
	}

	public void setBowlerProfileId(Long bowlerProfileId) {
		this.bowlerProfileId = bowlerProfileId;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
