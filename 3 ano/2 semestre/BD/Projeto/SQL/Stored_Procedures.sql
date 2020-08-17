-- Deleting zone
DROP PROCEDURE Projeto.Add_newStore;
DROP PROCEDURE Projeto.Add_storeProduct;
DROP PROCEDURE Projeto.Add_Warehouse;
DROP PROCEDURE Projeto.Add_warehouseProduct;
DROP PROCEDURE Projeto.Add_newClient;
DROP PROCEDURE Projeto.Remove_Store;
DROP PROCEDURE Projeto.Remove_storeProduct;
DROP PROCEDURE Projeto.Remove_Warehouse;
DROP PROCEDURE Projeto.Remove_WarehouseProduct;
DROP PROCEDURE Projeto.Remove_Client;
DROP PROCEDURE Projeto.BuyProduct;
DROP PROCEDURE Projeto.ReturnProduct;
DROP PROCEDURE Projeto.ProductsFromWarehouseToStore;
DROP PROCEDURE Projeto.UpdateAddress;
DROP PROCEDURE Projeto.UpdatePhone;
DROP PROCEDURE Projeto.Add_Worker
DROP PROCEDURE Projeto.Add_Delivery
DROP PROCEDURE Projeto.Remove_Worker
DROP PROCEDURE Projeto.Remove_Delivery

-- Adding Stored Procedures
GO
CREATE PROCEDURE Projeto.Add_newStore (@StoreNum INT, @Name VARCHAR(30), @Location VARCHAR(20)) 
AS
	INSERT Projeto.Loja (NumLoja, Nome, Localizacao) 
	VALUES (@StoreNum, @Name, @Location);
GO
--Test Procedure
--EXEC Projeto.Add_newStore 4, 'M&T Sports Line - Guarda', 'Guarda';
----------------------------------------------------------
GO
CREATE PROCEDURE Projeto.Add_storeProduct (@Code INT, @Price DECIMAL(5,2), @Name VARCHAR(40), @Category VARCHAR(20), @StoreNum INT, @NumUnits INT) 
AS
	IF EXISTS (SELECT * FROM Projeto.Loja WHERE Loja.NumLoja=@StoreNum)
	BEGIN
		IF EXISTS (SELECT * FROM Projeto.Artigo_Loja WHERE Artigo_Loja.Codigo=@Code AND Artigo_Loja.NumLoja=@StoreNum)
		BEGIN
			DECLARE @Units INT;
			SELECT @Units=Artigo_Loja.QuantArtigos FROM Projeto.Artigo_Loja WHERE Artigo_Loja.Codigo=@Code AND Artigo_Loja.NumLoja=@StoreNum;
			UPDATE Projeto.Artigo_Loja SET QuantArtigos=@Units + @NumUnits WHERE Artigo_Loja.Codigo=@Code AND Artigo_Loja.NumLoja=@StoreNum;
		END
		ELSE
		BEGIN
			IF EXISTS (SELECT * FROM Projeto.Artigo WHERE Artigo.Codigo=@Code)
			BEGIN
				INSERT Projeto.Artigo_Loja(Codigo, NumLoja, QuantArtigos) 
				VALUES (@Code, @StoreNum, @NumUnits);
			END
			ELSE
			BEGIN
				INSERT Projeto.Artigo(Codigo, Preco, Nome, Categoria) 
				VALUES (@Code, @Price, @Name, @Category);
				INSERT Projeto.Artigo_Loja(Codigo, NumLoja, QuantArtigos) 
				VALUES (@Code, @StoreNum, @NumUnits);
			END
		END
	END
	ELSE
	BEGIN
		RAISERROR ('The store number %d does not exists', 14, 1, @StoreNum);
	END
GO
--Test Procedure
--SELECT * FROM Projeto.Artigo;
--SELECT * FROM Projeto.Artigo_Loja WHERE Artigo_Loja.NumLoja=1;
--EXEC Projeto.Add_storeProduct 100004, 10.0, 'Calções Timberland', 'Vestuário', 1, 1;
----------------------------------------------------------
GO
CREATE PROCEDURE Projeto.Add_Warehouse (@WarehouseID INT, @Storage INT, @StoreNum INT) 
AS
	IF EXISTS (SELECT * FROM Projeto.Loja WHERE Loja.NumLoja=@StoreNum)
	BEGIN
		INSERT Projeto.Armazem(IDArmazem, Capacidade, NumLoja) 
		VALUES (@WarehouseID, @Storage, @StoreNum);
	END
	ELSE
		RAISERROR ('The store number %d does not exists', 14, 1, @StoreNum);
