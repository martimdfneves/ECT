-- Ex 53

-- a)
SELECT paciente.numUtente, nome
FROM paciente 
left outer JOIN prescricao
ON paciente.numUtente=prescricao.numUtente
WHERE numPresc IS NULL

-- b)
SELECT especialidade, count(especialidade) AS contagem
FROM medico 
JOIN prescricao
ON numMedico=numSNS
GROUP BY especialidade

--c)
SELECT farmacia, count(farmacia) AS cnt
FROM prescricao 
RIGHT outer JOIN farmacia
ON farmacia=nome
GROUP BY farmacia

--d)
SELECT nome 
FROM farmaco 
LEFT outer JOIN presc_farmaco
ON nome=nomeFarmaco
WHERE numPresc IS NULL

--e)
SELECT farmacia, numRegFarm, count(numRegFarm) AS CountFarmacos
FROM presc_farmaco 
JOIN prescricao
ON prescricao.numPresc=presc_farmaco.numPresc
WHERE farmacia IS NOT NULL
GROUP BY farmacia, numRegFarm

--f)
SELECT pres1.numUtente
FROM prescricao AS pres1, prescricao AS pres2
WHERE pres1.numUtente=pres2.numUtente AND pres1.numMedico!=pres2.numMedico;




  