USE
Transporte;

-- a)
SELECT NOME
FROM ex52.Fornecedor
         LEFT JOIN ex52.Encomenda
                   ON Fornecedor.NIF = Encomenda.FORNECEDOR
WHERE FORNECEDOR IS NULL;

-- b)
SELECT CODIGO, NOME, AVG(STOCK) AS AVG_ITEM
FROM ex52.Produto
         JOIN ex52.Conteudo
              ON Produto.CODIGO = Conteudo.PRODUTO
GROUP BY CODIGO, NOME, Conteudo.PRODUTO

-- c)
SELECT AVG(Num) as avrg
FROM (
         SELECT ENCOMENDA, COUNT(ENCOMENDA) as Num
         FROM ex52.Produto
                  JOIN ex52.Conteudo
                       ON codigo = produto
         GROUP BY encomenda) as sub


-- d)
SELECT fornecedor.nome AS fornecedor, produto.nome AS produto, UNIDADES
FROM ex52.Fornecedor
         JOIN ex52.Encomenda ON fornecedor = nif
         JOIN ex52.Conteudo ON Encomenda = numero
         JOIN ex52.Produto ON produto = codigo