package com.thinqq.qsports.shared;

import java.io.Serializable;

public class QsportsVo implements Serializable{
/**
	 * 
	 */
private static final long serialVersionUID = 4506106919953480798L;

private String signedInUserKey;



public String getSignedInUserKey() {
	return signedInUserKey;
}
public void setSignedInUserKey(String userKey) {
	this.signedInUserKey = userKey;
}

}
