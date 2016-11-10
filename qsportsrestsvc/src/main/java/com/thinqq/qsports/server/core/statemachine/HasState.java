package com.thinqq.qsports.server.core.statemachine;

public interface HasState {

	public void setState(int stateId);
	public int getState();
}
