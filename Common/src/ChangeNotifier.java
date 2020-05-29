
/**
 * The Class ChangeNotifier.
 */
public class ChangeNotifier {

    /** The instance. */
    private static ChangeNotifier INSTANCE;

    /** The scene callback. */
    ScenceCallback sceneCallback;

    /**
     * Gets the single instance of MessageNotifier.
     *
     * @return single instance of MessageNotifier
     */
    public static ChangeNotifier getInstance() {
        if (INSTANCE == null) {
            synchronized (ChangeNotifier.class) {
                if (INSTANCE == null)
                    INSTANCE = new ChangeNotifier();
            }
        }

        return INSTANCE;
    }

    /**
     * Register scene callback.
     *
     * @param callback the callback
     */
    public void registerSceneCallback(ScenceCallback callback) {
        this.sceneCallback = callback;
    }

    /**
     * Unregister scene callback.
     */
    public void unregisterSceneCallback() {
        this.sceneCallback = null;
    }

    /**
     * On message changed.
     *
     * @param newMessage the new message
     */
    public void onMessageChanged(String newMessage) {
        System.out.println(newMessage);

        if (this.sceneCallback != null) {
            this.sceneCallback.onMessageChanged(newMessage);
        }
    }

    /**
     * On connection changed.
     *
     * @param isConnected the is connected
     */
    public void onConnectionChanged(boolean isConnected) {
        if (this.sceneCallback != null) {
            this.sceneCallback.onConnectionStatusChanged(isConnected);
        }
    }

}
