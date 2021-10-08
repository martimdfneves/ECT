from django.shortcuts import render
from datetime import datetime
from lxml import etree as ET
from django.http import Http404, HttpResponse
import os
from urllib.request import urlopen
from urllib import parse as UParse
from webproj.settings import BASE_DIR


def home(request):
    tparams = {
        'title': 'Home Page',
        'year': datetime.now().year,
    }
    return render(request, 'index.html', tparams)


def cursos(request):
    fname = 'cursos.xml'
    pname = os.path.join(BASE_DIR, 'app/static/xml/' + fname)
    xml = ET.parse(pname)
    info = dict()
    query = '//curso'
    curs = xml.xpath(query)
    for c in curs:
        info[c.find('guid').text] = c.find('nome').text
    tparams = {
        'cursos':info,
    }
    return render(request, 'cursos.html', tparams)


def curso(request):
    fname = 'cursos.xml'
    pname = os.path.join(BASE_DIR, 'app/data/' + fname)
    xml = ET.parse(pname)
    if not 'guid' in request.GET:
        raise Http404("curso nao existe")
    guid = request.GET['guid']
    celem = xml.find("//curso[guid='{}']".format(guid))
    if celem == None:
        raise Http404("curso nao existe")
    info = dict()
    info['guid'] = celem.find('guid').text
    info['nome'] = celem.find('nome').text
    info['codigo'] = celem.find('codigo').text
    info['grau'] = celem.find('grau').text
    info['departamentos'] = []
    for selem in celem.xpath(".//departamento"):
        info['departamentos'].append(selem.text)
    inf['areascientificas'] = []
    for selem in celem.xpath(".//areascientifica"):
        info['areascientificas'].append(selem.text)
    info['local'] = celem.find('local').text
    tparams = {
        'info': info,
    }
    return render(request, 'curso.html', tparams)