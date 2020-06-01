import java.util.ArrayList;

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
     * @param acknowledgement the acknowledgement
     */
    public void onHandshakeEstablishmentChanged(String acknowledgement) {
        if (this.communicationCallback != null) {
            this.communicationCallback
                    .onHandshakeEstablishmentChanged(acknowledgement);
        }
    }

    /**
     * On white board join approval requested.
     *
     * @param userName the user name
     */
    public void onWhiteboardJoinApprovalRequested(String userName) {
        if (this.communicationCallback != null) {
            this.communicationCallback
                    .onWhiteboardJoinApprovalRequested(userName);
        }
    }

    /**
     * On white board cleared.
     */
    public void onwhiteboardCleared() {
        if (this.communicationCallback != null) {
            this.communicationCallback.onwhiteboardCleared();
        }
    }

    /**
     * On user added notification.
     *
     * @param userName the user name
     */
    public void onUserAddedNotification(String userName) {
        if (this.communicationCallback != null) {
            this.communicationCallback.onUserAddedNotification(userName);
        }
    }

    /**
     * On user removed notification.
     *
     * @param userName the user name
     */
    public void onUserRemovedNotification(String userName) {
        if (this.communicationCallback != null) {
            this.communicationCallback.onUserRemovedNotification(userName);
        }
    }

    /**
     * On manager kick user out notification.
     */
    public void OnManagerKickUserOutNotification() {
        if (this.communicationCallback != null) {
            this.communicationCallback.OnManagerKickUserOutNotification();
        }
    }

    /**
     * On all online users synchronization.
     *
     * @param userNameList the user name list
     */
    public void onAllOnlineUsersSynchronization(
            ArrayList<String> userNameList) {
        if (this.communicationCallback != null) {
            this.communicationCallback
                    .onAllOnlineUsersSynchronization(userNameList);
        }
    }

    /**
     * On white board join approval acknowledgement.
     *
     * @param acknowledgement the acknowledgement
     */
    public void onWhiteboardJoinApprovalAcknowledgement(
            String acknowledgement) {
        if (this.communicationCallback != null) {
            this.communicationCallback
                    .onWhiteboardJoinApprovalAcknowledgement(acknowledgement);
        }
    }

    /**
     * On white board owner shutdown notification.
     */
    public void onWhiteboardOwnerShutdownNotification() {
        if (this.communicationCallback != null) {
            this.communicationCallback.onWhiteboardOwnerShutdownNotification();
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
            this.communicationCallback
                    .onWholeWhiteboardRequested(requestUserName);
        }
    }

    /**
     * On whole white board acknowledgement.
     *
     * @param isBroadcastNewImage the is broadcast new image
     * @param imageAsString       the image as string
     */
    public void onWholeWhiteboardAcknowledgement(boolean isBroadcastNewImage,
            String imageAsString) {
        if (this.communicationCallback != null) {
            this.communicationCallback.onWholeWhiteboardAcknowledgement(
                    isBroadcastNewImage, imageAsString);
        }
    }

}
