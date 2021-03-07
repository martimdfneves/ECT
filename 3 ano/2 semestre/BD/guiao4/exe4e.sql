CREATE SCHEMA ex4e;
GO

--DROP TABLE IF EXISTS ex4e.Escreve;
--DROP TABLE IF EXISTS ex4e.Nao_Estudante;
--DROP TABLE IF EXISTS ex4e.Estudante;
--DROP TABLE IF EXISTS ex4e.Participante;
--DROP TABLE IF EXISTS ex4e.Autor;
--DROP TABLE IF EXISTS ex4e.Pessoa;
--DROP TABLE IF EXISTS ex4e.Artigo;
--DROP TABLE IF EXISTS ex4e.Instituicao;
--DROP SCHEMA IF EXISTS ex4e;

CREATE TABLE ex4e.Instituicao(
	NOME				VARCHAR(20),
	ENDERECO			VARCHAR(20),

	PRIMARY KEY(NOME)
);

CREATE TABLE ex4e.Artigo(
	TITULO				VARCHAR(20),
	NR_REGISTO			INT,

	PRIMARY KEY(NR_REGISTO)
);

CREATE TABLE ex4e.Pessoa(
	MAIL				VARCHAR(20),
	NOME				VARCHAR(20),
	INSTITUICAO			VARCHAR(20)		NOT NULL,

	PRIMARY KEY(MAIL),
	FOREIGN KEY(INSTITUICAO) REFERENCES ex4e.Instituicao(NOME)
);

CREATE TABLE ex4e.Autor(
	NOME				VARCHAR(20)	NOT NULL,
	MAIL				VARCHAR(20)	NOT NULL,
	INSTITUICAO			VARCHAR(20),

	PRIMARY KEY(MAIL),
	FOREIGN KEY(MAIL) REFERENCES ex4e.Pessoa(MAIL)
);

CREATE TABLE ex4e.Participante(
	NOME				VARCHAR(20),
	MORADA				VARCHAR(20),
	MAIL				VARCHAR(20),
	INSTITUICAO			VARCHAR(20),
	DATA_INSC			DATE,

	PRIMARY KEY(MAIL),
	FOREIGN KEY(MAIL) REFERENCES ex4e.Pessoa(MAIL)
);

CREATE TABLE ex4e.Estudante(
	MAIL				VARCHAR(20),
	COMPROVATIVO		VARCHAR(20),

	PRIMARY KEY(MAIL),
	FOREIGN KEY(MAIL) REFERENCES ex4e.Pessoa(MAIL)
);

CREATE TABLE ex4e.Nao_Estudante(
	MAIL				VARCHAR(20),
	REF					INT,

	PRIMARY KEY(MAIL),
	FOREIGN KEY(MAIL) REFERENCES ex4e.Pessoa(MAIL)
);

CREATE TABLE ex4e.Escreve(
	AUTOR			VARCHAR(20),
	ARTIGO			INT,

	PRIMARY KEY(AUTOR, ARTIGO),
	FOREIGN KEY(AUTOR) REFERENCES ex4e.Autor(MAIL),
	FOREIGN KEY(ARTIGO) REFERENCES ex4e.Artigo(NR_REGISTO)
);