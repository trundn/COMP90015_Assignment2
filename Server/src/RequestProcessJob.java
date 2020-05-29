import org.json.simple.JSONObject;

/**
 * The Class RequestProcessJob.
 */
public class RequestProcessJob extends AbstractJob {

    /** The connection. */
    private SocketConnection connection;

    /** The request. */
    private JSONObject request;

    /**
     * Instantiates a new request process job.
     *
     * @param connection the connection
     * @param request    the request
     */
    public RequestProcessJob(SocketConnection connection, JSONObject request) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "The given request JSON object is NULL.");
        }

        this.request = request;
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

        }

        return null;
    }

}
