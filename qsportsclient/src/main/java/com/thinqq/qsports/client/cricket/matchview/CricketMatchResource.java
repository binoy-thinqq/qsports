package com.thinqq.qsports.client.cricket.matchview;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.thinqq.qsports.client.common.IComponentResource;

public interface CricketMatchResource extends ClientBundle,IComponentResource {
	@Source("cricketmatchview.css")
	public CricketMatchViewStyle style();
	
	public interface CricketMatchViewStyle extends CssResource{
		public String matchsummarytable();
		public String matchsummarymainline();
		public String matchViewPanel();
	}
}
