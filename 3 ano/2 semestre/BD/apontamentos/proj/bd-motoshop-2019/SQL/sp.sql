use p2g5;

-- Drop Section
DROP PROCEDURE ms_INSERT_STOCK_MOTORCYCLE;
DROP PROCEDURE ms_INSERT_RENTABLE_MOTORCYCLE;
DROP PROCEDURE ms_INSERT_OWNED_MOTORCYCLE;
DROP PROCEDURE ms_ADD_STAND;
DROP PROCEDURE ms_ADD_WORKSHOP;
DROP PROCEDURE ms_REM_STAND;
DROP PROCEDURE ms_REM_WORKSHOP;
DROP PROCEDURE ms_ADD_SALESMAN;
DROP PROCEDURE ms_ADD_MECHANIC;
DROP PROCEDURE ms_REM_SALESMAN;
DROP PROCEDURE ms_REM_MECHANIC;
DROP PROCEDURE ms_UPDATE_STOCK_MOTORCYCLE;
DROP PROCEDURE ms_UPDATE_RENT_MOTORCYCLE;
DROP PROCEDURE ms_PERFORM_SALE;
DROP PROCEDURE ms_REM_SALE;
DROP PROCEDURE ms_PERFORM_REVISION;
DROP PROCEDURE ms_REM_REVISION;
DROP PROCEDURE ms_PERFORM_RENT;
DROP PROCEDURE ms_REM_RENT;

-- Stored Procedures
GO
CREATE PROCEDURE ms_INSERT_STOCK_MOTORCYCLE(@FRAME_NO INT, @MODEL VARCHAR(20), @BRAND VARCHAR(20), @YEAR INT, @CC INT, @HP INT,
								   @PRICE INT, @STAND INT)
AS
	-- Insert in the parent table
	INSERT MOTORCYCLE (FRAME_NO, MODEL, BRAND, YEAR, CC, HP) VALUES (@FRAME_NO, @MODEL, @BRAND, @YEAR, @CC, @HP);
	-- Insert in the child table
	INSERT STOCK_BIKE (FRAME_NO, PRICE, STAND) VALUES (@FRAME_NO, @PRICE, @STAND);
GO
-- Test procedure
-- EXEC INSERT_STOCK_MOTORCYCLE 888888888, 'Kawasaki', 'Power', 2013, 125, 10, 2000, 0 
---------------------------------------------

GO
CREATE PROCEDURE ms_INSERT_RENTABLE_MOTORCYCLE(@FRAME_NO INT, @MODEL VARCHAR(20), @BRAND VARCHAR(20), @YEAR INT, @CC INT, @HP INT,
								   @PRICE_KM INT, @TOTAL_KM INT, @MOTO_STATION INT)
AS
	-- Insert in the parent table
	INSERT MOTORCYCLE (FRAME_NO, MODEL, BRAND, YEAR, CC, HP) VALUES (@FRAME_NO, @MODEL, @BRAND, @YEAR, @CC, @HP);
	-- Insert in the child table
	INSERT RENTABLE_BIKE(FRAME_NO, PRICE_KM, TOTAL_KM, MOTO_STATION) VALUES (@FRAME_NO, @PRICE_KM, @TOTAL_KM, @MOTO_STATION);
GO
-----------------------------------------------

GO
CREATE PROCEDURE ms_INSERT_OWNED_MOTORCYCLE(@FRAME_NO INT, @MODEL VARCHAR(20), @BRAND VARCHAR(20), @YEAR INT, @CC INT, @HP INT,
								   @LICENSE_PLATE INT, @TOTAL_KM INT, @OWNER INT)
AS
	-- Insert in the parent table
	INSERT MOTORCYCLE (FRAME_NO, MODEL, BRAND, YEAR, CC, HP) VALUES (@FRAME_NO, @MODEL, @BRAND, @YEAR, @CC, @HP);
	-- Insert in the child table
	INSERT OWNED_BIKE(FRAME_NO, LICENSE_PLATE, TOTAL_KM, B_OWNER) VALUES (@FRAME_NO, @LICENSE_PLATE, @TOTAL_KM, @OWNER);
GO
---------------------------------------------

GO
CREATE PROCEDURE ms_ADD_STAND (@Localization varchar(150))
AS
	IF EXISTS (SELECT 1 FROM STAND WHERE LOCALIZATION=@Localization)
		RAISERROR ('Já existe um Stand em %s', 16, 1, @Localization);
	ELSE
		INSERT STAND(LOCALIZATION) VALUES (@Localization)
