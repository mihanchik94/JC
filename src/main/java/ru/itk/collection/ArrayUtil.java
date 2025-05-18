package ru.itk.collection;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ArrayUtil {
    public static <T> Map<T, Long> countElements(T[] elements) {
        return Arrays.stream(elements)
                .collect(Collectors.groupingBy(key -> key, Collectors.counting()));
    }
}
