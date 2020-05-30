
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

                if (Constants.HANDSHAKE_ACKNOWLEDGMENT_EVT_NAME
                        .equalsIgnoreCase(eventName)) {
                    String acknowledgment = EventMessageParser
                            .extractValFromMessage(message,
                                    Constants.ACK_ATTR);

                    if (!Constants.ACK_OK.equalsIgnoreCase(acknowledgment)) {
                        ChangeNotifier.getInstance()
                                .onHandshakeEstablishmentChanged(false);
                    }
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
                    String userName = EventMessageParser
                            .extractUserName(message);
                    ChangeNotifier.getInstance()
                            .onWholeWhiteboardRequested(userName);
                } else if (Constants.WHITE_BOARD_SYS_ACKNOWLEDGMENT
                        .equalsIgnoreCase(eventName)) {
                    String imageAsString = EventMessageParser
                            .extractValFromMessage(message,
                                    Constants.IMAGE_AS_STRING_ATTR);
                    ChangeNotifier.getInstance()
                            .onWholeWhiteboardAcknowledgement(imageAsString);
                }
            }
        }

        return null;
    }
}
