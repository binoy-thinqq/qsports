/*********************************************** Team model start ***********************************************/

TeamModel = Backbone.Model.extend({
    initialize: function () {
        _.bindAll(this, 'getPlayers');
    },
    url: function () {
        return API_GET_TEAM + this.get('teamId');
    },
    defaults: {
        "teamId": 000,
        "teamName": "Weekend Warriors",
        "city": "Chennai",
        "teamPlayersVo": [
            {
                "errors": [],
                "teamPlayersId": 48,
                "teamId": 113,
                "cricketProfileId": 4,
                "userInfo": {
                    "firstName": "test test 2",
                    "lastName": "",
                    "email": "rakeshnbr@gmail.com"
                }
            },
            {
                "errors": [],
                "teamPlayersId": 49,
                "teamId": 113,
                "cricketProfileId": 1,
                "userInfo": {
                    "firstName": "test test 1",
                    "lastName": "",
                    "email": "rakeshnbr@gmail.com"
                }
            }
        ]
    },
    validate: function (attrs) {
        var errors = {};
        if (!attrs.name) errors.name = "Hey! Give player name.";
        if (!attrs.city) errors.city = "player city please";
        if (!_.isEmpty(errors)) {
            return errors;
        }
    },
    getPlayers: function () {
        return $.extend(true, {}, this.get("teamPlayersVo"));
    },

    getPlByPro: function (cricketProfileId) {
        return _.where(this.getPlayers(), { cricketProfileId: cricketProfileId });
    },

    getPlById: function (teamPlayersId) {
        return _.where(this.getPlayers(), { teamPlayersId: teamPlayersId });
    },

    getPlName: function (cricketProfileId) {
        var pl = this.getPlByPro(cricketProfileId);
        if (pl && pl.length > 0) {
            return pl[0].userInfo.firstName || '' + pl[0].userInfo.lastName || '';
        }

    },

    getTeamId: function () {
        return this.get('teamId');
    },

    getPlVo: function () {
        return this.get('teamPlayersVo');
    },

    setTeamId: function (id) {
        this.set('teamId', id);
    }

});

/************************************************ Team model end ***********************************************/

/*********************************************** Player model start ***********************************************/

PlayerOfTeamModel = Backbone.Model.extend({
    initialize: function () {
        _.bindAll(this, 'getBatting');
    },
    defaults: {
        "cricketProfileId": 0,
        "battingStats": [
            {
                "battingStatsId": 20,
                "matches": 0,
                "cricketFormat": 3,
                "createdTime": 1396840552062,
                "updateTime": 1396840552062
            },
            {
                "battingStatsId": 18,
                "matches": 0,
                "innings": 0
            },
            {
                "battingStatsId": 18,
                "matches": 0,
                "innings": 0
            }
        ]
    },

    setPlayer: function (playerId) {
        this.set('cricketProfileId', playerId);
    },
    getBatting: function () {
        return this.get('battingStats');
    },
    getBowling: function () {
        return this.get('bowlingStats');
    },
    url: function () {
        return API_GET_USER_PROFILE + this.get('cricketProfileId');
    }
});

/*********************************************** Player model start ***********************************************/


/************************************************ Object declarion start ******************************************/

var player = new PlayerOfTeamModel();
var team = new TeamModel();

/*********************************************** Object declarion end ***********************************************/
