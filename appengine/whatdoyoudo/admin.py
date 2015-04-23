from django.contrib import admin
from django import forms
from django.utils import timezone
from whatdoyoudo.models import Mission, MissionNode
import copy  # (1) use python copy

def clone_mission(modeladmin, request, queryset):
    # sd is an instance of SemesterDetails
    for sd in queryset:
        print sd
        sd_copy = copy.copy(sd) # (2) django copy object
        sd_copy.id = None   # (3) set 'id' to None to create new object
        sd_copy.created = timezone.now()
        sd_copy.active = False
        sd_copy.name = sd_copy.name + " (copy)"
        sd_copy.save()    # initial save
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

