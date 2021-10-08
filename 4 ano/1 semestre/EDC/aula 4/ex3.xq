<root>
  {
    for $c in collection('CursosUA')//curso 
    for $d in  $c / departamentos/departamento
     return
      <elem>
        { $d / text() }
      </elem>
  }
</root>