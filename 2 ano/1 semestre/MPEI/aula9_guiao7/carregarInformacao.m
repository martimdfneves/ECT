function [ Set ] = carregarInformacao(filePath)

% Carrega o ficheiro dos dados dos filmes
udata=load(filePath); 

% Fica apenas com as duas primeiras colunas
u= udata(1:end,1:2);
clear udata;

% Lista de utilizadores
users = unique(u(:,1));                           % Extrai os IDs dos utilizadores
numUsers= length(users);          % Número de utilizadores

% Constrói a lista de filmes para cada utilizador
Set = cell(numUsers,1);            % Usa céulas
for n = 1:numUsers,                 % Para cada utilizador
    % Obtém os filmes de cada um
    ind = find(u(:,1) == users(n));
    
    % E guarda num array. Usa células porque cada utilizador tem um número
    % diferente de filmes. Se fossem iguais podia ser um array
    Set{n} = [Set{n} u(ind,2)];
end

end
