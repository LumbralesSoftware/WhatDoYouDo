from django.conf.urls import patterns, include

from rest_framework import routers
from whatdoyoudo.views import MissionNodeViewSet, MissionViewSet

# Routers provide a way of automatically determining the URL conf.
router = routers.DefaultRouter()
#router.register(r'users', UserViewSet)
router.register(r'nodes', MissionNodeViewSet, 'MissionNode')
router.register(r'missions', MissionViewSet, 'Mission')

urlpatterns = patterns('whatdoyoudo.views',
    (r'^', include(router.urls)),
)

