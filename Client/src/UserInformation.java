
public class UserInformation {

    /** The instance. */
    private static UserInformation INSTANCE;

    /** The user name. */
    private String userName;

    /** The operation. */
    WhiteboardOperation operation = WhiteboardOperation.JOIN;

    /**
     * Gets the single instance of UserInformation.
     *
     * @return single instance of UserInformation
     */
    public static UserInformation getInstance() {
        if (INSTANCE == null) {
            synchronized (UserInformation.class) {
                if (INSTANCE == null)
                    INSTANCE = new UserInformation();
            }
        }

        return INSTANCE;
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName the new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the operation.
     *
     * @return the operation
     */
    public WhiteboardOperation getOperation() {
        return operation;
    }

    /**
     * Sets the operation.
     *
     * @param operation the new operation
     */
    public void setOperation(WhiteboardOperation operation) {
        this.operation = operation;
    }

    /**
     * Checks if is manager.
     *
     * @return true, if is manager
     */
    public boolean isManager() {
        boolean result = false;

        if (this.operation == WhiteboardOperation.CREATE) {
            result = true;
        }

        return result;
    }

}
