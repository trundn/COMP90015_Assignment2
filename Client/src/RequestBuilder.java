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
    public static JSONObject buildShutDownRequest(String hostAddress) {
        JSONObject request = new JSONObject();

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
        request.put(Constants.PING_OPERATION, hostAddress);

        return request;
    }

    /**
     * Builds the connection establishment request.
     *
     * @param userName  the user name
     * @param isManager the is manager
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static JSONObject buildConnectionEstablishmentRequest(
            String userName, boolean isManager) {
        JSONObject request = new JSONObject();

        JSONObject content = new JSONObject();
        content.put(Constants.USER_NAME_ATTR, userName);
        content.put(Constants.USER_NAME_ATTR, isManager);

        request.put(Constants.ESTABLISH_CONNECTION, content);

        return request;
    }

}
