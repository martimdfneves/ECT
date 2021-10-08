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

USE
LojaDesporto
GO

CREATE SCHEMA Proj;
GO

-- CREATING TABLES
CREATE TABLE Proj.Artigo
(
    Codigo       INT         NOT NULL,
    Preco        DECIMAL(4, 2) CHECK (Preco > 0), -- 4 N�meros para as unidades e 2 para casas decimais
    Nome         VARCHAR(20) NOT NULL,
    Categoria    VARCHAR(20) NOT NULL,            -- Vestu�rio, cal�ado, acess�rios, etc...
    QuantArtigos INT CHECK (QuantArtigos >= 0),
    NumLoja      INT,
    IDArmazem    INT,
    IDTransporte INT,
    NumCompra    INT,
    IDDevolucao  INT,
    PRIMARY KEY (Codigo)
);

CREATE TABLE Proj.Loja
(
    NumLoja     INT CHECK (NumLoja > 0),
    Nome        VARCHAR(20) NOT NULL,
    Localizacao VARCHAR(20) NOT NULL,
    PRIMARY KEY (NumLoja)
);

ALTER TABLE Proj.Artigo
    ADD CONSTRAINT NUM_LOJA FOREIGN KEY (NumLoja) REFERENCES Proj.Loja (NumLoja);

CREATE TABLE Proj.Armazem
(
    IDArmazem  INT CHECK (IDArmazem > 0),
    Capacidade INT CHECK (Capacidade > 0),
    NumLoja    INT CHECK (NumLoja > 0),
    PRIMARY KEY (IDArmazem),
    FOREIGN KEY (NumLoja) REFERENCES Proj.Loja (NumLoja)
);

ALTER TABLE Proj.Artigo
    ADD CONSTRAINT ID_ARMAZEM FOREIGN KEY (IDArmazem) REFERENCES Proj.Armazem (IDArmazem);

CREATE TABLE Proj.Transporte
(
    IDTransporte INT CHECK (IDTransporte > 0),
    DataTransp   DATE,
    Destino      VARCHAR(20),
    PRIMARY KEY (IDTransporte)
);

ALTER TABLE Proj.Artigo
    ADD CONSTRAINT ID_TRANSPORTE FOREIGN KEY (IDTransporte) REFERENCES Proj.Transporte (IDTransporte);

CREATE TABLE Proj.Cliente
(
    NIF      INT CHECK (NIF > 0),
    Morada   VARCHAR(20),
    Nome     VARCHAR(20) NOT NULL,
    NumTelem INT,
    PRIMARY KEY (NIF)
);

CREATE TABLE Proj.Funcionario
(
    NumFunc  INT CHECK (NumFunc > 0),
    Morada   VARCHAR(20) NOT NULL,
    Nome     VARCHAR(20) NOT NULL,
    NumTelem INT         NOT NULL,
    NumLoja  INT CHECK (NumLoja > 0),
    PRIMARY KEY (NumFunc),
    FOREIGN KEY (NumLoja) REFERENCES Proj.Loja (NumLoja)
);

CREATE TABLE Proj.Compra
(
    NumCompra INT CHECK (NumCompra > 0),
    Data      DATE NOT NULL,
    Montante  DECIMAL(4, 2) CHECK (Montante > 0), -- 4 N�meros para as unidades e 2 para casas decimais
    NIF       INT CHECK (NIF > 0),
    NumFunc   INT CHECK (NumFunc > 0),
    PRIMARY KEY (NumCompra),
    FOREIGN KEY (NIF) REFERENCES Proj.Cliente (NIF),
    FOREIGN KEY (NumFunc) REFERENCES Proj.Funcionario (NumFunc)
);

ALTER TABLE Proj.Artigo
    ADD CONSTRAINT NUM_COMPRA FOREIGN KEY (NumCompra) REFERENCES Proj.Compra (NumCompra);

