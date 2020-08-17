use pubs;

-- a) Todos os tuplos da tabela autores (authors)
SELECT * FROM dbo.authors;

-- b) O primeiro nome, o �ltimo nome e o telefone dos autores
SELECT au_fname, au_lname, phone FROM dbo.authors;

-- c) Consulta definida em b) mas ordenada pelo primeiro nome (ascendente) e depois o �ltimo nome (ascendente);
SELECT au_fname, au_lname, phone FROM dbo.authors
ORDER BY au_fname, au_lname;

-- d) Consulta definida em c) mas renomeando os atributos para (first_name, last_name, telephone);
SELECT au_fname AS first_name, au_lname AS last_name, phone AS telephone FROM dbo.authors
ORDER BY au_fname, au_lname;

-- e) Consulta definida em d) mas s� os autores da Calif�rnia (CA) cujo �ltimo nome � diferente de �Ringer�; 
SELECT au_fname AS first_name, au_lname AS last_name, phone AS telephon FROM dbo.authors
WHERE state='CA' AND NOT au_lname='Ringer'
ORDER BY au_fname, au_lname;

-- f) Todas as editoras (publishers) que tenham �Bo� em qualquer parte do nome;
SELECT * FROM dbo.publishers
WHERE pub_name LIKE '%Bo%';

-- g) Nome das editoras que t�m pelo menos uma publica��o do tipo �Business�;
SELECT DISTINCT pub_name 
FROM (dbo.publishers JOIN dbo.titles ON dbo.publishers.pub_id=dbo.titles.pub_id) 
WHERE type='Business';

-- h) N�mero total de vendas de cada editora
SELECT pub_name, SUM(qty) AS total_vendas 
FROM ((dbo.publishers JOIN dbo.titles ON dbo.publishers.pub_id=dbo.titles.pub_id) 
JOIN dbo.sales ON dbo.sales.title_id=dbo.titles.title_id)
GROUP BY pub_name;

-- i) N�mero total de vendas de cada editora agrupado por t�tulo;
SELECT pub_name, title, SUM(qty) AS total_vendas 
FROM ((dbo.publishers JOIN dbo.titles ON dbo.publishers.pub_id=dbo.titles.pub_id) 
JOIN dbo.sales ON dbo.sales.title_id=dbo.titles.title_id)
GROUP BY pub_name, title;

-- j) Nome dos t�tulos vendidos pela loja �Bookbeat�;
SELECT title
FROM ((dbo.stores JOIN dbo.sales ON dbo.stores.stor_id=dbo.sales.stor_id) 
JOIN dbo.titles ON dbo.sales.title_id=dbo.titles.title_id) 
WHERE stor_name='Bookbeat';

-- k) Nome de autores que tenham publica��es de tipos diferentes;
SELECT DISTINCT au_fname, au_lname
FROM ((dbo.authors JOIN dbo.titleauthor ON dbo.authors.au_id=dbo.titleauthor.au_id) 
JOIN dbo.titles ON dbo.titles.title_id=dbo.titleauthor.title_id) 
GROUP BY au_fname, au_lname
HAVING COUNT(au_fname) > 1

-- l) Para os t�tulos, obter o pre�o m�dio e o n�mero total de vendas agrupado por tipo (type) e 
-- editora (pub_id); 
SELECT type, pub_id, AVG(price) AS avg_price, SUM(qty) AS nr_total_vendas
FROM (dbo.titles JOIN dbo.sales ON dbo.titles.title_id=dbo.sales.title_id)
GROUP BY type, pub_id

-- m) Obter o(s) tipo(s) de t�tulo(s) para o(s) qual(is) o m�ximo de dinheiro �� cabe�a� (advance) � 
-- uma vez e meia superior � m�dia do grupo (tipo); 
SELECT type
FROM dbo.titles 
GROUP BY type
HAVING MAX(advance) > 1.5 * AVG(advance)

-- n) Obter, para cada t�tulo, nome dos autores e valor arrecadado por estes com a sua venda;
SELECT au_fname, au_lname, title, ((ytd_sales * price * royalty * royaltyper) / (100 * 100)) AS valor_arrecadado
FROM ((dbo.authors JOIN dbo.titleauthor ON dbo.authors.au_id=dbo.titleauthor.au_id)
JOIN dbo.titles ON dbo.titles.title_id=dbo.titleauthor.title_id)

-- o) Obter uma lista que inclu�a o n�mero de vendas de um t�tulo (ytd_sales), o seu
-- nome, a fatura��o total, o valor da fatura��o relativa aos autores e o valor da
-- fatura��o relativa � editora;
SELECT DISTINCT ytd_sales, title, ytd_sales * price AS faturacao, 
((ytd_sales * price * royalty) / 100) AS author_money,
((ytd_sales * price) * (100 - royalty) / 100) AS publisher_money
FROM ((dbo.authors JOIN dbo.titleauthor ON dbo.authors.au_id=dbo.titleauthor.au_id)
JOIN dbo.titles ON dbo.titles.title_id=dbo.titleauthor.title_id)
ORDER BY title;

-- p) Obter uma lista que inclu�a o n�mero de vendas de um t�tulo (ytd_sales), o seu
-- nome, o nome de cada autor, o valor da fatura��o de cada autor e o valor da
-- fatura��o relativa � editora;
SELECT ytd_sales, title, au_fname + ' ' + au_lname AS author,
ytd_sales * price AS faturacao,
((ytd_sales * price * royalty * royaltyper) / 10000) AS author_money,
((ytd_sales * price) * (100 - royalty) / 100) AS publisher_money
FROM ((dbo.authors JOIN dbo.titleauthor ON dbo.authors.au_id=dbo.titleauthor.au_id)
JOIN dbo.titles ON dbo.titles.title_id=dbo.titleauthor.title_id)
ORDER BY title;

-- q) Lista de lojas que venderam pelo menos um exemplar de todos os livros;SELECT stor_name, COUNT(sales.title_id) AS nr_titles_sold
FROM ((stores JOIN sales ON stores.stor_id=sales.stor_id) 
JOIN titles ON sales.title_id=titles.title_id)
GROUP BY stor_name
HAVING COUNT(sales.title_id) >= (SELECT COUNT(titles.title_id) FROM titles);

-- r) Lista de lojas que venderam mais livros do que a m�dia de todas as lojas.SELECT stor_name, SUM(qty) AS total_books_sold
FROM (stores JOIN sales ON stores.stor_id=sales.stor_id)
GROUP BY stor_name
HAVING SUM(qty) > (
SELECT AVG(avg_books_sold)
FROM (
SELECT stor_name, AVG(qty) AS avg_books_sold
FROM (stores JOIN sales ON stores.stor_id=sales.stor_id)
GROUP BY stor_name) AS aux);

-- s) Nome dos t�tulos que nunca foram vendidos na loja �Bookbeat�;
SELECT title
FROM titles
WHERE title_id NOT IN (SELECT title_id FROM sales LEFT JOIN stores ON sales.stor_id=stores.stor_id WHERE stor_name='Bookbeat')

-- t) Para cada editora, a lista de todas as lojas que nunca venderam t�tulos dessa editora;
-- ...

