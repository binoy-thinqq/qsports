var profile = new Object();
var user = new Object();

//Fetch User
function fetchUser(pid) {
$.ajax({
   method: "GET",
		dataType: "json",
		contentType: "application/json",
		url: "ws/ckt/cricketprofile/" + pid,
		})
		.done(function(data) {
			if (data.errors.length > 0) {
				alert(data.errors);
			} else {
				profile = data;
				user = data.userInfo;
				user.isEnabled = false;
				refreshUserData();
				refreshProfileData();
				refreshStats();
				getAllTeams();
			}
		});
}

 //////////////////////////////////get all team start////////////////////////////////////////////////////
function getAllTeams() {
        $.ajax({
            type: "GET",
		dataType: "json",
		contentType: "application/json",
		url: "ws/ckt/cricketprofile/" + profile.cricketProfileId + "/teams",
        }).done(function(data){
               $.each(data, function(idx, obj) {
                    $('#tbl-team').append('<tr>   <td style="text-align: center;"><span class="location"><a href="team.html?tid='+ obj.teamId+'"> &nbsp;&nbsp;&nbsp;&nbsp;\
                                          '+ obj.teamName +'</a></span></td>   <td style="text-align: center;"> \
                                          '+ obj.city + '</td>   <td><span class="quickStats match-bg"> \
                                          Match: '+ obj.totalMatches + '</span> <span class="quickStats won-bg"> \
                                          Won: '+ obj.matchWon + '</span> <span class="quickStats lost-bg"> \
                                          Lost: '+ obj.matchLost + '</span></td> <td class="txtAlnCenter"> \
                                          '+ obj.winPercentage + '</td> </tr>');
					    $('#ul-team-list').append('<li><a href="team.html?tid=' + obj.teamId + '">' + obj.teamName +'</a></li>');

               });
            }
        );
}

//////////////////////////////////get all team end////////////////////////////////////////////////////

function acceptRequest(id,teamId,teamName,isrejected,action) {
      var divToHide = "li_" + id +"_"+ teamId;
      $.ajax({
        type: "POST",
        url: "ws/ckt/team/" + teamId + "/cricketprofile/"  +  id + "/" + action + "?status=" + isrejected,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
    }).done(function(data){
             $("#li_"+ id + "_" + teamId).hide();
			 if (action == "jointeam") {
				$('#ul-team-list').append('<li><a href="team.html?tid=' + teamId + '">' + teamName +'</a></li>');
			 }
       }
    );

 }

function refreshProfileData() {
            $("#sp-batting").text(HAND_TYPE[profile.battingHandType]+ " " + BATTING_ORDER[profile.battingOrder]);
            $("#sp-bowling").text(HAND_TYPE[profile.bowlingHandType]+ " " + BOWLING_STYLE[profile.bowlingMethod]);
            $("#sp-fielding").text(ALL_FIELD_POSITIONS[profile.fieldPosition]);
} 
 

function refreshUserData(){
            $("#sp-displayname").text(user.firstName+" "+user.lastName);
		
			if (user.dob) {
				var dateOfB  = new Date(user.dob);
				$("#sp-dob").text(dateOfB.getMonth()+1 + "/" + dateOfB.getDate() + "/" + dateOfB.getFullYear() );
			}
            $("#sp-gender").text(GENDER_CHOICE[user.gender]);
            $("#sp-mobileNumber").text(user.mobileNumber);
            $("#sp-city").text(user.city);
            $("#sp-state").text(user.state);
            $("#sp-country").text(COUNTRY[user.isoCountryCode]);
            $("#sp-timezone").text(user.timezone);
            $("#a-aboutme").text(user.aboutme);
            $("#perDisplayName").text(user.firstName+" "+user.lastName + '\'s performance (Last 5 matches)');
			if (!user.isEnabled) {
				$("#btn-editprofile").hide();
			}
			if (user.pictureUrl) {
			}
            if(user.contactShown){
                $("#parent-sp-email").show();
                $("#sp-email").text(user.email);
                $("#parent-sp-mobileNumber").show();
                $("#sp-mobileNumber").text(user.mobileNumber);
            } else {
                $("#parent-sp-email").hide();
                $("#parent-sp-mobileNumber").hide();
            }
}

