package PAXOS;

//import com.healthmarketscience.rmiio.RemoteInputStream;
//import com.healthmarketscience.rmiio.RemoteInputStreamServer;
//
//import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {

    /**
     *
     * @param message
     * @param requestType
     * @param fileName
     * @throws RemoteException
     */
    public void  sendMessageToCoordinator(String message, String requestType,
                                          String filePath, String fileName) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public String getServerName() throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void uploadToServer(String filePath, String fileName) throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void deleteFromServer() throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void downloadFromServer() throws RemoteException;

}
