package com.thinqq.qsports.server.svcimpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.thinqq.qsports.client.comp.suggestbox.TSuggestBox;
import com.thinqq.qsports.client.svc.QSportsCricketSVC;
import com.thinqq.qsports.server.persistence.dao.CricketFormatDAO;
import com.thinqq.qsports.server.persistence.dao.CricketInningsDAO;
import com.thinqq.qsports.server.persistence.dao.CricketMatchDAO;
import com.thinqq.qsports.server.persistence.dao.CricketProfileDAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketFormat;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketInnings;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketMatch;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketScorecard;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketScorecardBattingEntry;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketScorecardBowlingEntry;
import com.thinqq.qsports.server.process.InitialiseProcess;
import com.thinqq.qsports.server.process.RegistrationProcess;
import com.thinqq.qsports.shared.CricketMatchModel;
import com.thinqq.qsports.shared.InningsModel;
import com.thinqq.qsports.shared.QSportsRequestVo;
import com.thinqq.qsports.shared.ScoreCardBattingModel;
import com.thinqq.qsports.shared.ScoreCardBowlingModel;
import com.thinqq.qsports.shared.TeamMemberModel;
import com.thinqq.qsports.shared.TeamModel;
import com.thinqq.qsports.shared.registration.ConfirmationRequestVo;
import com.thinqq.qsports.shared.registration.ConfirmationResponseVo;
import com.thinqq.qsports.shared.registration.RegistrationRequestVo;
import com.thinqq.qsports.shared.registration.RegistrationResponseVo;

public class QSportsCricketSVCImpl extends RemoteServiceServlet implements QSportsCricketSVC {
	Logger logger = Logger.getLogger(QSportsCricketSVCImpl.class
			.getCanonicalName());

	private void initRequest(QSportsRequestVo request) {
		request.setServerName(getThreadLocalRequest().getServerName());

	}

	static CricketMatchDAO dao = new CricketMatchDAO();
	static CricketInningsDAO inningsDao = new CricketInningsDAO();
	static CricketProfileDAO profileDao = new CricketProfileDAO();

	@Override
	public CricketMatchModel getCricketMatch(String matchId) {
		CricketMatchModel matchModel = new CricketMatchModel();
		PersistenceManager pm = dao.getPersistenceManager();
		CricketMatch match = getCricketMatchObject(Long.valueOf(matchId), pm);
		matchModel.setVenue(match.getVenue());
		// matchModel.setFormat(match.getCricketFormat().getFormatName());
		// matchModel = testDataForMatchModel();
		return matchModel;
	}

	private CricketMatch getCricketMatchObject(Long matchId,
			PersistenceManager pm) {
		if (matchId == null) {
			return null;
		}
		return dao.getCricketMatchByCricketMatchId(matchId, pm);
	}

	@Override
	public CricketMatchModel createCricketMatch(
			CricketMatchModel cricketMatchModel) {
		PersistenceManager pm = dao.getPersistenceManager();
		boolean isUpdate = false;
		CricketMatch matchObject = new CricketMatch();

		// matchObject.setCreatedUserKey(UserDAO)
		if (cricketMatchModel.getMatchId() != null) {
			matchObject = getCricketMatchObject(cricketMatchModel.getMatchId(),
					pm);
			isUpdate = true;
		}
		// matchObject.setKey(Key)
		matchObject.setVenue(cricketMatchModel.getVenue());
		
		matchObject.setDate(cricketMatchModel.getDate());
		//matchObject.setLocation(cricketMatchModel.getLocation());
		CricketFormat format = getCricketFormat(cricketMatchModel.getFormat());
		//matchObject.setCricketFormatKey(format.getKey());
		fillTeam(cricketMatchModel, matchObject);
		fillScoreCard(cricketMatchModel, matchObject);
		matchObject.setStatus(cricketMatchModel.getStatus());
		matchObject = dao.update(matchObject, pm);
		cricketMatchModel.setMatchId(matchObject.getKey().getId());
		return cricketMatchModel;
	}

