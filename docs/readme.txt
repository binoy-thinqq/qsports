1) install python 2.7.*

http://www.python.org/getit/releases/2.7.5/

just play around with command line

sh-3.2# python --version
Python 2.7.5

sh-3.2# python
Python 2.7.5 (default, Aug 25 2013, 00:04:04)
[GCC 4.2.1 Compatible Apple LLVM 5.0 (clang-500.0.68)] on darwin
Type "help", "copyright", "credits" or "license" for more information.
>>> print 'hi hello world'
hi hello world
>>> print 1+3
4

>>> lst = [1,2,3]
>>> lst[2]
3

Best place to start with python
https://developers.google.com/edu/python/



2) Starting with django

install it
sh-3.2# pip install django==1.6

create the first sample project
https://docs.djangoproject.com/en/1.6/intro/tutorial01/


3) install dependent libraries before starting our project

sh-3.2# pip install python-social-auth


4) got to our project directory and syncdb

sh-3.2# python manage.py syncdb
Creating tables ...
Creating table django_admin_log
Creating table auth_permission
Creating table auth_group_permissions
Creating table auth_group
Creating table auth_user_groups
Creating table auth_user_user_permissions
Creating table auth_user
Creating table django_content_type
Creating table django_session
Creating table social_auth_usersocialauth
Creating table social_auth_nonce
Creating table social_auth_association
Creating table social_auth_code

You just installed Django's auth system, which means you don't have any superusers defined.
Would you like to create one now? (yes/no): yes
Username (leave blank to use 'root'): root
Email address: rakesh@thinqq.com
Password:
Password (again):
Superuser created successfully.
Installing custom SQL ...
Installing indexes ...
Installed 0 object(s) from 0 fixture(s)


5) before starting server, make one config change

open the file

 sportsapp/settings.py


remove "/Users/rakeshpullayikodi/QSports_new" and add your local path

STATICFILES_DIRS = (
  "/Users/rakeshpullayikodi/QSports_new/sportsapp/static/",
)


5)start server

sh-3.2# python manage.py runserver localhost:8080
Validating models...

0 errors found
January 22, 2014 - 03:55:25
Django version 1.6.1, using settings 'sportsapp.settings'
Starting development server at http://localhost:8080/
Quit the server with CONTROL-C.


>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Starting url >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
http://localhost:8080/crkt/


>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> FAQ >>>>>>>>>>>>>>>>>>>>>>>>>>

1) What ide? pycharm is one good

 http://www.jetbrains.com/pycharm/download/

2) some common errors

comma, white space & indentation  really matters in python





