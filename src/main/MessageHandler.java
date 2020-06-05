package main;

import main.hardware.DcMotor;
import main.hardware.Servo;
import main.misc.Constant;

import java.nio.FloatBuffer;
import java.util.Objects;

public class MessageHandler {

    public static void parseMessage(String msg) {

        if(msg.startsWith("/")) {
            String[] placeholder = msg.split(" ");

            String key = placeholder[0];

            switch (key) {
                case "/add":
                    String type = placeholder[1];
                    String id = placeholder[2];
                    switch (type) {
                        case "DcMotor":
                            Data.motors.add(new DcMotor(Integer.parseInt(id)));
                            break;
                        case "Servo":
                            Data.servos.add(new Servo(Integer.parseInt(id)));
                            break;
                        case "Constant":
                            String val = placeholder[3];
                            String numType = placeholder[4];
                            switch (numType) {
                                case "int":
                                    Data.constants.add(new Constant(id, Integer.parseInt(val)));
                                    break;
                                case "float":
                                    Data.constants.add(new Constant(id, Float.parseFloat(val)));
                                    break;
                                case "double":
                                    Data.constants.add(new Constant(id, Double.parseDouble(val)));
                                    break;
                                case "short":
                                    Data.constants.add(new Constant(id, Short.parseShort(val)));
                                    break;
                                case "long":
                                    Data.constants.add(new Constant(id, Long.parseLong(val)));
                                    break;
                            }
                    }
                    break;

                case "/start":
                    Data.setStart(true);
                    break;

                case "/battery":
                    Data.batteryLvl = Double.parseDouble(placeholder[1]);
                    break;

                case "/update":
                    type = placeholder[1];
                    id = placeholder[2];
                    switch (type) {
                        case "DcMotor":
                            Objects.requireNonNull(Data.getMotorById(Integer.parseInt(id))).encoderPos = Integer.parseInt(placeholder[3]);
                            Objects.requireNonNull(Data.getMotorById(Integer.parseInt(id))).power = Float.parseFloat(placeholder[4]);
                            break;
                        case "Servo":
                            Objects.requireNonNull(Data.getServoById(Integer.parseInt(id))).setPos(Float.parseFloat(placeholder[3]));
                            break;
                    }

            }
        }

    }

}
