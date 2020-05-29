import org.json.simple.JSONObject;

/**
 * The Class RequestBuilder.
 */
public class RequestBuilder {

    /**
     * Builds the shut down request.
     *
     * @param hostAddress the host address
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static JSONObject buildShutDownRequest(String hostAddress,
            String userName, boolean isManager) {
        JSONObject request = new JSONObject();

        JSONObject content = new JSONObject();
        content.put(Constants.USER_NAME_ATTR, userName);
        content.put(Constants.MANAGER_ROLE_ATTR, isManager);

        request.put(Constants.CLIENT_SHUTDOWN_REQUEST, content);

        return request;
    }

    /**
     * Builds the ping request.
     *
     * @param hostAddress the host address
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static JSONObject buildPingRequest(String hostAddress) {
        JSONObject request = new JSONObject();
        request.put(Constants.PING_REQUEST, hostAddress);

        return request;
    }

    /**
     * Builds the handshake establishment request.
     *
     * @param userName  the user name
     * @param isManager the is manager
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static JSONObject buildHandshakeEstablishmentRequest(String userName,
            boolean isManager) {
        JSONObject request = new JSONObject();

        JSONObject content = new JSONObject();
        content.put(Constants.USER_NAME_ATTR, userName);
        content.put(Constants.MANAGER_ROLE_ATTR, isManager);

        request.put(Constants.HANDSHAKE_ESTABLISHMENT_REQUEST, content);

        return request;
    }

}
