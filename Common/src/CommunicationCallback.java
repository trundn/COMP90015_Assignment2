import org.json.simple.JSONObject;

public interface CommunicationCallback {
    /**
     * On connection status changed.
     *
     * @param isConnected the is connected
     */
    void onConnectionStatusChanged(boolean isConnected);

    /**
     * On handshake establishment changed.
     *
     * @param ackowledgement the acknowledgement
     */
    void onHandshakeEstablishmentChanged(String acknowledgement);

    /**
     * On white board join approval requested.
     *
     * @param userName the user name
     */
    void onWhiteboardJoinApprovalRequested(String userName);

    /**
     * On white board join approval acknowledgement.
     *
     * @param acknowledgement the acknowledgement
     */
    void onWhiteboardJoinApprovalAcknowledgement(String acknowledgement);

    /**
     * On white board owner shutdown notification.
     */
    void onWhiteboardOwnerShutdownNotification();

    /**
     * On shape synchronization changed.
     *
     * @param message the message
     */
    void onShapeSynchronizationChanged(JSONObject message);

    /**
     * On whole white board requested.
     *
     * @param requestUserName the request user name
     */
    void onWholeWhiteboardRequested(String requestUserName);

    /**
     * On whole white board acknowledgement.
     *
     * @param imageAsString the image as string
     */
    void onWholeWhiteboardAcknowledgement(String imageAsString);
}
