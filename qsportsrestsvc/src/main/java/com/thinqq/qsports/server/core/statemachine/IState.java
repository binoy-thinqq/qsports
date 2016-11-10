package com.thinqq.qsports.server.core.statemachine;

import java.util.List;

import com.thinqq.qsports.shared.validation.ErrorVo;

public interface IState<T extends HasState> {
	public int getStateId();
	public List<ErrorVo> isEligible(T object);
	public boolean onEntry(T object);
}
