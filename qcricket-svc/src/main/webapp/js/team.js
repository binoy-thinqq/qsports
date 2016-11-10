const API_GET_TEAM = 'http://localhost:8085/qs/ws/getteam/';
var team = new Object();
var teamPlayerData = {};
var teamId = null;
var playerSel = new Object();
 function search_data(data) {
		$('#div-player-search').empty();
         var docs = data.response.docs;
        $.each(docs, function(idx, obj) {
                            $('#div-player-search').append('<div class="form-group" id="div-player-'+ obj.profileId + '"> \
                                                    <label class="col-sm-4 control-label">'+ obj.fname +'</label> \
                                                    <label class="col-sm-4 control-label">'+ obj.email +'</label> \
                                                    <label class="col-sm-2 control-label">'+ obj.city +'</label> \
                                                    <div class="col-sm-1"> \
                                                        <button id="btn-player-add" type="button" onClick="addPlayer(\'' + obj.fname + '\', + \'' + obj.profileId + '\')"  class="btn btn-default"> \
                                                            <span class="glyphicon glyphicon-plus"></span> \
                                                        </button> \
                                                    </div> \
                                                </div>');
												
    });
	}
//Refresh Team Data
function refreshTeamData() {
		$("#sp-description").text(team.description);
        $("#sp-teamName").text(team.teamName);
        $("#sp-city").text(team.city);
        $("#sp-state").text(team.state);
        $("#sp-country").text(team.country);
		refreshStats();
		refreshTeamPlayerList();
		for (i = 0; i < team.teamPlayersVo.length; i++) { 
		    var teamPlayer = team.teamPlayersVo[i];
			teamPlayerData[teamPlayer.cricketProfileId]=teamPlayer;
		}
		
		$.each(team.teamStatsVo, function(i, obj) {
			if (obj.cricketFormat == 1) {
			   $("#t20WinP").text(obj.winPercent);
			} else if(obj.cricketFormat == 2) {
			    $("#odiWinP").text(obj.winPercent);
			} else {
			    $("#testWinP").text(obj.winPercent);
			}
		});
		
		if (team.editable == false) {
			$("#btn-add-player").hide();
			$("#btn-editteam").hide();
			$("#add-m-btn").hide();
		}
		
		if (team.teamAssociation == 'ACCEPTED') {
			$("#btn-jointeam").hide();
		} else if(team.teamAssociation == 'REQ_BY_TEAM') {
			$("#requestMsg").text("Request has been sent by the team. Please accept.");
		} else if (team.teamAssociation == 'REQ_BY_PLAYER') {
			$("#requestMsg").text("A request is already sent to the team.");
		} else {
			$("#btn-jointeam").show();
		}
		var playerId = $('#sel-player').val();
		playerSel = teamPlayerData[playerId];
		changePlayerProfile(playerSel);	
}
//Refresh Player list

function refreshTeamPlayerList() {
	$.template('players',' <ul id="ul-squad-list" class="squad-list" ><li><a href="profile.html?pid=${cricketProfileId}" >{{each(k,firstName) userInfo}}{{if k == "firstName"}}${firstName}{{/if}}{{/each}}</a></li></ul>');
	$.template('playerOptionList',' <option value="${cricketProfileId}" >{{each(k,firstName) userInfo}}{{if k == "firstName"}}${firstName}{{/if}}{{/each}}</option>');
	$.tmpl("players",team.teamPlayersVo).appendTo("#playersList");
	$.tmpl("playerOptionList",team.teamPlayersVo).appendTo("#sel-player");
}
//Refresh Team Status
function refreshStats() {
$.template('headerTeamStat','<tr><th>&nbsp;</th><th>Match</th><th>Win</th><th>Loss</th><th>Tie</th><th>N.R</th><th>H.S</th><th>WIN%</th></tr>');
$.template('teamStats' , '<tr><td>{{if cricketFormat == 1}}TEST{{/if}}{{if cricketFormat == 2}}ODI{{/if}}{{if cricketFormat == 3}}T20{{/if}}</td><td>${matches}</td><td>${won}</td>' + 
            '<td>${lost}</td><td>${draw}</td><td>${noResult}</td><td>${runsScored}</td><td>${noResult}</td></tr>');
$.tmpl("headerTeamStat","").appendTo("#teamStatus");
$.tmpl("teamStats",team.teamStatsVo).appendTo("#teamStatus");
}

