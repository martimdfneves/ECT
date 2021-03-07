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
    path('continents/<slug:continent>/', app.views.continents, name="continent"),
    path('countries/<slug:country_code>/', app.views.country, name='country'),
    path('countries/<slug:country_code>/comment/', app.views.get_comment, name='get_comment'),
    path('seas/', app.views.seas, name='seas'),
    path('seas/<slug:sea_id>/', app.views.sea, name='sea'),
    path('mountains/', app.views.mountains, name='mountains'),
    path('mountains/<slug:mountain_id>/', app.views.mountain, name='mountain'),
    path('islands/', app.views.islands, name='islands'),
    path('islands/<slug:island_id>/', app.views.island, name='island'),
    path('rivers/', app.views.rivers, name='rivers'),
    path('rivers/<slug:river_id>/', app.views.river, name='river'),
    path('province/<slug:province_id>/', app.views.province, name='province'),
    path('city/<slug:city_id>/', app.views.city, name='city'),
    path('worldnews/', app.views.worldnews, name='news'),
    path('delete_country/<slug:continent>/<slug:country_code>', app.views.delete_country, name='delete_country'),
    path('add_population/<slug:country_code>', app.views.add_population, name='add_population'),
    path('edit_gdp/<slug:country_code>', app.views.edit_gdp, name='edit_gdp'),
    path('edit_pop_growth/<slug:country_code>', app.views.edit_pop_growth, name='edit_pop_growth'),
    path('edit_inflation/<slug:country_code>', app.views.edit_inflation, name='edit_inflation'),
    path('edit_unemployment/<slug:country_code>', app.views.edit_unemployment, name='edit_unemployment'),
    path('edit_government/<slug:country_code>', app.views.edit_government, name='edit_government'),
]
