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
package com.thinqq.qsports.client.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.client.home.QSportsHomeResources.ThinqQHomeStyle;
import com.thinqq.qsports.shared.Constants;

/**
 * Sample implementation of {@link QSportsHome}.
 */
public class QSportsHomeImpl extends Composite implements QSportsHome {

	private static QSportsHomeResources resource = GWT
			.create(QSportsHomeResources.class);
	private static ThinqQHomeStyle style = resource.style();

	interface Binder extends UiBinder<Widget, QSportsHomeImpl> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private Presenter listener;

	@UiField
	InlineLabel lblRegSuccMsg;
	
	public QSportsHomeImpl() {
		style.ensureInjected();
		initWidget(binder.createAndBindUi(this));
		addHandlers();
	}

	private void addHandlers() {
		/*
		 * sddpanellink.addClickHandler(new ClickHandler() {
		 * 
		 * @Override public void onClick(ClickEvent event) { listener.goTo(new
		 * SofwareDDPlace(""));
		 * 
		 * } });
		 */

	}

	@Override
	public void setName(String name) {
		if (name.endsWith(Constants.REG_SUCCESS_URL)) {
			lblRegSuccMsg.setText("Thank you for joining us");
		}else{
			lblRegSuccMsg.setText("");
		}
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	void onButtonClick(ClickEvent event) {
		Place newPlace = null;
		// TODO
		listener.goTo(newPlace);
	}

	@Override
	public void clean() {
		//Nothing to clean
	}

}
