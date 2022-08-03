echo "Transfering data to the bar node."
sshpass -f password ssh sd111@l040101-ws01.ua.pt 'pkill -U sd111 java'
sshpass -f password ssh sd111@l040101-ws01.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd111@l040101-ws01.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password scp genclass.jar dirBar.zip sd111@l040101-ws01.ua.pt:/home/sd111/test/Restaurant
echo "Decompressing data sent to the bar node."
sshpass -f password ssh sd111@l040101-ws01.ua.pt 'cd test/Restaurant ; unzip -uq dirBar.zip'
echo "Executing program at the bar node."
sshpass -f password ssh sd111@l040101-ws01.ua.pt 'cd test/Restaurant/dirBar ; ./bar.sh'
