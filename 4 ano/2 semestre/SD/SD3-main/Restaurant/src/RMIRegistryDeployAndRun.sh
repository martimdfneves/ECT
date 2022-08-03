echo "Transfering data to the RMIregistry node."
sshpass -f password ssh sd111@l040101-ws09.ua.pt 'kill $(lsof -t -i:22200)'
sshpass -f password ssh sd111@l040101-ws09.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd111@l040101-ws09.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password ssh sd111@l040101-ws09.ua.pt 'mkdir -p Public/classes/interfaces'
sshpass -f password ssh sd111@l040101-ws09.ua.pt 'rm -rf Public/classes/interfaces/*'
sshpass -f password ssh sd111@l040101-ws09.ua.pt 'mkdir -p Public/classes/commInfra'
sshpass -f password ssh sd111@l040101-ws09.ua.pt 'rm -rf Public/classes/commInfra/*'
sshpass -f password scp -r genclass.jar dirRMIRegistry.zip sd111@l040101-ws09.ua.pt:/home/sd111/test/Restaurant
echo "Decompressing data sent to the RMIregistry node."
sshpass -f password ssh -q sd111@l040101-ws09.ua.pt "cd test/Restaurant ; unzip -uq dirRMIRegistry.zip"
echo "Executing program at the RMIregistry node."
sshpass -f password ssh sd111@l040101-ws09.ua.pt 'cd test/Restaurant/dirRMIRegistry ; cp interfaces/*.class /home/sd111/Public/classes/interfaces ; cp commInfra/*.class /home/sd111/Public/classes/commInfra ; cp rmiregistry.sh /home/sd111'
sshpass -f password ssh -q sd111@l040101-ws09.ua.pt './rmiregistry.sh 22200'