GO
--Test Procedure
--EXEC Projeto.Add_Warehouse 160, 100, 1; 
----------------------------------------------------------
GO
CREATE PROCEDURE Projeto.Add_warehouseProduct (@Code INT, @Price DECIMAL(5,2), @Name VARCHAR(40), @Category VARCHAR(20), @WarehouseID INT, @NumUnits INT) 
AS
	IF EXISTS (SELECT * FROM Projeto.Armazem WHERE Armazem.IDArmazem=@WarehouseID)
	BEGIN
		IF EXISTS (SELECT * FROM Projeto.Artigo_Armazem WHERE Artigo_Armazem.Codigo=@Code AND Artigo_Armazem.IDArmazem=@WarehouseID)
		BEGIN
			DECLARE @Units INT;
			SELECT @Units=Artigo_Armazem.QuantArtigos FROM Projeto.Artigo_Armazem WHERE Artigo_Armazem.Codigo=@Code 
													  AND Artigo_Armazem.IDArmazem=@WarehouseID;
			UPDATE Projeto.Artigo_Armazem SET QuantArtigos=@Units + @NumUnits WHERE Artigo_Armazem.Codigo=@Code 
													  AND Artigo_Armazem.IDArmazem=@WarehouseID;

		END
		ELSE
		BEGIN
			IF EXISTS (SELECT * FROM Projeto.Artigo WHERE Artigo.Codigo=@Code)
			BEGIN
				INSERT Projeto.Artigo_Armazem(Codigo, IDArmazem, QuantArtigos) 
				VALUES (@Code, @WarehouseID, @NumUnits);
			END
			ELSE
			BEGIN
				INSERT Projeto.Artigo(Codigo, Preco, Nome, Categoria) 
				VALUES (@Code, @Price, @Name, @Category);
				INSERT Projeto.Artigo_Armazem(Codigo, IDArmazem, QuantArtigos) 
				VALUES (@Code, @WarehouseID, @NumUnits);
			END
		END
	END
	ELSE
	BEGIN
		RAISERROR ('The warehouse number %d does not exists', 14, 1, @WarehouseID);
	END
GO
--Test Procedure
--SELECT * FROM Projeto.Artigo;
--SELECT * FROM Projeto.Artigo_Armazem WHERE Artigo_Armazem.IDArmazem=160;
--EXEC Projeto.Add_warehouseProduct 100001, 1.0, 'Meias curtas Reebok', 'Vestuário', 160, 15;
----------------------------------------------------------
GO
CREATE PROCEDURE Projeto.Add_newClient (@NIF BIGINT, @Address VARCHAR(40), @Name VARCHAR(20), @Phone BIGINT) 
AS
	IF EXISTS (SELECT * FROM Projeto.Cliente WHERE Cliente.NIF=@NIF)
	BEGIN
		RAISERROR ('The client with NIF %I64i already exists', 14, 1, @NIF);
	END
	ELSE
	BEGIN
		INSERT Projeto.Cliente (NIF, Morada, Nome, NumTelem)
		VALUES (@NIF, @Address, @Name, @Phone);
	END
GO
--Test Procedure
--EXEC Projeto.Add_newClient 100000000, NULL, 'Zé', 911111111;
--SELECT * FROM Projeto.Cliente;
------------------------------------------------------------
GO
CREATE PROCEDURE Projeto.BuyProduct (@PurchaseID INT, @Date Date, @Value DECIMAL(5,2), @NIF BIGINT, @WorkersCode INT, @Store INT, 
								     @Code INT, @Units INT)
AS
	IF EXISTS(SELECT * FROM Projeto.Cliente WHERE Cliente.NIF=@NIF)
	BEGIN
		IF EXISTS(SELECT * FROM Projeto.Funcionario WHERE Funcionario.NumFunc=@WorkersCode)
		BEGIN
			DECLARE @Quant INT;
			SELECT @Quant = Artigo_Loja.QuantArtigos FROM Projeto.Artigo_Loja WHERE Artigo_Loja.Codigo=@Code AND Artigo_Loja.NumLoja=@Store;
			IF (@Quant > @Units)
			BEGIN
				UPDATE Projeto.Artigo_Loja SET Artigo_Loja.QuantArtigos=@Quant-@Units WHERE Artigo_Loja.Codigo=@Code AND Artigo_Loja.NumLoja=@Store;
				INSERT Projeto.Compra(NumCompra, Data, Montante, NIF, NumFunc)
				VALUES (@PurchaseID, @Date, @Value, @NIF, @WorkersCode);
				INSERT Projeto.Artigo_Comprado(Codigo, NumCompra, QuantArtigos)
				VALUES (@Code, @PurchaseID, @Units);
			END
			IF (@Quant - @Units = 0)
			BEGIN
				DELETE FROM Projeto.Artigo_Loja WHERE Artigo_Loja.NumLoja=@Store AND Artigo_Loja.Codigo=@Code;
				INSERT Projeto.Compra(NumCompra, Data, Montante, NIF, NumFunc)
				VALUES (@PurchaseID, @Date, @Value, @NIF, @WorkersCode);
				INSERT Projeto.Artigo_Comprado(Codigo, NumCompra, QuantArtigos)
				VALUES (@Code, @PurchaseID, @Units);
			END
		END
		ELSE
		BEGIN
			RAISERROR ('The worker with the code %d does not exists', 14, 1, @WorkersCode);
		END
	END
	ELSE
	BEGIN
		RAISERROR ('The client with NIF %I64i does not exists', 14, 1, @NIF);
	END
