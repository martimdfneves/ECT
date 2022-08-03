echo "Transfering data to the kitchen node."
sshpass -f password ssh sd111@l040101-ws04.ua.pt 'pkill -U sd111 java'
sshpass -f password ssh sd111@l040101-ws04.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd111@l040101-ws04.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password scp genclass.jar dirKitchen.zip sd111@l040101-ws04.ua.pt:/home/sd111/test/Restaurant
echo "Decompressing data sent to the kitchen node."
sshpass -f password ssh sd111@l040101-ws04.ua.pt 'cd test/Restaurant ; unzip -uq  dirKitchen.zip'
echo "Executing program at the kitchen node."
sshpass -f password ssh sd111@l040101-ws04.ua.pt 'cd test/Restaurant/dirKitchen ; ./kitchen.sh' 
