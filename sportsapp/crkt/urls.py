__author__ = 'rakeshpullayikodi'

from django.conf.urls import patterns, url

from crkt import views

urlpatterns = patterns('',
    # ex: /polls/
    url(r'^$', views.index, name='index'),
    # ex: /polls/5/
    url(r'^(?P<poll_id>\d+)/$', views.detail, name='detail'),
    # ex: /polls/5/results/
    url(r'^(?P<poll_id>\d+)/results/$', views.results, name='results'),
    # ex: /polls/5/vote/
    url(r'^(?P<poll_id>\d+)/vote/$', views.vote, name='vote'),

    url(r'^(?P<poll_id>\d+)/done/$', views.done, name='done'),
    url(r'^(?P<poll_id>\d+)/team/(?P<id>\d+)/$', views.team, name='team'),
    url(r'^(?P<poll_id>\d+)/match/(?P<id>\d+)/$', views.match, name='match'),
    url(r'^api/profile/$', views.profile, name='profile'),
    url(r'^api/team/$', views.teamsave, name='teamsave'),
    url(r'^api/getteam/(?P<id>\d+)/$', views.getteam, name='getteam'),
    url(r'^api/getmatch/(?P<id>\d+)/$', views.getmatch, name='getmatch'),
    url(r'^api/searchplayer/$', views.searchplayer, name='searchplayer'),
    url(r'^api/searchteam/$', views.searchteam, name='searchteam'),
    url(r'^api/addplayer/$', views.addplayer, name='addplayer'),
    url(r'^api/addplayermatch/$', views.addplayermatch, name='addplayermatch'),
    url(r'^api/getallteam$', views.getallteam, name='getallteam'),
    url(r'^api/savematch', views.savematch, name='savematch'),
    url(r'^api/addmod', views.addmoderator, name='addmoderator'),
    url(r'^api/getuserprofile/(?P<id>\d+)/$', views.getuserprofile, name='getuserprofile'),
    url(r'^api/jointeam', views.jointeam, name='jointeam'),
    url(r'^api/getnotifications', views.getnotification, name='getnotification'),
     url(r'^api/addtoteam', views.addtoteam, name='addtoteam'),



)
