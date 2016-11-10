/*********************************************** Match view start ***********************************************/
var MatchView = Backbone.View.extend({
    el: $('body'), // attaches `this.el` to an existing element.

    initialize: function () {
        _.bindAll(this, 'render', 'savematch', 'onInputChange', 'savedview', 'refresh', 'searchteam');
        this.render();
        this.model.on('sync', this.refresh, this);
    },

    events: {
        'click input#btn-savematch': 'savematch',
        'change .form-control': 'onInputChange',
        'blur .form-control': 'onInputChange',
        'click a#a-editmatch': 'editmatch',
        'click button#btn-cancelmatch': 'cancelmatch',
        'keyup input#in-opteam': 'searchteam',
        'mouseover .search-r': 'selitem',
        'mouseout .search-r': 'deselitem',
        'click .search-r': 'item'
    },

    item: function (e) {
        console.log('item:' + e.target.id);
        var id = e.target.id;
        var teamName = $('#' + id).html();
        var teamId = $('#' + id + '-key').html();

        $('#in-opteam').val(teamName);
        this.model.setOTeamId(parseInt(teamId));
        $('#dv-sch').empty();
    },

    deselitem: function (e) {
        console.log('deselected item:' + e.target.id);
        var id = e.target.id;
        $('#' + id).removeClass('selected')
    },

    selitem: function (e) {
        console.log('selected item:' + e.target.id);
        var id = e.target.id;
        $('#' + e.target.id).addClass('selected')
    },

    searchteam: function () {
        console.log('search team');
        $('#dv-sch').empty();
        console.log($('#in-opteam').val().length);
        if ($('#in-opteam').val().length >= 3) {

            var data = {"teamName": $('#in-opteam').val(),
                "notteamIds": JSON.stringify(teams.getTeamIds())
            };
            console.log(data);
            $.ajax({
                context: this,
                type: "POST",
                url: API_SEARCH_TEAM,
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            }).done(function (data) {
                    console.log('search team success ');
                    this.model.setOTeamId(0);
                    if (data.length > 0) {
                        var template = _.template($('#t-search').html(), {data: data});
                        $('#dv-sch').html(template);
                        console.log('updated search box');
                    }
                }
            );
        }

    },

    cancelmatch: function () {
        var id = window.location.pathname.split('/')[4]
        if (id == '000') {
            window.location = '/crkt/1/done';
        } else {
            window.location = '/crkt/1/match/' + id;
        }
    },

    editmatch: function () {
        $('#div-match').hide();

        //to refresh all input fields
        console.log(this.model.getTeamId());
        $('#in-myteam').val(this.model.getTeamId());
        $('#in-opteam').val(this.model.getOTeamNm());
        $('#in-matchtype').val(this.model.getCrFmt());
        $('#in-pitchtype').val(this.model.getPthType());
        $('#in-ground').val(this.model.getGrdNm());
        $('#in-groundloc').val(this.model.getGrdLoc());
        $('#in-city').val(this.model.getCity());
        $('#in-state').val(this.model.getState());
        $('#in-country').val(this.model.getCntry());
        $('#in-weather').val(this.model.getWeCnd());
        $('#in-ground_c').val(this.model.getGrdCnd());
        $('#in-match_o').val(this.model.getMthOff());

        $('#div-editmatch').show();
    },

    savematch: function () {
        //savematch clicked
        console.log('save match');
        var $myForm = $('#form-match')
        if ($myForm[0].checkValidity()) {
            var data = this.model.attributes;
            console.log('form valid, calling api to save');
            $.ajax({
                context: this,
                type: "POST",
                url: API_SAVE_MATCH,
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            }).done(function (data) {
                    this.savedview(data);
                }
            );
        }
    },

    onInputChange: function (e) {
        if ('opteam' == e.target.name) {
            return;
        }
        this.model.set(e.target.name, e.target.value);
    },

    render: function () {
        console.log('render match');
        var id = window.location.pathname.split('/')[4];
        if ('000' == id) {
            console.log('create new match view');
            $('#div-editmatch').show();
            $('#div-match').hide();
        } else {
            this.model.setMthId(id);
            this.model.fetch();
            $('#div-editmatch').hide();
            $('#div-match').show();
        }

    },
    savedview: function (match) {
        var id = window.location.pathname.split('/')[4]
        if (id == '000') {
            console.log('created new match success');
            window.location = '/crkt/1/match/' + match.matchId;
        } else {
            console.log('edit match success:' + match.matchId);
            this.model.set(match);
            $('#div-editmatch').hide();
            $('#div-match').show();
            var template = _.template($('#match-template').html(), {m: this.model});
            $('#div-match').html(template);
        }
    },

    refresh: function () {
        console.log('refresh match template');
        console.log(this.model);
        var template = _.template($('#t-match').html(), {m: this.model});
        $('#div-match').html(template);

        var names = _.pluck(match.get('teamPlayers'), 'profileName')
        var id = 'd-match-cnt-' + this.model.getTeamId();
        $('#' + id).html(_.template($('#t-match-plcnt').html(), {name: names}));

        names = _.pluck(match.get('teamOppPlayers'), 'profileName');
        id = 'd-match-cnt-' + this.model.getOTeamId();
        $('#' + id).html(_.template($('#t-match-plcnt').html(), {name: names}));
    }

});

