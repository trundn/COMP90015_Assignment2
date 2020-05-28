
/**
 * The Class WhiteboardServer.
 */
public class WhiteboardServer {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                SocketAcceptor.getInstance().cleanUp();
            }
        });

        if (args.length < 1) {
            System.out.println(
                    "The port should be specified in command line argument.");
        } else if (args.length > 1) {
            System.out.println(
                    "The number of command line arguments is invalid.");
        } else {
            try {
                // Parse port and dictionary path from arguments.
                int port = Integer.parseInt(args[0]);

                if (port < Constants.MIN_PORT_NUMBER
                        || port > Constants.MAX_PORT_NUMBER) {
                    throw new IllegalArgumentException("Invalid port number: "
                            + port + ". It must be in range ("
                            + Constants.MIN_PORT_NUMBER + ", "
                            + Constants.MAX_PORT_NUMBER + ").");
                }

                // Launch the socket acceptor.
                SocketAcceptor.getInstance().launch(port);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
