import decimal

from django.shortcuts import render
from django.http import HttpResponse, HttpRequest, HttpResponseRedirect
import os
import xmltodict
from BaseXClient import BaseXClient
import requests
from Wiki.settings import BASE_DIR
from s4api.graphdb_api import GraphDBApi
from s4api.swagger import ApiClient
import json
# Create your views here.

continentsdict = {
    'Europe': 'Europe',
    'Asia': 'Asia',
    'Australia$Oceania': 'Australia/Oceania',
    'North+America': 'North America',
    'South+America': 'South America',
    'Africa': 'Africa'
}

def home(request):
    return render(request, 'index.html')


def continents(request, continent):

    query = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
            "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
            "SELECT ?X ?C ?N WHERE {" \
            "?X mon:name ?N . " \
            "?X rdf:type mon:Country . " \
            "?X mon:encompassed ?C ." \
            "?C mon:name \""+continentsdict.get(continent)+"\" ." \
            "} ORDER BY ?N"
    dic = executeQuery(query)

    countries = {}
    for q in dic["results"]["bindings"]:
        country_code = q["X"]["value"].split("/")[-2]
        countries[country_code] = q["N"]["value"]

    content = {
        'continent_id': continent,
        'continent': continentsdict.get(continent),
        'countries': countries,
    }
    return render(request, 'continent.html', content)


