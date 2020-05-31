/**
 * The Class Constants.
 */
public class Constants {

    /** The Constant DEFAULT_FONT_SIZE. */
    public static final double DEFAULT_FONT_SIZE = 18;

    /** The Constant MIN_PORT_NUMBER. */
    public static final int MIN_PORT_NUMBER = 1024;

    /** The Constant MAX_PORT_NUMBER. */
    public static final int MAX_PORT_NUMBER = 49151;

    /** The Constant TOOGLE_BUTTON_MIN_WIDTH. */
    public static final double TOOGLE_BUTTON_MIN_WIDTH = 90;

    /** The canvas width. */
    public static final int CANVAS_WIDTH_INT = 680;

    /** The canvas height. */
    public static final int CANVAS_HEIGHT_INT = 590;

    /** The canvas width. */
    public static final double CANVAS_WIDTH = 680;

    /** The canvas height. */
    public static final double CANVAS_HEIGHT = 590;

    /** The Constant SCENE_WIDTH. */
    public static final double SCENE_WIDTH = 780;

    /** The Constant SCENE_HEIGHT. */
    public static final double SCENE_HEIGHT = 600;

    /** The Constant VBOX_SPACING. */
    public static final double VBOX_SPACING = 10;

    /** The Constant VBOX_PREF_WIDTH. */
    public static final double VBOX_PREF_WIDTH = 100;

    /** The Constant VBOX_PADDING. */
    public static final double VBOX_PADDING = 5;

    /** The Constant GRAPHIC_LINE_WIDTH. */
    public static final double GRAPHIC_LINE_WIDTH = 1;

    /** The Constant USER_NAME_REGULAR_EXPRESSION. */
    public static final String USER_NAME_REGULAR_EXPRESSION = "[A-Za-z0-9_]+";

    /** The new line. */
    public static final String NEW_LINE = System.getProperty("line.separator");

    /** The charset name. */
    public static final String CHARSET_NAME = "UTF-8";

    /** The Constant LINE_BUTTON_TEXT. */
    public static final String LINE_BUTTON_TEXT = "Line";

    /** The Constant CIRCLE_BUTTON_TEXT. */
    public static final String CIRCLE_BUTTON_TEXT = "Circle";

    /** The Constant RECTANGLE_BUTTON_TEXT. */
    public static final String RECTANGLE_BUTTON_TEXT = "Rectange";

    /** The Constant TEXT_BUTTON_TEXT. */
    public static final String TEXT_BUTTON_TEXT = "Text";

    /** The Constant DRAW_TOOLS_BACKGROUND_COLOR. */
    public static final String DRAW_TOOLS_BACKGROUND_COLOR = "-fx-background-color: #dddddd";

    /** The Constant FILE_MENU. */
    public static final String FILE_MENU = "_File";

    /** The Constant NEW_MENU_ITEM. */
    public static final String NEW_MENU_ITEM = "New";

    /** The Constant OPEN_MENU_ITEM. */
    public static final String OPEN_MENU_ITEM = "Open";

    /** The Constant SAVE_MENU_ITEM. */
    public static final String SAVE_MENU_ITEM = "Save";

    /** The Constant SAVE_AS_MENU_ITEM. */
    public static final String SAVE_AS_MENU_ITEM = "Save As";

    /** The Constant CLOSE_MENU_ITEM. */
    public static final String CLOSE_MENU_ITEM = "Close";

    /** The Constant OPEN_FILE_TILE. */
    public static final String OPEN_FILE_TILE = "Open File";

    /** The Constant SAVE_FILE_TITLE. */
    public static final String SAVE_FILE_TITLE = "Save File";

    /** The Constant SAVE_AS_FILE_TITLE. */
    public static final String SAVE_AS_FILE_TITLE = "Save As File";

    /** The Constant PNG_FILE_EXTENSION_UPPER. */
    public static final String PNG_FILE_EXTENSION_UPPER = "PNG";

    /** The Constant PNG_FILE_EXTENSION_LOWER. */
    public static final String PNG_FILE_EXTENSION_LOWER = "png";

    /** The Constant PNG_FILE_FILTER. */
    public static final String PNG_FILE_FILTER = "*.png";

    /** The Constant APPROVE_BUTTON_TYPE_NAME. */
    public static final String APPROVE_BUTTON_TYPE_NAME = "Approve";

    /** The Constant CANCEL_BUTTON_TYPE_NAME. */
    public static final String CANCEL_BUTTON_TYPE_NAME = "Cancel";

    /** The Constant CLOSE_BUTTON_TYPE_NAME. */
    public static final String CLOSE_BUTTON_TYPE_NAME = "Close";

    /** The Constant ACK_OK. */
    public static final String ACK_OK = "OK";

    /** The Constant ACK_MANAGER_EXISTED. */
    public static final String ACK_MANAGER_EXISTED = "MANAGER_EXISTED";

