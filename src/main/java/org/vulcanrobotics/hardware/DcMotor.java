package org.vulcanrobotics.hardware;

public class DcMotor {
    public int limLow;
    public int limHigh;
    public int encoderPos;
    public float power;
    public int id;

    public DcMotor() {}

    public DcMotor(int id) {
        this.id = id;
    }

    public DcMotor(int limLow, int limHigh, int encoderPos, int id) {
        this.limLow = limLow;
        this.limHigh = limHigh;
        this.encoderPos = encoderPos;
        this.id = id;
    }


}
