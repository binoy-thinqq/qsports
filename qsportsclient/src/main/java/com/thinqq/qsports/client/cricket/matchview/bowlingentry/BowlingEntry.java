package com.thinqq.qsports.client.cricket.matchview.bowlingentry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.client.common.IComponent;

public class BowlingEntry extends Composite implements
		IComponent<BowlingEntryModel, BowlingEntryResource> {
	private BowlingEntryResource resource = GWT.create(BowlingEntryResource.class);
	private BowlingEntryResource.Style style = resource.style();
	private static final Binder binder = GWT.create(Binder.class);
	@UiField
	Label runs;
	@UiField
	HorizontalPanel bowlingEntry;
	
	interface Binder extends UiBinder<Widget, BowlingEntry> {
	}

	public BowlingEntry() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void setData(BowlingEntryModel model) {}

	@Override
	public void cleanData() {}

	@Override
	public void refreshdata(BowlingEntryModel model) {}

	@Override
	public void initialiseComponent(BowlingEntryResource style) {
		//NO-OP now
	}

	@Override
	public void reInitialiseComponent(BowlingEntryResource style) {
		//NO-OP now
	}

}
