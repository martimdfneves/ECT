echo "Transfering data to the table node."
sshpass -f password ssh sd111@l040101-ws04.ua.pt 'pkill -U sd111 java'
sshpass -f password scp genclass.jar dirTable.zip sd111@l040101-ws04.ua.pt:/home/sd111
echo "Decompressing data sent to the table node."
sshpass -f password ssh sd111@l040101-ws04.ua.pt 'unzip -qo dirTable.zip'
echo "Executing program at the table node."
sshpass -f password ssh sd111@l040101-ws04.ua.pt 'java -classpath "genclass.jar:." serverSide.main.TableMain' 