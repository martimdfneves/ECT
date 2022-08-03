echo "Transfering data to the students node."
sshpass -f password ssh sd111@l040101-ws05.ua.pt 'pkill -U sd111 java'
sshpass -f password ssh sd111@l040101-ws05.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd111@l040101-ws05.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password scp genclass.jar dirStudent.zip sd111@l040101-ws05.ua.pt:/home/sd111/test/Restaurant
echo "Decompressing data sent to the students node."
sshpass -f password ssh sd111@l040101-ws05.ua.pt 'cd test/Restaurant ; unzip -uq dirStudent.zip'
echo "Executing program at the student node."
sshpass -f password ssh sd111@l040101-ws05.ua.pt 'cd test/Restaurant/dirStudent; ./student.sh' 