GO
--Test Procedure
--EXEC Projeto.BuyProduct 39230, '2020-06-04', 39.99, 123456789, 132996, 1, 101561, 1;
--SELECT Artigo.Nome AS Name, Preco AS Price, QuantArtigos AS Units
--                           FROM ((Projeto.Loja JOIN Projeto.Artigo_Loja ON Loja.NumLoja=Artigo_Loja.NumLoja)
--                           JOIN Projeto.Artigo ON Artigo_Loja.Codigo=Artigo.Codigo)
--                           WHERE Loja.NumLoja = 1
--SELECT * FROM Projeto.Artigo_Comprado;
--SELECT * FROM Projeto.Compra;
------------------------------------------------------------
GO
CREATE PROCEDURE Projeto.ReturnProduct (@ReturnID INT, @Date Date, @Value DECIMAL(5,2), @NIF BIGINT, @WorkersCode INT, @StoreNum INT, 
										@Code INT, @Quant INT)
AS
	IF EXISTS(SELECT * FROM Projeto.Cliente WHERE Cliente.NIF=@NIF)
	BEGIN
		IF EXISTS(SELECT * FROM Projeto.Funcionario WHERE Funcionario.NumFunc=@WorkersCode)
		BEGIN
			IF EXISTS(SELECT * FROM Projeto.Artigo WHERE Artigo.Codigo=@Code)
			BEGIN
				IF EXISTS(SELECT * FROM Projeto.Artigo_Loja WHERE Artigo_Loja.NumLoja=@StoreNum AND Artigo_Loja.Codigo=@Code)
				BEGIN
					DECLARE @Units INT;
					SELECT @Units=Artigo_Loja.QuantArtigos FROM Projeto.Artigo_Loja WHERE Artigo_Loja.Codigo=@Code 
																					  AND Artigo_Loja.NumLoja=@StoreNum;
					UPDATE Projeto.Artigo_Loja SET Artigo_Loja.QuantArtigos=@Units + @Quant WHERE Artigo_Loja.Codigo=@Code 
																							  AND Artigo_Loja.NumLoja=@StoreNum;
					INSERT Projeto.Devolucao(IDDevolucao, Data, Montante, NIF, NumFunc)
					VALUES (@ReturnID, @Date, @Value, @NIF, @WorkersCode);
					INSERT Projeto.Artigo_Devolvido(Codigo, IDDevolucao, QuantArtigos)
					VALUES (@Code, @ReturnID, @Quant);
				END
				ELSE
				BEGIN
					INSERT Projeto.Artigo_Loja(Codigo, NumLoja, QuantArtigos)
					VALUES (@Code, @StoreNum, @Quant);
					INSERT Projeto.Devolucao(IDDevolucao, Data, Montante, NIF, NumFunc)
					VALUES (@ReturnID, @Date, @Value, @NIF, @WorkersCode);
					INSERT Projeto.Artigo_Devolvido(Codigo, IDDevolucao, QuantArtigos)
					VALUES (@Code, @ReturnID, @Quant);
				END
			END
			ELSE
			BEGIN
				RAISERROR ('The product with the code %d does not exists', 14, 1, @Code);
			END
		END
		ELSE
		BEGIN
			RAISERROR ('The worker with the code %d does not exists', 14, 1, @WorkersCode);
		END
	END
	ELSE
	BEGIN
		RAISERROR ('The client with NIF %I64i does not exists', 14, 1, @NIF);
	END