def country(request, country_code):
    query1 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?X ?name ?area ?capital_name ?localname ?popul ?popul_growth ?gdp_total ?inflation ?unemployment ?indep_date ?dependent_name ?government WHERE {" \
             "?X mon:name ?name . " \
             "?X mon:area ?area ." \
             "?X mon:capital ?capital ." \
             "?capital mon:name ?capital_name ." \
             "?X mon:population ?popul ." \
             "OPTIONAL{?X mon:localname ?localname .}" \
             "OPTIONAL{?X mon:populationGrowth ?popul_growth .}" \
             "OPTIONAL {?X mon:gdpTotal ?gdp_total .}" \
             "OPTIONAL {?X mon:inflation ?inflation .}" \
             "OPTIONAL {?X mon:unemployment ?unemployment .}" \
             "OPTIONAL {?X mon:independenceDate ?indep_date .}" \
             "OPTIONAL {?X mon:dependentOf ?dependent . ?dependent mon:name ?dependent_name}" \
             "OPTIONAL {?X mon:government ?government .}" \
             "?X rdf:type mon:Country . " \
             "FILTER ( strends(str(?X), 'countries/"+country_code+"/') )" \
                                                       "}"

    dic1 = executeQuery(query1)
    dic1 = dic1["results"]["bindings"][0]
    area = dic1["area"]["value"]
    capital = dic1["capital_name"]["value"]
    name = dic1["name"]["value"]

    localname = ""
    if "localname" in list(dic1.keys()):
        localname = dic1["localname"]["value"]

    growth = 0
    if "popul_growth" in list(dic1.keys()):
        growth = dic1["popul_growth"]["value"]

    pib = 0
    if "gdp_total" in list(dic1.keys()):
        pib = dic1["gdp_total"]["value"]

    inf = 0
    if "inflation" in list(dic1.keys()):
        inf = dic1["inflation"]["value"]

    desemprego = 0
    if "unemployment" in list(dic1.keys()):
        desemprego = dic1["unemployment"]["value"]

    indep = ""
    if "indep_date" in list(dic1.keys()):
        indep = dic1["indep_date"]["value"]

    dep = ""
    if "dependent_name" in list(dic1.keys()):
        dep = dic1["dependent_name"]["value"]

    gov=""
    if "government" in list(dic1.keys()):
        gov = dic1["government"]["value"]

    query2 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?year ?value WHERE {" \
             "?X mon:hadPopulation ?hadPopul ." \
             "?hadPopul mon:year ?year ." \
             "?hadPopul mon:value ?value ." \
             "?X rdf:type mon:Country . " \
             "FILTER ( strends(str(?X), 'countries/"+country_code+"/') )}"

    dic2 = executeQuery(query2)
    arr = dic2["results"]["bindings"]

    popul = {}
    if len(arr) != 0:
        for record in arr:
            popul[record["year"]["value"]] = record["value"]["value"]
    else:
        if "population" in list(dic1.keys()):
            popul = dic1["population"]["value"]

    query3 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?percent ?cont_name WHERE {" \
             "?E rdf:type mon:Encompassed ." \
             "?E mon:percent ?percent ." \
             "?E mon:encompassedArea ?country ." \
             "?E mon:encompassedBy ?cont . " \
             "?cont mon:name ?cont_name ." \
             "FILTER ( strends(str(?country), 'countries/"+country_code+"/') )" \
            "} ORDER BY DESC(?percent)"

    dic3 = executeQuery(query3)
    arr = dic3["results"]["bindings"]
    contido2 = {}

    for record in arr:
        contido2[record["cont_name"]["value"]] = record["percent"]["value"]

    query4 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?name ?percent WHERE {" \
             "?E rdf:type mon:EthnicProportion . " \
             "?E mon:onEthnicGroup ?ethnicgroup ." \
             "?ethnicgroup mon:name ?name . " \
             "?E mon:percent ?percent . " \
             "?E mon:ethnicInfo- ?country ." \
             "FILTER ( strends(str(?country), 'countries/"+country_code+"/') )" \
            "}ORDER BY DESC(?percent)"
    dic4 = executeQuery(query4)
    arr = dic4["results"]["bindings"]
    ethnic = {}
    for record in arr:
        ethnic[record["name"]["value"]] = record["percent"]["value"]

    query5 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?name ?percent WHERE {" \
             "?R rdf:type mon:BelievedBy . " \
             "?R mon:onReligion ?religion ." \
             "?religion mon:name ?name ." \
             "?R mon:percent ?percent ." \
             "?R mon:religionInfo- ?country ." \
             "FILTER ( strends(str(?country), 'countries/"+country_code+"/') )" \
                                                            "}ORDER BY DESC(?percent)"
    dic5 = executeQuery(query5)
    arr = dic5["results"]["bindings"]
    rel = {}
    for record in arr:
        rel[record["name"]["value"]] = record["percent"]["value"]

    query6 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?name ?percent WHERE {" \
             "?L rdf:type mon:SpokenBy . " \
             "?L mon:onLanguage ?language ." \
             "?language mon:name ?name . " \
             "?L mon:percent ?percent . " \
             "?L mon:languageInfo- ?country ." \
             "FILTER ( strends(str(?country), 'countries/"+country_code+"/') )" \
                                                            "}ORDER BY DESC(?percent)"
    dic6 = executeQuery(query6)
    arr = dic6["results"]["bindings"]
    lang = {}
    for record in arr:
        lang[record["name"]["value"]] = record["percent"]["value"]

    query7 ="prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
            "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
            "SELECT ?length ?name WHERE {" \
            "?B rdf:type mon:Border . " \
            "?B mon:length ?length ." \
            "?B mon:isBorderOf ?countries ." \
            "FILTER ( strends(str(?countries), 'countries/"+country_code+"/') )" \
            "?B mon:isBorderOf ?country ." \
            "?B mon:length ?length . " \
            "?country mon:name ?name ." \
            "FILTER(!strends(str(?country), 'countries/"+country_code+"/'))" \
            "} ORDER BY DESC(?length)"
    dic7 = executeQuery(query7)
    arr = dic7["results"]["bindings"]
    bor2 = {}
    for record in arr:
        bor2[record["name"]["value"]] = record["length"]["value"]

    query8 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?P ?name WHERE {" \
             "?X rdf:type mon:Country ." \
             "?X mon:hasProvince ?P . " \
             "?P mon:name ?name . " \
             "FILTER ( strends(str(?X), 'countries/"+country_code+"/') )" \
                                                      "}ORDER BY ?name"
    dic8 = executeQuery(query8)
    arr = dic8["results"]["bindings"]
    provincias = {}
    for record in arr:
        province_code = record["P"]["value"].split("/")[-2]
        provincias[province_code] = record["name"]["value"]

    query9 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?C ?name WHERE {" \
             "?X rdf:type mon:Country ." \
             "?X mon:hasCity ?C . " \
             "?C mon:name ?name . " \
             "FILTER ( strends(str(?X), 'countries/"+country_code+"/') )" \
                                                      "} ORDER BY ?name"
    dic9 = executeQuery(query9)
    arr = dic9["results"]["bindings"]
    cidades = {}
    for record in arr:
        city_code = record["C"]["value"].split("/")[-2]
        cidades[city_code] = record["name"]["value"]

    content = {
        'country_code': country_code,
        'area': area,
        'capital': capital,
        'name': name,
        'localname': localname,
        'population': popul,
        'population_growth': growth,
        'gdp_total': pib,
        'inflation': inf,
        'unemployment': desemprego,
        'dependent': dep,
        'indep_date': indep,
        'government': gov,
        'encompassed': contido2,
        'ethnicgroup': ethnic,
        'religion': rel,
        'language': lang,
        'border': bor2,
        'province': provincias,
        'city': cidades,
    }
    return render(request, 'country.html', content)

def seas(request):

    query = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
            "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
            "SELECT ?X ?N WHERE {" \
            "?X mon:name ?N . " \
            "?X rdf:type mon:Sea . " \
            "}"
    dic = executeQuery(query)

    seas = {}
    for q in dic["results"]["bindings"]:
        sea_code = q["X"]["value"].split("/")[-2]
        seas[sea_code] = q["N"]["value"]

    content = {
        'seas': seas,
    }
    return render(request, 'seas.html', content)


