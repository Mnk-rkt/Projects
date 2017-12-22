package com.company;


import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

// определяем класс точки, его основные характеристики
public class Point {

    private double y;
    private double x;
    private double theta;
    private double r;

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

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }


    // Переводим декартовы координаты в полярные.
    // Угол theta
    public double getTheta() {
        this.theta = atan2(y, x);
        return theta;
    }

    // Возвращает расстояние до точки r
    public double getR() {
        this.r = Math.sqrt(x * x + y * y);
        return r;

    }

    // Cоздает точкb на основе угла theta и расстояния r с небольшим смещением угла.
    public Point markLeft(double r) {
        x = (r * cos(this.getTheta()- 0.00000000001));
        y = (r * sin(this.getTheta()- 0.00000000001));
        return new Point(x, y);
    }
    public Point markRight(double r) {
        x = (r * cos(this.getTheta()+ 0.00000000001));
        y = (r * sin(this.getTheta()+ 0.00000000001));
        return new Point(x, y);
    }
}