GO
--Test Procedure
--EXEC Projeto.ReturnProduct 30067, '2020-01-01', 10.00, 123456789, 123091, 1, 100004, 1;
--SELECT * FROM Projeto.Devolucao;
--SELECT Artigo.Nome AS Name, Preco AS Price, QuantArtigos AS Units
--                           FROM ((Projeto.Loja JOIN Projeto.Artigo_Loja ON Loja.NumLoja=Artigo_Loja.NumLoja)
--                           JOIN Projeto.Artigo ON Artigo_Loja.Codigo=Artigo.Codigo)
--                           WHERE Loja.NumLoja = 1
------------------------------------------------------------
GO
CREATE PROCEDURE Projeto.ProductsFromWarehouseToStore (@WarehouseID INT, @Code INT, @StoreNum INT, @Units INT)
AS
	IF EXISTS (SELECT * FROM Projeto.Loja WHERE Loja.NumLoja=@StoreNum)
	BEGIN
		IF EXISTS (SELECT * FROM Projeto.Armazem WHERE Armazem.IDArmazem=@WarehouseID)
		BEGIN
			IF EXISTS(SELECT * FROM Projeto.Artigo WHERE Artigo.Codigo=@Code)
			BEGIN
				DECLARE @WarehouseUnits INT;
				DECLARE @StoreUnits INT;
				SELECT @WarehouseUnits=Artigo_Armazem.QuantArtigos FROM Projeto.Artigo_Armazem WHERE Artigo_Armazem.IDArmazem=@WarehouseID
																								 AND Artigo_Armazem.Codigo=@Code;
				SELECT @StoreUnits=Artigo_Loja.QuantArtigos FROM Projeto.Artigo_Loja WHERE Artigo_Loja.NumLoja=@StoreNum
																					   AND Artigo_Loja.Codigo=@Code;
				UPDATE Projeto.Artigo_Armazem SET Artigo_Armazem.QuantArtigos=@WarehouseUnits-@Units WHERE Artigo_Armazem.IDArmazem=@WarehouseID
																								       AND Artigo_Armazem.Codigo=@Code;
				IF EXISTS(SELECT * FROM Projeto.Artigo_Loja WHERE Artigo_Loja.Codigo=@Code AND Artigo_Loja.NumLoja=@StoreNum)
				BEGIN
					UPDATE Projeto.Artigo_Loja SET Artigo_Loja.QuantArtigos=@StoreUnits+@Units WHERE Artigo_Loja.NumLoja=@StoreNum
																								 AND Artigo_Loja.Codigo=@Code;
				END
				ELSE
				BEGIN
					INSERT Projeto.Artigo_Loja(Codigo, NumLoja, QuantArtigos)
					VALUES (@Code, @StoreNum, @Units);
				END
			END
			ELSE
			BEGIN
				RAISERROR ('The product with the code %d does not exists', 14, 1, @Code);
			END
		END
		ELSE
		BEGIN
			RAISERROR ('The warehouse number %d does not exists', 14, 1, @WarehouseID);
		END
	END
	ELSE
	BEGIN
		RAISERROR ('The store number %d does not exists', 14, 1, @StoreNum);
	END
GO
--Test Procedure
--EXEC Projeto.ProductsFromWarehouseToStore 160, 156428, 4, 5;
--SELECT Artigo.Nome AS Name, Preco AS Price, QuantArtigos AS Units
--                           FROM ((Projeto.Loja JOIN Projeto.Artigo_Loja ON Loja.NumLoja=Artigo_Loja.NumLoja)
--                           JOIN Projeto.Artigo ON Artigo_Loja.Codigo=Artigo.Codigo)
--                           WHERE Loja.NumLoja = 4;
--SELECT Name, Price, Units FROM WarehousesProducts WHERE WarehouseID = 160;
------------------------------------------------------------
GO
CREATE PROCEDURE Projeto.UpdateAddress (@NIF BIGINT, @Address VARCHAR(40))
AS
	IF EXISTS(SELECT * FROM Projeto.Cliente WHERE Cliente.NIF=@NIF)
	BEGIN
		UPDATE Projeto.Cliente SET Cliente.Morada=@Address WHERE Cliente.NIF=@NIF;
	END
	ELSE
		RAISERROR ('The client with NIF %I64i does not exists', 14, 1, @NIF);
GO
--Test Procedure
--EXEC Projeto.UpdateAddress 214139274, 'R. de Egas Moniz, Viseu';
--SELECT * FROM Projeto.Cliente;
------------------------------------------------------------
GO
CREATE PROCEDURE Projeto.UpdatePhone (@NIF BIGINT, @Phone BIGINT)
AS
	IF EXISTS(SELECT * FROM Projeto.Cliente WHERE Cliente.NIF=@NIF)
	BEGIN
		UPDATE Projeto.Cliente SET Cliente.NumTelem=@Phone WHERE Cliente.NIF=@NIF;
	END
	ELSE
		RAISERROR ('The client with NIF %I64i does not exists', 14, 1, @NIF);
