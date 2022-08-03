echo "Compiling source code."
mkdir -p bin
javac --release 8 -cp genclass.jar */*.java */*/*.java -d bin
echo "Distributing intermediate code to the different execution environments."
mkdir -p /tmp/commInfra
mkdir -p /tmp/serverSide
mkdir -p /tmp/clientSide
cp -rf bin/commInfra/* /tmp/commInfra
cp -rf bin/serverSide/* /tmp/serverSide
cp -rf bin/clientSide/* /tmp/clientSide
a=$(pwd)
cd /tmp
echo "Compressing execution environments."
echo "  Bar"
zip -rq $a/dirBar.zip serverSide/main/BarMain.class serverSide/main/SimPar.class serverSide/entities/ServiceProviderAgent.class serverSide/sharedRegions/Bar.class  serverSide/sharedRegions/BarMessageExchange.class serverSide/sharedRegions/SharedRegionInterface.class serverSide/entities/* serverSide/stubs/* commInfra/*
echo "  Table"
zip -rq $a/dirTable.zip serverSide/main/TableMain.class serverSide/main/SimPar.class serverSide/entities/ServiceProviderAgent.class serverSide/sharedRegions/Table.class serverSide/sharedRegions/TableMessageExchange.class serverSide/sharedRegions/SharedRegionInterface.class serverSide/entities/* serverSide/stubs/* commInfra/*
echo "  Kitchen"
zip -rq $a/dirKitchen.zip serverSide/main/KitchenMain.class serverSide/main/SimPar.class serverSide/entities/ServiceProviderAgent.class serverSide/sharedRegions/Kitchen.class serverSide/sharedRegions/KitchenMessageExchange.class serverSide/sharedRegions/SharedRegionInterface.class serverSide/entities/* serverSide/stubs/* commInfra/*
echo "  General Repository"
zip -rq $a/dirGeneralRepos.zip serverSide/main/GeneralReposMain.class serverSide/main/SimPar.class serverSide/entities/ServiceProviderAgent.class serverSide/sharedRegions/GeneralRepos.class serverSide/sharedRegions/GeneralReposMessageExchange.class serverSide/sharedRegions/SharedRegionInterface.class commInfra/* serverSide/entities/*
echo "  Chef"
zip -rq $a/dirChef.zip clientSide/main/ChefMain.class serverSide/main/SimPar.class clientSide/stubs/* clientSide/entities/Chef.class clientSide/entities/ChefState.class commInfra/*
echo "  Waiter"
zip -rq $a/dirWaiter.zip clientSide/main/WaiterMain.class serverSide/main/SimPar.class clientSide/stubs/* clientSide/entities/Waiter.class clientSide/entities/WaiterState.class commInfra/*
echo "  Students"
zip -rq $a/dirStudent.zip clientSide/main/StudentMain.class serverSide/main/SimPar.class clientSide/stubs/* clientSide/entities/Student.class clientSide/entities/StudentState.class commInfra/*