package PAXOS;

//import com.healthmarketscience.rmiio.RemoteInputStream;
//import com.healthmarketscience.rmiio.RemoteInputStreamServer;
//
//import javax.crypto.KeyAgreement;
import java.io.IOException;
//import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICordinator extends Remote {

    /**
     * Phase one of two phase commit.
     */
    public void phase_one(String requestType, String filePath, String fileName) throws RemoteException;

    /**
     * Phase two of two phase commit.
     */
    public void phase_two(String requestType, String filePath, String fileName) throws RemoteException;

    /**
     * sends an upload image request to coordinator.
     *
     * @throws RemoteException throws remote exception.
     */
   // public void uploadImageRequest(RemoteInputStream file, String fileName) throws IOException;
    public void uploadImageRequest(String filePath, String fileName) throws IOException;

    /**
     * sends an download image request to coordinator.
     *
     * @throws RemoteException throws remote exception.
     */
    public void downloadImageRequest() throws RemoteException;

    /**
     * sends an delete image request to coordinator.
     *
     * @throws RemoteException throws remote exception.
     */
    public void deleteImageRequest() throws RemoteException;

    /**
     * Sends a message to the client.
     */
    public void messageBackToClient() throws RemoteException;

    /**
     * Find the objectId of an image.
     */
    public void findObjectId() throws RemoteException;

    /**
     * Sends server name to the coordinator.
     *
     * @throws RemoteException throws remote exception.
     */
    public void sendServerNameAndHostName(String serverName, String hostName) throws RemoteException;

    /**
     * Sends a port number to the coorindator from server.
     *
     * @param portNumber integer port number.
     * @throws RemoteException throws a remote exception.
     */
    public void sendPortNumber(Integer portNumber) throws RemoteException;

    public void sendBackMessageToCoordintor(String message) throws RemoteException;
}
