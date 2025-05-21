package org.example;

public class GeometryUtils {
    public static int compareAreas(double firstArea, double secondArea) {
        if (firstArea > secondArea) {
            return 1;
        }
        return firstArea == secondArea ? 0 : -1;
    }

    public static String getTextOfComparingAreasResult(double firstArea, double secondArea) {
        if (compareAreas(firstArea, secondArea) == 1) {
            return "The first shape area is greater than the area of the second shape.";
        }
        return compareAreas(firstArea, secondArea) == 0 ? "Areas are equal."
                : "The second shape area is greater than the area of the first shape.";
    }
}
