package com.thinqq.qsports.client.events;

import com.thinqq.qsports.client.Genie;
import com.thinqq.qsports.client.teamprofile.CreateNewTeamEventHandlerImpl;
import com.thinqq.qsports.client.userprofile.match.edit.CreateNewMatchEventHandlerImpl;

public final class GlobalEventInitialiser {
private GlobalEventInitialiser(){

}
public static void initialise(){
	initaliseCricketEvents();
}
private static void initaliseCricketEvents() {
	Genie.getEventmanager().registerEvent(CreateNewTeamEvent.TYPE, new CreateNewTeamEventHandlerImpl());
	Genie.getEventmanager().registerEvent(CreateNewMatchEvent.TYPE, new CreateNewMatchEventHandlerImpl());
}
}
