
/**
 * The Class MessageNotifier.
 */
public class MessageNotifier {

    /** The instance. */
    private static MessageNotifier INSTANCE;

    /**
     * Gets the single instance of MessageNotifier.
     *
     * @return single instance of MessageNotifier
     */
    public static MessageNotifier getInstance() {
        if (INSTANCE == null) {
            synchronized (MessageNotifier.class) {
                if (INSTANCE == null)
                    INSTANCE = new MessageNotifier();
            }
        }

        return INSTANCE;
    }

    /**
     * On message changed.
     *
     * @param newMessage the new message
     */
    public void onMessageChanged(String newMessage) {
        System.out.println(newMessage);
    }

}
