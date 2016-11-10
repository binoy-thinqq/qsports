package com.thinqq.qsports.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.place.impl.AbstractPlaceHistoryMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.thinqq.qsports.client.teamprofile.TeamProfilePlace;
import com.thinqq.qsports.client.userprofile.UserProfilePlace;
import com.thinqq.qsports.client.userprofile.match.edit.CricketMatchEditPlace;

public class QSportsPlaceHistoryMapperImpl extends
		AbstractPlaceHistoryMapper<Place> implements QSportsPlaceHistoryMapper {
	
	@SuppressWarnings("rawtypes")
	public static Map<String,PlaceTokenizer> PREFIX_PLACE_MAPPER = new HashMap<String, PlaceTokenizer>();
	static {
		PREFIX_PLACE_MAPPER.put(UserProfilePlace.HISTORY_PREFIX, UserProfilePlace.TOKENIZER);
		PREFIX_PLACE_MAPPER.put(TeamProfilePlace.HISTORY_PREFIX, TeamProfilePlace.TOKENIZER);
		PREFIX_PLACE_MAPPER.put(CricketMatchEditPlace.HISTORY_PREFIX, CricketMatchEditPlace.TOKENIZER);
	}
	@Override
	protected PrefixAndToken getPrefixAndToken(Place newPlace) {
		IQsportsPlace place = (IQsportsPlace)newPlace;
		return new PrefixAndToken(place.getPlacePrefix(), place.getToken());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected PlaceTokenizer<Place> getTokenizer(String prefix) {
		return PREFIX_PLACE_MAPPER.get(prefix);
	}

}
