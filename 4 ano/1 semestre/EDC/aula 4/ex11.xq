<root>
  {
     let $c := collection('cursosUA')//curso
     for $d in distinct-values($c/local)
     order by $d 
     return 
       <elem>
          { $d }
       </elem>
   }
</root>