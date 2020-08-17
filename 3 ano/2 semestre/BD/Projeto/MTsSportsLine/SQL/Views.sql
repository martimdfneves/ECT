--Deleting Zone
DROP VIEW Warehouse;
DROP VIEW StoreProducts;
DROP VIEW WarehousesProducts;
DROP VIEW Workers;
DROP VIEW FilterWorkers;
DROP VIEW FilterProducts;
DROP VIEW FilterWarehouseProducts;

--Creating views
GO
CREATE VIEW Warehouse AS
	SELECT IDArmazem AS Number, capacidade AS Storage, Loja.NumLoja AS Store
    FROM(Projeto.Loja JOIN Projeto.Armazem On Loja.NumLoja=Armazem.NumLoja);
GO
-----------------------------------------------------------------
GO
CREATE VIEW StoreProducts AS
	SELECT Artigo.Nome AS Name, Preco AS Price, QuantArtigos AS Units, Loja.NumLoja AS Store
    FROM ((Projeto.Loja JOIN Projeto.Artigo_Loja ON Loja.NumLoja=Artigo_Loja.NumLoja)
						JOIN Projeto.Artigo ON Artigo_Loja.Codigo=Artigo.Codigo)
GO
-----------------------------------------------------------------
GO
CREATE VIEW WarehousesProducts AS
	SELECT  Artigo.Nome AS Name, Preco AS Price, QuantArtigos AS Units, Armazem.IDArmazem AS WarehouseID
    FROM ((Projeto.Armazem JOIN Projeto.Artigo_Armazem ON Armazem.IDArmazem=Artigo_Armazem.IDArmazem) 
						   JOIN Projeto.Artigo ON Artigo_Armazem.Codigo=Artigo.Codigo)
GO
-----------------------------------------------------------------
GO
CREATE VIEW Workers AS
	SELECT Funcionario.NumFunc AS Num, Funcionario.Nome AS Name, Morada AS Address, Loja.NumLoja AS NumLoja
    FROM (Projeto.Loja JOIN Projeto.Funcionario ON Loja.NumLoja=Funcionario.NumLoja)
GO
-----------------------------------------------------------------
GO
CREATE VIEW FilterWorkers AS
	SELECT Funcionario.NumFunc AS Num, Funcionario.Nome AS Name, Morada AS Address, Loja.NumLoja AS NumLoja
    FROM  (Projeto.Loja JOIN Projeto.Funcionario ON Loja.NumLoja=Funcionario.NumLoja)
GO
-----------------------------------------------------------------
GO
CREATE VIEW FilterProducts AS
	SELECT Artigo.Nome AS Name, Preco AS Price, QuantArtigos AS Units, Loja.NumLoja AS NumLoja
    FROM ((Projeto.Loja JOIN Projeto.Artigo_Loja ON Loja.NumLoja=Artigo_Loja.NumLoja)
                        JOIN Projeto.Artigo ON Artigo_Loja.Codigo=Artigo.Codigo)
GO
-----------------------------------------------------------------
GO
CREATE VIEW FilterWarehouseProducts AS
	SELECT Artigo.Nome AS Name, Preco AS Price, QuantArtigos AS Units, Armazem.IDArmazem AS Armazem
    FROM ((Projeto.Armazem JOIN Projeto.Artigo_Armazem ON Armazem.IDArmazem=Artigo_Armazem.IDArmazem) 
                           Join Projeto.Artigo ON Artigo_Armazem.Codigo=Artigo.Codigo)
GO