use
AULASBD;
GO

CREATE SCHEMA aula10;
GO

--DROP TABLE IF EXISTS aula10.mytemp;
--DROP SCHEMA IF EXISTS aula10;

CREATE TABLE aula10.mytemp
(
    rid  BIGINT /*IDENTITY (1, 1)*/ NOT NULL,
    at1  INT NULL,
    at2  INT NULL,
    at3  INT NULL,
    lixo varchar(100) NULL
);
-- a)
CREATE
CLUSTERED INDEX rididx ON aula10.mytemp(rid);

--b)
DECLARE
@start_time DATETIME, @end_time DATETIME;
SET
@start_time = GETDATE();
PRINT
@start_time

DECLARE
@val INT;
SET
@val = 1;
DECLARE
@nelem INT;
SET
@nelem = 50000;

SET
nocount ON;

WHILE
@val <= @nelem
BEGIN
	DBCC
DROPCLEANBUFFERS;

	INSERT
aula10.mytemp(rid,at1,at2,at3,lixo)
SELECT cast((RAND() * @nelem * 40000) as INT),
       cast((RAND() * @nelem) as INT),
       cast((RAND() * @nelem) as INT),
       cast((RAND() * @nelem) as INT),
       'lixooo ..... lixooooo ..... lixooooo.... lixoooo';
SET
@val = @val + 1;
END

PRINT
'Inserted ' + str(@val) +' total elements!'

SET @end_time = GETDATE();
PRINT
'Total ms: ' + CONVERT(VARCHAR(20),
DATEDIFF(MILLISECOND, @start_time, @end_time));

-- Tempo total (hdd)
-- Inserted      50001 total elements!
-- Total ms: 110374
SELECT *
FROM sys.dm_db_index_physical_stats(db_id('AULASBD'),
                                    OBJECT_ID('aula10.mytemp'), NULL, NULL, 'DETAILED');
-- % Fragmenta��o: 99,2
-- % page_space:   68,3

-- c)
ALTER
INDEX ALL ON aula10.mytemp REBUILD WITH (FILLFACTOR=65);
-- run insertion here
-- Insertion times:
-- Fillfactor = 65 -> Total ms: 142106

ALTER
INDEX ALL ON aula10.mytemp REBUILD WITH (FILLFACTOR=80);
-- run insertion here
-- Insertion times:
-- Fillfactor = 80 -> Total ms: 165840

ALTER
INDEX ALL ON aula10.mytemp REBUILD WITH (FILLFACTOR=90);
-- run insertion here
-- Insertion times:
-- Fillfactor = 90 -> Total ms: 189070

-- Maiores fill factors tornam as paginas "leaf-level" mais cheias.
-- Isto significa que quando falamos de leitura, temos vantagens
-- em termos fill factores maiores pois existem menos paginas para ler.
-- No entanto quando falamos de escrita, ter um grande fill factor significa
-- que quando executarmos um insert, um page split vai ter de acontecer e esta
-- opera��o � pesada em termos computacionais.

-- O que observamos � que o fill factor maior cria maiores tempos comprovando
-- a ideia descrita acima.

-- d)

DROP TABLE IF EXISTS aula10.mytemp;
CREATE TABLE aula10.mytemp
(
    rid  BIGINT IDENTITY (1, 1) NOT NULL,
    at1  INT NULL,
    at2  INT NULL,
    at3  INT NULL,
    lixo varchar(100) NULL
);



SET
@start_time = GETDATE();
PRINT
@start_time

SET @val = 1;
SET
@nelem = 50000;

SET
nocount ON;

WHILE
@val <= @nelem
BEGIN
	DBCC
DROPCLEANBUFFERS;

	INSERT
aula10.mytemp(at1,at2,at3,lixo)
SELECT cast((RAND() * @nelem) as INT),
       cast((RAND() * @nelem) as INT),
       cast((RAND() * @nelem) as INT),
       'lixooo ..... lixooooo ..... lixooooo.... lixoooo';
SET
@val = @val + 1;
END

PRINT
'Inserted ' + str(@val) +' total elements!'

SET @end_time = GETDATE();
PRINT
'Total ms: ' + CONVERT(VARCHAR(20),
DATEDIFF(MILLISECOND, @start_time, @end_time));


-- Default fill factor:
-- time -> Total ms: 115620

ALTER
INDEX ALL ON aula10.mytemp REBUILD WITH (FILLFACTOR=65);
-- run insertion here
-- time -> Total ms: 128473

ALTER
INDEX ALL ON aula10.mytemp REBUILD WITH (FILLFACTOR=80);
-- run insertion here
-- time -> Total ms: 128403

ALTER
INDEX ALL ON aula10.mytemp REBUILD WITH (FILLFACTOR=90);
-- run insertion here
-- time -> Total ms: 120970

-- Como mencionado em cima, o facto de termos page splits � penalizador a 
-- nivel de desempenho. O facto de criarmos pkeys geradas automaticamente
-- com auto-incremento diminui estes page splits tornando assim
-- uma chave com auto incremento melhor a nivel de desempenho.

-- e) 

-- No indexes
DROP
INDEX IF EXISTS rididx on aula10.mytemp;
-- run insert here
-- time -> Total ms: 108220

-- All indexes
CREATE
INDEX rididx on aula10.mytemp(rid);
CREATE
INDEX at1idx on aula10.mytemp(at1);
CREATE
INDEX at2idx on aula10.mytemp(at2);
CREATE
INDEX at3idx on aula10.mytemp(at3);
CREATE
INDEX lixoidx on aula10.mytemp(lixo);
-- run insert here
-- time -> Total ms: 338577

-- Conclus�o:
-- Os indices melhoram BASTANTE os tempos de procura num base de dados,
-- no entanto quando se trata de inserir dados, quanto mais indices temos pior
-- Isto deve-se principalmente pelo facto de, para alem da inser��o dos dados,
-- o sgbd executa tmb instru��es em rela��o aos indices (p ex escrever 
-- informa��o relativa aos indices em paginas)