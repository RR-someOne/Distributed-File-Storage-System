package PAXOS;

//import com.healthmarketscience.rmiio.RemoteInputStream;
//import com.healthmarketscience.rmiio.RemoteInputStreamServer;
//import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Client implements Serializable{

    public Client() throws RemoteException {
        super();
    }

    private final static Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static String hostName;
    private static int portNumber;
    private static int cordPortNumber;
  //  private static String filePath;
    private static String coordName;
   // private static InputStream file;

    // Wrapper to send input stream file, normal inputstream does not work here for rmi...
    public static InputStream parseFilePath(String filePath) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File(filePath));
      //  RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inputStream);
        return inputStream;
    }

    public static void welcomeMessage(){
        System.out.println("******** Welcome to the client interface. ************\n\n" +
                "Please choose a command from upload, download or delete.\n" +
                "If you need help choosing, please enter the keyword help");
    }

    public static void helpMessage() {
        System.out.println("PhotoDrop can support the following commands:\n\n" +
                "Upload, Download, Delete \n" +
                "Enter one of these commands.");
    }

    public static void main(String[] args) throws IOException {
        FileHandler fh = new FileHandler("myLogRmiClient.txt");
        fh.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

        if (args.length < 2) {
            System.exit(1);
        }
        try {
            hostName = args[0].toString();
            portNumber = Integer.parseInt(args[1]);
            //filePath = args[2].toString();
            coordName = args[2].toString();
            cordPortNumber = Integer.parseInt(args[3]);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            LOGGER.info("Illegal arguments.");
            System.out.println("Illegal arguments. (hostName, port number, file path, coordName)");
        }
        try {
            Registry registry = LocateRegistry.getRegistry(hostName, cordPortNumber);
            ICordinator coordinator = (ICordinator) registry.lookup(coordName);
            while(true) {
                welcomeMessage();
                Scanner in = new Scanner(System.in);
                String request = in.nextLine();
                if(request.toLowerCase().equals("help")) {
                    helpMessage();
                    request = in.nextLine();
                }
                switch (request.toLowerCase()) {
                    case "upload":
                        //TODO: Get file Input stream send to coordinator for upload.
                        System.out.println("Enter a file path for to upload to server.");
                        String filePath = in.nextLine();
                        System.out.println("Now enter the fileName for the file.");
                        String fileName = in.nextLine();
                     //   InputStream file = parseFilePath(filePath);
                        InputStream file = parseFilePath(filePath);
                        //TEST HERE
                        try {
                            //coordinator.uploadImageRequest(file.export(), fileName);
                            coordinator.uploadImageRequest(filePath, fileName);
                            System.out.println("SUCCESS test");
                        } catch (Error e) {
                            e.printStackTrace();
                            System.out.println("Failed test here!!! ");
                        }
                        // LEFT OFF HERE...
                        break;
                    case "download":
                        // TODO: send file name to download and send the file path to store the image.
                        break;
                    case "delete":
                        //TODO: deletes the file and send the object id to delete.
                        break;
                }

            }
       } catch (NotBoundException e) {
            System.out.println("The coordinator is not bound. Please try again.");
        }
    }
}
