import logging
import json
import datetime
from django.http import HttpResponse, HttpResponseBadRequest
from django.views.decorators.http import require_POST
from django.shortcuts import get_object_or_404
from django.utils.translation import ugettext as _
from rest_framework import viewsets
from whatdoyoudo.models import Mission, MissionNode
from whatdoyoudo.serializers import MissionNodeSerializer, MissionSerializer

class MissionViewSet(viewsets.ModelViewSet):
    serializer_class = MissionSerializer

    def get_queryset(self):
        """
        Optionally restricts the returned mission to a given language
        by filtering against a `language` query parameter in the URL.
        """
        queryset = Mission.objects.all()
        language = self.request.QUERY_PARAMS.get('language', None)
        if language is not None:
            queryset = queryset.filter(language=language)
        return queryset

    def update(self, request, *args, **kwargs):
        logging.info(request.DATA)
        return super(MissionViewSet, self).update(request, *args, **kwargs)

    def create(self, request, *args, **kwargs):
        logging.info(request.DATA)
        return super(MissionViewSet, self).create(request, *args, **kwargs)

class MissionNodeViewSet(viewsets.ModelViewSet):
    #queryset = MissionNode.objects.all()
    serializer_class = MissionNodeSerializer
    model = MissionNode

    def get_queryset(self):
        """
        Optionally restricts the returned nodes to a given mission
        by filtering against a `mission` query parameter in the URL.
        """
        queryset = MissionNode.objects.all()
        mission = self.request.QUERY_PARAMS.get('mission', None)
        if mission is not None:
            queryset = queryset.filter(mission=mission)
        return queryset

    def update(self, request, *args, **kwargs):
        logging.info(request.DATA)
        return super(MissionNodeViewSet, self).update(request, *args, **kwargs)

    def create(self, request, *args, **kwargs):
        logging.info(request.DATA)
        return super(MissionNodeViewSet, self).create(request, *args, **kwargs)

