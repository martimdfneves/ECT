--CREATE DATABASE LojaDesporto;
--GO

--DELETING ZONE
--ALTER TABLE Projeto.Compra
--	DROP CONSTRAINT IF EXISTS NUMFUNC_COMPRA;
--ALTER TABLE Projeto.Devolucao
--	DROP CONSTRAINT IF EXISTS NUMFUNC_DEVOLUCAO;
--ALTER TABLE Projeto.Compra
--	DROP CONSTRAINT IF EXISTS NIF_COMPRA;
--ALTER TABLE Projeto.Devolucao
--	DROP CONSTRAINT IF EXISTS NIF_DEVOLUCAO;
--ALTER TABLE Projeto.Artigo_Comprado
--	DROP CONSTRAINT IF EXISTS NUM_COMPRA;
--ALTER TABLE Projeto.Artigo_Devolvido
--	DROP CONSTRAINT IF EXISTS ID_DEVOLUCAO;
--ALTER TABLE Projeto.Artigo_Loja
--	DROP CONSTRAINT IF EXISTS NUM_LOJA_ARTIGO;
--ALTER TABLE Projeto.Armazem
--	DROP CONSTRAINT IF EXISTS NUM_LOJA_ARMAZEM;
--ALTER TABLE Projeto.Artigo_Transporte
--	DROP CONSTRAINT IF EXISTS ID_TRANSPORTE;
--ALTER TABLE Projeto.Artigo_Armazem
--	DROP CONSTRAINT IF EXISTS ID_ARMAZEM;
--DROP TABLE IF EXISTS Projeto.Funcionario;
--DROP TABLE IF EXISTS Projeto.Cliente;
--DROP TABLE IF EXISTS Projeto.Compra;
--DROP TABLE IF EXISTS Pojeto.Artigo_Comprado;
--DROP TABLE IF EXISTS Projeto.Devolucao;
--DROP TABLE IF EXISTS Projeto.Artigo_Devolvido;
--DROP TABLE IF EXISTS Projeto.Loja;
--DROP TABLE IF EXISTS Projeto.Artigo_Loja;
--DROP TABLE IF EXISTS Projeto.Transporte;
--DROP TABLE IF EXISTS Projeto.Artigo_Transporte;
--DROP TABLE IF EXISTS Projeto.Armazem; 
--DROP TABLE IF EXISTS Projeto.Artigo_Armazem;
--DROP TABLE IF EXISTS Projeto.Artigo;
--DROP SCHEMA IF EXISTS Projeto;
--DROP DATABASE IF EXISTS LojaDesporto;

CREATE SCHEMA Projeto;
GO

--CREATING TABLES
CREATE TABLE Projeto.Artigo(
	Codigo			INT				CHECK(Codigo > 0),
	Preco			DECIMAL(5,2)	CHECK(Preco > 0),	-- 5 Números no total com 2 digitos de precisão decimal 
	Nome			VARCHAR(40)		NOT NULL,
	Categoria		VARCHAR(20)		NOT NULL,			-- Vestuário, Calçado e Acessórios
	PRIMARY KEY(Codigo)
);

CREATE TABLE Projeto.Artigo_Armazem(
	Codigo			INT				CHECK(Codigo > 0),
	IDArmazem		INT				CHECK(IDArmazem > 0),
	QuantArtigos	INT				CHECK(QuantArtigos > 0),
	PRIMARY KEY(Codigo, IDArmazem),
	FOREIGN KEY(Codigo) REFERENCES Projeto.Artigo(Codigo)
);

CREATE TABLE Projeto.Armazem(
	IDArmazem		INT				CHECK(IDArmazem > 0),
	Capacidade		INT				CHECK(Capacidade > 0),
	NumLoja			INT				CHECK(NumLoja > 0),
	PRIMARY KEY(IDArmazem)
);

CREATE TABLE Projeto.Artigo_Transporte(
	Codigo			INT				CHECK(Codigo > 0),
	IDTransporte	INT				CHECK(IDTransporte > 0),
	QuantArtigos	INT				CHECK(QuantArtigos > 0),
	PRIMARY KEY(Codigo, IDTransporte),
	FOREIGN KEY(Codigo) REFERENCES Projeto.Artigo(Codigo)
);

