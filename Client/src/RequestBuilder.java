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

}
