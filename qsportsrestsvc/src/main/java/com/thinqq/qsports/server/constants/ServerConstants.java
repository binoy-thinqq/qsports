package com.thinqq.qsports.server.constants;

public class ServerConstants {
	private static String confirmationKey = "sdjkmnfdoijoreqihjgowqerj2309ir5430o2ij5";
	public static String getConfirmationKey(){
		return confirmationKey;
	}
	public static String SESSION_USER_KEY="userKey";
	public static String SESSION_USER_OBJECT="USER_OBJECT";
	
	public static String GOOGLE_USER_PROFILE_SCOPE = "https://www.googleapis.com/auth/userinfo.profile";
	public static String GOOGLE_USER_EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
	public static String GOOGLE_USER_INFO_API_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=";
	
	public static String OAUTH_CALLBACK_SERVLET_URL="/oauth2callback";
	public static String OAUTH_SERVLET_URL="/oauth2";
	public static String HOME_PAGE="/Home.html?";
	public static String ERROR_PARAM="&isError=true";
	
	public static String NEW_USER_PARAM="&isNew=true";
	
	/** START ---- Should be changed when going to PROD/
	public static String GOOGLE_CLIENT_ID="616499903742.apps.googleusercontent.com";
	public static String GOOGLE_SECRET_KEY = "1jH4sw4oTzt5qFPP-T224BQX";
	public static String REDIRECT_URL = "/qsportsclient.jsp?";
	public static boolean IS_DEV = false;
	/** END ---- Should be changed when going to PROD*/
	
	/** START ---- Should be changed when going to DEV*/
	public static String GOOGLE_CLIENT_ID="616499903742.apps.googleusercontent.com";
	public static String GOOGLE_SECRET_KEY = "1jH4sw4oTzt5qFPP-T224BQX";
	public static String REDIRECT_URL = "/qsportsclient.jsp?gwt.codesvr=127.0.0.1:9997";
	public static boolean IS_DEV = true;
	/** END ---- Should be changed when going to DEV*/
}
