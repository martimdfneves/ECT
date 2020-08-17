use AULASBD;
-- Guiao 9
-- a)
GO
-- CREATE
ALTER PROC DELETE_ALL_FUNC @emp_ssn INT
AS
	UPDATE ex51.Employee SET Super_ssn=NULL WHERE Super_ssn=@emp_ssn
	DELETE FROM ex51.Employee WHERE @emp_ssn=Ssn
	DELETE FROM ex51.Works_on WHERE @emp_ssn=Essn
	DELETE FROM ex51.Dependent WHERE @emp_ssn=Essn
	UPDATE ex51.Department SET Mgr_ssn=NULL WHERE Mgr_ssn=@emp_ssn

EXEC DELETE_ALL_FUNC @emp_ssn=321233765
GO
-- b)
-- CREATE
ALTER PROC GESTORES (@OLDEST_MGR INT OUTPUT, @TOTAL_YEARS INT OUTPUT)
AS

	SELECT Ssn, Dname, Fname, Lname
	FROM ex51.Employee JOIN ex51.Department ON ex51.Employee.Ssn=ex51.Department.Mgr_Ssn
	ORDER BY  Mgr_Start_Date DESC

	SELECT @OLDEST_MGR=Ssn, @TOTAL_YEARS=DateDiff(year,Mgr_Start_Date, GETDATE())
	FROM ex51.Employee JOIN ex51.Department ON ex51.Employee.Ssn=ex51.Department.Mgr_Ssn
	ORDER BY  Mgr_Start_Date DESC

DECLARE @mgr INT; 
DECLARE @years INT;
EXEC GESTORES @mgr OUTPUT, @years OUTPUT
PRINT @mgr;
PRINT @years;

-- c)
GO
CREATE TRIGGER onemgr ON ex51.Department
AFTER INSERT, UPDATE
AS
    DECLARE @manager as INT
    DECLARE @count AS INT
    SELECT @manager = mgr_ssn FROM inserted;
    SELECT @count=count(mgr_ssn) FROM department WHERE mgr_ssn=@manager;
    print(@count)
    IF @count > 1
        BEGIN
            RAISERROR ('Already a manager',16,1);
            ROLLBACK TRAN;
        END
    ELSE
        PRINT 'New manager.';
GO

-- d) Instead of pois evitamos rollback nos casos em que alteramos os dados

CREATE Trigger NO_HIGHER_PAYMENT ON ex51.Employee
INSTEAD OF INSERT, UPDATE
AS
BEGIN

	DECLARE @mgr_salary as INT;
	DECLARE @added_salary as INT;
	DECLARE @mgr as INT;
	SELECT  @added_salary=Salary, @mgr=Super_ssn FROM inserted;

	SELECT @mgr_salary=m.Salary 
	FROM ((ex51.Employee as e JOIN ex51.Department ON e.Dno=Dnumber)
	JOIN ex51.Employee as m ON Mgr_ssn=m.Ssn);

	DECLARE @update as INT;
	SELECT @update=COUNT(*) FROM deleted;
		

	-- insert
	IF(@update = 0)
	BEGIN
		IF (@mgr_salary < @added_salary)
		BEGIN
			INSERT INTO ex51.Employee (Fname, Minit, Lname, Ssn, Bdate, [Address], Sex, Salary, Super_ssn, Dno)
			SELECT Fname, Minit, Lname, Ssn, Bdate, [Address], Sex, @mgr_salary-1, Super_Ssn, Dno 
			FROM inserted;
		END
		ELSE
		BEGIN
			INSERT INTO ex51.Employee SELECT * FROM inserted;
		END
	END
	-- update
	ELSE
	BEGIN
		IF (@mgr_salary < @added_salary)
		BEGIN
			UPDATE Ex51.Employee
			SET Fname=i.Fname, Minit=i.Minit, Lname=i.Lname,
				Ssn=i.Ssn, Bdate=i.Bdate, [Address]=i.[Address],
				Sex=i.Sex, Salary=@mgr_salary-1, Super_ssn=i.Super_ssn,
				Dno=i.Dno
				FROM Ex51.Employee
				INNER JOIN inserted AS i ON Ex51.Employee.Ssn=i.Ssn;

		END
		ELSE
		BEGIN
			UPDATE Ex51.Employee
				SET Fname=i.Fname, Minit=i.Minit, Lname=i.Lname,
					Ssn=i.Ssn, Bdate=i.Bdate, [Address]=i.[Address],
					Sex=i.Sex, Salary=i.Salary, Super_ssn=i.Super_ssn,
					Dno=i.Dno
					FROM Ex51.Employee
					INNER JOIN inserted AS i ON Ex51.Employee.Ssn=i.Ssn;		
		END
	END
