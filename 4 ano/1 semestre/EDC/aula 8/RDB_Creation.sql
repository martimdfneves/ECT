CREATE TABLE Local
(
    ID        TEXT,
    Nome      TEXT,
    Descricao TEXT
);

CREATE TABLE Evento
(
    ID        TEXT,
    Nome      TEXT,
    Data      DATE,
    Inicio    TEXT,
    Fim       TEXT,
    Descricao TEXT,
    Local_ID  TEXT
);

CREATE TABLE Pessoa
(
    ID        TEXT,
    Nome      TEXT,
    Email     TEXT,
    Evento_ID TEXT
);

INSERT INTO Local
VALUES ('l1', 'UA', 'Universidade de Aveiro');
INSERT INTO Local
VALUES ('l2', 'UC', 'Universidade de Coimbra');
INSERT INTO Local
VALUES ('l3', 'UP', 'Universidade do Porto');
INSERT INTO Local
VALUES ('l4', 'UL', 'Universidade de Lisboa');

INSERT INTO Evento
VALUES ('e1', 'Abertura UA', '2017-09-20', '09:00', '18:00',
        'Festa comemorativa da abertura do ano letivo na Universidade de Aveiro', 'l1');
INSERT INTO Evento
VALUES ('e2', 'Abertura UC', '2017-09-30', '10:00', '19:00',
        'Festa comemorativa da abertura do ano letivo na Universidade de Coimbra', 'l2');
INSERT INTO Evento
VALUES ('e3', 'Abertura UP', '2017-09-25', '13:00', '20:00',
        'Festa comemorativa da abertura do ano letivo na Universidade do Porto', 'l3');
INSERT INTO Evento
VALUES ('e4', 'Abertura UL', '2017-09-27', '15:00', '23:00',
        'Festa comemorativa da abertura do ano letivo na Universidade de Lisboa', 'l4');

INSERT INTO Pessoa
VALUES ('p1', 'João', 'joao@ua.pt', 'e1');
INSERT INTO Pessoa
VALUES ('p2', 'José', 'jose@uc.pt', 'e2');
INSERT INTO Pessoa
VALUES ('p3', 'Manuel', 'manuel@up.pt', 'e3');
INSERT INTO Pessoa
VALUES ('p4', 'Joaquim', 'joaquim@ul.pt', 'e4');
INSERT INTO Pessoa
VALUES ('p5', 'Antonio', 'antonio@ua.pt', NULL);
