<root>
  {
    for $c in collection('CursosUA')//curso 
    return
      <elem>
        { $c / guid }
        { $c / nome }
      </elem>
  }
</root>