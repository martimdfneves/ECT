import decimal

from django.shortcuts import render
from django.http import HttpResponse, HttpRequest, HttpResponseRedirect
import os
import xmltodict
from BaseXClient import BaseXClient
import requests
from Wiki.settings import BASE_DIR
import lxml.etree as ET
from .forms import *
# Create your views here.

continentsdict = {
    'europe': 'Europe',
    'asia': 'Asia',
    'australia': 'Australia',
    'northamerica': 'North America',
    'southamerica': 'South America',
    'africa': 'Africa'
}

def home(request):
    return render(request, 'index.html')

def continents(request, continent):
    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
            "funcs:getCountriesContinent('" + continent + "')"

    query = session.query(input).execute()

    dict = xmltodict.parse(query).get('countries').get('country')

    countries = {}

    for i in dict:

        c = i.get('code')
        countries[countryname(c)] = c

    content = {
        'continent_id': continent,
        'continent': continentsdict.get(continent),
        'countries': orderDict(countries, 0),
    }
    return render(request, 'continent.html', content)


def country(request, country_code):
    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
            "funcs:getCountry('" + country_code + "')"

    query = session.query(input).execute()

    tree = ET.fromstring(bytes(query, 'UTF-8'))
    xsd_file = os.path.join(BASE_DIR, "app/xml/xsd/country.xsd")
    xsd_parsed = ET.parse(xsd_file)
    xsd = ET.XMLSchema(xsd_parsed)


    if xsd.validate(tree):
        dict = xmltodict.parse(query).get('country')

        area = dict.get('@area')
        capital = dict.get('@capital')
        capital = cityname(capital)
        name = dict.get('name')
        localname = dict.get('localname')

        pop = dict.get('population')
        popul = {}
        for i in pop:
            year = i.get('@year')
            num = i.get('#text')
            popul[year] = num

        growth = dict.get('population_growth')
        pib = dict.get('gdp_total')
        inf = dict.get('inflation')
        desemprego = dict.get('unemployment')

        dep = ""
        if dict.get('dependent') is not None:
            dependent = dict.get('dependent')
            c = dependent.get('@country')
            dep = countryname(c)

        indep = ""
        if dict.get('indep_date') is not None:
            indep = dict.get('indep_date')

        gov = dict.get('government')

        encomp = dict.get('encompassed')
        contido = {}
        contido2 = {}
        lst_enc = []
        if isinstance(encomp, list):
            for j in encomp:
                if isinstance(j, str):
                    lst_enc.append(j)
                else:
                    cont = j.get('@continent')
                    perc = j.get('@percentage')
                    contido[cont] = float(perc)
        else:
            cont = encomp.get('@continent')
            perc = encomp.get('@percentage')
            contido[cont] = float(perc)
        if bool(contido):
            for ab, ac in contido.items():
                contido2[continentsdict[ab]] = ac

        ethnic = {}
        lst_eth = []
        if dict.get('ethnicgroup') is not None:
            eth = dict.get('ethnicgroup')
            ethnic = {}
            if isinstance(eth, list):
                for k in eth:
                    if isinstance(k, str):
                        lst_eth.append(k)
                    else:
                        per = k.get('@percentage')
                        ethnicity = k.get('#text')
                        ethnic[ethnicity] = float(per)
            else:
                per = eth.get('@percentage')
                ethnicity = eth.get('#text')
                ethnic[ethnicity] = float(per)
        religion = {}
        rel ={}
        diff = 0
        lst_rel = []
        try:
            religion = dict.get('religion')
            rel = {}
            lst_rel = []
            if isinstance(religion, list):
                for l in religion:
                    if isinstance(l, str):
                        lst_rel.append(l)
                    else:
                        p = l.get('@percentage')
                        r = l.get('#text')
                        rel[r] = float(p)
            else:
                p = religion.get('@percentage')
                r = religion.get('#text')
                rel[r] = float(p)
            soma = 0.0
            for e in rel.values():
                soma += float(e)
            diff = 100 - soma
            if bool(rel):
                rel['Atheist'] = diff
            else:
                lst_rel.append('Atheist')

        except:
            pass

        language = dict.get('language')
        lang = {}
        lst_lang = []
        if isinstance(language, list):
            for m in language:
                if isinstance(m, str):
                    lst_lang.append(m)
                else:
                    pe = m.get('@percentage')
                    lan = m.get('#text')
                    lang[lan] = float(pe)
        else:
            pe = language.get('@percentage')
            lan = language.get('#text')
            lang[lan] = float(pe)

        bor = {}
        bor2 = {}
        if dict.get('border') is not None:
            border = dict.get('border')
            if isinstance(border, list):
                for n in border:
                    country = n.get('@country')
                    length = n.get('@length')
                    bor[country] = float(length)
            else:
                country = border.get('@country')
                length = border.get('@length')
                bor[country] = float(length)
        if bool(bor)==True:
            for ba, bc in bor.items():
                ba = countryname(ba)
                bor2[ba] = bc

        provincias = {}
        if dict.get('province') is not None:
            prov = dict.get('province')
            if isinstance(prov, list):
                for o in prov:
                    ident = o.get('@id')
                    provincias[ident] = provincename(ident)
            else:
                ident = prov.get('@id')
                provincias[ident] = provincename(ident)

        cidades = {}
        if dict.get('city') is not None:
            cidade = dict.get('city')
            if isinstance(cidade, list):
                for p in cidade:
                    ident = p.get('@id')
                    cidades[ident] = cityname(ident)
            else:
                ident = cidade.get('@id')
                cidades[ident] = cityname(ident)

        comentarios = []
        if dict.get('comment') is not None:
            comentario = dict.get('comment')
            if isinstance(comentario, list):
                for p in comentario:
                    comentarios.append(p)
            else:
                comentarios.append(comentario)

        name = countryname(country_code)

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
            'encompassed': orderDict(contido2, 1),
            'ethnicgroup': orderDict(ethnic, 1),
            'religion': orderDict(rel, 1),
            'language': orderDict(lang, 1),
            'border': orderDict(bor2, 1),
            'province': orderDict(provincias, 2),
            'city': orderDict(cidades, 2),
            'diff': diff,
            'lst_eth': sorted(lst_eth),
            'lst_enc': sorted(lst_enc),
            'lst_rel': sorted(lst_rel),
            'lst_lang': sorted(lst_lang),
            'comments': comentarios,
        }
        return render(request, 'country.html', content)

    content = {
        "country_code": country_code
    }
    return render(request, 'validatefail.html',content)