def sea(request, sea_id):

    #info do mar
    query1 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
            "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
            "SELECT ?name ?area ?depth WHERE {" \
            "?X mon:name ?name . " \
            "?X rdf:type mon:Sea . " \
            "?X mon:depth ?depth . " \
            "OPTIONAL{?X mon:area ?area.}" \
            "FILTER ( strends(str(?X), 'seas/"+sea_id+"/') )" \
                                                            "}"
    dic1 = executeQuery(query1)

    dic1 = dic1["results"]["bindings"]
    area = ""
    for r in dic1:
        name = r["name"]["value"]
        if 'area' in r.keys():
            area = r["area"]["value"]
        depth = r["depth"]["value"]

    #obter países que são banhados pelo mar
    query2 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?name WHERE {" \
             "?O mon:locatedIn ?country . " \
             "?country mon:name ?name ." \
             "?O rdf:type mon:Sea . " \
             "FILTER ( strends(str(?O), 'seas/"+sea_id+"/') )" \
                                                             "}ORDER BY (?name)"
    dic2 = executeQuery(query2)

    sea_countries = []
    for q in dic2["results"]["bindings"]:
        country = q["name"]["value"]
        sea_countries.append(country)

    content = {
        "name": name,
        "area": area,
        "depth": depth,
        "sea_countries": sea_countries
    }
    return render(request, 'sea.html', content)


def rivers(request):

    query = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
            "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
            "SELECT ?R ?name WHERE {" \
            "?R mon:name ?name . " \
            "?R rdf:type mon:River . " \
            "}ORDER BY (?name)"
    dic = executeQuery(query)

    rivers = {}
    for q in dic["results"]["bindings"]:
        river_code = q["R"]["value"].split("/")[-2]
        rivers[river_code] = q["name"]["value"]

    content = {
        "rivers" : rivers
    }

    return render(request, 'rivers.html', content)


def river(request, river_id):

    #info geral do rio                              ESTA QUERY RETORNA VAZIO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    query1 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?X ?name ?area ?length ?flows_to ?source_long ?source_lat ?estuary_long ?estuary_lat WHERE {" \
             "?X mon:name ?name . " \
             "?X rdf:type mon:River . " \
             "OPTIONAL{ ?X mon:area ?area}" \
             "OPTIONAL{ ?X mon:length ?length}" \
             "OPTIONAL{ ?X mon:flowsInto ?f . ?f mon:name ?flows_to}" \
             "OPTIONAL{?S rdf:type mon:Source ." \
             "?S mon:longitude ?source_long ." \
             "?S mon:latitude ?source_lat ." \
             "FILTER ( strends(str(?S), 'sources/"+river_id+"/') )}" \
             "OPTIONAL{?E rdf:type mon:Estuary ." \
             "?E mon:longitude ?estuary_long ." \
             "?E mon:latitude ?estuary_lat ." \
             "FILTER ( strends(str(?E), 'estuarys/"+river_id+"/') )}" \
             "FILTER ( strends(str(?X), 'rivers/"+river_id+"/') )" \
                                                     "}"
    dic1 = executeQuery(query1)

    area = ""
    source_lat = ""
    source_long = ""
    source_elev = ""
    estuary_lat = ""
    estuary_long = ""
    estuary_elev = ""
    flows_to = ""
    dic1 = dic1['results']['bindings']
    #print(dic1)
    for r in dic1:
        name = r['name']['value']
        length = r['length']['value']
        if 'area' in r.keys():
            area = r['area']['value']
        if 'source_lat' in r.keys():
            source_lat = r['source_lat']['value']
        if 'source_long' in r.keys():
            source_long = r['source_long']['value']
        if 'source_elev' in r.keys():
            source_elev = r['source_elev']['value']
        if 'estuary_lat' in r.keys():
            estuary_lat = r['estuary_lat']['value']
        if 'estuary_long' in r.keys():
            estuary_long = r['estuary_long']['value']
        if 'estuary_elev' in r.keys():
            estuary_elev = r['estuary_elev']['value']
        if 'flows_to' in r.keys():
            flows_to = r['flows_to']['value']

    #países por onde passa
    query2 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?name {" \
             "?C mon:name ?name ." \
             "?C rdf:type mon:Country ." \
             "?R mon:locatedIn ?C." \
             "?R rdf:type mon:River ." \
             "FILTER ( strends(str(?R),'rivers/"+river_id+"/') )" \
                                                     "}"
    dic2 = executeQuery(query2)
    print(dic2)

    countries = []
    for q in dic2["results"]["bindings"]:
        country = q["name"]["value"]
        countries.append(country)
    print(countries)

    content = {
        "name": name,
        "area": area,
        "length": length,
        "source_lat": source_lat,
        "source_long": source_long,
        "source_elev": source_elev,
        "flows_through": countries,
        "estuary_lat": estuary_lat,
        "estuary_long": estuary_long,
        "estuary_elev": estuary_elev,
        "flows_to": flows_to,
    }
    return render(request, 'river.html', content)


def lakes(request):

    query = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
            "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
            "SELECT ?L ?name WHERE {" \
            "?L mon:name ?name . " \
            "?L rdf:type mon:Lake . " \
            "}ORDER BY ?name"
    dic = executeQuery(query)

    lakes = {}
    for q in dic["results"]["bindings"]:
        lake_code = q["L"]["value"].split("/")[-2]
        lakes[lake_code] = q["name"]["value"]

    content = {
        "lakes": lakes
    }
    return render(request, 'lakes.html', content)


