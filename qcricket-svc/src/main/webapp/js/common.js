
    var inProfile = new Object();
	var inUser = new Object();
	const URL_DOMAIN = 'http://localhost:8085/qvc';
	const SECRET_KEY = "0IGpk3GTkNBEbO8v8ei35J1vRY9v7WR1";
    const API_TEAM ="ws/ckt/team/";
    const API_SEARCH ="/crkt/api/searchplayer/";
    const API_ADD_PLAYER ="/crkt/api/addplayer/";
    const API_ADD_MOD ="/crkt/api/addmod/";
    const API_GET_USER_PROFILE = '/crkt/api/getuserprofile/';
    const API_GETTEAM = '/qs/ws/getteam/';
    const API_SAVE_MATCH = '/crkt/api/savematch';
    const API_GET_MATCH = '/crkt/api/getmatch/';
    const API_SEARCH_TEAM = '/crkt/api/searchteam/';
    const API_ADD_PLAYER_TO_MATCH = '/crkt/api/addplayermatch/';
    const API_JOIN_TEAM = '/crkt/api/jointeam';
    const API_GET_NOTIFICATIONS = '/crkt/api/getnotifications';
    const API_ADD_TO_TEAM = '/crkt/api/addtoteam';

    const API_PROFILE="/crkt/api/profile/";
  
  const ALL_FIELD_POSITIONS = { 0:"All Positions",1:"Third Man",2:"Slip",3:"Gully", 4:"Point", 5:"Fine Leg", 6:"Square Leg",
                7:"Cover", 8:"Mid wicket",9:"Mid Off", 10:"Mid On", 11:"Long Off",12:"Long On"}
    const HAND_TYPE = {0: 'Right Handed', 1: 'Left Handed', 2: 'Both'}
    const BATTING_ORDER = {0: 'Opening', 1: 'Top Order', 2: 'Middle Order', 3: 'Lower Order', 4: 'Lower Order', 5: 'Any Position'}
	const FORMAT = {1: 'TEST', 2: 'ODI', 3: 'T20'}
    const BOWLING_STYLE = {0: 'fast bowler',1: 'spin bowler', 2: 'medium-pace bowler'}
    const GENDER_CHOICE = {M: 'Male',F: 'Female'}
    const COUNTRY = {"AU":"Australia", "BD":"Bangladesh", "CA":"Canada", "IN":"India", "KE":"Kenya","NL":"Netherlands",
                        "PK":"Pakistan", "ZA":"South Africa","LK":"Sri Lanka", "GB":"United Kingdom", "US":"United States",
                        "ZW":"Zimbabwe"}
    const CONTACT_SHARE_SCOPE = { "T":"Team Mates","A":"All", "N":"None"}
    const COUNTRY_TIMEZONE = {
  "AU": [
    "Australia/Lord_Howe",
    "Antarctica/Macquarie",
    "Australia/Hobart",
    "Australia/Currie",
    "Australia/Melbourne",
    "Australia/Sydney",
    "Australia/Broken_Hill",
    "Australia/Brisbane",
    "Australia/Lindeman",
    "Australia/Adelaide",
    "Australia/Darwin",
    "Australia/Perth",
    "Australia/Eucla"
  ],
  "BD": [
    "Asia/Dhaka"
  ],
  "CA": [
    "America/St_Johns",
    "America/Halifax",
    "America/Glace_Bay",
    "America/Moncton",
    "America/Goose_Bay",
    "America/Blanc-Sablon",
    "America/Montreal",
    "America/Toronto",
    "America/Nipigon",
    "America/Thunder_Bay",
    "America/Iqaluit",
    "America/Pangnirtung",
    "America/Resolute",
    "America/Atikokan",
    "America/Rankin_Inlet",
    "America/Winnipeg",
    "America/Rainy_River",
    "America/Regina",
    "America/Swift_Current",
    "America/Edmonton",
    "America/Cambridge_Bay",
    "America/Yellowknife",
    "America/Inuvik",
    "America/Creston",
    "America/Dawson_Creek",
    "America/Vancouver",
    "America/Whitehorse",
    "America/Dawson"
  ],
  "IN": [
    "Asia/Kolkata"
  ],
  "KE": [
    "Africa/Nairobi"
  ],
  "NL": [
    "Europe/Amsterdam"
  ],
  "PK": [
    "Asia/Karachi"
  ],
  "ZA": [
    "Africa/Johannesburg"
  ],
  "LK": [
    "Asia/Colombo"
  ],
  "GB": [
    "Europe/London"
  ],
  "US": [
    "America/New_York",
    "America/Detroit",
    "America/Kentucky/Louisville",
    "America/Kentucky/Monticello",
    "America/Indiana/Indianapolis",
    "America/Indiana/Vincennes",
    "America/Indiana/Winamac",
    "America/Indiana/Marengo",
    "America/Indiana/Petersburg",
    "America/Indiana/Vevay",
    "America/Chicago",
    "America/Indiana/Tell_City",
    "America/Indiana/Knox",
    "America/Menominee",
    "America/North_Dakota/Center",
    "America/North_Dakota/New_Salem",
    "America/North_Dakota/Beulah",
    "America/Denver",
    "America/Boise",
    "America/Shiprock",
    "America/Phoenix",
    "America/Los_Angeles",
    "America/Anchorage",
    "America/Juneau",
    "America/Sitka",
    "America/Yakutat",
    "America/Nome",
    "America/Adak",
    "America/Metlakatla",
    "Pacific/Honolulu"
  ],
  "ZW": [
    "Africa/Harare"
  ]
}
//Call Profile Services
function callProfileServices() {
$.ajax({
   method: "GET",
		dataType: "json",
		contentType: "application/json",
		url: "ws/ckt/quser/0",
		})
		.done(function(data) {
			if (data.errors.length > 0) {
				alert(data.errors);
			} else {
				inUser = data;
				localStorage.setItem('userObj', JSON.stringify(inUser));
				$.ajax({
					method: "GET",
					dataType: "json",
					contentType: "application/json",
					url: "ws/ckt/quser/"+ inUser.userId + "/cricketprofile",
					})
					.done(function( data ) {
						if (data.errors.length > 0) {
							alert(data.errors);
						} else {
							inProfile = data;
							localStorage.setItem('profileObj', JSON.stringify(inProfile));
						}
						$(document ).trigger( "QCricketCoreInitialized");
						var url = "profile.html?pid=" + inProfile.cricketProfileId;
						window.location.href = url;
					});
				
			}
		});
}
//Initialize core for QCricket Application
$(document).ready(function(){
initializeCore();
function initializeCore() {
  if(location.pathname.indexOf("login") == -1) // This doesn't work, any suggestions?
    {
		inUser = JSON.parse(localStorage.getItem('userObj'));
		inProfile = JSON.parse(localStorage.getItem('profileObj'));
		if ($.isEmptyObject(inUser)) {
		window.location.url="login.html";
		} else{  
		$(document ).trigger( "QCricketCoreInitialized");
		}
	}

}
$("#header").load("header.html",function() {
 	$("#profileHeader").attr("alt",inUser.firstName+" "+ inUser.lastName);
	$("#profileHeader").attr("alt",inUser.firstName+" "+inUser.lastName);
	 $("#headerHome").click(function() {
			 var url = "profile.html?pid=" + inProfile.cricketProfileId;
						window.location.href = url;
			 });
});
$("#footer").load("footer.html",function() {
 	//Anything which need to be update in footer goes here...
});

//----------------------------------------------------------
//      check if cookie defined 
//----------------------------------------------------------
function getCookie(name) {

    var start = document.cookie.indexOf( name + "=" );
    var len = start + name.length + 1;

    if ( ( !start ) && ( name != document.cookie.substring( 0, name.length ) ) ) {
       return null;
    }

    if ( start == -1 ) return null;
    var end = document.cookie.indexOf( ';', len );
    if ( end == -1 ) end = document.cookie.length;
    return unescape( document.cookie.substring( len, end ) );
}

//------------------------------------------------------------
//      Remove cookie
//------------------------------------------------------------
function removeCookie(  name, path, domain) {
    if ( getCookie( name ) )
	{
        document.cookie = name + '=' + ( ( path ) ? ';path=' + path : '') + ( ( domain ) ? ';domain=' + domain : '' ) + ';expires=Thu, 01-Jan-1970 00:00:01 GMT';
	}
}
});
function getQueryParam(name) {
  url = location.href
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var results = regex.exec( url );
  return results == null ? null : results[1];
}

//Ajax Hanlder before send
$( document ).ajaxSend(function( event, request, settings ) {
	 request.setRequestHeader("SECRET_KEY" , SECRET_KEY);
});