
import java.util.ArrayList;

import org.json.simple.JSONObject;

/**
 * The Class RetrieveMsgJob.
 */
public class RetrieveMsgJob extends AbstractJob {

    /**
     * Call.
     *
     * @return the void
     * @throws Exception the exception
     */
    @Override
    protected Void call() throws Exception {
        while (!this.isCancelled()) {
            // Get the received message.
            JSONObject message = SocketHandler.getInstance().receive();

            if (message != null) {
                String eventName = EventMessageParser.extractEventName(message);
                String userName = EventMessageParser.extractUserName(message);

                if (Constants.WHITEBOARD_OWNER_SHUTDOWN_EVT_NAME
                        .equalsIgnoreCase(eventName)) {
                    ChangeNotifier.getInstance()
                            .onWhiteboardOwnerShutdownNotification();
                } else if (Constants.HANDSHAKE_ACKNOWLEDGMENT_EVT_NAME
                        .equalsIgnoreCase(eventName)) {
                    String acknowledgment = EventMessageParser
                            .extractValFromMessage(message, Constants.ACK_ATTR);
                    ChangeNotifier.getInstance()
                            .onHandshakeEstablishmentChanged(acknowledgment);
                } else if (Constants.REQUEST_WHITEBOARD_JOIN_APPROVAL_EVT_NAME
                        .equalsIgnoreCase(eventName)) {
                    ChangeNotifier.getInstance()
                            .onWhiteboardJoinApprovalRequested(userName);
                } else if (Constants.REQUEST_WHITEBOARD_JOIN_APPROVAL_ACK_EVT_NAME
                        .equalsIgnoreCase(eventName)) {
                    String acknowledgment = EventMessageParser
                            .extractValFromMessage(message, Constants.ACK_ATTR);
                    ChangeNotifier.getInstance()
                            .onWhiteboardJoinApprovalAcknowledgement(
                                    acknowledgment);
                } else if (Constants.WHITE_BOARD_CLEARED_EVT_NAME
                        .equalsIgnoreCase(eventName)) {
                    ChangeNotifier.getInstance().onwhiteboardCleared();
                } else if (Constants.USER_ADDED_EVT_NAME
                        .equalsIgnoreCase(eventName)) {
                    ChangeNotifier.getInstance()
                            .onUserAddedNotification(userName);
                } else if (Constants.USER_REMOVED_EVT_NAME
                        .equalsIgnoreCase(eventName)) {
                    ChangeNotifier.getInstance()
                            .onUserRemovedNotification(userName);
                } else if (Constants.MANGER_KICK_USER_OUT_EVT_NAME
                        .equalsIgnoreCase(eventName)) {
                    ChangeNotifier.getInstance()
                            .OnManagerKickUserOutNotification();
                } else if (Constants.ALL_ONLINE_USERS_SYN_EVT_NAME
                        .equalsIgnoreCase(eventName)) {
                    ArrayList<String> userNameList = EventMessageParser
                            .extractUserNameList(message);
                    ChangeNotifier.getInstance()
                            .onAllOnlineUsersSynchronization(userNameList);
                } else if (Constants.LINE_SYN_EVT_NAME
                        .equalsIgnoreCase(eventName)
                        || Constants.CIRCLE_SYN_EVT_NAME
                                .equalsIgnoreCase(eventName)
                        || Constants.RECTANGLE_SYN_EVT_NAME
                                .equalsIgnoreCase(eventName)
                        || Constants.TEXT_SYN_EVT_NAME
                                .equalsIgnoreCase(eventName)) {
                    ChangeNotifier.getInstance()
                            .onShapeSynchronizationChanged(message);
                } else if (Constants.WHITE_BOARD_SYS_EVT_NAME
                        .equalsIgnoreCase(eventName)) {
                    ChangeNotifier.getInstance()
                            .onWholeWhiteboardRequested(userName);
                } else if (Constants.WHITE_BOARD_SYS_ACKNOWLEDGMENT
                        .equalsIgnoreCase(eventName)) {
                    this.handleWhiteboardSynchronization(message, userName);
                }
            }
        }

        return null;
    }

    private void handleWhiteboardSynchronization(JSONObject message,
            String userName) {
        String imageAsString = EventMessageParser.extractValFromMessage(message,
                Constants.IMAGE_AS_STRING_ATTR);
        boolean isBroadcastNewImage = false;
        if (StringHelper.isNullOrEmpty(userName)) {
            isBroadcastNewImage = EventMessageParser
                    .extractBooleanValueFromMessage(message,
                            Constants.BROADCAST_NEW_IMAGE_ATTR);
        }
        ChangeNotifier.getInstance().onWholeWhiteboardAcknowledgement(
                isBroadcastNewImage, imageAsString);
    }
}
