<root>
{
  let $guid := "15"
  for $c in collection('CursosUA')//curso
  where $c / guid = $guid
  return
    <elem>
       { $c / nome }
       { $c / codigo }
       { $c / grau }
       { $c / local }
    </elem>
}
</root>