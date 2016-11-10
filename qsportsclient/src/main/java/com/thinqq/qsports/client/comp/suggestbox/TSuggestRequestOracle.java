package com.thinqq.qsports.client.comp.suggestbox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.thinqq.qsports.client.svc.QSportsCricketSVC;
import com.thinqq.qsports.client.svc.QSportsCricketSVCAsync;

public class TSuggestRequestOracle extends SuggestOracle{
	
	int requestId;
	int minChar = 1;
	
	 public boolean isDisplayStringHTML() {
         return true;
     }
	QSportsCricketSVCAsync cricketSVC = GWT.create(QSportsCricketSVC.class);
	public TSuggestRequestOracle(int requestId,int minChar) {
		this.requestId = requestId;
		this.minChar = minChar;
	}

	@Override
	public void requestSuggestions(SuggestOracle.Request req,SuggestOracle.Callback callback){
		String numberOfChar = req.getQuery();
		if(numberOfChar != null && numberOfChar.length() >= minChar){
			cricketSVC.getSuggestions(req, new ItemSuggestCallback(req, callback));
		}
		
	}
}

class ItemSuggestCallback implements AsyncCallback {

    private SuggestOracle.Request req;
    private SuggestOracle.Callback callback;

    public ItemSuggestCallback(SuggestOracle.Request _req,
            SuggestOracle.Callback _callback) {
        req = _req;
        callback = _callback;
    }

    public void onFailure(Throwable error) {
        callback.onSuggestionsReady(req, new SuggestOracle.Response());
    }

    public void onSuccess(Object retValue) {
        callback.onSuggestionsReady(req,
                (SuggestOracle.Response) retValue);
    }
}

