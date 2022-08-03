echo "Transfering data to the logger node."
sshpass -f password ssh sd111@l040101-ws03.ua.pt 'pkill -U sd111 java'
sshpass -f password ssh sd111@l040101-ws03.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd111@l040101-ws03.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password scp genclass.jar dirGeneralRepos.zip sd111@l040101-ws03.ua.pt:/home/sd111/test/Restaurant
echo "Decompressing data sent to the logger node."
sshpass -f password ssh sd111@l040101-ws03.ua.pt 'cd test/Restaurant ; unzip -uq dirGeneralRepos.zip'
echo "Executing program at the logger node."
sshpass -f password ssh sd111@l040101-ws03.ua.pt 'cd test/Restaurant/dirGeneralRepos ; ./generalRepos.sh'
