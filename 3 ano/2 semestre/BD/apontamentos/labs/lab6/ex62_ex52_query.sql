-- Ex 52

-- a)
SELECT nome
FROM fornecedor 
LEFT JOIN encomenda
ON fornecedor=nif
WHERE fornecedor IS NULL;

-- b)
SELECT codigo, nome, AVG(item.unidades) AS avg_item
FROM produto 
JOIN item
ON codigo=codProd
GROUP BY codigo, nome, codProd

-- c)
SELECT AVG(Num) as avrg
FROM (
SELECT numEnc, COUNT(numEnc) as Num
FROM produto JOIN item
ON  codigo=codProd
GROUP BY numEnc) as sub


-- d)
SELECT fornecedor.nome AS fornecedor, produto.nome AS produto, item.unidades
FROM fornecedor 
JOIN encomenda ON fornecedor=nif
JOIN item ON numEnc=numero
JOIN produto ON codProd=codigo
	
  
