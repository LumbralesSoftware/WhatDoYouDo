from django.contrib.auth.models import User
from rest_framework import serializers
from whatdoyoudo.models import Mission, MissionNode

# Serializers define the API representation.
class MissionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Mission
        fields = ('id', 'name', 'text', 'beginning')
class MissionNodeSerializer(serializers.ModelSerializer):
    class Meta:
        model = MissionNode
        fields = ('id', 'name', 'text', 'answer_1', 'answer_2', 'status', 'node_1', 'node_2', 'mission')
