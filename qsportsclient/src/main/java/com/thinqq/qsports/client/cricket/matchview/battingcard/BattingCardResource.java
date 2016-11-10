package com.thinqq.qsports.client.cricket.matchview.battingcard;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.thinqq.qsports.client.common.IComponentResource;

public interface BattingCardResource extends IComponentResource,ClientBundle{

	@Source("battingcard.css")
	public Style style();
	
	interface Style extends CssResource{
		String battingHeading();
	}
	interface BattingCardConstants extends Constants{
		
	@DefaultStringValue("'s batting")
	String battingHeading();
	}
}
