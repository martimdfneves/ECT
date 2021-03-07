from django.shortcuts import render
from django.http import HttpRequest, HttpResponse
from datetime import datetime


def IMC(request):
    assert isinstance(request, HttpRequest)
    if 'peso' in request.POST and 'altura' in request.POST:
        peso = request.POST['peso']
        altura = request.POST['altura']
        if peso and altura:
            return render(
                request,
                'IMCreturn.html',
                {
                    'peso':peso,
                    'altura':altura,
                }
            )
        else:
            return render(
                request,
                'IMC.html',
                {
                    'error':True,
                }
            )
    else:
        return render(
            request,
            'IMC.html',
            {
                'error': False,
            }
        )