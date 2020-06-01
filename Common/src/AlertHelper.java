import javafx.scene.control.ButtonType;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Window;

/**
 * The Class MessageHelper.
 */
public class AlertHelper {

    /**
     * Show alert.
     *
     * @param alertType the alert type
     * @param owner     the owner
     * @param title     the title
     * @param message   the message
     * @return the optional
     */
    public static void showAlert(Alert.AlertType alertType, Window owner,
            String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    /**
     * Show confirmation.
     *
     * @param owner   the owner
     * @param title   the title
     * @param message the message
     * @return the optional
     */
    public static Optional<ButtonType> showConfirmation(Window owner,
            String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES,
                ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initOwner(owner);
        return alert.showAndWait();
    }

    /**
     * Show confirmation.
     *
     * @param owner      the owner
     * @param title      the title
     * @param message    the message
     * @param okType     the ok type
     * @param cancelType the cancel type
     * @return the optional
     */
    public static Optional<ButtonType> showConfirmation(Window owner,
            String title, String message, ButtonType okType,
            ButtonType cancelType) {
        Alert alert = new Alert(AlertType.CONFIRMATION, message, okType,
                cancelType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initOwner(owner);
        return alert.showAndWait();
    }

    /**
     * Show confirmation.
     *
     * @param owner   the owner
     * @param title   the title
     * @param message the message
     * @param button1 the button 1
     * @param button2 the button 2
     * @param button3 the button 3
     * @return the optional
     */
    public static Optional<ButtonType> showConfirmation(Window owner,
            String title, String message, ButtonType button1,
            ButtonType button2, ButtonType button3) {
        Alert alert = new Alert(AlertType.CONFIRMATION, message, button1,
                button2, button3);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initOwner(owner);
        return alert.showAndWait();
    }

    /**
     * Show warning.
     *
     * @param owner   the owner
     * @param title   the title
     * @param message the message
     * @return the optional
     */
    public static Optional<ButtonType> showWarning(Window owner, String title,
            String message) {
        Alert alert = new Alert(AlertType.WARNING, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initOwner(owner);
        return alert.showAndWait();
    }

}
