package org.example;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Triangle implements Shape {
    private final int firstSide;
    private final int secondSide;
    private final int thirdSide;


    @Override
    public double getArea() {
        double halfPerimeter = getPerimeter() / 2;
        return Math.sqrt(halfPerimeter
                * (halfPerimeter - firstSide)
                * (halfPerimeter - secondSide)
                * (halfPerimeter - thirdSide));
    }

    @Override
    public double getPerimeter() {
        return firstSide + secondSide + thirdSide;
    }
}
