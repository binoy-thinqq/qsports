from django.template import RequestContext, loader
from django.http import HttpResponse

from django.conf import settings
from django.template import RequestContext
from django.shortcuts import render_to_response, redirect
from django.contrib.auth.decorators import login_required
from django.contrib.auth import logout as auth_logout
from django.views.decorators.csrf import csrf_exempt


import json
import requests
from django.http import HttpResponse
from social.backends.google import GooglePlusAuth

import time
from datetime import datetime

import urllib


#URL_DOMAIN = 'http://localhost:8080/qs'
URL_DOMAIN = 'http://localhost:8181/qvc'
URL_SEARCH_DOMAIN = 'http://dev.thinqq.com:8080/solr'


SERVER_SECRET = '0IGpk3GTkNBEbO8v8ei35J1vRY9v7WR1'

COOKIE_QSPORS_AUTH_KEY = 'QSPORS_AUTH_KEY'
COOKIE_QSPORS_USER_KEY = 'QSPORS_USER_KEY'
COOKIE_QSPORS_PROFILE_KEY = 'QSPORS_PROFILE_KEY'
COOKIE_QSPORS_USERID = 'QSPORS_USERID'
COOKIE_QSPORS_TEAMID_LIST = 'QSPORS_TEAMID_LIST'

HEADER = {'AUTH_KEY': '', 'SECRET_KEY': SERVER_SECRET, 'content-type': 'application/json', 'ACCEPT': 'application/json'}

URL_LOGIN = URL_DOMAIN + '/ws/ckt/login'
payload_login = {'firstName': '', 'lastName': '', 'email': '', 'registrationType': '', 'pictureUrl': '',
                 'city': '', 'state': '', 'isoCountryCode': '', 'timezone': ''}

URL_GETUSER_BYEMAIL = URL_DOMAIN + '/ws/ckt/getuseremail'
URL_GETPROFILE = URL_DOMAIN + '/ws/ckt/getcricketprofile/{}&isMin=false'
URL_GETUSER  = URL_DOMAIN + '/ws/ckt/getuser/{}'

URL_SAVEUSER = URL_DOMAIN + '/ws/ckt/saveuser'
payload_user = {'firstName': '', 'lastName': '', 'gender': '', 'city': '', 'state': '',
                'isoCountryCode': '', 'timezone': 'IST', 'locale': 'en_US', 'dob': '',
                'notes': '', 'mobileNumber': '', 'contactShown': 'N'}

URL_SAVEPROFILE = URL_DOMAIN + '/ws/ckt/savecricketprofile'
payload_profile = {'cricketProfileId': '', 'battingHandType': '', 'battingOrder': '', 'bowlingHandType': '',
                   'bowlingMethod': '', 'fieldPosition': '', 'wicketKeeper': ''}

URL_SAVETEAM = URL_DOMAIN + '/ws/ckt/saveteam'
URL_CREATETEAM = URL_DOMAIN + '/ws/ckt/createteam'
payload_team = {'teamId': '', 'teamName': '',
                'city': '', 'state': '',
                'country': 'IN', 'description': '',
                'createdId': ''}

payload_join_team = {'teamId': '', 'profileId': ''}

payload_add_player = {'matchId': 0, 'teamId': 0, 'profileId': 0}

URL_GETTEAMBYPLAYER = URL_DOMAIN + '/ws/ckt/getteamsbyplayer/{}'
URL_GETTEAM = URL_DOMAIN + '/ws/ckt/getteam/{}&ismin=false'

URL_SEARCH_PLAYER = URL_SEARCH_DOMAIN + '/player/select?q=(email:{} OR fname:{})&wt=json&indent=true'
URL_SEARCH_TEAM = URL_SEARCH_DOMAIN + '/team/select?q=(NOT(id:{}) AND teamname:{})&wt=json&intend=on&rows=10000000&sort=teamname asc'

URL_ADD_PLAYER = URL_DOMAIN + '/ws/ckt/addplayer'
URL_ADD_MODE = URL_DOMAIN + '/ws/ckt/addmoderator'
URL_SAVEMATCH = URL_DOMAIN + '/ws/ckt/savematch'
URL_GETMATCH =  URL_DOMAIN + '/ws/ckt/getmatch/{}'
URL_ADD_PLAYER_TO_MATCH = URL_DOMAIN + '/ws/ckt/addplayertomatch'