CREATE TABLE Projeto.Transporte(
	IDTransporte	INT				CHECK(IDTransporte > 0),
	Data			DATE			NOT NULL,
	Destino			VARCHAR(40)		NOT NULL,
	PRIMARY KEY(IDTransporte)
);

CREATE TABLE Projeto.Artigo_Loja(
	Codigo			INT				CHECK(Codigo > 0),
	NumLoja			INT				CHECK(NumLoja > 0),
	QuantArtigos	INT				CHECK(QuantArtigos > 0),
	PRIMARY KEY(Codigo, NumLoja),
	FOREIGN KEY(Codigo) REFERENCES Projeto.Artigo(Codigo)
);

CREATE TABLE Projeto.Loja(
	NumLoja			INT				CHECK(NumLoja > 0),
	Nome			VARCHAR(30)		NOT NULL,
	Localizacao		VARCHAR(20)		NOT NULL,
	PRIMARY KEY(NumLoja)
);

CREATE TABLE Projeto.Artigo_Devolvido(
	Codigo			INT				CHECK(Codigo > 0),
	IDDevolucao		INT				CHECK(IDDevolucao > 0),
	QuantArtigos	INT				CHECK(QuantArtigos > 0),
	PRIMARY KEY(Codigo, IDDevolucao),
	FOREIGN KEY(Codigo) REFERENCES Projeto.Artigo(Codigo) 
);

CREATE TABLE Projeto.Devolucao(
	IDDevolucao		INT				CHECK(IDDevolucao > 0),
	Data			DATE			NOT NULL,	
	Montante		DECIMAL(5,2)	CHECK(Montante > 0),	-- 5 Números no total com 2 digitos de precisão decimal 
	NIF				BIGINT			CHECK(NIF > 0),
	NumFunc			INT				CHECK(NumFunc > 0),
	PRIMARY KEY(IDDevolucao) 
);

CREATE TABLE Projeto.Artigo_Comprado(
	Codigo			INT				CHECK(Codigo > 0),
	NumCompra		INT				CHECK(NumCompra > 0),
	QuantArtigos	INT				CHECK(QuantArtigos > 0),
	PRIMARY KEY(Codigo, NumCompra), 
	FOREIGN KEY(Codigo) REFERENCES Projeto.Artigo(Codigo)
);

CREATE TABLE Projeto.Compra(
	NumCompra		INT				CHECK(NumCompra > 0),
	Data			DATE			NOT NULL,	
	Montante		DECIMAL(5,2)	CHECK(Montante > 0),	-- 5 Números no total com 2 digitos de precisão decimal 
	NIF				BIGINT			CHECK(NIF > 0),
	NumFunc			INT				CHECK(NumFunc > 0),
	PRIMARY KEY(NumCompra) 
);

CREATE TABLE Projeto.Cliente(
	NIF				BIGINT			CHECK(NIF > 0),
	Morada			VARCHAR(40),
	Nome			VARCHAR(20)		NOT NULL,
	NumTelem		BIGINT			CHECK(NumTelem > 0),
	PRIMARY KEY(NIF)
);

CREATE TABLE Projeto.Funcionario(
	NumFunc			INT				CHECK(NumFunc > 0),
	Morada			VARCHAR(40)		NOT NULL,
	Nome			VARCHAR(20)		NOT NULL,
	NumTelem		BIGINT			CHECK(NumTelem > 0),
	NumLoja			INT				CHECK(NumLoja > 0),
	PRIMARY KEY(NumFunc),
	FOREIGN KEY(NumLoja) REFERENCES Projeto.Loja(NumLoja)
);

