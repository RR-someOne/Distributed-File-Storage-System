package PAXOS;

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

    /**
     * Constructor for the client.
     * @throws RemoteException
     */
    public Client() throws RemoteException {
        super();
    }

    private final static Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static String hostName;
    private static int portNumber;
    private static int cordPortNumber;
    private static String coordName;

    /**
     * Input Stream parser.
     *
     * @param filePath String file path.
     * @return returns input stream.
     * @throws FileNotFoundException throws file not found exception.
     */
    public static InputStream parseFilePath(String filePath) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File(filePath));
        return inputStream;
    }

    /**
     * Welcome message of the client.
     */
    public static void welcomeMessage(){
        System.out.println("******** Welcome to the client interface. ************\n\n" +
                "Please choose a command from upload, download or delete.\n" +
                "If you need help choosing, please enter the keyword help");
    }

    /**
     * help message of the client.
     */
    public static void helpMessage() {
        System.out.println("PhotoDrop can support the following commands:\n\n" +
                "Upload, Download, Delete \n" +
                "Enter one of these commands.");
    }

    /**
     * Driver of the client.
     * @param args user inputted args.
     * @throws IOException throws input output exception.
     */
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
                        System.out.println("Enter a file path for to upload to server.");
                        String filePath = in.nextLine();
                        System.out.println("Now enter the fileName for the file.");
                        String fileName = in.nextLine();
                        InputStream file = parseFilePath(filePath);
                        try {
                            coordinator.uploadImageRequest(filePath, fileName);
                            System.out.println("SUCCESS test");
                        } catch (Error e) {
                            e.printStackTrace();
                            System.out.println("Failed test here!!! ");
                        }
                        break;
                    case "download":
                        System.out.println("Please enter the fileName for the file to download.");
                        String downloadFileName = in.nextLine();
                        System.out.println("Please enter the filePath to store on machine.");
                        String filePathDownload = in.nextLine();
                        coordinator.downloadImageRequest(downloadFileName, filePathDownload);
                        break;
                    case "delete":
                        System.out.println("Enter a file name to delete from the server.");
                        String deleteFileName = in.nextLine();
                        coordinator.deleteImageRequest(deleteFileName);
                        break;
                }

            }
       } catch (NotBoundException e) {
            System.out.println("The coordinator is not bound. Please try again.");
        }
    }
}