def lake(request, lake_id):

    #info do lago
    query = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
            "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
            "SELECT ?X ?name ?long ?lat ?elev ?area ?depth ?type ?flows_to ?located_in WHERE {" \
            "?X mon:name ?name . " \
            "?X rdf:type mon:Lake . " \
            "?X mon:longitude ?long ." \
            "?X mon:latitude ?lat ." \
            "OPTIONAL{?X mon:elevation ?elev .}" \
            "OPTIONAL{?X mon:area ?area .}" \
            "OPTIONAL{?X mon:depth ?depth .}" \
            "OPTIONAL{?X mon:type ?type .}" \
            "OPTIONAL{?X mon:flowsInto ?F . ?F mon:name ?flows_to}" \
            "OPTIONAL{?X mon:locatedIn ?C . ?C rdf:type mon:Country . ?C mon:name ?located_in}" \
            "FILTER ( strends(str(?X), 'lakes/"+lake_id+"/') )" \
                                                         "}"
    dic = executeQuery(query)

    elev = ""
    area = ""
    depth = ""
    typ = ""
    flows_to = ""
    located_in = ""
    dic = dic['results']['bindings']
    for q in dic:
        name = q['name']['value']
        long = q['long']['value']
        lat = q['lat']['value']
        if 'elev' in q.keys():
            elev = q['elev']['value']
        if 'area' in q.keys():
            area = q['area']['value']
        if 'depth' in q.keys():
            depth = q['depth']['value']
        if 'type' in q.keys():
            typ = q['type']['value']
        if 'flows_to' in q.keys():
            flows_to = q['flows_to']['value']
        if 'located_in' in q.keys():
            located_in = q['located_in']['value']

    content = {
        "name": name,
        "long": long,
        "lat": lat,
        "elev": elev,
        "area": area,
        "depth": depth,
        "type": typ,
        "flows_to": flows_to,
        "located_in": located_in,
    }
    return render(request, 'lake.html',content)

def mountains(request):

    query = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
            "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
            "SELECT ?M ?name WHERE {" \
            "?M mon:name ?name . " \
            "?M rdf:type mon:Mountain . " \
            "}ORDER BY ?name"
    dic = executeQuery(query)

    mountains = {}
    for q in dic["results"]["bindings"]:
        mountain_code = q["M"]["value"].split("/")[-2]
        mountains[mountain_code] = q["name"]["value"]

    content = {
        'mountains': mountains,
    }
    return render(request, 'mountains.html', content)


def mountain(request, mountain_id):

    #info montanha
    query1 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
            "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
            "SELECT ?X ?name ?long ?lat ?elev ?type ?last_erupt WHERE {" \
            "?X mon:name ?name . " \
            "?X rdf:type mon:Mountain . " \
            "?X mon:longitude ?long ." \
            "?X mon:latitude ?lat ." \
            "?X mon:elevation ?elev ." \
            "OPTIONAL{?X mon:type ?type.}" \
            "OPTIONAL{?X mon:lastEruption ?last_erupt.}" \
            "FILTER ( strends(str(?X), 'mountains/"+mountain_id+"/') )" \
                                                                  "}"
    dic1 = executeQuery(query1)

    typ = ""
    last_erupt = ""
    dic1 = dic1['results']['bindings']
    for q in dic1:
        name = q['name']['value']
        long = q['long']['value']
        lat = q['lat']['value']
        elev = q['elev']['value']
        if 'type' in q.keys():
            typ = q['type']['value']
        if 'last_erupt' in q.keys():
            last_erupt = q['last_erupt']['value']

    #paises da montanha
    query2 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?country WHERE { " \
             "?X rdf:type mon:Mountain . " \
             "?X mon:locatedIn ?C ." \
             "?C rdf:type mon:Country ." \
             "?C mon:name ?country ." \
             "FILTER ( strends(str(?X), 'mountains/"+mountain_id+"/') )" \
                                                           "}"
    dic2 = executeQuery(query2)

    mount_countries = []
    for q in dic2['results']['bindings']:
        country = q['country']['value']
        mount_countries.append(country)

    #provincias da montanha
    query3 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?country WHERE { " \
             "?X rdf:type mon:Mountain . " \
             "?X mon:locatedIn ?C ." \
             "?C rdf:type mon:Province ." \
             "?C mon:name ?country ." \
             "FILTER ( strends(str(?X), 'mountains/"+mountain_id+"/') )" \
                                                           "}"
    dic3 = executeQuery(query3)

    mount_provinces = []
    for r in dic3['results']['bindings']:
        province = r['country']['value']
        mount_provinces.append(province)

    content = {
        'mount_name': name,
        'mount_lat': lat,
        'mount_longe': long,
        'mount_elev': elev,
        'type': typ,
        'last_eruption': last_erupt,
        'mount_countries': mount_countries,
        'mount_provinces' : mount_provinces,
    }
    return render(request, 'mountain.html', content)


def islands(request):

    query = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
            "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
            "SELECT ?X ?name WHERE {" \
            "?X mon:name ?name . " \
            "?X rdf:type mon:Island . " \
            "}ORDER BY ?name"
    dic = executeQuery(query)

    islands = {}
    for q in dic["results"]["bindings"]:
        island_code = q["X"]["value"].split("/")[-2]
        islands[island_code] = q["name"]["value"]

    content = {
        'islands': islands,
    }
    return render(request, 'islands.html', content)


