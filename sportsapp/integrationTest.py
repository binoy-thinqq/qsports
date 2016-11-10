__author__ = 'rakeshpullayikodi'

import requests
import json
import sys

URL_SERVER = 'http://dev.thinqq.com:8080/qs'
HEADER = {'AUTH_KEY': '', 'SECRET_KEY': '0IGpk3GTkNBEbO8v8ei35J1vRY9v7WR1', 'content-type': 'application/json',
          'ACCEPT': 'application/json'}

############ login ###############
URL_LOGIN = URL_SERVER + '/ws/ckt/login'
PAYLOAD_LOGIN = {'firstName': '', 'email': '', 'registrationType': ''}

############ get userby email ###############
URL_GETUSER = URL_SERVER + '/ws/ckt/getuseremail'


############ save user ###############
URL_SAVEUSER = URL_SERVER + '/ws/ckt/saveuser'
PAYLOAD_USER = {'firstName': '', 'lastName': '', 'gender': '', 'city': '', 'state': '',
                'isoCountryCode': '', 'timezone': 'IST', 'locale': 'en_US', 'dob': '393100200000',
                'notes': '', 'mobileNumber': '', 'emailShown': 'true', 'contactShown': 'true'}


############ get and save profile ###############
URL_GETPROFILE = URL_SERVER + '/ws/ckt/getcricketprofile/{}&isMin=false'
URL_SAVEPROFILE = URL_SERVER + '/ws/ckt/savecricketprofile'
PAYLOAD_PROFILE = {'cricketProfileId': '', 'battingHandType': '', 'battingOrder': '', 'bowlingHandType': '',
                   'bowlingMethod': '', 'fieldPosition': '', 'wicketKeeper': ''}


def main():
    print 'script to make sure services are up and running'

    PAYLOAD_LOGIN['firstName'] = 'Rak KP'
    PAYLOAD_LOGIN['email'] = 'rakeshnbr@gmail.com'
    PAYLOAD_LOGIN['registrationType'] = 'GOOGLE'

    #login
    HEADER['AUTH_KEY'] = login(PAYLOAD_LOGIN)

    #get
    user = getUser(PAYLOAD_LOGIN['email'])

    #save
    saveUser()

    #get again
    savedUser = getUser(PAYLOAD_LOGIN['email'])
    print savedUser['state']
    assert 'KL' == savedUser['state'], "saved data not found"


    # #get profile
    profile = getProfile(user['userId'])
    print profile['cricketProfileId']
    #
    # #save profile
    saveProfile(profile['cricketProfileId'])

    #get profile again
    getProfile(user['userId'])


def saveProfile(key):
    print '>' * 50 + 'start: save Cricket profile' + '>' * 50
    PAYLOAD_PROFILE['cricketProfileId'] = key
    PAYLOAD_PROFILE['battingHandType'] = 1
    PAYLOAD_PROFILE['bowlingHandType'] = 1
    PAYLOAD_PROFILE['bowlingMethod'] = 1
    PAYLOAD_PROFILE['fieldPosition'] = 1
    PAYLOAD_PROFILE['battingOrder'] = 1
    PAYLOAD_PROFILE['wicketKeeper'] = 'true'
    resp = requests.post(URL_SAVEPROFILE, data=json.dumps(PAYLOAD_PROFILE), headers=HEADER)
    print  resp.status_code
    print '>' * 50 + 'end: save Cricket profile' + '>' * 50


def saveUser():
    print '>' * 50 + 'start: save User' + '>' * 50
    PAYLOAD_USER['firstName'] = 'r'
    PAYLOAD_USER['lastName'] = 'kp'
    PAYLOAD_USER['gender'] = 'M'
    PAYLOAD_USER['city'] = 'kannur'
    PAYLOAD_USER['state'] = 'KL'
    PAYLOAD_USER['isoCountryCode'] = 'IND'
    PAYLOAD_USER['timezone'] = 'IST'

    print URL_SAVEUSER
    print json.dumps(PAYLOAD_USER)

    resp = requests.post(URL_SAVEUSER, data=json.dumps(PAYLOAD_USER), headers=HEADER)
    print json.dumps(resp.text)
    print resp.status_code

    print '>' * 50 + 'end: save User' + '>' * 50


def login(data):
    print  '>' * 50 + 'start: login' + '>' * 50
    print URL_LOGIN
    print json.dumps(data)
    resp = requests.post(URL_LOGIN, data=json.dumps(data), headers=HEADER)
    print resp.text
    #fix issue with second login
    # print '>'*50+'end: login'+'>'*50
    # return 'YWc1emZtSnBibTk1Y1hOd2IzSjBjM0lSQ3hJRVZYTmxjaGlBZ0lDQWdPU1JDUXc='
    return resp.text


def getProfile(userId):
    print '>' * 50 + 'start: getProfile' + '>' * 50
    resp = requests.get(str.format(URL_GETPROFILE, userId), headers=HEADER)
    profile = json.loads(resp.text)
    print profile
    print '>' * 50 + 'end: getProfile' + '>' * 50
    return profile


def getUser(email):
    print '>' * 50 + 'start: getUser by email' + '>' * 50
    print URL_GETUSER
    resp = requests.post(URL_GETUSER, data=email, headers=HEADER)
    user = json.loads(resp.text)
    print user
    print '>' * 50 + 'end: getUser userId id' + '>' * 50
    print user['userId']
    return user

# This is the standard boilerplate that calls the main() function.
if __name__ == '__main__':
    main()