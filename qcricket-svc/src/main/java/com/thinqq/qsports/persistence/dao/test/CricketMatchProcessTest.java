package com.thinqq.qsports.persistence.dao.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;

import com.thinqq.qsports.persistence.dto.AddPlayerToMatchResponse;
import com.thinqq.qsports.persistence.dto.CricketMatchResponseVo;
import com.thinqq.qsports.persistence.dto.CricketMatchVo;
import com.thinqq.qsports.persistence.dto.CricketTeamPlayerMatchVo;
import com.thinqq.qsports.persistence.dto.InningsMinResponseVo;
import com.thinqq.qsports.persistence.dto.TossInfoVo;
import com.thinqq.qsports.server.error.UnauthorizedException;
import com.thinqq.qsports.server.process.CricketMatchProcess;

//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//////@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/webservice-servlet.xml" })
@Configurable
public class CricketMatchProcessTest {

	@Autowired
	@Qualifier("cricketMatchProcess")
	CricketMatchProcess cricketMatchProcess;

	@Test
	public void testMethods() {

		//create match
/*		try {
			cricketMatchProcess.saveMatch(getCricketMatchVo(),
					getCricketMatchResponseVo());

		} catch (QSportsServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		//Add players to the match
		/*try {
			
			for(int i=2;i<=13;i++){
				cricketMatchProcess.addPlayerToMatch(getCricketTeamPlayerMatchVo(new Integer(i),25,9,1),
					getAddPlayerToMatchResponse());
				cricketMatchProcess.addPlayerToMatch(getCricketTeamPlayerMatchVo(new Integer(i+10),26,9,1),
						getAddPlayerToMatchResponse());
			}
			
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//Start match
		try {
			cricketMatchProcess.startMatch(getTossInfoVo(), getInningsMinResponseVo());
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Add innings
		/*
		try {
			cricketMatchProcess.addInnings(9, 1, false, getInningsMinResponseVo());
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	private static TossInfoVo getTossInfoVo(){
		TossInfoVo tossInfoVo = new TossInfoVo();
		tossInfoVo.setMatchId(9);
		tossInfoVo.setTossWonBy(1);
		tossInfoVo.setElectedTo(1);
		tossInfoVo.setSignedInUserId(1);
		return tossInfoVo;
	}
	
	private static InningsMinResponseVo getInningsMinResponseVo(){
		return new InningsMinResponseVo();
	}

	private static AddPlayerToMatchResponse getAddPlayerToMatchResponse() {
		AddPlayerToMatchResponse addPlayerToMatchResponse = new AddPlayerToMatchResponse();
		return addPlayerToMatchResponse;
	}

	private static CricketTeamPlayerMatchVo getCricketTeamPlayerMatchVo(Integer profileId,Integer teamId,Integer matchId,Integer signedInUser ) {
		CricketTeamPlayerMatchVo cricketTeamPlayerMatchVo = new CricketTeamPlayerMatchVo();
		cricketTeamPlayerMatchVo.setProfileId(profileId);
		cricketTeamPlayerMatchVo.setTeamId(teamId);
		cricketTeamPlayerMatchVo.setMatchId(matchId);
		cricketTeamPlayerMatchVo.setSignedInUserId(signedInUser);
		return cricketTeamPlayerMatchVo;
	}

	private static CricketMatchVo getCricketMatchVo() {
		CricketMatchVo cricketMatchVo = new CricketMatchVo();
		cricketMatchVo.setTeamId(25);
		cricketMatchVo.setOppTeamId(26);
		cricketMatchVo.setSignedInUserId(1);
		cricketMatchVo.setMatchDate(new Date());
		cricketMatchVo.setGroundName("TEST");
		cricketMatchVo.setGroundLoc("TEST");
		cricketMatchVo.setCity("TEST");
		cricketMatchVo.setCountry("TEST");
		cricketMatchVo.setGroundName("TEST");
		cricketMatchVo.setGroundLoc("TEST");
		return cricketMatchVo;
	}

	private static CricketMatchResponseVo getCricketMatchResponseVo() {
		CricketMatchResponseVo cricketMatchResponseVo = new CricketMatchResponseVo();
		return cricketMatchResponseVo;
	}
}
