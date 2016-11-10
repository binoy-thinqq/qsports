package com.thinqq.qsports.client.core.statemachine;

public interface HasState {

	public void setState(int stateId);
	public int getState();
}
