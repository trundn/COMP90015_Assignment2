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

    /** The user name. */
    private String userName;

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
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName the new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * @param request the request
     * @return the string
     */
    public String send(JSONObject request) {
        String error = "";

        if (this.outputStream != null) {
            try {
                this.outputStream.writeObject(request);
                System.out.println("Sent request to server. Content: "
                        + request.toString());
            } catch (IOException ex) {
                error = "Cannot send request to server. Error: "
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
        JSONObject response = null;

        if (this.inputStream != null) {
            try {
                response = (JSONObject) this.inputStream.readObject();
                System.out.println(
                        "Received response from server. Content: " + response);
            } catch (IOException | ClassNotFoundException ex) {
                // Do nothing here.
            }
        }

        return response;
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
        JSONObject request = RequestBuilder
                .buildShutDownRequest(this.getHostAddress());
        this.send(request);
    }
}
