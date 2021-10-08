<root>
  {
    let $local := "Ciências Biomédicas"
    for $c in collection('CursosUA')//curso
    where $c // areacientifica = $local
    return ($c / nome)
  }
</root>