--CREATE DATABASE empresa;
--GO

USE
empresa
GO

CREATE SCHEMA ex51;
GO

--Deleting zone
--ALTER TABLE ex51.employee
--	DROP CONSTRAINT TRABALHA;
--DROP TABLE IF EXISTS ex51.Dependent;
--DROP TABLE IF EXISTS ex51.Works_on;
--DROP TABLE IF EXISTS ex51.Project;
--DROP TABLE IF EXISTS ex51.Dept_locations;
--DROP TABLE IF EXISTS ex51.Department;
--DROP TABLE IF EXISTS ex51.employee;
--DROP SCHEMA IF EXISTS ex51; 

-- Creating tables
CREATE TABLE ex51.Employee
(
    Fname     VARCHAR(20),
    Minit     CHAR,
    Lname     VARCHAR(20),
    Ssn       INT,
    Bdate     DATE,
    Address   VARCHAR(20),
    Sex       CHAR,
    Salary    INT,
    Super_ssn INT,
    Dno       INT,
    PRIMARY KEY (Ssn),
    FOREIGN KEY (Super_ssn) REFERENCES ex51.Employee (Ssn)
);

CREATE TABLE ex51.Department
(
    Dname          VARCHAR(20),
    Dnumber        INT,
    Mgr_ssn        INT,
    Mgr_start_date DATE,
    PRIMARY KEY (Dnumber),
    FOREIGN KEY (Mgr_ssn) REFERENCES ex51.Employee (Ssn)
);

CREATE TABLE ex51.Dept_locations
(
    Dnumber   INT,
    Dlocation VARCHAR(20),
    PRIMARY KEY (Dnumber, Dlocation),
    FOREIGN KEY (Dnumber) REFERENCES ex51.Department (Dnumber)
);

CREATE TABLE ex51.Project
(
    Pname     VARCHAR(20),
    Pnumber   INT,
    Plocation VARCHAR(20),
    Dnum      INT
        PRIMARY KEY(Pnumber),
    FOREIGN KEY (Dnum) REFERENCES ex51.Department (Dnumber)
);

CREATE TABLE ex51.Works_on
(
    Essn  INT,
    Pno   INT,
    Hours INT,
    PRIMARY KEY (Essn, Pno),
    FOREIGN KEY (Pno) REFERENCES ex51.Project (Pnumber),
    FOREIGN KEY (Essn) REFERENCES ex51.Employee (Ssn)
);

CREATE TABLE ex51.Dependent
(
    Essn           INT,
    Dependent_name VARCHAR(20),
    Sex            CHAR,
    Bdate          DATE,
    Relationship   VARCHAR(20),
    PRIMARY KEY (Essn, Dependent_name),
    FOREIGN KEY (Essn) REFERENCES ex51.Employee (Ssn)
);

-- Insert data
INSERT INTO ex51.Employee(Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno)
VALUES ('Paula', 'A', 'Sousa', 183623612, '2001-08-11', 'Rua da FREN111TE', 'F', 1450.00, NULL, 3),
       ('Carlos', 'D', 'Gomes', 21312332, '2000-01-01', 'Rua XPTO', 'M', 1200.00, NULL, 1),
       ('Juliana', 'A', 'Amaral', 321233765, '1980-08-11', 'Rua BZZZZ', 'F', 1350.00, NULL, 3),
       ('Maria', 'I', 'Pereira', 342343434, '2001-05-01', 'Rua JANOTA', 'F', 1250.00, 21312332, 2),
       ('Joao', 'G', 'Costa', 41124234, '2001-01-01', 'Rua YGZ', 'M', 1300.00, 21312332, 2),
       ('Ana', 'L', 'Silva', 12652121, '1990-03-03', 'Rua ZIG ZAG', 'F', 1400.00, 21312332, 2)
    INSERT
INTO ex51.Department(Dname, Dnumber, Mgr_ssn, Mgr_start_date)
VALUES ('Investigacao', 1, 21312332, '2010-08-02'),
    ('Comercial', 2, 321233765, '2013-05-16'),
    ('Logistica', 3, 41124234, '2013-05-16'),
    ('Recursos Humanos', 4, 12652121, '2014-04-02'),
    ('Desporto', 5, NULL, NULL)

ALTER TABLE ex51.Employee
    ADD CONSTRAINT TRABALHA FOREIGN KEY (Dno) REFERENCES ex51.Department (Dnumber);

INSERT INTO ex51.Dept_locations(Dnumber, Dlocation)
VALUES (2, 'Aveiro'),
       (3, 'Coimbra')
    INSERT
INTO ex51.Project(Pname, Pnumber, Plocation, Dnum)
VALUES ('Aveiro Digital', 1, 'Aveiro', 3),
    ('BD Open Day', 2, 'Espinho', 2),
    ('Dicoogle', 3, 'Aveiro', 3),
    ('GOPACS', 4, 'Aveiro', 3)

INSERT INTO ex51.Works_on(Essn, Pno, Hours)
VALUES (183623612, 1, 20.0),
    (183623612, 3, 10.0),
    (21312332, 1, 20.0),
    (321233765, 1, 25.0),
    (342343434, 1, 20.0),
    (342343434, 4, 25.0),
    (41124234, 2, 20.0),
    (41124234, 3, 30.0)

INSERT INTO ex51.Dependent(Essn, Dependent_name, Sex, Bdate, Relationship)
VALUES (21312332, 'Joana Costa', 'F', '2008-04-01', 'Filho'),
    (21312332, 'Maria Costa', 'F', '1990-10-05', 'Neto'),
    (21312332, 'Rui Costa', 'M', '2000-08-04', 'Neto'),
    (321233765, 'Filho Lindo', 'M', '2001-02-22', 'Filho'),
    (342343434, 'Rosa Lima', 'F', '2006-03-11', 'Filho'),
    (41124234, 'Ana Sousa', 'F', '2007-04-13', 'Neto'),
    (41124234, 'Gaspar Pinto', 'M', '2006-02-08', 'Sobrinho')