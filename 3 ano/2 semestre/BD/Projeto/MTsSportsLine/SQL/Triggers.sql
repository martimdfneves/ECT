--Deleting Zone
DROP TRIGGER Projeto.Complete_Sale;
DROP TRIGGER Projeto.Check_UnitsWarehouse;
DROP TRIGGER Projeto.PhoneNumber_Availability;
DROP TRIGGER Projeto.WorkerPhoneNumber_Availability;

--Creating Triggers
GO
CREATE TRIGGER Complete_Sale ON Projeto.Artigo_Comprado
AFTER INSERT
AS
	BEGIN
		DECLARE @Code INT;
		DECLARE @Units INT;
		DECLARE @Purchase INT;
		DECLARE @Worker INT;
		DECLARE @Store INT;
		DECLARE @Quant INT;
		SELECT @Code = Codigo FROM INSERTED;
		SELECT @Units = QuantArtigos FROM INSERTED;
		SELECT @Purchase = NumCompra FROM INSERTED;
		SELECT @Worker = Compra.NumFunc FROM Projeto.Compra WHERE Compra.NumCompra=@Purchase;
		SELECT @Store = Funcionario.NumLoja FROM Projeto.Funcionario WHERE Funcionario.NumFunc=@Worker;
		SELECT @Quant = Artigo_Loja.QuantArtigos FROM Projeto.Artigo_Loja WHERE Artigo_Loja.Codigo=@Code AND Artigo_Loja.NumLoja=@Store;
		IF(@Units > @Quant)
		BEGIN
			RAISERROR('Theres not enough units in the store!', 16, 1);
			ROLLBACK TRAN
		END
	END	
GO
--Test Trigger
SELECT Artigo.Nome AS Name, Preco AS Price, QuantArtigos AS Units
                           FROM ((Projeto.Loja JOIN Projeto.Artigo_Loja ON Loja.NumLoja=Artigo_Loja.NumLoja)
                           JOIN Projeto.Artigo ON Artigo_Loja.Codigo=Artigo.Codigo)
                           WHERE Loja.NumLoja = 1
SELECT * FROM Projeto.Compra;
INSERT INTO Projeto.Compra(NumCompra, Data, Montante, NIF, NumFunc) VALUES (40002, '2020-06-04', 239.94, 123456789, 129078);
INSERT INTO Projeto.Artigo_Comprado(Codigo, NumCompra, QuantArtigos) VALUES (101561, 40002, 1000);
---------------------------------------------------------------------
GO
CREATE TRIGGER Check_UnitsWarehouse ON Projeto.Artigo_Armazem
AFTER INSERT
AS
	BEGIn
		DECLARE @Warehouse INT; 
		DECLARE @Units INT;
		DECLARE @Occupied INT;
		DECLARE @Storage INT;
		SELECT @Warehouse = IDArmazem FROM INSERTED;
		SELECT @Units = QuantArtigos FROM INSERTED
		SELECT @Storage=Armazem.Capacidade FROM Projeto.Armazem WHERE Armazem.IDArmazem=@Warehouse;
		SELECT @Occupied=SUM(Artigo_Armazem.QuantArtigos) FROM Projeto.Artigo_Armazem WHERE Artigo_Armazem.IDArmazem=@Warehouse;
		IF(@Occupied+@Units > @Storage)
		BEGIN
			RAISERROR('Warehouse storage reached!', 16, 1);
			ROLLBACK TRAN;
		END
	END
GO
--Test Trigger
SELECT * FROM Projeto.Artigo_Armazem WHERE Artigo_Armazem.IDArmazem=110;
INSERT INTO Projeto.Artigo_Armazem(Codigo, IDArmazem, QuantArtigos) VALUES(198932, 110, 600);
---------------------------------------------------------------------
GO
CREATE TRIGGER Projeto.PhoneNumber_Availability ON Projeto.Cliente
AFTER INSERT
AS
	BEGIN
		DECLARE @Phone BIGINT;
		DECLARE @Exists INT = 0;
		PRINT'DECLARE: ' +STR(@Exists);
		SELECT @Phone = NumTelem FROM INSERTED;
		PRINT'Phone: ' +STR(@Phone);
		SELECT @Exists=COUNT(NIF) FROM Projeto.Cliente WHERE Cliente.NumTelem=@Phone;
		PRINT'COUNT1: ' +STR(@Exists);
		SELECT @Exists=@Exists+COUNT(NumFunc) FROM Projeto.Funcionario WHERE Funcionario.NumTelem=@Phone;
		PRINT'COUNT2: ' +STR(@Exists);
		IF(@Exists > 0)
		BEGIN
			RAISERROR('The phone number inserted already exists!', 16, 1);
			ROLLBACK TRAN;
		END
	END	
GO
--Test Trigger
SELECT * FROM Projeto.Cliente WHERE Cliente.NumTelem=925642354;
SELECT * FROM Projeto.Funcionario;
INSERT INTO Projeto.Cliente(NIF, Morada, Nome, NumTelem) VALUES(234167271, 'Rua da Frente, Aveiro', 'Maria Tonico', 925642354);
INSERT INTO Projeto.Funcionario(NumFunc, Morada, Nome, NumTelem) VALUES(142057, 'Rua da Frente, Aveiro', 'Maria Tonico', 925642354);
---------------------------------------------------------------------
GO
CREATE TRIGGER Projeto.WorkerPhoneNumber_Availability ON Projeto.Funcionario
AFTER INSERT
AS
	BEGIN
		DECLARE @Phone BIGINT;
		DECLARE @Exists INT = 0;
		SELECT @Phone = NumTelem FROM INSERTED;
		SELECT @Exists=COUNT(NIF) FROM Projeto.Cliente WHERE Cliente.NumTelem=@Phone;
		PRINT'COUNT1: ' +STR(@Exists);
		SELECT @Exists=@Exists+COUNT(NumFunc) FROM Projeto.Funcionario WHERE Funcionario.NumTelem=@Phone;
		PRINT'COUNT2: ' +STR(@Exists);
		IF(@Exists > 0)
		BEGIN
			RAISERROR('The phone number inserted already exists!', 16, 1);
			ROLLBACK TRAN;
		END
	END	
GO
--Test Trigger
INSERT INTO Projeto.Funcionario(NumFunc, Morada, Nome, NumTelem, NumLoja) VALUES(110970, 'Rua da Frente, Aveiro', 'Maria Tonico', 925642354, 1);