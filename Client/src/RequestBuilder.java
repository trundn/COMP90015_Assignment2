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

    /**
     * Builds the line syn request.
     *
     * @param userName the user name
     * @param startX   the start X
     * @param startY   the start Y
     * @param endX     the end X
     * @param endY     the end Y
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static JSONObject buildLineSynRequest(String userName, double startX,
            double startY, double endX, double endY) {
        JSONObject request = new JSONObject();

        JSONObject header = new JSONObject();
        header.put(Constants.USER_NAME_ATTR, userName);

        JSONObject content = new JSONObject();
        content.put(Constants.START_X_ATTR, startX);
        content.put(Constants.START_Y_ATTR, startY);
        content.put(Constants.END_X_ATTR, endX);
        content.put(Constants.END_Y_ATTR, endY);

        request.put(Constants.HEADER_ATTR, header);
        request.put(Constants.LINE_SYN_REQUEST, content);

        return request;
    }

    /**
     * Builds the circle syn request.
     *
     * @param userName the user name
     * @param centerX  the center X
     * @param centerY  the center Y
     * @param radius   the radius
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static final JSONObject buildCircleSynRequest(String userName,
            double centerX, double centerY, double radius) {
        JSONObject request = new JSONObject();

        JSONObject header = new JSONObject();
        header.put(Constants.USER_NAME_ATTR, userName);

        JSONObject content = new JSONObject();
        content.put(Constants.CENTER_X_ATTR, centerX);
        content.put(Constants.CENTER_Y_ATTR, centerY);
        content.put(Constants.RADIUS_ATTR, radius);

        request.put(Constants.HEADER_ATTR, header);
        request.put(Constants.CIRCLE_SYN_REQUEST, content);

        return request;
    }

    /**
     * Builds the rectangle syn request.
     *
     * @param userName the user name
     * @param startX   the start X
     * @param startY   the start Y
     * @param width    the width
     * @param height   the height
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static final JSONObject buildRectangleSynRequest(String userName,
            double startX, double startY, double width, double height) {
        JSONObject request = new JSONObject();

        JSONObject header = new JSONObject();
        header.put(Constants.USER_NAME_ATTR, userName);

        JSONObject content = new JSONObject();
        content.put(Constants.START_X_ATTR, startX);
        content.put(Constants.START_Y_ATTR, startY);
        content.put(Constants.WIDTH_ATTR, width);
        content.put(Constants.HEIGHT_ATTR, height);

        request.put(Constants.HEADER_ATTR, header);
        request.put(Constants.RECTANGLE_SYN_REQUEST, content);

        return request;
    }

    /**
     * Builds the text syn request.
     *
     * @param userName the user name
     * @param startX   the start X
     * @param startY   the start Y
     * @param text     the text
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static final JSONObject buildTextSynRequest(String userName,
            double startX, double startY, String text) {
        JSONObject request = new JSONObject();

        JSONObject header = new JSONObject();
        header.put(Constants.USER_NAME_ATTR, userName);

        JSONObject content = new JSONObject();
        content.put(Constants.START_X_ATTR, startX);
        content.put(Constants.START_Y_ATTR, startY);
        content.put(Constants.TEXT_ATTR, text);

        request.put(Constants.HEADER_ATTR, header);
        request.put(Constants.TEXT_SYN_REQUEST, content);

        return request;
    }

}
