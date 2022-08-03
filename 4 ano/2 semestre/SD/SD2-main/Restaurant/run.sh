xterm  -geometry 70x46-470+80 -T "General Repository" -hold -e "java -cp "genclass.jar:." serverSide.main.GeneralReposMain" &
xterm  -geometry 65x12+10+0 -T "Table" -hold -e "java -cp "genclass.jar:." serverSide.main.TableMain" &
xterm  -geometry 65x15+10+250 -T "Bar" -hold -e "java -cp "genclass.jar:." serverSide.main.BarMain" &
xterm  -geometry 65x14+10+505 -T "Kitchen" -hold -e "java -cp "genclass.jar:." serverSide.main.KitchenMain" &
sleep 1
xterm  -geometry 65x12-10+0 -T "Chef" -hold -e "java -cp "genclass.jar:." clientSide.main.ChefMain" &
xterm  -geometry 65x12-10+250 -T "Waiter" -hold -e "java -cp "genclass.jar:." clientSide.main.WaiterMain" &
xterm  -geometry 65x20-10+460 -T "Students" -hold -e "java -cp "genclass.jar:." clientSide.main.StudentMain" &