function refreshStats() {
$.template('headerBatStat','<tr> <th>&nbsp;</th>  <th>M</th><th>Inn</th><th>Runs</th> <th>HS</th><th>Avg</th><th>SR</th><th>NO</th><th>100</th><th>50</th> <th>4s</th> <th>6s</th> </tr>');
$.template('battingStats' , '<tr><td>{{if cricketFormat == 1}}TEST{{/if}}{{if cricketFormat == 2}}ODI{{/if}}{{if cricketFormat == 3}}T20{{/if}}</td><td>${matches}</td><td>${innings}</td><td>${runs}</td><td>${highestScore}</td><td>${average}</td><td>${strikeRate}</td><td>${notOuts}</td><td>${hundreds}</td>'
                 + '<td>${fifties}</td><td>${fours}</td><td>${sixes}</td></tr>');
$.template('headerBowlStat','<tr><th>&nbsp;</th><th>M</th><th>Inn</th><th>BL</th><th>WK</th><th>Avg</th><th>ECO</th><th>RN</th><th>3WKs</th><th>5WKs</th><th>CAT</th><th>BST</th></tr>');
$.template('bowlingStats','<tr><td>{{if cricketFormat == 1}}TEST{{/if}}{{if cricketFormat == 2}}ODI{{/if}}{{if cricketFormat == 3}}T20{{/if}}</td><td>${matches}</td><td>${innings}</td>'
             +   '<td>${balls}</td><td>${wickets}</td><td>${average}</td><td>${economy}</td><td>${runs}</td><td>${threeWickets}</td><td>${fiveWickets}</td><td>${catches}</td><td>${bestBowling}</td></tr>');
 $.tmpl("headerBatStat","").appendTo("#battingStats");
 $.tmpl("battingStats",profile.battingStats).appendTo("#battingStats");
 
  $.tmpl("headerBowlStat","").appendTo("#bowlingStats");
 $.tmpl("bowlingStats",profile.bowlingStats).appendTo("#bowlingStats");
}

function refreshUserDataInEditMode(){
            $("#firstName").val(user.firstName);
            $("#lastName").val(user.lastName);
            $("#dob").val(user.dob);
            $('#gender option[value="'+user.gender+'"]').attr("selected", "selected");
            $("#mobileNumber").val(user.mobileNumber);
            $("#city").val(user.city);
            $("#state").val(user.state);
            $('#country option[value="'+user.country+'"]').attr("selected", "selected");
            $('#timezone option[value="'+user.timezone+'"]').attr("selected", "selected");
            $("#aboutme").val(user.aboutme);
            $("#contactShown").val(user.contactShown);
			if(profile) {
            $('#batting-hand option[value="'+profile.battingHand+'"]').attr("selected", "selected");
            $('#batting-order option[value="'+profile.battingOrder+'"]').attr("selected", "selected");
            $('#bowling-hand option[value="'+profile.bowlingHand+'"]').attr("selected", "selected");
            $('#bowling-method option[value="'+profile.bowlingMethod+'"]').attr("selected", "selected");
            $('#fielding option[value="'+profile.fielding+'"]').attr("selected", "selected");
			}
}
 $( document ).on( "QCricketCoreInitialized", function() {
	var pid = getQueryParam("pid");
	if (pid) {
		if(pid != inProfile.cricketProfileId) {
			fetchUser(pid);
		} else {
			profile = inProfile;
			user = inUser;
			user.isEnabled = true;
			refreshUserData();
			refreshProfileData();
			refreshStats();
			getAllTeams();
		}
	} else {
	}
	
 });

