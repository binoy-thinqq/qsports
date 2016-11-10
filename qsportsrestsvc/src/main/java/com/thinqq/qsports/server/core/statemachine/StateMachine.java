package com.thinqq.qsports.server.core.statemachine;

import java.util.ArrayList;
import java.util.List;

import com.thinqq.qsports.shared.validation.ErrorRepository;
import com.thinqq.qsports.shared.validation.ErrorVo;

public abstract class StateMachine<T extends HasState> {
	public final List<ErrorVo> gotoState(T object, int targetStateId){
		IState<T> state = getState(targetStateId);
		if(state != null){
			List<ErrorVo> errors = state.isEligible(object);
			if(errors == null || errors.isEmpty()){
				if(state.onEntry(object)){
					object.setState(state.getStateId());
					return errors;
				}
			} 
		} else{
			List<ErrorVo> errors = new ArrayList<ErrorVo>();
			errors.add(ErrorRepository.oppsSomethingBroke);
			return errors;
		}
		return new ArrayList<ErrorVo>();
	}
	public abstract IState<T> getState(int stateId);
	public final List<ErrorVo> isEligible(T object, int stateId){
		return getState(stateId).isEligible(object);
	}
	
}
