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
        Point camera = new Point(0, 0);
        int crossingCounter = 0;

        // Input sequence for point coordinates. Each cycle will repeat until valid number is typed.
        // Input method can be changed as long as ArrayList Segments is completed.
        int numberOfSegments = 0;
        System.out.println("Please input number of segments:");
        while (numberOfSegments <= 0) {
            if (sc.hasNextInt()) {
                numberOfSegments = sc.nextInt();
            }
        }
        System.out.println(numberOfSegments);
        int i = 0;
        while (i < numberOfSegments) {
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
                System.out.println("X2: \n");
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
            s = new Segment(a, b);
            if (!s.isAPoint()) {
                Segments.add(s);
                i++;
            } else {
                System.out.println("Please enter valid coordinates. This segment is a point.\n");
            }
        }

        Segment redSegment = Segments.get(Segments.size() - 1);
        // viewDistance is used to limit maximum beam length
        // viewDistanceIncrement is used to guarantee that the red segment is within viewDistance in rare corner cases
        double viewDistanceIncrement = 1;
        double viewDistance = redSegment.getMaxDistance() + viewDistanceIncrement;
        // Creating test beams (technically, segments) from camera to marked points
        // Each segment will produce four beams.
        ArrayList<Segment> beams = new ArrayList<Segment>();
        for (com.company.Segment Segment : Segments) {
            Point m1 = Segment.getHead().markLeft(viewDistance);
            Point m2 = Segment.getTail().markLeft(viewDistance);
            Point m3 = Segment.getHead().markRight(viewDistance);
            Point m4 = Segment.getTail().markRight(viewDistance);
            beams.add(new Segment(camera, m1));
            beams.add(new Segment(camera, m2));
            beams.add(new Segment(camera, m3));
            beams.add(new Segment(camera, m4));
        }
        // dummy point is used to reset testPoint.
        // Is farther away than red segment by default.
        Point dummy = new Point(viewDistance, viewDistance);
        Point testPoint = dummy;
        for (Segment beam : beams) {
            for (Segment Segment : Segments) {
                //Tests for the beam crossing each segment. If crossing point is closer than the previous one,
                // testPoint is updated
                if (beam.isCrossed(Segment) && testPoint.getR() > beam.getCrossingPoint(Segment).getR()) {
                    testPoint = beam.getCrossingPoint(Segment);
                }
            }
            // After finding closest crossing point checks if the beam is crossed with red segment
            // and dummy point is not default.
            Segment testSegment = new Segment(camera, testPoint);
            if (testSegment.isCrossed(redSegment) && testPoint != dummy) {
                crossingCounter++;
            }
            testPoint = dummy;
        }
        if (crossingCounter > 0) {
            System.out.println("Red segment is visible");
        } else {
            System.out.println("Red segment is not visible");
        }
    }
}