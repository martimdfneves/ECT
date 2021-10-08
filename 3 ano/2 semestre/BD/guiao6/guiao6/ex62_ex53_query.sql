USE
Comprimidos;
GO

-- a)
SELECT NR_UTENTE, NOME
FROM ex53a.Paciente
         left outer JOIN ex53a.Prescricao
                         ON Paciente.NR_UTENTE = Prescricao.PACIENTE
WHERE NUMERO IS NULL

-- b)
SELECT especialidade, count(especialidade) AS contagem
FROM ex53a.Medico
         JOIN ex53a.Prescricao
              ON Prescricao.MEDICO = Medico.SNS
GROUP BY especialidade

--c)
SELECT FARMACIA, count(FARMACIA) AS cnt
FROM ex53a.Prescricao
         RIGHT outer JOIN ex53a.Farmacia
                          ON Prescricao.FARMACIA = Farmacia.NOME
GROUP BY FARMACIA

--d)
SELECT Farmaco.NOME
FROM ex53a.Farmaco
         LEFT outer JOIN ex53.Farmaceutica
                         ON Farmaceutica.NUMERO = 906
         LEFT outer JOIN ex53a.Venda
                         ON Farmaceutica.NOME = Venda.NOMEFARMACO
WHERE NUMPRESC IS NULL

--e)
SELECT numRegFarm, count(numRegFarm) AS CountFarmacos
FROM ex53a.Venda
         JOIN ex53a.Prescricao
              ON prescricao.NUMERO = Venda.numPresc
WHERE farmacia IS NOT NULL
GROUP BY farmacia, numRegFarm

--f)
SELECT pres1.PACIENTE
FROM ex53a.Prescricao AS pres1,
     ex53a.Prescricao AS pres2
WHERE pres1.NUMERO = pres2.NUMERO
  AND pres1.MEDICO!=pres2.MEDICO;




  