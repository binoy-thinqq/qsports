package com.thinqq.qsports.server.pesistence.dataobjects;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class CricketProfile extends Profile {
	@Persistent
	private Integer battingHandType; 
	@Persistent  
	private Integer battingOrder;
	@Persistent
	private Integer bowlingHandType; 
	@Persistent 
	private Integer bowlingMethod;
	@Persistent
	private Integer fieldPosition;
	@Persistent
	private Boolean wicketKeeper;
	@Persistent
	private Key t20fieldingStat;
	@Persistent
	private Key t20battingStat;
	@Persistent
	private Key t20bowlingStatistics;
	@Persistent
	private Key ODfieldingStat;
	@Persistent
	private Key ODbattingStat;
	@Persistent
	private Key ODbowlingStatistics;
	@Persistent
	private Key testfieldingStat;
	@Persistent
	private Key testbattingStat;
	@Persistent
	private Key testbowlingStatistics;
	@Persistent
	private List<Key> matches;
	
	public Integer getFieldPosition() {
		return fieldPosition;
	}
	public void setFieldPosition(Integer fieldPosition) {
		this.fieldPosition = fieldPosition;
	}
	public Boolean getWicketKeeper() {
		return wicketKeeper;
	}
	public void setWicketKeeper(Boolean wicketKeeper) {
		this.wicketKeeper = wicketKeeper;
	}
	public List<Key> getMatches() {
		return matches;
	}
	public void setMatches(List<Key> matches) {
		this.matches = matches;
	}
	public Integer getBattingHandType() {
		return battingHandType;
	}
	public void setBattingHandType(Integer battingHandType) {
		this.battingHandType = battingHandType;
	}
	public Integer getBattingOrder() {
		return battingOrder;
	}
	public void setBattingOrder(Integer battingOrder) {
		this.battingOrder = battingOrder;
	}
	public Integer getBowlingHandType() {
		return bowlingHandType;
	}
	public void setBowlingHandType(Integer bowlingHandType) {
		this.bowlingHandType = bowlingHandType;
	}
	public Integer getBowlingMethod() {
		return bowlingMethod;
	}
	public void setBowlingMethod(Integer bowlingMethod) {
		this.bowlingMethod = bowlingMethod;
	}
	public Key getT20fieldingStat() {
		return t20fieldingStat;
	}
	public void setT20fieldingStat(Key t20fieldingStat) {
		this.t20fieldingStat = t20fieldingStat;
	}
	public Key getT20battingStat() {
		return t20battingStat;
	}
	public void setT20battingStat(Key t20battingStat) {
		this.t20battingStat = t20battingStat;
	}
	public Key getT20bowlingStatistics() {
		return t20bowlingStatistics;
	}
	public void setT20bowlingStatistics(Key t20bowlingStatistics) {
		this.t20bowlingStatistics = t20bowlingStatistics;
	}
	public Key getTestfieldingStat() {
		return testfieldingStat;
	}
	public void setTestfieldingStat(Key testfieldingStat) {
		this.testfieldingStat = testfieldingStat;
	}
	public Key getTestbattingStat() {
		return testbattingStat;
	}
	public void setTestbattingStat(Key testbattingStat) {
		this.testbattingStat = testbattingStat;
	}
	public Key getTestbowlingStatistics() {
		return testbowlingStatistics;
	}
	public void setTestbowlingStatistics(Key testbowlingStatistics) {
		this.testbowlingStatistics = testbowlingStatistics;
	}
	public Key getODfieldingStat() {
		return ODfieldingStat;
	}
	public void setODfieldingStat(Key oDfieldingStat) {
		ODfieldingStat = oDfieldingStat;
	}
	public Key getODbattingStat() {
		return ODbattingStat;
	}
	public void setODbattingStat(Key oDbattingStat) {
		ODbattingStat = oDbattingStat;
	}
	public Key getODbowlingStatistics() {
		return ODbowlingStatistics;
	}
	public void setODbowlingStatistics(Key oDbowlingStatistics) {
		ODbowlingStatistics = oDbowlingStatistics;
	}
	
	
}
