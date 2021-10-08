module namespace funcs = "mondial.funcs";

declare function funcs:getRiverProvinces($river_id) as element()*
{
  let $x := collection('mondial')//river[@id=$river_id]/child::located

for $l in $x
  let $provinces := $l/@province/string()
  return 
    
  for $p in tokenize($provinces,' ')
    let $c := collection('mondial')//country[@car_code=$l/@country/string()]
    let $n := $c/province[@id=$p]/name/text()
    return <province>{$n}({$c/name/text()})</province>
};

declare function funcs:getCountryName($code) as element(){
  let $c := collection('mondial')//country[@car_code=$code]/name
  return <name>{fn:string-join($c,'/')}</name>
};
declare function funcs:getMountainName($id) as element(){
  let $c := collection('mondial')//mountain[@id=$id]/name
  return <name>{fn:string-join($c,'/')}</name>
};
declare function funcs:getIslandName($id) as element(){
  let $c := collection('mondial')//island[@id=$id]/name
  return <name>{fn:string-join($c,'/')}</name>
};
declare function funcs:getRiverName($id) as element(){
  let $c := collection('mondial')//river[@id=$id]/name
  return <name>{fn:string-join($c,'/')}</name>
};

declare function funcs:getCityName($id) as element(){
  let $c := collection('mondial')//city[@id=$id]/name
  return <name>{fn:string-join($c,'/')}</name>
};

declare function funcs:getProvinceName($id) as element(){
  let $c := collection('mondial')//province[@id=$id]/name
  return <name>{fn:string-join($c,'/')}</name>
};

declare function funcs:getSeaName($id) as element(){
  let $c := collection('mondial')//sea[@id=$id]/name
  return <name>{fn:string-join($c,'/')}</name>
};

declare function funcs:getCountriesContinent($continent) as element(){
  <countries>{ 
    for $x in collection('mondial')//country 
      where $x/encompassed[@continent=$continent]
      let $code := $x/@car_code/string()
      return <country>{$x/name}<code>{$code}</code></country>}
  </countries>
};

declare function funcs:getCountry($country_code) as element(){
  let $x := collection('mondial')//country[@car_code=$country_code] 
return $x
};

declare function funcs:getSeas() as element(){
<seas>{ 
  for $x in collection('mondial')//sea
    return <sea>{$x/name}<id>{$x/@id/string()}</id></sea>}
</seas>
};

declare function funcs:getSea($id) as element(){
let $x := collection('mondial')//sea[@id = $id]
let $countries := $x//located/@country/string() 
return 
  <sea>
    {$x/name}
    {$x/area}
    {$x/depth} 
    { for $c in $countries 
        let $country := collection('mondial')//country[@car_code=$c]/name/text() 
        return <country>{$country}</country> } 
  </sea>
};

declare function funcs:getRivers() as element(){
<rivers>{ 
  for $x in collection('mondial')//river 
    return <river>{$x/name}<id>{$x/@id/string()}</id></river>}
</rivers>
};

declare function funcs:getRiver($river_id) as element(){
let $x := collection('mondial')//river[@id=$river_id] 
let $to := collection('mondial')/id($x/to/@water/string())
let $sourcecountry := collection('mondial')//country[@car_code=$x/source/@country]/name 
let $sourceprovince := collection('mondial')//province[@id=$x/source/located/@province]/name 
let $sourcecountry := collection('mondial')//country[@car_code=$x/source/@country]/name 
let $sourceprovince := collection('mondial')//province[@id=$x/source/located/@province]/name 
let $estuarycountry := collection('mondial')//country[@car_code=$x/estuary/@country]/name 
let $estuaryprovince := collection('mondial')//province[@id=$x/estuary/located/@province]/name 

return <river>{$x/name}<to>{$to//name/text()}({$x/to/@watertype/string()})</to>{$x/area}{$x/length}<provinces>{funcs:getRiverProvinces($river_id)}</provinces><source><located>{$sourceprovince/text()} - {$sourcecountry/text()}</located>{$x/source/latitude}{$x/source/longitude}{$x/source/elevation}</source><estuary><located>{$estuaryprovince/text()} - {$estuarycountry/text()}</located>{$x/estuary/latitude}{$x/estuary/longitude}{$x/estuary/elevation}</estuary></river>
};

declare function funcs:getMountains() as element(){
<mountains>{ 
  for $x in collection('mondial')//mountain 
    return <mountain>{$x/name}<id>{$x/@id/string()}</id></mountain>}
</mountains>
};

declare function funcs:getMountain($mountain_id) as element(){
  let $x := collection('mondial')//mountain[@id= $mountain_id]
  return $x
};

declare function funcs:getIslands() as element(){
  <islands>{ 
  for $x in collection('mondial')//island
    return <island>{$x/name}<id>{$x/@id/string()}</id></island>}
</islands>
};

declare function funcs:getIsland($island_id) as element(){
  let $x := collection('mondial')//island[@id=$island_id]
return $x
};

declare function funcs:getProvince($province_id) as element(){
  collection('mondial')//province[@id=$province_id]
};

declare function funcs:getCity($city_id) as element(){
  collection('mondial')//city[@id=$city_id]
};

(: TESTES
declare function funcs:getCoiso($country_code) as element(){
    let $x := collection('mondial')//country[@car_code=$country_code]
    return $x
};

(: insere um elemento <comment> num país :)
declare updating function funcs:setComment($element, $comment){
    insert node element {'comments'} {$comment} into $element
};:)

declare updating function funcs:deleteCountry($country_code){
  let $country := collection('mondial')//country[@car_code=$country_code]
  
  return delete node $country
};

declare updating function funcs:addPopulation($country_code,$year,$population){
  let $country := collection('mondial')//country[@car_code=$country_code] 
  return insert node <population year="{$year}">{$population}</population> before $country/population_growth
};

declare updating function funcs:editGDP($country_code,$gdp){
  let $text := collection('mondial')//country[@car_code=$country_code]/gdp_total/text() 
  return replace node $text with $gdp
};
declare updating function funcs:editGrowth($country_code,$growth){
  let $text := collection('mondial')//country[@car_code=$country_code]/population_growth/text() 
  return replace node $text with $growth
};

declare updating function funcs:editUnemployment($country_code,$unemployment){
  let $text := collection('mondial')//country[@car_code=$country_code]/unemployment/text() 
  return replace node $text with $unemployment
};

declare updating function funcs:editGovernment($country_code,$government){
  let $text := collection('mondial')//country[@car_code=$country_code]/government/text() 
  return replace node $text with $government
};


