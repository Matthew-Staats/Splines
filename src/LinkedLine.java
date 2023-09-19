import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.function.Function;
//
//public class LinkedLine extends Line {
//    private Circle point1, point2;
//    public LinkedLine(Circle point1, Circle point2){
//        this.startXProperty().bind(point1.centerXProperty());
//        this.startYProperty().bind(point1.centerYProperty());
//        this.endXProperty().bind(point2.centerXProperty());
//        this.endYProperty().bind(point2.centerYProperty());
//        this.point1 = point1;
//        this.point2 = point2;
//    }
//    public Function<Double, Point2D> getLerpFunction() {
//        return t -> {
//            // calculate the x and y values for the lerp
//            double x = (1 - t) * getStartX() + t * getEndX();
//            double y = (1 - t) * getStartY() + t * getEndY();
//
//            // return the lerp as a Point2D object
//            return new Point2D(x, y);
//        };
//    }
//
//    public Circle getPoint1() {
//        return point1;
//    }
//
//    public void setPoint1(Circle point1) {
//        this.point1 = point1;
//    }
//
//    public Circle getPoint2() {
//        return point2;
//    }
//
//    public void setPoint2(Circle point2) {
//        this.point2 = point2;
//    }
//
//}
public class LinkedLine extends Line {
    private Circle circle1;
    private Circle circle2;
    private Function<Double, Point> lerpFunction;

    @Override
    public String toString() {
        return "Point 1: "+circle1.getCenterX()+","+circle1.getCenterY()+" Point 2: "+circle2.getCenterX()+","+circle2.getCenterY();
    }

    public LinkedLine(Circle circle1, Circle circle2) {
        super(circle1.getCenterX(), circle1.getCenterY(), circle2.getCenterX(), circle2.getCenterY());
        this.circle1 = circle1;
        this.circle2 = circle2;
//        updateLerpFunction();
        lerpFunction = (t) -> {
            double x = (1 - t) * circle1.getCenterX() + t * circle2.getCenterX();
            double y = (1 - t) * circle1.getCenterY() + t * circle2.getCenterY();
            return new Point(x, y);
        };
        circle1.centerXProperty().addListener((observable, oldValue, newValue) -> updateLerpFunction());
        circle1.centerYProperty().addListener((observable, oldValue, newValue) -> updateLerpFunction());
        circle2.centerXProperty().addListener((observable, oldValue, newValue) -> updateLerpFunction());
        circle2.centerYProperty().addListener((observable, oldValue, newValue) -> updateLerpFunction());
    }

    private void updateLerpFunction() {
        lerpFunction = (t) -> {
            double x = (1 - t) * circle1.getCenterX() + t * circle2.getCenterX();
            double y = (1 - t) * circle1.getCenterY() + t * circle2.getCenterY();
            return new Point(x, y);
        };
        System.out.println("A lerp function is being updated because one of it's circles was moved");
        BezierCurves.drawBezierCurve(BezierCurves.curvePoints,BezierCurves.initialValue,false,false);
    }

    public Function<Double, Point> getLerpFunction() {
        return lerpFunction;
    }
}