--INSERT DATA
INSERT INTO Projeto.Artigo(Codigo, Preco, Nome, Categoria)
VALUES	(156428, 69.99, 'Camisola Equipamento SLB', 'Vestuário'),
		(245875, 59.99, 'Camisola Equipamento SCP', 'Vestuário'),
		(114526, 19.99, 'Pulseira Desportiva', 'Acessórios'),
		(177895, 9.99, 'Tapete ginástica/yoga', 'Acessórios'),
		(103568, 1.99, 'Touca natação', 'Acessórios'),
		(165856, 89.99, 'Raquete Ténis Head Ig Challenge Pro', 'Acessórios'),
		(187853, 9.99, 'Bolas Ténis Dunlop Bipk4', 'Acessórios'),
		(130055, 19.99, 'Calções Puma', 'Vestuário'),
		(172733, 59.99, 'Camisola Equipamento FCP', 'Vestuário'),
		(291208, 29.99, 'Relógio Silver Portugal', 'Acessório'),
		(827127, 14.99, 'Boné Adidas a3', 'Acessório'),
		(758763, 15.49, 'Casaco de Ciclismo', 'Vestuário'),
		(172630, 4.99, 'Gorro Star Wars', 'Acessórios'),
		(198932, 189.99, 'Halteres 20 Kg Bodytone', 'Acessórios'),
		(231230, 3.99, 'Meias Invisíveis', '´Vestuário'),
		(231310, 7.99, 'Meias Curtas Adidas', 'Vestuário'),
		(198359, 41.24, 'Camisola Térmica Nike', 'Vestuário'),
		(187651, 49.99, 'Casaco Adidas Foil', 'Vestuário'),
		(190826, 39.99, 'Corta-vento Nike', 'Vestuário'),
		(168194, 12.99, 'Luvas Boxe', 'Acessórios'),
		(892339, 14.99, 'Calções Silver Re-mimetic', 'Vestuário'),
		(128626, 41.24, 'Botas Converse Rival', 'Calçado'),
		(101561, 39.99, 'Smartwatch Innova', 'Acessórios'),
		(178978, 12.99, 'Óculos Natação', 'Acessórios'),
		(825488, 39.19, 'Sapatilha Adidas Runfalcon', 'Calçado'),
		(148597, 14.99, 'Bola futebol Adidas final 19', 'Acessórios'),
		(154004, 14.99, 'Bola basquetebol Nike baller', 'Acessórios'),
		(187634, 29.99, 'Sapatilha Puma Up Clássico', 'Calçado'),
		(636548, 9.49, 'Mochila Puma Phase', 'Acessórios'),
		(204860, 179.99, 'Prancha de surf Quiksilver', 'Acessórios'),
		(176530, 9.99, 'Luvas Ciclismo Mitical', 'Acessórios'),
		(821728, 25.99, 'Boné North Face', 'Acessório'),
		(826096, 29.99, 'Calças Montanha', 'Vestuário'),
		(176537, 69.99, 'Sapatilha Air Max', 'Calçado'),
		(190005, 49.99, 'Sapatilha Nike MD Runner 2', 'Calçado'),
		(402456, 49.99, 'Chuteiras Nike Mercurial', 'Calçado'),
		(578954, 19.99, 'Bola Euro 2020', 'Acessórios'),
		(253619, 29.74, 'Calças Nike Academy', 'Vestuário'),
		(827412, 35.99, 'Calças Adidas Aop', 'Vestuário'),
		(958944, 42.39, 'Sapatilha Nike Zoom', 'Calçado'),
		(198208, 79.99, 'Casaco Puma Metal Mulher', 'Vestuário'),
		(442211, 11.99, 'Casaco Montanha Boriken', 'Vestuário'),
		(290212, 6.49, 'Casaco Polar Up', 'Vestuário'),
		(908123, 10.49, 'Casaco Acolchoado Boriken', 'Vestuário'),
		(231631, 12.99, 'Boné Adidas Logo Metal', 'Acessório'),
		(921733, 24.99, 'Pulsómetro Army Watch', 'Acessório')

INSERT INTO Projeto.Artigo_Armazem(Codigo, IDArmazem, QuantArtigos)
-- ARMAZEM 110 (2000 CAPACIDADE)
VALUES	(128626, 110, 240),
		(165856, 110, 135),
		(204860, 110, 150),
		(176530, 110, 130),
		(101561, 110, 20),
		(821728, 110, 70),
		(231310, 110, 50),
		(892339, 110, 45),
		(402456, 110, 120),
		(578954, 110, 70),
		(148597, 110, 110),
		(154004, 110, 80),
		(187651, 110, 90),
		(190826, 110, 110),
-- ARMAZEM 120 (500 CAPACIDADE)
		(825488, 120, 60),
		(826096, 120, 50),
		(176537, 120, 55),
		(190005, 120, 80),
		(204860, 120, 40),
		(168194, 120, 45),
		(176530, 120, 130),
		(172733, 120, 30),
-- ARMAZEM 130 (3500 CAPACIDADE)
		(103568, 130, 270),
		(101561, 130, 190),
		(827127, 130, 330),
		(165856, 130, 150),
		(177895, 130, 200),
		(187853, 130, 280),
		(198932, 130, 195),
		(231230, 130, 100),
		(231310, 130, 250),
		(291208, 130, 300),
		(402456, 130, 110),
