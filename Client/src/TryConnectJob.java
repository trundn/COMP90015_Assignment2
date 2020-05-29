import java.util.concurrent.TimeUnit;

/**
 * The Class TryConnectJob.
 */
public class TryConnectJob extends AbstractJob {

    /** The default backoff time. */
    private long defaultBackoffTime = 1;

    /** The minimum backoff time. */
    private long minimumBackoffTime = 1;

    /** The maximum backoff time. */
    private long maximumBackoffTime = 32;

    /**
     * Call.
     *
     * @return the void
     * @throws Exception the exception
     */
    @Override
    protected Void call() throws Exception {
        while (!this.isCancelled()) {
            SocketHandler handler = SocketHandler.getInstance();

            if (!handler.isConnected()) {
                if (this.minimumBackoffTime > this.maximumBackoffTime) {
                    this.minimumBackoffTime = this.defaultBackoffTime;
                }

                long leftLimit = 0L;
                long rightLimit = 1000L;
                long generatedLong = leftLimit
                        + (long) (Math.random() * (rightLimit - leftLimit));
                long delay = this.minimumBackoffTime
                        + generatedLong / rightLimit;

                TimeUnit.SECONDS.sleep(delay);

                this.minimumBackoffTime *= 2;

                // Establish connection to socket server.
                String message = handler.connect();
                updateMessage(message);
                MessageNotifier.getInstance().onMessageChanged(message);
            }
        }

        return null;
    }

}