CREATE TABLE Proj.Devolucao
(
    IDDevolucao INT CHECK (IDDevolucao > 0),
    Data        DATE NOT NULL,
    Montante    DECIMAL(4, 2) CHECK (Montante > 0), -- 4 N�meros para as unidades e 2 para casas decimais
    NIF         INT CHECK (NIF > 0),
    NumFunc     INT CHECK (NumFunc > 0),
    PRIMARY KEY (IDDevolucao),
    FOREIGN KEY (NIF) REFERENCES Proj.Cliente (NIF),
    FOREIGN KEY (NumFunc) REFERENCES Proj.Funcionario (NumFunc)
);

ALTER TABLE Proj.Artigo
    ADD CONSTRAINT ID_DEVOLUCAO FOREIGN KEY (IDDevolucao) REFERENCES Proj.Devolucao (IDDevolucao);

-- INSERT DATA
INSERT INTO Proj.Artigo(Codigo, Preco, Nome, Categoria, QuantArtigos, NumLoja, IDArmazem, IDTransporte, NumCompra,
                        IDDevolucao)
VALUES (156428, 69.99, 'Camisola Equipamento SLB', 'Vestu�rio', 12, 1, Null, Null, Null, Null),
       (156428, 69.29, 'Camisola Equipamento SLB', 'Vestu�rio', 12, Null, 120, Null, Null, Null),
       (245876, 59.99, 'Camisola Equipamento SCP', 'Vestu�rio', 18, 1, Null, Null, Null, Null),
       (302548, 64.49, 'Camisola Equipamento FCP', 'Vestu�rio', 14, 2, Null, Null, Null, Null),
       (402458, 49.99, 'Chuteiras Nike Mercurial', 'Cal�ado', 1, Null, Null, 175896, Null, Null),
       (578956, 19.99, 'Bola Euro 2020', 'Acess�rios', 2, Null, Null, Null, 30000, Null),
       (636548, 9.49, 'Mochila Puma Phase', 'Acess�rios', 15, 4, Null, Null, Null, Null),
       (758769, 15.49, 'Casaco de Ciclismo', 'Vestu�rio', 2, Null, Null, Null, 30352, Null),
       (758769, 15.49, 'Casaco de Ciclismo', 'Vestu�rio', 2, Null, Null, Null, Null, 23),
       (825489, 39.19, 'Sapatilha Adidas Runfalcon', 'Cal�ado', 20, 4, Null, Null, Null, Null),
       (825489, 39.19, 'Sapatilha Adidas Runfalcon', 'Cal�ado', Null, Null, Null, Null, 31671, Null),
       (825489, 39.19, 'Sapatilha Adidas Runfalcon', 'Cal�ado', Null, Null, Null, Null, Null, 30),
       (958945, 42.39, 'Sapatilha Nike Zoom', 'Cal�ado', 4, Null, Null, Null, 30050, Null),
       (101568, 39.99, 'Smartwatch Innova', 'Acess�rios', 50, Null, 130, Null, Null, Null),
       (101568, 39.99, 'Smartwatch Innova', 'Acess�rios', Null, Null, Null, Null, 30098, Null),
       (101568, 39.99, 'Smartwatch Innova', 'Acess�rios', Null, Null, Null, Null, Null, 11),
       (114526, 19.99, 'Pulseira Desportiva', 'Acess�rios', 20, 5, Null, Null, Null, Null),
       (128627, 41.24, 'Botas Converse Rival', 'Cal�ado', 30, 3, Null, Null, Null, Null),
       (130058, 19.99, 'Cal��es Puma', 'Vestu�rio', 20, 2, Null, Null, Null, Null),
       (130058, 19.99, 'Cal��es Puma', 'Vestu�rio', Null, Null, Null, Null, 31527, Null),
       (130058, 19.99, 'Cal��es Puma', 'Vestu�rio', Null, Null, Null, Null, Null, 35),
       (148600, 14.99, 'Bola futebol Adidas final 19', 'Acess�rios', 15, 5, Null, Null, Null, Null),
       (148600, 14.99, 'Bola futebol Adidas final 19', 'Acess�rios', Null, Null, Null, Null, 30027, Null),
       (148600, 14.99, 'Bola futebol Adidas final 19', 'Acess�rios', Null, Null, Null, Null, Null, 17),
       (154009, 14.99, 'Bola basquetebol Nike baller', 'Acess�rios', 10, 5, 140, Null, Null, Null),
       (154009, 14.99, 'Bola basquetebol Nike baller', 'Acess�rios', Null, Null, Null, Null, 30100, Null),
       (165860, 89.99, 'Raquete T�nis Head Ig Challenge Pro', 'Acess�rios', 12, 2, 130, Null, Null, Null),
       (165860, 89.99, 'Raquete T�nis Head Ig Challenge Pro', 'Acess�rios', Null, Null, Null, Null, 30110, Null),
       (165860, 89.99, 'Raquete T�nis Head Ig Challenge Pro', 'Acess�rios', Null, Null, Null, Null, Null, 42),
       (177893, 9.99, 'Tapete gin�stica/yoga', 'Acess�rios', Null, Null, Null, Null, 30167, Null),
       (177893, 9.99, 'Tapete gin�stica/yoga', 'Acess�rios', Null, Null, Null, Null, Null, 50),
       (177893, 9.99, 'Tapete gin�stica/yoga', 'Acess�rios', 30, 1, Null, Null, Null, Null),
       (187856, 9.99, 'Bolas T�nis Dunlop Bipk4', 'Acess�rios', 20, 4, 110, Null, Null, Null),
       (187856, 9.99, 'Bolas T�nis Dunlop Bipk4', 'Acess�rios', Null, Null, Null, Null, 32098, Null),
       (198935, 189.99, 'Halteres 20 Kg Bodytone', 'Acess�rios', 5, 1, Null, Null, Null, Null),
       (198935, 189.99, 'Halteres 20 Kg Bodytone', 'Acess�rios', Null, Null, Null, Null, 32167, Null),
       (198935, 189.99, 'Halteres 20 Kg Bodytone', 'Acess�rios', Null, Null, Null, Null, Null, 54),
       (204863, 179.99, 'Prancha de surf Quiksilver', 'Acess�rios', 4, 5, Null, Null, Null, Null),
       (204863, 179.99, 'Prancha de surf Quiksilver', 'Acess�rios', Null, Null, Null, Null, 32569, Null),
       (204863, 179.99, 'Prancha de surf Quiksilver', 'Acess�rios', Null, Null, Null, Null, Null, 15),
       (268194, 12.99, 'Luvas Boxe', 'Acess�rios', 6, 2, Null, Null, Null, Null),
       (268194, 12.99, 'Luvas Boxe', 'Acess�rios', Null, Null, Null, Null, 33456, Null),
       (268194, 12.99, 'Luvas Boxe', 'Acess�rios', Null, Null, Null, Null, Null, 60),
       (103571, 1.99, 'Touca nata��o', 'Acess�rios', 10, 5, Null, Null, Null, Null),
       (103571, 1.99, 'Touca nata��o', 'Acess�rios', Null, Null, Null, Null, 35897, Null)
    INSERT
