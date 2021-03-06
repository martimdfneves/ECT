CREATE SCHEMA ex4d;
GO

--DROP TABLE IF EXISTS ex4d.Venda;
--DROP TABLE IF EXISTS ex4d.Farmacia;
--DROP TABLE IF EXISTS ex4d.Farmaco;
--DROP TABLE IF EXISTS ex4d.Farmaceutica;
--DROP TABLE IF EXISTS ex4d.Prescricao;
--DROP TABLE IF EXISTS ex4d.Paciente;
--DROP TABLE IF EXISTS ex4d.Medico;
--DROP SCHEMA IF EXISTS ex4d;

CREATE TABLE ex4d.Medico(
	NUM					INT,
	NOME				VARCHAR(20)		NOT NULL,
	ESPECIALIDADE		VARCHAR(20),

	PRIMARY KEY(NUM)
);

CREATE TABLE ex4d.Paciente(
	NR_UTENTE			INT,
	NOME				VARCHAR(20)		NOT NULL,
	DATA_NAS			DATE			NOT NULL,
	ENDERECO			VARCHAR(20),

	PRIMARY KEY(NR_UTENTE)
);

CREATE TABLE ex4d.Prescricao(
	NUMERO				INT,
	MEDICO				INT				NOT NULL,
	PACIENTE			INT				NOT NULL,

	PRIMARY KEY(NUMERO),
	FOREIGN KEY(MEDICO) REFERENCES ex4d.Medico(NUM),
	FOREIGN KEY(PACIENTE) REFERENCES ex4d.Paciente(NR_UTENTE)
);

CREATE TABLE ex4d.Farmaceutica (
	NUMERO				INT,
	NOME				VARCHAR(20)	NOT NULL,
	ENDERECO			VARCHAR(20)	NOT NULL,
	TELEFONE			VARCHAR(9),

	PRIMARY KEY(NUMERO)
);

CREATE TABLE ex4d.Farmaco(
	NUMERO				INT,
	NOME_COM			VARCHAR(20)		NOT NULL,
	FORMULA				VARCHAR(20)		NOT NULL,
	PRESCRICAO			INT,

	PRIMARY KEY(NUMERO,NOME_COM),
	FOREIGN KEY(NUMERO) REFERENCES ex4d.Farmaceutica(NUMERO),
	FOREIGN KEY(PRESCRICAO) REFERENCES ex4d.Prescricao(NUMERO)
);

CREATE TABLE ex4d.Venda(
	NOME_FARMACO		VARCHAR(20),
	NR_FARMACEUTICA		INT,
	FARMACIA			VARCHAR(20),

	PRIMARY KEY(NR_FARMACEUTICA, NOME_FARMACO, FARMACIA),
	FOREIGN KEY(NR_FARMACEUTICA, NOME_FARMACO) REFERENCES ex4d.Farmaco(NUMERO, NOME_COM),
);

CREATE TABLE ex4d.Farmacia(
	NIF					INT,
	NOME				VARCHAR(20),
	ENDERECO			VARCHAR(20)		NOT NULL,
	TELEFONE			VARCHAR(20),

	PRIMARY KEY(NIF)
);

