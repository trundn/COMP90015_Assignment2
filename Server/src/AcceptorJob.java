import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

import javax.net.ServerSocketFactory;

/**
 * The Class AcceptorJob.
 */
public class AcceptorJob extends AbstractJob {

    /** The port. */
    private int port;

    /** The connection process executor. */
    private ThreadPoolJobExecutor connectionProcessExecutor;

    /** The request process job executor. */
    private ThreadPoolJobExecutor requestProcessJobExecutor;

    /**
     * Instantiates a new acceptor job.
     *
     * @param port                      the port
     * @param connectionProcessExecutor the connection process executor
     * @param requestProcessJobExecutor the request process job executor
     */
    public AcceptorJob(int port,
            ThreadPoolJobExecutor connectionProcessExecutor,
            ThreadPoolJobExecutor requestProcessJobExecutor) {
        this.port = port;
        this.connectionProcessExecutor = connectionProcessExecutor;
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
        ServerSocketFactory factory = ServerSocketFactory.getDefault();

        try (ServerSocket server = factory.createServerSocket(this.port)) {
            while (!this.isCancelled()) {
                // Accept the client socket connection.
                Socket client = server.accept();

                // Instantiate socket connection.
                String clientId = UUID.randomUUID().toString();
                SocketConnection connection = new SocketConnection(clientId,
                        client);
                SocketManager.getInstance().put(connection);

                // Notify message to the main scene.
                MessageNotifier.getInstance()
                        .onMessageChanged(String.format(
                                "Accepted a new client connection [%s]",
                                client.getInetAddress().getHostAddress()));

                // Instantiate the client handler job.
                ClientHandlerJob job = new ClientHandlerJob(connection,
                        this.requestProcessJobExecutor);
                this.connectionProcessExecutor.queue(job);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

}
