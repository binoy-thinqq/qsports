package com.thinqq.qsports.shared.teamprofile;

import com.thinqq.qsports.shared.QSportsResponseVo;

public class AddTeamPlayerResponseVo extends QSportsResponseVo {
private static final long serialVersionUID = -5265318168412234842L;
private Boolean addedSuccessfully;
private Boolean noOwnerPrivilege;
private Boolean alreadyExists;
public Boolean getAddedSuccessfully() {
	return addedSuccessfully;
}
public void setAddedSuccessfully(Boolean addedSuccessfully) {
	this.addedSuccessfully = addedSuccessfully;
}
public Boolean getNoOwnerPrivilege() {
	return noOwnerPrivilege;
}
public void setNoOwnerPrivilege(Boolean noOwnerPrivilege) {
	this.noOwnerPrivilege = noOwnerPrivilege;
}
public Boolean getAlreadyExists() {
	return alreadyExists;
}
public void setAlreadyExists(Boolean alreadyExists) {
	this.alreadyExists = alreadyExists;
}

}