URL_JOIN_TEAM = URL_DOMAIN + '/ws/ckt/jointeam/'
URL_NOTIFICATION = URL_DOMAIN + '/ws/ckt/getnotifications/'

URL_ADD_TO_TEAM = URL_DOMAIN + '/ws/ckt/addplayer/'

DOB_FORMAT = '%m/%d/%Y'

def index(request):
    template = loader.get_template('crkt/index.html')
    context = RequestContext(request)
    return HttpResponse(template.render(context))


def detail(request, poll_id):
    return HttpResponse("You're looking at crkt detail %s." % poll_id)


def results(request, poll_id):
    return HttpResponse("You're looking at the results of match %s." % poll_id)


def vote(request, poll_id):
    return HttpResponse("You're looking for your score  %s." % poll_id)


@login_required
def done(request, poll_id):
    """Login complete view, displays user data"""

    scope = ' '.join(GooglePlusAuth.DEFAULT_SCOPE)

    print 10 * '*' + 'logged in users email id:'
    print request.user
    payload_login['email'] = request.user.email
    payload_login['firstName'] = request.user.first_name
    payload_login['lastName'] = request.user.last_name
    payload_login['registrationType'] = 'GOOGLE'
    payload_login['pictureUrl'] = request.user.avatar

    loginToken = getLoginToken(payload_login)
    print "Login token is:" + loginToken

    HEADER['AUTH_KEY'] = loginToken
    user = getUser(request.user.email)
    print 'user >>>>>>>>>>>>>>>>>> user'
    print user
    profile = getProfile(user['userId'])
    profile = json.loads(profile)

    # print profile
    if(user['dob'] != None):
       dob = datetime.fromtimestamp(float(user['dob'])/1000.0).strftime(DOB_FORMAT)
    else:
        dob = "01/01/2014"


    response = render_to_response('crkt/profile.html', {
        'avatar_url': user['pictureUrl'],
        'firstName': user['firstName'],
        'lastName': user['lastName'],
        'email': user['email'],
        'city': user['city'],
        'state': user['state'],
        'country': user['isoCountryCode'],
        'timezone': user['timezone'],
        'aboutme': user['notes'],
        'mobileNumber': user['mobileNumber'],
        'contactShown': user['contactShown'],
        'gender':user['gender'],
        'dob': dob,
        'battingHand': profile['battingHandType'],
        'battingOrder': profile['battingOrder'],
        'bowlingHand': profile['bowlingHandType'],
        'bowlingMethod':profile['bowlingMethod'],
        'fielding': profile['fieldPosition'],
        'plus_id': getattr(settings, 'SOCIAL_AUTH_GOOGLE_PLUS_KEY', None),
        'plus_scope': scope,
        'isnewuser': 'true',
        'battingStats': profile['battingStats'],
        'bowlingStats': profile['bowlingStats'],
    }, RequestContext(request))

    response.set_cookie(COOKIE_QSPORS_AUTH_KEY, loginToken)
    response.set_cookie(COOKIE_QSPORS_PROFILE_KEY, profile['cricketProfileId'])
    response.set_cookie(COOKIE_QSPORS_USERID, user['userId'])

    # response.set_cookie(COOKIE_QSPORS_USER_KEY, user['userKey'])
    print 'done.........return response'
    return response


@login_required
def match(request, poll_id, id):
    print 'get match'
    print id
    return render_to_response('crkt/match.html', {'teamId': '', }, context_instance=RequestContext(request))



