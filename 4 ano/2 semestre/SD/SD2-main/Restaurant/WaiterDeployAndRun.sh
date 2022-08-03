echo "Transfering data to the waiter node."
sshpass -f password ssh sd111@l040101-ws07.ua.pt 'pkill -U sd111 java'
sshpass -f password scp genclass.jar dirWaiter.zip sd111@l040101-ws07.ua.pt:/home/sd111
rm -r *.zip
echo "Decompressing data sent to the waiter node."
sshpass -f password ssh sd111@l040101-ws07.ua.pt 'unzip -qo dirWaiter.zip'
echo "Executing program at the waiter node."
sshpass -f password ssh sd111@l040101-ws07.ua.pt 'java -classpath "genclass.jar:." clientSide.main.WaiterMain' 