	@Override
	public Boolean initialise() {

		return new InitialiseProcess().initialiseDatabase();
	}

	@Override
	public RegistrationResponseVo registerUser(
			RegistrationRequestVo registrationRequest) {
		initRequest(registrationRequest);
		return RegistrationProcess.getInstance().register(registrationRequest);
	}

	@Override
	public ConfirmationResponseVo confirmUser(ConfirmationRequestVo request) {
		initRequest(request);
		return RegistrationProcess.getInstance().confirmUser(request,
				getThreadLocalRequest());
	}

	private CricketFormat getCricketFormat(String formatName) {

		CricketFormatDAO cricketFormatDao = new CricketFormatDAO();
		CricketFormat format = cricketFormatDao.getCricketFormatByName(
				formatName, cricketFormatDao.getPersistenceManager());
		if (format == null) {
			format = new CricketFormat();
			format.setCreatedTime(Calendar.getInstance().getTime());
			format.setFormatDescription(formatName);
			format.setFormatName(formatName);
			format.setUpdatedTime(Calendar.getInstance().getTime());
			format = cricketFormatDao.insert(format,
					cricketFormatDao.getPersistenceManager());
		}
		return format;
	}

	/**
	 * Populate the Team in CricketMatchObject
	 * 
	 * @param cricketMatchModel
	 * @param matchUpdateObject
	 */
	public void fillTeam(CricketMatchModel cricketMatchModel,
			CricketMatch matchUpdateObject) {
		if (cricketMatchModel.getTeams() == null) {
			return;
		}
		boolean team1 = true;
		for (TeamModel team : cricketMatchModel.getTeams()) {
			List<String> nonProfilePlayers = new ArrayList<String>();
			List<Key> profilePlayers = new ArrayList<Key>();
			if (team.getTeamList() == null) {
				continue;
			}
			for (TeamMemberModel teamMember : team.getTeamList()) {
				if (teamMember.getPlayerProfileId() != null
						&& !"".equals(teamMember.getPlayerProfileId().trim())) {
					// profilePlayers.add
				} else {
					nonProfilePlayers.add(teamMember.getPlayerName());
				}
			}
			if (team1) {
				//matchUpdateObject.setTeam1NoProfilePlayers(nonProfilePlayers);
				//matchUpdateObject.setTeam1Players(profilePlayers);
				team1 = false;
			} else {
				//matchUpdateObject.setTeam2NoProfilePlayers(nonProfilePlayers);
				//matchUpdateObject.setTeam2Players(profilePlayers);
			}
		}
	}

	/**
	 * Fill Scorecard Entry
	 * 
	 * @param matchModel
	 * @param matchUpdateObject
	 */
	public void fillScoreCard(CricketMatchModel matchModel,
			CricketMatch matchUpdateObject) {
		if (matchModel.getInnings() == null) {
			return;
		}
		boolean isTeam1InningsOne = false;
		if (matchModel.getTeamIndex() == 0
				&& matchModel.getChooseTo().equals("Batting")) {
			isTeam1InningsOne = true;
		}
		if (matchModel.getTeamIndex() == 1
				&& matchModel.getChooseTo().equals("Bowling")) {
			isTeam1InningsOne = true;
		}
		for (int i = 0; i < matchModel.getNoOfInnings(); i++) {
			CricketInnings innings = new CricketInnings();
			InningsModel inningsModel = matchModel.getInnings().get(i);
			if (isTeam1InningsOne) {
				populateInnings(innings, inningsModel);
			} else {
				populateInnings(innings, inningsModel);
			}
			isTeam1InningsOne = !isTeam1InningsOne;
			innings = inningsDao.insert(innings,
					inningsDao.getPersistenceManager());
			if (i == 0)
				matchUpdateObject.setInnings1Key(innings.getKey());
			else if (i == 1) {
				matchUpdateObject.setInnings2Key(innings.getKey());
			} else if (i == 2) {
				matchUpdateObject.setInnings3Key(innings.getKey());
			} else if (i == 3) {
				matchUpdateObject.setInnings3Key(innings.getKey());
			}
		}
	}

/*	@Override
	public SigninResponseVo signInUser(SigninRequestVo request) {
		return RegistrationProcess.getInstance().signInUser(request,
				getThreadLocalRequest());
	}*/

