import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

/**
 * The Class SocketController.
 */
public class SocketHandler {

    /** The instance. */
    private static SocketHandler INSTANCE;

    /** The is connected. */
    private boolean isConnected = false;

    /** The port. */
    private int port;

    /** The host address. */
    private String hostAddress;

    /** The socket. */
    private Socket socket;

    /** The input stream. */
    private ObjectInputStream inputStream;

    /** The output stream. */
    private ObjectOutputStream outputStream;

    /**
     * Gets the single instance of SocketController.
     *
     * @return single instance of SocketController
     */
    public static SocketHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (SocketHandler.class) {
                if (INSTANCE == null)
                    INSTANCE = new SocketHandler();
            }
        }

        return INSTANCE;
    }

    /**
     * Checks if is connected.
     *
     * @return true, if is connected
     */
    public synchronized boolean isConnected() {
        return isConnected;
    }

    /**
     * Sets the connected.
     *
     * @param isConnected the new connected
     */
    public synchronized void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    /**
     * Gets the port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the port.
     *
     * @param port the new port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Gets the host address.
     *
     * @return the host address
     */
    public String getHostAddress() {
        return hostAddress;
    }

    /**
     * Sets the host address.
     *
     * @param hostAddress the new host address
     */
    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    /**
     * Gets the local address.
     *
     * @return the local address
     */
    public String getLocalAddress() {
        String result = "";

        if (this.socket != null) {
            result = this.socket.getLocalAddress().getHostAddress();
        }

        return result;
    }

    /**
     * Connect.
     *
     * @return the string
     */
    public String connect() {
        String message = "";

        try {
            this.cleanUp();

            this.socket = new Socket(this.hostAddress, this.port);

            this.outputStream = new ObjectOutputStream(
                    this.socket.getOutputStream());
            this.inputStream = new ObjectInputStream(
                    this.socket.getInputStream());

            this.setConnected(true);
            message = "Connected to server successfully.";
        } catch (UnknownHostException ex) {
            this.setConnected(false);
            message = "The host address could not be determined. Address: "
                    + this.hostAddress;
        } catch (IOException ex) {
            this.setConnected(false);
            message = "Failed to connect to server. Error: " + ex.getMessage();
        }

        return message;
    }

    /**
     * Send.
     *
     * @param message the message
     * @return the string
     */
    public String send(JSONObject message) {
        String error = "";

        if (this.outputStream != null) {
            try {
                this.outputStream.writeObject(message);
                String eventName = EventMessageParser.extractEventName(message);

                if (!Constants.PING_EVT_NAME.equalsIgnoreCase(eventName)) {
                    System.out.println("Sent message to server. Content: "
                            + message.toString());
                }
            } catch (IOException ex) {
                error = "Cannot send message to server. Error: "
                        + ex.getMessage();
                ex.printStackTrace();
            }
        } else {
            error = "There is no connection between server and client.";
            System.out.println(error);
        }

        return error;
    }

    /**
     * Send urgent data.
     *
     * @param data the data
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void sendUrgentData(int data) throws IOException {
        if (this.socket != null) {
            this.socket.sendUrgentData(data);
        }
    }

    /**
     * Receive.
     *
     * @return the JSON object
     */
    public JSONObject receive() {
        JSONObject message = null;

        if (this.inputStream != null) {
            try {
                message = (JSONObject) this.inputStream.readObject();
                String eventName = EventMessageParser.extractEventName(message);

                if (!Constants.PING_EVT_NAME.equalsIgnoreCase(eventName)) {
                    System.out.println("Received message from server. Content: "
                            + message);
                }
            } catch (IOException | ClassNotFoundException ex) {
                // Do nothing here.
            }
        }

        return message;
    }

    /**
     * Clean up.
     */
    public void cleanUp() {
        try {
            if (this.inputStream != null) {
                this.inputStream.close();
            }

            if (this.outputStream != null) {
                this.outputStream.close();
            }

            if (this.socket != null) {
                this.socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Notify shut down.
     */
    public void notifyShutDown() {
        // Send shutdown notification to server.
        JSONObject request = EventMessageBuilder.buildShutDownMessage(
                UserInformation.getInstance().getUserName(),
                UserInformation.getInstance().isManager());
        this.send(request);
    }
}
