package com.thinqq.qsports.client.cricket;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.thinqq.qsports.client.core.ICleanable;
import com.thinqq.qsports.shared.CricketMatchModel;

public interface CricketView extends IsWidget, ICleanable  {

	void initializeView(CricketMatchModel model);

	boolean validateMatch();

	void saveMatch();

	void setPresenter(Presenter listener);

	public interface Presenter {

		/**
		 * Navigate to a new Place in the browser.
		 */
		void goTo(Place place);

		void saveMatch(CricketMatchModel matchStep1);

		void validateMatch();

		void getMatchDetails(String matchId);

		CricketMatchModel getCricketMatch();

		void setCricketMatch(CricketMatchModel cricketMatch);
	}

}
