import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * The Class WhiteboardClient.
 */
public class WhiteboardClient extends Application {

    /** The main canvas. */
    private CanvasEx canvas;

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Start.
     *
     * @param stage the stage
     */
    @Override
    public void start(Stage stage) {
        // Create menu bar and tool bar
        MenuBar menuBar = createMenuBar(stage);
        VBox toolBar = createToolbar();

        // Create canvas extension
        this.canvas = new CanvasEx(Constants.CANVAS_WIDTH,
                Constants.CANVAS_HEIGHT);

        // Create the Border Pane
        BorderPane borderPane = new BorderPane();

        // Add the Menu bar, Tool bar, and Canvas to the Pane
        borderPane.setTop(menuBar);
        borderPane.setLeft(toolBar);
        borderPane.setCenter(this.canvas);

        // Create the Scene
        Scene scene = new Scene(borderPane, Constants.SCENE_WIDTH,
                Constants.CANVAS_HEIGHT);

        // Add the Scene to the Stage
        stage.setScene(scene);

        // Set the Title of the Stage
        stage.setTitle("Whiteboard Client");

        // Display the Stage
        stage.show();
    }

    /**
     * Creates the tool bar.
     *
     * @return the v box
     */
    private VBox createToolbar() {
        // Create toggle buttons
        ToggleButton lineButton = new ToggleButton(Constants.LINE_BUTTON_TEXT);
        ToggleButton circleButton = new ToggleButton(
                Constants.CIRCLE_BUTTON_TEXT);
        ToggleButton rectButton = new ToggleButton(
                Constants.RECTANGLE_BUTTON_TEXT);
        ToggleButton textButton = new ToggleButton(Constants.TEXT_BUTTON_TEXT);

        // Create toggle button group
        ToggleButton[] toogleButtonList = { lineButton, circleButton,
                rectButton, textButton };
        ToggleGroup toogleGroup = new ToggleGroup();

        for (ToggleButton button : toogleButtonList) {
            button.setMinWidth(Constants.TOOGLE_BUTTON_MIN_WIDTH);
            button.setToggleGroup(toogleGroup);
            button.setCursor(Cursor.HAND);
        }

        // Register action for Line toggle button
        lineButton.setOnAction(evt -> {
            if (lineButton.isSelected()) {
                this.canvas.setDrawToolType(DrawToolType.LINE);
            }
        });

        // Register action for Circle toggle button
        circleButton.setOnAction(evt -> {
            if (circleButton.isSelected()) {
                this.canvas.setDrawToolType(DrawToolType.CIRCLE);
            }
        });

        // Register action for Rectangle toggle button
        rectButton.setOnAction(evt -> {
            if (rectButton.isSelected()) {
                this.canvas.setDrawToolType(DrawToolType.RECTANGLE);
            }
        });

        // Register action for Text toggle button
        textButton.setOnAction(evt -> {
            if (textButton.isSelected()) {
                this.canvas.setDrawToolType(DrawToolType.TEXT);
            }
        });

        // Create Text Area
        TextArea textArea = new TextArea();
        textArea.setPrefRowCount(3);

        // Register text changed event for Text Area
        textArea.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    this.canvas.notifyTextChanged(newValue);
                });

        // Build Tool bar VBox
        VBox vBox = new VBox(Constants.VBOX_SPACING);
        vBox.getChildren().addAll(lineButton, circleButton, rectButton,
                textButton, textArea);
        vBox.setPadding(new Insets(Constants.VBOX_PADDING));
        vBox.setStyle(Constants.DRAW_TOOLS_BACKGROUND_COLOR);
        vBox.setPrefWidth(Constants.VBOX_PREF_WIDTH);

        return vBox;
    }

    /**
     * Creates the menu bar.
     *
     * @param stage the stage
     * @return the menu bar
     */
    private MenuBar createMenuBar(Stage stage) {
        // Create File menu and all corresponding menu items
        Menu fileMenu = new Menu(Constants.FILE_MENU);
        MenuItem newMenuItem = new MenuItem(Constants.NEW_MENU_ITEM);
        MenuItem openMenuItem = new MenuItem(Constants.OPEN_MENU_ITEM);
        MenuItem saveMenuItem = new MenuItem(Constants.SAVE_MENU_ITEM);
        MenuItem saveAsMenuItem = new MenuItem(Constants.SAVE_AS_MENU_ITEM);
        MenuItem closeMenuItem = new MenuItem(Constants.CLOSE_MENU_ITEM);

        // Build key code combinations
        newMenuItem.setAccelerator(
                new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        openMenuItem.setAccelerator(
                new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        saveMenuItem.setAccelerator(
                new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveAsMenuItem.setAccelerator(
                new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        closeMenuItem.setAccelerator(
                new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));

        // Add menu items to parent menu
        fileMenu.getItems().add(newMenuItem);
        fileMenu.getItems().add(openMenuItem);
        fileMenu.getItems().add(saveMenuItem);
        fileMenu.getItems().add(saveAsMenuItem);
        fileMenu.getItems().add(closeMenuItem);

        // Register action for New menu item
        newMenuItem.setOnAction(evt -> {
            this.canvas.clearGraphicsContext();
        });

        // Register action for Open menu item
        openMenuItem.setOnAction(evt -> {
            FileChooser openFile = buildFileChooser(Constants.OPEN_FILE_TILE);

            File file = openFile.showOpenDialog(stage);
            if (file != null) {
                try {
                    InputStream io = new FileInputStream(file);
                    Image img = new Image(io);
                    this.canvas.drawImage(img);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
        });

        // Register action for Save menu item
        saveMenuItem.setOnAction(evt -> {
            FileChooser savefile = buildFileChooser(Constants.SAVE_FILE_TITLE);

            File file = savefile.showSaveDialog(stage);
            if (file != null) {
                this.canvas.saveImage(file);
            }
        });

        // Add menu to main menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    /**
     * Builds the file chooser.
     *
     * @param tille the title
     * @return the file chooser
     */
    private FileChooser buildFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter(
                        Constants.PNG_FILE_EXTENSION_UPPER,
                        Constants.PNG_FILE_FILTER));

        return fileChooser;
    }

}