@login_required
def team(request, poll_id, id):
    if (str(id) == '000'):
        print 'create new team'
        print id
        return render_to_response('crkt/newteam.html', {'teamId': '', }, context_instance=RequestContext(request))
    else:
        print 'get team details and load existing team'
        print id
        teamResp = getTeam(id, request)
        teamResp = json.loads(teamResp)

        response_data = {'teamName': teamResp['teamName'],
                     'teamId': teamResp['teamId'],
                     'city': teamResp['city'],
                     'state': teamResp['state'],
                     'country': teamResp['country'],
                     'description': teamResp['description'],
                     'teamStatsVo': teamResp['teamStatsVo'],
                     'teamPlayersVo': teamResp['teamPlayersVo'],
                      'editable': teamResp['editable'],
                      'teamEntryState': teamResp['teamEntryState']
                    }
        return render_to_response('crkt/team.html', response_data, context_instance=RequestContext(request))


@csrf_exempt
def getteam(request, id):
    print 'get team api call'
    teamResp = getTeam(id, request)
    return HttpResponse(teamResp, content_type="application/json")

@csrf_exempt
def getmatch(request, id):
    print 'get match api call'
    print 'id'+id
    url = str.format(URL_GETMATCH, id)
    print 'url:'+url
    getcookie(request=request, userrequest='', profilerequest=payload_profile)
    resp = requests.get(url, headers=HEADER)
    print resp.text
    print '>' * 50 + 'get match end,' + '>' * 50
    if (200 == resp.status_code):
        return HttpResponse(resp.text, content_type="application/json")
    print '>' * 50 + 'error in get match' + '>' * 50
    response_data = "'resp': 'failure'"
    return HttpResponse(response_data, content_type="application/json")

@csrf_exempt
def profile(request):
    # make the post call to back end and save the profile
    # payload = {'key1': 'value1', 'key2': 'value2'}
    # requests.post("http://google.appengine/thinqq/qsports/api/profile", payload)
    print 10 * '*' + 'profile data from ui'
        # {u'batting-style': u'0', u'city': u'Tpba', u'name': u'rakesh pulll', u'dob': u'', u'country': u'',
        #  u'aboutme': u'dsadada', u'bowling-order': u'0', u'sex': u'M', u'state': u'KL', u'batting-order': u'0',
        #  u'bowling-style': u'0', u'fielding': u'0', u'email': u'rakeshnbr@gmail.com'}
    editedUser = json.loads(request.body)
    print editedUser

    payload_user['firstName'] = editedUser['firstName']
    payload_user['lastName'] = editedUser['lastName']
    payload_user['gender'] = editedUser['gender']
    payload_user['city'] = editedUser['city']
    payload_user['state'] = editedUser['state']
    payload_user['isoCountryCode'] = editedUser['country']
    payload_user['timezone'] = editedUser['timezone']
    payload_user['notes'] = editedUser['aboutme'].strip()
    payload_user['mobileNumber'] = editedUser['mobileNumber']
    payload_user['contactShown'] = editedUser['contactShown']
    #dateofbirth = editedUser['dob']
    t = datetime.strptime(editedUser['dob'], DOB_FORMAT)
    payload_user['dob'] = long(time.mktime(t.timetuple())*1000);

    payload_profile['battingHandType'] = editedUser['batting-hand']
    payload_profile['battingOrder'] = editedUser['batting-order']
    payload_profile['bowlingHandType'] = editedUser['bowling-hand']
    payload_profile['bowlingMethod'] = editedUser['bowling-style']
    payload_profile['fieldPosition'] = editedUser['fielding']

    payload_profile['wicketKeeper'] = 'true'
        #editedUser['wick-keeper']

    getcookie(request=request, userrequest='', profilerequest=payload_profile)
    saveUsedResp = saveuser(payload_user)

    saveProfileResp = saveProfile(payload_profile)

    #fix saved user should return proper data
    # savedUser = getUser(editedUser['email'])
    if (saveUsedResp is not None and saveProfileResp is not None):
        print 'save user resp'
        print saveUsedResp
        print 'save profile resp'
        print saveProfileResp

    # print battingHandType[2]
    # print battingHandType[int(i)]
    # print battingHandType
    # print saveProfileResp
    # print saveUsedResp
    #+' '+battingOrder[saveProfileResp['battingOrder']]

    response_data = {
                    'firstName': saveUsedResp['firstName'],
                    'lastName': saveUsedResp['lastName'],
                    'gender':saveUsedResp['gender'],
                    'dob': datetime.fromtimestamp(float(saveUsedResp['dob'])/1000.0).strftime(DOB_FORMAT),
                    'city': saveUsedResp['city'],
                     'state':saveUsedResp['state'],
                     'country': saveUsedResp['isoCountryCode'],
                     'timezone':saveUsedResp['timezone'],
                     'mobileNumber': saveUsedResp['mobileNumber'],
                     'contactShown': saveUsedResp['contactShown'],
                     'battingHand': saveProfileResp['battingHandType'],
                     'battingOrder': saveProfileResp['battingOrder'],
                     'bowlingHand': saveProfileResp['bowlingHandType'],
                     'bowlingMethod':saveProfileResp['bowlingMethod'],
                     'fielding': saveProfileResp['fieldPosition']}
    return HttpResponse(json.dumps(response_data), content_type="application/json")


