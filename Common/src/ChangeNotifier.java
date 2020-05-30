import org.json.simple.JSONObject;

/**
 * The Class ChangeNotifier.
 */
public class ChangeNotifier {

    /** The instance. */
    private static ChangeNotifier INSTANCE;

    /** The message callback. */
    MessageCallback messageCallback;

    /** The communication callback. */
    CommunicationCallback communicationCallback;

    /**
     * Gets the single instance of MessageNotifier.
     *
     * @return single instance of MessageNotifier
     */
    public static ChangeNotifier getInstance() {
        if (INSTANCE == null) {
            synchronized (ChangeNotifier.class) {
                if (INSTANCE == null)
                    INSTANCE = new ChangeNotifier();
            }
        }

        return INSTANCE;
    }

    /**
     * Register message callback.
     *
     * @param callback the callback
     */
    public void registerMsgCallback(MessageCallback callback) {
        this.messageCallback = callback;
    }

    /**
     * Unregister message callback.
     */
    public void unregisterMsgCallback() {
        this.messageCallback = null;
    }

    /**
     * Register communication callback.
     *
     * @param callback the callback
     */
    public void registerCommCallback(CommunicationCallback callback) {
        this.communicationCallback = callback;
    }

    /**
     * Unregister communication callback.
     */
    public void unregisterCommCallback() {
        this.communicationCallback = null;
    }

    /**
     * On message changed.
     *
     * @param newMessage the new message
     */
    public void onMessageChanged(String newMessage) {
        System.out.println(newMessage);

        if (this.messageCallback != null) {
            this.messageCallback.onMessageChanged(newMessage);
        }
    }

    /**
     * On connection status changed.
     *
     * @param isConnected the is connected
     */
    public void onConnectionStatusChanged(boolean isConnected) {
        if (this.communicationCallback != null) {
            this.communicationCallback.onConnectionStatusChanged(isConnected);
        }
    }

    /**
     * On handshake establishment changed.
     *
     * @param isOkAck the is ok ack
     */
    public void onHandshakeEstablishmentChanged(boolean isOkAck) {
        if (this.communicationCallback != null) {
            this.communicationCallback.onHandshakeEstablishmentChanged(isOkAck);
        }
    }

    /**
     * On shape synchronization changed.
     *
     * @param message the message
     */
    public void onShapeSynchronizationChanged(JSONObject message) {
        if (this.communicationCallback != null) {
            this.communicationCallback.onShapeSynchronizationChanged(message);
        }
    }

    /**
     * On whole white board requested.
     *
     * @param requestUserName the request user name
     */
    public void onWholeWhiteboardRequested(String requestUserName) {
        if (this.communicationCallback != null) {
            this.communicationCallback.onWholeWhiteboardRequested(requestUserName);
        }
    }

    /**
     * On whole white board acknowledgement.
     *
     * @param imageAsString the image as string
     */
    public void onWholeWhiteboardAcknowledgement(String imageAsString) {
        if (this.communicationCallback != null) {
            this.communicationCallback.onWholeWhiteboardAcknowledgement(imageAsString);
        }
    }

}
