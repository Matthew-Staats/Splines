import javafx.application.Application;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class BezierCurves extends Application {
    static Stage stage;
    static Scene scene;
    static BorderPane root;
    static int width = 500;
    static int height = 400;
    static Point selected;

    public static Point getSelected() {return selected;}

    public static void setSelected(Point Selected) {selected = Selected;}

    static TextField selectedX;
    static TextField selectedY;
    static Button submit;
    Button addPoint;
    static Button removePoint;
    static Button clear;
    static Button fixSlider;
    static Slider slider;
    static int radius = 10;
    static int radius2 = 7;
    static ArrayList<Key<Circle>> goldenCircles = new ArrayList<>();
    static ArrayList<Function<Double, Point>> lerps = new ArrayList<>();
    static ArrayList<LinkedLine> lerpLines = new ArrayList<>();
    static ArrayList<Point> curvePoints;
    static double initialValue = 0.75;

    static ArrayList<Function<Double, Point>> initialLerps = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {

        setUp(primaryStage);

        drawBezierCurve(curvePoints, initialValue,true,true);
//        for (LinkedLine line:lerpLines) {
//            System.out.println("Lerp lines:");
//            System.out.println("Line: "+line);
//            root.getChildren().add(line);
//        }


        // create the group and add the circles and curve to it

//        for (Function<Double, Point2D> lerp:lerps) {
//            Line line = new Line(lerp.apply(0.0).getX(),lerp.apply(0.0).getY(),lerp.apply(1.0).getX(),lerp.apply(1.0).getY());
//            root.getChildren().add(line);
//        }
    }

    private void setUp(Stage primaryStage) {
        root = new BorderPane();
        stage = primaryStage;
        primaryStage.setTitle("Bezier Example");
        scene = new Scene(root, width, height, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();

        createBottomBar();
        createTopBar();

        scene.setOnKeyPressed(event -> {
            if(selected==null) {
                switch (event.getCode()) {
                    case A:
                    case N:
                        addAPoint();
                        sliderBinding(slider);
                        break;
                    case C:
                        clearGoldenCircles();
                        sliderBinding(slider);
                        break;
                    case F:
                    case S:
                        // Bind the value of the slider to a property in your application
                        sliderBinding(slider);
                        break;
                }
            }
            else {
                switch(event.getCode()) {
                    case N:
                        BezierCurves.addAPoint();
                        BezierCurves.sliderBinding(BezierCurves.slider);
                        break;
                    case C:
                        BezierCurves.clearGoldenCircles();
                        BezierCurves.sliderBinding(BezierCurves.slider);
                        break;
                    case F:
                        // Bind the value of the slider to a property in your application
                        BezierCurves.sliderBinding(BezierCurves.slider);
                        break;
                    case R:
                        BezierCurves.removePoint();
                        break;
                    case UP:
                    case W:
                        selected.setCenterY(selected.getCenterY()-10);
                        break;
                    case DOWN:
                    case S:
                        selected.setCenterY(selected.getCenterY()+10);
                        break;
                    case LEFT:
                    case A:
                        selected.setCenterX(selected.getCenterX()+10);
                        break;
                    case RIGHT:
                    case D:
                        selected.setCenterX(selected.getCenterX()-10);
                        break;
                }
            }
        });

        // create the Points:
        curvePoints = new ArrayList<>(Arrays.asList(
                new Point(100, 100, radius, Color.BLUE, scene,root,selected,selectedX,selectedY,submit),
//                new Point(200, 200, radius, Color.RED, scene,root,selected,selectedX,selectedY,submit),
                new Point(300, 100, radius, Color.GREEN, scene,root,selected,selectedX,selectedY,submit)
                ,new Point(400, 200, radius, Color.PURPLE, scene,root,selected,selectedX,selectedY,submit)
        ));
        for (Point point: curvePoints) {
            root.getChildren().add(point);
        }
    }

    public static void sliderBinding(Slider slider) {
        DoubleProperty sliderValue = new SimpleDoubleProperty();
        sliderValue.bind(slider.valueProperty());

        // Add a listener to the property to update the curve when the slider value changes
        sliderValue.addListener((observable, oldValue, newValue) -> {
            System.out.println("Slider is updated");
            drawBezierCurve(curvePoints, newValue.doubleValue(), false, true);
        });
    }

    public static void addAPoint() {
        Point point = new Point(width / 2, height / 2, radius, Color.rgb((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)), scene, root, selected, selectedX, selectedY, submit);
        selected = point;
        root.getChildren().add(point);
        curvePoints.add(point);
        drawBezierCurve(curvePoints, initialValue,true,false);
    }


    public static void drawBezierCurve(List<Point> points,double t,Boolean moreOrLessPoints,Boolean sliderUpdate) {
        if(!moreOrLessPoints&&!sliderUpdate) {
            System.out.println("The Golden Circle are:");
            for (int i = 0; i < goldenCircles.size(); i++) {
                System.out.println("Circle: "+i+" "+goldenCircles.get(i));
                root.getChildren().remove(goldenCircles.get(i).getValue());
            }
        }
        ArrayList<Function<Double, Point>> nextLerps = new ArrayList<>();
        if(moreOrLessPoints) {
            while (0 < lerpLines.size()) {
                LinkedLine line = lerpLines.get(0);
                root.getChildren().remove(line);
                lerpLines.remove(line);
            }
            while (0 < goldenCircles.size()) {
                root.getChildren().remove(goldenCircles.get(0).getValue());
                goldenCircles.remove(goldenCircles.get(0));
            }
            for (int i = 0; i < points.size() - 1; i++) {
                Point point1 = points.get(i);
                Point point2 = points.get(i + 1);
                LinkedLine line = new LinkedLine(point1, point2);
                line.startXProperty().bind(points.get(i).centerXProperty());
                line.startYProperty().bind(points.get(i).centerYProperty());
                line.endXProperty().bind(points.get(i+1).centerXProperty());
                line.endYProperty().bind(points.get(i+1).centerYProperty());

                lerpLines.add(line);
                root.getChildren().add(line);

                Function<Double, Point> lerp = line.getLerpFunction();

                lerps.add(lerp);
                initialLerps.add(lerp);
                nextLerps.add(lerp);
            }
        }
        else {
            nextLerps = initialLerps;
        }

        //A comment:
        /*System.out.println("All Points:");
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            System.out.println(i+": " + point+" ");
        }
        System.out.println();*/

//        for (int i = 0; i < points.size() - 1; i++) {
//            Point point1 = points.get(i);
//            Point point2 = points.get(i +1);
//            LinkedLine line;
//            if(recalculate){
//                line = new LinkedLine(point1,point2);
//                lerpLines.add(line);
//                root.getChildren().add(line);
//            }
//            else {
//                line = lerpLines.get(i);
//                line.setStartX(point1.getCenterX());
//                line.setStartY(point1.getCenterY());
//                line.setEndX(point2.getCenterX());
//                line.setEndY(point2.getCenterY());
//            }

//            if (i < lerpLines.size()) {
//                line = lerpLines.get(i);
//                line.setStartY(circle1.getCenterY());
//                line.setEndX(circle2.getCenterX());
//                line.setEndY(circle2.getCenterY());
//            } else {

            // Bind the value of the slider to a property in your application
//            DoubleProperty sliderValue = new SimpleDoubleProperty();
//            sliderValue.bind(slider.valueProperty());
//            }
//            Function<Double, Point2D> lerp = line.getLerpFunction();
//            lerps.add(lerp);
//            nextLerps.add(lerp);
//        }
//        for (Function<Double, Point2D> lerp:nextLerps) {
//            System.out.println("Next lerps");
//            System.out.print("Lerp at 0: "+lerp.apply(0.0)+" Lerp at 1: "+lerp.apply(1.0));
//        }

        for (int i = 0; nextLerps.size()>2; i++) {
//            System.out.println("Calculating in between lerps for the "+i+"th time");
            ArrayList<Function<Double, Point>> currentLerps = nextLerps;
            nextLerps = new ArrayList<>();

            for (int j = 0; j < currentLerps.size() - 1; j++) {
                Point point1 = currentLerps.get(j).apply(t);
                Point point2 = currentLerps.get(j+1).apply(t);

                System.out.println();
                System.out.println("Lerped points looking at:");
                System.out.println(1+": " + point1+" ");
                System.out.println(2+": " + point2+" ");
                System.out.println();

                LinkedLine line;

                if (!moreOrLessPoints) {/*                    line = lerpLines.get(i + j+ (points.size()-2));
                    line.setStartX(point1.getCenterX());
                    line.setStartY(point1.getCenterY());
                    line.setEndX(point2.getCenterX());
                    line.setEndY(point2.getCenterY()); //might not be necessary*//*                    line.startXProperty().bind(point1.centerXProperty());
                    line.startYProperty().bind(point1.centerYProperty());
                    line.endXProperty().bind(point2.centerXProperty());
                    line.endYProperty().bind(point2.centerYProperty());*/
                    LinkedLine temp = lerpLines.get(i + j+ (points.size()-2)); //main thing
                    lerpLines.remove(temp);
                    root.getChildren().remove(temp);

                    line = new LinkedLine(point1,point2);
                    lerpLines.add(line);
                    root.getChildren().add(line);
                }
                else {
//                    Point circle1 = new Point(point2D1,radius, Color.BLACK, scene,root,selected,selectedX,selectedY,submit);
//                    Point circle2 = new Point(point2D2,radius, Color.BLACK, scene,root,selected,selectedX,selectedY,submit);
                    line = new LinkedLine(point1,point2);
//                    line.startXProperty().bind(point1.centerXProperty());
//                    line.startYProperty().bind(point1.centerYProperty());
//                    line.endXProperty().bind(point2.centerXProperty());
//                    line.endYProperty().bind(point2.centerYProperty());

                    lerpLines.add(line);
                    root.getChildren().add(line);
                }

                Function<Double, Point> lerp = line.getLerpFunction();
                lerps.add(lerp);
                nextLerps.add(lerp);
            }
        }
        if(nextLerps.size()==2){
//            System.out.println("Calculating the Golden Circle");
            Point point1 = nextLerps.get(0).apply(t);
            Point point2 = nextLerps.get(1).apply(t);

//            System.out.println();
//            System.out.println("Lerped points for the Golden Circle Line:");
//            System.out.println(1+": " + point1+" ");
//            System.out.println(2+": " + point2+" ");
//            System.out.println();

            LinkedLine line;

            if (!moreOrLessPoints) { //Basically if this is the 2nd time this has been called
//                line = lerpLines.get(nextLerps.size()-1);
//                line.setStartX(point1.getCenterX());
//                line.setStartY(point1.getCenterY());
//                line.setEndX(point2.getCenterX());
//                line.setEndY(point2.getCenterY());
                LinkedLine temp = lerpLines.get(nextLerps.size()-1);
                lerpLines.remove(temp);
                root.getChildren().remove(temp);

                line = new LinkedLine(point1,point2);
                lerpLines.add(line);
                root.getChildren().add(line);
            }
            else {
                line = new LinkedLine(point1,point2);
                lerpLines.add(line);
                root.getChildren().add(line);
            }
            Function<Double, Point> lerp = line.getLerpFunction();
            lerps.add(lerp);

            if(moreOrLessPoints||sliderUpdate) {
                Circle goldenCircle = new Circle(lerp.apply(t).getCenterX(), lerp.apply(t).getCenterY(), radius2, Color.GOLD);
                Key circleKey = new Key(t,goldenCircle);
                try {
                    root.getChildren().add(goldenCircle);
                    goldenCircles.add(circleKey);
                    System.out.println("New Golden Circle "+goldenCircles);
                } catch (Exception ignored) {}
            }
            else { //move every golden circle to its new spot:
                int size = goldenCircles.size();
                System.out.println("Moving every golden circle to its new spot. Should be this many circles moved: "+size);
                for (int i = 0; i < size; i++) {
                    moveGoldenCircle(curvePoints, goldenCircles.get(i).getIndex());
//                    System.out.println("Moving a golden circle to its new spot, this is time: "+i+". Currently tempGoldenCircles is this long: "+tempGoldenCircles.size()+".");
//                    System.out.print("Moved "+goldenCircles.get(i).getValue());
                }
            }
        }

        for (int i = 0; i < lerpLines.size(); i++) {
            LinkedLine line = lerpLines.get(i);
            System.out.println("Lerp lines:");
            System.out.println("Line "+i+": " + line);
            if(!root.getChildren().contains(line)) {
                root.getChildren().add(line);
                System.out.println("Was added to the root!");
            }
        }
    }
    static void moveGoldenCircle(List<Point> points,double t) {
        ArrayList<Function<Double, Point>> nextLerps = initialLerps;
        for (int i = 0; nextLerps.size() > 2; i++) {
//            System.out.println("Calculating in between lerps for the " + i + "th time");
            ArrayList<Function<Double, Point>> currentLerps = nextLerps;
            nextLerps = new ArrayList<>();

            for (int j = 0; j < currentLerps.size() - 1; j++) {
                Point point1 = currentLerps.get(j).apply(t);
                Point point2 = currentLerps.get(j + 1).apply(t);
//                System.out.println();
//                System.out.println("Lerped points looking at:");
//                System.out.println(1 + ": " + point1 + " ");
//                System.out.println(2 + ": " + point2 + " ");
//                System.out.println();
                LinkedLine temp = lerpLines.get(i + j+ (points.size()-2)); //main thing
                lerpLines.remove(temp);
                root.getChildren().remove(temp);

                LinkedLine line = new LinkedLine(point1,point2);
                lerpLines.add(line);
//                root.getChildren().add(line);

                Function<Double, Point> lerp = line.getLerpFunction();
                lerps.add(lerp);
                nextLerps.add(lerp);
            }
        }
        if (nextLerps.size() == 2) {
//            System.out.println("Calculating the Golden Circle");
            Point point1 = nextLerps.get(0).apply(t);
            Point point2 = nextLerps.get(1).apply(t);
//            System.out.println();
//            System.out.println("Lerped points for the Golden Circle Line:");
//            System.out.println(1 + ": " + point1 + " ");
//            System.out.println(2 + ": " + point2 + " ");
//            System.out.println();
            LinkedLine temp = lerpLines.get(nextLerps.size() - 1);
            lerpLines.remove(temp);
            root.getChildren().remove(temp);

            LinkedLine line = new LinkedLine(point1, point2);
            lerpLines.add(line);
//            root.getChildren().add(line);

            Function<Double, Point> lerp = line.getLerpFunction();
            lerps.add(lerp);

            Key tempGoldenCircle = goldenCircles.get(0);
            goldenCircles.remove(tempGoldenCircle);
            root.getChildren().remove(tempGoldenCircle);

            Circle goldenCircle = new Circle(lerp.apply(t).getCenterX(), lerp.apply(t).getCenterY(), radius2, Color.GOLD);
            Key circleKey = new Key(t, goldenCircle);
            try {
                root.getChildren().add(goldenCircle);
                goldenCircles.add(circleKey);
//                System.out.println("New Golden Circle " + goldenCircles);
            } catch (Exception ignored) {}
        }
    }
//    private void drawBezierCurve(List<Point> points,double t) {
//        lerps = new ArrayList<>();
//        System.out.print("current lerps storred:");
//        for (Function<Double, Point2D> lerp:lerps) {
//            System.out.print(" "+lerp.apply(t));
//        }
//        System.out.println();
//        System.out.println("drawing a point at t: "+t);
//        ArrayList<Function<Double, Point2D>> nextLerps = new ArrayList<>();
//        for (int i = 0; i < points.size() - 1; i++) {
//            Point circle1 = points.get(i);
//            Point circle2 = points.get(i +1);
//            LinkedLine line = new LinkedLine(circle1,circle2);
//            lerpLines.add(line);
//            Function<Double, Point2D> lerp = line.getLerpFunction();
//            lerps.add(lerp);
//            nextLerps.add(lerp);
//        }
//        System.out.print("first next lerps:");
//        for (Function<Double, Point2D> lerp:nextLerps) {
//            System.out.print(" "+lerp.apply(t));
//        }
//        System.out.println();
//        for (int i = 0; nextLerps.size()>2; i++) {
//            ArrayList<Function<Double, Point2D>> currentLerps = nextLerps;
//            nextLerps = new ArrayList<>();
//            for (int j = 0; j < currentLerps.size() - 1; j++) {
//                Point2D point2D1 = currentLerps.get(j).apply(t);
//                Point2D point2D2 = currentLerps.get(j+1).apply(t);
////                LinkedLine line = new LinkedLine(circle1,circle2);
//                lerpLines.add(line);
//                Function<Double, Point2D> lerp = line.getLerpFunction();
//                lerps.add(lerp);
//                nextLerps.add(lerp);
//            }
//        }
//        if(nextLerps.size()==2){
//            Point circle1 = new Point(nextLerps.get(0).apply(t),radius, Color.BLACK, scene,root,selected,selectedX,selectedY,submit);
//            Point circle2 = new Point(nextLerps.get(1).apply(t),radius, Color.BLACK, scene,root,selected,selectedX,selectedY,submit);
//            LinkedLine line = new LinkedLine(circle1,circle2);
//            Function<Double, Point2D> lerp = line.getLerpFunction();
//            lerps.add(lerp);
//            goldenCircles.add(new Circle(lerp.apply(t).getX(),lerp.apply(t).getY(),radius,Color.GOLD));
//        }
//    }
/*
    private void drawBezierCurve(List<Point> points, double t) {
        lerps.clear();
        for (LinkedLine lerp : lerpLines) {
            root.getChildren().remove(lerp);
        }
        lerpLines.clear();
        goldenCircles.clear();

        for (int i = 0; i < points.size() - 1; i++) {
            Point circle1 = points.get(i);
            Point circle2 = points.get(i + 1);
            LinkedLine line = new LinkedLine(circle1, circle2);
            lerpLines.add(line);
            Function<Double, Point2D> lerp = line.getLerpFunction();
            lerps.add(lerp);
        }

        while (lerps.size() > 2) {
            ArrayList<Function<Double, Point2D>> currentLerps = new ArrayList<>(lerps);
            lerps.clear();
            for (int i = 0; i < currentLerps.size() - 1; i++) {
                Function<Double, Point2D> lerp1 = currentLerps.get(i);
                Function<Double, Point2D> lerp2 = currentLerps.get(i + 1);
                Point2D point1 = lerp1.apply(t);
                Point2D point2 = lerp2.apply(t);
                Circle circle1 = new Circle(point1.getX(),point1.getY(),radius2);
                Circle circle2 = new Circle(point2.getX(),point2.getY(),radius2);
                LinkedLine line = new LinkedLine(circle1, circle2);
                lerpLines.add(line);
                lerps.add(line.getLerpFunction());
            }
        }

        if (lerps.size() == 2) {
            Function<Double, Point2D> lerp1 = lerps.get(0);
            Function<Double, Point2D> lerp2 = lerps.get(1);
            Point2D point1 = lerp1.apply(t);
            Point2D point2 = lerp2.apply(t);
            Circle circle1 = new Circle(point1.getX(),point1.getY(),radius2);
            Circle circle2 = new Circle(point2.getX(),point2.getY(),radius2);
            LinkedLine line = new LinkedLine(circle1, circle2);
            lerpLines.add(line);
            Function<Double, Point2D> lerp = line.getLerpFunction();
            lerps.add(lerp);
            goldenCircles.add(new Circle(lerp.apply(t).getX(), lerp.apply(t).getY(), radius2, Color.GOLD));
            root.getChildren().add(goldenCircles.get(goldenCircles.size() - 1));
        }

        root.getChildren().addAll(lerpLines);
    }
*/
    private void createBottomBar() {
        selectedX = new TextField();
        selectedX.setPrefColumnCount(15);
        selectedX.setPromptText("x");
        selectedX.setVisible(false);
        selectedX.setMaxWidth(50);

        selectedY = new TextField();
        selectedY.setPrefColumnCount(15);
        selectedY.setPromptText("y");
        selectedY.setMaxWidth(50);
        selectedY.setVisible(false);

        submit = new Button("Set new Cords");
        submit.setVisible(false);
        submit.setOnMouseClicked(event -> {
            try {
                selected.setCenterX(Double.parseDouble(selectedX.getText()));
                selected.setCenterY(Double.parseDouble(selectedY.getText()));
            }
            catch (Exception ignored){
            }
//            relinkLines();
        });

        fixSlider = new Button("Fix the Slider");
        fixSlider.setOnMouseClicked(event -> {
            // Bind the value of the slider to a property in your application
            sliderBinding(slider);
        });

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(selectedX,selectedY,submit,fixSlider);
        root.setBottom(hBox);

    }

    private void createTopBar() {
//        clear = new Button("Clear -Dedicated to Camille Staats");
        clear = new Button("The Camille Clear Button");
        clear.setOnMouseClicked(event -> {
            clearGoldenCircles();
        });
        addPoint = new Button("Add a point");
        addPoint.setOnMouseClicked(event -> addAPoint());

        removePoint = new Button("Remove this point");
        removePoint.setOnMouseClicked(event -> {
            removePoint();
        });
        removePoint.setVisible(selected!=null); //Ideally this should be bound to this but currently it's manually set

////        selected.addEventHandler((observable, oldValue, newValue) -> {
//            if(newValue){
//                removePoint.setVisible(true);
//            }
//        });
//        SimpleObjectProperty<Point> simpleObjectProperty = new SimpleObjectProperty<>(selected,"is",null);
//        removePoint.visibleProperty().bind(Bindings.isNotNull(simpleObjectProperty));

        slider = getSlider(curvePoints, initialValue);
        // create the control panel
        HBox controlPanel = new HBox(10,clear,slider, addPoint, removePoint);
        controlPanel.setAlignment(Pos.CENTER);
        root.setTop(controlPanel);
    }

    static void removePoint() {
        root.getChildren().remove(selected);
        curvePoints.remove(selected);

        selected = null;
        selectedX.setVisible(false);
        selectedY.setVisible(false);
        submit.setVisible(false);
        removePoint.setVisible(false);
        drawBezierCurve(curvePoints, initialValue,true,false);
    }

    public static void clearGoldenCircles() {
        while(goldenCircles.size()>0){
            root.getChildren().remove(goldenCircles.get(0).getValue());
            goldenCircles.remove(goldenCircles.get(0));
            // Bind the value of the slider to a property in your application
            sliderBinding(slider);
        }
    }

    private Slider getSlider(ArrayList<Point> points,double initialValalue) {
        Slider slider = new Slider(0, 1, initialValalue);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(0.1);
        slider.setMinorTickCount(10);
        slider.setBlockIncrement(0.1);

        // Bind the value of the slider to a property in your application
        sliderBinding(slider);
//        slider.setOnMouseClicked(event -> drawBezierCurve(curvePoints, slider.getValue(),false,true));
        return slider;
    }
}