# TODO: move to separate class




@csrf_exempt
def teamsave(request):
    print 10 * '*' + 'team data from ui'
    teamdata = json.loads(request.body)
    print teamdata

    payload_team['teamName'] = teamdata['teamName']
    payload_team['city'] = teamdata['city']
    payload_team['state'] = teamdata['state']
    #payload_team['country'] = teamData['country']
    payload_team['description'] = teamdata['description']

    getcookie(request=request, userrequest='', profilerequest=payload_profile)

    print 'teamId is:'+ teamdata['teamId']
    if(teamdata['teamId'] != ''):
        payload_team['teamId'] = teamdata['teamId']
        teamSaveResp = saveTeam(payload_team)
    else:
        teamSaveResp = createTeam(payload_team)

    print teamSaveResp['teamId']

    # value = request.COOKIES[COOKIE_QSPORS_USER_KEY]
    # if(value != ''):
    #     value = value + ':'
    #
    # value = value + teamSaveResp['teamId']
    # print value;
    # response.set_cookie(COOKIE_QSPORS_TEAMID_LIST, value)

    response_data = {'teamName': teamSaveResp['teamName'],
                     'teamId': teamSaveResp['teamId'],
                     'city': teamSaveResp['city'],
                     'state': teamSaveResp['state'],
                     'country': teamSaveResp['country'],
                     'description': teamSaveResp['description']
                    }

    return HttpResponse(json.dumps(response_data), content_type="application/json")

@csrf_exempt
def getallteam(request):
    print '>' * 50 + 'getteamsbyplayer start,' + '>' * 50

    if request.COOKIES.has_key(COOKIE_QSPORS_USERID):
        userId = request.COOKIES[COOKIE_QSPORS_USERID]
        url =   str.format(URL_GETTEAMBYPLAYER, userId)
        print 'url:'+url
        getcookie(request=request, userrequest='', profilerequest=payload_profile)
        resp = requests.get(url, headers=HEADER)
        print resp.text
        print '>' * 50 + 'getteamsbyplayer end,' + '>' * 50
        if (200 == resp.status_code):
            return HttpResponse(resp.text, content_type="application/json")
    print '>' * 50 + 'error in getteamsbyplayer' + '>' * 50
    response_data = {'resp': 'failure'}
    return HttpResponse(json.dumps(response_data), content_type="application/json")

