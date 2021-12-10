package PAXOS;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {

    public static void main(String[] args) {
        if(args.length < 1) {
            System.exit(1);
        }
        try {
            int portNumber = Integer.parseInt(args[0]);
            String serverName = args[1].toString();
            String hostName = args[2].toString();
            String cordHost = args[3].toString();
            String coordName = args[4].toString();
            String databaseName = args[5].toString();
            int cordPortNumber = Integer.parseInt(args[6]);

            String con = String.format("mongodb+srv://testUser:testUser@server1." +
                    "w54b6.mongodb.net/%s?retryWrites=true&w=majority", databaseName);

            MongoClient client = MongoClients.create(con);
            MongoDatabase db = client.getDatabase(databaseName);

            // connect with registry rmiregistry
            Registry registryCord = LocateRegistry.getRegistry(cordHost, cordPortNumber);
            ICordinator coordinator = (ICordinator) registryCord.lookup(coordName);

            // Create a server node
            Server server = new Server(coordinator, serverName, databaseName);

            // Connect with rmiregistry
            Registry registry = LocateRegistry.createRegistry(portNumber);
            registry.bind(serverName, server);

            // Send the server name to the coordinator on instantiation.
            coordinator.sendServerNameAndHostName(serverName, hostName);
            // Send the port number to the coordinator
            coordinator.sendPortNumber(portNumber);


            System.out.println("SUCCESS CONNECTED TO THE SERVER.");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Server made a remote exception.");
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
            System.out.println("Server made an already bounds exception.");
        } catch (NotBoundException e) {
            e.printStackTrace();
            System.out.println("Server made a not bounds exception.");
        }
    }
}
