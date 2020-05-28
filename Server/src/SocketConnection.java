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
                System.out.println("Sent response to client. Content: "
                        + request.toString());
            } catch (IOException ex) {
                error = "Cannot send response to client. Error: "
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
        JSONObject request = null;

        if (this.inputStream != null) {
            try {
                request = (JSONObject) inputStream.readObject();
                System.out.println(
                        "Received request from client. Content: " + request);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        return request;
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
