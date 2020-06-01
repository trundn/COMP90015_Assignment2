import java.util.ArrayList;

import org.json.simple.JSONArray;
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
     * Extract user name list.
     *
     * @param message the message
     * @return the array list
     */
    public static final ArrayList<String> extractUserNameList(
            JSONObject message) {
        JSONObject body = (JSONObject) message.get(Constants.MESSAGE_BODY);
        JSONObject content = (JSONObject) body
                .get(Constants.MESSAE_EVENT_CONTENT);

        ArrayList<String> userNameList = new ArrayList<String>();
        JSONArray array = (JSONArray) content
                .get(Constants.USER_NAME_LIST_ATTR);
        for (int index = 0; index < array.size(); index++) {
            userNameList.add(array.get(index).toString());
        }

        return userNameList;
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
     * @param paramName    the parameter name
     * @return the string
     */
    public static final String extractValFromContent(JSONObject eventContent,
            String paramName) {
        return eventContent.get(paramName).toString();
    }

    /**
     * Extract double value from content.
     *
     * @param eventContent the event content
     * @param paramName    the parameter name
     * @return the double
     */
    public static final double extractDoubleValFromContent(
            JSONObject eventContent, String paramName) {
        String value = extractValFromContent(eventContent, paramName);
        return Double.parseDouble(value);
    }

    /**
     * Extract value from message.
     *
     * @param message   the message
     * @param paramName the parameter name
     * @return the string
     */
    public static final String extractValFromMessage(JSONObject message,
            String paramName) {
        JSONObject body = (JSONObject) message.get(Constants.MESSAGE_BODY);
        JSONObject content = (JSONObject) body
                .get(Constants.MESSAE_EVENT_CONTENT);
        return content.get(paramName).toString();
    }

    /**
     * Extract value from message as double.
     *
     * @param message   the message
     * @param paramName the parameter name
     * @return the double
     */
    public static final double extractDoubleValueFromMessage(JSONObject message,
            String paramName) {
        String value = extractValFromMessage(message, paramName);
        return Double.parseDouble(value);
    }

    /**
     * Extract boolean value from message.
     *
     * @param message   the message
     * @param paramName the parameter name
     * @return true, if successful
     */
    public static final boolean extractBooleanValueFromMessage(
            JSONObject message, String paramName) {
        String value = extractValFromMessage(message, paramName);
        return Boolean.parseBoolean(value);
    }

}
