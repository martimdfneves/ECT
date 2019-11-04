function [ Set ] = carregarInformacao(filePath)

% Carrega o ficheiro dos dados dos filmes
udata=load(filePath); 

% Fica apenas com as duas primeiras colunas
u= udata(1:end,1:2);
clear udata;

% Lista de utilizadores
users = unique(u(:,1));                           % Extrai os IDs dos utilizadores
numUsers= length(users);          % N�mero de utilizadores

% Constr�i a lista de filmes para cada utilizador
Set = cell(numUsers,1);            % Usa c�ulas
for n = 1:numUsers,                 % Para cada utilizador
    % Obt�m os filmes de cada um
    ind = find(u(:,1) == users(n));
    
    % E guarda num array. Usa c�lulas porque cada utilizador tem um n�mero
    % diferente de filmes. Se fossem iguais podia ser um array
    Set{n} = [Set{n} u(ind,2)];
end

end
