"""Wiki URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path

import app.views

urlpatterns = [
    path('admin/', admin.site.urls),
    path('', app.views.home, name="home"),
    path('continents/<str:continent>/', app.views.continents, name="continent"),
    path('countries/<str:country_code>/', app.views.country, name='country'),
    path('seas/', app.views.seas, name='seas'),
    path('seas/<str:sea_id>/', app.views.sea, name='sea'),
    path('mountains/', app.views.mountains, name='mountains'),
    path('mountains/<str:mountain_id>/', app.views.mountain, name='mountain'),
    path('islands/', app.views.islands, name='islands'),
    path('islands/<str:island_id>/', app.views.island, name='island'),
    path('rivers/', app.views.rivers, name='rivers'),
    path('rivers/<str:river_id>/', app.views.river, name='river'),
    path('province/<str:province_id>/', app.views.province, name='province'),
    path('deserts/', app.views.deserts, name='deserts'),
    path('deserts/<str:desert_id>/', app.views.desert, name='desert'),
    path('lakes/', app.views.lakes, name='lakes'),
    path('lakes/<str:lake_id>/', app.views.lake, name='lake'),
    path('city/<str:city_id>/', app.views.city, name='city'),
    path('province/<str:province_id>/', app.views.province, name='province'),
    path('delete_population/<slug:country_code>', app.views.delete_population, name='delete_population'),
    path('edit_gdp/<slug:country_code>', app.views.edit_gdp, name='edit_gdp'),
    path('edit_pop_growth/<slug:country_code>', app.views.edit_pop_growth, name='edit_pop_growth'),
    path('edit_inflation/<slug:country_code>', app.views.edit_inflation, name='edit_inflation'),
    path('edit_unemployment/<slug:country_code>', app.views.edit_unemployment, name='edit_unemployment'),
    path('edit_government/<slug:country_code>', app.views.edit_government, name='edit_government'),
    path('inferences/', app.views.inferences, name='inferences'),
]