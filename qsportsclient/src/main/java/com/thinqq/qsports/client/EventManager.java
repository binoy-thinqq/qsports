package com.thinqq.qsports.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.Event.Type;
import com.google.web.bindery.event.shared.EventBus;

public class EventManager<T> {

	private static final EventManager<EventHandler> instance = new EventManager<EventHandler>();
	private EventBus eventBus;
	private EventManager(){};
	public static EventManager<EventHandler> getInstance(){
		return instance;
	}
	public void setEventBus(final EventBus eventBus){
		this.eventBus = eventBus;
	}
	public <E extends T> void fireEvent(Event<E> event){
		eventBus.fireEvent(event);
	}
	public <E extends T> void  registerEvent(Type<E> type, E handler){
		eventBus.addHandler(type, handler);
	}
	
}
