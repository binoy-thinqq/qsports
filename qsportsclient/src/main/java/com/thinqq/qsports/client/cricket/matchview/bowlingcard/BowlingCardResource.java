package com.thinqq.qsports.client.cricket.matchview.bowlingcard;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.thinqq.qsports.client.common.IComponentResource;

public interface BowlingCardResource extends IComponentResource,ClientBundle{

	@Source("bowlingcard.css")
	public Style style();
	
	interface Style extends CssResource{
		String battingHeading();
	}
	interface BattingCardConstants extends Constants{
		
	@DefaultStringValue("'s bowling")
	String battingHeading();
	}
}