GO
-- Test Procedure
-- EXEC ms_ADD_STAND 'Lisboa'
---------------------------------------------

GO
CREATE PROCEDURE ms_ADD_WORKSHOP (@Localization varchar(150))
AS
	IF EXISTS (SELECT 1 FROM WORKSHOP WHERE LOCALIZATION=@Localization)
		RAISERROR ('Já existe uma Workshop em %s', 16, 1, @Localization);
	ELSE
		INSERT WORKSHOP(LOCALIZATION) VALUES (@Localization);
GO
-- Test Procedure
-- EXEC ms_ADD_STAND 'Loures'
---------------------------------------------

GO
CREATE PROCEDURE ms_REM_STAND (@ID INT)
AS
	IF EXISTS (SELECT * FROM STAND WHERE NUMBER=@ID)
		DELETE FROM STAND WHERE NUMBER=@ID;
	ELSE
		RAISERROR ('Não existe nenhum Stand com o ID %d', 14, 1, @ID);
GO
-- Test Procedure
-- EXEC ms_REM_STAND 7
---------------------------------------------

GO
CREATE PROCEDURE ms_REM_WORKSHOP (@ID INT)
AS
	IF EXISTS (SELECT * FROM WORKSHOP WHERE NUMBER=@ID)
		DELETE FROM WORKSHOP WHERE NUMBER=@ID;
	ELSE
		RAISERROR ('Não existe nenhuma Workshop com o ID %d', 14, 1, @ID);
GO
-- Test Procedure
-- EXEC ms_REM_WORKSHOP 1
---------------------------------------------

GO
CREATE PROCEDURE ms_ADD_SALESMAN (@Name varchar(20), @Address varchar(150), @Stand INT)
AS
	INSERT STAFF_MEMBER(S_NAME, S_ADDRESS) VALUES (@Name, @Address);
	Declare @id INT
	SELECT @id=NUMBER FROM STAFF_MEMBER WHERE S_NAME=@NAME;
	
	INSERT SALESMAN(NUMBER, WORK_STAND) VALUES (@id, @Stand);
GO
-- Test Procedure
-- EXEC ms_ADD_SALESMAN 'Rui Silva', 'Rua da frente', 4
---------------------------------------------

GO
CREATE PROCEDURE ms_ADD_MECHANIC (@Name varchar(20), @Address varchar(150), @Workshop INT)
AS
	INSERT STAFF_MEMBER(S_NAME, S_ADDRESS) VALUES (@Name, @Address);
	Declare @id INT
	SELECT @id=NUMBER FROM STAFF_MEMBER WHERE S_NAME=@NAME;
	
	INSERT MECHANIC(NUMBER, WORK_WORKSHOP) VALUES (@id, @Workshop);
GO
-- Test Procedure
-- EXEC ms_ADD_MECHANIC 'Rui Silva', 'Rua da frente', 3
---------------------------------------------

GO
CREATE PROCEDURE ms_REM_SALESMAN (@Number INT)
AS 
	IF EXISTS (SELECT * FROM SALESMAN WHERE NUMBER=@Number)
	BEGIN
		DELETE FROM SALESMAN WHERE NUMBER=@Number;
		DELETE FROM STAFF_MEMBER WHERE NUMBER=@Number;
	END
	ELSE
		RAISERROR ('Não existe nenhum vendedor com o Número %d', 14, 1, @Number);
GO
-- Test Procedure
-- EXEC ms_REM_SALESMAN 103
---------------------------------------------

GO
CREATE PROCEDURE ms_REM_MECHANIC (@Number INT)
AS 
	IF EXISTS (SELECT * FROM MECHANIC WHERE NUMBER=@Number)
	BEGIN
		DELETE FROM MECHANIC WHERE NUMBER=@Number;
		DELETE FROM STAFF_MEMBER WHERE NUMBER=@Number;
	END
	ELSE
		RAISERROR ('Não existe nenhum mecânico com o Número %d', 14, 1, @Number);
GO
-- Test Procedure
-- EXEC ms_REM_MECHANIC 117
---------------------------------------------

GO
CREATE PROCEDURE ms_UPDATE_STOCK_MOTORCYCLE(@Frame INT, @newprice INT)
AS
	UPDATE STOCK_BIKE SET PRICE=@newprice WHERE FRAME_NO = @Frame;
GO
---------------------------------------------