def seas(request):

    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
            "funcs:getSeas()"

    query = session.query(input).execute()
    dict = xmltodict.parse(query).get('seas').get('sea')


    seas = {}
    for i in dict:
        id = i.get('id')
        seas[seaname(id)] = id

    content = {
        'seas': orderDict(seas, 0),
    }
    return render(request, 'seas.html', content)


def sea(request, sea_id):

    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
            "funcs:getSea('"+sea_id+"')"

    xml_result = session.query(input).execute()
    xml_result = "<?xml version=\"1.0\"?>" + "\n\r" + xml_result
    xslt_name = "sea.xsl"
    xsl_file = os.path.join(BASE_DIR, 'app/xml/xslt/' + xslt_name)

    tree = ET.fromstring(bytes(xml_result, 'UTF-8'))

    xslt = ET.parse(xsl_file)
    transform = ET.XSLT(xslt)
    newdoc = transform(tree)

    content = {
        'content': newdoc,
    }
    return render(request, 'default.html', content)


def rivers(request):
    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
            "funcs:getRivers()"

    query = session.query(input).execute()
    dict = xmltodict.parse(query).get('rivers').get('river')

    rivers = {}

    for i in dict:

        id = i.get('id')

        rivers[rivername(id)] = id
    content = {
        'rivers': orderDict(rivers, 0),
    }
    return render(request, 'rivers.html', content)


