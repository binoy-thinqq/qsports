package com.thinqq.qsports.client.teamprofile;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.thinqq.qsports.client.Genie;
import com.thinqq.qsports.client.common.CommonResources;
import com.thinqq.qsports.client.common.ConfirmationPopUp;
import com.thinqq.qsports.client.cricket.widgets.CricketCell;
import com.thinqq.qsports.client.uidatamodel.CricketMatchMinUIData;
import com.thinqq.qsports.client.userprofile.UserProfilePlace;
import com.thinqq.qsports.client.userprofile.min.CricketPlayerProfileMin;
import com.thinqq.qsports.shared.CountryList;
import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.teamprofile.GetTeamPlayerProfileResponseVo.TeamPlayerMinDetails;
import com.thinqq.qsports.shared.teamprofile.GetTeamStatisticsResponseVo;
import com.thinqq.qsports.shared.teamprofile.RemoveTeamPlayerRequestVo;
import com.thinqq.qsports.shared.teamprofile.TeamProfileResponseVo;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;

public class TeamProfileViewImpl extends Composite implements TeamProfileView{
	private TeamProfileView.Presenter listener;
	private static TeamProfileViewImplUiBinder uiBinder = GWT
			.create(TeamProfileViewImplUiBinder.class);
	private static String TEAM_NAME_PREFIX="Team - ";
	private static final String DOE_LABEL="Date Estd.: ";
	private static final String CITY_LABEL="City: ";
	private static final String STATE_LABEL="State: ";
	private static final String COUNTRY_LABEL="Country: ";
	private static final String OWNER_LABEL="(Owner)";
	CommonResources RESOURCES = GWT.create(CommonResources.class);
	private static CricketCell cricketCellTemplates = GWT.create(CricketCell.class);
	
	@UiField(provided=true) CellList<TeamPlayerMinDetails> playersList = new CellList<TeamPlayerMinDetails>(new AbstractCell<TeamPlayerMinDetails>(){
		@Override
		public void render(Context context, TeamPlayerMinDetails playerData, SafeHtmlBuilder sb) {
			String playerName = playerData.getPlayer().getDisplayName();
			if(playerData.isOwner()){
				playerName += " "+OWNER_LABEL;
			}
			if(playerData.getPictureURL() == null){
				playerData.setPictureURL("");
			}
			sb.append(cricketCellTemplates.playerCell(playerData.getPictureURL(), playerName,
					UserProfilePlace.TOKENIZER.getToken(new UserProfilePlace(playerData.getPlayer().getKey())),
					playerData.getBatting(), playerData.getBowling(), playerData.getMatches(), playerData.getRuns(),playerData.getWkts()));
		}
	});
	@UiField(provided=true) CellList<CricketMatchMinUIData> matchesList = new CellList<CricketMatchMinUIData>(new AbstractCell<CricketMatchMinUIData>(){
		@Override
		public void render(Context context, CricketMatchMinUIData matchData, SafeHtmlBuilder sb) {
			sb.append(cricketCellTemplates.matchCell(70, "matchcell", matchData.getTeam1Name(), matchData.getTeam2Name(), matchData.getMatchData(), matchData.getWonByMatchData(), matchData.getInnings1Data(), matchData.getInnings2Data(), matchData.getVenueDetail(), "teamname"));
		}
	});
	@UiField
	SimplePager playerListPager;
	
	private AddTeamPlayer addTeamPlayer;
	
	private EditTeamProfile editTeamProfile;
	
	ListDataProvider<TeamPlayerMinDetails> playerListDataProvider = new ListDataProvider<TeamPlayerMinDetails>();
	

    final SingleSelectionModel<TeamPlayerMinDetails> playerListSelectionModel = new SingleSelectionModel<TeamPlayerMinDetails>(
    		PLAYER_LIST_KEY_PROVIDER);
	
