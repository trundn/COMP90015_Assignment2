import org.json.simple.JSONObject;

/**
 * The Class EventMessageBuilder.
 */
public class EventMessageBuilder {

    /**
     * Builds the event header.
     *
     * @param userName the user name
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static JSONObject buildEventHeader(String userName) {
        JSONObject header = new JSONObject();

        header.put(Constants.USER_NAME_ATTR, userName);

        return header;
    }

    /**
     * Builds the event body.
     *
     * @param eventName    the event name
     * @param eventContent the event content
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static JSONObject buildEventBody(String eventName,
            JSONObject eventContent) {
        JSONObject body = new JSONObject();

        body.put(Constants.MESSAGE_EVENT_NAME, eventName);
        body.put(Constants.MESSAE_EVENT_CONTENT, eventContent);

        return body;
    }

    /**
     * Builds the event message.
     *
     * @param header the header
     * @param body   the body
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static final JSONObject buildEventMessage(JSONObject header,
            JSONObject body) {
        JSONObject message = new JSONObject();

        message.put(Constants.MESSAGE_HEADER, header);
        message.put(Constants.MESSAGE_BODY, body);

        return message;
    }

    /**
     * Builds the client shut down message.
     *
     * @param userName  the user name
     * @param isManager the is manager
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static JSONObject buildClientShutDownMessage(String userName,
            boolean isManager) {
        JSONObject content = new JSONObject();
        content.put(Constants.MANAGER_ROLE_ATTR, isManager);

        JSONObject header = buildEventHeader(userName);
        JSONObject body = buildEventBody(Constants.CLIENT_SHUTDOWN_EVT_NAME,
                content);

        return buildEventMessage(header, body);
    }

    /**
     * Builds the white board manager shutdown broadcast message.
     *
     * @return the JSON object
     */
    public static JSONObject buildWhiteboardManagerShutdownBroadcastMessage() {
        JSONObject content = new JSONObject();

        JSONObject header = buildEventHeader("");
        JSONObject body = buildEventBody(
                Constants.WHITEBOARD_OWNER_SHUTDOWN_EVT_NAME, content);

        return buildEventMessage(header, body);
    }

    /**
     * Builds the ping message.
     *
     * @param hostAddress the host address
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static JSONObject buildPingMessage(String hostAddress) {
        JSONObject content = new JSONObject();
        content.put(Constants.HOST_ADDRESS_ATTR, hostAddress);

        JSONObject header = buildEventHeader("");
        JSONObject body = buildEventBody(Constants.PING_EVT_NAME, content);

        return buildEventMessage(header, body);
    }

    /**
     * Builds the handshake establishment message.
     *
     * @param userName  the user name
     * @param isManager the is manager
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static JSONObject buildHandshakeEstablishmentMessage(String userName,
            boolean isManager) {
        JSONObject content = new JSONObject();
        content.put(Constants.MANAGER_ROLE_ATTR, isManager);

        JSONObject header = buildEventHeader(userName);
        JSONObject body = buildEventBody(
                Constants.HANDSHAKE_ESTABLISHMENT_EVT_NAME, content);

        return buildEventMessage(header, body);
    }

    /**
     * Builds the handshake establishment acknowledgment message.
     *
     * @param acknowledgment the acknowledgment
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static JSONObject buildHandshakeEstablishmentAckMessage(
            String acknowledgment) {
        JSONObject content = new JSONObject();
        content.put(Constants.ACK_ATTR, acknowledgment);

        JSONObject header = buildEventHeader("");
        JSONObject body = buildEventBody(
                Constants.HANDSHAKE_ACKNOWLEDGMENT_EVT_NAME, content);

        return buildEventMessage(header, body);
    }

    /**
     * Builds the request white board join approval message.
     *
     * @param userName the user name
     * @return the JSON object
     */
    public static JSONObject buildRequestWhiteboardJoinApprovalMessage(
            String userName) {
        JSONObject content = new JSONObject();

        JSONObject header = buildEventHeader(userName);
        JSONObject body = buildEventBody(
                Constants.REQUEST_WHITEBOARD_JOIN_APPROVAL_EVT_NAME, content);

        return buildEventMessage(header, body);
    }

