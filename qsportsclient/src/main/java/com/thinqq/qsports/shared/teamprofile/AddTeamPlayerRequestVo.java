package com.thinqq.qsports.shared.teamprofile;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class AddTeamPlayerRequestVo extends QSportsRequestVo {
	
private static final long serialVersionUID = -8223848997955264357L;

private String teamKey;

private String profileKey;

private boolean isAddAsOwner;



public boolean isAddAsOwner() {
	return isAddAsOwner;
}

public void setAddAsOwner(boolean isAddAsOwner) {
	this.isAddAsOwner = isAddAsOwner;
}

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

}
