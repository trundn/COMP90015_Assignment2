import org.json.simple.JSONObject;

/**
 * The Class RequestProcessJob.
 */
public class RequestProcessJob extends AbstractJob {

    /** The connection. */
    private SocketConnection connection;

    /** The request. */
    private JSONObject request;

    /**
     * Instantiates a new request process job.
     *
     * @param connection the connection
     * @param request    the request
     */
    public RequestProcessJob(SocketConnection connection, JSONObject request) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "The given request JSON object is NULL.");
        }

        this.request = request;
        this.connection = connection;
    }

    /**
     * Send response.
     *
     * @param response the response
     */
    private void sendResponse(JSONObject response) {
        if (this.connection != null) {
            ChangeNotifier.getInstance().onMessageChanged(String.format(
                    "Sending response message to client [%s]. Content: %s",
                    this.connection.getHostAddress(), response.toJSONString()));

            this.connection.send(response);
        }
    }

    /**
     * Call.
     *
     * @return the void
     * @throws Exception the exception
     */
    @Override
    protected Void call() throws Exception {
        if (!this.isCancelled()) {
            if (request
                    .containsKey(Constants.HANDSHAKE_ESTABLISHMENT_REQUEST)) {
                handleHandshakeEstablishment(request);
            }
        }

        return null;
    }

    /**
     * Handle handshake establishment.
     *
     * @param request the request
     */
    @SuppressWarnings("unchecked")
    private void handleHandshakeEstablishment(JSONObject request) {
        JSONObject messageContent;
        messageContent = (JSONObject) request
                .get(Constants.HANDSHAKE_ESTABLISHMENT_REQUEST);

        // Extract user name and manager role values
        String userName = messageContent.get(Constants.USER_NAME_ATTR)
                .toString();
        boolean isManager = Boolean.parseBoolean(
                messageContent.get(Constants.MANAGER_ROLE_ATTR).toString());

        String acknowledgment = Constants.ACK_OK;
        if (isManager) {
            SocketConnection managerRoleConnection = SocketManager.getInstance()
                    .anyUsersWithManagerRole();
            if (managerRoleConnection != null) {
                // Do not allow 2 users to have a manager role
                isManager = false;
                acknowledgment = Constants.ACK_NG;
                // Identify connection with manger role
                SocketManager.getInstance()
                        .setManagerRoleConnection(managerRoleConnection);
            }
        }

        // Update user information
        this.connection.setUserName(userName);
        this.connection.setManager(isManager);

        // Send response acknowledgement to client
        JSONObject response = new JSONObject();
        JSONObject reponseContent = new JSONObject();

        reponseContent.put(Constants.ACK_ATTR, acknowledgment);
        response.put(Constants.HANDSHAKE_ACKNOWLEDGMENT, reponseContent);
        this.sendResponse(response);
    }

}
