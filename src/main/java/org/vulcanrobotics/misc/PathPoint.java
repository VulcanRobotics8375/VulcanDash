package org.vulcanrobotics.misc;

import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

public class PathPoint {
    public int id;
    public Point point;
    public Rectangle rect;
    public TextField xField;
    public TextField yField;

    public PathPoint(int id) {
        this.id = id;
    }

    public PathPoint(int id, Point point, Rectangle rect) {
        this.id = id;
        this.point = point;
        this.rect = rect;
    }

}
