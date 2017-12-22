package com.company;

// Определяем класс отрезка.
public class Segment {
    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public void setB(Point b) {
        this.b = b;
    }

    public Point getB() {
        return b;
    }

    private Point a;
    private Point b;

    Segment(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    // Проверяет, является ли отрезок точкой.
    boolean isAPoint() {
        return this.a.getX() == this.b.getX() && this.a.getY() == this.b.getY();
    }

    // Проверяет, возможно ли пересечение луча из точки (0,0) с отрезком.
    public boolean isIntercectible() {
        return this.a.getTheta() == this.b.getTheta();
    }

    // Возвращяет точку пересечения отрезков.
    // Используется стандартное уравнение прямой.
    public Point getCPoint(Segment s2) {

        //строим уравнения прямых и получаем детерминант.
        double a1 = this.b.getY() - this.a.getY();
        double b1 = this.a.getX() - this.b.getX();
        double c1 = a1 * this.a.getX() + b1 * this.a.getY();

        double a2 = s2.b.getY() - s2.a.getY();
        double b2 = s2.a.getX() - s2.b.getX();
        double c2 = a2 * s2.a.getX() + b2 * s2.a.getY();

        double det = a1 * b2 - a2 * b1;

        // получаем точку пересечения линий, заданных отрезками.
        double x = (b2 * c1 - b1 * c2) / det;
        double y = (a1 * c2 - a2 * c1) / det;
        return new Point(x, y);
    }

    //Проверка на существование точки пересечения отрезков.
    public boolean isCrossed(Segment s2) {
        double c = (this.b.getX() - this.a.getX()) * (s2.getB().getY() - s2.getA().getY()) -
                (this.getB().getY() - this.a.getY()) * (s2.getB().getX() - s2.getA().getX());

        if (c == 0) return false;

        double a1 = (this.a.getY() - s2.getA().getY()) * (s2.getB().getX() - s2.getA().getX()) -
                (this.a.getX() - s2.getA().getX()) * (s2.getB().getY() - s2.getA().getY());
        double a2 = (this.a.getY() - s2.getA().getY()) * (this.b.getX() - this.a.getX()) -
                (this.a.getX() - s2.getA().getX()) * (this.getB().getY() - this.a.getY());

        double b1 = a1 / c;
        double b2 = a2 / c;

        return b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1;
    }


    //Метод возвращает точку отрезка с максимальным значением theta
    public Point getMaxTP() {
        if (this.a.getTheta() > this.b.getTheta()) {
            return this.a;
        } else return this.b;
    }

    //Метод возвращает точку отрезка с минимальным значением theta
    public Point getMinTP() {
        if (this.a.getTheta() < this.b.getTheta()) {
            return this.a;
        } else return this.b;
    }

    //Возвращает максимальное расстояние до отрезка. Нет смысла строить лучи в бесконечность, верно?
    public double getMaxR() {
        if (this.a.getR() > this.b.getR()) {
            return this.a.getR();
        } else return this.b.getR();
    }

}