GO
--Test Procedure
--EXEC Projeto.UpdatePhone 100000000, 966666660;
--SELECT * FROM Projeto.Cliente;
------------------------------------------------------------
GO
CREATE PROCEDURE Projeto.Add_Worker (@Num INT, @Address VARCHAR(40), @Name VARCHAR(20), @Phone BIGINT, @StoreNum INT) 
AS
	IF EXISTS (SELECT * FROM Projeto.Loja WHERE Loja.NumLoja=@StoreNum)
	BEGIN
		IF EXISTS (SELECT * FROM Projeto.Funcionario WHERE Funcionario.NumFunc=@Num)
		BEGIN
			RAISERROR ('The worker with number %d already exists', 14, 1, @Num);
		END
		ELSE
			INSERT Projeto.Funcionario (NumFunc, Morada, Nome, NumTelem, NumLoja)
			VALUES (@Num, @Address, @Name, @Phone, @StoreNum);
	END
	ELSE
		RAISERROR ('The store number %d does not exists', 14, 1, @StoreNum);
GO
--Test Procedure
--EXEC Projeto.Add_Worker 100000000, 'Rua da Frente, Maia', 'Carlos Manuel', 911111111, 2;
----------------------------------------------------------
GO
CREATE PROCEDURE Projeto.Add_Delivery (@Id INT, @Data DATE, @Dest VARCHAR(40), @Code INT, @Quant INT, @Store INT)
AS
	IF EXISTS (SELECT * FROM Projeto.Transporte WHERE Transporte.IDTransporte=@Id)
	BEGIN
		RAISERROR ('The delivery with number %d already exists', 14, 1, @Id);
	END
	ELSE
		IF EXISTS (SELECT * FROM Projeto.Artigo_Loja WHERE Artigo_Loja.Codigo=@Code AND Artigo_Loja.NumLoja=@Store)
		BEGIN
			DECLARE @Units INT
			SELECT @Units=Artigo_Loja.QuantArtigos FROM Projeto.Artigo_Loja WHERE Artigo_Loja.Codigo=@Code AND Artigo_Loja.NumLoja=@Store
			IF (@Quant>@Units)
			BEGIN
				RAISERROR ('There are not enough units to complete the delivery! Number of available units: %d', 14, 1, @Units);
			END
			ELSE
				IF (@Quant=@Units)
				BEGIN
					INSERT Projeto.Transporte (IDTransporte, Data, Destino)
					VALUES (@Id, @Data, @Dest);
					INSERT Projeto.Artigo_Transporte (Codigo, IDTransporte, QuantArtigos)
					VALUES (@Code, @Id, @Quant);
					DELETE FROM Projeto.Artigo_Loja WHERE Artigo_Loja.Codigo=@Code AND Artigo_Loja.NumLoja=@Store;
				END
				ELSE
					DECLARE @NewQuant INT = @Units - @Quant;
					UPDATE Projeto.Artigo_Loja SET Artigo_Loja.QuantArtigos=@NewQuant WHERE Artigo_Loja.Codigo=@Code AND Artigo_Loja.NumLoja=@Store;
					INSERT Projeto.Transporte (IDTransporte, Data, Destino)
					VALUES (@Id, @Data, @Dest);
					INSERT Projeto.Artigo_Transporte (Codigo, IDTransporte, QuantArtigos)
					VALUES (@Code, @Id, @Quant);
		END
		ELSE 
			RAISERROR ('The product with code %d does not exist!', 14, 1, @Code);
GO
--Test Procedure
--EXEC Projeto.Add_Delivery 272727, '2020-06-03', 'Rua da Frente, Maia', 2, 101561, 2;

