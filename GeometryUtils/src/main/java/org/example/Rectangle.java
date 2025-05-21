package org.example;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Rectangle implements Shape {
    private final int firstSide;
    private final int secondSide;

    @Override
    public double getArea() {
        return firstSide * secondSide;
    }

    @Override
    public double getPerimeter() {
        return firstSide * 2 + secondSide * 2;
    }
}
