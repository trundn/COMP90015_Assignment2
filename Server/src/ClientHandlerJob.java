import org.json.simple.JSONObject;

/**
 * The Class ClientHandlerJob.
 */
public class ClientHandlerJob extends AbstractJob {

    /** The socket connection. */
    private SocketConnection socketConnection;

    /** The request process job executor. */
    private ThreadPoolJobExecutor requestProcessJobExecutor;

    /**
     * Instantiates a new client handler job.
     *
     * @param connection                the connection
     * @param requestProcessJobExecutor the request process job executor
     */
    public ClientHandlerJob(SocketConnection connection,
            ThreadPoolJobExecutor requestProcessJobExecutor) {
        this.socketConnection = connection;
        this.requestProcessJobExecutor = requestProcessJobExecutor;
    }

    /**
     * Call.
     *
     * @return the void
     * @throws Exception the exception
     */
    @Override
    protected Void call() throws Exception {
        while (!this.isCancelled()) {
            JSONObject request = this.socketConnection.receive();
            if (request != null) {

            }
        }

        return null;
    }

}