def river(request, river_id):
    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR,'app/queries.xq')+"'; " \
            "funcs:getRiver('"+river_id+"')"

    xml_result = session.query(input).execute()
    xml_result = "<?xml version=\"1.0\"?>"+"\n\r"+xml_result
    xslt_name = "river.xsl"
    xsl_file = os.path.join(BASE_DIR, 'app/xml/xslt/'+xslt_name)

    tree = ET.fromstring(bytes(xml_result, 'UTF-8'))
    xslt = ET.parse(xsl_file)
    transform = ET.XSLT(xslt)
    newdoc = transform(tree)

    content = {
        'content': newdoc,
    }
    return render(request, 'default.html', content)


def mountains(request):
    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq')+"'; " \
            "funcs:getMountains()"

    query = session.query(input).execute()
    dict = xmltodict.parse(query).get('mountains').get('mountain')

    mountains = {}

    for i in dict:
        id = i.get('id')

        mountains[mountainname(id)] = id

    content = {
        'mountains': orderDict(mountains, 0),
    }
    return render(request, 'mountains.html', content)


def mountain(request, mountain_id):
    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq')+"'; " \
            "funcs:getMountain('"+mountain_id+"')"

    query = session.query(input).execute()
    name = xmltodict.parse(query).get('mountain').get('name')
    range = xmltodict.parse(query).get('mountain').get('mountains')
    lat = xmltodict.parse(query).get('mountain').get('latitude')
    longe = xmltodict.parse(query).get('mountain').get('longitude')
    elev = xmltodict.parse(query).get('mountain').get('elevation')

    mount_countries = {}
    mount_island = {}
    country = xmltodict.parse(query).get('mountain').get('@country')
    island = xmltodict.parse(query).get('mountain').get('@island')

    if island is not None:
        i = getisland(island)
        n = i.get('name')
        group = i.get('islands')
        if group is not None:
            n += " - "+group
        mount_island[island] = n

    if country is not None:
        c = country.split(' ')
        if len(c) > 1:
            for i in c:
                mount_countries[i] = countryname(i)
        else:
            mount_countries[c[0]] = countryname(c[0])

    mount_provinces = {}
    located = xmltodict.parse(query).get('mountain').get('located')

    if located is not None:
        if isinstance(located, list):
            for l in located:
                code = l.get('@country')
                province = l.get('@province')
                p = province.split(' ')
                if len(p) > 1:
                    for i in p:
                        mount_provinces[i] = provincename(i)
                else:
                    mount_provinces[province] = provincename(province)
        else:
            code = located.get('@country')
            province = located.get('@province')
            p = province.split(' ')
            if len(p) > 1:
                for i in p:
                    mount_provinces[i] = provincename(i)
            else:
                mount_provinces[province] = provincename(province)

    content = {
        'mount_name': name,
        'mount_lat': lat,
        'mount_longe': longe,
        'mount_elev': elev,
        'mount_range': range,
        'mount_countries': orderDict(mount_countries, 1),
        'mount_island' : orderDict(mount_island, 1),
        'mount_provinces' : orderDict(mount_provinces, 1),
    }
    return render(request, 'mountain.html', content)


def islands(request):
    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq')+"'; " \
            "funcs:getIslands()"

    query = session.query(input).execute()
    dict = xmltodict.parse(query).get('islands').get('island')

    islands = {}
    for i in dict:
        id = i.get('id')

        islands[islandname(id)] = id

    content = {
        'islands': orderDict(islands, 0),
    }
    return render(request, 'islands.html', content)


