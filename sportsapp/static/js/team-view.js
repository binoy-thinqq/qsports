///////////////////////////////Team view ///////////////////////////////
var TeamView = Backbone.View.extend({
    el: $('body'), // attaches `this.el` to an existing element.

    initialize: function(){
      _.bindAll(this, 'render', 'rePaint', 'playerChange'); // fixes loss of context for 'this' within methods

       this.render(); // not all views are self-rendering. This one is.
       //this.model.on("change", this.render);
       this.model.set('teamId',  window.location.pathname.split('/')[4]);
       this.model.fetch();
    },

    events: {
       'change select#sel-player' : 'playerChange'
    },

    playerChange: function() {
        //get selected player set that in model
        console.log('back born Team view- events on playerChange');
        if($('#sel-player').val() == '0'){
            this.render();
            return;
        }
        this.rePaint($('#sel-player').val());
    },
    render: function(){
        $('#div-player-view').hide();
        $('#div-pl-view').find('span:first').show();
    },
    rePaint: function(cricketProfileId){
      $('#div-pl-view').find('span:first').hide();
      $('#div-player-view').show();
      $('#div-player-view').find('.player-rec-his-left img').attr('src', this.model.getPlByPro(parseInt(cricketProfileId))[0].userInfo.pictureUrl);
      $('#div-player-view').find('.player-rec-his-left strong').html(this.model.getPlByPro(parseInt(cricketProfileId))[0].userInfo.firstName);
      if (this.model.getPlByPro(parseInt(cricketProfileId))[0].userInfo.city) {
      $('#div-player-view').find('.player-rec-his-left #p-player').html(this.model.getPlByPro(parseInt(cricketProfileId))[0].userInfo.city);
        }
    if (this.model.getPlByPro(parseInt(cricketProfileId))[0].userInfo.notes) {
        $('#div-player-dec').find('#p-desc').html(this.model.getPlByPro(parseInt(cricketProfileId))[0].userInfo.notes);
    } else {
          $('#div-player-dec').find('#p-desc').html("No Description Available");
    }
     if (this.model.getPlByPro(parseInt(cricketProfileId))[0].isModerator) {
         $('#add-m-btn').find('.addm').hide();
     }

    }

});


///////////////////////////////Player view ///////////////////////////////

var PlayerView = Backbone.View.extend({
    el: $('body'),
    initialize: function(){
      _.bindAll(this, 'render', 'rePaint', 'playerChange', 'toggleRecordActive'); // fixes loss of context for 'this' within methods
       this.render(); // not all views are self-rendering. This one is.
       this.model.on('change:battingStats', this.rePaint);
    },

    events: {
       'change select#sel-player' : 'playerChange',
       'click  a#a-batting' : 'showBatting',
       'click  a#a-bowling' : 'showBowling',
       'click  a#a-add-mod' : 'addModerator'
    },
    addModerator: function(){
        var jsonData = {};
        var profileId = $('#sel-player').val();
        jsonData['teamId'] = $('#in-id-teamId').val();
        jsonData['profileId'] = $('#sel-player').val();

        $.ajax({
            type: "POST",
            url: API_ADD_MOD,
            data: JSON.stringify(jsonData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            }).done(function(data){
                console.log('added moderator- done');
                $('#div-add-mod-modal').find('.mod-success span').html(team.getPlByPro(parseInt(profileId))[0].userInfo.firstName);
                $('#div-add-mod-modal').modal('show');
            }
        );

    },
    showBatting: function(){
       this.toggleRecordActive();

       $('#div-batting').show();
       $('#div-bowling').hide();
    },
    showBowling: function(){
       this.toggleRecordActive();

       $('#div-batting').hide();
       $('#div-bowling').show();
    },
    playerChange: function() {
      //get selected player set that in model
      console.log('back born Player View- events on playerChange');
      this.model.setPlayer($('#sel-player').val());
      this.model.fetch();

    },

    toggleRecordActive: function(){
       $('#a-batting').toggleClass('activeTab');
       $('#a-bowling').toggleClass('activeTab');
    },
    rePaint: function(){
       console.log('back born Player View- fetched new record');
       console.log(this.model.getBatting());

       $('#a-batting').removeClass('activeTab');
       $('#a-bowling').removeClass('activeTab');
       $('#a-batting').addClass('activeTab');
       $('#div-batting').show();
       $('#div-bowling').hide();
       var template = _.template($('#player-rec-template').html(), {pl: this.model});
       $('#div-player-rec-right').html(template);
    },

    render: function(){
       console.log('called - player view render');
    },

});

var teamView = new TeamView({model: team});
var playerView = new PlayerView({model: player});