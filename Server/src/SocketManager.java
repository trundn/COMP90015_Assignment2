import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class SocketManager.
 */
public class SocketManager {

    /** The instance. */
    private static SocketManager INSTANCE;

    /** The manager user connection. */
    private SocketConnection whiteboardOwnerConnection;

    /** The connection map. */
    private Map<String, SocketConnection> connectionMap;

    /**
     * Gets the single instance of SocketManager.
     *
     * @return single instance of SocketManager
     */
    public static SocketManager getInstance() {
        if (INSTANCE == null) {
            synchronized (SocketManager.class) {
                if (INSTANCE == null)
                    INSTANCE = new SocketManager();
            }
        }

        return INSTANCE;
    }

    /**
     * Instantiates a new socket manager.
     */
    public SocketManager() {
        this.connectionMap = new HashMap<String, SocketConnection>();
    }

    /**
     * Put.
     *
     * @param connection the connection
     */
    public synchronized void put(SocketConnection connection) {
        this.connectionMap.put(connection.getIdentifier(), connection);
    }

    /**
     * Removes the.
     *
     * @param identifier the identifier
     */
    public synchronized void remove(String identifier) {
        if (!StringHelper.isNullOrEmpty(identifier)) {
            this.connectionMap.remove(identifier);
        }
    }

    /**
     * Gets the.
     *
     * @param identifier the identifier
     * @return the socket connection
     */
    public synchronized SocketConnection get(String identifier) {
        SocketConnection connection = null;

        if (!StringHelper.isNullOrEmpty(identifier)) {
            connection = this.connectionMap.get(identifier);
        }

        return connection;
    }

    /**
     * Any user owning white board.
     *
     * @return the socket connection
     */
    public synchronized SocketConnection anyUserOwningWhiteboard() {
        SocketConnection result = null;

        for (SocketConnection connection : this.connectionMap.values()) {
            if (connection.isManager()) {
                result = connection;
                break;
            }
        }

        return result;
    }

    /**
     * Gets the user connection.
     *
     * @param identifier the identifier
     * @return the user connection
     */
    public synchronized SocketConnection getUserConnection(String userName) {
        SocketConnection result = null;

        if (!StringHelper.isNullOrEmpty(userName)) {
            for (SocketConnection connection : this.connectionMap.values()) {
                if (!StringHelper.isNullOrEmpty(connection.getUserName())
                        && connection.getUserName()
                                .equalsIgnoreCase(userName)) {
                    result = connection;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Gets the all user connection list.
     *
     * @return the all user connection list
     */
    public Collection<SocketConnection> getAllUserConnectionList() {
        return this.connectionMap.values();
    }

    /**
     * Gets the user connection list.
     *
     * @param exceptForUser the except for user
     * @return the user connection list
     */
    public ArrayList<SocketConnection> getUserConnectionList(
            String exceptForUser) {
        ArrayList<SocketConnection> returnList = new ArrayList<SocketConnection>();

        if (!StringHelper.isNullOrEmpty(exceptForUser)) {
            for (SocketConnection connection : this.connectionMap.values()) {
                if (!connection.getUserName().equalsIgnoreCase(exceptForUser)) {
                    returnList.add(connection);
                }
            }
        }

        return returnList;
    }

    /**
     * Gets the white board owner connection.
     *
     * @return the white board owner connection
     */
    public SocketConnection getWhiteboardOwnerConnection() {
        return this.whiteboardOwnerConnection;
    }

    /**
     * Sets the white board owner connection.
     *
     * @param whiteboardOwner the new white board owner connection
     */
    public synchronized void setWhiteboardOwnerConnection(
            SocketConnection whiteboardOwner) {
        this.whiteboardOwnerConnection = whiteboardOwner;
    }

}
