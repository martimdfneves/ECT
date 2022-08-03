echo "Removing directories."
rm -rf dir*/*/
echo "Compiling source code."
javac --release 8 -cp genclass.jar */*.java */*/*.java
echo "Distributing intermediate code to the different execution environments."

echo "  RMI registry"
rm -rf dirRMIRegistry/interfaces dirRMIRegistry/commInfra
mkdir -p dirRMIRegistry/interfaces dirRMIRegistry/commInfra
cp interfaces/*.class dirRMIRegistry/interfaces
cp commInfra/*.class dirRMIRegistry/commInfra

echo "  Register Remote Objects"
rm -rf dirRegistry/serverSide dirRegistry/interfaces dirRegistry/commInfra
mkdir -p dirRegistry/serverSide dirRegistry/serverSide/main dirRegistry/serverSide/objects dirRegistry/interfaces dirRegistry/commInfra
cp commInfra/* dirRegistry/commInfra
cp serverSide/main/RegisterRemoteObjectMain.class dirRegistry/serverSide/main
cp serverSide/objects/RegisterRemoteObject.class dirRegistry/serverSide/objects
cp interfaces/RegisterInterface.class dirRegistry/interfaces

echo "  General Repository of Information"
rm -rf dirGeneralRepos/serverSide dirGeneralRepos/clientSide dirGeneralRepos/interfaces
mkdir -p dirGeneralRepos/serverSide dirGeneralRepos/serverSide/main dirGeneralRepos/serverSide/objects dirGeneralRepos/interfaces \
         dirGeneralRepos/clientSide dirGeneralRepos/clientSide/entities dirGeneralRepos/commInfra
cp commInfra/* dirGeneralRepos/commInfra
cp serverSide/main/GeneralReposMain.class dirGeneralRepos/serverSide/main
cp serverSide/objects/GeneralRepos.class dirGeneralRepos/serverSide/objects
cp interfaces/RegisterInterface.class interfaces/GeneralReposInterface.class dirGeneralRepos/interfaces
cp clientSide/entities/ChefState.class clientSide/entities/WaiterState.class clientSide/entities/StudentState.class dirGeneralRepos/clientSide/entities

echo "  Kitchen"
rm -rf dirKitchen/serverSide dirKitchen/clientSide dirKitchen/interfaces dirKitchen/commInfra
mkdir -p dirKitchen/serverSide dirKitchen/serverSide/main dirKitchen/serverSide/objects dirKitchen/interfaces \
         dirKitchen/clientSide dirKitchen/clientSide/entities dirKitchen/commInfra
cp serverSide/main/KitchenMain.class dirKitchen/serverSide/main
cp serverSide/objects/Kitchen.class dirKitchen/serverSide/objects
cp interfaces/*.class dirKitchen/interfaces
cp clientSide/entities/ChefState.class clientSide/entities/WaiterState.class dirKitchen/clientSide/entities
cp commInfra/* dirKitchen/commInfra

echo "  Bar"
rm -rf dirBar/serverSide dirBar/clientSide dirBar/interfaces dirBar/commInfra
mkdir -p dirBar/serverSide dirBar/serverSide/main dirBar/serverSide/objects dirBar/interfaces \
         dirBar/clientSide dirBar/clientSide/entities dirBar/commInfra
cp commInfra/* dirBar/commInfra
cp serverSide/main/BarMain.class dirBar/serverSide/main
cp serverSide/objects/Bar.class dirBar/serverSide/objects
cp interfaces/*.class dirBar/interfaces
cp clientSide/entities/ChefState.class clientSide/entities/WaiterState.class clientSide/entities/StudentState.class clientSide/entities/Student.class clientSide/entities/Waiter.class \
dirBar/clientSide/entities
cp commInfra/*.class dirBar/commInfra

echo "  Table"
rm -rf dirTable/serverSide dirTable/clientSide dirTable/interfaces dirTable/commInfra
mkdir -p dirTable/serverSide dirTable/serverSide/main dirTable/serverSide/objects dirTable/interfaces \
         dirTable/clientSide dirTable/clientSide/entities dirTable/commInfra
cp serverSide/main/TableMain.class dirTable/serverSide/main
cp serverSide/objects/Table.class dirTable/serverSide/objects
cp interfaces/*.class dirTable/interfaces
cp clientSide/entities/WaiterState.class clientSide/entities/StudentState.class clientSide/entities/Student.class clientSide/entities/Waiter.class dirTable/clientSide/entities
cp commInfra/*.class dirTable/commInfra

echo "  Chef"
rm -rf dirChef/clientSide dirChef/interfaces
mkdir -p dirChef/clientSide dirChef/clientSide/main dirChef/clientSide/entities \
         dirChef/interfaces dirChef/commInfra
cp clientSide/main/ChefMain.class dirChef/clientSide/main
cp clientSide/entities/Chef.class clientSide/entities/ChefState.class dirChef/clientSide/entities
cp interfaces/* dirChef/interfaces
cp commInfra/*.class dirChef/commInfra

echo "  Waiter"
rm -rf dirWaiter/serverSide dirWaiter/clientSide dirWaiter/interfaces
mkdir -p dirWaiter/clientSide dirWaiter/clientSide/main dirWaiter/clientSide/entities \
         dirWaiter/interfaces dirWaiter/commInfra
cp clientSide/main/WaiterMain.class dirWaiter/clientSide/main
cp clientSide/entities/Waiter.class clientSide/entities/WaiterState.class dirWaiter/clientSide/entities
cp interfaces/* dirWaiter/interfaces
cp commInfra/*.class dirWaiter/commInfra

echo "  Student"
rm -rf dirStudent/clientSide dirStudent/interfaces
mkdir -p dirStudent/clientSide dirStudent/clientSide/main dirStudent/clientSide/entities \
         dirStudent/interfaces dirStudent/commInfra
cp clientSide/main/StudentMain.class dirStudent/clientSide/main
cp clientSide/entities/Student.class clientSide/entities/StudentState.class dirStudent/clientSide/entities
cp interfaces/* dirStudent/interfaces
cp commInfra/*.class dirStudent/commInfra

echo "Compressing execution environments."
echo "  RMI registry"
rm -f  dirRMIRegistry.zip
zip -rq dirRMIRegistry.zip dirRMIRegistry
echo "  Register Remote Objects"
rm -f  dirRegistry.zip
zip -rq dirRegistry.zip dirRegistry
echo "  General Repository of Information"
rm -f  dirGeneralRepos.zip
zip -rq dirGeneralRepos.zip dirGeneralRepos
echo "  Kitchen"
rm -f  dirKitchen.zip
zip -rq dirKitchen.zip dirKitchen
echo "  Bar"
rm -f  dirBar.zip
zip -rq dirBar.zip dirBar
echo "  Table"
rm -f  dirTable.zip
zip -rq dirTable.zip dirTable
echo "  Chef"
rm -f  dirChef.zip
zip -rq dirChef.zip dirChef
echo "  Waiter"
rm -f  dirWaiter.zip
zip -rq dirWaiter.zip dirWaiter
echo "  Student"
rm -f  dirStudent.zip
zip -rq dirStudent.zip dirStudent
