CREATE DATABASE Comprimidos;
GO

USE Comprimidos;
GO

CREATE SCHEMA ex53a;
GO

DROP TABLE IF EXISTS ex53a.Venda;
DROP TABLE IF EXISTS ex53a.Farmaco;
DROP TABLE IF EXISTS ex53a.Prescricao;
DROP TABLE IF EXISTS ex53a.Medico;
DROP TABLE IF EXISTS ex53a.Paciente;
DROP TABLE IF EXISTS ex53a.Farmacia;
DROP TABLE IF EXISTS ex53a.Farmaceutica;
--DROP SCHEMA IF EXISTS ex53a;
--DROP DATABASE IF EXISTS Comprimidos;

CREATE TABLE ex53a.Medico(
	SNS				INT,
	NOME			VARCHAR(30)		NOT NULL,
	ESPECIALIDADE	VARCHAR(20),

	PRIMARY KEY(SNS)
);

CREATE TABLE ex53a.Paciente(
	NR_UTENTE		INT,
	NOME			VARCHAR(30)		NOT NULL,
	DATA_NASC		DATE			NOT NULL,
	ENDERECO		VARCHAR(30),

	PRIMARY KEY(NR_UTENTE)
);

CREATE TABLE ex53a.Farmacia(
	NOME			VARCHAR(20),
	TELEFONE		VARCHAR(9),
	ENDERECO		VARCHAR(30)		NOT NULL,

	PRIMARY KEY(NOME)
);

CREATE TABLE ex53a.Farmaceutica(
	NUMERO			INT,
	NOME			VARCHAR(20)		NOT NULL,
	ENDERECO		VARCHAR(40)		NOT NULL,
	
	PRIMARY KEY(NUMERO)
);

CREATE TABLE ex53a.Farmaco(
	NUMERO			INT,
	NOME			VARCHAR(20)		NOT NULL,
	FORMULA			VARCHAR(20)		NOT NULL,

	PRIMARY KEY(NUMERO, NOME),
	FOREIGN KEY(NUMERO) REFERENCES ex53a.Farmaceutica(NUMERO)
);

CREATE TABLE ex53a.Prescricao(
	NUMERO			INT,
	PACIENTE		INT				NOT NULL,
	MEDICO			INT				NOT NULL,
	FARMACIA		VARCHAR(20),
	DATA_PRESC		DATE,

	PRIMARY KEY(NUMERO),
	FOREIGN KEY(MEDICO) REFERENCES ex53a.Medico(SNS),
	FOREIGN KEY(PACIENTE) REFERENCES ex53a.Paciente(NR_UTENTE),
	FOREIGN KEY(FARMACIA) REFERENCES ex53a.Farmacia(NOME)
);

CREATE TABLE ex53a.Venda(
	NUMPRESC		INT				NOT NULL,
	NUMREGFARM		INT				NOT NULL,
	NOMEFARMACO		VARCHAR(20)		NOT NULL,

	PRIMARY KEY(NUMPRESC,NOMEFARMACO),
	FOREIGN KEY(NUMPRESC) REFERENCES ex53a.Prescricao(NUMERO),
	FOREIGN KEY(NUMREGFARM,NOMEFARMACO) REFERENCES ex53a.Farmaco(NUMERO,NOME)
);

INSERT INTO ex53a.Medico(SNS,NOME,ESPECIALIDADE)
VALUES (101,'Joao Pires Lima', 'Cardiologia'),
	   (102,'Manuel Jose Rosa', 'Cardiologia'),
	   (103,'Rui Luis Caraca', 'Pneumologia'),
	   (104,'Sofia Sousa Silva', 'Radiologia'),
	   (105,'Ana Barbosa', 'Neurologia');

