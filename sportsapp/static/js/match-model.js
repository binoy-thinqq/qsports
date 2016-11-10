/************************************************ Match model start ***********************************************/

MatchModel = Backbone.Model.extend({
    initialize: function () {
        // _.bindAll(this);
    },
    url: function () {
        return API_GET_MATCH + window.location.pathname.split('/')[4];
    },
    defaults: {
        "matchId": 0,
        "teamId": 0,
        "oppTeamId": 0,
        "groundName": "",
        "groundLoc": "",
        "city": "",
        "state": "",
        "matchDate": 1406635250236,
        "country": "",
        "cricketFormat": 1,
        "groundCondition": "",
        "pitchType": "1",
        "weatherCondition": "",
        "matchOfficials": "",
        "maxPlayers": 0,
        "maxOvers": 0
    },
    validate: function (attrs) {
        var errors = {};
        if (!attrs.name) errors.name = "Hey! Give player name.";
        if (!attrs.city) errors.city = "player city please";
        if (!_.isEmpty(errors)) {
            return errors;
        }
    },

    getMthId: function () {
        return this.get('matchId');
    },
    getTeamId: function () {
        return this.get('teamId');
    },

    getOTeamId: function () {
        return this.get('oppTeamId');
    },

    getTeamPlNm: function () {
        return _.pluck(this.get('teamPlayers'), 'profileName');
    },

    getTeamPl: function () {
        return this.get('teamPlayers');
    },

    getOTeamPl: function () {
        return this.get('teamOppPlayers');
    },

    getOTeamPlNm: function () {
        return _.pluck(this.get('teamOppPlayers'), 'profileName');
    },

    getTeamPlId: function () {
        return _.pluck(this.get('teamPlayers'), 'profileId');
    },

    getOTeamPlId: function () {
        return _.pluck(this.get('teamOppPlayers'), 'profileId');
    },

    getOTeamNm: function () {
        return this.get('oppTeamName');
    },

    getTeamNm: function () {
        return this.get('teamName');
    },
    getCrFmt: function () {
        return this.get('cricketFormat');
    },

    getPthType: function () {
        return this.get('pitchType');
    },

    getGrdNm: function () {
        return this.get('groundName');
    },

    getGrdLoc: function () {
        return this.get('groundLoc');
    },

    getCity: function () {
        return this.get('city');
    },

    getState: function () {
        return this.get('state');
    },

    getCntry: function () {
        return this.get('country');
    },

    getWeCnd: function () {
        return this.get('weatherCondition');
    },

    getGrdCnd: function () {
        return this.get('groundCondition');
    },

    getMthOff: function () {
        return this.get('matchOfficials');
    },

    setMthId: function (id) {
        this.set('matchId', id);
    },
    setOTeamId: function (id) {
        this.set('oppTeamId', id);
    }


});

/************************************************ Match model end ***********************************************/


/*********************************************** All Team model start *********************************************/


AllTeamModel = Backbone.Model.extend({
    initialize: function () {
        _.bindAll(this, 'getTeamNames', 'getTeamIds');
    },
    defaults: [
        {
            "errors": [],
            "teamId": 213,
            "teamName": "bbbb",
            "city": "bbbb",
            "state": "bbb"
        },
        {
            "errors": [],
            "teamId": 213,
            "teamName": "aaaaaa",
            "city": "bbbb",
            "state": "bbb"
        }
    ],
    url: function () {
        return API_GETTEAM;
    },
    getTeamNames: function () {
        return _.pluck(this.attributes, 'teamName');
    },
    getTeamIds: function () {
        return _.pluck(this.attributes, 'teamId');
    }

});

/*********************************************** All Team model end ***********************************************/


/*********************************************** Player of Match start ***********************************************/

PlayerOfMatchModel = Backbone.Model.extend({
    initialize: function () {
        _.bindAll(this, 'update', 'isPlAdd', 'isGuAdd');
    },
    defaults: {
        playerId: [],
        guest: [],
        playeradd: false,
        guestadd: false
    },
    update: function (ids, names) {
        this.set('playerId', ids);
        this.set('guest', names);
        this.set('playeradd', false);
        this.set('guestadd', false);

    },

    isPlAdd: function () {
        return this.get('playeradd');
    },

    isGuAdd: function () {
        return this.get('guestadd');
    },

    getPlIds: function () {
        return this.get('playerId');
    },

    getGuest: function () {
        return this.get('guest');
    },

    setPlIds: function (ids) {
        this.set('playerId', ids);
    },

    setPlAdd: function (b) {
        this.set('playeradd', b);
    },

    setGuest: function (gus) {
        this.set('guest', gus);
    },

    setGuAdd: function (b) {
        this.set('guestadd', b);
    }


});

/*********************************************** Player of Match end ***********************************************/


/*********************************************** object declarion start ***********************************************/

var teams = new AllTeamModel();
var match = new MatchModel();

/************************************************ object declarion end ***********************************************/