def island(request, island_id):
    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq')+"'; " \
            "funcs:getIsland('"+island_id+"')"

    query = session.query(input).execute()
    country = xmltodict.parse(query).get('island').get('@country')
    sea = xmltodict.parse(query).get('island').get('@sea')
    islands = xmltodict.parse(query).get('island').get('islands')
    name = xmltodict.parse(query).get('island').get('name')
    area = xmltodict.parse(query).get('island').get('area')
    lat = xmltodict.parse(query).get('island').get('latitude')
    longe = xmltodict.parse(query).get('island').get('longitude')
    elev = xmltodict.parse(query).get('island').get('elevation')

    c = country.split(' ')
    di = {}
    for x in c:
        di[x] = countryname(x)

    s = sea.split(' ')
    di2 = {}
    for x in s:
        di2[x] = seaname(x)

    content = {
        'island_name': name,
        'island_area': area,
        'island_lat': lat,
        'island_longe': longe,
        'island_elev': elev,
        'island_countries': orderDict(di, 1),
        'island_seas': orderDict(di2, 1),
        'island_islands': islands,
    }
    return render(request, 'island.html', content)


def province(request, province_id):
    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR,'app/queries.xq')+"'; " \
            "funcs:getProvince('"+province_id+"')"

    xml_result = session.query(input).execute()
    xml_result = "<?xml version=\"1.0\"?>" + "\n\r" + xml_result
    xslt_name = "province.xsl"
    xsl_file = os.path.join(BASE_DIR, 'app/xml/xslt/' + xslt_name)
    tree = ET.fromstring(bytes(xml_result, 'UTF-8'))
    xslt = ET.parse(xsl_file)
    transform = ET.XSLT(xslt)
    newdoc = transform(tree)

    content = {
        'content': newdoc
    }

    return render(request, 'default.html', content)


def city(request, city_id):
    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR,'app/queries.xq')+"'; " \
            "funcs:getCity('"+city_id+"')"

    xml_result = session.query(input).execute()
    xml_result = "<?xml version=\"1.0\"?>" + "\n\r" + xml_result
    xslt_name = "city.xsl"
    xsl_file = os.path.join(BASE_DIR, 'app/xml/xslt/' + xslt_name)

    tree = ET.fromstring(bytes(xml_result, 'UTF-8'))
    xslt = ET.parse(xsl_file)
    transform = ET.XSLT(xslt)
    newdoc = transform(tree)

    content = {
        'content': newdoc
    }

    return render(request, 'default.html', content)


def worldnews(request):
    xml_file = requests.get(
        "https://www.nytimes.com/svc/collections/v1/publish/https://www.nytimes.com/section/world/rss.xml")
    xslt_name = "rss.xsl"
    xsl_file = os.path.join(BASE_DIR, 'app/xml/xslt/' + xslt_name)

    tree = ET.fromstring(xml_file.content)
    xslt = ET.parse(xsl_file)
    transform = ET.XSLT(xslt)
    newdoc = transform(tree)

    content = {
        'content': newdoc
    }

    return render(request, 'default.html', content)


def countryname(car_code):

    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            "" + str(os.path.join(BASE_DIR, 'app/queries.xq')) + "'; " \
            "funcs:getCountryName('" + car_code + "')"

    query = session.query(input).execute()
    return xmltodict.parse(query).get('name')


def getisland(id):

    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "let $x := collection('mondial')//island[@id='" + id + "'] " \
            "return $x"

    query = session.query(input).execute()
    return xmltodict.parse(query).get('island')


def provincename(id):

    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
            "funcs:getProvinceName('" + id + "')"

    query = session.query(input).execute()
    return xmltodict.parse(query).get('name')


def rivername(id):
    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
            "funcs:getRiverName('" + id + "')"

    query = session.query(input).execute()
    return xmltodict.parse(query).get('name')


def mountainname(id):
    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
            "funcs:getMountainName('" + id + "')"

    query = session.query(input).execute()
    return xmltodict.parse(query).get('name')

def islandname(id):
    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
            "funcs:getIslandName('" + id + "')"

    query = session.query(input).execute()
    return xmltodict.parse(query).get('name')

def seaname(id):
    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
            "funcs:getSeaName('" + id + "')"

    query = session.query(input).execute()
    return xmltodict.parse(query).get('name')