-- ARMAZEM 140 (1000 CAPACIDADE)
		(758763, 140, 120),
		(172630, 140, 80),
		(253619, 140, 200),
		(821728, 140, 150),
		(827412, 140, 110),
		(826096, 140, 140),
		(154004, 140, 150),
		(187651, 140, 50),
-- ARMAZEM 150 (4500 CAPACIDADE)
		(758763, 150, 200),
		(825488, 150, 150),
		(958944, 150, 100),
		(101561, 150, 110),
		(130055, 150, 190),
		(148597, 150, 250),
		(154004, 150, 230),
		(165856, 150, 270),
		(177895, 150, 300),
		(187853, 150, 160),
		(198932, 150, 40),
		(204860, 150, 150),
		(168194, 150, 290),
		(103568, 150, 125),
		(190005, 150, 194),
		(172733, 150, 30),
		(156428, 150, 110)

INSERT INTO Projeto.Armazem(IDArmazem, Capacidade, NumLoja)
VALUES	(130, 3500, 2),
		(110, 2000, 1),
		(150, 4500, 3),
		(120, 500, 1),
		(140, 1000, 2)

ALTER TABLE Projeto.Artigo_Armazem
	ADD CONSTRAINT ID_ARMAZEM FOREIGN KEY(IDArmazem) REFERENCES Projeto.Armazem(IDArmazem);

INSERT INTO Projeto.Artigo_Transporte(Codigo, IDTransporte, QuantArtigos)
VALUES	(402456, 130254, 1),
		(578954, 120548, 4),
		(154004, 175896, 3),
		(187651, 158930, 1),
		(892339, 190800, 2),
		(156428, 187232, 1),
		(825488, 165489, 1),
		(826096, 175876, 1),
		(176537, 156899, 1),
		(190005, 178999, 1),
		(103568, 120061, 3),
		(101561, 135896, 1),
		(827127, 185896, 3),
		(758763, 147025, 2),
		(172630, 105896, 2),
		(253619, 148667, 2),
		(821728, 152338, 1),
		(190005, 100259, 1),
		(176530, 175296, 2),
		(148597, 165896, 2),
		(198208, 174896, 1)


INSERT INTO Projeto.Transporte(IDTransporte, Data, Destino)
VALUES	(130254, '2020-04-15', 'Av. Dr. Lourenço Peixinho, Aveiro'),
		(120548, '2020-01-22', 'Av. da Boavista, Porto'),
		(175896, '2020-02-24', 'R. 21 de Agosto, Viseu'),
		(158930, '2019-12-20', 'Av. Central, Braga'),
		(175876, '2020-04-16', 'R. do Brasil, Coimbra'),
		(165489, '2019-06-02', 'R. da Prata, Lisboa'),
		(174896, '2019-12-23', 'R. Cidade de Bejar, Guarda'),
		(175296, '2020-02-07', 'R. DomJoão III, Coimbra'),
		(100259, '2020-03-12', 'Estrada de Santiago, Aveiro'),
		(152338, '2020-03-27', 'R. Andrade Corvo, Braga'),
		(148667, '2020-04-11', 'R. Qta. da Alagoa, Viseu'),
		(165896, '2020-03-03', 'R. Alves Roçadas, Guarda'),
		(147025, '2020-03-09', 'Praça da Liberdade, Porto'),
		(105896, '2020-02-19', 'R. Infantaria 23, Coimbra'),
		(185896, '2019-11-03', 'R. da Paz, Viseu'),
		(135896, '2020-01-02', 'Av. da Igreja, Guarda'),
		(120061, '2020-01-23', 'R. Serrado, Viseu'),
		(178999, '2020-02-03', 'Av. Eira do Serrado, Guarda'),
		(156899, '2020-03-14', 'R. dos Gatos, Lisboa'),
		(187232, '2020-04-27', 'R. Dos Bons Homens, Porto'),
		(190800, '2020-04-29', 'R. das Almas, Peniche')

ALTER TABLE Projeto.Artigo_Transporte
	ADD CONSTRAINT ID_TRANSPORTE FOREIGN KEY(IDTransporte) REFERENCES Projeto.Transporte(IDTransporte);

