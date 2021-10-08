<root>
  {
     let $c := collection('cursosUA')//curso
     for $d in distinct-values($c//departamento)
     order by $d 
     return 
       <elem>
          { $d }
       </elem>
   }
</root>