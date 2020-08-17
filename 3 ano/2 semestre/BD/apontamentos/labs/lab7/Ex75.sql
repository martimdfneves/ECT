-- Ex 7.5 -> Views
--use p2g5;

-- a)
-- i. Nome dos títulos e nome dos respetivos autores;--CREATE VIEW TITULO_AUTOR AS--SELECT au_fname + ' ' + au_lname AS author, title --FROM ((pubs.dbo.titles JOIN pubs.dbo.titleauthor ON pubs.dbo.titles.title_id=pubs.dbo.titleauthor.title_id)--JOIN pubs.dbo.authors ON pubs.dbo.titleauthor.au_id=pubs.dbo.authors.au_id)-- ii. Nome dos editores e nome dos respetivos funcionários;--CREATE VIEW EDITOR_FUNCIONARIO AS--SELECT pub_name, fname, lname--FROM (pubs.dbo.publishers JOIN pubs.dbo.employee --ON pubs.dbo.publishers.pub_id=pubs.dbo.employee.pub_id)-- iii. Nome das lojas e o nome dos títulos vendidos nessa loja;--CREATE VIEW TITULOS_PLOJA AS--SELECT stor_name, title --FROM ((pubs.dbo.stores JOIN pubs.dbo.sales ON pubs.dbo.stores.stor_id=pubs.dbo.sales.stor_id)--JOIN pubs.dbo.titles ON pubs.dbo.sales.title_id=pubs.dbo.titles.title_id)-- iv. Livros do tipo ‘Business’;--CREATE VIEW LIVROS_BUSINESS AS--SELECT title--FROM pubs.dbo.titles --WHERE pubs.dbo.titles.type='Business'--WITH CHECK OPTION-- b)
-- i. 
SELECT title FROM TITULO_AUTOR WHERE author LIKE '%Albert%';

-- ii. 
SELECT fname, lname FROM EDITOR_FUNCIONARIO WHERE pub_name = 'Binnet & Hardley';

-- iii.
SELECT * FROM TITULOS_PLOJA;

-- iv. 
SELECT * FROM LIVROS_BUSINESS;

-- c)SELECT stor_name, authorFROM TITULO_AUTOR JOIN TITULOS_PLOJA ON TITULO_AUTOR.title=TITULOS_PLOJA.titleORDER BY stor_name;
