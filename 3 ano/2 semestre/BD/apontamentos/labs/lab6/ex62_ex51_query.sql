-- Ex 51

-- a)
SELECT Pname,Fname,Lname,Ssn 
FROM works_on
JOIN project ON Pno=Pnumber
JOIN employee ON Essn=Ssn

-- b)
SELECT Fname
FROM employee 
JOIN (SELECT Ssn AS SSsn
FROM employee
WHERE Fname='Carlos' AND Minit='D' AND Lname='Gomes') AS pasta ON Super_ssn=SSsn

-- c)
SELECT Pname,SUM(Hours) AS TotalHoras
FROM project
JOIN works_on ON Pnumber=Pno
GROUP BY Pname

-- d)
SELECT Fname, Lname
FROM project
JOIN works_on ON Pnumber=Pno
JOIN employee ON Ssn=Essn
WHERE Hours>20 AND Dno=3 AND Pname='Aveiro Digital'

-- e)
SELECT Fname, Lname
FROM employee
LEFT outer JOIN works_on ON Ssn=Essn
WHERE Essn IS NULL

-- f)
SELECT Dname, AVG(Salary) AS avg_salary
FROM department
LEFT outer JOIN employee ON Dnumber=Dno
WHERE Sex='F'
GROUP BY Dname

-- g)
SELECT Fname, Lname, COUNT(Essn) AS cnt
FROM employee
JOIN dependent ON Ssn=Essn
GROUP BY Fname,Lname
HAVING cnt>2

-- h)
SELECT Fname, Lname
FROM department
JOIN employee ON Mgr_ssn=Ssn
LEFT outer JOIN dependent ON Essn=Ssn
WHERE Dependent_name IS NULLL

-- i)
SELECT Fname, Lname, Address
FROM department
LEFT outer JOIN dept_location ON department.Dnumber=dept_location.Dnumber
JOIN employee ON Dno=department.Dnumber
JOIN works_on ON Ssn=Essn
JOIN project ON Pno=Pnumber
WHERE Plocation = 'Aveiro' AND Dlocation!='Aveiro'
