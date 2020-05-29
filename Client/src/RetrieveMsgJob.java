
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
                
            }
        }

        return null;
    }
}
