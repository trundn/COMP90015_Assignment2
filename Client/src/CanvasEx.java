import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
            this.gc.drawImage(img, 0, 0);
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
            System.out.println("Error!");
        }
    }

    /**
     * Clear graphics context.
     */
    public void clearGraphicsContext() {
        this.gc.clearRect(0, 0, this.getWidth(), this.getHeight());
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
                this.gc.fillText(this.text, evt.getX(), evt.getY());
                this.gc.strokeText(this.text, evt.getX(), evt.getY());
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
            switch (this.drawToolType) {
            case LINE:
                this.line.setEndX(evt.getX());
                this.line.setEndY(evt.getY());
                this.gc.strokeLine(this.line.getStartX(), this.line.getStartY(),
                        this.line.getEndX(), this.line.getEndY());
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

                this.gc.strokeOval(this.circle.getCenterX(),
                        this.circle.getCenterY(), this.circle.getRadius(),
                        this.circle.getRadius());
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

                this.gc.strokeRect(this.rectangle.getX(), this.rectangle.getY(),
                        this.rectangle.getWidth(), this.rectangle.getHeight());
                break;
            default:
                // Do nothing here
            }
        });
    }

}
