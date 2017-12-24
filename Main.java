package com.company;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // ArrayList Segments will store all Segments.
        ArrayList<Segment> Segments = new ArrayList<Segment>();
        double x1;
        double y1;
        double x2;
        double y2;
        Point a;
        Point b;
        Segment s;
        Point cam = new Point(0, 0);
        int crossingCounter = 0;
        boolean c = true;
        while (c) {

            // Input for point coordinates. Each cycle will repeat until valid number is typed.
            // Input method can be changed as long as ArrayList Segments is completed.
            System.out.print("Please enter coordinates one by one\n");
            while (true) {
                System.out.println("X1: \n");
                if (sc.hasNextDouble()) {
                    x1 = sc.nextDouble();
                    break;
                }
            }
            while (true) {
                System.out.println("Y1: \n");
                if (sc.hasNextDouble()) {
                    y1 = sc.nextDouble();
                    break;
                }
            }
            while (true) {
                System.out.println("X1: \n");
                if (sc.hasNextDouble()) {
                    x2 = sc.nextDouble();
                    break;
                }
            }
            while (true) {
                System.out.println("Y2: \n");
                if (sc.hasNextDouble()) {
                    y2 = sc.nextDouble();
                    break;
                }
            }

            //Filling ArrayList Segments
            a = new Point(x1, y1);
            b = new Point(x2, y2);
            if ((a.getX() == 0 && a.getY() == 0) || (b.getY() == 0 && b.getX() == 0)) {
                System.out.println("Point is in the way, I can't see anything.");
            } else {
                s = new Segment(a, b);
                if (!s.isAPoint()) {
                    Segments.add(s);
                } else {
                    System.out.println("Please enter valid coordinates. This segment is a point.\n");
                }
            }

            // Запрашиваем подтверждение на продолжение ввода. Цикл повторяется, пока не будет введен "y" или "n".
            start:
            while (c) {
                System.out.println("Is this the last segment? y/n \n");
                switch (sc.next()) {
                    case "y":
                        c = false;
                        break;
                    case "n":
                        System.out.println("Please enter coordinates of the next segment.");
                        break start;
                }
            }
        }


        try {
            //Creating test beams (technically, segments) from camera to marked points
            // Each segment will produce four beams.
            ArrayList<Segment> beams = new ArrayList<Segment>();
            double globalR = Segments.get(Segments.size() - 1).getMaxR() + 1;
            for (int i = 0; i < Segments.size(); i++) {
                Point m1 = new Point(Segments.get(i).getA().getX(), Segments.get(i).getA().getY()).markLeft(globalR);
                Point m2 = new Point(Segments.get(i).getB().getX(), Segments.get(i).getB().getY()).markLeft(globalR);
                Point m3 = new Point(Segments.get(i).getA().getX(), Segments.get(i).getA().getY()).markRight(globalR);
                Point m4 = new Point(Segments.get(i).getB().getX(), Segments.get(i).getB().getY()).markRight(globalR);
                beams.add(new Segment(cam, m1));
                beams.add(new Segment(cam, m2));
                beams.add(new Segment(cam, m3));
                beams.add(new Segment(cam, m4));
            }
            //testPoint is used to create new temporary segments.
            Point testPoint = new Point(globalR + 1, globalR + 1);

            for (int z = 0; z < beams.size(); z++) {
                // Resets testPoint after each iteration
                testPoint.setX(globalR + 1);
                testPoint.setY(globalR + 1);
                for (int e = 0; e < Segments.size(); e++) {
                    //Tests for the beam crossing each segment. If crossing point is closer than the previous one,
                    // testPoint is updated
                    if (beams.get(z).isCrossed(Segments.get(e)) && testPoint.getR() >=
                            beams.get(z).getCPoint(Segments.get(e)).getR()) {
                        testPoint = beams.get(z).getCPoint(Segments.get(e));
                    }
                }// After finding closest crossing point checks if the beam is crossed with target segment.
                Segment testSegment = new Segment(cam, testPoint);
                if (testSegment.isCrossed(Segments.get(Segments.size() - 1)) && testPoint.getX() != (globalR + 1) && testPoint.getY() != globalR + 1) {
                    crossingCounter++;
                }
            }
            System.out.println("Crossings found: " + crossingCounter);
            if (crossingCounter > 0) {
                System.out.println("Target segment is visible");
            } else {
                System.out.println("Target segment is not visible");
            }

        }

        // Обрабатываем случай отсутствия данных.
        catch (IndexOutOfBoundsException e) {
            System.out.println("Segments list is empty");
        }


    }


}





