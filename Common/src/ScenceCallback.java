
/**
 * The Interface ScenceCallback.
 */
public interface ScenceCallback {

    /**
     * On message changed.
     *
     * @param newMessage the new message
     */
    void onMessageChanged(String newMessage);

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
}