	private void populateInnings(CricketInnings innings,
			InningsModel inningsModel) {
		List<ScoreCardBowlingModel> bowlingScorecards = inningsModel
				.getBowlingScoreCard();
		List<ScoreCardBattingModel> battingScorecards = inningsModel
				.getBattingScoreCard();
		CricketScorecard scoreCardPerInnings = new CricketScorecard();
		List<CricketScorecardBowlingEntry> bowlingEntires = new ArrayList<CricketScorecardBowlingEntry>();
		List<CricketScorecardBattingEntry> battingEntries = new ArrayList<CricketScorecardBattingEntry>();
		scoreCardPerInnings.setBattingEntires(battingEntries);
		scoreCardPerInnings.setBowlingEntires(bowlingEntires);
		//innings.setScorecard(scoreCardPerInnings);
		//innings.setName(inningsModel.getTeamName());
		for (ScoreCardBowlingModel bowlingModel : bowlingScorecards) {
			CricketScorecardBowlingEntry entry = new CricketScorecardBowlingEntry();
			entry.setNoProfileString(bowlingModel.getBowlerName());
			entry.setMaidens(bowlingModel.getMaiden());
			entry.setNoballs(bowlingModel.getNoBalls());
			entry.setWides(bowlingModel.getWides());
			entry.setWickets(bowlingModel.getWickets());
			entry.setOvers(bowlingModel.getOversBowled());
			entry.setRuns(bowlingModel.getRunsConcieved());
			bowlingEntires.add(entry);
		}

		for (ScoreCardBattingModel btModel : battingScorecards) {
			CricketScorecardBattingEntry entry = new CricketScorecardBattingEntry();
			entry.setBalls(btModel.getBallsFaced());
			entry.setBowlerNoProfileString(btModel.getBowledBy());
			entry.setRuns(btModel.getRunsScored());
			entry.setFours(btModel.getFours());
			entry.setSixes(btModel.getSixes());
			entry.setNoProfileString(btModel.getBatsman());
			if (btModel.getOutType() > 0) {
				int outTypeId = btModel.getOutType();
				switch (outTypeId) {
				case 1:
					entry.setBowlerNoProfileString(btModel.getBowledBy());
					break;
				case 2:
					entry.setFielder1NoProfileString(btModel.getCaughtBy());
					entry.setFielder2NoProfileString(btModel.getBowledBy());
					break;
				case 3:
					entry.setBowlerNoProfileString(btModel.getBowledBy());
					break;
				default:
					entry.setBowlerNoProfileString(btModel.getBowledBy());
					entry.setFielder1NoProfileString(btModel.getCaughtBy());
					break;
				}
			} else {
				entry.setBowlerNoProfileString(btModel.getBowledBy());
				entry.setFielder1NoProfileString(btModel.getCaughtBy());
			}
			battingEntries.add(entry);
		}

	}

	@Override
	public SuggestOracle.Response getSuggestions(Request req) {
		String query = req.getQuery();
		profileDao.getProfileUsingName(query,
				profileDao.getPersistenceManager());
		SuggestOracle.Response resp = new SuggestOracle.Response();
		List<Suggestion> suggestions = new ArrayList<Suggestion>();
		suggestions.add(new TSuggestBox("Test", 123L));
		suggestions.add(new TSuggestBox("Test2", 1232L));
		suggestions.add(new TSuggestBox("Test3", 1233L));
		suggestions.add(new TSuggestBox("Test4", 1234L));
		suggestions.add(new TSuggestBox("Test5", 1235L));
		resp.setSuggestions(suggestions);
		return resp;
	}
}
