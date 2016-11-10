package com.thinqq.qsports.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.thinqq.qsports.client.common.CommonResources;
import com.thinqq.qsports.client.common.MessageUtil;
import com.thinqq.qsports.client.events.GlobalEventInitialiser;
import com.thinqq.qsports.client.svc.QSportsSecureServices;
import com.thinqq.qsports.client.svc.QSportsSecureServicesAsync;
import com.thinqq.qsports.client.userprofile.UserProfilePlace;
import com.thinqq.qsports.client.wireframe.QSportsWireActivity;
import com.thinqq.qsports.client.wireframe.QSportsWireFrame;
import com.thinqq.qsports.client.wireframe.QSportsWirePlace;
import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Qsportsclient implements EntryPoint {
    private Place defaultPlace = new UserProfilePlace(""); 
   CommonResources RESOURCES = GWT.create(CommonResources.class);
   CommonResources.CommonCss STYLE = RESOURCES.getStyle();
	QSportsSecureServicesAsync secureServices = GWT.create(QSportsSecureServices.class);
	/**
	 * This is the entry point method.
	 */ 
	public void onModuleLoad() {
			//STYLE.ensureInjected();
			ClientFactory clientFactory = GWT.create(ClientFactory.class);  
			QSportsWireFrame appWidget = clientFactory.getThinqQWireFrame();
			appWidget.setPresenter(new QSportsWireActivity(new QSportsWirePlace(""), clientFactory));
			EventBus eventBus = clientFactory.getEventBus(); 
			EventManager<EventHandler> eventManager = EventManager.getInstance();
			eventManager.setEventBus(eventBus);
			PlaceController placeController = clientFactory.getPlaceController();        
			Genie.setClientFactory(clientFactory);
			Genie.setPlaceController(placeController);
			Genie.setEventManager(eventManager);
			loadSignedInUserDetails(appWidget);
			MessageUtil.initialise();
			GlobalEventInitialiser.initialise();
			// Start ActivityManager for the main widget with our ActivityMapper       
			ActivityMapper activityMapper = new QSportsActivityMapper(clientFactory);        
			ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);        
			activityManager.setDisplay(appWidget.getCentralArea());        
			// Start PlaceHistoryHandler with our PlaceHistoryMapper        
			QSportsPlaceHistoryMapper historyMapper= GWT.create(QSportsPlaceHistoryMapper.class);        
			PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);        
			historyHandler.register(placeController, eventBus, defaultPlace);  
			//RootPanel.get().setStylePrimaryName("theroot");
			RootPanel.get().add(appWidget);
			// Goes to the place represented on URL else default place        
			historyHandler.handleCurrentHistory();
	}
	private void loadSignedInUserDetails(final QSportsWireFrame wireframe) {
		secureServices.getSignedInUserProfile(new AsyncCallback<UserProfileResponseVo>() {
			
			@Override
			public void onSuccess(UserProfileResponseVo response) {
				Genie.setUser(response);
				wireframe.setUser(new NameAndKey(response.getName(), response.getUserKey()));
			}
			@Override
			public void onFailure(Throwable error) {
				//show FATAL Error
			}
		});
	}

}
