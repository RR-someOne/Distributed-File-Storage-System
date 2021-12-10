package PAXOS;

import java.rmi.Remote;

public interface ICordinator extends Remote {

    /**
     * Phase one of two phase commit.
     */
    public void phase_one();

    /**
     * Phase two of two phase commit.
     */
    public void phase_two();

    /**
     * Sends a message to the client.
     */
    public void messageBackToClient();

    /**
     * Sends to upload message to the server.
     */
    public void messageToServerUpload();

    /**
     * Sends a delete message to the server.
     */
    public void messageToServerDelete();

    /**
     * Sends a download message to the server.
     */
    public void messageToServerDownload();

    /**
     * Find the objectId of an image.
     */
    public void findObjectId();
}