def island(request, island_id):

    #info ilha
    query1 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?name ?long ?lat ?elev ?area ?type ?archipelago WHERE {" \
             "?X mon:name ?name . " \
             "?X rdf:type mon:Island . " \
             "?X mon:longitude ?long ." \
             "?X mon:latitude ?lat ." \
             "?X mon:elevation ?elev ." \
             "?X mon:area ?area ." \
             "OPTIONAL{?X mon:type ?type.}" \
             "OPTIONAL{?X mon:belongsToArchipelago ?A . ?A mon:name ?archipelago}" \
             "FILTER ( strends(str(?X), 'islands/"+island_id+"/') )" \
                                                          "}"
    dic1 = executeQuery(query1)

    typ = ""
    archipelago = ""
    dic1 = dic1['results']['bindings']
    for q in dic1:
        name = q['name']['value']
        area = q['area']['value']
        longe = q['long']['value']
        lat = q['lat']['value']
        elev = q['elev']['value']
        if 'type' in q.keys():
            typ = q['type']['value']
        if 'archipelago' in q.keys():
            archipelago = q['archipelago']['value']

    #paises na ilha
    query2 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?country WHERE {" \
             "?X rdf:type mon:Island . " \
             "?X mon:locatedIn ?C ." \
             "?C rdf:type mon:Country ." \
             "?C mon:name ?country ." \
             "FILTER ( strends(str(?X), 'islands/"+island_id+"/') )" \
                                                        "}"
    dic2 = executeQuery(query2)

    di = []
    for i in dic2['results']['bindings']:
        country = i['country']['value']
        di.append(country)

    #mares à volta da ilha
    query3 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?sea WHERE {" \
             "?X rdf:type mon:Island . " \
             "?X mon:locatedInWater ?S ." \
             "?S rdf:type mon:Sea ." \
             "?S mon:name ?sea ." \
             "FILTER ( strends(str(?X), 'islands/"+island_id+"/') )" \
                                                        "}ORDER BY ?sea"
    dic3 = executeQuery(query3)

    di2 = []
    for r in dic3['results']['bindings']:
        sea = r['sea']['value']
        di2.append(sea)

    content = {
        'island_name': name,
        'island_area': area,
        'island_lat': lat,
        'island_longe': longe,
        'island_elev': elev,
        'island_countries': di,
        'island_seas': di2,
        'archipelago': archipelago,
        'type': typ,
    }
    return render(request, 'island.html', content)

def deserts(request):
    #todos os desertos
    query = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
            "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
            "SELECT ?X ?name WHERE {" \
            "?X mon:name ?name . " \
            "?X rdf:type mon:Desert ." \
            "}ORDER BY ?name"
    dic = executeQuery(query)

    deserts = {}
    for q in dic["results"]["bindings"]:
        desert_code = q["X"]["value"].split("/")[-2]
        deserts[desert_code] = q["name"]["value"]

    content = {
        "deserts": deserts,
    }
    return render(request, 'deserts.html', content)


def desert(request, desert_id):

    #info deserto
    query1 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
            "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
            "SELECT ?name ?long ?lat ?area ?type WHERE {" \
            "?X mon:name ?name . " \
            "?X rdf:type mon:Desert . " \
            "?X mon:longitude ?long ." \
            "?X mon:latitude ?lat ." \
            "?X mon:area ?area ." \
            "OPTIONAL{?X mon:type ?type.}" \
            "FILTER ( strends(str(?X), 'deserts/"+desert_id+"/') )" \
                                                              "}"
    dic1 = executeQuery(query1)

    typ = ""
    dic1 = dic1['results']['bindings']
    for q in dic1:
        name = q['name']['value']
        area = q['area']['value']
        long = q['long']['value']
        lat = q['lat']['value']
        if 'type' in q.keys():
            typ = q['type']['value']

    #paises no deserto
    query2 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?country WHERE {" \
             "?X rdf:type mon:Desert . " \
             "?X mon:locatedIn ?C ." \
             "?C rdf:type mon:Country ." \
             "?C mon:name ?country ." \
             "FILTER ( strends(str(?X), 'deserts/"+desert_id+"/') )" \
                                                        "}"
    dic2 = executeQuery(query2)
    countries = []
    for i in dic2['results']['bindings']:
        country = i['country']['value']
        countries.append(country)

    content = {
        "name": name,
        "long": long,
        "lat": lat,
        "area": area,
        "type": typ,
        "countries": countries,
    }
    return render(request, 'desert.html', content)

