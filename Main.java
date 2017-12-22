package com.company;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) {
        run();
    }


    private static void run() {

        Scanner sc = new Scanner(System.in);
        // В коллекции Segments хранятся сведения о всех используемых отрезках.
        ArrayList<Segment> Segments = new ArrayList<Segment>();
        double x1;
        double y1;
        double x2;
        double y2;
        Point a;
        Point b;
        Segment s;
        Point cam = new Point(0, 0);
        boolean c = true;
        while (c) {
            /*
            Запрашиваем координаты точек. Каждый из циклов повторяется, пока не будет введено значение,
            приводимое к типу double.
            */
            System.out.print("Пожалуйста, введите поочередно координаты точек отрезка\n");
            while (true) {
                System.out.println("X координата первой точки отрезка: \n");
                if (sc.hasNextDouble()) {
                    x1 = sc.nextDouble();
                    break;
                }
            }
            while (true) {
                System.out.println("Y координата первой точки отрезка: \n");
                if (sc.hasNextDouble()) {
                    y1 = sc.nextDouble();
                    break;
                }
            }
            while (true) {
                System.out.println("X координата второй точки отрезка: \n");
                if (sc.hasNextDouble()) {
                    x2 = sc.nextDouble();
                    break;
                }
            }
            while (true) {
                System.out.println("Y координата второй точки отрезка: \n");
                if (sc.hasNextDouble()) {
                    y2 = sc.nextDouble();
                    break;
                }
            }
            //Создаем точки, объединяем в сегменты и добавляем к общему массиву.
            a = new Point(x1, y1);
            b = new Point(x2, y2);
            if ((a.getX() == 0 && a.getY() == 0) || (b.getY() == 0 && b.getX() == 0)) {
                System.out.println("Точка находится в камере, ничего не видно.");
            } else {
                s = new Segment(a, b);

                //Проверяем, является ли отрезок точкой.
                if (!s.isAPoint()) {
                    Segments.add(s);
                } else {
                    System.out.println("Координаты первой и второй точек отрезка не должны совпадать.\n");
                }
            }

            // Запрашиваем подтверждение на продолжение ввода. Цикл повторяется, пока не будет введен "y" или "n".
            start:
            while (c) {
                System.out.println("Завершить ввод? y/n \n");
                switch (sc.next()) {
                    case "y":
                        c = false;
                        break;
                    case "n":
                        System.out.println("Принято.");
                        break start;
                }
            }
        }

        if (Segments.size() == 1) {
            System.out.println("Отрезок всего один, конечно, он виден");
        } else {
            try {
                //Создаем отрезки-маркеры от камеры к концам "красного" отрезка.
                System.out.println(Segments.size());
                Segment mark1 = new Segment(cam, Segments.get(Segments.size() - 1).getMaxTP());


                System.out.println(mark1.getCPoint(Segments.get(Segments.size() - 1)).getX());
                System.out.println(mark1.getCPoint(Segments.get(Segments.size() - 1)).getY());

                // Определяем "конус видимости"
                double globalMaxT = Segments.get(Segments.size() - 1).getMaxTP().getTheta();
                double globalMinT = Segments.get(Segments.size() - 1).getMinTP().getTheta();


                // Создаем отрезки  под углом +- 0.0000000001 theta к существующим точкам и с длиной globalR
                ArrayList<Segment> beams = new ArrayList<Segment>();
                // Определяем расстояние, на котором тестируем на пересечение.
                // Для перестраховки, берем расстояние до дальней точки красного отрезка +1
                double globalR = Segments.get(Segments.size() - 1).getMaxR() + 1;
                for (int i = 0; i < Segments.size(); i++) {
                    // Создание и добавление лучей(технически отрезков) с минимальным смещением. Степень смещения можно
                    // изменить в методах markLeft, markRight класса Point.
                    Point m1 = new Point(Segments.get(i).getA().getX(), Segments.get(i).getA().getY()).markLeft(globalR);
                    Point m2 = new Point(Segments.get(i).getB().getX(), Segments.get(i).getB().getY()).markLeft(globalR);
                    Point m3 = new Point(Segments.get(i).getA().getX(), Segments.get(i).getA().getY()).markRight(globalR);
                    Point m4 = new Point(Segments.get(i).getB().getX(), Segments.get(i).getB().getY()).markRight(globalR);
                    beams.add(new Segment(cam, m1));
                    beams.add(new Segment(cam, m2));
                    beams.add(new Segment(cam, m3));
                    beams.add(new Segment(cam, m4));
                }
                //"ленивый" счетчик пересечений тестового и красного отрезков. Красный виден, если vs > 0.
                int vs = 0;
                // Основной цикл проверки. Перебирает на наличие пересечений все лучи и отрезки.
                for (int z = 0; z < beams.size(); z++) {

                    Point testPoint = new Point(globalR + 1, globalR + 1);

                    for (int e = 0; e < Segments.size(); e++) {
                        //Точка-маркер, используется для построения временных отрезков из (0,0)

                        // Проверяем, пересекается ли луч из (0,0) с отрезками из Segments и, если да, обновляем
                        // точку-маркер. Это позволяет узнать ближайшую точку пересечения с чем-либо.
                        if (beams.get(z).isCrossed(Segments.get(e)) && testPoint.getR() >=
                                beams.get(z).getCPoint(Segments.get(e)).getR()) {
                            testPoint.setX(beams.get(z).getCPoint(Segments.get(e)).getX());
                            testPoint.setY(beams.get(z).getCPoint(Segments.get(e)).getY());
                            System.out.println(testPoint.getR());
                            System.out.println("xt " + testPoint.getX());
                            System.out.println("yt " + testPoint.getY());
                            // Создает новый отрезок из точки (0,0) к эталонной и проверяет, пересекается ли она
                            // красный отрезком. Если да, счетчик vs увеличивается.
                            Segment testSegment = new Segment(cam, testPoint);
                            System.out.println("testsegment");
                            if (testSegment.isCrossed(Segments.get(Segments.size()-1))) {
                                vs++;
                                System.out.println("x: " + (testSegment.getCPoint(Segments.get(Segments.size() - 1)).getX()));
                                System.out.println("y: " + (testSegment.getCPoint(Segments.get(Segments.size() - 1)).getY()));

                            }
                            testPoint.setX(globalR + 1);
                            testPoint.setY(globalR + 1);
                        }

                    }
                }
                System.out.println("найдено пересечений: " + vs);

            }

            // Обрабатываем случай отсутствия данных.
            catch (IndexOutOfBoundsException e) {
                System.out.println("Не был указан ни один отрезок.");
            }


        }


    }
}




