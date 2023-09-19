//import javafx.application.Application;
//import javafx.geometry.Point2D;
//import javafx.geometry.Pos;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.scene.shape.Line;
//import javafx.stage.Stage;
//
//import java.util.ArrayList;
//
//public class Main extends Application {
//    Stage stage;
//    Scene scene;
//    BorderPane root;
//    Circle selected;
//    TextField selectedX;
//    TextField selectedY;
//    Button submit;
//    int width = 400;
//    int height = 400;
//    ArrayList<LinkedLine> linkedLines = new ArrayList<>();
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        stage = primaryStage;
//        primaryStage.setTitle("Splines");
//        root = new BorderPane();
//        scene = new Scene( root, width, height);
//        createBottomBar();
//
//        Circle circle = createCircle(20,50,10,Color.AQUA);
//        Circle circle2 = createCircle(new Point2D(20,100),10,Color.GOLD);
//        Line line = new LinkedLine(circle,circle2);
////        Point2D point2D2 = new Point2D(100,300);
//        root.getChildren().addAll(circle,circle2,line);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//    private Line createLine(Circle point1, Circle point2){
//        Line line = new Line(point1.getCenterX(),point1.getCenterY(),point2.getCenterX(),point2.getCenterY());
//        return line;
//    }
//
//    private Line createLine(double x1, double y1, double x2, double y2){
//        return new Line(x1,y1,x2,y2);
//    }
//
//    private void createBottomBar() {
//        selectedX = new TextField();
//        selectedX.setPrefColumnCount(15);
//        selectedX.setPromptText("x");
//        selectedX.setVisible(false);
//        selectedX.setMaxWidth(50);
//
//        selectedY = new TextField();
//        selectedY.setPrefColumnCount(15);
//        selectedY.setPromptText("y");
//        selectedY.setMaxWidth(50);
//        selectedY.setVisible(false);
//
//        submit = new Button("Set new Cords");
//        submit.setVisible(false);
//        submit.setOnMouseClicked(event -> {
//            try {
//                selected.setCenterX(Double.parseDouble(selectedX.getText()));
//                selected.setCenterY(Double.parseDouble(selectedY.getText()));
//            }
//            catch (Exception ignored){
//            }
//            relinkLines();
//        });
//        HBox hBox = new HBox(10);
//        hBox.setAlignment(Pos.CENTER);
//        hBox.getChildren().addAll(selectedX,selectedY,submit);
//        root.setBottom(hBox);
//    }
//
//    private Circle circleUIControls(double radius, Color color, Circle circle) {
//        circle.setFill(color);
////        circle.setOnMouseDragged( event -> {
////            circle.setCenterX(event.getX());
////            circle.setCenterY(event.getY());
////        });
//        circle.setOnMouseDragged(event -> {
//            if (event.getX() < radius) {
//                circle.setCenterX(radius);
//            } else {
//                circle.setCenterX(Math.min(event.getX(), scene.getWidth() - radius));
//            }
//            if (event.getY() < radius) {
//                circle.setCenterY(radius);
//            } else {
//                circle.setCenterY(Math.min(event.getY(), scene.getHeight() - radius));
//            }
//            relinkLines();
//        });
//        circle.setOnMouseClicked(event -> {
//            if(circle != selected) {
//                circle.setStroke(Color.SILVER);
//                if(selected != null) selected.setStroke(Color.TRANSPARENT);
//                selected = circle;
//                selectedX.setVisible(true);
//                selectedY.setVisible(true);
//                submit.setVisible(true);
//            }
//            else {
//                selected.setStroke(Color.TRANSPARENT);
//                selected = null;
//                selectedX.setVisible(false);
//                selectedY.setVisible(false);
//                submit.setVisible(false);
//            }
//        });
//        return circle;
//    }
//    private Circle createCircle(Point2D center, double radius, Color color) {
//        Circle circle = new Circle(radius);
//        circle.setCenterX(center.getX());
//        circle.setCenterY(center.getY());
//        return circleUIControls(radius, color, circle);
//    }
//
//    private Circle createCircle(double x,double y, double radius, Color color) {
//        Circle circle = new Circle(radius);
//        circle.setCenterX(x);
//        circle.setCenterY(y);
//        return circleUIControls(radius, color, circle);
//    }
//
//    private void relinkLines(){
//        for (LinkedLine line:linkedLines) {
//            line.relink();
//        }
//    }
//}