def cityname(id):

    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    input = "import module namespace funcs = 'mondial.funcs' at '" \
            + os.path.join(BASE_DIR, 'app/queries.xq')+"'; " \
            "funcs:getCityName('"+id+"')"

    query = session.query(input).execute()
    return xmltodict.parse(query).get('name')

def get_comment(request, country_code):
    form = CommentForm()

    session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
    session.execute("open mondial")

    if request.method == 'POST':
        form = CommentForm(request.POST)
        if form.is_valid():
            print(form.cleaned_data)
            comment = form.cleaned_data['your_comment']

            session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
            session.execute("open mondial")

            # Create new node
            node = '<comment> ' + str(comment) + '</comment>'
            # Insert new course
            session.execute("xquery let $x := collection('mondial')//country[@car_code='" + country_code + "'] " \
                            "return insert node " + node + " as last into $x")

    return HttpResponseRedirect('/countries/'+country_code+'/')


def delete_country(request, continent, country_code):

    if request.method == 'POST':

        session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
        session.execute("open mondial")

        input = "import module namespace funcs = 'mondial.funcs' at '" \
                + os.path.join(BASE_DIR, 'app/queries.xq')+"'; " \
                "funcs:deleteCountry('" + country_code + "')"
        session.query(input).execute()

    return HttpResponseRedirect("/continents/"+continent)


def add_population(request, country_code):
    if 'year' in request.POST and 'population' in request.POST:

        year = request.POST['year']
        population = request.POST['population']
        try:
            year = int(year)
            population = int(population)
        except:
            pass

        if isinstance(year, int) and isinstance(population, int):
            if year > 0 and population > 0:
                print(year)
                print(population)
                session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
                session.execute("open mondial")

                input = "import module namespace funcs = 'mondial.funcs' at '" \
                        + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
                        "funcs:addPopulation('" + country_code + "', " + str(year) + ", " + str(population) + ")"

                query = session.query(input).execute()

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


def edit_gdp(request, country_code):

    if 'gdp' in request.POST:

        gdp = request.POST['gdp']
        try:
            gdp = int(gdp)

        except:
            pass

        if isinstance(gdp, int):
            if gdp > 0:
                session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
                session.execute("open mondial")

                input = "import module namespace funcs = 'mondial.funcs' at '" \
                        + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
                        "funcs:editGDP('" + country_code + "', " + str(gdp) + ")"

                session.query(input).execute()

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

            session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
            session.execute("open mondial")

            input = "import module namespace funcs = 'mondial.funcs' at '" \
                        + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
                        "funcs:editGrowth('" + country_code + "', " + str(growth) + ")"

            session.query(input).execute()

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

            session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
            session.execute("open mondial")

            input = "import module namespace funcs = 'mondial.funcs' at '" \
                        + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
                        "funcs:editGrowth('" + country_code + "', " + str(inflation) + ")"

            session.query(input).execute()

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
                session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
                session.execute("open mondial")

                input = "import module namespace funcs = 'mondial.funcs' at '" \
                        + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
                         "funcs:editUnemployment('" + country_code + "', " + str(unemployment) + ")"

                session.query(input).execute()

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

            session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')
            session.execute("open mondial")

            input = "import module namespace funcs = 'mondial.funcs' at '" \
                    + os.path.join(BASE_DIR, 'app/queries.xq') + "'; " \
                     "funcs:editGovernment('" + country_code + "', '" + str(government) + "')"

            session.query(input).execute()

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


def orderDict(dic, p):
    if p == 0:
        sorted_lst = sorted(dic.items(), key=lambda x: x[0])
        sorted_dict = dict(sorted_lst)
    elif p==1:
        sorted_lst = sorted(dic.items(), reverse=True, key=lambda x: x[1])
        sorted_dict = dict(sorted_lst)
    else:
        sorted_lst = sorted(dic.items(), key=lambda x: x[1])
        sorted_dict = dict(sorted_lst)
    return sorted_dict