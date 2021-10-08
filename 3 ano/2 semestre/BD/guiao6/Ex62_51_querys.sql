use
empresa;

-- a) Obtenha uma lista contendo os projetos e funcion�rios (ssn e nome completo) que l� trabalham;
SELECT Pname, Ssn, Fname + ' ' + Minit + ' ' + Lname AS Nome_Completo
FROM ((ex51.Project JOIN ex51.Works_on ON Project.Pnumber = Works_on.Pno)
         JOIN ex51.Employee ON Works_on.Essn = Employee.Ssn);

-- b) Obtenha o nome de todos os funcion�rios supervisionados por 'Carlos D Gomes';
SELECT Fname
FROM ex51.Employee
         JOIN (SELECT Ssn AS SSsn
               FROM ex51.Employee
               WHERE Fname = 'Carlos'
                 AND Minit = 'D'
                 AND Lname = 'Gomes') AS pasta ON Super_ssn = SSsn

-- c) Para cada projeto, listar o seu nome e o n�mero de horas (por semana) gastos nesse projeto por todos os funcion�rios;
SELECT Pname, SUM(Hours) AS Total_Horas
FROM (ex51.Project JOIN ex51.Works_on ON Project.Pnumber = Works_on.Pno)
GROUP BY Pname;

-- d) Obter o nome de todos os funcion�rios do departamento 3 que trabalham mais de 20 horas por semana no projeto 'Aveiro Digital';
SELECT Fname, Lname
FROM ((ex51.Works_on JOIN ex51.Project ON Works_on.Pno = Project.Pnumber)
         JOIN ex51.Employee ON Works_on.Essn = Employee.Ssn)
WHERE Dno = 3
  AND Pname = 'Aveiro Digital'
  AND Hours > 20;

-- e) Nome dos funcion�rios que n�o trabalham para projetos;
SELECT Fname, Lname
FROM (ex51.Employee LEFT JOIN ex51.Works_on ON Works_on.Essn = Employee.Ssn)
WHERE Essn IS Null;

-- f) Para cada departamento, listar o seu nome e o sal�rio m�dio dos seus funcion�rios do sexo feminino;
SELECT Dname, AVG(Salary) AS Salario_Medio
FROM (ex51.Department JOIN ex51.Employee ON Department.Mgr_ssn = Employee.Ssn)
WHERE Sex = 'F'
GROUP BY Dname;

-- g) Obter uma lista de todos os funcion�rios com mais do que dois dependentes;
SELECT Fname, Lname, COUNT(Ssn) AS Num_Depend
FROM ex51.Dependent
         JOIN ex51.Employee ON Dependent.Essn = Employee.Ssn
GROUP BY Fname, Lname
HAVING COUNT(Ssn) > 2;

-- h) Obtenha uma lista de todos os funcion�rios gestores de departamento que n�o t�m dependentes;
SELECT Fname, Lname
FROM ((ex51.Department JOIN ex51.Employee ON Mgr_ssn = Ssn)
         LEFT OUTER JOIN ex51.Dependent ON Essn = Ssn)
WHERE Dependent_name IS NULL

-- i) Obter os nomes e endere�os de todos os funcion�rios que trabalham em, pelo menos, um projeto localizado em Aveiro mas o 
-- seu departamento n�o tem nenhuma localiza��o em Aveiro.
SELECT DISTINCT Fname, Lname, Address
FROM ((((ex51.Department JOIN ex51.Dept_locations ON department.Dnumber = Dept_locations.Dnumber)
    JOIN ex51.Employee ON Dno = department.Dnumber)
    JOIN ex51.Works_on ON Ssn = Essn)
         JOIN ex51.Project ON Pno = Pnumber)
WHERE Plocation = 'Aveiro'
  AND Dlocation != 'Aveiro';