    public static final ProvidesKey<TeamPlayerMinDetails> PLAYER_LIST_KEY_PROVIDER = new ProvidesKey<TeamPlayerMinDetails>() {
        @Override
        public Object getKey(TeamPlayerMinDetails item) {
        	if(item!=null && item.getPlayer()!=null && item.getPlayer().getKey()!=null){
        		return item.getPlayer().getKey();
        	}
        return null;
      }
    };
	
	interface TeamProfileViewImplUiBinder extends UiBinder<Widget, TeamProfileViewImpl> {
	}

	@UiField
	Label name;
	@UiField
	Label about;
	@UiField
	Label city;
	@UiField
	Label state;
	@UiField
	Label country;
	@UiField
	Label tDoe;
	@UiField
	FlowPanel owners;
	@UiField
	Image loadingProfileImage;
	@UiField
	Button editProfile;

	@UiField
	Button addPlayerToTeam;
	
	public TeamProfileViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		loadingProfileImage.setResource(RESOURCES.loadingImageSmall());
		addActionHandlers();
		playerListPager.setDisplay(playersList);
		playersList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		playerListSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	      public void onSelectionChange(SelectionChangeEvent event) {
	    	  if(playerListSelectionModel.getSelectedObject() != null){
	    		  listener.onPlayerSelection(playerListSelectionModel.getSelectedObject().getCricketProfileKey(), 
	    				  playerListSelectionModel.getSelectedObject().getPlayer());
	    	  }
	      }
	    });
	    playersList.setSelectionModel(playerListSelectionModel);
		List<CricketMatchMinUIData> matchDataList = new ArrayList<CricketMatchMinUIData>();
		for(int i=0;i<5;i++){
			CricketMatchMinUIData matchData = new CricketMatchMinUIData();
			matchData.setMatchBattingOneliner("Batting : 23(8 balls) SR : 124 ");
			matchData.setMatchBowlingOneliner("Bowling : 23/4 (3.5 overs) Econ : 4.56");
			matchData.setTeam1Name("Weekend Warriors");
			matchData.setTeam2Name("Fear the XI");
			matchData.setMatchData("07 July 2012 - Day/Night - T20") ;
			matchData.setWonByMatchData("Weekend Warriors won by 62 runs");
			matchData.setInnings1Data("Innings 1 - Weekend Warriors 182 for 8 in 19.4 overs");
			matchData.setInnings2Data("Innings 2 - Fear the XI 120 for 10 in 16 overs");
			matchData.setVenueDetail("Mat Pitch- Seva sadan, Tambaram, Chennai, Tamil nadu, India");
			matchDataList.add(matchData);
		}
		ListDataProvider<CricketMatchMinUIData> matchDataProvider = new ListDataProvider<CricketMatchMinUIData>(matchDataList);
		matchDataProvider.addDataDisplay(matchesList);
	}

	private void addActionHandlers() {
		addPlayerToTeam.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(addTeamPlayer == null){
					addTeamPlayer = Genie.getClientfactory().getAddTeamPlayerPopUp();
				}
				addTeamPlayer.setListener((AddTeamPlayer.Presenter)listener);
				addTeamPlayer.setPixelSize(600, 50);
				addTeamPlayer.center();
			}
		});
		editProfile.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				listener.onEditTeamClick();
			}
		});
	}

	
	
	@Override
	public void setName(String helloName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;

	}

	@Override
	public void setTeamDetails(TeamProfileResponseVo details) {
		if(details.getName()!=null){
			name.setText(TEAM_NAME_PREFIX+details.getName());
		}else{
			name.setText(" - ");
		}
		if(details.getDescription()!=null){
			about.setText(details.getDescription());
		}else{
			about.setText("");
		}
		if(details.getDateOfEstdString()!=null){
			tDoe.setText(DOE_LABEL+details.getDateOfEstdString());
		}else{
			tDoe.setText(DOE_LABEL+" - ");
		}
		List<NameAndKey> ownersList = details.getOwners();
		owners.clear();
		for(NameAndKey owner: ownersList){
			Hyperlink link = new Hyperlink(owner.getDisplayName(), UserProfilePlace.TOKENIZER.getToken(new UserProfilePlace(owner.getKey())));
			link.setStyleName(RESOURCES.getStyle().ownerLinkStyle(), true);
			owners.add(link);
			Button removeButton = new Button("X");
			removeButton.setStyleName(RESOURCES.getStyle().ownerCloseButtonStyle(), true);
			removeButton.addClickHandler(new OwnerRemoveClickHandler(owner.getMiscInfo(), details.getTeamKey(), listener));
			owners.add(removeButton);
		}
		if(details.getCity()!=null){
			city.setText(CITY_LABEL+details.getCity());
		}else{
			city.setText(CITY_LABEL+" - ");
		}
		if(details.getState()!=null){
			state.setText(STATE_LABEL+details.getState());
		}else{
			state.setText(STATE_LABEL+" - ");
		}
		if(details.getCountry()!=null){
			country.setText(COUNTRY_LABEL+CountryList.getListCountry().get(details.getCountry()));
		}else{
			country.setText(COUNTRY_LABEL+" - ");
		}
		editProfile.setVisible(details.isAllowEdit());
		addPlayerToTeam.setVisible(details.isAllowEdit());
		loadingProfileImage.setVisible(false);
	}


	@UiField 
	Label matchesT20;
	@UiField 
	Label winT20;
	@UiField 
	Label lossT20;
	@UiField 
	Label tiedT20;
	@UiField 
	Label noResultT20;
	@UiField 
	Label hsT20;

	@UiField 
	Label matchesOD;
	@UiField 
	Label winOD;
	@UiField 
	Label lossOD;
	@UiField 
	Label tiedOD;
	@UiField 
	Label noResultOD;
	@UiField 
	Label hsOD;

	@UiField 
	Label matchesTst;
	@UiField 
	Label winTst;
	@UiField 
	Label lossTst;
	@UiField 
	Label tiedTst;
	@UiField 
	Label noResultTst;
	@UiField 
	Label hsTst;

	@UiField 
	Label winPercentage;
	
	@UiField
	Image loadingStatsImage;

	@Override
	public void setTeamStatistics(GetTeamStatisticsResponseVo statistics) {
		List<String> t20Stats = statistics.getT20Statistics();
		matchesT20.setText(t20Stats.get(0));
		winT20.setText(t20Stats.get(1));
		lossT20.setText(t20Stats.get(2));
		tiedT20.setText(t20Stats.get(3));
		noResultT20.setText(t20Stats.get(4));
		hsT20.setText(t20Stats.get(5));

		List<String> oDStats = statistics.getoDStatistics();
		matchesOD.setText(oDStats.get(0));
		winOD.setText(oDStats.get(1));
		lossOD.setText(oDStats.get(2));
		tiedOD.setText(oDStats.get(3));
		noResultOD.setText(oDStats.get(4));
		hsOD.setText(oDStats.get(5));

		List<String> testStats = statistics.getTestStatistics();
		matchesTst.setText(testStats.get(0));
		winTst.setText(testStats.get(1));
		lossTst.setText(testStats.get(2));
		tiedTst.setText(testStats.get(3));
		noResultTst.setText(testStats.get(4));
		hsTst.setText(testStats.get(5));
		
		winPercentage.setText(statistics.getWinPercentage());
		loadingStatsImage.setVisible(false);
	}

	@UiField
	Image loadingPlayers;
	
	@UiField
	CricketPlayerProfileMin minProfile;
	
	@Override
	public void setTeamPlayerList(List<TeamPlayerMinDetails> playerListData) {
		//playerListDataProvider.removeDataDisplay(playersList);
		playerListDataProvider.setList(playerListData);
		if (!playerListDataProvider.getDataDisplays().contains(playersList)){
			playerListDataProvider.addDataDisplay(playersList);
		}
		playerListDataProvider.flush();
		if(!playerListData.isEmpty()){
			TeamPlayerMinDetails playerMinDetails = playerListData.get(0);
			playerListSelectionModel.setSelected(playerMinDetails, true);
		}
		playersList.setSelectionModel(playerListSelectionModel);
		loadingPlayers.setVisible(false);
	}

	@Override
	public void setTeamPlayerStatistics(List<String> t20Batting,
			List<String> oDBatting, List<String> testBatting,
			List<String> t20Bowling, List<String> oDBowling,
			List<String> testBowling, NameAndKey user, String cricketProfileKey) {
		minProfile.clean();
		minProfile.setListener((CricketPlayerProfileMin.Presenter)listener);
		minProfile.setUser(user);
		minProfile.setCricketProfileKey(cricketProfileKey);
		minProfile.setBattingStatistics(t20Batting, oDBatting, testBatting);
		minProfile.setBowlingStatistics(t20Bowling, oDBowling, testBowling);
	}

	@Override
	public void setPlayerDetailsOnAddPlayerPopup(UserProfileResponseVo response) {
		addTeamPlayer.setUserDetails(response);
	}

	@Override
	public void showEditTeamProfile(TeamProfileResponseVo details, String teamKey) {
		if(editTeamProfile == null){
			editTeamProfile = Genie.getClientfactory().getEditTeamProfilePopUp();
		}
		editTeamProfile.setListener((EditTeamProfile.Presenter)listener);
		editTeamProfile.setTeamDetails(details);
		if(teamKey != null){
			editTeamProfile.setTeamProfileKey(teamKey);
		}
		editTeamProfile.setPixelSize(500, 300);
		editTeamProfile.center();
		
	}

	@Override
	public void clean() {
		
		//Initialize Team details
		name.setText(" - ");
		about.setText("");
		tDoe.setText(DOE_LABEL+" - ");
		owners.clear();
		city.setText(CITY_LABEL+" - ");
		state.setText(STATE_LABEL+" - ");
		country.setText(COUNTRY_LABEL+" - ");
		editProfile.setVisible(false);
		addPlayerToTeam.setVisible(false);
		loadingProfileImage.setVisible(true);
		
		//Initialize Team statistics
		matchesT20.setText("-");
		winT20.setText("-");
		lossT20.setText("-");
		tiedT20.setText("-");
		noResultT20.setText("-");
		hsT20.setText("-");

		matchesOD.setText("-");
		winOD.setText("-");
		lossOD.setText("-");
		tiedOD.setText("-");
		noResultOD.setText("-");
		hsOD.setText("-");

		matchesTst.setText("-");
		winTst.setText("-");
		lossTst.setText("-");
		tiedTst.setText("-");
		noResultTst.setText("-");
		hsTst.setText("-");
		
		winPercentage.setText(" - ");
		loadingStatsImage.setVisible(true);
		if(playerListSelectionModel.getSelectedObject() != null){
			playerListSelectionModel.setSelected(playerListSelectionModel.getSelectedObject(), false);
		}
		if(playerListDataProvider.getDataDisplays() != null && playerListDataProvider.getDataDisplays().contains(playersList)){
			playerListDataProvider.removeDataDisplay(playersList);
		}
		playersList.setSelectionModel(null);
		loadingPlayers.setVisible(true);
		minProfile.clean();
	}
	private static class OwnerRemoveClickHandler implements ClickHandler{
		private String ownerProfileKey;
		private String teamKey;
		TeamProfileView.Presenter listener;
		public OwnerRemoveClickHandler(String ownerProfileKey, String teamKey, TeamProfileView.Presenter listener) {
			super();
			this.ownerProfileKey = ownerProfileKey;
			this.teamKey = teamKey;
			this.listener = listener;
		}

		@Override
		public void onClick(ClickEvent event) {
			ConfirmationPopUp popUp = new ConfirmationPopUp(
					"Remove Ownership", 
					"Are you sure you want to remove the owner?", 
					new ClickHandler() {								
						@Override
						public void onClick(ClickEvent event) {
							RemoveTeamPlayerRequestVo request = new RemoveTeamPlayerRequestVo();
							request.setProfileKey(ownerProfileKey);
							request.setTeamKey(teamKey);
							request.setRemoveOnlyOwnership(true);
							listener.onRemoveOwner(request);
						}
					});
			popUp.show();
			popUp.center();
		}
	}

}
