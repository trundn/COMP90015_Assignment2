
/**
 * The Class StringHelper.
 */
public class StringHelper {

    /**
     * Checks if is null or empty.
     *
     * @param input the input
     * @return true, if is null or empty
     */
    public static boolean isNullOrEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
