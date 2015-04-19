import datetime
from django.db import models
from django.core.validators import MaxLengthValidator
from django.db.models.signals import pre_save
from django.dispatch import receiver

class Mission(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=200, verbose_name="Mission name")
    text = models.TextField(max_length=1000, verbose_name="Mission Description")
    beginning = models.ForeignKey('MissionNode', verbose_name="First node", related_name="beginning", null=True, blank=True)
    created = models.DateTimeField(verbose_name="Created date", null=True, blank=True, auto_now_add=True)

    def __str__(self):
        return unicode(self).encode('utf-8')

    def __unicode__(self):
        return self.name


class MissionNode(models.Model):
    STATUS = ((1, 'Win'), (2, 'Lose'), (3, 'Continue'))

    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=200, verbose_name="Stop name")
    text = models.TextField(max_length=1000, verbose_name="Description text/question")
    question = models.CharField(max_length=300, verbose_name="Question", null=True, blank=True)
    answer_1 = models.CharField(max_length=200, verbose_name="Answer 1", null=True, blank=True)
    answer_2 = models.CharField(max_length=200, verbose_name="Answer 2", null=True, blank=True)
    status = models.IntegerField(max_length=1, choices=STATUS, verbose_name="Node status", null=True, default=3)
    node_1 = models.ForeignKey('MissionNode', verbose_name="Answer 1 next", related_name="next_1", null=True, blank=True)
    node_2 = models.ForeignKey('MissionNode', verbose_name="Answer 2 next", related_name="next_2", null=True, blank=True)
    mission = models.ForeignKey('Mission', verbose_name="Mission", related_name="mission")
    created = models.DateTimeField(verbose_name="Created date", null=True, blank=True, auto_now_add=True)

    def __str__(self):
        return unicode(self).encode('utf-8')

    def __unicode__(self):
        return self.name

