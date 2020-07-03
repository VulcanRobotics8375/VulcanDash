package org.vulcanrobotics;

import org.vulcanrobotics.hardware.DcMotor;
import org.vulcanrobotics.hardware.Servo;
import org.vulcanrobotics.misc.Constant;
import org.vulcanrobotics.misc.TelemetryMsg;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Objects;

public class MessageHandler {

    public static void parseMessage(String msg) {

        if(msg.startsWith("/")) {
            String[] placeholder = msg.split(" ");
//            System.out.println(Arrays.toString(placeholder));
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
                        case "Robot":
                            Data.robotPos.x = Double.parseDouble(placeholder[2]);
                            Data.robotPos.y = Double.parseDouble(placeholder[3]);
                            Data.robotAngle = Double.parseDouble(placeholder[4]);
                    }
                    break;

                case "/telemetry":
                    TelemetryMsg telemetry = new TelemetryMsg();
                    boolean telemetrySet = false;
                    int telemetryId = 0;
                    for (TelemetryMsg telemetryMsg : Data.telemetryList) {
                        if(telemetryMsg.id != Integer.parseInt(placeholder[1])) {
                            telemetryId++;
                            continue;
                        }
                        telemetry.setTelemetry(telemetryMsg);
                        telemetrySet = true;
                    }
                    StringBuilder message = new StringBuilder();
                    for (int i = 2; i < placeholder.length; i++) {
                        message.append(placeholder[i]).append(" ");
                    }
                    if(!telemetrySet) {
                        telemetry.setTelemetry(new TelemetryMsg(message.toString(), telemetryId));
                        Data.telemetryList.add(telemetry);
                    } else {
                        telemetry.setMessage(message.toString());
                    }
            }
        }

    }

}
