package com.thinqq.qsports.shared;

import java.io.Serializable;

public class NameAndKey implements Serializable {

	private static final long serialVersionUID = 5799866682358914685L;
	private String displayName;
	private String key;
	private String miscInfo;
	
	public NameAndKey(){
	}
	public NameAndKey(String displayName, String key, String miscInfo) {
		super();
		this.displayName = displayName;
		this.key = key;
		this.miscInfo = miscInfo;
	}
	public NameAndKey(String displayName, String key) {
		super();
		this.displayName = displayName;
		this.key = key;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMiscInfo() {
		return miscInfo;
	}
	public void setMiscInfo(String miscInfo) {
		this.miscInfo = miscInfo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		NameAndKey other = (NameAndKey) obj;
		if (key == null) {
			if (other.key != null){
				return false;
			}
		} else if (!key.equals(other.key)){
			return false;
		}
		return true;
	}

}
