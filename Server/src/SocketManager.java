import java.util.HashMap;
import java.util.Map;

/**
 * The Class SocketManager.
 */
public class SocketManager {

    /** The instance. */
    private static SocketManager INSTANCE;

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
    public void put(SocketConnection connection) {
        this.connectionMap.put(connection.getIdentifier(), connection);
    }

    /**
     * Gets the.
     *
     * @param identifier the identifier
     * @return the socket connection
     */
    public SocketConnection get(String identifier) {
        SocketConnection connection = null;

        if (!StringHelper.isNullOrEmpty(identifier)) {
            connection = this.connectionMap.get(identifier);
        }

        return connection;
    }

}
