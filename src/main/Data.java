package main;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import main.hardware.DcMotor;
import main.hardware.Servo;
import main.misc.Constant;
import main.misc.PathPoint;
import main.misc.Point;
import main.misc.TelemetryMsg;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static List<DcMotor> motors = new ArrayList<>();
    public static List<Servo> servos = new ArrayList<>();
    public static List<Constant> constants = new ArrayList<>();
    public static List<TelemetryMsg> telemetryList = new ArrayList<>();
    public static List<PathPoint> pathPoints = new ArrayList<>();
    public static Pane pathLines = new Pane();
    public static Point startPos = new Point();
    public static Point robotPos = new Point();
    public static double robotAngle = 0;
    public static volatile boolean running;
    public static double batteryLvl;
    private static volatile boolean started;

    public static void setStart(boolean started) {
        Data.started = started;
    }

    public static boolean getStart() {
        return started;
    }

    public static Point getRobotPos() {
        return robotPos;
    }

    public static Point getStartPos() {
        return startPos;
    }

    public static boolean isRunning() {
        return running;
    }

    public static DcMotor getMotorById(int id) {
        // search through motor list until motor with matching id is found
        for (DcMotor motor : motors) {
            if (motor.id != id)
                continue;
            return motor;
        }

        //if there is no match, return null
        return null;
    }

    public static Servo getServoById(int id) {
        // search through servo list until servo with matching id is found
        for (Servo servo : servos) {
            if (servo.id != id)
                continue;
            return servo;
        }

        //if there is no match, return null
        return null;
    }

    public static TelemetryMsg getTelemetryById(int id) {
        for (TelemetryMsg msg : telemetryList) {
            if(msg.id == id)
                continue;
            return msg;
        }
        return null;
    }

    public static Constant getConstantById(String key) {
        for (Constant constant : constants) {
            if(!(constant.key.equals(key)))
                continue;
            return constant;
        }

        return null;
    }

}
