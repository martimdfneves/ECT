-- i)
CREATE UNIQUE CLUSTERED INDEX IxSSN ON Employee(Ssn);

-- ii)
CREATE CLUSTERED INDEX IxEmployeeName ON Employee(FName, LName);

-- iii)
CREATE INDEX IxDnoON Employee(Dno);
CREATE UNIQUE CLUSTERED INDEX IxDnumberON Department(Dnumber);

-- iv)
CREATE INDEX IxPnoON Works_On(Pno);
CREATE UNIQUE CLUSTERED INDEX IxPnumberON Project(Pnumber);

-- v)
CREATE CLUSTERED INDEX IxEssnON Dependent(Essn);

-- vi)
CREATE CLUSTERED INDEX IxDnum On Project(Dnum);