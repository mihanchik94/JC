package org.example;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Circle implements Shape {
    private final int radius;

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
}
