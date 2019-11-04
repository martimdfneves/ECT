N = 1e6;
doente = rand(1,N) < 1/10000;
aux = rand(1,N);
darCancro = ((aux < 0.9) & doente) + ((aux < 0.1) & (1-doente));
simProb = sum(darCancro & doente)/sum(darCancro)

%% Calcule nova estimativa, mas agora considerando as mulheres que procuram
% a consulta especifica e que para este subgrupo a ocorrencia de cancro
% atinge 1 em 1000.

% T - ter cancro
% N - nao ter cancro
% P - mamografia positiva
% p(T|P) = p(P|T) / ( p(P|T)p(T) + p(P|N)p(N) )

pT = 0.001;
pN = 1 - pT;
pP = 0.9*pT + 0.1*pN;

probCancro = (0.9*pT)/pP