-- Removing Stored Procedures
GO
CREATE PROCEDURE Projeto.Remove_Store (@StoreNum INT) 
AS
	IF EXISTS (SELECT * FROM Projeto.Loja WHERE Loja.NumLoja=@StoreNum)
	BEGIN
		--Removing workers in @StoreNume store and dependencies
		DECLARE @WorkerID INT;
		SELECT @WorkerID = Funcionario.NumFunc FROM Projeto.Funcionario WHERE Funcionario.NumLoja=@StoreNum; 
		
		DECLARE @PurchaseID INT;
		DECLARE @ReturnID INT;
		SELECT @PurchaseID = Compra.NumCompra FROM Projeto.Compra WHERE Compra.NumFunc=@WorkerID;
		SELECT @ReturnID = Devolucao.IDDevolucao FROM Projeto.Devolucao WHERE Devolucao.NumFunc=@WorkerID;

		IF EXISTS(SELECT * FROM Projeto.Artigo_Comprado WHERE Artigo_Comprado.NumCompra=@PurchaseID)
		BEGIN
			DELETE FROM Projeto.Artigo_Comprado WHERE Artigo_Comprado.NumCompra=@PurchaseID;
		END
		IF EXISTS(SELECT * FROM Projeto.Artigo_Devolvido WHERE Artigo_Devolvido.IDDevolucao=@ReturnID)
		BEGIN
			DELETE FROM Projeto.Artigo_Devolvido WHERE Artigo_Devolvido.IDDevolucao=@ReturnID;
		END
		IF EXISTS(SELECT * FROM Projeto.Compra WHERE Compra.NumFunc=@WorkerID)
		BEGIN
			DELETE FROM Projeto.Compra WHERE Compra.NumFunc=@WorkerID;
		END
		IF EXISTS(SELECT * FROM Projeto.Devolucao WHERE Devolucao.NumFunc=@WorkerID)
		BEGIN
			DELETE FROM Projeto.Devolucao WHERE Devolucao.NumFunc=@WorkerID;
		END
		IF EXISTS(SELECT * FROM Projeto.Funcionario WHERE Funcionario.NumLoja=@StoreNum)
		BEGIN
			DELETE FROM Projeto.Funcionario WHERE Funcionario.NumLoja=@StoreNum;
		END

		--Removing store and dependencies
		DECLARE @numWarehouses INT;
		SELECT @numWarehouses = COUNT(*) FROM Projeto.Armazem WHERE Armazem.NumLoja=@StoreNum;
		WHILE (@numWarehouses) > 0
		BEGIN
			DECLARE @Warehouse INT;
			SELECT @Warehouse = Armazem.IDArmazem FROM Projeto.Armazem WHERE Armazem.NumLoja=@StoreNum; 

			IF EXISTS(SELECT * FROM Projeto.Artigo_Armazem WHERE Artigo_Armazem.IDArmazem=@Warehouse)
			BEGIN
				DELETE FROM Projeto.Artigo_Armazem WHERE Artigo_Armazem.IDArmazem=@Warehouse;
			END
			IF EXISTS(SELECT * FROM Projeto.Armazem WHERE Armazem.IDArmazem=@Warehouse)
			BEGIN
				DELETE FROM Projeto.Armazem WHERE Armazem.IDArmazem=@Warehouse;
			END
			IF EXISTS(SELECT * FROM Projeto.Artigo_Loja WHERE Artigo_Loja.NumLoja=@StoreNum)
			BEGIN
				DELETE FROM Projeto.Artigo_Loja WHERE Artigo_Loja.NumLoja=@StoreNum;
			END
			SET @numWarehouses -= 1;
		END
		DELETE FROM Projeto.Loja WHERE Loja.NumLoja=@StoreNum;
	END
	ELSE
		RAISERROR ('The store number %d does not exists', 14, 1, @StoreNum);
GO
--Test Procedure
--EXEC Projeto.Remove_Store 5;
--SELECT * FROM Projeto.Armazem WHERE Armazem.NumLoja=5
--SELECT * FROM Projeto.Loja
--SELECT Name, Price, Units FROM WarehousesProducts WHERE WarehouseID = 180	
--SELECT COUNT(*) FROM Projeto.Armazem WHERE Armazem.NumLoja=5
----------------------------------------------------------
GO
CREATE PROCEDURE Projeto.Remove_storeProduct (@StoreNum INT, @Code INT, @Units INT) 
AS
	IF EXISTS(SELECT * FROM Projeto.Loja WHERE Loja.NumLoja=@StoreNum)
	BEGIN
		IF EXISTS (SELECT * FROM Projeto.Artigo_Loja WHERE Artigo_Loja.NumLoja=@StoreNum AND Artigo_Loja.Codigo=@Code)
		BEGIN
			DECLARE @Quant INT;
			SELECT @Quant = Artigo_Loja.QuantArtigos FROM Projeto.Artigo_Loja WHERE Artigo_Loja.NumLoja=@StoreNum AND Artigo_Loja.Codigo=@Code;
			IF(@Units < @Quant)
			BEGIN
				UPDATE Projeto.Artigo_Loja SET Artigo_Loja.QuantArtigos=@Quant-@Units WHERE Artigo_Loja.NumLoja=@StoreNum AND Artigo_Loja.Codigo=@Code;
			END
			IF(@Quant - @Units = 0)
			BEGIN
				DELETE FROM Projeto.Artigo_Loja WHERE Artigo_Loja.NumLoja=@StoreNum AND Artigo_Loja.Codigo=@Code;
			END
			IF(@Units > @Quant)
			BEGIN
				RAISERROR ('Theres not %d units of the product %d in the store %d', 14, 1, @Units, @Code, @StoreNum);
			END
		END
		ELSE
		BEGIN
			RAISERROR ('The product %d in the store number %d does not exists', 14, 1, @Code, @StoreNum);
		END
	END
	ELSE
	BEGIN
		RAISERROR ('The store number %d does not exists', 14, 1, @StoreNum);
	END
