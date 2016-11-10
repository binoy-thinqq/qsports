package com.thinqq.qsports.client.events;

import com.google.web.bindery.event.shared.Event;

public class CreateNewMatchEvent extends Event<CreateNewMatchEventHandler> {

	public static final Type<CreateNewMatchEventHandler> TYPE = new Type<CreateNewMatchEventHandler>();
	
	@Override
	protected void dispatch(CreateNewMatchEventHandler handler) {
		handler.createNewMatchEvent(this);
	}

	@Override
	public com.google.web.bindery.event.shared.Event.Type<CreateNewMatchEventHandler> getAssociatedType() {
		return TYPE;
	}

	

}
