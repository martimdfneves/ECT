CREATE SCHEMA ex52;
GO

-- Delete Section
--DROP TABLE IF EXISTS ex52.Conteudo;
--DROP TABLE IF EXISTS ex52.Produto;
--DROP TABLE IF EXISTS ex52.Encomenda;
--DROP TABLE IF EXISTS ex52.Fornecedor;
--DROP TABLE IF EXISTS ex52.Tipo_Fornecedor;
--DROP SCHEMA IF EXISTS ex52;

-- Init Section
CREATE TABLE ex52.Produto(
	CODIGO		INT,
	NOME		VARCHAR(20),
	PRECO		INT				CHECK(PRECO >= 0),
	IVA			INT				CHECK(IVA >= 0 AND IVA <= 100),
	STOCK		INT				CHECK(STOCK > 0),
	
	PRIMARY KEY(CODIGO)
);

CREATE TABLE ex52.Conteudo(
	PRODUTO			INT,
	ENCOMENDA		INT,
	UNIDADES		INT			CHECK(UNIDADES >= 0),

	PRIMARY KEY(PRODUTO, ENCOMENDA),
	FOREIGN KEY(PRODUTO) REFERENCES ex52.Produto(CODIGO)
);


CREATE TABLE ex52.Encomenda(
	NUMERO			INT,
	DATA_ENCOMENDA	DATE,
	FORNECEDOR		INT			NOT NULL,

	PRIMARY KEY(NUMERO)
);

CREATE TABLE ex52.Fornecedor(
	NIF				INT,
	TIPO			INT			NOT NULL,
	NOME			VARCHAR(20),
	FAX				INT,
	ENDERECO		VARCHAR(20),
	CODIGO			INT,
	PAGAMENTO		INT			CHECK(PAGAMENTO > 0),

	PRIMARY KEY(NIF)
);

CREATE TABLE ex52.Tipo_Fornecedor(
	CODIGO		INT,
	DESIGN		VARCHAR(20),

	PRIMARY KEY(CODIGO)
);

ALTER TABLE ex52.Conteudo
	ADD CONSTRAINT CNTDENCOMENDA FOREIGN KEY(ENCOMENDA) REFERENCES ex52.Encomenda(NUMERO);
	
ALTER TABLE ex52.Encomenda
	ADD CONSTRAINT NIFENCOMENDA FOREIGN KEY(FORNECEDOR) REFERENCES ex52.Fornecedor(NIF);

ALTER TABLE ex52.Fornecedor
	ADD CONSTRAINT TIPOFORNECEDOR FOREIGN KEY(TIPO) REFERENCES ex52.Tipo_Fornecedor(CODIGO);