package com.company;

//
class Segment {

    private Point a;
    private Point b;

    Point getHead() {
        return a;
    }

    Point getTail() {
        return b;
    }

    Segment(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    // Checks whether or not segment is a point.
    boolean isAPoint() {
        return this.a.getX() == this.b.getX() && this.a.getY() == this.b.getY();
    }

    // Returns crossing point of the lines.
    // Note: not segments.
    Point getCrossingPoint(Segment s2) {

        double a1 = this.b.getY() - this.a.getY();
        double b1 = this.a.getX() - this.b.getX();
        double c1 = a1 * this.a.getX() + b1 * this.a.getY();

        double a2 = s2.b.getY() - s2.a.getY();
        double b2 = s2.a.getX() - s2.b.getX();
        double c2 = a2 * s2.a.getX() + b2 * s2.a.getY();

        double det = a1 * b2 - a2 * b1;

        double x = (b2 * c1 - b1 * c2) / det;
        double y = (a1 * c2 - a2 * c1) / det;
        return new Point(x, y);
    }

    // Checks if segment is crossed with specified one.
    boolean isCrossed(Segment s2) {
        double c = (this.b.getX() - this.a.getX()) * (s2.getTail().getY() - s2.getHead().getY()) -
                (this.getTail().getY() - this.a.getY()) * (s2.getTail().getX() - s2.getHead().getX());

        if (c == 0) return false;

        double a1 = (this.a.getY() - s2.getHead().getY()) * (s2.getTail().getX() - s2.getHead().getX()) -
                (this.a.getX() - s2.getHead().getX()) * (s2.getTail().getY() - s2.getHead().getY());
        double a2 = (this.a.getY() - s2.getHead().getY()) * (this.b.getX() - this.a.getX()) -
                (this.a.getX() - s2.getHead().getX()) * (this.getTail().getY() - this.a.getY());

        double b1 = a1 / c;
        double b2 = a2 / c;

        return b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1;
    }

    // Returns maximum possible distance (r) from (0,0) to the segment
    double getMaxDistance() {
        if (this.a.getR() > this.b.getR()) {
            return this.a.getR();
        } else return this.b.getR();
    }

}