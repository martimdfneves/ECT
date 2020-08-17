--CREATE DATABASE LojaDesporto;
--GO

-- DELETING ZONE
--USE LojaDesporto
--GO
--ALTER TABLE Proj.Artigo
--	DROP CONSTRAINT IF EXISTS ID_DEVOLUCAO;
--ALTER TABLE Proj.Artigo
--	DROP CONSTRAINT IF EXISTS NUM_COMPRA;
--ALTER TABLE Proj.Artigo
--	DROP CONSTRAINT IF EXISTS ID_TRANSPORTE;
--ALTER TABLE Proj.Artigo
--	DROP CONSTRAINT IF EXISTS ID_ARMAZEM;
--ALTER TABLE Proj.Artigo
--	DROP CONSTRAINT IF EXISTS NUM_LOJA
--DROP TABLE IF EXISTS Proj.Devolucao;
--DROP TABLE IF EXISTS Proj.Compra;
--DROP TABLE IF EXISTS Proj.Funcionario;
--DROP TABLE IF EXISTS Proj.Cliente;
--DROP TABLE IF EXISTS Proj.Transporte;
--DROP TABLE IF EXISTS Proj.Armazem;
--DROP TABLE IF EXISTS Proj.Loja;
--DROP TABLE IF EXISTS Proj.Artigo;
--DROP SCHEMA IF EXISTS Proj;
--DROP DATABASE IF EXISTS LojaDesporto; 

USE LojaDesporto
GO

CREATE SCHEMA Proj;
GO

-- CREATING TABLES
CREATE TABLE Proj.Artigo(
	Codigo			INT				NOT NULL,
	Preco			DECIMAL(4,2)	CHECK(Preco > 0),	-- 4 Números para as unidades e 2 para casas decimais 
	Nome			VARCHAR(20)		NOT NULL,
	Categoria		VARCHAR(20)		NOT NULL,			-- Vestuário, calçado, acessórios, etc...
	QuantArtigos	INT				CHECK(QuantArtigos >= 0),
	NumLoja			INT,
	IDArmazem		INT,
	IDTransporte	INT,
	NumCompra		INT,
	IDDevolucao		INT,
	PRIMARY KEY(Codigo)
);

CREATE TABLE Proj.Loja(
	NumLoja			INT				CHECK(NumLoja > 0),
	Nome			VARCHAR(20)		NOT NULL,
	Localizacao		VARCHAR(20)		NOT NULL,
	PRIMARY KEY(NumLoja)
);

ALTER TABLE Proj.Artigo
ADD CONSTRAINT NUM_LOJA FOREIGN KEY(NumLoja) REFERENCES Proj.Loja(NumLoja); 

CREATE TABLE Proj.Armazem(
	IDArmazem		INT				CHECK(IDArmazem > 0),
	Capacidade		INT				CHECK(Capacidade > 0),
	NumLoja			INT				CHECK(NumLoja > 0),
	PRIMARY KEY(IDArmazem),
	FOREIGN KEY(NumLoja) REFERENCES Proj.Loja(NumLoja)
);

ALTER TABLE Proj.Artigo
ADD CONSTRAINT ID_ARMAZEM FOREIGN KEY(IDArmazem) REFERENCES Proj.Armazem(IDArmazem); 

CREATE TABLE Proj.Transporte(
	IDTransporte	INT				CHECK(IDTransporte > 0),
	DataTransp		DATE,
	Destino			VARCHAR(20),
	PRIMARY KEY(IDTransporte)
);

ALTER TABLE Proj.Artigo
ADD CONSTRAINT ID_TRANSPORTE FOREIGN KEY(IDTransporte) REFERENCES Proj.Transporte(IDTransporte);

CREATE TABLE Proj.Cliente(
	NIF				INT				CHECK(NIF > 0),
	Morada			VARCHAR(20),
	Nome			VARCHAR(20)		NOT NULL,
	NumTelem		INT,
	PRIMARY KEY(NIF)
);

