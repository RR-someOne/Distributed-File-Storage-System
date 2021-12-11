FROM bellsoft/liberica-openjdk-alpine-musl:11 AS client-build
COPY . ~/Documents/Project_Final
WORKDIR ~/Documents/Project_Final
RUN javac -cp PAXOS/mongo-java-driver-3.11.2.jar: PAXOS/ResponseBetweenServers.java
RUN javac -cp PAXOS/mongo-java-driver-3.11.2.jar: PAXOS/Operations.java
RUN javac -cp PAXOS/mongo-java-driver-3.11.2.jar: PAXOS/OperationsImpl.java
RUN javac -cp PAXOS/mongo-java-driver-3.11.2.jar: PAXOS/Server.java
RUN javac -cp PAXOS/mongo-java-driver-3.11.2.jar: PAXOS/Sandbox.java
RUN javac -cp PAXOS/mongo-java-driver-3.11.2.jar: PAXOS/Client.java


# cmd to run server locally - java server.ServerApp 1111 5555
ENTRYPOINT ["java", "PAXOS.Server"]



# javac -cp PAXOS/mongo-java-driver-3.11.2.jar: PAXOS/*.java
# rmiregistry
# java -cp PAXOS/mongo-java-driver-3.11.2.jar: PAXOS/StartCoordinator 2000
# java -cp PAXOS/mongo-java-driver-3.11.2.jar: PAXOS/StartServer 1000 s1 localhost localhost Coordinator db10 2000
# java -cp PAXOS/mongo-java-driver-3.11.2.jar: PAXOS/Client localhost 3000 Coordinator 2000
