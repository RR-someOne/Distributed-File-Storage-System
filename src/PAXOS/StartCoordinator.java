package PAXOS;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartCoordinator {

    /**
     * Driver method for starting the coordinator.
     *
     * @param args String user arguments.
     * @throws RemoteException throws remote exception.
     */
    public static void main(String [] args) throws RemoteException, AlreadyBoundException {

        if(args.length < 1) {
            System.exit(1);
        }
        try {
            int portNumber = Integer.parseInt(args[0]);
            Coordinator coordinator = new Coordinator();
            Registry registry = LocateRegistry.createRegistry(portNumber);
            registry.bind("Coordinator", coordinator);
            System.out.println("SUCCESS CONNECTED TO THE COORDINATOR.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("Arguments not in the right format.");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Coordinator gave a remote exception.");
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
            System.out.println("Coordintor gave an already bound exception.");
        }
    }
}