def province(request, province_id):

    #info provincia
    query1 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?name ?localname ?area ?capital ?population ?hadPopul WHERE {" \
             "?X mon:name ?name . " \
             "?X rdf:type mon:Province ." \
             "OPTIONAL{?X mon:localname ?localname .}" \
             "OPTIONAL{?X mon:area ?area .}" \
             "OPTIONAL{?X mon:capital ?capital .}" \
             "OPTIONAL{?X mon:population ?population .}" \
             "OPTIONAL{?X mon:hadPopulation ?hadPopul .}" \
             "FILTER ( strends(str(?X), 'provinces/"+province_id+"/') )" \
                                                           "}"
    dic1 = executeQuery(query1)

    localname = ""
    area = ""
    population = ""
    capital = ""
    aux = []
    dic1 = dic1['results']['bindings']
    for q in dic1:
        name = q['name']['value']
        if 'localname' in q.keys():
            localname = q['localname']['value']
        if 'area' in q.keys():
            area = q['area']['value']
        if 'population' in q.keys():
            population = q['population']['value']
        if 'capital' in q.keys():
            capital = q['capital']['value'].split("/")[-2]
            if "+" in capital:
                aux = capital.split("+")
                capital = ""
                for i in aux:
                    capital += i + " "

    #cidades na provincia
    #não esquecer hrefs para paginas de cada cidade como nas páginas que tem listas
    query2 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?C ?city WHERE {" \
             "?X rdf:type mon:Province . " \
             "?X mon:hasCity ?C ." \
             "?C mon:name ?city ." \
             "FILTER ( strends(str(?X), 'provinces/"+province_id+"/') )" \
                                                        "}"
    dic2 = executeQuery(query2)

    cities = {}
    for i in dic2['results']['bindings']:
        city_id = i['C']['value'].split("/")[-2]
        cities[city_id] = i['city']['value']

    content = {
        "name": name,
        "localname": localname,
        "area": area,
        "capital": capital,
        "population": population,
        "cities": cities
    }

    return render(request, 'province.html', content)


def city(request, city_id):

    #info cidade
    query1 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?name ?other ?lat ?long ?elev ?popul ?hadPopul WHERE {" \
             "?X mon:name ?name . " \
             "OPTIONAL{?X mon:othername ?other .}" \
             "?X rdf:type mon:City ." \
             "?X mon:latitude ?lat ." \
             "?X mon:longitude ?long ." \
             "OPTIONAL{?X mon:elevation ?elev .}" \
             "OPTIONAL{?X mon:population ?popul .}" \
             "OPTIONAL{?X mon:hadPopulation ?hadPopul .}" \
             "FILTER ( strends(str(?X), 'cities/"+city_id+"/') )" \
                                                       "}"
    dic1 = executeQuery(query1)

    popul = ""
    elev = ""
    other = ""
    dic1 = dic1['results']['bindings']
    for q in dic1:
        name = q['name']['value']
        if 'other' in q.keys():
            other = q['other']['value']
        long = q['long']['value']
        lat = q['lat']['value']
        if 'elev' in q.keys():
            elev = q['elev']['value']
        if 'popul' in q.keys():
            popul = q['popul']['value']

    content = {
        "name": name,
        "other": other,
        "lat": lat,
        "long": long,
        "elev": elev,
        "popul": popul
    }

    return render(request, 'city.html', content)

