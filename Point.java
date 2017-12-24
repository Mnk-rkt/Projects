package com.company;


import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

// определяем класс точки, его основные характеристики
 class Point {

    private final double y;
    private final double x;


     Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

     double getY() {
        return y;
    }

     double getX() {
        return x;
    }


    // Two following methods are used to get polar coordinates of a point (angle theta and distance r)
     double getTheta() {
        return atan2(y, x);
    }

     double getR() {
        return Math.sqrt(x * x + y * y);

    }

    // Two following methods are used to create a marking points with a small offset from the original.
    private static double offset = 0.00000000000001;

     Point markLeft(double r) {
        double x = ((r) * cos(this.getTheta() - offset));
        double y = ((r) * sin(this.getTheta() - offset));
        return new Point(x, y);
    }

     Point markRight(double r) {
        double x = ((r) * cos(this.getTheta() + offset));
        double y = ((r) * sin(this.getTheta() + offset));
        return new Point(x, y);
    }
}

