package com.thinqq.qsports.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.place.shared.PlaceController;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;

public final class Genie {
private static ClientFactory clientFactory;
private static PlaceController placeController;
private static EventManager<EventHandler> eventManager;
private static UserProfileResponseVo user;

private Genie(){}

public static ClientFactory getClientfactory() {
	return clientFactory;
}

public static PlaceController getPlacecontroller() {
	return placeController;
}

public static EventManager<EventHandler> getEventmanager() {
	return eventManager;
}

public static String getLocationURLString(){
	String location = com.google.gwt.user.client.Window.Location.getHref();
	if(location!=null && location.length()>0){
		int indexOfHash = location.indexOf('#');
		if(indexOfHash != -1){
			location = location.substring(0, indexOfHash);
		}
	}
	return location;
}

static final void setClientFactory(ClientFactory clientFactory) {
	Genie.clientFactory = clientFactory;
}

static final void setPlaceController(PlaceController placeController) {
	Genie.placeController = placeController;
}

static final void setEventManager(EventManager<EventHandler> eventManager) {
	Genie.eventManager = eventManager;
}

public static UserProfileResponseVo getUser() {
	return user;
}

public static void setUser(UserProfileResponseVo user) {
	Genie.user = user;
};

}