def inferences (request):

    #paises grandes(população e area)
    query1 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?c ?n ?p ?a WHERE {" \
                 "?c rdf:type mon:Country ." \
                 "?c mon:name ?n . "\
                 "?c mon:population ?p ." \
                 "?c mon:area ?a ." \
                 "filter(?p > 50000000)" \
                 "filter(?a > 1000000)" \
             "}ORDER BY DESC(?p)" \

    dic1 = executeQuery(query1)

    dic1 = dic1['results']['bindings']
    ret1 = {}
    for q in dic1:
        code = q['c']['value'].split("/")[-2]
        ret1[code] = q['n']['value']

    #Countries where the majority of people speak english
    query2 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?country ?cname ?percent WHERE {" \
                 "?L rdf:type mon:SpokenBy . " \
                 "?L mon:onLanguage ?language ." \
                 "?language mon:name ?name .  " \
                 "?L mon:percent ?percent . " \
                 "?L mon:languageInfo- ?country ." \
                 "?country rdf:type mon:Country ." \
                 "?country mon:name ?cname ." \
                 "filter(?name = 'English')" \
                 "filter(?percent > 50)" \
             "}ORDER BY DESC(?percent) " \

    dic2 = executeQuery(query2)

    dic2 = dic2['results']['bindings']

    ret2 = {}
    for w in dic2:
        code = w['country']['value'].split("/")[-2]
        ret2[code] = w['cname']['value']

    #high density provinces
    query3 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?prov ?n ?cname ?density WHERE {" \
                 "?country mon:hasProvince ?prov ." \
                 "?country mon:name ?cname ." \
                 "?prov rdf:type mon:Province ." \
                 "?prov mon:name ?n ." \
                 "?prov mon:population ?p ." \
                 "?prov mon:area ?a ." \
                 "BIND(?p / ?a AS ?density)" \
                 "filter(?density > 8000)" \
             "}ORDER BY DESC(?density)" \

    dic3 = executeQuery(query3)

    dic3 = dic3['results']['bindings']

    ret3 = {}
    for e in dic3:
        code = e['prov']['value'].split("/")[-2]
        ret3[code] = e['n']['value']

    #rivers that flow north (at least 5 degrees in latitude, ordered by latitude difference between source and estuary)
    query4 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?r ?name WHERE {" \
                 "?r mon:name ?name . " \
                 "?r rdf:type mon:River ." \
                 "?s rdf:type mon:Source ." \
                 "?r mon:length ?l ." \
                 "?r mon:hasSource ?s ." \
                 "?r mon:hasEstuary ?e ." \
                 "?s mon:latitude ?source_lat ." \
                 "?e rdf:type mon:Estuary ." \
                 "?e mon:latitude ?estuary_lat ." \
                 "filter(?source_lat < ?estuary_lat)" \
                 "bind(if(?estuary_lat < 0 , -?estuary_lat, ?estuary_lat) as ?mod_estuary)" \
                 "bind(if(?source_lat < 0 , -?source_lat, ?source_lat) as ?mod_source)" \
                 "bind(?mod_estuary -?mod_source as ?diff)" \
                 "filter(?diff > 6 )" \
             "}ORDER BY DESC(?diff)" \

    dic4 = executeQuery(query4)

    dic4 = dic4['results']['bindings']

    ret4 = {}
    for r in dic4:
        code = r['r']['value'].split("/")[-2]
        ret4[code] = r['name']['value']

    #High mountains(every peak above 8000 meters)
    query5 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?M ?name ?elevation WHERE {" \
                 "?M mon:name ?name . " \
                 "?M rdf:type mon:Mountain . " \
                 "?M mon:elevation ?elevation ." \
                 "filter(?elevation > 8000)" \
             "}ORDER BY DESC(?elevation)" \

    dic5 = executeQuery(query5)

    dic5 = dic5['results']['bindings']

    ret5 = {}
    for t in dic5:
        code = t['M']['value'].split("/")[-2]
        ret5[code] = t['name']['value']

    #Good countries to live in (high gdp per capita, low unemployment, low inflation, low infant mortality) ordered by gdp per capita
    query6 = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
             "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
             "SELECT ?c ?n WHERE {" \
                 "?c mon:name ?n . " \
                 "?c rdf:type mon:Country . " \
                 "?c mon:gdpTotal ?gdp ." \
                 "?c mon:population ?p ." \
                 "?c mon:unemployment ?u ." \
                 "?c mon:inflation ?i ." \
                 "?c mon:infantMortality ?im ." \
                 "BIND(?gdp * 1000000 AS ?gdpm)" \
                 "BIND(?gdpm / ?p AS ?gdppercapita)" \
                 "FILTER(?gdppercapita > 40000)" \
                 "FILTER(?u < 8 )" \
                 "FILTER(?i < 10)" \
                 "FILTER(?im < 5 )" \
             "}ORDER BY DESC(?gdppercapita)" \

    dic6 = executeQuery(query6)

    dic6 = dic6['results']['bindings']

    ret6 = {}
    for y in dic6:
        code = y['c']['value'].split("/")[-2]
        ret6[code] = y['n']['value']

    content = {
        "ret1": ret1,
        "ret2": ret2,
        "ret3": ret3,
        "ret4": ret4,
        "ret5": ret5,
        "ret6": ret6,
    }

    return render(request, 'inferences.html', content)

def delete_population(request,country_code):
    if 'year' in request.POST:
        year = request.POST['year']
        year = int(year)

    if request.method == 'POST':
        delete = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
                 "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
                 "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" \
                 "DELETE {" \
                 "?hadPopul ?p ?v " \
                 "}" \
                 "WHERE {" \
                 "?X mon:name ?name . " \
                 "?X mon:hadPopulation ?hadPopul ." \
                 "?hadPopul mon:year ?year ." \
                 "?hadPopul mon:value ?value ." \
                 "?X rdf:type mon:Country . " \
                 "FILTER ( strends(str(?X), 'countries/"+country_code+"/') )" \
                 "FILTER (?year = '"+str(year)+"'^^xsd:gYear)" \
                 "?hadPopul ?p ?v" \
                 "}"
        executeInsert(delete)

    return HttpResponseRedirect("/countries/"+country_code)

def edit_gdp(request, country_code):

    if 'gdp' in request.POST:

        gdp = request.POST['gdp']
        try:
            gdp = int(gdp)

        except:
            pass

        if isinstance(gdp, int):
            if gdp > 0:
                update = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
                        "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
                        "DELETE{" \
                        "?X mon:gdpTotal ?gdp ." \
                        "}" \
                        "INSERT{" \
                        "?X mon:gdpTotal '"+str(gdp)+"'^^xsd:integer ." \
                                           "}" \
                                           "WHERE {" \
                                           "?X rdf:type mon:Country . " \
                                           "?X mon:gdpTotal ?gdp ." \
                                           "FILTER ( strends(str(?X), 'countries/"+country_code+"/') )" \
                                                                                    "}"

                executeInsert(update)

                return HttpResponseRedirect("/countries/"+country_code)
            else:
                content = {
                    'error': "Invalid Data!"
                }
                return render(request, 'error.html', content)
        else:
            content = {
                'error': "Invalid Data!"
            }
            return render(request, 'error.html', content)
    else:
        content = {
            'error': "Invalid Data!"
        }
        return render(request, 'error.html', content)