CREATE TABLE Proj.Funcionario(
	NumFunc			INT				CHECK(NumFunc > 0),
	Morada			VARCHAR(20)		NOT NULL,
	Nome			VARCHAR(20)		NOT NULL,
	NumTelem		INT				NOT NULL,
	NumLoja			INT				CHECK(NumLoja > 0),
	PRIMARY KEY(NumFunc),
	FOREIGN KEY(NumLoja) REFERENCES Proj.Loja(NumLoja)
);

CREATE TABLE Proj.Compra(
	NumCompra		INT				CHECK(NumCompra > 0),
	Data			DATE			NOT NULL,
	Montante		DECIMAL(4,2)	CHECK(Montante > 0),	-- 4 Números para as unidades e 2 para casas decimais
	NIF				INT				CHECK(NIF > 0),
	NumFunc			INT				CHECK(NumFunc > 0),
	PRIMARY KEY(NumCompra),
	FOREIGN KEY(NIF) REFERENCES Proj.Cliente(NIF),
	FOREIGN KEY(NumFunc) REFERENCES Proj.Funcionario(NumFunc)
);

ALTER TABLE Proj.Artigo
ADD CONSTRAINT NUM_COMPRA FOREIGN KEY(NumCompra) REFERENCES Proj.Compra(NumCompra);

CREATE TABLE Proj.Devolucao(
	IDDevolucao		INT				CHECK(IDDevolucao > 0),
	Data			DATE			NOT NULL,
	Montante		DECIMAL(4,2)	CHECK(Montante > 0),	-- 4 Números para as unidades e 2 para casas decimais
	NIF				INT				CHECK(NIF > 0),
	NumFunc			INT				CHECK(NumFunc > 0),
	PRIMARY KEY(IDDevolucao),
	FOREIGN KEY(NIF) REFERENCES Proj.Cliente(NIF),
	FOREIGN KEY(NumFunc) REFERENCES Proj.Funcionario(NumFunc)
);

ALTER TABLE Proj.Artigo
ADD CONSTRAINT ID_DEVOLUCAO FOREIGN KEY(IDDevolucao) REFERENCES Proj.Devolucao(IDDevolucao);

