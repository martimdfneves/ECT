echo "Transfering data to the table node."
sshpass -f password ssh sd111@l040101-ws06.ua.pt 'pkill -U sd111 java'
sshpass -f password ssh sd111@l040101-ws06.ua.pt 'rm -rf test/'
sshpass -f password ssh sd111@l040101-ws06.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd111@l040101-ws06.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password scp genclass.jar dirTable.zip sd111@l040101-ws06.ua.pt:/home/sd111/test/Restaurant
echo "Decompressing data sent to the table node."
sshpass -f password ssh sd111@l040101-ws06.ua.pt 'cd test/Restaurant ; unzip -uq dirTable.zip'
echo "Executing program at the table node."
sshpass -f password ssh sd111@l040101-ws06.ua.pt 'cd test/Restaurant/dirTable ; ./table.sh'