def edit_pop_growth(request, country_code):

    if 'pop_growth' in request.POST:

        growth = request.POST['pop_growth']
        try:
            growth = float(growth)

        except:
            pass

        if isinstance(growth, float):
            update = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
                     "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
                     "DELETE{" \
                     "?X mon:populationGrowth ?growth ." \
                     "}" \
                     "INSERT{" \
                     "?X mon:populationGrowth  '"+str(growth)+"'^^xsd:decimal ." \
                     "}" \
                     "WHERE {" \
                     "?X rdf:type mon:Country . " \
                     "?X mon:populationGrowth   ?growth ." \
                     "FILTER ( strends(str(?X), 'countries/"+country_code+"/') )" \
                     "}"

            executeInsert(update)

            return HttpResponseRedirect("/countries/"+country_code)

        else:
            content = {
                'error': "Invalid Data!"
            }
            return render(request, 'error.html', content)
    else:
        content = {
            'error': "Invalid Data!"
        }
        return render(request, 'error.html', content)


def edit_inflation(request, country_code):

    if 'inflation' in request.POST:

        inflation = request.POST['inflation']

        try:
            inflation = float(inflation)

        except:
            pass

        if isinstance(inflation, float):

            update = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
                     "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
                     "DELETE{" \
                     "?X mon:inflation  ?inf ." \
                     "}" \
                     "INSERT{" \
                     "?X mon:inflation  '"+str(inflation)+"'^^xsd:decimal ." \
                                            "}" \
                                            "WHERE {" \
                                            "?X rdf:type mon:Country . " \
                                            "?X mon:inflation  ?inf ."  \
                                            "FILTER ( strends(str(?X), 'countries/"+country_code+"/') )" \
                                                                                     "}"

            executeInsert(update)

            return HttpResponseRedirect("/countries/"+country_code)

        else:
            content = {
                'error': "Invalid Data!"
            }
            return render(request, 'error.html', content)
    else:
        content = {
            'error': "Invalid Data!"
        }
        return render(request, 'error.html', content)

    return HttpResponseRedirect("/countries/"+country_code)


def edit_unemployment(request, country_code):

    if 'unemployment' in request.POST:

        unemployment = request.POST['unemployment']
        try:
            unemployment = float(unemployment)

        except:
            pass

        if isinstance(unemployment, float):

            if 100 > unemployment > 0:
                update = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#>" \
                         "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" \
                         "DELETE{" \
                         "?X mon:unemployment ?unemployment ." \
                         "}" \
                         "INSERT{" \
                         "?X mon:unemployment '"+str(unemployment)+"'^^xsd:decimal ." \
                                                    "}" \
                                                    "WHERE {" \
                                                    "?X rdf:type mon:Country ." \
                                                    "?X mon:unemployment ?unemployment ." \
                                                    "FILTER ( strends(str(?X), 'countries/"+country_code+"/') )" \
                                                    "}"

                executeInsert(update)

                return HttpResponseRedirect("/countries/"+country_code)

            else:
                content = {
                    'error': "Invalid Data!"
                }
                return render(request, 'error.html', content)


        else:
            content = {
                'error': "Invalid Data!"
            }
            return render(request, 'error.html', content)
    else:
        content = {
            'error': "Invalid Data!"
        }
        return render(request, 'error.html', content)

    return HttpResponseRedirect("/countries/"+country_code)


def edit_government(request, country_code):

    if 'government' in request.POST:

        government = request.POST['government']
        try:
            government = str(government)

        except:
            pass

        if isinstance(government, str):

            update = "prefix mon: <http://www.semwebtech.org/mondial/10/meta#> " \
                     "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " \
                     "DELETE{ " \
                     "?X mon:government ?gov . " \
                     "} " \
                     "INSERT{ " \
                     "?X mon:government '"+government+"'^^xsd:string . " \
                                                    "} " \
                                                    "WHERE { " \
                                                    "?X rdf:type mon:Country . " \
                                                    "?X mon:government  ?gov " \
                                                    "FILTER ( strends(str(?X), 'countries/"+country_code+"/')) " \
                                                                                             "} "
            print(update)
            executeInsert(update)

            return HttpResponseRedirect("/countries/"+country_code)

        else:
            content = {
                'error': "Invalid Data!"
            }
            return render(request, 'error.html', content)
    else:
        content = {
            'error': "Invalid Data!"
        }
        return render(request, 'error.html', content)

    return HttpResponseRedirect("/countries/"+country_code)


def executeQuery(query):
    client = ApiClient(endpoint="http://localhost:7200")
    accessor = GraphDBApi(client)
    payload_query = { "query" : query }
    res = accessor.sparql_select(body=payload_query, repo_name="mondial")
    try:
        return json.loads(res)
    except Exception as e:
        print(res)
        return None


def executeInsert(data):
    client = ApiClient(endpoint="http://localhost:7200")
    accessor = GraphDBApi(client)
    payload_update = {"update": data}
    res = accessor.sparql_update(body=payload_update, repo_name="mondial")
    print(res)
    return res
