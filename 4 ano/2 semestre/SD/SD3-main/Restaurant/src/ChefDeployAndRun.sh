echo "Transfering data to the chef node."
sshpass -f password ssh sd111@l040101-ws02.ua.pt 'pkill -U sd111 java'
sshpass -f password ssh sd111@l040101-ws02.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd111@l040101-ws02.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password scp genclass.jar dirChef.zip sd111@l040101-ws02.ua.pt:/home/sd111/test/Restaurant
echo "Decompressing data sent to the chef node."
sshpass -f password ssh sd111@l040101-ws02.ua.pt 'cd test/Restaurant ; unzip -qo dirChef.zip'
echo "Executing program at the chef node."
sshpass -f password ssh sd111@l040101-ws02.ua.pt 'cd test/Restaurant/dirChef; ./chef.sh' 
