package com.thinqq.qsports.client.userprofile;

import com.google.gwt.core.client.JavaScriptObject;

public class UserProfileModel extends JavaScriptObject{
	
	protected UserProfileModel() {}

	  public static native UserProfileModel create(String name) /*-{
	    return {name: name};
	  }-*/;

	  public final native String getName() /*-{
	    return this.name;
	  }-*/;


}
