import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Class WhiteboardServer.
 */
public class WhiteboardServer extends Application implements ScenceCallback {

    /** The socket port. */
    private static int socketPort;

    /** The IP address field. */
    private TextField ipAddressField;

    /** The port field. */
    private TextField portField;

    /** The application log area. */
    private TextArea appLogArea;

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                SocketAcceptor.getInstance().cleanUp();
            }
        });

        if (args.length < 1) {
            System.out.println(
                    "The port should be specified in command line argument.");
        } else if (args.length > 1) {
            System.out.println(
                    "The number of command line arguments is invalid.");
        } else {
            try {
                // Parse port and dictionary path from arguments.
                int port = Integer.parseInt(args[0]);

                if (port < Constants.MIN_PORT_NUMBER
                        || port > Constants.MAX_PORT_NUMBER) {
                    throw new IllegalArgumentException("Invalid port number: "
                            + port + ". It must be in range ("
                            + Constants.MIN_PORT_NUMBER + ", "
                            + Constants.MAX_PORT_NUMBER + ").");
                }

                // Keep the socket port.
                WhiteboardServer.socketPort = port;

                // Launch the socket acceptor.
                SocketAcceptor.getInstance().launch(port);

                // Launch UI application
                Application.launch(args);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * On message changed.
     *
     * @param newMessage the new message
     */
    @Override
    public void onMessageChanged(String newMessage) {
        this.appLogArea.appendText(newMessage);
        this.appLogArea.appendText(Constants.NEW_LINE);
    }

    /**
     * On connection status changed.
     *
     * @param isConnected the is connected
     */
    @Override
    public void onConnectionStatusChanged(boolean isConnected) {
        // TODO Auto-generated method stub

    }

    /**
     * Start.
     *
     * @param stage the stage
     * @throws Exception the exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Create IpAddress label
        Label addressLabel = new Label();
        addressLabel.setText("IP Address");
        addressLabel.setLayoutX(14);
        addressLabel.setLayoutY(14);
        addressLabel.setPrefWidth(63);
        addressLabel.setPrefHeight(18);

        // Create IpAddress text field
        this.ipAddressField = new TextField();
        this.ipAddressField.setLayoutX(77);
        this.ipAddressField.setLayoutY(11);
        this.ipAddressField.setPrefWidth(160);
        this.ipAddressField.setPrefHeight(9);

        // Create port label
        Label portLabel = new Label();
        portLabel.setText("Port");
        portLabel.setLayoutX(255);
        portLabel.setLayoutY(14);
        portLabel.setPrefWidth(38);
        portLabel.setPrefHeight(18);

        // Create port text field
        this.portField = new TextField();
        this.portField.setLayoutX(286);
        this.portField.setLayoutY(11);
        this.portField.setPrefWidth(160);
        this.portField.setPrefHeight(9);

        // Create application log text area
        this.appLogArea = new TextArea();
        this.appLogArea.setLayoutX(14);
        this.appLogArea.setLayoutY(73);
        this.appLogArea.setPrefWidth(610);
        this.appLogArea.setPrefHeight(313);
        this.appLogArea.setWrapText(true);

        // Create VBox
        VBox vbox = new VBox();
        vbox.setPrefSize(640, 400);

        // Create anchor pane
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(addressLabel);
        pane.getChildren().add(this.ipAddressField);
        pane.getChildren().add(portLabel);
        pane.getChildren().add(this.portField);
        pane.getChildren().add(this.appLogArea);

        // Create scene
        vbox.getChildren().add(pane);
        Scene scene = new Scene(vbox);

        // Add the Scene to the Stage
        stage.setScene(scene);

        // Set the Title of the Stage
        stage.setTitle("Whiteboard Server");

        // Do initialization
        this.initialize();

        // Disable maximum button
        stage.setResizable(false);

        // Display the Stage
        stage.show();
    }

    /**
     * Initialize.
     */
    private void initialize() {
        ChangeNotifier.getInstance().registerSceneCallback(this);

        this.showIpAddress();
        this.showSocketPort(WhiteboardServer.socketPort);
    }

    /**
     * Show ip address.
     */
    private void showIpAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            ipAddressField.setText(ip.getHostAddress());
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Show socket port.
     *
     * @param port the port
     */
    private void showSocketPort(int port) {
        this.portField.setText(Integer.toString(port));
    }

}