function fetchTeam(teamId) {
$.ajax({
   method: "GET",
		dataType: "json",
		contentType: "application/json",
		url: API_TEAM + teamId,
		})
		.done(function(data) {
			if (data.errors.length > 0) {
				alert(data.errors);
			} else {
				team = data;
				refreshTeamData();
			}
		});
}
 $(document).ready(function(){
			teamId = getQueryParam("tid");
			if (teamId) {
				$("#div-editteam").hide();
				$("#div-team").show();
				$("#div-editplayers").hide();
				$("#div-players").show();
				fetchTeam(teamId);		
			} else {
				teamId = "000";
				$("#div-editteam").show();
				$("#div-team").hide();
				$("#div-editplayers").show();
				$("#div-players").hide();
			}
		
			 ////////////////////////////////// team save start ////////////////////////////////////////////////////
            $("#btn-saveteam").click(function(){
                var elem = document.getElementById('form-team').elements;
                for(var i = 0; i < elem.length; i++){
                    if(!elem[i].checkValidity()){
                      return;
                    }
                }
                var postData = $("#form-team").serializeArray();
                var jsonData = { };
                for(var i = 0, l = postData.length; i < l; i++) {
                    jsonData[postData[i].name] = postData[i].value;
                }
                $.ajax({
                        type: "POST",
                        url: API_TEAM + teamId,
                        data: JSON.stringify(jsonData),
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        }).done(function(data){
                            $("#div-editteam").hide();
                            $("#div-team").show();
                            $("#in-id-teamId").val(data.teamId);
							if (teamId == "000") {
								window.location.href = "team.html?tid=" + data.teamId;	
							} else {
								refreshTeamData();
							}
                        }
                );
            });
			
			$( "#sel-player" ).change(function() {
				playerSel = teamPlayerData[$("#sel-player").val()];
				changePlayerProfile(playerSel);	
			});
			
////////////////////////////////// team save end ////////////////////////////////////////////////////

           $("#btn-cancelteam").click(function(){
                if (teamId) {
					$("#div-editteam").hide();
					$("#div-team").show();
					$("#div-editplayers").hide();
					$("#div-players").show();
				} else {
				    window.location.href = "profile.html?pid=" + inProfile.cricketProfileId;	
				}
           });

           $("#btn-clearteam").click(function(){
                //clear all fields

           });

////////////////////////////////// team edit start ////////////////////////////////////////////////////

            $("#btn-editteam").click(function(){
                $("#in-id-teamId").hide();
                $("#div-editteam").show();
                $("#div-team").hide();
           });

            $("#btn-jointeam").click(function(){
                 $("#btn-jointeam").hide();
                  $.ajax({
                        type: "POST",
                        url: "ws/ckt/team/" + teamId + "/cricketprofile/" + inProfile.cricketProfileId +"/jointeam",
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        }).done(function(data){
							$("#requestMsg").text("A request is already sent to the team.");
                        });
             });


////////////////////////////////// team edit end ////////////////////////////////////////////////////


////////////////////////////////// search player ////////////////////////////////////////////////////




             $("#btn-player-search").click(function(){
                 
                 var email = $("#id-in-player-email").val();
                 var playername = $("#id-in-player-name").val();
				 
				 var url = "http://localhost:8983/solr/player/select?q=(";
				 if (email) {
					url = url + "email:" + email + "* AND ";
				 }
				 if (playername) {
					url = url + "fname:" + playername + "* AND ";
				 }
				 url = url + "NOT teamId:" + teamId + ")&rows=50&indent=on&wt=json&callback=?&json.wrf=search_data";
				 $.getJSON(url);
             });

////////////////////////////////// search player ////////////////////////////////////////////////////


////////////////////////////////// add player click ////////////////////////////////////////////////////

            $("#btn-add-player").click(function(){
                $("#div-player-search").empty();
                $("#ul-player-list").empty();
            });
			

////////////////////////////////// add player click ////////////////////////////////////////////////////
	//Toggle Player Stats
    $("#a-batting").click(function(){
		$("#a-bowling").removeClass("activeTab");
		$("#a-batting").addClass("activeTab");
		$("#div-bowling").hide();
		$("#div-batting").show();
     });

    $("#a-bowling").click(function(){
		$("#a-batting").removeClass("activeTab");
		$("#a-bowling").addClass("activeTab");
		$("#div-batting").hide();
		$("#div-bowling").show();
     });
	 
	 
//Add Moderator

	$("#a-add-mod").click(function(){
			$.ajax({
				method: "POST",
				dataType: "json",
				contentType: "application/json",
				url: "ws/ckt/team/" + team.teamId + "/cricketprofile/" + playerSel.cricketProfileId + "/addmod",
				})
				.done(function(data) {
					$("#add-m-btn").hide();
				});
    });
});




////////////////////////////////// add player api call ////////////////////////////////////////////////////

        function addPlayer(fname, id){
            $('#ul-player-list').append('<li><a href="profile.html?pid=' + id + '">' + fname +'</a></li>');
			$('#div-player-'+id).remove();
           $.ajax({
                type: "POST",
                url: "ws/ckt/team/" + teamId + "/cricketprofile/" + id +"/addplayer",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                }).done(function(data){

                }
            );

        };

////////////////////////////////// add player ////////////////////////////////////////////////////


		
function changePlayerProfile (player) {
	$( "#div-player-rec-right" ).empty();
	$("#player-rec-template").tmpl(player).appendTo("#div-player-rec-right");	
	$("#div-bowling").hide();
	$("#div-batting").show();
	var userInfo = player.userInfo;
	$("#pl-playerName").text(userInfo.firstName);
	if (player.isModerator || !team.editable) {
		$("#add-m-btn").hide();
	} else {
		$("#add-m-btn").show();
	}
	if (userInfo.notes) {
		$("#p-desc").text(userInfo.notes);
	}	else {
		$("#p-desc").text("");
	}
	if (userInfo.pictureUrl) {
		$("#pl-img").attr("src", userInfo.pictureUrl);
	} else {
		$("#pl-img").attr("src", "images/defaultPlayer.jpg");
	}
}