$(document).ready(function(){
	
  $("#div-editprofile").hide();
  $("#dob").datepicker();
  
		
  $("#btn-editprofile").click(function(){
    $("#div-profile").hide();
    $("#btn-editprofile").hide();
    $("#div-editprofile").show();
    refreshUserDataInEditMode();
  });

  $("#btn-cancelprofile").click(function(){
    $("#div-editprofile").hide();
    $("#div-profile").show();
    $("#btn-editprofile").show();
  });
 /////////////// Initialise User Data///////////////////////////////


//////////////////////////////////Start Populate Dropdowns//////////////////////////////////////////////
    $("#fielding").append("<option value=''> - Choose - </option>");
    $.each(ALL_FIELD_POSITIONS, function(i,val){
        $("#fielding").append("<option value='" + i +"'>" + val + "</option>");
    });
    $.each(HAND_TYPE, function(i,val){
        $("#batting-hand").append("<option value='" + i +"'>" + val + "</option>");
    });
    $.each(BATTING_ORDER, function(i,val){
        $("#batting-order").append("<option value='" + i +"'>" + val + "</option>");
    });
    $.each(HAND_TYPE, function(i,val){
        $("#bowling-hand").append("<option value='" + i +"'>" + val + "</option>");
    });
    $.each(BOWLING_STYLE, function(i,val){
        $("#bowling-style").append("<option value='" + i +"'>" + val + "</option>");
    });
    $("#gender").append("<option value=''> - Choose - </option>");
    $.each(GENDER_CHOICE, function(i,val){
        $("#gender").append("<option value='" + i +"'>" + val + "</option>");
    });
    $("#country").append("<option > - Choose - </option>");
    $.each(COUNTRY, function(i,val){
        $("#country").append("<option value='" + i +"'>" + val + "</option>");
    });
    $.each(CONTACT_SHARE_SCOPE, function(i,val){
        $("#contactShown").append("<option value='" + i +"'>" + val + "</option>");
    });
    
   // refreshUserData();
   // refreshUserDataInEditMode();
///////////////////////////////////////End Populate Dropdowns//////////////////////////////////////////////
////////////////////////////////// save profile start ////////////////////////////////////////////////////



  $("#btn-saveprofile").click(function(){
    $("#div-editprofile").hide();
    var postData = $("#form-profile").serializeArray();
    var userData = { };
	var profileData = { };

    for(var i = 0, l = postData.length; i < l; i++) {
        if(postData[i].name == 'battingHandType' || postData[i].name == 'battingOrder' || postData[i].name == 'aboutme' || postData[i].name == 'bowlingHandType' || postData[i].name == 'bowlingMethod' || postData[i].name == 'fieldPosition' || postData[i].name == 'wicketKeeper'){
            profileData[postData[i].name] = postData[i].value;
        }else{
			if (postData[i].name == 'dob') {
			 userData[postData[i].name] = new Date(postData[i].value);
			} else {
				userData[postData[i].name] = postData[i].value;
			}
        }
    }
	saveUserInfo(userData);


  });
  ////////////////////////////////// save  profile end ////////////////////////////////////////////////////


 
    //////////////////////////////////get all notifications start////////////////////////////////////////////////////

        $.ajax({
             type: "GET",
		dataType: "json",
		contentType: "application/json",
		url: "ws/ckt/getnotifications",
        }).done(function(data){
               teamRequest = 0;
               joinTeam = 0;
               $.each(data, function(idx, obj) {
                    notificationData=obj.notificationData;
                    notificationType=obj.notificationType;
                    if(notificationType == 'ADD_TO_TEAM_REQUEST'){
                        $('#div-notification-player-ul').append('<li class="vcard " id="li_'+ notificationData.profileId +'_'+ notificationData.teamId +'">  <div class="profile-summary"><h4>\
            <span><strong>      <a href="profile.html?pid=' + notificationData.profileId + '>\
           '+ notificationData.profileName +'</a> </strong></span> </h4> <p class="headline">Requested to Join : \
           <a href="team.html?tid='+ notificationData.teamId +'"> '+ notificationData.teamName +'</a></p></div>  <ul class="actions"><li class="remove">\
            <input type="button" id="btn" value="Accept"onclick="acceptRequest('+ notificationData.profileId +',' + notificationData.teamId +',' + notificationData.teamName + ',false,\'addplayer\')"/>\
            <input type="button" id="btn" value="Reject"onclick="acceptRequest('+ notificationData.profileId +',' + notificationData.teamId +',' + notificationData.teamName + ',true,\'addplayer\')"/> </li>  </ul> </li>');
                        teamRequest = teamRequest + 1;
                    } else if (notificationType == 'JOIN_TEAM_REQUEST') {
                         $('#div-notification-team').append('<li class="vcard " id="li_'+ inProfile.cricketProfileId +'_'+ notificationData.teamId +'">  <div class="profile-summary"><h4>\
            <span><strong>      <a href="team.html?tid=' + notificationData.teamId + '">\
           '+ notificationData.teamName +'</a> </strong></span> </h4> <p class="headline"> <a href="team.html?tid='+ notificationData.teamId +'">'+ notificationData.teamName +'</a> Requested to Join their team \
          </p></div>  <ul class="actions"><li class="remove">\
            <input type="button" id="btn" value="Accept"onclick="acceptRequest('+ inProfile.cricketProfileId +',' + notificationData.teamId +',' + notificationData.teamName + ',false,\'jointeam\')"/>\
            <input type="button" id="btn" value="Reject"onclick="acceptRequest('+ inProfile.cricketProfileId +',' + notificationData.teamId +',' + notificationData.teamName + ',true,\'jointeam\')"/> </li>  </ul> </li>');
						 
                        joinTeam=joinTeam+1;
                    }
               });
               if (teamRequest == 0) {
                 $("#div-notification-player-ul").hide();
               }
                if (joinTeam == 0) {
                 $("#div-notification-team").hide();
               }

            }
        );

});

//////////////////////////////////get all notifications end////////////////////////////////////////////////////

function saveUserInfo (userData) {
  $.ajax({
        type: "POST",
		beforeSend: function (request)
           {
               request.setRequestHeader("SECRET_KEY" ,"0IGpk3GTkNBEbO8v8ei35J1vRY9v7WR1");
           },
        url: "ws/ckt/quser/" + user.userId,
        data: JSON.stringify(userData),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
    }).done(function(data){
            user.firstName = data.firstName;
            user.lastName = data.lastName;
            user.dob = data.dob.toString();
            user.gender = data.gender;
            user.city = data.city;
            user.state = data.state;
            user.country = data.country;
            user.timezone = data.timezone;
            user.email = data.email;
            user.mobileNumber = data.mobileNumber;
            user.aboutme = data.aboutme;
			localStorage.setItem('userObj', JSON.stringify(user));
            refreshUserData();
			refreshUserDataInEditMode();
            $("#div-profile").show();
            $("#btn-editprofile").show();
        }
    );
}