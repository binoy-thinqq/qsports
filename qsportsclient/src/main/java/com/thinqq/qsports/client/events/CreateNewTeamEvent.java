package com.thinqq.qsports.client.events;

import com.google.web.bindery.event.shared.Event;

public class CreateNewTeamEvent extends Event<CreateNewTeamEventHandler> {

	public static final Type<CreateNewTeamEventHandler> TYPE = new Type<CreateNewTeamEventHandler>();
	
	@Override
	protected void dispatch(CreateNewTeamEventHandler handler) {
		handler.createNewTeamEvent(this);
	}

	@Override
	public com.google.web.bindery.event.shared.Event.Type<CreateNewTeamEventHandler> getAssociatedType() {
		return TYPE;
	}

}
