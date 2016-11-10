package com.thinqq.qsports.client.cricket.matchview.innings;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.client.common.IComponent;

public class InningsComponent extends Composite implements IComponent<InningsComponentModel, InningsComponentResource> {

	private static final Binder binder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, InningsComponent> {
	}

	public InningsComponent() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void setData(InningsComponentModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshdata(InningsComponentModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialiseComponent(InningsComponentResource style) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reInitialiseComponent(InningsComponentResource style) {
		// TODO Auto-generated method stub
		
	}

}
