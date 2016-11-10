package com.thinqq.qsports.shared.teamprofile;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class RemoveTeamPlayerRequestVo extends QSportsRequestVo {
	
private static final long serialVersionUID = -879512528293476135L;

	
private String teamKey;

private String profileKey;

private boolean removeOnlyOwnership;

public String getTeamKey() {
	return teamKey;
}

public void setTeamKey(String teamKey) {
	this.teamKey = teamKey;
}

public String getProfileKey() {
	return profileKey;
}

public void setProfileKey(String profileKey) {
	this.profileKey = profileKey;
}

public boolean isRemoveOnlyOwnership() {
	return removeOnlyOwnership;
}

public void setRemoveOnlyOwnership(boolean removeOnlyOwnership) {
	this.removeOnlyOwnership = removeOnlyOwnership;
}


}
