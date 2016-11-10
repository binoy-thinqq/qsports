package com.thinqq.qsports.client.cricket.matchview.bowlingentry;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.thinqq.qsports.client.common.IComponentResource;


public interface BowlingEntryResource extends IComponentResource,ClientBundle {

	@Source("bowlingentry.css")
	public Style style();
	
	interface Style extends CssResource{
		String battingEntry();
		String battingEntryGreen();
	}
	

}
