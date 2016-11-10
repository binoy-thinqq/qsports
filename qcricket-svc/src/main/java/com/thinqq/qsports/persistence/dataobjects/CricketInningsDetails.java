package com.thinqq.qsports.persistence.dataobjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cricket.cricket_innings_details")
public class CricketInningsDetails implements DataObject {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7359874319155733726L;
	@Id
	@Column(name = "innings_id")
	@SequenceGenerator(name = "cricket_match_ext_seq", sequenceName = "cricket.cricket_match_ext_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cricket_match_ext_seq")
	Integer inningsId;
	
	
	@Column(name = "match_name")
	String matchName;
	
	@Column(name = "innings_info")
	String inningsDetails;

	public Integer getInningsId() {
		return inningsId;
	}

	public void setInningsId(Integer inningsId) {
		this.inningsId = inningsId;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public String getInningsDetails() {
		return inningsDetails;
	}

	public void setInningsDetails(String inningsDetails) {
		this.inningsDetails = inningsDetails;
	}

}
