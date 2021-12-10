package PAXOS;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Client {

    private final static Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static String hostName;
    private static int portNumber;
    private static String filePath;
    private static String coordName;

    public InputStream parseFilePath(String filePath) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File(filePath));
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
            filePath = args[2].toString();
            coordName = args[3].toString();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            Registry registry = LocateRegistry.getRegistry(hostName, portNumber);
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
