CODEBASE="http://l040101-ws09.ua.pt/sd111/classes/"
rmiregistry -J-Djava.rmi.server.codebase=$CODEBASE\
            -J-Djava.rmi.server.useCodebaseOnly=true $1
