package PAXOS;

//import com.healthmarketscience.rmiio.RemoteInputStream;
//import com.healthmarketscience.rmiio.RemoteInputStreamClient;
//import com.healthmarketscience.rmiio.RemoteInputStreamServer;

import java.io.*;
import java.rmi.NotBoundException;
//import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Coordinator extends java.rmi.server.UnicastRemoteObject implements ICordinator, Serializable {

    private final static Logger LOGGER = Logger.getLogger(Coordinator.class.getName());
    private ArrayList<String> serverNames = new ArrayList<>();
    private ICrud crudOperations;
    private ArrayList<String> hostNames = new ArrayList<>();
    private ArrayList<Integer> portNumbers = new ArrayList<>();
    private ArrayList<IServer> servers = new ArrayList<>();
    private final String UPLOAD = "upload";
    private final String DOWNLOAD = "download";
    private final String DELETE = "delete";
    // TODO: NEED TO RESET THE MESSAGES ARRAY AFTER DONE WITH PHASE 1
    private ArrayList<String> messages = new ArrayList<>();

    public Coordinator() throws RemoteException {
        super();
        crudOperations = new Crud();
    }


    //TODO: FAILED RMI SEND FILE BECAUSE USING SERVER RMI HERE
    @Override
    public void phase_one(String requestType, String filePath, String fileName) throws RemoteException{
        // Need to send a message to all servers
        for(int i = 0; i < serverNames.size(); i ++ ) {
            try{
                Registry registry = LocateRegistry.getRegistry(hostNames.get(i), portNumbers.get(i));
                IServer server = (IServer) registry.lookup(serverNames.get(i));
                servers.add(server);
            } catch (NotBoundException e) {
                e.printStackTrace();
                System.out.println("Registry is not bound from the coordinator phase one.");
            } catch(RemoteException e) {
                e.printStackTrace();
                System.out.println("Coordinator throws a remote exception from phase one.");
            }
        }
        // Send a message to each server and check all responses for ok messages.
        for(IServer server : servers) {
            server.sendMessageToCoordinator("From server: " + server.getServerName(),
                    UPLOAD, filePath, fileName);
        }
        if(messages.contains("ABORT") || messages.size() != portNumbers.size()) {
            LOGGER.info("PHASE 1 WAS ABORTED.");
            messages.clear();
            // Rememeber to also clear servers.
            servers.clear();
        } else {
            LOGGER.info("PHASE 1 WAS SUCCESS.");
            // Start phase 2
            phase_two(requestType, filePath, fileName);
            messages.clear();
            // Rememeber to also clear servers.
            servers.clear();
        }
    }

    @Override
    public void phase_one_delete(String requestType, String fileName) throws RemoteException {
        // Need to send a message to all servers
        for(int i = 0; i < serverNames.size(); i ++ ) {
            try{
                Registry registry = LocateRegistry.getRegistry(hostNames.get(i), portNumbers.get(i));
                IServer server = (IServer) registry.lookup(serverNames.get(i));
                servers.add(server);
            } catch (NotBoundException e) {
                e.printStackTrace();
                System.out.println("Registry is not bound from the coordinator phase one.");
            } catch(RemoteException e) {
                e.printStackTrace();
                System.out.println("Coordinator throws a remote exception from phase one.");
            }
        }
        // Send a message to each server and check all responses for ok messages.
        for(IServer server : servers) {
            server.sendMessageToCoordinatorForDelete("From server: " + server.getServerName(),
                    fileName);
        }
        if(messages.contains("ABORT") || messages.size() != portNumbers.size()) {
            LOGGER.info("PHASE 1 WAS ABORTED.");
            messages.clear();
            // Rememeber to also clear servers.
            servers.clear();
        } else {
            LOGGER.info("PHASE 1 WAS SUCCESS.");
            // Start phase 2
            phaseTwoDelete(fileName);
           // phase_two(requestType, filePath, fileName);
            messages.clear();
            // Rememeber to also clear servers.
            servers.clear();
        }
    }

    @Override
    public void phase_two(String requestType, String filePath, String fileName) throws RemoteException{
        switch (requestType) {
            case UPLOAD:
                for(IServer server: servers) {
                    try {
                        server.uploadToServer(filePath, fileName);
                    } catch (Error | IOException e) {
                        LOGGER.info("Send upload request to the server failed.");
                        // TODO: NEED TO ADD A ROLL BACK FEATURE IN CASE A SERVER FAILS AFTER PHASE 1
                        break;
                    }
                }
                break;
            case DOWNLOAD:
                break;
            case DELETE:
                break;
        }
    }

    @Override
    public void phaseTwoDelete(String fileName) throws RemoteException {
        for (IServer server : servers) {
            try {
                server.deleteFromServer(fileName);
            } catch (Error | IOException e) {
                LOGGER.info("Send upload request to the server failed.");
                // TODO: NEED TO ADD A ROLL BACK FEATURE IN CASE A SERVER FAILS AFTER PHASE 1
                break;
            }
        }
    }

//    @Override
//    public void uploadImageRequest(RemoteInputStream file, String fileName) throws IOException {
//        // Start phase one here
//        InputStream fileData = RemoteInputStreamClient.wrap(file);
//        phase_one(UPLOAD, fileData, fileName);
//    }


    @Override
    public void uploadImageRequest(String filePath, String fileName) throws IOException {
        // Start phase one here
        //InputStream fileData = RemoteInputStreamClient.wrap(file);
       // InputStream file = new FileInputStream(new File(fileName));
       // phase_one(UPLOAD, file, fileName);
        phase_one(UPLOAD, filePath, fileName);
    }

    @Override
    public void downloadImageRequest(String fileName, String downloadImagePath) throws RemoteException {
        // Does not change state so does not need 2 phase commit
        for(int i = 0; i < serverNames.size(); i ++ ) {
            try{
                Registry registry = LocateRegistry.getRegistry(hostNames.get(i), portNumbers.get(i));
                IServer server = (IServer) registry.lookup(serverNames.get(i));
                server.downloadFromServer(fileName, downloadImagePath);
                System.out.println("SUCCESS DOWNLOADED FROM SERVER");
                break;
            } catch (NotBoundException e) {
                e.printStackTrace();
                System.out.println("Registry is not bound from the coordinator phase one.");
                continue;
            } catch(RemoteException e) {
                e.printStackTrace();
                System.out.println("Coordinator throws a remote exception from phase one.");
                continue;
            }
        }
    }

    @Override
    public void deleteImageRequest(String deleteFileName) throws RemoteException {
        // Start phase one here
        phase_one_delete(DELETE, deleteFileName);
    }

    @Override
    public void messageBackToClient() throws RemoteException {

    }

    @Override
    public void findObjectId() throws RemoteException{

    }

    @Override
    public void sendServerNameAndHostName(String serverName, String hostName) throws RemoteException {
        serverNames.add(serverName);
        hostNames.add(hostName);
    }

    @Override
    public void sendPortNumber(Integer portNumber) throws RemoteException {
        portNumbers.add(portNumber);
    }

    @Override
    public void sendBackMessageToCoordintor(String message) throws RemoteException {
        messages.add(message);
    }
}
