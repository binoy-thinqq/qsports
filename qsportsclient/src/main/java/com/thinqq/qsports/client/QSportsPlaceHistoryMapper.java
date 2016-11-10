package com.thinqq.qsports.client;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.thinqq.qsports.client.teamprofile.TeamProfilePlace;
import com.thinqq.qsports.client.userprofile.UserProfilePlace;
import com.thinqq.qsports.client.userprofile.match.edit.CricketMatchEditPlace;

@WithTokenizers({ 
	UserProfilePlace.Tokenizer.class,
	TeamProfilePlace.Tokenizer.class,
	CricketMatchEditPlace.Tokenizer.class
	})
public interface QSportsPlaceHistoryMapper extends PlaceHistoryMapper {

}