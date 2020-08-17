use bd;
CREATE SCHEMA ex51;

-- Delete Section
DROP TABLE ex51.Dependent;
DROP TABLE ex51.Works_on;
DROP TABLE ex51.Project;
DROP TABLE ex51.Dept_locations;
ALTER TABLE ex51.Employee
	DROP CONSTRAINT TRABALHA
DROP TABLE ex51.Department;
DROP TABLE ex51.Employee;

-- Init Section
CREATE TABLE ex51.Employee(
	Fname		VARCHAR(20),
	Minit		CHAR,
	LName		VARCHAR(20),
	Ssn			INT,
	Bdate		DATE,
	Address		VARCHAR(20),
	Sex			CHAR,
	Salary		INTEGER,
	Super_ssn	INT,
	Dno			INT,

	PRIMARY KEY (Ssn),
	FOREIGN KEY (Super_ssn) REFERENCES ex51.employee(Ssn)
);

CREATE TABLE ex51.Department(
	Dname			VARCHAR(20),
	Dnumber			INT,
	Mgr_ssn			INT,
	Mgr_start_date	DATE,

	PRIMARY KEY (Dnumber),
	FOREIGN KEY (Mgr_ssn) REFERENCES ex51.Employee(Ssn)
);

ALTER TABLE ex51.Employee
	ADD CONSTRAINT TRABALHA FOREIGN KEY (Dno) REFERENCES ex51.Department(Dnumber);

CREATE TABLE ex51.Dept_locations(
	Dnumber		INT,
	Dlocation	VARCHAR(20),
	
	PRIMARY KEY (Dnumber,Dlocation),
	FOREIGN KEY (Dnumber) REFERENCES ex51.Department(Dnumber)
);

CREATE TABLE ex51.Project(
	Pname		VARCHAR(20),
	Pnumber		INT,
	Plocation	VARCHAR(20),
	Dnum		INT,
	
	PRIMARY KEY (Pnumber),
	FOREIGN KEY (Dnum) REFERENCES ex51.Department(Dnumber)
);

CREATE TABLE ex51.Works_on(
	Essn		INT,
	Pno			INT,
	Hours		INT,
	
	PRIMARY KEY (Essn,Pno),
	FOREIGN KEY (Essn) REFERENCES ex51.Employee(Ssn),
	FOREIGN KEY (Pno) REFERENCES ex51.Project(Pnumber)
);

CREATE TABLE ex51.Dependent(
	Essn			INT,
	Dependent_name	VARCHAR(20),
	Sex				CHAR,
	Bdate			DATE,
	Relationship	VARCHAR(20),
	
	PRIMARY KEY (Essn,Dependent_name),
	FOREIGN KEY (Essn) REFERENCES ex51.employee(Ssn)
);


INSERT ex51.Department(Dname,Dnumber,Mgr_ssn,Mgr_start_date)
VALUES ('Investigacao',1,NULL ,'20100802')

INSERT ex51.Department(Dname,Dnumber,Mgr_ssn,Mgr_start_date)
VALUES ('Comercial',2,NULL,'20130516')


INSERT ex51.Department(Dname,Dnumber,Mgr_ssn,Mgr_start_date)
VALUES ('Logistica',3,NULL,'20130516')

INSERT ex51.Department(Dname,Dnumber,Mgr_ssn,Mgr_start_date)
VALUES ('Recursos Humanos',4,NULL,'20140402')

INSERT ex51.Department(Dname,Dnumber,Mgr_ssn,Mgr_start_date)
VALUES ('Desporto',5,NULL,NULL)


INSERT ex51.Employee (Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno)
VALUES ('Paula','A','Sousa',183623612,'20010811','Rua da FRENTE','F',1450.00,NULL,3);

INSERT ex51.Employee (Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno)
VALUES ('Carlos','D','Gomes',21312332 ,'20000101','Rua XPTO','M',1200.00,NULL,1);

INSERT ex51.Employee (Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno)
VALUES ('Juliana','A','Amaral',321233765,'19800811','Rua BZZZZ','F',1350.00,NULL,3);

INSERT ex51.Employee (Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno)
VALUES ('Maria','I','Pereira',342343434,'20010501','Rua JANOTA','F',1250.00,NULL,2);

INSERT ex51.Employee (Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno)
VALUES ('Joao','G','Costa',41124234 ,'20010101','Rua YGZ','M',1300.00,NULL,2);

INSERT ex51.Employee (Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno)
VALUES ('Ana','L','Silva',12652121 ,'19900303','Rua ZIG ZAG','F',1400.00,NULL,2);

INSERT INTO ex51.Project(Pname, Pnumber, Plocation, Dnum)
VALUES ('Aveiro Digital',1,'Aveiro',3),
 ('BD Open Day',2,'Espinho',2),
 ('Dicoogle',3,'Aveiro',3),
 ('GOPACS',4,'Aveiro',3);

 INSERT ex51.works_on(Essn, Pno, Hours)
 VALUES (183623612,1,20.0),
(183623612,3,10.0),
(21312332 ,1,20.0),
(321233765,1,25.0),
(342343434,1,20.0),
(342343434,4,25.0),
(41124234 ,2,20.0),
(41124234 ,3,30.0);

INSERT ex51.Dependent(Essn, Dependent_name, Sex, Bdate, Relationship)
VALUES 	(21312332 ,'Joana Costa','F','20080401', 'Filho'),
       	(21312332 ,'Maria Costa','F','19901005', 'Neto'),
        (21312332 ,'Rui Costa','M','20000804','Neto'),
        (321233765,'Filho Lindo','M','20010222','Filho'),
        (342343434,'Rosa Lima','F','20060311','Filho'),
        (41124234 ,'Ana Sousa','F','20070413','Neto'),
      	(41124234 ,'Gaspar Pinto','M','20060208','Sobrinho');

INSERT ex51.dept_locations(Dnumber, Dlocation)
VALUES (2, 'Aveiro'),
	   (3, 'Coimbra');

	