GO
CREATE PROCEDURE ms_UPDATE_RENT_MOTORCYCLE(@Frame INT, @newprice INT, @newkm INT)
AS
	DECLARE @OldKm INT;
	SELECT @OldKm = TOTAL_KM FROM RENTABLE_BIKE WHERE FRAME_NO=@Frame

	IF @OldKm < @newkm
		UPDATE RENTABLE_BIKE SET PRICE_KM=@newprice, TOTAL_KM = @newkm WHERE FRAME_NO = @Frame;
	ELSE
		RAISERROR ('Novos kilometros não podem ser inferiores', 16, 1);
GO
---------------------------------------------

GO
CREATE PROCEDURE ms_PERFORM_SALE(@Bike varchar(30), @Client INT, @Seller INT)
AS
	DECLARE @Price INT;
	DECLARE @Frame INT;

	SELECT @Price = PRICE
	FROM STOCK_BIKE JOIN MOTORCYCLE ON STOCK_BIKE.FRAME_NO=MOTORCYCLE.FRAME_NO
	WHERE (BRAND + ' ' + MODEL + ' ' + CONVERT(varchar, CC)) = @Bike

	SELECT @Frame = FRAME_NO
	FROM MOTORCYCLE WHERE (BRAND + ' ' + MODEL + ' ' + CONVERT(varchar, CC)) = @Bike

	INSERT INTO SALE (CLIENT, MOTORCYCLE, SELLER, PRICE, S_DATE) VALUES (@Client, @Frame, @Seller, @Price, CONVERT (date, GETDATE())); 
GO
-- Test Procedure
-- EXEC ms_PERFORM_SALE 'Kawasaki Star 1100', 430803428, 105
---------------------------------------------

GO
CREATE PROCEDURE ms_REM_SALE(@Invoice INT)
AS
	DELETE FROM SALE WHERE SALE.INVOICE_NO = @Invoice;
GO
-- Test Procedure
-- EXEC ms_REM_SALE 1001
---------------------------------------------

GO
CREATE PROCEDURE ms_PERFORM_REVISION(@Bike varchar(30), @Mechanic INT, @Price INT)
AS
	DECLARE @Frame INT;

	SELECT @Frame = FRAME_NO
	FROM MOTORCYCLE WHERE (BRAND + ' ' + MODEL + ' ' + CONVERT(varchar, CC)) = @Bike

	INSERT INTO REVISION (FRAME_NO, R_DATE, PRICE, MECHANIC) VALUES (@Frame, CONVERT (date, GETDATE()), @Price, @Mechanic); 

	DECLARE @Part INT;
	SELECT @Part = NUMBER FROM PART ORDER BY NEWID()
	
	DECLARE @RevNumber INT;
	SELECT @RevNumber = REVISION_NO FROM REVISION ORDER BY REVISION_NO ASC
	
	INSERT INTO CHANGED_PARTS(REVISION_NO, PART) VALUES (@RevNumber, @Part);

GO
-- Test Procedure
-- EXEC ms_PERFORM_REVISION 'BMW GS 200', 104, 3213
---------------------------------------------

GO
CREATE PROCEDURE ms_REM_REVISION(@Number INT)
AS
	DELETE FROM REVISION WHERE REVISION.REVISION_NO = @Number;
GO
-- Test Procedure
-- EXEC ms_REM_REVISION 1001
---------------------------------------------

GO
CREATE PROCEDURE ms_PERFORM_RENT(@Bike varchar(30), @Client INT)
AS
	DECLARE @Frame INT;

	SELECT @Frame = FRAME_NO
	FROM MOTORCYCLE WHERE (BRAND + ' ' + MODEL + ' ' + CONVERT(varchar, CC)) = @Bike

	INSERT INTO RENT (FRAME_NO, R_DATE, CLIENT) VALUES (@Frame, CONVERT (date, GETDATE()), @Client); 

GO
-- Test Procedure
-- EXEC ms_PERFORM_REVISION 'BMW GS 200', 104, 3213
---------------------------------------------

GO
CREATE PROCEDURE ms_REM_RENT(@Bike varchar(30), @Date varchar(20))
AS
	DECLARE @Frame INT;

	SELECT @Frame = FRAME_NO
	FROM MOTORCYCLE WHERE (BRAND + ' ' + MODEL) = @Bike
	
	DELETE FROM RENT WHERE RENT.FRAME_NO = @Frame AND RENT.R_DATE = CONVERT(Date, @Date, 6);
GO
-- Test Procedure
-- EXEC ms_REM_RENT 'Yamanha MT', '06 Mar 16'
---------------------------------------------