/*********************************************** Match view end *******************************************************/


/************************************************* Team selection view start ******************************************/

var MyTeamSelectionView = Backbone.View.extend({
    el: $('body'), // attaches `this.el` to an existing element.

    initialize: function () {
        _.bindAll(this, 'render');
        this.model.fetch();
        this.model.on('change', this.render);

    },
    render: function () {
        var teamNames = this.model.getTeamNames();
        var teamIds = this.model.getTeamIds();
        for (i = 0; i < teamNames.length; i++) {
            $('#in-myteam').append('<option value=\"' + teamIds[i] + '\">' + teamNames[i] + '</option>');
        }
        ;
    }
});


/*********************************************** Team selection view end **********************************************/


/*********************************************** add player view start ************************************************/

var AddPlayerView = Backbone.View.extend({
    el: $('body'),

    initialize: function (op) {
        _.bindAll(this, 'render', 'addplayer', 'removeplayer', 'showteamplayer', 'saveplayer');

        this.team = op.team;
        this.player = op.player;
        this.match = op.match;
        console.log('initialize .. AddPlayerView ');

        this.team.on('change:teamPlayersVo', this.showteamplayer);

    },

    events: {
        'click .t-player': 'addplayer',
        'click .m-player': 'removeplayer',
        'click button#btn-addpl-tm1': 'render',
        'click button#btn-addpl-tm2': 'render',
        'click button#btn-save-player': 'saveplayer'
    },

    render: function (e) {
        console.log('render add player ui of team')
        var teamId = e.target.parentElement.parentElement.getElementsByClassName('hidden')[0].innerHTML;
        console.log(teamId);
        this.team.setTeamId(teamId);

        var players = [];
        var ids = [];
        if (teamId == this.match.getTeamId()) {
            players = this.match.getTeamPl();
            ids = this.match.getTeamPlId();
        } else if (teamId == this.match.getOTeamId()) {
            players = this.match.getOTeamPl();
            ids = this.match.getOTeamPlId();
        }
        console.log('name nd ids of players');
        console.log(ids);

        this.player.update(ids, []);

        /* render already selected players */
        $('#div-m-player').empty();
        _.each(players, function (pl) {
            var template = _.template($('#t-matchpl').html(), {name: pl.profileName, id: pl.profileId});
            $('#div-m-player').append(template);
        });

        this.team.fetch();
    },

    addplayer: function (e) {
        console.log('add player clicked');
        console.log(e.target.parentElement.id);
        var id = e.target.parentElement.id;
        if (!!id) {
            id = id.replace('t-player-', '');
            console.log('templating');
            console.log(id);

            id = parseInt(id);
            var idList = this.player.getPlIds();
            if (idList.length > 0 && _.contains(idList, id)) {
                console.log('id contains');
            } else {
                idList = idList.concat(id);
                this.player.setPlIds(idList);
                this.player.setPlAdd(true);

                var template = _.template($('#t-matchpl').html(), {name: this.team.getPlName(id), id: id});
                $('#div-m-player').append(template);
            }
        } else {
            var name = $('#in-g-player').val();
            if (!!(name)) {
                var guestList = this.player.get('guest');
                if (guestList.length > 0 && _.contains(guestList, name)) {
                    console.log('name contains')
                } else {
                    guestList = guestList.concat(name);
                    this.player.setGuest(guestList);
                    this.player.setGuAdd(true);

                    var template = _.template($('#t-guestpl').html(), {t: {name: name}});
                    $('#div-m-player').append(template);
                    console.log('guest player addition');
                }
            }
        }

    },

    removeplayer: function (e) {
        console.log('remove player clicked');
        var id = e.target.parentElement.parentElement.id;
        if (!!id) {
            id = id.replace('m-player-', '');
            id = parseInt(id);
            console.log(id);
            var idList = this.player.getPlIds();
            if (idList.length > 0 && _.contains(idList, id)) {
                idList.splice(idList.indexOf(id), 1);
                this.player.setPlIds(idList);
                this.player.setPlAdd(true);
            }

        } else {
            var name = e.target.parentElement.getAttribute('name');
            console.log(name)
            var guestList = this.player.getGuest();
            if (guestList.length > 0 && _.contains(guestList, name)) {
                console.log('name contains')
                var index = guestList.indexOf(name);
                guestList.splice(index, 1);
                this.player.setGuest(guestList);
                this.player.setGuAdd(true);
            }
        }
        e.target.parentElement.parentElement.remove();
    },

    showteamplayer: function (e) {
        console.log('iterate and show team members');
        //console.log(this.team.getPlayers());
        var template = _.template($('#t-teampl').html(), {t: this.team});
        $('#div-t-player').html(template);
    },

    renderMatchView: function () {
        var names = [];
//        if (this.player.isPlAdd()) {
        names = _.map(this.player.getPlIds(), function (id) {
            return this.team.getPlName(id);
        }, this)
//        }

//        if (this.player.isGuAdd()) {
        names = names.concat(this.player.getGuest());
        names = _.select(names, function (name) {
            if (name)return name;
        });
//        }

        console.log(names);

        $('#' + id).empty();
        var id = 'd-match-cnt-' + this.team.getTeamId();
        $('#' + id).html(_.template($('#t-match-plcnt').html(), {name: names}));
    },

    saveplayer: function (e) {
        console.log('save player');
        var data = {player: this.player, teamId: this.team.getTeamId(), matchId: this.match.getMthId()};
        $.ajax({
            context: this,
            type: "POST",
            url: API_ADD_PLAYER_TO_MATCH,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (data) {
                console.log('add player success');
            }
        );

        /* render guest player */
        this.renderMatchView();
        console.log('add guest players');
    }

});

/*********************************************** add player view end **************************************************/

/*********************************************** objects in this view start *******************************************/

var myteamSelectionView = new MyTeamSelectionView({model: teams});
var matchView = new MatchView({model: match});

var teammodel_1 = new TeamModel();
var teammodel_2 = new TeamModel();

var playerOfMatch_1 = new PlayerOfMatchModel();
var playerOfMatch_2 = new PlayerOfMatchModel();

var addplayerView_1 = new AddPlayerView({team: teammodel_1, player: playerOfMatch_1, match: match});

/***********************************************  objects in this view end ********************************************/