INSERT INTO Projeto.Artigo_Loja(Codigo, NumLoja, QuantArtigos)
-- LOJA 1
VALUES	(156428, 1, 12),
		(245875, 1, 18),
		(114526, 1, 20),
		(177895, 1, 30),
		(103568, 1, 10),
		(165856, 1, 15),
		(187853, 1, 28),
		(130055, 1, 19),
		(172733, 1, 12),
		(291208, 1, 13),
		(827127, 1, 23),
		(758763, 1, 12),
		(172630, 1, 20),
		(198932, 1, 29),
		(231230, 1, 14),
		(231310, 1, 15),
		(198359, 1, 12),
		(187651, 1, 10),
		(190826, 1, 12),
		(636548, 1, 14),
		(168194, 1, 25),
		(892339, 1, 14),
		(128626, 1, 24),
		(101561, 1, 19),
		(178978, 1, 10),
-- LOJA 2
		(187853, 2, 20),
		(825488, 2, 20),
		(130055, 2, 20),
		(148597, 2, 15),
		(154004, 2, 10),
		(165856, 2, 12),
		(168194, 2, 16),
		(177895, 2, 20),
		(198932, 2, 19),
		(231230, 2, 10),
		(231310, 2, 25),
		(291208, 2, 30),
		(827127, 2, 13),
		(758763, 2, 20),
		(172630, 2, 12),
		(187634, 2, 10),
		(198359, 2, 21),
		(156428, 2, 20),
		(245875, 2, 18),
		(172733, 2, 28),
		(187651, 2, 12),
		(190826, 2, 21),
		(101561, 2, 29),
		(178978, 2, 15),
		(636548, 2, 10),
-- LOJA 3
		(636548, 3, 15),
		(128626, 3, 39),
		(204860, 3, 17),
		(165856, 3, 19),
		(176530, 3, 33),
		(101561, 3, 20),
		(821728, 3, 45),
		(148597, 3, 19),
		(154004, 3, 23),
		(187651, 3, 21),
		(826096, 3, 20),
		(176537, 3, 29),
		(190005, 3, 32),
		(758763, 3, 25),
		(172630, 3, 30),
		(190826, 3, 14),
		(187634, 3, 10),
		(156428, 3, 12),
		(245875, 3, 22),
		(198359, 3, 21),
		(172733, 3, 18),
		(130055, 3, 19)

INSERT INTO Projeto.Loja(NumLoja, Nome, Localizacao)
VALUES	(1, 'M&T Sports Line - Aveiro', 'Aveiro'),
		(2, 'M&T Sports Line - Porto', 'Porto'),
		(3, 'M&T Sports Line - Lisboa', 'Lisboa')

ALTER TABLE Projeto.Armazem
	ADD CONSTRAINT NUM_LOJA_ARMAZEM FOREIGN KEY(NumLoja) REFERENCES Projeto.Loja(NumLoja);

ALTER TABLE Projeto.Artigo_Loja
	ADD CONSTRAINT NUM_LOJA_ARTIGO FOREIGN KEY(NumLoja) REFERENCES Projeto.Loja(NumLoja);

INSERT INTO Projeto.Artigo_Devolvido(Codigo, IDDevolucao, QuantArtigos)
VALUES	(758763, 30023, 2),
		(825488, 30030, 1),
		(101561, 30011, 1),
		(130055, 30035, 1),
		(148597, 30017, 2),
		(165856, 30042, 1),
		(177895, 30050, 3),
		(198932, 30054, 1),
		(204860, 30015, 1),
		(168194, 30060, 2),
		(176530, 30012, 2),
		(178978, 30010, 3),
		(172630, 30025, 1),
		(187634, 30041, 1),
		(198359, 30036, 1),
		(187651, 30020, 1),
		(190826, 30053, 1),
		(176537, 30047, 1),
		(190005, 30039, 1),
		(178978, 30032, 1),
		(198208, 30028, 1)