@csrf_exempt
def savematch(request):
    print '>' * 50 + 'save match start,' + '>' * 50
    matchdata = json.loads(request.body)
    print 'match data from ui'
    print matchdata

    payload_match = {'matchDate':1406635250236}

    if(matchdata['teamId'] == 0):
        print 'error in match save response'
        response_data = {'resp': 'failure'}
        return HttpResponse(json.dumps(response_data), content_type="application/json")

    if (matchdata['matchId'] != '000' and matchdata['matchId'] != 0 and matchdata['matchId'] != '0'):
        print 'update match'
        print matchdata['matchId']
        payload_match['matchId'] =  matchdata['matchId']
    else:
        print 'create new match call'

    if(matchdata['oppTeamId'] != '' and matchdata['oppTeamId'] != 0):
        print 'oppTeamId is'
        print matchdata['oppTeamId']
        payload_match['oppTeamId'] = matchdata['oppTeamId']
    else:
        print 'No id new teamname is'
        print matchdata['oppTeamName']
        payload_match['oppTeamName'] = matchdata['oppTeamName']


    payload_match['teamId'] = matchdata['teamId']
    payload_match['groundName'] = matchdata['groundName']
    payload_match['groundLoc'] = matchdata['groundLoc']
    payload_match['city'] = matchdata['city']
    payload_match['state'] = matchdata['state']
    payload_match['country'] = matchdata['country']
    payload_match['cricketFormat'] = matchdata['cricketFormat']
    payload_match['groundCondition'] = matchdata['groundCondition']
    payload_match['pitchType'] = matchdata['pitchType']
    payload_match['weatherCondition'] = matchdata['weatherCondition']
    payload_match['matchOfficials'] = matchdata['matchOfficials']
    payload_match['maxPlayers'] = matchdata['maxPlayers']
    payload_match['maxOvers'] = matchdata['maxOvers']

    print 'save match request data'
    print json.dumps(payload_match)

    getcookie(request=request, userrequest='', profilerequest=payload_profile)
    resp = requests.post(URL_SAVEMATCH, data=json.dumps(payload_match), headers=HEADER)
    print resp.text
    if (200 == resp.status_code):
        return HttpResponse(resp.text, content_type="application/json")
    print '>' * 50 + 'error in match save' + '>' * 50
    response_data = {'resp': 'failure'}
    return HttpResponse(json.dumps(response_data), content_type="application/json")


@csrf_exempt
def getuserprofile(request, id):
    print '>' * 50 + 'get user profile,' + '>' * 50
    getcookie(request=request, userrequest='', profilerequest=payload_profile)
    user = id
    print 'user >>>>>>>>>>>>>>>>>> user'
    print user
    profile = getProfile(user)
    if(profile != ''):
        return HttpResponse(profile, content_type="application/json")
    response_data = {'resp': 'failure'}
    return HttpResponse(json.dumps(response_data), content_type="application/json")

@csrf_exempt
def searchteam(request):
    print '>' * 50 + 'search team start,' + '>' * 50
    searchdata = json.loads(request.body)
    print searchdata
    exteams = searchdata['notteamIds']
    exteams = exteams.replace("[","")
    exteams = exteams.replace("]","")
    split = searchdata['teamName'].split()

    teamnames=''
    if(len(split) >= 1):
        teamnames = split[0]+'*'

    for index in range(len(split)):
        if(index > 0):
            teamnames = teamnames+' AND teamname:'+split[index]+'*'

    print teamnames
    url = URL_SEARCH_TEAM.format(exteams, teamnames)
    print url
    resp = requests.get(url)
    if (200 == resp.status_code):
        print '>' * 50 + 'search team success,' + '>' * 50
        resp = json.loads(resp.text)
        return HttpResponse(json.dumps(resp['response']['docs']), content_type="application/json")
    print '>' * 50 + 'error in search team' + '>' * 50
    response_data = {'resp': 'failure'}
    return HttpResponse(json.dumps(response_data), content_type="application/json")


@csrf_exempt
def searchplayer(request):
    print '>' * 50 + 'search player start,' + '>' * 50
    searchdata = json.loads(request.body)
    print searchdata
    url = URL_SEARCH_PLAYER.format(searchdata['email']+'*', searchdata['name']+'*')
    resp = requests.get(url)
    if (200 == resp.status_code):
        print '>' * 50 + 'search player success,' + '>' * 50
        resp = json.loads(resp.text)
        return HttpResponse(json.dumps(resp['response']['docs']), content_type="application/json")
    print '>' * 50 + 'error in search player' + '>' * 50
    response_data = {'resp': 'failure'}
    return HttpResponse(json.dumps(response_data), content_type="application/json")

@csrf_exempt
def addplayer(request):
    print '>' * 50 + 'add player to team start,' + '>' * 50
    playerdata = json.loads(request.body)
    print playerdata
    getcookie(request=request, userrequest='', profilerequest=payload_profile)
    resp = requests.post(URL_ADD_PLAYER, data=json.dumps(playerdata), headers=HEADER)
    if (200 == resp.status_code):
        print 'add player success'
        return HttpResponse(json.dumps('{resp:success}'), content_type="application/json")
    print 'add player failed'
    return HttpResponse(json.dumps('{resp:failure}'), content_type="application/json")

