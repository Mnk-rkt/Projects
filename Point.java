package com.company;


import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

// определяем класс точки, его основные характеристики
public class Point {

    private double y;
    private double x;


    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }


    // Two following methods are used to get polar coordinates of a point (angle theta and distance r)
    public double getTheta() {
        return atan2(y, x);
    }

    public double getR() {
        return Math.sqrt(x * x + y * y);

    }

    // Two following methods are used to create a marking points with a small offset from the original.
    private static double offset = 0.00000000000001;

    public Point markLeft(double r) {
        x = ((r) * cos(this.getTheta() - offset));
        y = ((r) * sin(this.getTheta() - offset));
        return new Point(x, y);
    }

    public Point markRight(double r) {
        x = ((r) * cos(this.getTheta() + offset));
        y = ((r) * sin(this.getTheta() + offset));
        return new Point(x, y);
    }
}

