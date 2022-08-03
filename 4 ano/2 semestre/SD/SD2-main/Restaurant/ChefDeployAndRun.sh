echo "Transfering data to the chef node."
sshpass -f password ssh sd111@l040101-ws05.ua.pt 'pkill -U sd111 java'
sshpass -f password scp genclass.jar dirChef.zip sd111@l040101-ws05.ua.pt:/home/sd111
echo "Decompressing data sent to the chef node."
sshpass -f password ssh sd111@l040101-ws05.ua.pt 'unzip -qo dirChef.zip'
echo "Executing program at the chef node."
sshpass -f password ssh sd111@l040101-ws05.ua.pt 'java -classpath "genclass.jar:." clientSide.main.ChefMain' 
