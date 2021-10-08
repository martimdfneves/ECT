<root>
  {
    let $nome := "Biologia e Geologia"
    for $c in collection('CursosUA')//curso
    where $c / nome = $nome
    return 
      <elem>
        {$c / areascientificas / areacientifica}
      </elem>
  }
</root>