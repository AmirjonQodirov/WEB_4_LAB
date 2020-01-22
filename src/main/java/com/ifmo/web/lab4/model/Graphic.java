package com.ifmo.web.lab4.model;

import com.ifmo.web.lab4.model.data.Point;
import com.ifmo.web.lab4.model.data.PointForClient;
import org.springframework.stereotype.Component;

@Component
public class Graphic {
    private boolean isInArea(double x, double y, double r) {
        boolean square = x <= r && x >= 0 && y <= r / 2.0 && y >= 0;
        boolean triangle = y >= (2 * x - r) && y <= 0 && x >= 0;
        boolean sector = (x * x + y * y) <= r * r  && x <= 0 && y <= 0;
        return triangle || square || sector;
    }

    public boolean isInArea(Point point) {
        return isInArea(point.getX(), point.getY(), point.getR());
    }

    public boolean isInArea(PointForClient point) {
        return isInArea(point.getX(), point.getY(), point.getR());
    }

}
