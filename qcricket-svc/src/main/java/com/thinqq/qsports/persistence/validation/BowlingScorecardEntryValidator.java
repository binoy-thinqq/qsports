package com.thinqq.qsports.persistence.validation;

import java.util.ArrayList;
import java.util.List;

import com.thinqq.qsports.persistence.dto.BowlingScorecardEntryVo;
import com.thinqq.qsports.server.error.ErrorConstants;
import com.thinqq.qsports.server.error.ErrorInfo;

public class BowlingScorecardEntryValidator implements ValidatorAdapter<BowlingScorecardEntryVo> {

	@Override
	public List<ErrorInfo> validate(BowlingScorecardEntryVo validateObject) {
		//Player Id and Innings not equal to NULL
		
		//Maiden shall not exceed number of overs
		//Wickets not greater than 10
		List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();
		if (validateObject.getMatchPlayerId() == null 
				&& validateObject.getInningsId() == null) {
			errorInfos.add(new ErrorInfo(ErrorConstants.INVALID_BOWLING_SCORECARD_ENTRY, "Player and Innings Id cannot be omitted"));
		} else if ((validateObject.getMaiden() != null && validateObject.getOvers() != null)
				&& validateObject.getMaiden() > validateObject.getOvers()) {
			errorInfos.add(new ErrorInfo(ErrorConstants.INVALID_NUMBER_OF_MAIDEN_OVERS, "Maiden overs cannot exceed the number of overs bowled"));
		} else if (validateObject.getOvers() == null || !isValidOvers(validateObject.getOvers())) {
			errorInfos.add(new ErrorInfo(ErrorConstants.INVALID_NUMBER_OF_OVERS, "Overs cannot have more than 6 balls"));
		} 
		return errorInfos;
	}
	
	private boolean isValidOvers(Double overs) {
		boolean isValid = true;
		String[] oversBowled = overs.toString().split("\\.");
		if (oversBowled.length > 1 && Integer.parseInt(oversBowled[1]) > 5) {
			isValid = false;
		}
		return isValid;
	}

}
