/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thinqq.qsports.client.userprofile.match.edit;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.thinqq.qsports.client.IQsportsPlace;

/**
 * A place object representing a particular state of the UI. A Place can be converted to and from a
 * URL history token by defining a {@link PlaceTokenizer} for each {@link Place}, and the 
 * {@link PlaceHistoryHandler} automatically updates the browser URL corresponding to each 
 * {@link Place} in your app.
 */
public class CricketMatchEditPlace extends Place implements IQsportsPlace{
  
	public static final String HISTORY_PREFIX = "CMEP";

	public static Tokenizer TOKENIZER = new Tokenizer();
	private String matchKey;
	public CricketMatchEditPlace(String token) {
		this.matchKey = token;
	}

	public String getMatchKey() {
		return matchKey;
	}

	/**
	 * PlaceTokenizer knows how to serialize the Place's state to a URL token.
	 */
	public static class Tokenizer implements PlaceTokenizer<CricketMatchEditPlace> {

		@Override
		public String getToken(CricketMatchEditPlace place) {
			return HISTORY_PREFIX+":"+place.getMatchKey();
		}

		@Override
		public CricketMatchEditPlace getPlace(String token) {
			return new CricketMatchEditPlace(token);
		}

	}

	@Override
	public String getPlacePrefix() {
		return HISTORY_PREFIX;
	}

	@Override
	public String getToken() {
		return matchKey;
	}
}
