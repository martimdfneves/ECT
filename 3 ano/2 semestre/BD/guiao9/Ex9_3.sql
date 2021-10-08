-- i)
CREATE
UNIQUE CLUSTERED INDEX IxSSN ON Employee(Ssn);

-- ii)
CREATE
CLUSTERED INDEX IxEmployeeName ON Employee(FName, LName);

-- iii)
CREATE
INDEX IxDnoON Employee(Dno);
CREATE
UNIQUE CLUSTERED INDEX IxDnumber ON Department(Dnumber);

-- iv)
CREATE
INDEX IxPnoON Works_On(Pno);
CREATE
UNIQUE CLUSTERED INDEX IxPnumber ON Project(Pnumber);

-- v)
CREATE
CLUSTERED INDEX IxEssn ON Dependent(Essn);

-- vi)
CREATE
CLUSTERED INDEX IxDnum On Project(Dnum);