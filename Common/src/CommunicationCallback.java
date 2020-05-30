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
     * @param isOkAck the is ok ack
     */
    void onHandshakeEstablishmentChanged(boolean isOkAck);

    /**
     * On shape synchronization changed.
     *
     * @param message the message
     */
    void onShapeSynchronizationChanged(JSONObject message);

    /**
     * On whole whiteboard requested.
     *
     * @param requestUserName the request user name
     */
    void onWholeWhiteboardRequested(String requestUserName);

    /**
     * On whole whiteboard acknowledgement.
     *
     * @param imageAsString the image as string
     */
    void onWholeWhiteboardAcknowledgement(String imageAsString);
}
