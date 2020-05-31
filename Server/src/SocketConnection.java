import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.json.simple.JSONObject;

/**
 * The Class SocketConnection.
 */
public class SocketConnection {

    /** The identifier. */
    private String identifier;

    /** The user name. */
    private String userName;

    /** The is manager. */
    private boolean isManager;

    /** The is joined white board. */
    private boolean isJoinedWhiteboard;

    /** The client socket. */
    private Socket clientSocket;

    /** The input stream. */
    private ObjectInputStream inputStream;

    /** The output stream. */
    private ObjectOutputStream outputStream;

    /**
     * Instantiates a new socket connection.
     *
     * @param id     the id
     * @param socket the socket
     */
    public SocketConnection(String id, Socket socket) {
        this.identifier = id;
        this.clientSocket = socket;

        try {
            this.inputStream = new ObjectInputStream(
                    this.clientSocket.getInputStream());
            this.outputStream = new ObjectOutputStream(
                    this.clientSocket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the identifier.
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Gets the host address.
     *
     * @return the host address
     */
    public String getHostAddress() {
        String address = null;

        if (this.clientSocket != null) {
            address = this.clientSocket.getInetAddress().getHostAddress();
        }

        return address;
    }

    /**
     * Gets the local address.
     *
     * @return the local address
     */
    public String getLocalAddress() {
        String result = "";

        if (this.clientSocket != null) {
            result = this.clientSocket.getLocalAddress().getHostAddress();
        }

        return result;
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
     * Checks if is manager.
     *
     * @return true, if is manager
     */
    public boolean isManager() {
        return isManager;
    }

    /**
     * Sets the manager.
     *
     * @param isManager the new manager
     */
    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }

    /**
     * Checks if is joined white board.
     *
     * @return true, if is joined white board
     */
    public boolean isJoinedWhiteboard() {
        return isJoinedWhiteboard;
    }

    /**
     * Sets the joined white board.
     *
     * @param isJoinedWhiteboard the new joined white board
     */
    public void setJoinedWhiteboard(boolean isJoinedWhiteboard) {
        this.isJoinedWhiteboard = isJoinedWhiteboard;
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

                String notifiedMessage = "Sent message to client. Content: "
                        + message.toString();

                if (!Constants.PING_EVT_NAME.equalsIgnoreCase(eventName)) {
                    System.out.println(notifiedMessage);
                    ChangeNotifier.getInstance()
                            .onMessageChanged(notifiedMessage);
                }
            } catch (IOException ex) {
                error = "Cannot send message to client. Error: "
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
     * Receive.
     *
     * @return the JSON object
     */
    public JSONObject receive() {
        JSONObject message = null;

        if (this.inputStream != null) {
            try {
                message = (JSONObject) inputStream.readObject();
                String eventName = EventMessageParser.extractEventName(message);

                if (!Constants.PING_EVT_NAME.equalsIgnoreCase(eventName)) {
                    System.out.println("Received message from client. Content: "
                            + message);
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
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

            if (this.clientSocket != null) {
                this.clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