INSERT INTO Projeto.Devolucao(IDDevolucao, Data, Montante, NIF, NumFunc)
VALUES	(30023, '2020-04-22', 30.98, 224234256, 112034),
		(30015, '2020-03-02', 179.99, 234167270, 132388),
		(30011, '2020-01-26', 39.99, 232157891, 133286),
		(30017, '2020-03-12', 29.98, 232187270, 131111),
		(30030, '2020-02-23', 39.19, 367192368, 139901),
		(30035, '2019-12-27', 19.99, 721352076, 111890),
		(30042, '2020-03-05', 89.99, 218368289, 133214),
		(30050, '2020-04-01', 29.97, 209964271, 112034),
		(30054, '2020-03-02', 189.99, 232157891, 114936),
		(30060, '2020-03-20', 25.98, 212445278, 111890),
		(30012, '2020-04-04', 19.98, 232157891, 132388),
		(30010, '2020-02-02', 23.97, 233464200, 139827),
		(30025, '2020-03-28', 4.99, 211119034, 133286),
		(30041, '2020-01-20', 29.99, 204138828, 133286),
		(30036, '2020-03-30', 41.24, 219903030, 134300),
		(30020, '2020-01-24', 49.99, 209969279, 133286),
		(30053, '2020-02-09', 39.99, 209964271, 138754),
		(30047, '2020-02-02', 69.99, 232157891, 123091),
		(30039, '2020-01-29', 49.99, 218368289, 129078),
		(30032, '2020-01-26', 12.99, 232187270, 134300),
		(30028, '2020-01-01', 79.99, 538160672, 138754)

ALTER TABLE Projeto.Artigo_Devolvido
	ADD CONSTRAINT ID_DEVOLUCAO FOREIGN KEY(IDDevolucao) REFERENCES Projeto.Devolucao(IDDevolucao);

INSERT INTO Projeto.Artigo_Comprado(Codigo, NumCompra, QuantArtigos)
VALUES	(578954, 30000, 2),
		(758763, 30352, 2),
		(825488, 31671, 1),
		(958944, 30051, 4),
		(101561, 30098, 1),
		(130055, 31527, 2),
		(148597, 30027, 4),
		(154004, 30100, 2),
		(165856, 30110, 2),
		(177895, 30167, 3),
		(187853, 32098, 6),
		(198932, 32167, 2),
		(204860, 32569, 1),
		(168194, 33456, 2),
		(103568, 35897, 3),
		(190005, 34251, 1),
		(172630, 30972, 3),
		(187651, 31263, 1),
		(190826, 30001, 1),
		(190005, 30212, 1),
		(442211, 33212, 2),
		(290212, 39212, 2),
		(908123, 38222, 2),
		(892339, 31273, 1),
		(827412, 30198, 1),
		(826096, 34108, 1),
		(253619, 39100, 1),
		(821728, 31111, 1),
		(231631, 31221, 1),
		(827127, 31999, 1),
		(231230, 30932, 4),
		(231310, 32399, 2),
		(291208, 30008, 1),
		(921733, 35548, 1),
		(156428, 34252, 2)

INSERT INTO Projeto.Compra(NumCompra, Data, Montante, NIF, NumFunc)
VALUES	(30000, '2020-04-01', 39.98, 438291045, 139999),
		(30051, '2020-04-02', 169.56, 209964271, 134300),
		(30352, '2020-04-03', 30.98, 214131211, 129078),
		(31671, '2020-04-04', 39.19, 367192368, 139901),
		(30098, '2020-04-05', 39.99, 232157891, 133286),
		(31527, '2020-06-07', 39.98, 721352076, 111890),
		(30027, '2020-04-07', 59.96, 232187270, 131111),
		(30100, '2020-04-08', 29.98, 214139274, 130903),
		(30110, '2020-04-09', 179.98, 218368289, 133214),
		(30167, '2020-04-10', 29.97, 209964271, 112034),
		(32098, '2020-04-11', 59.99, 214139274, 132388),
		(32167, '2020-04-15', 279.98, 232157891, 114936),
		(32569, '2020-04-17', 179.99, 224234256, 133214),
		(33456, '2020-04-19', 25.98, 212445278, 111890),	
		(35897, '2020-04-21', 5.97, 199197979, 140002),
		(34251, '2020-02-12', 49.99, 222162274, 136765),
		(30972, '2020-02-12', 14.97, 233464200, 139999),
		(31263, '2020-01-01', 49.99, 438291045, 138273),
		(30001, '2020-03-29', 39.99, 222162274, 111890),
		(30212, '2020-02-23', 49.99, 211119034, 123632),
		(33212, '2020-01-19', 23.98, 209964271, 114936),
		(39212, '2020-01-06', 12.98, 564820954, 140003),
		(38222, '2020-01-20', 20.98, 721352076, 139999),
		(31273, '2020-01-14', 14.99, 204003989, 140003),
		(30198, '2020-01-27', 35.99, 367192368, 123303),
		(34108, '2020-01-28', 29.99, 222162274, 123303),
		(39100, '2020-02-27', 29.74, 234167270, 130903),
		(31111, '2020-03-01', 25.99, 209969279, 130903),
		(31221, '2020-03-07', 12.99, 212445278, 139827),
		(31999, '2020-03-24', 14.99, 224234256, 130903),
		(30932, '2020-03-08', 15.96, 438291045, 127635),
		(32399, '2020-03-29', 15.98, 367192368, 110969),
		(30008, '2020-01-31', 29.99, 218368289, 123303),
		(35548, '2020-04-04', 24.99, 200197299, 139909),
		(34252, '2020-03-30', 139.98, 214139274, 112034)