@csrf_exempt
def addplayermatch(request):
    print '>' * 50 + 'add player to match,' + '>' * 50
    playerdata = json.loads(request.body)
    for id in playerdata.get('player').get('playerId'):
        payload_add_player['profileId'] = id;
        payload_add_player['teamId'] = playerdata.get('teamId');
        payload_add_player['matchId'] = playerdata.get('matchId');

        data = json.dumps(payload_add_player)
        print URL_ADD_PLAYER_TO_MATCH;
        print data;

        getcookie(request=request, userrequest='', profilerequest=payload_profile)
        resp = requests.post(URL_ADD_PLAYER_TO_MATCH, data=data, headers=HEADER)
        print resp.text;


    return HttpResponse(json.dumps('{resp:failure}'), content_type="application/json")


@csrf_exempt
def addmoderator(request):
    print 10 * '*' + 'start add moderator'
    req = json.loads(request.body)
    print req
    getcookie(request=request, userrequest='', profilerequest=payload_profile)
    resp = requests.post(URL_ADD_MODE, data=json.dumps(req), headers=HEADER)
    if (200 == resp.status_code):
        print 'add moderator success'
        return HttpResponse(json.dumps('{resp:success}'), content_type="application/json")
    print 10 * '*' + 'add moderator - failure'
    response_data = {'resp': 'failure'}
    return HttpResponse(json.dumps(response_data), content_type="application/json")


def getTeam(id, request):
    print '>' * 50 + 'getTeam start,' + '>' * 50
    print 'id'+id
    url = str.format(URL_GETTEAM, id)
    print 'url:'+url
    getcookie(request=request, userrequest='', profilerequest=payload_profile)
    resp = requests.get(url, headers=HEADER)
    print resp.text
    print '>' * 50 + 'getTeam end,' + '>' * 50
    if (200 == resp.status_code):
        return resp.text
    print '>' * 50 + 'error in get Team' + '>' * 50
    response_data = "'resp': 'failure'"
    return response_data

def getLoginToken(data):
    print '>' * 50 + 'start: call login' + '>' * 50
    print 'url:' + URL_LOGIN + 'data:'
    print data
    resp = requests.post(URL_LOGIN, data=json.dumps(data), headers=HEADER)
    print resp.text

    #fix issue with login
    print '>' * 50 + 'end: call login' + '>' * 50
    return resp.text


def getUser(email):
    print '>' * 50 + 'start: getUser' + '>' * 50
    resp = requests.post(URL_GETUSER_BYEMAIL, data=email, headers=HEADER)
    print '>' * 50 + 'end: getUser' + '>' * 50
    # try catch ValueError
    return json.loads(resp.text)

def getUserByid(id):
    print '>' * 50 + 'start: getUser' + '>' * 50
    resp = requests.get(str.format(URL_GETUSER, id), headers=HEADER)
    print '>' * 50 + 'end: getUser' + '>' * 50
    # try catch ValueError
    return json.loads(resp.text)

def getProfile(userKey):
    print '>' * 50 + 'start: getProfile' + '>' * 50
    print 'user key is:', userKey
    resp = requests.get(str.format(URL_GETPROFILE, userKey), headers=HEADER)
    print 'cricket profile'
    print resp.text
    print '>' * 50 + 'end: getProfile' + '>' * 50
    if (200 == resp.status_code):
        return resp.text
    return ''

def saveTeam(teamData):
    print '>' * 50 + 'start: save team' + '>' * 50 + json.dumps(payload_team)
    resp = requests.post(URL_SAVETEAM, data=json.dumps(teamData), headers=HEADER)
    print 'response create team'
    print  resp.status_code
    print resp.text
    print '>' * 50 + 'end: save team' + '>' * 50
    if (200 == resp.status_code):
        return json.loads(resp.text)
    return ''

