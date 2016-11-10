from django.db import models
from django.contrib.auth.models import AbstractUser


class User(AbstractUser):
    """
    Users within the Django authentication system are represented by this
    model.

    Username, password and email are required. Other fields are optional.
    """
    avatar = models.URLField('URL',null=True,blank=True)
    registrationType = models.CharField(max_length=255)

    class Meta(AbstractUser.Meta):
        swappable = 'AUTH_USER_MODEL'