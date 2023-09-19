//import javafx.geometry.Point2D;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//
//import java.util.function.Function;
//
//public class LerpPoint extends Point {
//    // other fields and methods
//    Scene scene;
//    BorderPane root;
//    Point selected;
//    TextField selectedX;
//    TextField selectedY;
//    Button submit;
//
//    public LerpPoint(double centerX, double centerY, double radius, Color color, Scene scene, Pane root, Point selected, TextField selectedX, TextField selectedY, Button submit) {
//        super.super(centerX, centerY, radius, color);
//
//        // other code
//
//        // bind the lerp functions to the x and y positions of the points
////        Function<Double, Double> lerpX = t -> (1 - t) * this.centerXProperty().get() + t * next.centerXProperty().get();
////        Function<Double, Double> lerpY = t -> (1 - t) * this.centerYProperty().get() + t * next.centerYProperty().get();
////        Function<Double, Point2D> lerp = t -> new Point2D(lerpX.apply(t), lerpY.apply(t));
////
////        // add the lerp function to the list of lerp functions
////        lerps.add(lerp);
//    }
//    private void circleUIControls(double radius, Color color) {
////        root.getChildren().add(this);
//        super.setFill(color);
////        circle.setOnMouseDragged( event -> {
////            circle.setCenterX(event.getX());
////            circle.setCenterY(event.getY());
////        });
//        super.setOnMouseDragged(event -> {
//            if (event.getX() < radius) {
//                super.setCenterX(radius);
//            } else {
//                super.setCenterX(Math.min(event.getX(), scene.getWidth() - radius));
//            }
//            if (event.getY() < radius) {
//                super.setCenterY(radius);
//            } else {
//                super.setCenterY(Math.min(event.getY(), scene.getHeight() - radius));
//            }
//        });
//        super.setOnMouseClicked(event -> {
//            if(!this.equals(selected)) {
//                super.setStroke(Color.SILVER);
//                if(selected != null) {
//                    selected.setStroke(Color.TRANSPARENT);
//                }
//                selectedX.setVisible(true);
//                selectedY.setVisible(true);
//                submit.setVisible(true);
//                selected = this;
//
//            }
//            else {
//                selected.setStroke(Color.TRANSPARENT);
//                selected = null;
//                selectedX.setVisible(false);
//                selectedY.setVisible(false);
//                submit.setVisible(false);
//            }
//        });
//    }
//}