def createTeam(teamData):
    print '>' * 50 + 'start: create team' + '>' * 50 + json.dumps(teamData)
    resp = requests.put(URL_CREATETEAM, data=json.dumps(teamData), headers=HEADER)
    print 'response create team'
    print  resp.status_code
    print resp.text
    print '>' * 50 + 'end: create team' + '>' * 50
    if (200 == resp.status_code):
        return json.loads(resp.text)
    return ''

def saveuser(editedData):
    print '>' * 50 + 'start: save User' + '>' * 50 + json.dumps(editedData)
    resp = requests.post(URL_SAVEUSER, data=json.dumps(editedData), headers=HEADER)
    print '>' * 50 + 'end: save User' + '>' * 50
    if (200 == resp.status_code):
        return json.loads(resp.text)
    return ''


def saveProfile(editedProfile):
    print '>' * 50 + 'start: save Cricket profile' + '>' * 50
    print json.dumps(editedProfile)

    resp = requests.post(URL_SAVEPROFILE, data=json.dumps(editedProfile), headers=HEADER)
    print 'response code from save profile'
    print  resp.status_code
    print '>' * 50 + 'end: save Cricket profile' + '>' * 50
    if (200 == resp.status_code):
        return json.loads(resp.text)
    return ''


def getcookie(request, userrequest, profilerequest):
    if request.COOKIES.has_key(COOKIE_QSPORS_AUTH_KEY):
        value = request.COOKIES[COOKIE_QSPORS_AUTH_KEY]
        print 'COOKIE_QSPORS_AUTH_KEY >>>' + value
        HEADER['AUTH_KEY'] = value
    if request.COOKIES.has_key(COOKIE_QSPORS_USER_KEY):
        value = request.COOKIES[COOKIE_QSPORS_USER_KEY]
        print 'COOKIE_QSPORS_USER_KEY >>>' + value
    if request.COOKIES.has_key(COOKIE_QSPORS_PROFILE_KEY):
        value = request.COOKIES[COOKIE_QSPORS_PROFILE_KEY]
        profilerequest['cricketProfileId'] = value
        print 'COOKIE_QSPORS_PROFILE_KEY >>>' + value



@csrf_exempt
def jointeam(request):
    print 'join team Called'
    playerdata = json.loads(request.body)
    getcookie(request=request, userrequest='', profilerequest=payload_profile)
    value = request.COOKIES[COOKIE_QSPORS_PROFILE_KEY]

    payload_join_team['profileId'] = value;
    payload_join_team['teamId'] = playerdata.get('teamId');


    data = json.dumps(payload_join_team)
    print URL_JOIN_TEAM
    resp = requests.post(URL_JOIN_TEAM, data=data, headers=HEADER)
    if (200 == resp.status_code):
        print 'join team success'
        return HttpResponse(json.dumps('{resp:success}'), content_type="application/json")
    print 10 * '*' + 'join team - failure'
    response_data = {'resp': 'failure'}
    return ''

@csrf_exempt
def getnotification(request):
    getcookie(request=request, userrequest='', profilerequest=payload_profile)
    print URL_NOTIFICATION
    resp = requests.get(str.format(URL_NOTIFICATION), headers=HEADER)
    if (200 == resp.status_code):
     resp = json.loads(resp.text)
     return HttpResponse(json.dumps(resp), content_type="application/json")
    response_data = {'resp': 'failure'}
    return ''

@csrf_exempt
def addtoteam(request):
    playerdata = json.loads(request.body)
    payload_join_team['profileId'] =  playerdata.get('profileId');
    payload_join_team['teamId'] = playerdata.get('teamId');
    data = json.dumps(payload_join_team)
    getcookie(request=request, userrequest='', profilerequest=payload_profile)
    print URL_ADD_TO_TEAM
    resp = requests.post(URL_ADD_TO_TEAM, data=data, headers=HEADER)
    if (200 == resp.status_code):
        print 'join team success'
        return HttpResponse(json.dumps('{resp:success}'), content_type="application/json")
    print 10 * '*' + 'join team - failure'
    response_data = {'resp': 'failure'}
    return ''

@csrf_exempt
def getteamMatches(dd, request):
    print 'get team api call'
    teamResp = getTeam(id, request)
    return HttpResponse(teamResp, content_type="application/json")