-- INSERT DATA
INSERT INTO Proj.Artigo(Codigo, Preco, Nome, Categoria, QuantArtigos, NumLoja, IDArmazem, IDTransporte, NumCompra, IDDevolucao)
VALUES	(156428, 69.99, 'Camisola Equipamento SLB', 'Vestuário', 12, 1, Null, Null, Null, Null),
		(156428, 69.29, 'Camisola Equipamento SLB', 'Vestuário', 12, Null, 120, Null, Null, Null),
		(245876, 59.99, 'Camisola Equipamento SCP', 'Vestuário', 18, 1, Null, Null, Null, Null),
		(302548, 64.49, 'Camisola Equipamento FCP', 'Vestuário', 14, 2, Null, Null, Null, Null),
		(402458, 49.99, 'Chuteiras Nike Mercurial', 'Calçado', 1, Null, Null, 175896, Null, Null),
		(578956, 19.99, 'Bola Euro 2020', 'Acessórios', 2, Null, Null, Null, 30000, Null),
		(636548, 9.49, 'Mochila Puma Phase', 'Acessórios', 15, 4, Null, Null, Null, Null),
		(758769, 15.49, 'Casaco de Ciclismo', 'Vestuário', 2, Null, Null, Null, 30352, Null),
		(758769, 15.49, 'Casaco de Ciclismo', 'Vestuário', 2, Null, Null, Null, Null, 23),
		(825489, 39.19, 'Sapatilha Adidas Runfalcon', 'Calçado', 20, 4, Null, Null, Null, Null),
		(825489, 39.19, 'Sapatilha Adidas Runfalcon', 'Calçado', Null, Null, Null, Null, 31671, Null),
		(825489, 39.19, 'Sapatilha Adidas Runfalcon', 'Calçado', Null, Null, Null, Null, Null, 30),
		(958945, 42.39, 'Sapatilha Nike Zoom', 'Calçado', 4, Null, Null, Null, 30050, Null),
		(101568, 39.99, 'Smartwatch Innova', 'Acessórios', 50, Null, 130, Null, Null, Null),
		(101568, 39.99, 'Smartwatch Innova', 'Acessórios', Null, Null, Null, Null, 30098, Null),
		(101568, 39.99, 'Smartwatch Innova', 'Acessórios', Null, Null, Null, Null, Null, 11),
		(114526, 19.99, 'Pulseira Desportiva', 'Acessórios', 20, 5, Null, Null, Null, Null),
		(128627, 41.24, 'Botas Converse Rival', 'Calçado', 30, 3, Null, Null, Null, Null),
		(130058, 19.99, 'Calções Puma', 'Vestuário', 20, 2, Null, Null, Null, Null),
		(130058, 19.99, 'Calções Puma', 'Vestuário', Null, Null, Null, Null, 31527, Null),
		(130058, 19.99, 'Calções Puma', 'Vestuário', Null, Null, Null, Null, Null, 35),
		(148600, 14.99, 'Bola futebol Adidas final 19', 'Acessórios', 15, 5, Null, Null, Null, Null),
		(148600, 14.99, 'Bola futebol Adidas final 19', 'Acessórios', Null, Null, Null, Null, 30027, Null),
		(148600, 14.99, 'Bola futebol Adidas final 19', 'Acessórios', Null, Null, Null, Null, Null, 17),
		(154009, 14.99, 'Bola basquetebol Nike baller', 'Acessórios', 10, 5, 140, Null, Null, Null),
		(154009, 14.99, 'Bola basquetebol Nike baller', 'Acessórios', Null, Null, Null, Null, 30100, Null),
		(165860, 89.99, 'Raquete Ténis Head Ig Challenge Pro', 'Acessórios', 12, 2, 130, Null, Null, Null),
		(165860, 89.99, 'Raquete Ténis Head Ig Challenge Pro', 'Acessórios', Null, Null, Null, Null, 30110, Null),
		(165860, 89.99, 'Raquete Ténis Head Ig Challenge Pro', 'Acessórios', Null, Null, Null, Null, Null, 42),
		(177893, 9.99, 'Tapete ginástica/yoga', 'Acessórios', Null, Null, Null, Null, 30167, Null),
		(177893, 9.99, 'Tapete ginástica/yoga', 'Acessórios', Null, Null, Null, Null, Null, 50),
		(177893, 9.99, 'Tapete ginástica/yoga', 'Acessórios', 30, 1, Null, Null, Null, Null),
		(187856, 9.99, 'Bolas Ténis Dunlop Bipk4', 'Acessórios', 20, 4, 110, Null, Null, Null),
		(187856, 9.99, 'Bolas Ténis Dunlop Bipk4', 'Acessórios', Null, Null, Null, Null, 32098, Null),
		(198935, 189.99, 'Halteres 20 Kg Bodytone', 'Acessórios', 5, 1, Null, Null, Null, Null),
		(198935, 189.99, 'Halteres 20 Kg Bodytone', 'Acessórios', Null, Null, Null, Null, 32167, Null),
		(198935, 189.99, 'Halteres 20 Kg Bodytone', 'Acessórios', Null, Null, Null, Null, Null, 54),
		(204863, 179.99, 'Prancha de surf Quiksilver', 'Acessórios', 4, 5, Null, Null, Null, Null),
		(204863, 179.99, 'Prancha de surf Quiksilver', 'Acessórios', Null, Null, Null, Null, 32569, Null),
		(204863, 179.99, 'Prancha de surf Quiksilver', 'Acessórios', Null, Null, Null, Null, Null, 15),
		(268194, 12.99, 'Luvas Boxe', 'Acessórios', 6, 2, Null, Null, Null, Null),
		(268194, 12.99, 'Luvas Boxe', 'Acessórios', Null, Null, Null, Null, 33456, Null),
		(268194, 12.99, 'Luvas Boxe', 'Acessórios', Null, Null, Null, Null, Null, 60),
		(103571, 1.99, 'Touca natação', 'Acessórios', 10, 5, Null, Null, Null, Null),
		(103571, 1.99, 'Touca natação', 'Acessórios', Null, Null, Null, Null, 35897, Null)

