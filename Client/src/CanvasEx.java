import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * The Class CanvasEx.
 */
public class CanvasEx extends Canvas {

    /** The text. */
    String text;

    /** The graphics context. */
    GraphicsContext gc;

    /** The line. */
    Line line = new Line();

    /** The circle. */
    Circle circle = new Circle();

    /** The rectangle. */
    Rectangle rectangle = new Rectangle();

    /** The draw tool type. */
    private DrawToolType drawToolType = DrawToolType.NONE;

    /** The lock. */
    private Object lock = new Object();

    /**
     * Instantiates a new canvas extension.
     */
    public CanvasEx(double width, double height) {
        super(width, height);

        // Get the graphics context
        this.gc = this.getGraphicsContext2D();
        // Set line width
        this.gc.setLineWidth(Constants.GRAPHIC_LINE_WIDTH);
        // Set default font size
        this.gc.setFont(Font.font(Constants.DEFAULT_FONT_SIZE));

        // Register mouse events
        this.registerMousePressedEvent();
        this.registerMouseReleasedEvent();
    }

    /**
     * Gets the draw tool type.
     *
     * @return the draw tool type
     */
    public DrawToolType getDrawToolType() {
        return drawToolType;
    }

    /**
     * Sets the draw tool type.
     *
     * @param drawToolType the new draw tool type
     */
    public void setDrawToolType(DrawToolType drawToolType) {
        this.drawToolType = drawToolType;
    }

    /**
     * Notify text changed.
     *
     * @param newValue the new value
     */
    public void notifyTextChanged(String newValue) {
        this.text = newValue;
    }

    /**
     * Draw image.
     *
     * @param img the image
     */
    public void drawImage(Image img) {
        if (img != null) {
            synchronized (this.lock) {
                this.gc.drawImage(img, 0, 0);
            }
        }
    }

