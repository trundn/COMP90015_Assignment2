import org.json.simple.JSONObject;

public class EventMessageParser {

    /**
     * Extract event name.
     *
     * @param message the message
     * @return the string
     */
    public static final String extractEventName(JSONObject message) {
        JSONObject body = (JSONObject) message.get(Constants.MESSAGE_BODY);
        return body.get(Constants.MESSAGE_EVENT_NAME).toString();
    }

    /**
     * Extract user name.
     *
     * @param message the message
     * @return the string
     */
    public static final String extractUserName(JSONObject message) {
        JSONObject header = (JSONObject) message.get(Constants.MESSAGE_HEADER);
        return header.get(Constants.USER_NAME_ATTR).toString();
    }

    /**
     * Extract event content.
     *
     * @param message the message
     * @return the JSON object
     */
    public static final JSONObject extractEventContent(JSONObject message) {
        JSONObject body = (JSONObject) message.get(Constants.MESSAGE_BODY);
        return (JSONObject) body.get(Constants.MESSAE_EVENT_CONTENT);
    }

    /**
     * Extract value from content.
     *
     * @param eventContent the event content
     * @param paramName the parameter name
     * @return the string
     */
    public static final String extractValueFromContent(
            JSONObject eventContent, String paramName) {
        return eventContent.get(paramName).toString();
    }

    /**
     * Extract value from message.
     *
     * @param message the message
     * @param paramName the parameter name
     * @return the string
     */
    public static final String extractValueFromMessage(
            JSONObject message, String paramName) {
        JSONObject body = (JSONObject) message.get(Constants.MESSAGE_BODY);
        JSONObject content = (JSONObject) body.get(Constants.MESSAE_EVENT_CONTENT);
        return content.get(paramName).toString();
    }

}