GO
--Test Procedure
--EXEC Projeto.Remove_storeProduct 1, 100003, 2;	
--SELECT * FROM Projeto.Artigo;
--SELECT * FROM Projeto.Artigo_Loja WHERE Artigo_Loja.NumLoja=1;
----------------------------------------------------------
GO
CREATE PROCEDURE Projeto.Remove_Warehouse (@WarehouseID INT) 
AS
	IF EXISTS (SELECT * FROM Projeto.Armazem WHERE Armazem.IDArmazem=@WarehouseID)
	BEGIN
		--Removing warehouse dependencies
		DELETE FROM Projeto.Artigo_Armazem WHERE Artigo_Armazem.IDArmazem=@WarehouseID;
		--Removing Warehouse
		DELETE FROM Projeto.Armazem WHERE Armazem.IDArmazem=@WarehouseID;
	END
	ELSE
		RAISERROR ('The warehouse number %d does not exists', 14, 1, @WarehouseID);
GO
--Test Procedure
--EXEC Projeto.Remove_Warehouse 160; 
----------------------------------------------------------
GO
CREATE PROCEDURE Projeto.Remove_WarehouseProduct (@WarehouseID INT, @Code INT, @Units INT) 
AS
	IF EXISTS(SELECT * FROM Projeto.Armazem WHERE Armazem.IDArmazem=@WarehouseID)
	BEGIN
		IF EXISTS (SELECT * FROM Projeto.Artigo_Armazem WHERE Artigo_Armazem.IDArmazem=@WarehouseID AND Artigo_Armazem.Codigo=@Code)
		BEGIN
			DECLARE @Quant INT;
			SELECT @Quant = Artigo_Armazem.QuantArtigos FROM Projeto.Artigo_Armazem WHERE Artigo_Armazem.IDArmazem=@WarehouseID 
																					  AND Artigo_Armazem.Codigo=@Code;
			IF(@Units < @Quant)
			BEGIN
				UPDATE Projeto.Artigo_Armazem SET Artigo_Armazem.QuantArtigos=@Quant-@Units WHERE Artigo_Armazem.IDArmazem=@WarehouseID 
																							  AND Artigo_Armazem.Codigo=@Code;
			END
			IF(@Quant - @Units = 0)
			BEGIN
				DELETE FROM Projeto.Artigo_Armazem WHERE Artigo_Armazem.IDArmazem=@WarehouseID AND Artigo_Armazem.Codigo=@Code;
			END
			IF(@Units > @Quant)
			BEGIN
				RAISERROR ('Theres not %d units of the product %d in the warehouse %d', 14, 1, @Units, @Code, @WarehouseID);
			END
		END
		ELSE
		BEGIN
			RAISERROR ('The product %d in the warehouse number %d does not exists', 14, 1, @Code, @WarehouseID);
		END
	END
	ELSE
	BEGIN
		RAISERROR ('The warehouse number %d does not exists', 14, 1, @WarehouseID);
	END
GO
--Test Procedure
--SELECT * FROM Projeto.Artigo_Armazem WHERE Artigo_Armazem.IDArmazem=160;
--EXEC Projeto.Remove_WarehouseProduct 160, 100001, 14;
----------------------------------------------------------
GO
CREATE PROCEDURE Projeto.Remove_Client (@NIF BIGINT) 
AS
	IF EXISTS (SELECT * FROM Projeto.Cliente WHERE Cliente.NIF=@NIF)
	BEGIN
		IF EXISTS (SELECT * FROM Projeto.Compra WHERE Compra.NIF=@NIF)
		BEGIN
			DECLARE @numPurchases INT;
			SELECT @numPurchases=COUNT(*) FROM Projeto.Compra WHERE Compra.NIF=@NIF;
			WHILE (@numPurchases)>0
			BEGIN
				DECLARE @PurchaseID INT;
				SELECT @PurchaseID = Compra.NumCompra FROM Projeto.Compra WHERE Compra.NIF=@NIF;
				DELETE FROM Projeto.Artigo_Comprado WHERE Artigo_Comprado.NumCompra=@PurchaseID;
				DELETE FROM Projeto.Compra WHERE Compra.NIF=@NIF AND Compra.NumCompra=@PurchaseID;
				SET @numPurchases-=1;
			END
		END
		IF EXISTS (SELECT * FROM Projeto.Devolucao WHERE Devolucao.NIF=@NIF)
		BEGIN
			DECLARE @numReturns INT;
			SELECT @numReturns=COUNT(*) FROM Projeto.Devolucao WHERE Devolucao.NIF=@NIF;
			WHILE (@numReturns)>0
			BEGIN
				DECLARE @ReturnID INT;
				SELECT @ReturnID = Devolucao.IDDevolucao FROM Projeto.Devolucao WHERE Devolucao.NIF=@NIF;
				DELETE FROM Projeto.Artigo_Devolvido WHERE Artigo_Devolvido.IDDevolucao=@ReturnID;
				DELETE FROM Projeto.Devolucao WHERE Devolucao.NIF=@NIF AND Devolucao.IDDevolucao=@ReturnID;
				SET @numReturns-=1;
			END
		END
		DELETE FROM Projeto.Cliente WHERE Cliente.NIF=@NIF;
	END
	ELSE
	BEGIN
		RAISERROR ('The client with the NIF %I64i does not exists', 14, 1, @NIF);
	END
