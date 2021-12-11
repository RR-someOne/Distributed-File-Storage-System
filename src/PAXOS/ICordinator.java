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
     * Phase one of two phase commit upload.
     *
     * @param requestType String request type.
     * @param filePath String file path.
     * @param fileName String filename.
     * @throws RemoteException throws remote exception.
     */
    public void phase_one(String requestType, String filePath, String fileName) throws RemoteException;


    /**
     * Phase one of the two phase commit delete.
     *
     * @param requestType String request type.
     * @param fileName String file path.
     * @throws RemoteException throws remote exception.
     */
    public void phase_one_delete(String requestType, String fileName) throws RemoteException;

    /**
     * phase two of the two phase commit upload.
     *
     * @param requestType String request type.
     * @param filePath String file path.
     * @param fileName String filename.
     * @throws RemoteException throws remote exception.
     */
    public void phase_two(String requestType, String filePath, String fileName) throws RemoteException;

    /**
     * phase two of the two phase commit delete.
     *
     * @param fileName String file path.
     * @throws RemoteException throws remote exception.
     */
    public void phaseTwoDelete(String fileName) throws RemoteException;

    /**
     * upload image request.
     *
     * @param filePath String file path.
     * @param fileName String filename.
     * @throws IOException throws io exception.
     */
    public void uploadImageRequest(String filePath, String fileName) throws IOException;

    /**
     * download image request.
     *
     * @param fileName String file path.
     * @param filePathDownload String filePathDownload.
     * @throws RemoteException throws remote exception.
     */
    public void downloadImageRequest(String fileName, String filePathDownload) throws RemoteException;

    /**
     *
     * @param deleteFileName
     * @throws RemoteException
     */
    public void deleteImageRequest(String deleteFileName) throws RemoteException;


    /**
     *
     * @throws RemoteException
     */
    public void messageBackToClient() throws RemoteException;


    /**
     *
     * @param serverName
     * @param hostName
     * @throws RemoteException
     */
    public void sendServerNameAndHostName(String serverName, String hostName) throws RemoteException;

    /**
     *
     * @param portNumber
     * @throws RemoteException
     */
    public void sendPortNumber(Integer portNumber) throws RemoteException;

    /**
     *
     * @param message
     * @throws RemoteException
     */
    public void sendBackMessageToCoordintor(String message) throws RemoteException;
}