INTO Proj.Loja(NumLoja, Nome, Localizacao)
VALUES (1, 'M&T Sports Line - Aveiro', 'Aveiro'),
    (2, 'M&T Sports Line - Porto', 'Porto'),
    (3, 'M&T Sports Line - Lisboa', 'Lisboa'),
    (4, 'M&T Sports Line - Braga', 'Braga'),
    (5, 'M&T Sports Line - Coimbra', 'Coimbra')

INSERT INTO Proj.Armazem(IDArmazem, Capacidade, NumLoja)
VALUES (140, 2500, 4),
    (120, 3500, 2),
    (160, 1500, 3),
    (110, 3000, 1),
    (130, 4500, 3),
    (170, 500, 1),
    (180, 100, 2),
    (150, 3000, 5)

INSERT INTO Proj.Transporte(IDTransporte, DataTransp, Destino)
VALUES (130254, '15-04-2020', 'Av. Dr. Louren�o Peixinho, Aveiro'),
    (120548, '22-01-2020', 'Av. da Boavista, Porto'),
    (175896, '24-02-2020', 'R. 21 de Agosto, Viseu'),
    (158930, '20-12-2019', 'Av. Central, Braga'),
    (175896, '16-04-2020', 'R. do Brasil, Coimbra'),
    (165489, '02-06-2019', 'R. da Prata, Lisboa'),
    (175896, '23-12-2019', 'R. Cidade de Bejar, Guarda'),
    (175896, '07-02-2020', 'R. DomJo�o III, Coimbra'),
    (100259, '12-03-2020', 'Estrada de Santiago, Aveiro'),
    (152338, '27-03-2020', 'R. Andrade Corvo, Braga'),
    (148667, '11-04-2020', 'R. Qta. da Alagoa, Viseu'),
    (175896, '03-03-2020', 'R. Alves Ro�adas, Guarda'),
    (147025, '09-03-2020', 'Pra�a da Liberdade, Porto'),
    (105896, '19-02-2020', 'R. Infantaria 23, Coimbra'),
    (175896, '03-11-2019', 'R. da Paz, Viseu'),
    (175896, '02-01-2020', 'Av. da Igreja, Guarda'),
    (120061, '23-01-2020', 'R. Serrado, Viseu')

