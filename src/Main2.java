//import javafx.application.Application;
//import javafx.geometry.Point2D;
//import javafx.geometry.Pos;
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
//public class Main2 extends Application {
//    Stage stage;
//    Scene scene;
//    BorderPane root;
//    Point selected;
//    TextField selectedX;
//    TextField selectedY;
//    Button submit;
//    int width = 400;
//    int height = 400;
//    ArrayList<LinkedLine> linkedLines = new ArrayList<>();
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        stage = primaryStage;
//        primaryStage.setTitle("Splines");
//        root = new BorderPane();
//        scene = new Scene( root, width, height);
//        createBottomBar();
//
//        Point point1 = new Point(20,50,10, Color.AQUA, scene, root, selected, selectedX, selectedY, submit);
//        Point point2 = new Point(new Point2D(20,100),10, Color.GOLD, scene, root, selected, selectedX, selectedY, submit);
//        Point point3 = new Point(new Point2D(100,100),10, Color.GOLD, scene, root, selected, selectedX, selectedY, submit);
//        Line line = new LinkedLine(point1,point2);
////        root.getChildren().addAll(point1,point2,point3,line);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
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
////            relinkLines();
//        });
//        HBox hBox = new HBox(10);
//        hBox.setAlignment(Pos.CENTER);
//        hBox.getChildren().addAll(selectedX,selectedY,submit);
//        root.setBottom(hBox);
//    }
//}
