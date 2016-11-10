package com.thinqq.qsports.client.cricket.matchview.battingentry;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.thinqq.qsports.client.common.IComponentResource;


public interface BattingEntryResource extends IComponentResource,ClientBundle {

	@Source("battingentry.css")
	public Style style();
	
	interface Style extends CssResource{
		String battingEntry();
		String battingEntryGreen();
	}
	

}
