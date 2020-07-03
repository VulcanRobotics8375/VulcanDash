package org.vulcanrobotics.misc;

public class TelemetryMsg {
    private String msg;
    public int id;

    public TelemetryMsg() {}

    public TelemetryMsg(int id) {
        this.id = id;
    }

    public TelemetryMsg(TelemetryMsg msg) {
        this.msg = msg.msg;
        this.id = msg.id;
    }

    public TelemetryMsg(String msg, int id) {
        this.msg = msg;
        this.id = id;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public void setTelemetry(TelemetryMsg msg) {
        this.msg = msg.msg;
        this.id = msg.id;
    }

}