GO
--Test Procedure
--EXEC Projeto.Remove_Client 100000000;
--SELECT * FROM Projeto.Cliente;
------------------------------------------------------------
GO
CREATE PROCEDURE Projeto.Remove_Worker (@Num INT) 
AS
	IF EXISTS (SELECT * FROM Projeto.Funcionario WHERE Funcionario.NumFunc=@Num)
	BEGIN
		IF EXISTS (SELECT * FROM Projeto.Compra WHERE Compra.NumFunc=@Num)
		BEGIN
			DECLARE @numSales INT;
			SELECT @numSales=COUNT(*) FROM Projeto.Compra WHERE Compra.NumFunc=@Num;
			WHILE (@numSales)>0
			BEGIN
				DECLARE @PurchaseID INT;
				SELECT  @PurchaseID = Compra.NumCompra FROM Projeto.Compra WHERE Compra.NumFunc=@Num;
				DELETE FROM Projeto.Artigo_Comprado WHERE Artigo_Comprado.NumCompra=@PurchaseID;
				DELETE FROM Projeto.Compra WHERE Compra.NumFunc=@Num AND Compra.NumCompra=@PurchaseID;
				SET @numSales-=1;
			END
		END
		IF EXISTS (SELECT * FROM Projeto.Devolucao WHERE Devolucao.NumFunc=@Num)
		BEGIN
			DECLARE @numReturns INT;
			SELECT @numReturns=COUNT(*) FROM Projeto.Devolucao WHERE Devolucao.NumFunc=@Num;
			WHILE (@numReturns)>0
			BEGIN
				DECLARE @ReturnID INT;
				SELECT @ReturnID = Devolucao.IDDevolucao FROM Projeto.Devolucao WHERE Devolucao.NumFunc=@Num;
				DELETE FROM Projeto.Artigo_Devolvido WHERE Artigo_Devolvido.IDDevolucao=@ReturnID;
				DELETE FROM Projeto.Devolucao WHERE Devolucao.NumFunc=@Num AND Devolucao.IDDevolucao=@ReturnID;
				SET @numReturns-=1;
			END
		END
		DELETE FROM Projeto.Funcionario WHERE Funcionario.NumFunc=@Num;
	END
	ELSE
		RAISERROR ('The employee with the number %d does not exists', 14, 1, @Num);
GO
--Test Procedure
--EXEC Projeto.Remove_Worker 112034;
--------------------------------------------------------------
GO
CREATE PROCEDURE Projeto.Remove_Delivery (@Id INT, @Store INT) 
AS
	IF EXISTS (SELECT * FROM Projeto.Transporte WHERE Transporte.IDTransporte=@Id)
	BEGIN
		DECLARE @Code INT;
		SELECT @Code=Artigo_Transporte.Codigo FROM Projeto.Artigo_Transporte WHERE Artigo_Transporte.IDTransporte=@Id;
		DECLARE @Units INT;
		SELECT @Units=Artigo_Transporte.QuantArtigos FROM Projeto.Artigo_Transporte WHERE Artigo_Transporte.IDTransporte=@Id;
		IF EXISTS(SELECT * FROM Projeto.Artigo_Loja WHERE Artigo_Loja.NumLoja=@Store AND Artigo_Loja.Codigo=@Code)
		BEGIN
			UPDATE Projeto.Artigo_Loja SET Artigo_Loja.QuantArtigos=Artigo_Loja.QuantArtigos+@Units WHERE Artigo_Loja.NumLoja=@Store AND Artigo_Loja.Codigo=@Code;
			DELETE FROM Projeto.Artigo_Transporte WHERE Artigo_Transporte.IDTransporte=@Id;
			DELETE FROM Projeto.Transporte WHERE Transporte.IDTransporte=@Id;
		END
		ELSE
			INSERT Projeto.Artigo_Loja(Codigo, NumLoja, QuantArtigos) 
			VALUES (@Code, @Store, @Units); 
			DELETE FROM Projeto.Artigo_Transporte WHERE Artigo_Transporte.IDTransporte=@Id;
			DELETE FROM Projeto.Transporte WHERE Transporte.IDTransporte=@Id;
	END
	ELSE 
		RAISERROR ('The delivery with ID %d does not exists', 14, 1, @Id);
GO
--Test Procedure
--EXEC Projeto.Remove_Delivery 100259,2;
