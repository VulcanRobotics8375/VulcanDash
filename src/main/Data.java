package main;

import main.hardware.DcMotor;
import main.hardware.Servo;
import main.misc.Constant;
import main.misc.Point;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static  List<DcMotor> motors = new ArrayList<>();
    public static  List<Servo> servos = new ArrayList<>();
    public static ArrayList<Constant> constants = new ArrayList<>();
    public static Point startPos = new Point();
    public static Point robotPos = new Point();
    public static boolean running;
    public static double batteryLvl;

    private static boolean started;

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
        // search through motor list until motor with matching id is found
        for (Servo servo : servos) {
            if (servo.id != id)
                continue;
            return servo;
        }

        //if there is no match, return null
        return null;
    }

}