INSERT INTO Proj.Cliente(NIF, Morada, Nome, NumTelem)
VALUES (234167270, 'R. Jos� Afonso, Aveiro', 'Maria Almeida', 925642354),
    (214139274, 'R. de Egas Moniz, Porto', 'Rui Tom�s', 913245559),
    (209969279, Null, 'Carlos Pedro', 965699300),
    (222162274, 'R. Ant�nio leit�o, Coimbra', 'J�ssica Costa', 938341311),
    (199197979, Null, 'M�rio Cruz', 960082959),
    (204138828, Null, 'Gon�alo Dias', 926602050),
    (232157891, 'R. dos Galos, Braga', 'Beatriz Prata', 960992011),
    (224234256, 'R. do Carmo, Aveiro', 'Guilherme Marques', 965711954),
    (212445278, 'R. S�o Frnaciso, Viseu', 'Liliana Barbosa', 919632390),
    (218368289, 'R. de J�lio Dinis, Porto', 'Pedro Costa', 934645399),
    (233464200, Null, 'Adriana Caetano', 920692366),
    (234162272, 'R. Paulo Em�lio, Viseu', 'In�s Pereira', 960047392),
    (232187270, 'R.da Pena, Porto', 'M�nica Alves', 915634555),
    (200197299, 'R. Jo�o Mendes, Viseu', 'Diogo Matos', 966642056),
    (214131211, 'R. da Avia��o Naval, Aveiro', 'Hugo Castro', 938445390),
    (224193223, 'R. Angola, Coimbra', 'Marisa Bernardo', 931946115),
    (209964271, 'R. Campo Alegre', 'Paulo Jesus', 965642354),
    (204003989, Null, 'Mariana Santos', 965642354),
    (219903030, 'R. Artur Bivar, Braga', 'Jo�o Pedro', 910012984),
    (211119034, 'R. Dr. Lu�z Ferreira', 'Marta Silva', 961119421),
    (297361230, 'R. Direita, Lisboa', 'Jo�o Silva', 925048749),
    (564820954, Null, 'Manuel Dias', 937174610),
    (438291045, 'Avenida Louren�o Peixinho, Aveiro', 'Susana Cardoso', 9145107491),
    (123456789, 'R. Em�dio Navarro, �gueda', 'Gracinda Sim�es', 927451250),
    (721352076, Null, 'Rui Pinheiro', 964397521),
    (547198471, 'Praceta da Liberdade, Gouveia', 'Afonso Ramos', 934560126),
    (312467201, 'R. Mouzinho da Silveira, Set�bal', 'Ema Duarte', 913739134),
    (538160672, 'Avenida 1� de Maio, Seia', 'Jos� Dias', 937143918),
    (123804287, 'Avenida Bombeiros Volunt�rios, Braga', 'Matilde Coelho', 967123986),
    (367192368, 'R. do Est�dio Municipal, Portim�o', 'Guilherme Domingues', 925291856)

