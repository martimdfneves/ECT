#! /bin/bash

sudo python3 setup.py install
sleep 7
path=$(pwd)
gnome-terminal --tab --title="vanetza" --working-directory=$path/vanetza -- /bin/bash -c "sudo docker-compose up; exec /bin/bash" 
sleep 7
gnome-terminal --tab --title="app.py" --working-directory=$path/app -- /bin/bash -c "python3 app.py; exec /bin/bash" 
sleep 1
gnome-terminal --tab --title="Web App" --working-directory=$path -- /bin/bash -c "google-chrome http://127.0.0.1:5000; exec /bin/bash" 