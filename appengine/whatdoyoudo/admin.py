from django.contrib import admin
from django import forms
from whatdoyoudo.models import Mission, MissionNode


class MissionAdmin(admin.ModelAdmin):
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

