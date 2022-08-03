echo "Transfering data to the students node."
sshpass -f password ssh sd111@l040101-ws06.ua.pt 'pkill -U sd111 java'
sshpass -f password scp genclass.jar dirStudent.zip sd111@l040101-ws06.ua.pt:/home/sd111
echo "Decompressing data sent to the students node."
sshpass -f password ssh sd111@l040101-ws06.ua.pt 'unzip -qo dirStudent.zip'
echo "Executing program at the student node."
sshpass -f password ssh sd111@l040101-ws06.ua.pt 'java -classpath "genclass.jar:." clientSide.main.StudentMain' 
