import java.util.concurrent.TimeUnit;

/**
 * The Class TryConnectJob.
 */
public class TryConnectJob extends AbstractJob {

    /** The default back off time. */
    private long defaultBackoffTime = 1;

    /** The minimum back off time. */
    private long minimumBackoffTime = 1;

    /** The maximum back off time. */
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

                // Notify the message changed
                updateMessage(message);
                ChangeNotifier.getInstance().onMessageChanged(message);

                // Notify connection status changed
                if (handler.isConnected()) {
                    ChangeNotifier.getInstance().onConnectionChanged(true);
                }
            }
        }

        return null;
    }

}
