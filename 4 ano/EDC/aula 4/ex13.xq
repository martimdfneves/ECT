<root>
  {
     let $c := collection('cursosUA')//curso
     for $d in distinct-values($c//areacientifica)
     order by $d 
     return 
       <elem>
          { $d }
       </elem>
   }
</root>