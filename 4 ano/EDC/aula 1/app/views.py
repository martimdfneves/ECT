from django.shortcuts import render
from django.http import HttpRequest, HttpResponse
from datetime import datetime


def hello(request):
    return HttpResponse("Hello World!!!")


def numero(request, num):
    resp = "<html><body><h1>{}</h1></body></html>".format(num)
    return HttpResponse(resp)


def numerot(request, num):
    tparams = {
        'num_arg': num,
    }
    return render(request, 'numerot.html', tparams)