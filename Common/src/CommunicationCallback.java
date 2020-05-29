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
     * On canvas synchronization changed.
     *
     * @param message the message
     */
    void onCanvasSynchronizationChanged(JSONObject message);
}
