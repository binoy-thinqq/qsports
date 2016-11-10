package com.thinqq.qsports.client.cricket;

import com.google.gwt.user.client.ui.Composite;
import com.thinqq.qsports.client.cricket.CricketView.Presenter;
import com.thinqq.qsports.shared.CricketMatchModel;


/**
 * Cricket Match Tab should extend this class
 *
 */
public abstract class CricketMatchTab extends Composite {

	boolean isInitialized = false;
	
	Presenter presenter;

	public abstract boolean validateTab();

	public abstract void populateMatch();

	public boolean isInitialized() {
		return isInitialized;
	}

	public void setInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	public Presenter getPresenter() {
		return this.presenter;
	}
	
	public CricketMatchModel getCricketMatchModel() {
		return presenter.getCricketMatch();
	}
}
