<root>
  {
     for $c in collection('CursosUA')//curso 
     return ( $c / guid, $c / home, $c / local)
   }
</root>