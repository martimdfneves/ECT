<root>
  {
    let $local := "Campus Universitário de Santiago, Aveiro"
    for $c in collection('CursosUA')//curso
    where $c / local = $local
    return
      <elem>
        {$c / nome}
      </elem>
  }
</root>