INSERT INTO Proj.Loja(NumLoja, Nome, Localizacao)
VALUES	(1, 'M&T Sports Line - Aveiro', 'Aveiro'),
		(2, 'M&T Sports Line - Porto', 'Porto'),
		(3, 'M&T Sports Line - Lisboa', 'Lisboa'),
		(4, 'M&T Sports Line - Braga', 'Braga'),
		(5, 'M&T Sports Line - Coimbra', 'Coimbra')

INSERT INTO Proj.Armazem(IDArmazem, Capacidade, NumLoja)
VALUES	(140, 2500, 4),
		(120, 3500, 2),
		(160, 1500, 3),
		(110, 3000, 1),
		(130, 4500, 3),
		(170, 500, 1),
		(180, 100, 2),
		(150, 3000, 5)

INSERT INTO Proj.Transporte(IDTransporte, DataTransp, Destino)
VALUES	(130254, '15-04-2020', 'Av. Dr. Lourenço Peixinho, Aveiro'),
		(120548, '22-01-2020', 'Av. da Boavista, Porto'),
		(175896, '24-02-2020', 'R. 21 de Agosto, Viseu'),
		(158930, '20-12-2019', 'Av. Central, Braga'),
		(175896, '16-04-2020', 'R. do Brasil, Coimbra'),
		(165489, '02-06-2019', 'R. da Prata, Lisboa'),
		(175896, '23-12-2019', 'R. Cidade de Bejar, Guarda'),
		(175896, '07-02-2020', 'R. DomJoão III, Coimbra'),
		(100259, '12-03-2020', 'Estrada de Santiago, Aveiro'),
		(152338, '27-03-2020', 'R. Andrade Corvo, Braga'),
		(148667, '11-04-2020', 'R. Qta. da Alagoa, Viseu'),
		(175896, '03-03-2020', 'R. Alves Roçadas, Guarda'),
		(147025, '09-03-2020', 'Praça da Liberdade, Porto'),
		(105896, '19-02-2020', 'R. Infantaria 23, Coimbra'),
		(175896, '03-11-2019', 'R. da Paz, Viseu'),
		(175896, '02-01-2020', 'Av. da Igreja, Guarda'),
		(120061, '23-01-2020', 'R. Serrado, Viseu')

INSERT INTO Proj.Cliente(NIF, Morada, Nome, NumTelem)
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
		(438291045, 'Avenida Lourenço Peixinho, Aveiro','Susana Cardoso',9145107491),
		(123456789, 'R. Emídio Navarro, Águeda','Gracinda Simões', 927451250),
		(721352076, Null,'Rui Pinheiro',964397521),
		(547198471, 'Praceta da Liberdade, Gouveia', 'Afonso Ramos', 934560126),
		(312467201, 'R. Mouzinho da Silveira, Setúbal', 'Ema Duarte' ,913739134),
		(538160672, 'Avenida 1º de Maio, Seia', 'José Dias', 937143918),
		(123804287, 'Avenida Bombeiros Voluntários, Braga', 'Matilde Coelho' ,967123986),
		(367192368, 'R. do Estádio Municipal, Portimão', 'Guilherme Domingues' ,925291856)

