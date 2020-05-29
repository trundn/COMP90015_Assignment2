import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

/**
 * The Class PingJob.
 */
public class PingJob extends AbstractJob {

    /**
     * Call.
     *
     * @return the void
     * @throws Exception the exception
     */
    @Override
    protected Void call() throws Exception {
        SocketHandler handler = SocketHandler.getInstance();
        while (!this.isCancelled()) {
            JSONObject pingReq = RequestBuilder
                    .buildPingRequest(handler.getLocalAddress());
            String error = handler.send(pingReq);
            if (!StringHelper.isNullOrEmpty(error)) {
                handler.setConnected(false);
                updateMessage("Failed to ping to server. Error: " + error);
                ChangeNotifier.getInstance().onConnectionChanged(false);
            }

            TimeUnit.SECONDS.sleep(3);
        }

        return null;
    }

}