    /** The Constant ACK_USER_NAME_EXISTED. */
    public static final String ACK_USER_NAME_EXISTED = "USER_NAME_EXISTED";

    /** The Constant ACK_APPROVED. */
    public static final String ACK_APPROVED = "APPROVED";

    /** The Constant ACK_CANCELED. */
    public static final String ACK_CANCELED = "CANCELED";

    /** The Constant PING_EVT_NAME. */
    public static final String PING_EVT_NAME = "ping";

    /** The Constant HANDSHAKE_ESTABLISHMENT_EVT_NAME. */
    public static final String HANDSHAKE_ESTABLISHMENT_EVT_NAME = "handshake_establishment";

    /** The Constant HANDSHAKE_ACKNOWLEDGMENT_EVT_NAME. */
    public static final String HANDSHAKE_ACKNOWLEDGMENT_EVT_NAME = "handshake_ack";

    /** The Constant REQUEST_WHITEBOARD_JOIN_APPROVAL_EVT_NAME. */
    public static final String REQUEST_WHITEBOARD_JOIN_APPROVAL_EVT_NAME = "request_whiteboard_join_approval";

    /** The Constant REQUEST_WHITEBOARD_JOIN_APPROVAL_ACK_EVT_NAME. */
    public static final String REQUEST_WHITEBOARD_JOIN_APPROVAL_ACK_EVT_NAME = "request_whiteboard_join_approval_ack";

    /** The Constant CLIENT_SHUTDOWN_EVT_NAME. */
    public static final String CLIENT_SHUTDOWN_EVT_NAME = "client_shutdown_notification";

    /** The Constant WHITEBOARD_OWNER_SHUTDOWN_EVT_NAME. */
    public static final String WHITEBOARD_OWNER_SHUTDOWN_EVT_NAME = "whiteboard_owner_shutdown_notification";

    /** The Constant WHITE_BOARD_SYS_EVT_NAME. */
    public static final String WHITE_BOARD_SYS_EVT_NAME = "whiteboard_synchronization";

    /** The Constant WHITE_BOARD_SYS_ACKNOWLEDGMENT. */
    public static final String WHITE_BOARD_SYS_ACKNOWLEDGMENT = "whiteboard_synchronization_ack";

    /** The Constant LINE_SYN_EVT_NAME. */
    public static final String LINE_SYN_EVT_NAME = "line_synchronization";

    /** The Constant CIRCLE_SYN_EVT_NAME. */
    public static final String CIRCLE_SYN_EVT_NAME = "circle_synchronization";

    /** The Constant RECTANGLE_SYN_EVT_NAME. */
    public static final String RECTANGLE_SYN_EVT_NAME = "rectangle_synchronization";

    /** The Constant TEXT_SYN_EVT_NAME. */
    public static final String TEXT_SYN_EVT_NAME = "text_synchronization";

    /** The Constant MESSAGE_HEADER. */
    public static final String MESSAGE_HEADER = "header";

    /** The Constant MESSAGE_BODY. */
    public static final String MESSAGE_BODY = "body";

    /** The Constant MESSAGE_EVENT_NAME. */
    public static final String MESSAGE_EVENT_NAME = "event_name";

    /** The Constant MESSAE_EVENT_CONTENT. */
    public static final String MESSAE_EVENT_CONTENT = "event_content";

    /** The Constant USER_NAME_ATTR. */
    public static final String USER_NAME_ATTR = "user_name";

    /** The Constant HOST_ADDRESS_ATTR. */
    public static final String HOST_ADDRESS_ATTR = "host_address";

    /** The Constant MANAGER_ROLE_ATTR. */
    public static final String MANAGER_ROLE_ATTR = "has_manager_role";

    /** The Constant ACK_ATTR. */
    public static final String ACK_ATTR = "ack";

    /** The Constant START_X_ATTR. */
    public static final String START_X_ATTR = "start_x";

    /** The Constant START_Y_ATTR. */
    public static final String START_Y_ATTR = "start_y";

    /** The Constant END_X_ATTR. */
    public static final String END_X_ATTR = "end_x";

    /** The Constant END_Y_ATTR. */
    public static final String END_Y_ATTR = "end_y";

    /** The Constant CENTER_X_ATTR. */
    public static final String CENTER_X_ATTR = "center_x";

    /** The Constant CENTER_Y_ATTR. */
    public static final String CENTER_Y_ATTR = "center_y";

    /** The Constant RADIUS_ATTR. */
    public static final String RADIUS_ATTR = "radius";

    /** The Constant WIDTH_ATTR. */
    public static final String WIDTH_ATTR = "width";

    /** The Constant HEIGHT_ATTR. */
    public static final String HEIGHT_ATTR = "height";

    /** The Constant TEXT_ATTR. */
    public static final String TEXT_ATTR = "text";

    /** The Constant IMAGE_AS_STRING_ATTR. */
    public static final String IMAGE_AS_STRING_ATTR = "image_as_string";
}
