package com.thinqq.qsports.client.cricket;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.thinqq.qsports.client.ClientFactory;
import com.thinqq.qsports.client.svc.QSportsCricketSVC;
import com.thinqq.qsports.client.svc.QSportsCricketSVCAsync;
import com.thinqq.qsports.shared.CricketMatchModel;

/**
 * Creates Match Entity and Handles the Scorecard details
 *
 */
public class CricketActivity extends AbstractActivity implements
		CricketView.Presenter {

	/**
	 * Used to obtain views, eventBus, placeController. Alternatively, could be
	 * injected via GIN.
	 */
	private ClientFactory clientFactory;
	private String matchId;
	private final CricketView view;

	private CricketMatchModel cricketMatch;

	QSportsCricketSVCAsync cricketSVC = GWT.create(QSportsCricketSVC.class);

	public CricketActivity(CricketPlace place, ClientFactory clientFactory) {
		this.matchId = place.getMatchId();
		if (matchId != null && !"".equals(matchId.trim())) {
			cricketSVC.getCricketMatch(matchId,
					new AsyncCallback<CricketMatchModel>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Couldn't find the match");
						}

						@Override
						public void onSuccess(CricketMatchModel result) {
							cricketMatch = result;

						}
					});
		}
		this.clientFactory = clientFactory;
		view = null;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus arg1) {
		view.setPresenter(this);
		view.initializeView(cricketMatch);
		containerWidget.setWidget(view.asWidget());

	}

	@Override
	public void goTo(Place place) {

		clientFactory.getPlaceController().goTo(place);

	}

	@Override
	public void saveMatch(CricketMatchModel model) {
		cricketSVC.createCricketMatch(model,
				new AsyncCallback<CricketMatchModel>() {

					@Override
					public void onSuccess(CricketMatchModel model) {
						cricketMatch = model;
					}

					@Override
					public void onFailure(Throwable arg0) {
						arg0.printStackTrace();
					}
				});
	}

	@Override
	public void getMatchDetails(String matchId) {
		cricketSVC.getCricketMatch(matchId,
				new AsyncCallback<CricketMatchModel>() {
					@Override
					public void onSuccess(CricketMatchModel model) {
						cricketMatch = model;
					}

					@Override
					public void onFailure(Throwable arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	
	public CricketMatchModel getCricketMatch() {
		if (cricketMatch == null) {
			cricketMatch = new CricketMatchModel();
		}
		return cricketMatch;
	}

	public void setCricketMatch(CricketMatchModel cricketMatch) {
		this.cricketMatch = cricketMatch;
	}

	@Override
	public void validateMatch() {
		view.validateMatch();
		
	}
}
