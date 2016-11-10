title Qsports app sequence

note over client,django backend: google/facebook/**

client->+django backend: oauth2 success(email, firstname etc)
django backend->+Service layer: getUserByEmail(email)

Service layer-->-django backend: UserProfile json(USERKEY as json param) / 404 if no user exist for this email

 alt if getUserByEmail return 404
    django backend->+Service layer: saveUser(UserProfile json)[USERKEY will be null in json. Create new user in this case]
    Service layer-->-django backend: UserProfile json(USERKEY as json param)
 end

django backend-->-client: UserProfile json

client ->+django backend: on save of user profile edit page.
django backend->+Service layer: saveUser(UserProfile json)
Service layer-->-django backend: UserProfile json(USERKEY as json param)
django backend-->-client:UserProfile json on callback

client ->django backend: onLoad of team profile ui
django backend->+Service layer:getTeam(request json. userKey will be preset in the request)
Service layer-->-django backend: TeamProfile json(TEAMKEY as json param) / 404 if no team for this user
django backend-->-client:TeamProfile json on callback 


client ->+django backend: on save of edit team profile page save
django backend->+Service layer: saveTeam(TeamProfile json)
Service layer-->-django backend: TeamProfile json(TEAMKEY as json param)
django backend-->-client:Teamprofile json on callback


client ->+django backend:getMatch (teamKey or UserKey ??)
client ->+django backend:saveMatch (teamKey?, team2Key user ???)
