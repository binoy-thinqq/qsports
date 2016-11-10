package com.thinqq.qsports.persistence.dto;


public class BowlingScorecardEntryVo extends BaseVo {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2490124628935891865L;

		private Integer bowlingEntryId;
		
		private Integer matchPlayerId;
		
		private Integer innings;
		
		private Double overs;
		
		private Integer runs;
		
		private Integer noBalls;
		
		private Integer wide;
		
		private Integer maiden;
		
		public Integer getBowlingEntryId() {
			return bowlingEntryId;
		}

		public void setBowlingEntryId(Integer bowlingEntryId) {
			this.bowlingEntryId = bowlingEntryId;
		}

		public Integer getMatchPlayerId() {
			return matchPlayerId;
		}

		public void setMatchPlayerId(Integer player) {
			this.matchPlayerId = player;
		}

		public Integer getInningsId() {
			return innings;
		}

		public void setInningsId(Integer innings) {
			this.innings = innings;
		}

		public Double getOvers() {
			return overs;
		}

		public void setOvers(Double overs) {
			this.overs = overs;
		}

		public Integer getRuns() {
			return runs;
		}

		public void setRuns(Integer runs) {
			this.runs = runs;
		}

				public Integer getNoBalls() {
			return noBalls;
		}

		public void setNoBalls(Integer noBalls) {
			this.noBalls = noBalls;
		}

		public Integer getWide() {
			return wide;
		}

		public void setWide(Integer wide) {
			this.wide = wide;
		}

		public Integer getMaiden() {
			return maiden;
		}

		public void setMaiden(Integer maiden) {
			this.maiden = maiden;
		}

}