INSERT INTO Proj.Funcionario(NumFunc, Morada, Nome, NumTelem, NumLoja)
VALUES (142056, 'Av. 25 de Abril, Aveiro', 'Pedro Martins', 915486359, 1),
    (110969, 'R. de S�o Martinho, Aveiro', 'Joana Vicente', 925436450, 4),
    (112034, 'R. de Tr�s, Porto', 'Andr� Rebelo', 963485656, 2),
    (123303, 'Av. 31 de Janeiro, Braga', 'M�rcio Costa', 965406233, 5),
    (130903, 'R. Andr� Soares, Braga', 'Marco Pinto', 936273351, 2),
    (133214, 'R. J�lio Dinis, Ovar', 'Pedro Almeida', 915363728, 2),
    (114936, 'R. das Violetas, Guimar�es', 'F�bio Costa', 965294842, 3),
    (111890, 'R. Gon�alinho, Viseu', 'Edgar Alves', 912263455, 3),
    (132996, 'R. Forno, Coimbra', 'Eduardo Marques', 965770350, 1),
    (132388, 'R. Dr. Manuel Alegre, �gueda', 'Andreia Cabe�as', 966452830, 4),
    (129078, 'R do Ferraz, Porto', 'Maria Bernardo', 960098423, 4),
    (123091, 'Av. dos Combatentes, Lisboa', 'Lu�s Br�s', 936754434, 1),
    (127635, 'R. Liberdade, Almada', 'Rita Pais', 916667543, 2),
    (133286, 'Av. Jaime Cortes�o, Set�bal', 'Angela Pires', 934500987, 3),
    (139901, 'R. Gago Coutinho, Cascais', 'Sofia Marques', 938900097, 1),
    (140002, 'R. Escura, Viseu', 'Aires Neves', 966787754, 4),
    (139998, 'Av. de S�o Miguel, Guarda', 'Nuno Sim�es', 927765321, 4),
    (134300, 'R. do Cortinhal, Mangualde', 'Madalena Prata', 928766568, 1),
    (131111, 'R. Calouste Gulbenkian, Guarda', 'Sara Gomes', 965568987, 2),
    (132456, 'R. Ch�, Porto', 'Maria Gr�cio', 960098909, 4),
    (136766, 'R. Cam�es, Celorico', 'Tiago Rocha', 939988760, 5),
    (139909, 'R. Manuel Martins, Trancoso', 'Gon�alo Oliveira', 929873232, 2),
    (140003, 'R. Direita, Gouveia', 'Alexandre Pereira', 967786543, 3),
    (139999, 'R. do Sol, Porto', 'S�rgio Costa', 928765654, 3),
    (120879, 'R. S. Gon�alinho, Aveiro', 'Joana Moreira', 925710756, 5)

INSERT INTO Proj.Compra(NumCompra, Data, Montante, NIF, NumFunc)
VALUES (30000, '1-04-2020', 19.99, 438291045, 139999),
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
VALUES (23, '22-04-2020', 15.49, 224234256, 112034),
    (15, '22-04-2020', 179.99, 234167270, 132388),
    (11, '22-04-2020', 39.99, 232157891, 133286),
    (17, '22-04-2020', 14.99, 232187270, 120879),
    (30, '22-04-2020', 39.19, 367192368, 139901),
    (35, '22-04-2020', 19.99, 721352076, 111890),
    (42, '22-04-2020', 89.99, 218368289, 133214),
    (50, '22-04-2020', 9.99, 209964271, 112034),
    (54, '22-04-2020', 189.99, 232157891, 114936),
    (60, '22-04-2020', 12.99, 212445278, 111890)