    /**
     * Draw image.
     *
     * @param imageAsString the image as string
     */
    public void drawImage(String imageAsString) {
        Decoder decoder = Base64.getDecoder();
        try {
            byte[] byteArray = decoder.decode(imageAsString);
            for (int i = 0; i < byteArray.length; ++i) {
                if (byteArray[i] < 0) {
                    byteArray[i] += 256;
                }
            }

            OutputStream outputStream = new FileOutputStream(
                    "synchronization.png");
            outputStream.write(byteArray);
            outputStream.flush();
            outputStream.close();

            InputStream inputStream = new ByteArrayInputStream(byteArray);
            BufferedImage capture = ImageIO.read(inputStream);

            Image image = SwingFXUtils.toFXImage(capture, null);
            this.drawImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save image.
     *
     * @param file the file
     */
    public void saveImage(File file) {
        try {
            WritableImage writableImage = new WritableImage(
                    Constants.CANVAS_WIDTH_INT, Constants.CANVAS_HEIGHT_INT);

            this.snapshot(null, writableImage);

            RenderedImage renderedImage = SwingFXUtils
                    .fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, Constants.PNG_FILE_EXTENSION_LOWER,
                    file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the image as string.
     *
     * @return the image as string
     */
    public String getImageAsString() {
        String result = "";

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            WritableImage writableImage = new WritableImage(
                    Constants.CANVAS_WIDTH_INT, Constants.CANVAS_HEIGHT_INT);

            this.snapshot(null, writableImage);

            RenderedImage renderedImage = SwingFXUtils
                    .fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, Constants.PNG_FILE_EXTENSION_LOWER,
                    outputStream);

            // Convert byte array to String
            Encoder encoder = Base64.getEncoder();
            result = encoder.encodeToString(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    /**
     * Clear graphics context.
     */
    public void clearGraphicsContext() {
        synchronized (this.lock) {
            this.gc.clearRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    /**
     * Draw text.
     *
     * @param startX       the start X
     * @param startY       the start Y
     * @param expectedText the expected text
     */
    public void drawText(double startX, double startY, String expectedText) {
        synchronized (this.lock) {
            this.gc.fillText(expectedText, startX, startY);
            this.gc.strokeText(expectedText, startX, startY);
        }
    }

    /**
     * Draw line.
     *
     * @param startX the start X
     * @param startY the start Y
     * @param endX   the end X
     * @param endY   the end Y
     */
    public void drawLine(double startX, double startY, double endX,
            double endY) {
        synchronized (this.lock) {
            this.gc.strokeLine(startX, startY, endX, endY);
        }
    }

    /**
     * Draw circle.
     *
     * @param centerX the center X
     * @param centerY the center Y
     * @param radius  the radius
     */
    public void drawCircle(double centerX, double centerY, double radius) {
        synchronized (this.lock) {
            this.gc.strokeOval(centerX, centerY, radius, radius);
        }
    }

    /**
     * Draw rectangle.
     *
     * @param startX the start X
     * @param startY the start Y
     * @param width  the width
     * @param height the height
     */
    public void drawRectangle(double startX, double startY, double width,
            double height) {
        synchronized (this.lock) {
            this.gc.strokeRect(startX, startY, width, height);
        }
    }

    /**
     * Register mouse pressed event.
     */
    private void registerMousePressedEvent() {
        this.setOnMousePressed(evt -> {
            switch (this.drawToolType) {
            case LINE:
                this.line.setStartX(evt.getX());
                this.line.setStartY(evt.getY());
                break;
            case CIRCLE:
                this.circle.setCenterX(evt.getX());
                this.circle.setCenterY(evt.getY());
                break;
            case RECTANGLE:
                this.rectangle.setX(evt.getX());
                this.rectangle.setY(evt.getY());
                break;
            case TEXT:
                synchronized (this.lock) {
                    this.gc.fillText(this.text, evt.getX(), evt.getY());
                    this.gc.strokeText(this.text, evt.getX(), evt.getY());
                }

                // Broadcast canvas changes to all peers
                JSONObject request = EventMessageBuilder.buildTextSynMessage(
                        UserInformation.getInstance().getUserName(), evt.getX(),
                        evt.getY(), this.text);
                SocketHandler.getInstance().send(request);
                break;
            default:
                // Do nothing here
            }
        });
    }

    /**
     * Register mouse released event.
     */
    private void registerMouseReleasedEvent() {
        this.setOnMouseReleased(evt -> {
            JSONObject request = null;

            switch (this.drawToolType) {
            case LINE:
                this.line.setEndX(evt.getX());
                this.line.setEndY(evt.getY());

                this.drawLine(this.line.getStartX(), this.line.getStartY(),
                        this.line.getEndX(), this.line.getEndY());

                // Broadcast canvas changes to all peers
                request = EventMessageBuilder.buildLineSynMessage(
                        UserInformation.getInstance().getUserName(),
                        this.line.getStartX(), this.line.getStartY(),
                        this.line.getEndX(), this.line.getEndY());
                SocketHandler.getInstance().send(request);
                break;
            case CIRCLE:
                this.circle.setRadius(
                        (Math.abs(evt.getX() - this.circle.getCenterX()) + Math
                                .abs(evt.getY() - this.circle.getCenterY()))
                                / 2);

                if (this.circle.getCenterX() > evt.getX()) {
                    this.circle.setCenterX(evt.getX());
                }
                if (this.circle.getCenterY() > evt.getY()) {
                    this.circle.setCenterY(evt.getY());
                }

                this.drawCircle(this.circle.getCenterX(),
                        this.circle.getCenterY(), this.circle.getRadius());

                // Broadcast canvas changes to all peers
                request = EventMessageBuilder.buildCircleSynMessage(
                        UserInformation.getInstance().getUserName(),
                        this.circle.getCenterX(), this.circle.getCenterY(),
                        this.circle.getRadius());
                SocketHandler.getInstance().send(request);
                break;
            case RECTANGLE:
                this.rectangle.setWidth(
                        Math.abs((evt.getX() - this.rectangle.getX())));
                this.rectangle.setHeight(
                        Math.abs((evt.getY() - this.rectangle.getY())));

                if (this.rectangle.getX() > evt.getX()) {
                    this.rectangle.setX(evt.getX());
                }

                if (this.rectangle.getY() > evt.getY()) {
                    this.rectangle.setY(evt.getY());
                }

                this.drawRectangle(this.rectangle.getX(), this.rectangle.getY(),
                        this.rectangle.getWidth(), this.rectangle.getHeight());

                // Broadcast canvas changes to all peers
                request = EventMessageBuilder.buildRectangleSynMessage(
                        UserInformation.getInstance().getUserName(),
                        this.rectangle.getX(), this.rectangle.getY(),
                        this.rectangle.getWidth(), this.rectangle.getHeight());
                SocketHandler.getInstance().send(request);
                break;
            default:
                // Do nothing here
            }
        });
    }

}
