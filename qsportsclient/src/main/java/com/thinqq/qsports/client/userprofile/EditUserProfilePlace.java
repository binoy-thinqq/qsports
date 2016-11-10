package com.thinqq.qsports.client.userprofile;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class EditUserProfilePlace extends Place {
  
	/**
	 * Sample property (stores token). 
	 */
	private String name;

	public EditUserProfilePlace(String token) {
		this.name = token;
	}

	public String getName() {
		return name;
	}

	/**
	 * PlaceTokenizer knows how to serialize the Place's state to a URL token.
	 */
	public static class Tokenizer implements PlaceTokenizer<EditUserProfilePlace> {

		@Override
		public String getToken(EditUserProfilePlace place) {
			return place.getName();
		}

		@Override
		public EditUserProfilePlace getPlace(String token) {
			return new EditUserProfilePlace(token);
		}

	}
}