END
GO
-- To test queries, inserts and updates.
SELECT * FROM ex51.Employee;
INSERT INTO ex51.Employee VALUES ('Ramalho', 'A', 'Foitas', 123123123, GETDATE(), 'Rua ramalho oliveira', 'M', 800, NULL, 2);
UPDATE ex51.Employee 
SET Salary=2000
WHERE Fname='Ramalho'
DELETE FROM ex51.Employee WHERE Ssn=123123123
------

-- e)
GO
CREATE FUNCTION ex51.FUNC_PROJ_LOC (@ssn INT) returns @table Table (Pname varchar(30), Plocation varchar(30))
AS
    BEGIN
        INSERT @table
            SELECT Pname, Plocation 
            FROM employee LEFT OUTER JOIN works_on ON Ssn=Essn JOIN project ON pno=pnumber
            WHERE Ssn = @ssn;
        RETURN;
    END;
GO


-- f)
GO
CREATE FUNCTION ex51.FUNC_VENC_ALTO(@dno INT) returns @table Table
											  (Ssn INT, Fname varchar(30), Lname varchar(30),
											  Salary INT)
AS
BEGIN
	DECLARE @avg_dep_salary AS INT
	SELECT @avg_dep_salary=AVG(Salary) FROM ex51.Employee WHERE Dno=@dno

	INSERT @table SELECT Ssn, Fname, Lname, Salary 
		   FROM ex51.Employee 
		   WHERE Salary>@avg_dep_salary AND Dno=@dno;
	RETURN;
END
GO
SELECT * FROM ex51.FUNC_VENC_ALTO(2)


-- g)

GO
CREATE FUNCTION ex51.DEP_PROJ_BUDGET (@dnum INT) returns @table Table (Pname varchar(30), Pnumber INT, Plocation varchar(30), Budget FLOAT, Total FLOAT)
AS
    BEGIN
        DECLARE C CURSOR
        FOR
            SELECT Pname, Pnumber, Plocation, SUM((Salary*1.0*Hours)/40) AS Budget 
            FROM project JOIN works_on
            ON Pnumber=Pno
            JOIN employee
            ON Essn=Ssn
            WHERE Dnum=@dnum
            GROUP BY Pnumber, Pname, Plocation;
 
        OPEN C;
        DECLARE @pname as varchar(30), @pnumber as INT, @plocation as varchar(30), @budget as float, @total as FLOAT;
        FETCH C INTO @pname, @pnumber, @plocation, @budget;
        SELECT @total=0;
        WHILE @@FETCH_STATUS = 0
            BEGIN
                SET @total += @budget;
                INSERT INTO @table VALUES (@pname, @pnumber, @plocation, @budget, @total);
                FETCH C INTO @pname, @pnumber, @plocation, @budget;
            END;
        CLOSE C;
        DEALLOCATE C;
    RETURN;
    END;
 
    SELECT * FROM ex51.DEP_PROJ_BUDGET(3)

GO
-- h)
-- com o trigger instead of conseguimos
-- eliminar todas as dependências do departamento antes de 
-- o eliminar efetivamente
CREATE TRIGGER DEP_DELETED ON ex51.Department
INSTEAD OF DELETE
AS
	DECLARE @deleted_dno AS INT
	SELECT @deleted_dno = Dnumber FROM deleted;
	-- create the table if it doesnt exist
	IF(NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.TABLES 
		WHERE TABLE_SCHEMA='ex51' AND TABLE_NAME='department_deleted'))
	BEGIN
	CREATE TABLE ex51.department_deleted(Dname varchar(30), Dnumber INT,
									     Mgr_Ssn INT, Mgr_Start_Date Date,
										 PRIMARY KEY(Dnumber),
										 FOREIGN KEY(Mgr_Ssn) REFERENCES ex51.Employee(Ssn));
	END

	DELETE FROM ex51.Project WHERE Dnum=@deleted_dno;
	UPDATE ex51.Employee SET Dno=NULL WHERE Dno=@deleted_dno;
	INSERT ex51.department_deleted SELECT * FROM deleted;
	DELETE FROM ex51.Department WHERE Dnumber=@deleted_dno;

GO
-- 21312332
-- Testing the trigger
INSERT ex51.Department(Dname, Dnumber, Mgr_Ssn, Mgr_Start_Date)
VALUES ('outro7', 7, 21312332, getdate());
DELETE FROM ex51.Department WHERE Dnumber=7
SELECT * FROM ex51.department_deleted;

-- i)
/*

Stored Procedures 
	- não é necessário recompilar código sempre que o procedimento é executado
	- podem ter argumentos de entrada e retornar valores
	- podem alterar a bd com inserts, updates e deletes
	- suportam blocos de try... catch
	- são mais utilizados em casos em que é necessário alterar a bd

UDF's
	- retornam sempre um valor, mas este pode ser uma tabela
	- se for necessário retornar uma tabela, utiliza-se UDF's


