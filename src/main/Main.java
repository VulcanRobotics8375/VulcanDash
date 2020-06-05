package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.hardware.DcMotor;

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
    double offset = 5;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
//        Server server = new Server(8375);
//        server.start();
//        waitForStart();
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
//        Data.motors.add(new DcMotor(0));
//        Data.motors.add(new DcMotor(1));

        HashMap<Integer, TextField> limHighs = new HashMap<>();
        HashMap<Integer, TextField> limLows = new HashMap<>();

        //iterate through all of the motors and add text boxes and labels for each motor constant.
        int row = 1;
        for (int i = 0; i < Data.motors.size(); i++) {

            Label label = new Label("DcMotor " + Data.motors.get(i).id + ": Upper Limit");
            TextField field = new TextField();
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
            TextField field = new TextField();
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
            while(Data.isRunning()) {
                robotView.setX(Data.getRobotPos().x);
                robotView.setY(Data.getRobotPos().y);
            }
        }).start();

        robotView.setPreserveRatio(true);

        Pane boardPane = new Pane();
        boardPane.getChildren().add(boardView);
        boardPane.getChildren().add(robotView);

        //add the 3 columns of the splitter, put the robot map in a scrollPane to account for different sized windows
        splitter.getItems().addAll(constantTabs, new ScrollPane(boardPane), new ScrollPane(new Label("col 3")));

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