INSERT INTO ex53a.Paciente(NR_UTENTE,NOME,DATA_NASC,ENDERECO)
VALUES (1,'Renato Manuel Cavaco','1980-01-03','Rua Nova do Pilar 35'),
	   (2,'Paula Vasco Silva','1972-10-30','Rua Direita 43'),
	   (3,'Ines Couto Souto','1985-05-12','Rua de Cima 144'),
	   (4,'Rui Moreira Porto','1970-12-12','Rua Zig Zag 235'),
	   (5,'Manuel Zeferico Polaco','1990-06-05','Rua da Baira Rio 1135');

INSERT INTO ex53a.Farmacia(NOME,TELEFONE,ENDERECO)
VALUES ('Farmacia BelaVista','221234567','Avenida Principal 973'),
	   ('Farmacia Central','234370500','Avenida da Liberdade 33'),
	   ('Farmacia Peixoto','234375111','Largo da Vila 523'),
	   ('Farmacia Vitalis','229876543','Rua Visconde Salgado 263');

INSERT INTO ex53a.Farmaceutica(NUMERO,NOME,ENDERECO)
VALUES (905,'Roche','Estrada Nacional 249'),
	   (906,'Bayer','Rua da Quinta do Pinheiro 5'),
	   (907,'Pfizer','Empreendimento Lagoas Park - Edificio 7'),
	   (908,'Merck', 'Alameda Fernão Lopes 12');

INSERT INTO ex53a.Farmaco(NUMERO,NOME,FORMULA)
VALUES (905,'Boa Saude em 3 Dias','XZT9'),
	   (906,'Voltaren Spray','PLTZ32'),
	   (906,'Xelopironi 350','FRR-34'),
	   (906,'Gucolan 1000','VFR-750'),
	   (907,'GEROaero Rapid','DDFS-XEN9'),
	   (908,'Aspirina 1000','BIOZZ02');

INSERT INTO ex53a.Prescricao(NUMERO,PACIENTE,MEDICO,FARMACIA,DATA_PRESC)
VALUES (10001,1,105,'Farmacia Central','2015-03-03'),
	   (10002,1,105,NULL,NULL),
	   (10003,3,102,'Farmacia Central','2015-01-17'),
	   (10004,3,101,'Farmacia BelaVista','2015-02-09'),
	   (10005,3,102,'Farmacia Central','2015-01-17'),
	   (10006,4,102,'Farmacia Vitalis','2015-02-22'),
	   (10007,5,103,NULL,NULL),
	   (10008,1,103,'Farmacia Central','2015-01-02'),
	   (10009,3,102,'Farmacia Peixoto','2015-02-02');

INSERT INTO ex53a.Venda(NUMPRESC,NUMREGFARM,NOMEFARMACO)
VALUES (10001,905,'Boa Saude em 3 Dias'),
	   (10002,907,'GEROaero Rapid'),
	   (10003,906,'Voltaren Spray'),
	   (10003,906,'Xelopironi 350'),
	   (10003,908,'Aspirina 1000'),
	   (10004,905,'Boa Saude em 3 Dias'),
	   (10004,908,'Aspirina 1000'),
	   (10005,906,'Voltaren Spray'),
	   (10006,905,'Boa Saude em 3 Dias'),
	   (10006,906,'Voltaren Spray'),
	   (10006,906,'Xelopironi 350'),
	   (10006,908,'Aspirina 1000'),
	   (10007,906,'Voltaren Spray'),
	   (10008,905,'Boa Saude em 3 Dias'),
	   (10008,908,'Aspirina 1000'),
	   (10009,905,'Boa Saude em 3 Dias'),
	   (10009,906,'Voltaren Spray'),
	   (10009,908,'Aspirina 1000');

SELECT * FROM ex53a.Medico; -- TEM VALORES
SELECT * FROM ex53a.Paciente; -- TEM VALORES
SELECT * FROM ex53a.Farmacia; -- TEM VALORES 
SELECT * FROM ex53a.Farmaceutica; -- TEM VALORES
SELECT * FROM ex53a.Farmaco; -- TEM VALORES
SELECT * FROM ex53a.Prescricao; -- TEM VALORES
SELECT * FROM ex53a.Venda; -- TEM VALORES