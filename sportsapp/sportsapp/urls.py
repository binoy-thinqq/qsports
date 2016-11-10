from django.conf.urls import patterns, include, url
from django.conf import settings
from django.conf.urls.static import static
from django.contrib import admin

admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'sportsapp.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^crkt/', include('crkt.urls')),
    url(r'^admin/', include(admin.site.urls)),
    url(r'', include('social.apps.django_app.urls', namespace='social')),
)+ static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)