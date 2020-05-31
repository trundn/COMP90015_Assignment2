import java.util.ArrayList;
import org.json.simple.JSONObject;

/**
 * The Class EventMsgProcessJob.
 */
public class EventMsgProcessJob extends AbstractJob {

    /** The connection. */
    private SocketConnection connection;

    /** The message. */
    private JSONObject message;

    /**
     * Instantiates a new request process job.
     *
     * @param connection the connection
     * @param message    the message
     */
    public EventMsgProcessJob(SocketConnection connection, JSONObject message) {
        if (message == null) {
            throw new IllegalArgumentException(
                    "The given request JSON object is NULL.");
        }

        this.message = message;
        this.connection = connection;
    }

    /**
     * Send response.
     *
     * @param response the response
     */
    private void sendResponse(JSONObject response) {
        if (this.connection != null) {
            ChangeNotifier.getInstance().onMessageChanged(String.format(
                    "Sending response message to client [%s]. Content: %s",
                    this.connection.getHostAddress(), response.toJSONString()));

            this.connection.send(response);
        }
    }

    /**
     * Call.
     *
     * @return the void
     * @throws Exception the exception
     */
    @Override
    protected Void call() throws Exception {
        if (!this.isCancelled()) {
            String eventName = EventMessageParser
                    .extractEventName(this.message);

            if (Constants.HANDSHAKE_ESTABLISHMENT_EVT_NAME
                    .equalsIgnoreCase(eventName)) {
                this.handleHandshakeEstablishment();
            } else if (Constants.LINE_SYN_EVT_NAME.equalsIgnoreCase(eventName)
                    || Constants.CIRCLE_SYN_EVT_NAME.equalsIgnoreCase(eventName)
                    || Constants.RECTANGLE_SYN_EVT_NAME
                            .equalsIgnoreCase(eventName)
                    || Constants.TEXT_SYN_EVT_NAME
                            .equalsIgnoreCase(eventName)) {
                this.handleShapeSynchronization();
            } else if (Constants.WHITE_BOARD_SYS_ACKNOWLEDGMENT
                    .equalsIgnoreCase(eventName)) {
                this.handleWhiteboardSynchronization();
            }
        }

        return null;
    }

    /**
     * Handle canvas synchronization.
     */
    private void handleShapeSynchronization() {
        String userName = EventMessageParser.extractUserName(this.message);

        ArrayList<SocketConnection> connectionList = SocketManager.getInstance()
                .getUserConnectionList(userName);

        for (SocketConnection peerConnection : connectionList) {
            peerConnection.send(this.message);
        }
    }

    /**
     * Handle white board synchronization.
     */
    private void handleWhiteboardSynchronization() {
        String userName = EventMessageParser.extractUserName(this.message);

        SocketConnection connection = SocketManager.getInstance()
                .getUserConnection(userName);
        connection.send(this.message);
    }

    /**
     * Handle handshake establishment.
     *
     * @param request the request
     */
    private void handleHandshakeEstablishment() {
        // Extract user name and manager role values
        JSONObject eventContent = EventMessageParser
                .extractEventContent(this.message);
        String userName = EventMessageParser.extractUserName(this.message);
        boolean isManager = Boolean.parseBoolean(
                EventMessageParser.extractValFromContent(eventContent,
                        Constants.MANAGER_ROLE_ATTR));

        String acknowledgment = Constants.ACK_OK;
        if (isManager) {
            SocketConnection managerRoleConnection = SocketManager.getInstance()
                    .anyUserOwnWhiteboard();
            if (managerRoleConnection != null) {
                // Do not allow 2 users to have a manager role
                isManager = false;
                acknowledgment = Constants.ACK_NG;
                // Identify connection with manger role
                SocketManager.getInstance()
                        .setWhiteboardOwnerConnection(managerRoleConnection);
            }
        }

        // Update user information
        this.connection.setUserName(userName);
        this.connection.setManager(isManager);

        // Send response acknowledgement to client
        this.sendResponse(EventMessageBuilder
                .buildHandshakeEstablishmentAckMessage(acknowledgment));

        // Request the lasted canvas from manager user
        if (!isManager) {
            SocketConnection manager = SocketManager.getInstance()
                    .getWhiteboardOwnerConnection();
            if (manager != null) {
                manager.send(EventMessageBuilder
                        .buildWhiteboardSynMessage(userName));
            }
        }
    }

}
