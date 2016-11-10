package com.thinqq.qsports.shared.vo;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class SearchResultsRequestVo extends QSportsRequestVo{

	private static final long serialVersionUID = 1L;

	private String searchKey;

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
}
