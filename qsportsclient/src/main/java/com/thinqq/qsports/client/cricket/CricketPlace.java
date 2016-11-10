package com.thinqq.qsports.client.cricket;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CricketPlace extends Place{
	private String name;
	private String matchId;

	public CricketPlace(String token) {
		this.name = token;
	}

	public String getName() {
		return name;
	}

	public String getMatchId(){
		return matchId;
	}
	
	public void setMatchId(String matchId){
		this.matchId = matchId;
	}
	
	/**
	 * PlaceTokenizer knows how to serialize the Place's state to a URL token.
	 */
	public static class Tokenizer implements PlaceTokenizer<CricketPlace> {

		@Override
		public String getToken(CricketPlace place) {
			return place.getName();
		}

		@Override
		public CricketPlace getPlace(String token) {
			return new CricketPlace(token);
		}

	}
}
