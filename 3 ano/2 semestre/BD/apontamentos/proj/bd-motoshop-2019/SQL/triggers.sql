use p2g5;

-- Drop Section
DROP TRIGGER COMPLETE_SALE;
DROP TRIGGER GRANT_DISJOINT_OWNED;
DROP TRIGGER GRANT_DISJOINT_RENT;
DROP TRIGGER GRANT_DISJOINT_STOCK;
DROP TRIGGER GRANT_DISJOINT_SALESMAN;
DROP TRIGGER GRANT_DISJOINT_MECHANIC;

-- Triggers
GO
CREATE TRIGGER COMPLETE_SALE ON SALE
AFTER INSERT
AS
	BEGIN
		DECLARE @sold_bike AS INT;
		DECLARE @sold_to   AS INT;
		DECLARE @sold_bike_license AS INT = RAND()*(999999-100000)+100000;
		SELECT  @sold_bike = MOTORCYCLE FROM inserted;
		SELECT  @sold_to = CLIENT FROM inserted; 

		DELETE FROM STOCK_BIKE WHERE FRAME_NO=@sold_bike
		INSERT INTO OWNED_BIKE (FRAME_NO, LICENSE_PLATE, TOTAL_KM, B_OWNER) 
		VALUES(@sold_bike, @sold_bike_license, 0, @sold_to);
		
	END
GO
-- Test trigger
-- INSERT INTO SALE (INVOICE_NO, CLIENT, MOTORCYCLE, SELLER) VALUES (4033, 306854700, 875506209, 696);
---------------------------------------------------

GO
CREATE TRIGGER GRANT_DISJOINT_OWNED ON OWNED_BIKE
AFTER INSERT
AS
	DECLARE @bike AS INT;
	DECLARE @exists_in_rent AS INT;
	DECLARE @exists_in_stock AS INT;
	SELECT @bike = FRAME_NO FROM inserted;
	SELECT @exists_in_rent = COUNT(*)  FROM RENTABLE_BIKE WHERE FRAME_NO = @bike;
	SELECT @exists_in_stock = COUNT(*) FROM STOCK_BIKE WHERE FRAME_NO = @bike;

	IF(@exists_in_rent > 0 OR @exists_in_stock > 0)
	BEGIN
		raiserror('Mota j� existente em RENT ou STOCK', 16, 1);
		ROLLBACK TRAN
	END
GO
-- Test Trigger
-- SELECT * FROM STOCK_BIKE
-- SELECT * FROM OWNED_BIKE
-- INSERT INTO OWNED_BIKE(FRAME_NO, LICENSE_PLATE, TOTAL_KM, B_OWNER) VALUES (128752417, 222222, 0, 814313356);
---------------------------------------------------

GO
CREATE TRIGGER GRANT_DISJOINT_RENT ON RENTABLE_BIKE
AFTER INSERT
AS
	DECLARE @bike AS INT;
	DECLARE @exists_in_owned AS INT;
	DECLARE @exists_in_stock AS INT;
	SELECT @bike = FRAME_NO FROM inserted;
	SELECT @exists_in_owned = COUNT(*)  FROM OWNED_BIKE WHERE FRAME_NO = @bike;
	SELECT @exists_in_stock = COUNT(*) FROM STOCK_BIKE WHERE FRAME_NO = @bike;

	IF(@exists_in_owned > 0 OR @exists_in_stock > 0)
	BEGIN
		raiserror('Mota j� existente em OWNED ou STOCK', 16, 1);
		ROLLBACK TRAN
	END
GO
---------------------------------------------------

CREATE TRIGGER GRANT_DISJOINT_STOCK ON STOCK_BIKE
AFTER INSERT
AS
	DECLARE @bike AS INT;
	DECLARE @exists_in_rent AS INT;
	DECLARE @exists_in_owned AS INT;
	SELECT @bike = FRAME_NO FROM inserted;
	SELECT @exists_in_rent = COUNT(*)  FROM RENTABLE_BIKE WHERE FRAME_NO = @bike;
	SELECT @exists_in_owned = COUNT(*) FROM OWNED_BIKE WHERE FRAME_NO = @bike;

	IF(@exists_in_rent > 0 OR @exists_in_owned > 0)
	BEGIN
		raiserror('Mota j� existente em RENT ou OWNED', 16, 1);
		ROLLBACK TRAN
	END
GO
---------------------------------------------------

CREATE TRIGGER GRANT_DISJOINT_SALESMAN ON SALESMAN
AFTER INSERT
AS
	DECLARE @id AS INT;
	DECLARE @exists AS INT;
	SELECT @id = NUMBER FROM inserted;
	SELECT @exists = COUNT(*) FROM MECHANIC WHERE NUMBER = @id;

	IF(@exists > 0)
	BEGIN
		raiserror('Funcion�rio j� existe e � um mec�nico', 16, 1);
		ROLLBACK TRAN
	END
GO
---------------------------------------------------

CREATE TRIGGER GRANT_DISJOINT_MECHANIC ON MECHANIC
AFTER INSERT
AS
	DECLARE @id AS INT;
	DECLARE @exists AS INT;
	SELECT @id = NUMBER FROM inserted;
	SELECT @exists = COUNT(*) FROM SALESMAN WHERE NUMBER = @id;

	IF(@exists > 0)
	BEGIN
		raiserror('Funcion�rio j� existe e � um vendedor', 16, 1);
		ROLLBACK TRAN
	END
GO
---------------------------------------------------