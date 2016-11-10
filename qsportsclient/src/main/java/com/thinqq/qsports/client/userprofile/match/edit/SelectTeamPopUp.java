package com.thinqq.qsports.client.userprofile.match.edit;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.thinqq.qsports.client.common.MessageUtil;
import com.thinqq.qsports.client.common.NameAndKeyListComponent;
import com.thinqq.qsports.client.core.ICleanable;
import com.thinqq.qsports.shared.IdUtil;
import com.thinqq.qsports.shared.NameAndKey;

public class SelectTeamPopUp extends PopupPanel implements ICleanable {
	
	private static String SELECT_TEAM_PREFIX= "Select players for ";

	private static SelectTeamPopUpUiBinder uiBinder = GWT
			.create(SelectTeamPopUpUiBinder.class);
	
	interface Presenter {
		void saveTeamList(String matchKey, NameAndKey team, List<String> keys);
	}
	
	@UiField TextBox suggestPlayer;
	
	@UiField FocusPanel focusSuggestPlayerPanel;
	
	@UiField VerticalPanel playerSelectPanel;
	
	@UiField Button btnSaveMatch;
	
	@UiField Button btnCancel;
	
	@UiField Label heading;
	
	private NameAndKey teamKey;
	
	private String matchKey;
	
	private Presenter listener;
	
	private NameAndKeyListComponent nameKeyList;
	
	private SuggestBox suggestionBox;
	
	private HandlerRegistration saveHandler;
	
	private HandlerRegistration cancelHandler;
	
	private HandlerRegistration sBKeyPressReg;
	
	private HandlerRegistration sBSelectionReg;
	
	interface SelectTeamPopUpUiBinder extends UiBinder<Widget, SelectTeamPopUp> {
	}

	public SelectTeamPopUp() {
		setModal(true);
		setGlassEnabled(true);
		setWidget(uiBinder.createAndBindUi(this));
		suggestionBox = new SuggestBox(PlayerSuggestOracle.getInstance(), suggestPlayer);
		suggestionBox.setAutoSelectEnabled(false);
		focusSuggestPlayerPanel.add(suggestionBox);
	}

	public void setTeamPlayerList(String match, NameAndKey team, List<NameAndKey> playersList){
		String teamName = null;
		if(team.getKey() == null){
			teamName = IdUtil.removeNamePrefix(team.getDisplayName());
		} else {
			teamName = team.getDisplayName();
		}
		heading.setText(SELECT_TEAM_PREFIX+teamName);
		this.matchKey = match;
		this.teamKey = team;
		if(nameKeyList == null){
			nameKeyList = NameAndKeyListComponent.getInstance(true);
			nameKeyList.setData(playersList);
		} else {
			nameKeyList.clean();
			nameKeyList.setData(playersList);
		}
		nameKeyList.getElement().setAttribute("style", "padding:20px 10px 10px 10px");
		playerSelectPanel.insert(nameKeyList,2);
		sBKeyPressReg = suggestionBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				int keyCode = event.getNativeKeyCode();
				if(keyCode == KeyCodes.KEY_ENTER){
					String name = suggestionBox.getText();
					if(name != null && !name.trim().isEmpty()){
						name = IdUtil.addNamePrefix(name);
						NameAndKey playerName = new NameAndKey(name, null);
						nameKeyList.add(playerName);
						suggestionBox.setText("");
					}
				}
			}
		});
		
		sBSelectionReg = suggestionBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				PlayerSuggestion player = (PlayerSuggestion)event.getSelectedItem();
				NameAndKey playerName = new NameAndKey(player.getPlayerName(), player.getProfileKey());
				if(nameKeyList.contains(playerName)){
					MessageUtil.showMessage(MessageUtil.WARNING, "Player has already been added");
					suggestionBox.setText("");
					return;
				}
				nameKeyList.add(playerName);
				suggestionBox.setText("");
			}
		});
		final NameAndKey teamNK = this.teamKey;
		saveHandler = btnSaveMatch.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(listener != null){
					List<String> playerList = new ArrayList<String>();
					for(String player : nameKeyList.getKeys()){
						playerList.add(player);
					}
					listener.saveTeamList(matchKey, teamNK, playerList);
					hide();
				}
			}
		});
		
		cancelHandler = btnCancel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
	}
	
	@Override
	public void clean() {
		matchKey = null;
		teamKey = null;
		suggestionBox.setText("");
		if(nameKeyList != null){
			nameKeyList.clean();
		}
		if(saveHandler != null){
			saveHandler.removeHandler();
		}
		if(cancelHandler != null){
			cancelHandler.removeHandler();
		}
		if(sBKeyPressReg != null){
			sBKeyPressReg.removeHandler();
		}
		if(sBSelectionReg != null){
			sBSelectionReg.removeHandler();
		}
	}

	public Presenter getListener() {
		return listener;
	}

	public void setListener(Presenter listener) {
		this.listener = listener;
	}

}
