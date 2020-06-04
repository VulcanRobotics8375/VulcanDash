package main.hardware;

public class Servo {

    public int id;
    public double pos;

    public Servo() {}

    public Servo(int id) {
        this.id = id;
    }

    public Servo(int id, double pos) {
        this.id = id;
        this.pos = pos;
    }

}
