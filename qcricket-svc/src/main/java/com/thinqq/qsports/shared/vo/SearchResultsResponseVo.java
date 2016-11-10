package com.thinqq.qsports.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.QSportsResponseVo;

public class SearchResultsResponseVo extends QSportsResponseVo {

	public static class SearchResult implements Serializable{

		private static final long serialVersionUID = -2790337958623373466L;
		
		private String searchMatch;
		private NameAndKey link;
		private String additionalInformation;
		private String infoLine1;
		
		
		public SearchResult(String searchMatch, NameAndKey link,
				String additionalInformation, String infoLine1) {
			super();
			this.searchMatch = searchMatch;
			this.link = link;
			this.additionalInformation = additionalInformation;
			this.infoLine1 = infoLine1;
		}
		public SearchResult(){}
		public SearchResult(String searchMatch, NameAndKey link,
				String additionalInformation) {
			super();
			this.searchMatch = searchMatch;
			this.link = link;
			this.additionalInformation = additionalInformation;
		}
		public String getSearchMatch() {
			return searchMatch;
		}
		public void setSearchMatch(String searchMatch) {
			this.searchMatch = searchMatch;
		}
		public NameAndKey getLink() {
			return link;
		}
		public void setLink(NameAndKey link) {
			this.link = link;
		}
		public String getAdditionalInformation() {
			return additionalInformation;
		}
		public void setAdditionalInformation(String additionalInformation) {
			this.additionalInformation = additionalInformation;
		}
		public String getInfoLine1() {
			return infoLine1;
		}
		public void setInfoLine1(String infoLine1) {
			this.infoLine1 = infoLine1;
		}
	}
	private static final long serialVersionUID = -5357844250512160515L;

	private List<SearchResult> results;
	private boolean invlaidSearchKey;
	
	public boolean isInvlaidSearchKey() {
		return invlaidSearchKey;
	}
	public void setInvlaidSearchKey(boolean invlaidSearchKey) {
		this.invlaidSearchKey = invlaidSearchKey;
	}
	public List<SearchResult> getResults() {
		return results;
	}

	public void setResults(List<SearchResult> results) {
		this.results = results;
	}
	
	
}
