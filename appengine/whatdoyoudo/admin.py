from django.contrib import admin
from django import forms
from django.utils import timezone
from whatdoyoudo.models import Mission, MissionNode
import copy  # (1) use python copy

def clone_mission(modeladmin, request, queryset):
    # sd is an instance of SemesterDetails
    for mission in queryset:
        #print mission
        mission_copy = copy.copy(mission) # (2) django copy object
        mission_copy.id = None   # (3) set 'id' to None to create new object
        mission_copy.created = timezone.now()
        mission_copy.active = False
        mission_copy.name = mission_copy.name + " (copy)"
        mission_copy.save()    # initial save

        #clone all nodes
        nodes = MissionNode.objects.filter(mission=mission)
        clonedNodes = {}
        for node in nodes:
            #print node
            node_copy = copy.copy(node) # (2) django copy object
            node_copy.id = None   # (3) set 'id' to None to create new object
            node_copy.mission = mission_copy
            node_copy.created = timezone.now()
            #node_copy.name = node.name + " (copy)"
            node_copy.save()    # initial save
            clonedNodes[node] = node_copy

        #print clonedNodes
        #print mission_copy.id
        #reasign references
        for oldNode in clonedNodes:
            newNode = clonedNodes[oldNode]
            #print 'cloned ' + str(newNode)
            if newNode.node_1 is not None:
                newNode.node_1 = clonedNodes[newNode.node_1]
                newNode.save()

            if newNode.node_2 is not None:
                newNode.node_2 = clonedNodes[newNode.node_2]
                newNode.save()

        # Adjust reference to the beginning clonned node
        if mission_copy.beginning is not None:
            mission_copy.beginning = clonedNodes[mission_copy.beginning]
            mission_copy.save()
    return True

class MissionAdmin(admin.ModelAdmin):
    actions = [clone_mission]
    list_display = ("id", "name", "beginning", "language", "active", "created")
    #ordering = ('-created',)

class MissionNodeAdminForm(forms.ModelForm):
    def __init__(self, *args, **kwargs):
        super(MissionNodeAdminForm, self).__init__(*args, **kwargs)

        # access object through self.instance...
        # show only nodes belonging to the mission they are associated
        try:
            mission = self.instance.mission
        except:
            mission = Mission.objects.latest('created')
            self.fields['mission'].initial = mission
            pass

        self.fields['node_1'].queryset = MissionNode.objects.filter(mission=mission)
        self.fields['node_2'].queryset = MissionNode.objects.filter(mission=mission)

class MissionNodeAdmin(admin.ModelAdmin):
    list_display = ("id", "name", "question", "status", "mission", "node_1", "node_2", "created")
    list_filter = ('mission__id', 'status',)
    form = MissionNodeAdminForm
    #ordering = ('-created',)


admin.site.register(Mission, MissionAdmin)
admin.site.register(MissionNode, MissionNodeAdmin)