ALTER TABLE Projeto.Artigo_Comprado
	ADD CONSTRAINT NUM_COMPRA FOREIGN KEY(NumCompra) REFERENCES Projeto.Compra(NumCompra);

INSERT INTO Projeto.Cliente(NIF, Morada, Nome, NumTelem)
VALUES	(234167270, 'R. José Afonso, Aveiro', 'Maria Almeida', 925642354),
		(214139274, 'R. de Egas Moniz, Porto', 'Rui Tomás', 913245559),
		(209969279, Null, 'Carlos Pedro', 965699300),
		(222162274, 'R. António leitão, Coimbra', 'Jéssica Costa', 938341311),
		(199197979, Null, 'Mário Cruz', 960082959),
		(204138828, Null, 'Gonçalo Dias', 926602050),
		(232157891, 'R. dos Galos, Braga', 'Beatriz Prata', 960992011),
		(224234256, 'R. do Carmo, Aveiro', 'Guilherme Marques', 965711954),
		(212445278, 'R. São Frnaciso, Viseu', 'Liliana Barbosa', 919632390),
		(218368289, 'R. de Júlio Dinis, Porto', 'Pedro Costa', 934645399),
		(233464200, Null, 'Adriana Caetano', 920692366),
		(234162272, 'R. Paulo Emílio, Viseu', 'Inês Pereira', 960047392),
		(232187270, 'R.da Pena, Porto', 'Mónica Alves', 915634555),
		(200197299, 'R. João Mendes, Viseu', 'Diogo Matos', 966642056),
		(214131211, 'R. da Aviação Naval, Aveiro', 'Hugo Castro', 938445390),
		(224193223, 'R. Angola, Coimbra', 'Marisa Bernardo', 931946115),
		(209964271, 'R. Campo Alegre', 'Paulo Jesus', 965642354),
		(204003989, Null, 'Mariana Santos', 965642354),
		(219903030, 'R. Artur Bivar, Braga', 'João Pedro', 910012984),
		(211119034, 'R. Dr. Luíz Ferreira', 'Marta Silva', 961119421),
		(297361230, 'R. Direita, Lisboa','João Silva',925048749),
		(564820954, Null,'Manuel Dias',937174610),
		(438291045, 'Av. Lourenço Peixinho, Aveiro','Susana Cardoso',9145107491),
		(123456789, 'R. Emídio Navarro, Águeda','Gracinda Simões', 927451250),
		(721352076, Null,'Rui Pinheiro',964397521),
		(547198471, 'Praceta da Liberdade, Gouveia', 'Afonso Ramos', 934560126),
		(312467201, 'R. Mouzinho da Silveira, Setúbal', 'Ema Duarte' ,913739134),
		(538160672, 'Av. 1º de Maio, Seia', 'José Dias', 937143918),
		(123804287, 'Av. Bombeiros Voluntários, Braga', 'Matilde Coelho' ,967123986),
		(367192368, 'R. do Estádio Municipal, Portimão', 'Guilherme Domingues' ,925291856)

ALTER TABLE Projeto.Devolucao
	ADD CONSTRAINT NIF_DEVOLUCAO FOREIGN KEY(NIF) REFERENCES Projeto.Cliente(NIF);

ALTER TABLE Projeto.Compra
	ADD CONSTRAINT NIF_COMPRA FOREIGN KEY(NIF) REFERENCES Projeto.Cliente(NIF);

