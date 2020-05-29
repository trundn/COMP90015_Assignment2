
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
            // Get the response message.
            JSONObject response = SocketHandler.getInstance().receive();

            if (response != null) {
                if (response.containsKey(Constants.HANDSHAKE_ACKNOWLEDGMENT)) {
                    JSONObject content = (JSONObject) response
                            .get(Constants.HANDSHAKE_ACKNOWLEDGMENT);
                    String acknowledgment = content.get(Constants.ACK_ATTR)
                            .toString();

                    if (!Constants.ACK_OK.equalsIgnoreCase(acknowledgment)) {
                        ChangeNotifier.getInstance()
                                .onHandshakeEstablishmentChanged(false);
                    }
                }
            }
        }

        return null;
    }
}
