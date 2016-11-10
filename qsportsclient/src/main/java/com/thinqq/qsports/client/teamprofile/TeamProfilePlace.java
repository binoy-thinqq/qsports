package com.thinqq.qsports.client.teamprofile;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.thinqq.qsports.client.IQsportsPlace;

public class TeamProfilePlace extends Place implements IQsportsPlace{
	  
	public static final String HISTORY_PREFIX = "TPP";

	public static Tokenizer TOKENIZER = new Tokenizer();
		private String teamKey;

		public TeamProfilePlace(String token) {
			this.teamKey = token;
		}

		public String getTeamKey() {
			return teamKey;
		}

		/**
		 * PlaceTokenizer knows how to serialize the Place's state to a URL token.
		 */
		public static class Tokenizer implements PlaceTokenizer<TeamProfilePlace> {

			@Override
			public String getToken(TeamProfilePlace place) {
				return HISTORY_PREFIX+":"+place.getTeamKey();
			}

			@Override
			public TeamProfilePlace getPlace(String teamKey) {
				return new TeamProfilePlace(teamKey);
			}

		}

		@Override
		public String getPlacePrefix() {
			return HISTORY_PREFIX;
		}

		@Override
		public String getToken() {
			return teamKey;
		}
}
