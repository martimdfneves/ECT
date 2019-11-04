function fmp=pmfLetrasPT(ficheiros,alpha)

contador=zeros(1,length(alpha));

debug=0;   

totalLetras=0;  

for fich=1:length(ficheiros)

    fid=fopen(ficheiros{fich});

    while 1

        linha=fgetl(fid);
    
        if ~ischar(linha), break, end
    
        if debug 
            fprintf(1,'lido = |%s|  length= %d \n',linha,length(linha));    
        end
        
        if length(linha)>0
            linha=[linha sprintf('\n')];
        end
    
        if length(linha)==1
            fprintf (1, 'length =1  !!! \n');
        end
    
        
        totalLetras=totalLetras+ length(linha);

        for k=1:length(alpha)
            resul=find(linha==alpha(k));

            contador(k)=contador(k)+length(resul);
        end
   
        if debug
            fprintf(1,'letras processadas = %d \n',totalLetras);
        end
  
    end

    fclose(fid);  

end  
  
fmp=contador / (sum(contador)) ;