INSERT INTO Projeto.Funcionario(NumFunc, Morada, Nome, NumTelem, NumLoja)
VALUES	(142056, 'Av. 25 de Abril, Aveiro', 'Pedro Martins', 915486359, 1),
		(132996, 'R. Forno, Coimbra', 'Eduardo Marques', 965770350, 1),
		(129078, 'R do Ferraz, Porto', 'Maria Bernardo', 960098423, 1),
		(123091, 'Av. dos Combatentes, Lisboa', 'Luís Brás', 936754434, 1),
		(139901, 'R. Gago Coutinho, Cascais', 'Sofia Marques', 938900097, 1),
		(134300, 'R. do Cortinhal, Mangualde', 'Madalena Prata', 928766568, 1),
		(136766, 'R. Camões, Celorico', 'Tiago Rocha', 939988760, 1),
		(136765, 'R. Infante Henrique, Lisboa', 'Carlos Simões', 965243746, 1),
		(138754, 'Av. da Pombas, Leiria', 'Mónica Silva', 912435678, 1),
		(138273, 'Av. da Bela Justa, Coimbra', 'Rosa Peixe', 917888790, 1),
		(110969, 'R. de São Martinho, Aveiro', 'Joana Vicente', 925436450, 2),
		(112034, 'R. de Trás, Porto', 'André Rebelo', 963485656, 2),
		(123303, 'Av. 31 de Janeiro, Braga', 'Márcio Costa', 965406233, 2),
		(130903, 'R. André Soares, Braga', 'Marco Pinto', 936273351, 2),
		(133214, 'R. Júlio Dinis, Ovar', 'Pedro Almeida', 915363728, 2),
		(127635, 'R. Liberdade, Almada', 'Rita Pais', 916667543, 2),
		(140002, 'R. Escura, Viseu', 'Aires Neves', 966787754, 2),
		(131111, 'R. Calouste Gulbenkian, Guarda', 'Sara Gomes', 965568987, 2),
		(139909, 'R. Manuel Martins, Trancoso', 'Gonçalo Oliveira', 929873232, 2),
		(139827, 'R. Pedro Alvares Cabral, Braga', 'Francisca Ferreira', 924343541, 2),
		(114936, 'R. das Violetas, Guimarães', 'Fábio Costa', 965294842, 3),
		(111890, 'R. Gonçalinho, Viseu', 'Edgar Alves', 912263455, 3),
		(132388, 'R. Dr. Manuel Alegre, Águeda', 'Andreia Cabeças', 966452830, 3),
		(133286, 'Av. Jaime Cortesão, Setúbal', 'Angela Pires', 934500987, 3),
		(139998, 'Av. de São Miguel, Guarda', 'Nuno Simões', 927765321, 3),
		(132456, 'R. Chã, Porto', 'Maria Grácio', 960098909, 3),
		(140003, 'R. Direita, Gouveia', 'Alexandre Pereira', 967786543, 3),
		(139999, 'R. do Sol, Porto', 'Sérgio Costa', 928765654, 3),
		(138726, 'R. da Escuridão, Sintra', 'Pedro Matos', 934524242, 3),
		(123632, 'Av. da Liberdade, Lisboa', 'Maria Louçã', 923233400, 3)

ALTER TABLE Projeto.Devolucao
	ADD CONSTRAINT NUMFUNC_DEVOLUCAO FOREIGN KEY(NumFunc) REFERENCES Projeto.Funcionario(NumFunc);

ALTER TABLE Projeto.Compra
	ADD CONSTRAINT NUMFUNC_COMPRA FOREIGN KEY(NumFunc) REFERENCES Projeto.Funcionario(NumFunc);

--QUERYS TESTS
--SELECT * FROM Projeto.Armazem;
--SELECT * FROM Projeto.Artigo;
--SELECT * FROM Projeto.Artigo_Armazem;
--SELECT * FROM Projeto.Artigo_Comprado;
--SELECT * FROM Projeto.Artigo_Devolvido;
--SELECT * FROM Projeto.Artigo_Loja;
--SELECT * FROM Projeto.Artigo_Transporte;
--SELECT * FROM Projeto.Cliente;
--SELECT * FROM Projeto.Compra;
--SELECT * FROM Projeto.Devolucao;
--SELECT * FROM Projeto.Funcionario;
--SELECT * FROM Projeto.Loja;
--SELECT * FROM Projeto.Transporte;