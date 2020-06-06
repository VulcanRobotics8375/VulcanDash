package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.hardware.DcMotor;
import main.misc.Point;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {
    double offset = 7;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Server server = new Server(8375);
        Data.running = true;
        server.sendToRobot("/start");
        server.start();
        waitForStart();
        primaryStage.setTitle("Vulcan Dashboard");

        //main Pane for the whole window. Holds 3 columns split at default 20% width and 80% width. The columns are resizable
        SplitPane splitter = new SplitPane();
        splitter.setDividerPositions(0.2, 0.8);

        //the TabPane for the left column
        TabPane constantTabs = new TabPane();
        Tab motorTab = new Tab("Motors");
        Tab servoTab = new Tab("Servos");
        Tab miscTab = new Tab("Constants");

        constantTabs.getTabs().addAll(motorTab, servoTab, miscTab);
        constantTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //GridPane for holding the first tabs content
        GridPane motorGrid = new GridPane();
        setGridTabOptions(motorGrid);

        //TODO remove debug code
//        Data.motors.add(new DcMotor(1));
//        Data.motors.add(new DcMotor(0));

        HashMap<Integer, TextField> limHighs = new HashMap<>();
        HashMap<Integer, TextField> limLows = new HashMap<>();

        //iterate through all of the motors and add text boxes and labels for each motor constant.
        int row = 1;
        for (int i = 0; i < Data.motors.size(); i++) {

            Label label = new Label("DcMotor " + Data.motors.get(i).id + ": Upper Limit");
            TextField field = new TextField(Integer.toString(Data.motors.get(i).limHigh));
            limHighs.put(Data.motors.get(i).id, field);
            motorGrid.add(label, 1, row);
            row++;
            motorGrid.add(field, 1, row);
            row++;
        }
        Button updateMotors = new Button("Update Motor Constants");
        updateMotors.setOnAction(e -> {
            for (DcMotor motor : Data.motors) {
                String val = limHighs.get(motor.id).getText();
//                server.sendToRobot("/set DcMotor " + motor.id + " limHigh " + val);
            }
        });

        motorGrid.add(updateMotors, 1, row);
        //put motorGrid inside of a scrollPane in the case that the height of the gridpane is taller than the window.
        motorTab.setContent(new ScrollPane(motorGrid));

        //do the same for the servo and misc constant tabs.
        GridPane servoGrid = new GridPane();
        setGridTabOptions(servoGrid);

        row = 1;
        for (int i = 0; i < Data.servos.size(); i++) {
            Label label = new Label("Servo " + Data.servos.get(i).id + ": Start Position");
            TextField field = new TextField(Double.toString(Data.servos.get(i).getPos()));
            servoGrid.add(label, 1, row);
            row++;
            servoGrid.add(field, 1, row);
            row++;
        }
        Button updateServos = new Button("Update Servo Constants");
        updateServos.setOnAction(e -> {
//            server.sendToRobot("");
        });
        servoGrid.add(updateServos, 1, row);
        servoTab.setContent(servoGrid);

        GridPane constantGrid = new GridPane();
        setGridTabOptions(constantGrid);

        row = 1;
        for (int i = 0; i < Data.constants.size(); i++) {
            Label label = new Label(Data.constants.get(i).key);
            TextField field = new TextField(Data.constants.get(i).val.toString());
            constantGrid.add(label, 1, row);
            row++;
            constantGrid.add(field, 1, row);
            row++;
        }
        Button updateConstants = new Button("Update Constants");
        updateConstants.setOnAction(e -> {
            //button action
        });
        constantGrid.add(updateConstants, 1, row);
        miscTab.setContent(constantGrid);

        Image board = new Image(new FileInputStream("/Users/williampaoli/IdeaProjects/VulcanDashFX/res/img/board.png"));
        Image robot = new Image(new FileInputStream("/Users/williampaoli/IdeaProjects/VulcanDashFX/res/img/dashboardBot.png"));
        ImageView boardView = new ImageView(board);
        ImageView robotView = new ImageView(robot);

        boardView.setFitHeight(800);
        boardView.setPreserveRatio(true);
        robotView.setFitHeight(120);
        robotView.setY(Data.getStartPos().y + offset);
        robotView.setX(Data.getStartPos().x + offset);
        new Thread(() -> {
            System.out.println("robot position updater started");
            while(Data.running) {
                robotView.setX(Data.getRobotPos().x);
                robotView.setY(Data.getRobotPos().y);
                robotView.setRotate(Data.robotAngle);
            }
        }).start();

        robotView.setPreserveRatio(true);

        Pane boardPane = new Pane();
        boardPane.getChildren().add(boardView);
        boardPane.getChildren().add(robotView);
        boardPane.setOnMouseClicked(e -> {
//            System.out.println(e.getX());
//            System.out.println(e.getY());
//            TODO Auton create rectangle stuff
            Point rectCenter = new Point(e.getX(), e.getY());
            Rectangle pathPoint = new Rectangle(e.getX() - 25, e.getY() - 12.5, 50, 25);
            pathPoint.setCursor(Cursor.OPEN_HAND);
            pathPoint.setStroke(Color.BLACK);
            pathPoint.setStrokeWidth(2);
            pathPoint.setOpacity(0.5);
            pathPoint.setFill(Color.LIGHTGRAY);
            boardPane.getChildren().add(pathPoint);
        });

        TabPane monitorTabs = new TabPane();
        Tab sensorInfo = new Tab("Sensors/Encoders");
        Tab autoTab = new Tab("Auto");

        monitorTabs.getTabs().addAll(sensorInfo, autoTab);
        monitorTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        GridPane motorInfoGrid = new GridPane();
        setGridTabOptions(motorInfoGrid);

        for (int i = 0; i < Data.motors.size(); i++) {
            Label label = new Label("");
        }

        //add the 3 columns of the splitter, put the robot map in a scrollPane to account for different sized windows
        splitter.getItems().addAll(constantTabs, new ScrollPane(boardPane), monitorTabs);

        //finally, put the main components in a VBox, in case we need to add anymore elements outside of the SplitPane.
        VBox container = new VBox();
        container.getChildren().add(splitter);
        Image appIcon = new Image(new FileInputStream("/Users/williampaoli/IdeaProjects/VulcanDashFX/res/img/vulcanPNG.png"));
        primaryStage.getIcons().add(appIcon);
        primaryStage.setScene(new Scene(container, 1280, 700));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Data.running = false);
    }


    public static void main(String[] args) {

        launch(args);
    }

    public void setGridTabOptions(GridPane pane) {
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(5);
        pane.setVgap(5);
        pane.setPadding(new Insets(10, 10, 10, 10));
    }

    public void waitForStart() {
        System.out.println("waiting for start command from client...");
        while(!Data.getStart()) {

        }
        System.out.println("Start command received, Starting Dashboard");
    }
}
