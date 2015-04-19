from django.contrib import admin
from whatdoyoudo.models import Mission, MissionNode

class MissionAdmin(admin.ModelAdmin):
    list_display = ("id", "name", "beginning", "language", "created")
    #ordering = ('-created',)
class MissionNodeAdmin(admin.ModelAdmin):
    list_display = ("id", "name", "question", "status", "mission", "node_1", "node_2", "created")
    list_filter = ('mission__id', 'status',)
    #ordering = ('-created',)


admin.site.register(Mission, MissionAdmin)
admin.site.register(MissionNode, MissionNodeAdmin)

