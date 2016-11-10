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
package com.thinqq.qsports.client.confirmation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.client.common.CommonResources;
import com.thinqq.qsports.shared.Constants;

/**
 * Sample implementation of {@link Confirmation}.
 */
public class ConfirmationImpl extends Composite implements Confirmation {

	interface Binder extends UiBinder<Widget, ConfirmationImpl> {
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	private Presenter listener;

	@UiField
	Label emailLabel;

	
	public ConfirmationImpl() {
		initWidget(binder.createAndBindUi(this));
		initialiseDialgoBoxes();
		//confirmingMessage.show();
		
	}
	DialogBox confirmingMessage = new DialogBox(false,true);
	public static final CommonResources commonPics = GWT.create(CommonResources.class);
	private void initialiseDialgoBoxes() {
		confirmingMessage.setGlassEnabled(true);
		confirmingMessage.setAnimationEnabled(true);
	    HorizontalPanel dialogContents = new HorizontalPanel();
	    dialogContents.add(new Image(commonPics.loadingImage()));
	    dialogContents.add(new Label("Confirming ..."));
	    dialogContents.setVisible(true);
	    confirmingMessage.setWidget(dialogContents);

	   

	}
	public String getEmail(){
	    return Window.Location.getParameter(Constants.EMAIL);
	}
	public String getConfirmationKey(){

	    return Window.Location.getParameter(Constants.CONFIRMATION_KEY);
	}
	@Override
	public void setName(String name) {

	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}
	@Override
	public void showConfirmingMessage() {
		confirmingMessage.show();
	}
	@Override
	public void hideConfirmingMessage() {
		confirmingMessage.hide();
		
	}
	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

}
