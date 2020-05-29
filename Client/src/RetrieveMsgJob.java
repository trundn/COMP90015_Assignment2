
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
                if (message.containsKey(Constants.HANDSHAKE_ACKNOWLEDGMENT)) {
                    JSONObject content = (JSONObject) message
                            .get(Constants.HANDSHAKE_ACKNOWLEDGMENT);
                    String acknowledgment = content.get(Constants.ACK_ATTR)
                            .toString();

                    if (!Constants.ACK_OK.equalsIgnoreCase(acknowledgment)) {
                        ChangeNotifier.getInstance()
                                .onHandshakeEstablishmentChanged(false);
                    }
                } else if (message.containsKey(Constants.LINE_SYN_REQUEST)
                        || message.containsKey(Constants.CIRCLE_SYN_REQUEST)
                        || message.containsKey(Constants.RECTANGLE_SYN_REQUEST)
                        || message.containsKey(Constants.TEXT_SYN_REQUEST)) {
                    ChangeNotifier.getInstance()
                            .onCanvasSynchronizationChanged(message);
                }
            }
        }

        return null;
    }
}
