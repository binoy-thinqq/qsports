package com.thinqq.qsports.server.error;

public class ErrorConstants {

	/**Error Codes*/
	public static int INVALID_REQUEST = 1000;
	public static int UNAUTHORIZED = 1001;
	public static int USER_NOT_FOUND = 1002;
	public static int SERVICE_ERROR = 1003;
	public static int TEAM_NOT_FOUND = 1004;
	public static int BLOCKED_BY_USER = 1005;
	public static int BLOCKED_BY_TEAM = 1006;
	public static int USER_ALREADY_PART_OF_TEAM = 1007;
	public static int USER_NOT_PART_OF_TEAM = 1008;
	public static int USER_NOT_ACTIVE_IN_TEAM = 1009;
	public static int INVALID_AUTH_KEY = 1010;
	
	
	/**Validation Error Code*/
	public static int NOT_NULL = 5000;
	public static int EMAIL_INVALID = 5001;
	public static int MAX_SIZE_EXCEEDED = 5002;
	public static int NOT_ALLOWED_VALUE = 5003;
	public static int MIN_SIZE_REQUIRED = 5004;
	

	/**Cricket Match Errors*/
	public static int INVALID_NUMBER_OF_BALLS_FACED = 6001;
	public static int INVALID_TOTAL_RUNS = 6002;
	public static int INVALID_OUT_DECLARATION = 6003;
	public static int MATCH_TEAMS_NOT_DEFINED = 6004;
	public static int MATCH_NOT_EXIST = 6005;
	public static int MATCH_STARTED = 6006;

	public static int INVALID_BOWLING_SCORECARD_ENTRY = 6007;
	public static int INVALID_NUMBER_OF_MAIDEN_OVERS = 6008;
	public static int INVALID_NUMBER_OF_WICKETS = 6009;
	
	public static int INVALID_TOSS_INFO = 6010;
	public static int INVALID_ELECTED_TO_INFO = 6011;
	public static int INVALID_NUMBER_OF_PLAYERS = 6012;
	
	public static int INVALID_MATCH_DATE = 6013;
	public static int INVALID_MATCH_STATE = 6014;
	
	public static int MAX_INNINGS_FOR_MATCH = 6015;
	
	public static int PLAYER_ALREADY_ADDED_TO_MATCH = 6016;
	
	public static int INNINGS_NOT_FOUND = 6017;
	public static int PLAYER_NOT_PART_OF_MATCH = 6018;
	public static int MAX_ENTRIES_REACHED = 6019;
	public static int ENTRY_NOT_FOUND = 6020;
	public static int INVALID_NUMBER_OF_OVERS = 6021;
	public static int MAX_OVERS_REACHED = 6022;
	public static int NOT_OUT_COUNT = 6023;
	
	
	public static int OVERS_NOT_PLAYED = 6024;
	public static int INVALID_INNINGS_SIZE = 6025;
	
	public static int INVALID_BOWLER = 6026;
	public static int INVALID_FIELDER = 6027;
	public static int INVALID_BOWLER_OR_FIELDER = 6028;
	public static int CANNOT_REVISE_TARGET = 6029;
	public static int INVALID_PLAYER = 6030;
	
	/**User Registration*/
	
	public static int USER_ALREADY_REGISTERED = 8000;
	

	public static int USER_NOT_REGISTERED = 8001;
	
	public static int USER_PASSWORD_NOT_MATCH = 8002;

}
