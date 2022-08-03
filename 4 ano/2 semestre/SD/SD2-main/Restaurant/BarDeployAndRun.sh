echo "Transfering data to the bar node."
sshpass -f password ssh sd111@l040101-ws02.ua.pt 'pkill -U sd111 java'
sshpass -f password scp genclass.jar dirBar.zip sd111@l040101-ws02.ua.pt:/home/sd111
echo "Decompressing data sent to the bar node."
sshpass -f password ssh sd111@l040101-ws02.ua.pt 'unzip -qo dirBar.zip'
echo "Executing program at the bar node."
sshpass -f password ssh sd111@l040101-ws02.ua.pt 'java -classpath "genclass.jar:." serverSide.main.BarMain'
