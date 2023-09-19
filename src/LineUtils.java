import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public class LineUtils {
    public static Point2D lerp(Line line, double t) {
        // calculate the x and y values for the lerp
        double x = (1 - t) * line.getStartX() + t * line.getEndX();
        double y = (1 - t) * line.getStartY() + t * line.getEndY();

        // return the lerp as a Point2D object
        return new Point2D(x, y);
    }
}