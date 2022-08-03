echo "Transfering data to the waiter node."
sshpass -f password ssh sd111@l040101-ws07.ua.pt 'pkill -U sd111 java'
sshpass -f password ssh sd111@l040101-ws07.ua.pt 'rm -rf test/'
sshpass -f password ssh sd111@l040101-ws07.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd111@l040101-ws07.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password scp genclass.jar dirWaiter.zip sd111@l040101-ws07.ua.pt:/home/sd111/test/Restaurant
rm -r *.zip
echo "Decompressing data sent to the waiter node."
sshpass -f password ssh sd111@l040101-ws07.ua.pt 'cd test/Restaurant ; unzip -uq dirWaiter.zip'
echo "Executing program at the waiter node."
sshpass -f password ssh sd111@l040101-ws07.ua.pt 'cd test/Restaurant/dirWaiter; ./waiter.sh'
