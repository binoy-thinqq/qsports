package com.thinqq.qsports.client.userprofile.match.edit;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.thinqq.qsports.client.common.ClientConstants;
import com.thinqq.qsports.client.common.MessageUtil;
import com.thinqq.qsports.client.svc.QSportsSecureServices;
import com.thinqq.qsports.client.svc.QSportsSecureServicesAsync;
import com.thinqq.qsports.shared.vo.SearchResultsRequestVo;
import com.thinqq.qsports.shared.vo.SearchResultsResponseVo;
import com.thinqq.qsports.shared.vo.SearchResultsResponseVo.SearchResult;

public class PlayerSuggestOracle extends SuggestOracle {

	private static PlayerSuggestOracle s_instance = new PlayerSuggestOracle();
	private PlayerSuggestOracle(){}
	public static PlayerSuggestOracle getInstance(){
		return s_instance;
	}
	private QSportsSecureServicesAsync secureServices = GWT.create(QSportsSecureServices.class);
	@Override
	public void requestSuggestions(final Request request, final Callback callback) {
		SearchResultsRequestVo searchRequest = new SearchResultsRequestVo();
		searchRequest.setSearchKey(request.getQuery());
		secureServices.searchPlayerNameStartingWith(searchRequest, new AsyncCallback<SearchResultsResponseVo>() {
			
			@Override
			public void onSuccess(SearchResultsResponseVo result) {
				if(callback != null &&  result.getResults()!=null){
					List<PlayerSuggestion> players = new ArrayList<PlayerSuggestion>();
					for(SearchResult searchEntry: result.getResults()){
						String playerKey = "";
						if(searchEntry.getLink() != null){
							playerKey = searchEntry.getLink().getKey();
						}
						players.add(new PlayerSuggestion(searchEntry.getSearchMatch(), 
								searchEntry.getAdditionalInformation(),playerKey));
					}
					Response response = new Response();
					response.setSuggestions(players);
					callback.onSuggestionsReady(request, response);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);
			}
		});
	}
	@Override
	public boolean isDisplayStringHTML() {
		return true;
	}

}
