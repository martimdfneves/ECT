function [ wordsficheiro ] = readficheiro(nomeFicheiro)
fprintf('Lendo ficheiro %s...\n', nomeFicheiro);
ficheiro = fopen(nomeFicheiro);
conteudo_ficheiro = fscanf(ficheiro, '%c', inf);
wordsficheiro = strsplit(conteudo_ficheiro);

end
