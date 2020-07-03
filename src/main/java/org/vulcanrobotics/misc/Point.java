package org.vulcanrobotics.misc;

import java.util.ArrayList;

public class Point {
    public double x;
    public double y;

    public Point(){
        x = 0.0;
        y = 0.0;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public static ArrayList<Double> toXList(ArrayList<Point> points) {
        ArrayList<Double> values = new ArrayList<Double>();

        for (Point p : points) {
            values.add(p.x);
        }

        return values;

    }

    public static ArrayList<Double> toYList(ArrayList<Point> points) {
        ArrayList<Double> values = new ArrayList<Double>();

        for (Point p : points) {
            values.add(p.y);
        }

        return values;

    }


    public void setPoint(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

}
