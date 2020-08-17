--Deleting Zone
DROP FUNCTION Projeto.PurchasedProductsPerClient;
DROP FUNCTION Projeto.ReturnedProductsPerClient;
DROP FUNCTION Projeto.SoldProductsPerEmployee;
DROP FUNCTION Projeto.ReturnedProductsPerEmployee;
DROP FUNCTION Projeto.CheckNewProdut;

--UDF'S
GO
CREATE FUNCTION Projeto.PurchasedProductsPerClient(@NIF BIGINT) RETURNS @PurchasedProducts TABLE
												  (Number INT, Product_Name VARCHAR(40), NºUnits INT, Date DATE, Purchase_Price DECIMAL(5,2))
AS
BEGIN
	INSERT @PurchasedProducts SELECT Compra.NumCompra AS Number, Artigo.Nome AS Product_Name, 
							  Artigo_Comprado.QuantArtigos AS NºUnits, Compra.Data AS Date, Compra.Montante AS Purchase_Price  
							  FROM (((Projeto.Artigo_Comprado JOIN Projeto.Compra ON Artigo_Comprado.NumCompra=Compra.NumCompra) 
							  JOIN Projeto.Cliente ON Compra.NIF=Cliente.NIF) JOIN Projeto.Artigo ON 
							  Artigo_Comprado.Codigo=Artigo.Codigo)
							  WHERE Cliente.NIF = @NIF
	RETURN;
END
GO
--Test Function
SELECT * FROM Projeto.PurchasedProductsPerClient(123456789);
--------------------------------------------------------------------------------------------
GO
CREATE FUNCTION Projeto.ReturnedProductsPerClient(@NIF BIGINT) RETURNS @ReturnedProducts TABLE
												  (Number INT, Product_Name VARCHAR(40), NºUnits INT, Date DATE, Returned_Value DECIMAL(5,2))
AS
BEGIN
	INSERT @ReturnedProducts SELECT Devolucao.IDDevolucao AS Number, Artigo.Nome AS Product_Name, 
						     Artigo_Devolvido.QuantArtigos AS NºUnits, Devolucao.Data AS Date, Devolucao.Montante AS Returned_Value
							 FROM (((Projeto.Artigo_Devolvido JOIN Projeto.Devolucao ON 
							 Artigo_Devolvido.IDDevolucao=Devolucao.IDDevolucao) JOIN Projeto.Artigo ON 
							 Artigo.Codigo=Artigo_Devolvido.Codigo) JOIN Projeto.Cliente ON Cliente.NIF=Devolucao.NIF)
							 WHERE Cliente.NIF = @NIF
	RETURN;
END
GO
--Test Function
SELECT * FROM Projeto.ReturnedProductsPerClient(123456789);
--------------------------------------------------------------------------------------------
GO
CREATE FUNCTION Projeto.SoldProductsPerEmployee(@Num INT) RETURNS @Sales TABLE
												  (Sale_Number INT, Date DATE, Sale_Price DECIMAL(5,2), NIF BIGINT)
AS
BEGIN
	INSERT @Sales SELECT Compra.NumCompra AS Sale_Number, Compra.Data AS Date, Compra.Montante AS Sale_Price, Compra.NIF AS NIF
                  FROM (Projeto.Funcionario JOIN Projeto.Compra ON Funcionario.NumFunc=Compra.NumFunc)
                  WHERE Funcionario.NumFunc = @Num
	RETURN;
END
GO
--Test Function
SELECT * FROM Projeto.SoldProductsPerEmployee(112034);
---------------------------------------------------------------------------------------------
GO
CREATE FUNCTION Projeto.ReturnedProductsPerEmployee(@Num INT) RETURNS @Returns TABLE
												  (Return_Number INT, Date DATE, Returned_Value DECIMAL(5,2), NIF BIGINT)
AS
BEGIN
	INSERT @Returns SELECT Devolucao.IDDevolucao AS Return_Number, Devolucao.Data AS Date, Devolucao.Montante AS Returned_Value, Devolucao.NIF AS NIF
                    FROM (Projeto.Funcionario JOIN Projeto.Devolucao ON Funcionario.NumFunc=Devolucao.NumFunc)
                    WHERE Funcionario.NumFunc = @Num
	RETURN;
END
GO
--Test Function
SELECT * FROM Projeto.ReturnedProductsPerEmployee(112034);
---------------------------------------------------------------------------------------------
GO
CREATE FUNCTION Projeto.CheckNewProdut(@Code INT, @Price DECIMAL(5,2), @Name VARCHAR(40), @Type VARCHAR(20)) Returns INT
AS
BEGIN
	DECLARE @Flag INT;
	SELECT @Flag=COUNT(*) FROM Projeto.Artigo WHERE Artigo.Codigo=@Code AND Artigo.Preco=@Price AND Artigo.Nome=@Name AND Artigo.Categoria=@Type;
	IF (@Flag = 1)
	BEGIN
		RETURN 1;
	END
	RETURN 0;
END
GO
--Test Function
SELECT Projeto.CheckNewProdut(156428, 69.99, 'Camisola Equipamento SLB', 'Vestuário');