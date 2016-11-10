/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thinqq.qsports.client.wireframe;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.client.ClientFactory;
import com.thinqq.qsports.client.Genie;
import com.thinqq.qsports.client.common.CommonResources;
import com.thinqq.qsports.client.events.CreateNewMatchEvent;
import com.thinqq.qsports.client.events.CreateNewTeamEvent;
import com.thinqq.qsports.client.svc.QSportsSecureServices;
import com.thinqq.qsports.client.svc.QSportsSecureServicesAsync;
import com.thinqq.qsports.shared.NameAndKey;

/**
 * Sample implementation of {@link QSportsWireFrame}.
 */
public class QSportsWireFrameImpl extends Composite implements QSportsWireFrame {

	private static CommonResources resource = GWT.create(CommonResources.class);
	private static CommonResources.CommonCss style = resource.getStyle();
	private ClientFactory clientFactory;
	QSportsSecureServicesAsync secureServices = GWT.create(QSportsSecureServices.class);
	
	
	interface Binder extends UiBinder<Widget, QSportsWireFrameImpl> {
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	private Presenter listener;
	@UiField SimplePanel centralArea;
	@UiField FlowPanel messagePanel;
	
//	@UiField
//	Anchor createTeam;
//	@UiField
//	Anchor createMatch;
//	@UiField
//	Image goHome;
//	@UiField
//	Image invite;
	@UiField
	Image logout;
	@UiField
	Label userGreeting;
	
	@UiField
	HTMLPanel tweet;

	@UiField
	MenuBar testMenu;
	
	MenuItem createMatch;
	
	MenuItem createTeam;
	
	
	public QSportsWireFrameImpl(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		style.ensureInjected();
		initWidget(binder.createAndBindUi(this));
		addHandlers();
		HTML tweetContent = new HTML("<a class=\"twitter-timeline\"  href=\"https://twitter.com/CricketQSports/cricketq\"  data-widget-id=\"368013046121115648\">Tweets from @CricketQSports/cricketq</a> " +
		"<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+\"://platform.twitter.com/widgets.js\";fjs.parentNode.insertBefore(js,fjs);}}(document,\"script\",\"twitter-wjs\");</script>");
		//addTeams();
		tweet.add(tweetContent);
		Document doc = Document.get();
	    ScriptElement script = doc.createScriptElement();
	    script.setSrc("http://platform.twitter.com/widgets.js");
	    script.setType("text/javascript");
	    script.setLang("javascript");
	    doc.getBody().appendChild(script);
	    
	    // TO TEST

	    SafeHtml homeImage = SafeHtmlUtils.fromTrustedString("<img src='/images/icon-home.png'/>");
	    SafeHtml addMatchImage = SafeHtmlUtils.fromTrustedString("<img src='/images/icon-batsman.png'/>");
	    
	    SafeHtml peopleImage = SafeHtmlUtils.fromTrustedString("<img src='/images/icon-people.png'/>");
//	    MenuBar home = new MenuBar(true);
	    Command homeCommand = new Command() {
			
			@Override
			public void execute() {
				Window.alert("fire the view request event to show the home view");
				
			}
		};
		
		Command peopleCommand = new Command() {
			
			@Override
			public void execute() {
				// TODO Navigate to people tab.
				
			}
		};
	    MenuBar addMatch = new MenuBar(true);
	    
	    testMenu.addItem(homeImage , homeCommand);
	    testMenu.addItem(addMatchImage, addMatch);
	    testMenu.addItem(peopleImage, peopleCommand);
	    
	    SafeHtml createMatchText = SafeHtmlUtils.fromSafeConstant("New Match");
	    SafeHtml createTeamText = SafeHtmlUtils.fromSafeConstant("New Team");
	    createMatch = new MenuItem(createMatchText);
	    createMatch = new MenuItem(createMatchText);
	    createMatch.addStyleName("fancybox");
	    createTeam = new MenuItem(createTeamText);
	    addMatch.addItem(createMatch);
	    addMatch.addItem(createTeam);
	    
	    createTeam.setCommand(new Command() {

			@Override
			public void execute() {
				Genie.getEventmanager().fireEvent(new CreateNewTeamEvent());
				
			}
		});
	    
	    createMatch.setCommand(new Command() {
			
			@Override
			public void execute() {
				Genie.getEventmanager().fireEvent(new CreateNewMatchEvent());
				
			}
		});
	}

	private void addHandlers() {
//		createTeam.setCommand(new Command() {
//
//			@Override
//			public void execute() {
//				Genie.getEventmanager().fireEvent(new CreateNewTeamEvent());
//				
//			}
//		});
//		createMatch.setCommand(new Command() {
//			
//			@Override
//			public void execute() {
//				Genie.getEventmanager().fireEvent(new CreateNewMatchEvent());
//				
//			}
//		});
//		createTeam.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent arg0) {
//				Genie.getEventmanager().fireEvent(new CreateNewTeamEvent());
//			}
//		});
//		createMatch.addClickHandle(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent arg0) {
//				Genie.getEventmanager().fireEvent(new CreateNewMatchEvent());
//			}
//		});
		logout.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				Window.Location.replace(Window.Location.getProtocol()+"//"+Window.Location.getHost()+"/OAuthLogoutServlet");
			}
		});
		userGreeting.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				String style = DOM.getElementById("greetingMenu").getStyle().getDisplay();
				Window.alert(style);
			}
		});
		
		
	}

//	@UiHandler("addTeam")
//	protected void addTeamHandler(final ClickEvent event) {
//		createMatch.setVisible(true);
//		createTeam.setVisible(true);
//	}
	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}
	public SimplePanel getCentralArea(){
		return centralArea;
	}
	
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void clean() {
		userGreeting.setText("");
//		createTeam.setVisible(false);
//		createMatch.setVisible(false);
//		goHome.setVisible(false);
//		invite.setVisible(false);
		logout.setVisible(false);
	}

	@Override
	public void setUser(NameAndKey user) {
		
		if(user != null){
			userGreeting.setText(user.getDisplayName());
//			createTeam.setVisible(true);
//			createMatch.setVisible(true);
//			goHome.setVisible(true);
//			invite.setVisible(true);
			logout.setVisible(true);
		}
	}

	@Override
	public FlowPanel getMessagePanel() {
		return messagePanel;
	}
	

}
