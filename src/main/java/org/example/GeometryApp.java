package org.example;

import java.util.List;

public class GeometryApp {
    public static void main(String[] args) {
        List<Shape> shapes = List.of(
                new Circle(2),
                new Rectangle(3, 4),
                new Triangle(2, 3, 4)
        );

        shapes.forEach(shape -> {
            String name = shape.getClass().getSimpleName();
            double area = shape.getArea();
            double perimeter = shape.getPerimeter();

            System.out.printf("Shape: %s. Area: %.2f, Perimeter: %.2f%n", name, area, perimeter);
        });

        double firstShapeArea = shapes.get(0).getArea();
        double secondShapeArea = shapes.get(1).getArea();

        System.out.println(GeometryUtils.compareAreas(firstShapeArea, secondShapeArea));
        System.out.println(GeometryUtils.getTextOfComparingAreasResult(firstShapeArea, secondShapeArea));

        Cube cube = new Cube();
        Sphere sphere = new Sphere();
    }
}
