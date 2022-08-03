echo "Transfering data to the kitchen node."
sshpass -f password ssh sd111@l040101-ws03.ua.pt 'pkill -U sd111 java'
sshpass -f password scp genclass.jar dirKitchen.zip sd111@l040101-ws03.ua.pt:/home/sd111
echo "Decompressing data sent to the kitchen node."
sshpass -f password ssh sd111@l040101-ws03.ua.pt 'unzip -qo  dirKitchen.zip'
echo "Executing program at the kitchen node."
sshpass -f password ssh sd111@l040101-ws03.ua.pt 'java -classpath "genclass.jar:." serverSide.main.KitchenMain' 