    /**
     * Builds the request white board join approval ack message.
     *
     * @param userName the user name
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static JSONObject buildRequestWhiteboardJoinApprovalAckMessage(
            String userName, String acknowledgment) {
        JSONObject content = new JSONObject();
        content.put(Constants.ACK_ATTR, acknowledgment);

        JSONObject header = buildEventHeader(userName);
        JSONObject body = buildEventBody(
                Constants.REQUEST_WHITEBOARD_JOIN_APPROVAL_ACK_EVT_NAME, content);

        return buildEventMessage(header, body);
    }

    /**
     * Builds the line synchronization message.
     *
     * @param userName the user name
     * @param startX   the start X
     * @param startY   the start Y
     * @param endX     the end X
     * @param endY     the end Y
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static JSONObject buildLineSynMessage(String userName, double startX,
            double startY, double endX, double endY) {
        JSONObject content = new JSONObject();
        content.put(Constants.START_X_ATTR, startX);
        content.put(Constants.START_Y_ATTR, startY);
        content.put(Constants.END_X_ATTR, endX);
        content.put(Constants.END_Y_ATTR, endY);

        JSONObject header = buildEventHeader(userName);
        JSONObject body = buildEventBody(Constants.LINE_SYN_EVT_NAME, content);

        return buildEventMessage(header, body);
    }

    /**
     * Builds the circle synchronization message.
     *
     * @param userName the user name
     * @param centerX  the center X
     * @param centerY  the center Y
     * @param radius   the radius
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static final JSONObject buildCircleSynMessage(String userName,
            double centerX, double centerY, double radius) {
        JSONObject content = new JSONObject();
        content.put(Constants.CENTER_X_ATTR, centerX);
        content.put(Constants.CENTER_Y_ATTR, centerY);
        content.put(Constants.RADIUS_ATTR, radius);

        JSONObject header = buildEventHeader(userName);
        JSONObject body = buildEventBody(Constants.CIRCLE_SYN_EVT_NAME,
                content);

        return buildEventMessage(header, body);
    }

    /**
     * Builds the rectangle synchronization message.
     *
     * @param userName the user name
     * @param startX   the start X
     * @param startY   the start Y
     * @param width    the width
     * @param height   the height
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static final JSONObject buildRectangleSynMessage(String userName,
            double startX, double startY, double width, double height) {
        JSONObject content = new JSONObject();
        content.put(Constants.START_X_ATTR, startX);
        content.put(Constants.START_Y_ATTR, startY);
        content.put(Constants.WIDTH_ATTR, width);
        content.put(Constants.HEIGHT_ATTR, height);

        JSONObject header = buildEventHeader(userName);
        JSONObject body = buildEventBody(Constants.RECTANGLE_SYN_EVT_NAME,
                content);

        return buildEventMessage(header, body);
    }

    /**
     * Builds the text synchronization message.
     *
     * @param userName the user name
     * @param startX   the start X
     * @param startY   the start Y
     * @param text     the text
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static final JSONObject buildTextSynMessage(String userName,
            double startX, double startY, String text) {
        JSONObject content = new JSONObject();
        content.put(Constants.START_X_ATTR, startX);
        content.put(Constants.START_Y_ATTR, startY);
        content.put(Constants.TEXT_ATTR, text);

        JSONObject header = buildEventHeader(userName);
        JSONObject body = buildEventBody(Constants.TEXT_SYN_EVT_NAME, content);

        return buildEventMessage(header, body);
    }

    /**
     * Builds the white board synchronization message.
     *
     * @param userName the user name
     * @return the JSON object
     */
    public static final JSONObject buildWhiteboardSynMessage(String userName) {
        JSONObject content = new JSONObject();

        JSONObject header = buildEventHeader(userName);
        JSONObject body = buildEventBody(Constants.WHITE_BOARD_SYS_EVT_NAME,
                content);

        return buildEventMessage(header, body);
    }

    /**
     * Builds the white board synchronization acknowledgment message.
     *
     * @param userName      the user name
     * @param imageAsString the image as string
     * @return the JSON object
     */
    @SuppressWarnings("unchecked")
    public static final JSONObject buildWhiteboardSynAckMessage(String userName,
            String imageAsString) {
        JSONObject content = new JSONObject();
        content.put(Constants.IMAGE_AS_STRING_ATTR, imageAsString);

        JSONObject header = buildEventHeader(userName);
        JSONObject body = buildEventBody(
                Constants.WHITE_BOARD_SYS_ACKNOWLEDGMENT, content);

        return buildEventMessage(header, body);
    }

}
