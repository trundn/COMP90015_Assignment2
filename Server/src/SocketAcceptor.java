import java.time.Duration;
import java.util.concurrent.TimeoutException;

/**
 * The Class SocketAcceptor.
 */
public class SocketAcceptor {

    /** The instance. */
    private static SocketAcceptor INSTANCE;

    /** The Constant MAX_ACCEPTED_CONNECTION. */
    private static final int MAX_ACCEPTED_CONNECTION = 101;

    /** The Constant MAX_REQUEST_PROCESSING_THREAD. */
    private static final int MAX_REQUEST_PROCESSING_THREAD = 10;

    /** The Constant MAX_REQUEST_PROCESSING_QUEUE_SIZE. */
    private static final int MAX_REQUEST_PROCESSING_QUEUE_SIZE = 500;

    /** The connection process executor. */
    private ThreadPoolJobExecutor connectionProcessExecutor;

    /** The request process job executor. */
    private ThreadPoolJobExecutor requestProcessJobExecutor;

    /**
     * Gets the single instance of SocketAcceptor.
     *
     * @return single instance of SocketAcceptor
     */
    public static SocketAcceptor getInstance() {
        if (INSTANCE == null) {
            synchronized (SocketAcceptor.class) {
                if (INSTANCE == null)
                    INSTANCE = new SocketAcceptor();
            }
        }

        return INSTANCE;
    }

    /**
     * Instantiates a new socket acceptor.
     */
    private SocketAcceptor() {
        try {
            this.connectionProcessExecutor = new ThreadPoolJobExecutor(-1,
                    MAX_ACCEPTED_CONNECTION, MAX_ACCEPTED_CONNECTION);
            this.requestProcessJobExecutor = new ThreadPoolJobExecutor(-1,
                    MAX_REQUEST_PROCESSING_THREAD,
                    MAX_REQUEST_PROCESSING_QUEUE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Launch.
     *
     * @param port the port
     */
    public void launch(int port) {
        try {
            AcceptorJob job = new AcceptorJob(port,
                    this.connectionProcessExecutor,
                    this.requestProcessJobExecutor);
            this.connectionProcessExecutor.queue(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Clean up resources.
     */
    public void cleanUp() {
        try {
            this.connectionProcessExecutor.terminate();
            this.connectionProcessExecutor
                    .waitForTermination(Duration.ofSeconds(5).toMillis());

        } catch (TimeoutException e) {
            this.connectionProcessExecutor.forceInterrupt();
        }

        this.requestProcessJobExecutor.terminate();
        try {
            this.requestProcessJobExecutor
                    .waitForTermination(Duration.ofSeconds(5).toMillis());
        } catch (TimeoutException e) {
            this.requestProcessJobExecutor.forceInterrupt();
        }

    }

}
