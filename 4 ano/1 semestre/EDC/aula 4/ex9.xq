<root>
  {
    let $local := "Departamento de Eletrónica, Telecomunicações e Informática"
    for $c in collection('CursosUA')//curso
    where $c // departamento = $local
    return
      <elem>
        {$c / nome}
      </elem>
  }
</root>