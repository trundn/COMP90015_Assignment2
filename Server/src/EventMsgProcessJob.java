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
            } else if (Constants.REQUEST_WHITEBOARD_JOIN_APPROVAL_ACK_EVT_NAME
                    .equalsIgnoreCase(eventName)) {
                this.handleWhiteboardJoinRequestAcknowledgment();
            } else if (Constants.MANGER_KICK_USER_OUT_EVT_NAME
                    .equals(eventName)) {
                this.handleMangerKickUserOut();
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
     * Handle manger kick user out.
     */
    private void handleMangerKickUserOut() {
        String userName = EventMessageParser.extractUserName(this.message);
        SocketConnection targetConnection = SocketManager.getInstance()
                .getUserConnection(userName);
        targetConnection.send(this.message);
    }

    /**
     * Handle white board join request acknowledgment.
     */
    private void handleWhiteboardJoinRequestAcknowledgment() {
        // Extract user name from header
        String userName = EventMessageParser.extractUserName(this.message);

        // Check if user name existed in the system or not
        SocketConnection existedConnection = SocketManager.getInstance()
                .getUserConnection(userName);
        if (existedConnection != null) {
            existedConnection.send(this.message);

            // Request white board manager send the lasted canvas if needed
            String acknowledgment = EventMessageParser
                    .extractValFromMessage(this.message, Constants.ACK_ATTR);
            if (Constants.ACK_APPROVED.equalsIgnoreCase(acknowledgment)) {
                this.connection.setJoinedWhiteboard(true);

                // Broadcast new added user to all online peers
                JSONObject userAddedBroadcastMsg = EventMessageBuilder
                        .buildUserAddedBroadcastMessage(userName);
                ArrayList<SocketConnection> tobeNotifiedUserList = SocketManager
                        .getInstance().getUserConnectionList(userName);
                for (SocketConnection tobeNotified : tobeNotifiedUserList) {
                    tobeNotified.send(userAddedBroadcastMsg);
                }

                // Update all online peers for the current user
                ArrayList<String> peerNameList = SocketManager.getInstance()
                        .getNotManagerUserNameList(userName);
                SocketConnection targetConnection = SocketManager.getInstance()
                        .getUserConnection(userName);
                targetConnection.send(EventMessageBuilder
                        .buildAllOnlineUserSynMessage(peerNameList));

                // Request the lasted white board from manager
                SocketConnection whiteboardOwner = SocketManager.getInstance()
                        .getWhiteboardOwnerConnection();
                if (whiteboardOwner != null) {
                    whiteboardOwner.send(EventMessageBuilder
                            .buildWhiteboardSynMessage(userName));
                }
            }
        }
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
        handleMangerKickUserOut();
    }

    /**
     * Handle handshake establishment.
     *
     * @param request the request
     */
    private void handleHandshakeEstablishment() {
        String acknowledgment = Constants.ACK_OK;

        // Extract user name from header
        String userName = EventMessageParser.extractUserName(this.message);

        // Check if user name existed in the system or not
        SocketConnection existedConnection = SocketManager.getInstance()
                .getUserConnection(userName);

        if (existedConnection != null) {
            System.out.println(existedConnection);
            acknowledgment = Constants.ACK_USER_NAME_EXISTED;
            // Send response acknowledgement to client
            this.sendResponse(EventMessageBuilder
                    .buildHandshakeEstablishmentAckMessage(acknowledgment));
        } else {
            // Update user information
            this.connection.setUserName(userName);

            // Extract user role from event body
            JSONObject eventContent = EventMessageParser
                    .extractEventContent(this.message);
            boolean isManager = Boolean.parseBoolean(
                    EventMessageParser.extractValFromContent(eventContent,
                            Constants.MANAGER_ROLE_ATTR));

            // Get user connection with manager role
            SocketConnection owningWhiteboard = SocketManager.getInstance()
                    .anyUserOwningWhiteboard();

            if (isManager) {
                if (owningWhiteboard != null) {
                    // Do not allow 2 users to have a manager role
                    acknowledgment = Constants.ACK_MANAGER_EXISTED;
                } else {
                    // Update manager role for this socket connection
                    this.connection.setManager(isManager);
                    this.connection.setJoinedWhiteboard(true);

                    // Identify connection with manger role
                    SocketManager.getInstance()
                            .setWhiteboardOwnerConnection(this.connection);

                    for (SocketConnection notJoinedUser : SocketManager
                            .getInstance()
                            .getNotJoinedWhiteboardConnectionList()) {
                        this.connection.send(EventMessageBuilder
                                .buildRequestWhiteboardJoinApprovalMessage(
                                        notJoinedUser.getUserName()));
                    }
                }
            }

            // Send response acknowledgement to client
            this.sendResponse(EventMessageBuilder
                    .buildHandshakeEstablishmentAckMessage(acknowledgment));

            // Send white board join approval request message to manager if
            // needed
            if ((owningWhiteboard != null)
                    && Constants.ACK_OK.equalsIgnoreCase(acknowledgment)
                    && !this.connection.isManager()) {
                owningWhiteboard.send(EventMessageBuilder
                        .buildRequestWhiteboardJoinApprovalMessage(userName));
            }
        }
    }

}
