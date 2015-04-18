import dbindexer
from django.conf.urls import url, include
from django.contrib import admin
from django.http import HttpResponse

# django admin
admin.autodiscover()

# search for dbindexes.py in all INSTALLED_APPS and load them
dbindexer.autodiscover()

# Wire up our API using automatic URL routing.
# Additionally, we include login URLs for the browseable API.
urlpatterns = [
    url(r'^$', lambda r: HttpResponse("", mimetype="text/plain")),
    url(r'^_ah/warmup$', 'djangoappengine.views.warmup'),
    url(r'^robots\.txt$', lambda r: HttpResponse("User-agent: *\nDisallow: /", mimetype="text/plain")),
    url(r'^admin/', include(admin.site.urls)),
    url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework'))
]
