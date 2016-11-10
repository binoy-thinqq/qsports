package com.thinqq.qsports.client.comp.suggestbox;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class TSuggestBox implements Suggestion,IsSerializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String displayText;
	Long profileId;

	public TSuggestBox(){
		
	}
	public TSuggestBox(String displayText,Long profileId){
		this.displayText = displayText;
		this.profileId = profileId;
	}
	@Override
	public String getDisplayString() {
		return displayText;
	}

	@Override
	public String getReplacementString() {
		return displayText;
	}

}
