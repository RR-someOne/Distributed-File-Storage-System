package PAXOS;

//import com.healthmarketscience.rmiio.RemoteInputStream;
//import com.healthmarketscience.rmiio.RemoteInputStreamServer;
//import jdk.swing.interop.SwingInterOpUtils;

import java.io.*;
import java.rmi.RemoteException;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
import java.util.logging.Logger;

public class Server extends Crud implements Runnable, IServer, Serializable {

    private final static Logger LOGGER = Logger.getLogger(Server.class.getName());
    private  ICordinator cordinator;
    private String serverName;
    private String databaseName;

    public Server(ICordinator cordinator, String serverName, String databaseName) throws RemoteException {
        this.cordinator = cordinator;
        this.serverName = serverName;
        this.databaseName = databaseName;
    }

    @Override
    public void sendMessageToCoordinator(String message, String requestType, String filePath,
                                         String fileName) throws RemoteException {
        switch (requestType) {
            case "upload":
                try {
                    InputStream file = new FileInputStream(new File(filePath));
                    //upload(file, fileName, databaseName);
                    LOGGER.info("SUCCESS CAN UPLOAD FROM SERVER." + message);
                    cordinator.sendBackMessageToCoordintor("OK");
                }  catch (Error | FileNotFoundException e) {
                    LOGGER.info("Aborted" + message);
                    cordinator.sendBackMessageToCoordintor("ABORT");
                }
                break;
            case "download":
                break;
            case "delete":
                break;
        }
    }



//    @Override
//    public void sendMessageToCoordinator(String message, String requestType,
//                                         InputStream file, String fileName) throws RemoteException {
//        switch (requestType) {
//            case "upload":
//                try {
//                    upload(file, fileName, databaseName);
//                    LOGGER.info("SUCCESS CAN UPLOAD FROM SERVER." + message);
//                    cordinator.sendBackMessageToCoordintor("OK");
//                }  catch (Error e) {
//                    LOGGER.info("Aborted" + message);
//                    cordinator.sendBackMessageToCoordintor("ABORT");
//                }
//                break;
//            case "download":
//                break;
//            case "delete":
//                break;
//        }
//    }

    @Override
    public String getServerName() throws RemoteException {
        return this.serverName;
    }

    @Override
    public void uploadToServer(String filePath, String fileName) throws RemoteException {
        try {
            InputStream file = new FileInputStream(new File(filePath));
            upload(file, fileName, databaseName);
            LOGGER.info("Success, uploaded the file to the server.");
        } catch (Error | FileNotFoundException e) {
            LOGGER.info("Could not upload the file to the server.");
        }
    }

    @Override
    public void deleteFromServer() throws RemoteException {

    }

    @Override
    public void downloadFromServer() throws RemoteException {

    }

    @Override
    public void run() {
        execute();
    }

    public void execute() {

    }
}
