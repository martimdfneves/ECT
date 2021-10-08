<root>
  {
    let $nome := "Engenharia Computacional"
    for $c in collection('CursosUA')//curso
    where $c / nome = $nome
    return 
      <elem>
        {$c / departamentos / departamento}
      </elem>
  }
</root>