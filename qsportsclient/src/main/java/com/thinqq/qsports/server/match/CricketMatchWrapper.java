package com.thinqq.qsports.server.match;

import com.thinqq.qsports.client.core.statemachine.HasState;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketMatch;

public class CricketMatchWrapper implements HasState{

	private final CricketMatch match;
	
	//private CricketMatchWrapper(){ match = null;}
	
	public CricketMatchWrapper(CricketMatch match){
		this.match = match;
	}

	@Override
	public void setState(int stateId) {
		match.setStatus(stateId);
	}

	@Override
	public int getState() {
		return match.getStatus();
	}

	public CricketMatch getMatch() {
		return match;
	}



	
}
