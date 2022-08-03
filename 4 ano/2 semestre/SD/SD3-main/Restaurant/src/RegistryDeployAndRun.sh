echo "Transfering data to the Registry node."
sshpass -f password ssh sd111@l040101-ws09.ua.pt 'kill $(lsof -t -i:22201)'
sshpass -f password ssh sd111@l040101-ws09.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd111@l040101-ws09.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password scp -r genclass.jar dirRegistry.zip sd111@l040101-ws09.ua.pt:/home/sd111/test/Restaurant
echo "Decompressing data sent to the registry node."
sshpass -f password ssh -q sd111@l040101-ws09.ua.pt "cd test/Restaurant ; unzip -uq dirRegistry.zip"
echo "Executing program at the registry node."
sshpass -f password ssh -q sd111@l040101-ws09.ua.pt 'cd test/Restaurant/dirRegistry ; ./registry.sh'
