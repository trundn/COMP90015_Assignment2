import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * The Class WhiteboardClient.
 */
public class WhiteboardClient extends Application {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Create the Border Pane
        BorderPane borderPane = new BorderPane();

        ToggleButton lineButton = new ToggleButton(Constants.LINE_BUTTON_TEXT);
        ToggleButton circleButton = new ToggleButton(Constants.CIRCLE_BUTTON_TEXT);
        ToggleButton rectButton = new ToggleButton(Constants.RECTANGLE_BUTTON_TEXT);
        ToggleButton textButton = new ToggleButton(Constants.TEXT_BUTTON_TEXT);

        ToggleButton[] toogleButtonList = { lineButton, circleButton,
                rectButton, textButton };

        ToggleGroup toogleGroup = new ToggleGroup();

        for (ToggleButton button : toogleButtonList) {
            button.setMinWidth(90);
            button.setToggleGroup(toogleGroup);
            button.setCursor(Cursor.HAND);
        }

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(lineButton, circleButton, rectButton,
                textButton);
        vBox.setPadding(new Insets(5));
        vBox.setStyle("-fx-background-color: #999");
        vBox.setPrefWidth(100);

        // Create the Canvas
        Canvas canvas = new Canvas(1080, 790);

        // Get the graphics context of the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);

        // Add the Canvas to the Pane
        borderPane.setLeft(vBox);
        borderPane.setCenter(canvas);

        // Create the Scene
        Scene scene = new Scene(borderPane, 1200, 800);

        // Add the Scene to the Stage
        stage.setScene(scene);

        // Set the Title of the Stage
        stage.setTitle("Whiteboard Client");

        // Display the Stage
        stage.show();
    }

}
