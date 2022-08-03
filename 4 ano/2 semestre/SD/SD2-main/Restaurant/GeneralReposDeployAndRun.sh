echo "Transfering data to the logger node."
sshpass -f password ssh sd111@l040101-ws01.ua.pt 'pkill -U sd111 java'
sshpass -f password scp genclass.jar dirGeneralRepos.zip sd111@l040101-ws01.ua.pt:/home/sd111
echo "Decompressing data sent to the logger node."
sshpass -f password ssh sd111@l040101-ws01.ua.pt 'unzip -qo dirGeneralRepos.zip'
echo "Executing program at the logger node."
sshpass -f password ssh sd111@l040101-ws01.ua.pt 'java -classpath "genclass.jar:." serverSide.main.GeneralReposMain' 
