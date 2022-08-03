rm -r bin
xterm  -geometry 65x12+10+0 -T "Table" -hold -e "./TableDeployAndRun.sh" &
xterm  -geometry 70x46-470+80 -T "General Repository" -hold -e "./GeneralReposDeployAndRun.sh" &
xterm  -geometry 65x15+10+250 -T "Bar" -hold -e "./BarDeployAndRun.sh" &
xterm  -geometry 65x14+10+505 -T "Kitchen" -hold -e "./KitchenDeployAndRun.sh" &
sleep 1
xterm  -geometry 65x20-10+460 -T "Students" -hold -e "./StudentsDeployAndRun.sh" &
xterm  -geometry 65x12-10+0 -T "Chef" -hold -e "./ChefDeployAndRun.sh" &
xterm  -geometry 65x12-10+250 -T "Waiter" -hold -e "./WaiterDeployAndRun.sh" &