INSERT INTO Proj.Funcionario(NumFunc, Morada, Nome, NumTelem, NumLoja)
VALUES	(142056, 'Av. 25 de Abril, Aveiro', 'Pedro Martins', 915486359, 1),
		(110969, 'R. de São Martinho, Aveiro', 'Joana Vicente', 925436450, 4),
		(112034, 'R. de Trás, Porto', 'André Rebelo', 963485656, 2),
		(123303, 'Av. 31 de Janeiro, Braga', 'Márcio Costa', 965406233, 5),
		(130903, 'R. André Soares, Braga', 'Marco Pinto', 936273351, 2),
		(133214, 'R. Júlio Dinis, Ovar', 'Pedro Almeida', 915363728, 2),
		(114936, 'R. das Violetas, Guimarães', 'Fábio Costa', 965294842, 3),
		(111890, 'R. Gonçalinho, Viseu', 'Edgar Alves', 912263455, 3),
		(132996, 'R. Forno, Coimbra', 'Eduardo Marques', 965770350, 1),
		(132388, 'R. Dr. Manuel Alegre, Águeda', 'Andreia Cabeças', 966452830, 4),
		(129078, 'R do Ferraz, Porto', 'Maria Bernardo', 960098423, 4),
		(123091, 'Av. dos Combatentes, Lisboa', 'Luís Brás', 936754434, 1),
		(127635, 'R. Liberdade, Almada', 'Rita Pais', 916667543, 2),
		(133286, 'Av. Jaime Cortesão, Setúbal', 'Angela Pires', 934500987, 3),
		(139901, 'R. Gago Coutinho, Cascais', 'Sofia Marques', 938900097, 1),
		(140002, 'R. Escura, Viseu', 'Aires Neves', 966787754, 4),
		(139998, 'Av. de São Miguel, Guarda', 'Nuno Simões', 927765321, 4),
		(134300, 'R. do Cortinhal, Mangualde', 'Madalena Prata', 928766568, 1),
		(131111, 'R. Calouste Gulbenkian, Guarda', 'Sara Gomes', 965568987, 2),
		(132456, 'R. Chã, Porto', 'Maria Grácio', 960098909, 4),
		(136766, 'R. Camões, Celorico', 'Tiago Rocha', 939988760, 5),
		(139909, 'R. Manuel Martins, Trancoso', 'Gonçalo Oliveira', 929873232, 2),
		(140003, 'R. Direita, Gouveia', 'Alexandre Pereira', 967786543, 3),
		(139999, 'R. do Sol, Porto', 'Sérgio Costa', 928765654, 3),
		(120879, 'R. S. Gonçalinho, Aveiro','Joana Moreira',925710756,5)

INSERT INTO Proj.Compra(NumCompra, Data, Montante, NIF, NumFunc)
VALUES	(30000, '1-04-2020', 19.99, 438291045, 139999),
		(30050, '2-04-2020', 42.39, 209964271, 134300),
		(30352, '3-04-2020', 15.49, 214131211, 129078),
		(31671, '4-04-2020', 39.19, 367192368, 139901),
		(30098, '5-04-2020', 39.99, 232157891, 133286),
		(31527, '6-04-2020', 19.99, 721352076, 111890),
		(30027, '7-04-2020', 14.99, 232187270, 120879),
		(30100, '8-04-2020', 14.99, 214139274, 130903),
		(30110, '9-04-2020', 89.99, 218368289, 133214),
		(30167, '10-04-2020', 9.99, 209964271, 112034),
		(32098, '11-04-2020', 9.99, 214139274, 132388),
		(32167, '15-04-2020', 189.99, 232157891, 114936),
		(32569, '17-04-2020', 179.99, 224234256, 133214),
		(33456, '19-04-2020', 12.99, 212445278, 111890),	
		(35897, '21-04-2020', 1.99, 199197979, 140002)

INSERT INTO Proj.Devolucao(IDDevolucao, Data, Montante, NIF, NumFunc)
VALUES	(23, '22-04-2020', 15.49, 224234256, 112034),
		(15, '22-04-2020', 179.99, 234167270, 132388),
		(11, '22-04-2020', 39.99, 232157891, 133286),
		(17, '22-04-2020', 14.99, 232187270, 120879),
		(30, '22-04-2020', 39.19, 367192368, 139901),
		(35, '22-04-2020', 19.99, 721352076, 111890),
		(42, '22-04-2020', 89.99, 218368289, 133214),
		(50, '22-04-2020', 9.99, 209964271, 112034),
		(54, '22-04-2020', 189.99, 232157891, 114936),
		(60, '22-04-2020', 12.99, 212445278, 111890)