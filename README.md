# Photo-Drop

## RUN
1. java -cp PAXOS/mongo-java-driver-3.11.2.jar: PAXOS/*.java
2. rmiregistry
3. java -cp PAXOS/mongo-java-driver-3.11.2.jar: PAXOS/StartCoordinator 2000
4. java -cp PAXOS/mongo-java-driver-3.11.2.jar: PAXOS/StartServer 1001 s1 localhost localhost Coordinator db1 2000
5. java -cp PAXOS/mongo-java-driver-3.11.2.jar: PAXOS/Client localhost 3000 Coordinator 2000
