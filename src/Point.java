import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Point extends Circle {
    Scene scene;
    BorderPane root;
//    Point selected;
    TextField selectedX;
    TextField selectedY;
    Button submit;
//    BezierCurves bezierCurves = new BezierCurves();
    public Point(Point2D center, double radius, Color color, Scene scene, BorderPane root,
                 Point selected, TextField selectedX, TextField selectedY, Button submit) {
        super(radius);
        this.setCenterX(center.getX());
        this.setCenterY(center.getY());
//        this.centerXProperty().bind(center.centerXProperty());
//        this.centerYProperty().bind(center.centerYProperty());
        this.scene = scene;
        this.root = root;
//        this.selected = selected;
        this.selectedX = selectedX;
        this.selectedY = selectedY;
        this.submit = submit;
        circleUIControls(radius, color);
    }
    public Point(Circle center, double radius, Color color, Scene scene, BorderPane root,
                 Point selected, TextField selectedX, TextField selectedY, Button submit) {
        super(radius);
        this.setCenterX(center.getCenterX());
        this.setCenterY(center.getCenterY());
        this.centerXProperty().bind(center.centerXProperty());
        this.centerYProperty().bind(center.centerYProperty());
        this.scene = scene;
        this.root = root;
//        this.selected = selected;
        this.selectedX = selectedX;
        this.selectedY = selectedY;
        this.submit = submit;
        circleUIControls(radius, color);
    }


    public Point(double x,double y, double radius, Color color, Scene scene, BorderPane root,
                 Point selected, TextField selectedX, TextField selectedY, Button submit) {
        super(radius);
        super.setCenterX(x);
        super.setCenterY(y);
//        this.centerXProperty().bind(x);
//        this.centerYProperty().bind(center.centerYProperty());
        this.scene = scene;
        this.root = root;
//        this.selected = selected;
        this.selectedX = selectedX;
        this.selectedY = selectedY;
        this.submit = submit;
        circleUIControls(radius, color);
    }

    public Point(double x, double y) {
        super.setCenterX(x);
        super.setCenterY(y);
    }

    private void circleUIControls(double radius, Color color) {
//        root.getChildren().add(this);
        this.setFill(color);
//        circle.setOnMouseDragged( event -> {
//            circle.setCenterX(event.getX());
//            circle.setCenterY(event.getY());
//        });
        this.setOnMouseDragged(event -> {
            if (event.getX() < radius) {
                this.setCenterX(radius);
            } else {
                this.setCenterX(Math.min(event.getX(), scene.getWidth() - radius));
            }
            if (event.getY() < radius) {
                this.setCenterY(radius);
            } else {
                this.setCenterY(Math.min(event.getY(), scene.getHeight() - radius));
            }
        });
        this.setOnMouseClicked(event -> {
            if(!this.equals(BezierCurves.getSelected())) {
                this.setStroke(Color.SILVER);
//                if(selected != null) {
//                    selected.setStroke(Color.TRANSPARENT);
//                }
                selectedX.setVisible(true);
                selectedX.setPromptText(""+getCenterX());
                selectedY.setVisible(true);
                selectedY.setPromptText(""+getCenterY());
                centerXProperty().addListener((observable, oldValue, newValue) -> {
                    selectedX.setPromptText("" + newValue);
                    selectedY.setVisible(true);
                });
                centerYProperty().addListener((observable, oldValue, newValue) -> {
                    selectedY.setPromptText("" + newValue);
                    selectedY.setVisible(true);
                });
                submit.setVisible(true);
                BezierCurves.setSelected(this);
                BezierCurves.removePoint.setVisible(true);
//                System.out.println("Selected is: "+bezierCurves.getSelected());
            }
            else {
                this.setStroke(Color.TRANSPARENT);
                BezierCurves.setSelected(null);
                selectedX.setVisible(false);
                selectedY.setVisible(false);
                submit.setVisible(false);
                BezierCurves.removePoint.setVisible(false);
            }
//            System.out.println("Selected is: "+bezierCurves.getSelected());
        });
    }
}
