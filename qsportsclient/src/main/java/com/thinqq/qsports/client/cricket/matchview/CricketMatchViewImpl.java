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
package com.thinqq.qsports.client.cricket.matchview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Sample implementation of {@link CricketMatchView}.
 */
public class CricketMatchViewImpl extends Composite implements CricketMatchView {

	interface Binder extends UiBinder<Widget, CricketMatchViewImpl> {
	}
	private static final Binder binder = GWT.create(Binder.class);
	@UiField
	public VerticalPanel matchViewPanel;
	private Presenter listener;
	private CricketMatchResource resource = GWT.create(CricketMatchResource.class);
	public CricketMatchViewImpl() {
		resource.style().ensureInjected();
		initWidget(binder.createAndBindUi(this));
		matchViewPanel.setStylePrimaryName(resource.style().matchViewPanel());
	}

	@Override
	public void setName(String name) {

	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@Override
	public void setData(CricketMatchModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshdata(CricketMatchModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialiseComponent(CricketMatchResource style) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reInitialiseComponent(CricketMatchResource style) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

}
