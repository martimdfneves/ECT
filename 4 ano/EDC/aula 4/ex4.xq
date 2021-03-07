<root>
  {
    for $c in collection('CursosUA')//curso 
    for $d in  $c / areascientificas / areacientifica
     return
      <elem>
        { $d / text() }
      </elem>
  }
</root>