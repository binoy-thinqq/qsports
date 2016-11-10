package com.thinqq.qsports.shared.teamprofile;

import com.thinqq.qsports.shared.QSportsResponseVo;

public class RemoveTeamPlayerResponseVo extends QSportsResponseVo {
private static final long serialVersionUID = -3467726043597169830L;
private Boolean removedSuccessfully;
private Boolean noOwnerPrivilege;
private Boolean alreadyRemoved;
private Boolean cannotRemoveTheOnlyOwner;

public Boolean getCannotRemoveTheOnlyOwner() {
	return cannotRemoveTheOnlyOwner;
}
public void setCannotRemoveTheOnlyOwner(Boolean cannotRemoveTheOnlyOwner) {
	this.cannotRemoveTheOnlyOwner = cannotRemoveTheOnlyOwner;
}
public Boolean getRemovedSuccessfully() {
	return removedSuccessfully;
}
public void setRemovedSuccessfully(Boolean removedSuccessfully) {
	this.removedSuccessfully = removedSuccessfully;
}
public Boolean getNoOwnerPrivilege() {
	return noOwnerPrivilege;
}
public void setNoOwnerPrivilege(Boolean noOwnerPrivilege) {
	this.noOwnerPrivilege = noOwnerPrivilege;
}
public Boolean getAlreadyRemoved() {
	return alreadyRemoved;
}
public void setAlreadyRemoved(Boolean alreadyRemoved) {
	this.alreadyRemoved = alreadyRemoved;
}

}
